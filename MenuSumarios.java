import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

// Clase para representar un estado del patron State para cambiar entre menus de la aplicacion
// Segundo estado: Menú de Sumarios
class MenuSumarios implements MenuState {
    private Scanner scanner;

    public MenuSumarios(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void mostrarMenu() {
        System.out.println("=== Menú de Sumarios ===");
        System.out.println("1. Registrar Sumario");
        System.out.println("2. Modificar Sumario");
        System.out.println("3. Eliminar Sumario");
        System.out.println("4. Consultar Sumario");
        System.out.println("5. Listar Sumarios");
        System.out.println("6. Imprimir Sumario");
        System.out.println("0. Volver al Menú Principal");
    }

    @Override
    public MenuState procesarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                this.registrarSumario();
                return this;
            case 2:
                this.modificarSumario();
                return this;
            case 3:
                this.eliminarSumario();
                return this;
            case 4:
                this.consultarSumario();
                return this;
            case 5:
                this.listarSumarios();
                return this;
            case 6:
                this.imprimirSumario();
                return this;
            case 0:
                return new MenuPrincipal(scanner);
            default:
                System.out.println("Opción no válida. Por favor, seleccione nuevamente.");
                return this;
        }
    }

    void registrarSumario() {

        List<String> atributos;

        int numero = this.obtenerNumeroSumario();

        atributos = this.obtenerAtributos();

        SumarioDAO.instancia().agregarSumario(numero, atributos.get(0), atributos.get(1), atributos.get(2), atributos.get(3), atributos.get(4), atributos.get(5));
    }

    int obtenerNumeroSumario() {
        int numero = -1;

        while(numero == -1) {
            try {
                numero =  this.obtenerNumero(scanner, "Numero");
            } catch (InputMismatchException e) {
                System.out.println("Error: Debes ingresar un valor numérico.");
                numero = -1;
                scanner.next();
            }
        }

        return numero;


    }

    List<String> obtenerAtributos() {
        
        
        String juzgado;
        String fiscalia;
        String juez;
        String fiscal;
        String imputados;
        String damnificados;

        List<String> atributos = new ArrayList<>();
        

        
        juzgado = this.obtenerAtributo(this.scanner, "Juzgado");
        fiscalia = this.obtenerAtributo(this.scanner,"Fiscalia");
        juez = this.obtenerAtributo(this.scanner,"Juez");
        fiscal = this.obtenerAtributo(this.scanner,"Fiscal");
        imputados = this.obtenerAtributo(this.scanner,"Imputados");
        damnificados = this.obtenerAtributo(this.scanner,"Damnificados");

        atributos.add(juzgado);
        atributos.add(fiscalia);
        atributos.add(juez);
        atributos.add(fiscal);
        atributos.add(imputados);
        atributos.add(damnificados);

        return atributos;
    }


    void eliminarSumario() {
        int id;
        System.out.println("=== Eliminar Sumario ===");
        this.listarSumarios();

        try {
            id = this.obtenerNumero(this.scanner, "ID");
            SumarioDAO.instancia().eliminarSumario(id);
        } catch (InputMismatchException e) {
            System.out.println("Error: Debes ingresar un valor numérico.");
            scanner.next();
        }
        
    }

    void modificarSumario() {
        List<String> atributos;
        int id;
        int numero;

        
        System.out.println("=== Modificar Sumario ===");
        this.listarSumarios();

        try {
            id = this.obtenerNumero(this.scanner, "ID");
            numero = this.obtenerNumeroSumario();
            atributos = this.obtenerAtributos();
            SumarioDAO.instancia().actualizarSumario(id, numero, atributos.get(0), atributos.get(1), atributos.get(2), atributos.get(3), atributos.get(4), atributos.get(5));
            
        } catch (InputMismatchException e) {
            System.out.println("Error: Debes ingresar un valor numérico.");
            scanner.next();
        }
        
    }

    void consultarSumario() {

        System.out.println("=== Consulta de Sumarios ===");
        
        int opcion = -1; 

        System.out.println("=== Elija opcion de busqueda ===");
        System.out.println("1. Por numero");
        System.out.println("2. Por juzgado");
        System.out.println("3. Por fiscalia");
        System.out.println("4. Por juez");
        System.out.println("5. Por fiscal");
        System.out.println("6. Por inputados");
        System.out.println("7. Por damnificados");
    
        while (opcion == -1) {

            try {
                opcion = leerNumero(scanner);
            } catch (InputMismatchException e) {
                System.out.println("Error: Debes ingresar un valor numérico.");
                scanner.next();
                opcion = -1;
            }
            switch (opcion) {
                case 1:
                    this.consultarSumarioPorNumero();
                    break;
                case 2:
                    this.consultarSumarioPorJuzgado();
                    break;
                case 3:
                    this.consultarSumarioPorFiscalia();
                    break;
                case 4:
                    this.consultarSumarioPorJuez();
                    break;
                case 5:
                    this.consultarSumarioPorFiscal();
                    break;
                case 6:
                    this.consultarSumarioPorImputados();
                    break;
                case 7:
                    this.consultarSumarioPorDamnificados();
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, seleccione nuevamente.");
                    continue;
            }

        }
        
    }

    void consultarSumarioPorNumero() {
        int numero;

        numero = this.obtenerNumeroSumario();

        for(Sumario sumario : SumarioDAO.instancia().buscarSumarioPorNumero(numero)) {
            this.imprimirSumario(sumario);
        }
    }

    void consultarSumarioPorFiscalia() {
        String fiscalia;

        fiscalia = this.obtenerAtributo(this.scanner, "Fiscalia");

        for(Sumario sumario : SumarioDAO.instancia().buscarSumarioPorFiscalia(fiscalia)) {
            this.imprimirSumario(sumario);
        }
    }

    void consultarSumarioPorJuzgado() {
        String juzgado;

        juzgado = this.obtenerAtributo(this.scanner, "Juzgado");

        for(Sumario sumario : SumarioDAO.instancia().buscarSumarioPorJuzgado(juzgado)) {
            this.imprimirSumario(sumario);
        }
    }

    void consultarSumarioPorJuez() {
        String juez;

        juez = this.obtenerAtributo(this.scanner, "Juez");

        for(Sumario sumario : SumarioDAO.instancia().buscarSumarioPorJuez(juez)) {
            this.imprimirSumario(sumario);
        }
    }

    void consultarSumarioPorFiscal() {
        String fiscal;

        fiscal = this.obtenerAtributo(this.scanner, "Fiscal");

        for(Sumario sumario : SumarioDAO.instancia().buscarSumarioPorFiscal(fiscal)) {
            this.imprimirSumario(sumario);
        }
    }

    void consultarSumarioPorImputados() {
        String imputados;

        imputados = this.obtenerAtributo(this.scanner, "Imputados");

        for(Sumario sumario : SumarioDAO.instancia().buscarSumarioPorImputados(imputados)) {
            this.imprimirSumario(sumario);
        }
    }

    void consultarSumarioPorDamnificados() {
        String damnificados;

        damnificados = this.obtenerAtributo(this.scanner, "Damnificados");

        for(Sumario sumario : SumarioDAO.instancia().buscarSumarioPorDamnificados(damnificados)) {
            this.imprimirSumario(sumario);
        }
    }


    void imprimirSumario() {
        int id;
        Sumario sumario;
        System.out.println("=== Imprimir Sumario ===");
        this.listarSumarios();

        try {
            id = this.obtenerNumero(this.scanner, "ID");
            sumario = SumarioDAO.instancia().obtenerSumario(id);
            if(sumario != null) {
                sumario.imprimirSumario();
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Debes ingresar un valor numérico.");
            scanner.next();
        }
        
    }

}