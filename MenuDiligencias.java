import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;
// Clase para representar un estado del patron State para cambiar entre menus de la aplicacion
// Tercer estado: Menú de Diligencias
class MenuDiligencias implements MenuState {
    private Scanner scanner;

    public MenuDiligencias(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void mostrarMenu() {
        System.out.println("=== Menú de Diligencias ===");
        System.out.println("1. Registrar Diligencia");
        System.out.println("2. Modificar Diligencia");
        System.out.println("3. Eliminar Diligencia");
        System.out.println("4. Consultar Diligencia");
        System.out.println("5. Listar Diligencias");
        System.out.println("0. Volver al Menú Principal");
    }

    @Override
    public MenuState procesarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                this.registrarDiligencia();
                return this;
            case 2:
                this.modificarDiligencia();
                return this;
            case 3:
                this.eliminarDiligencia();
                return this;
            case 4:
                this.consultarDiligencia();
                return this;
            case 5:
                this.listarDiligencias();
                return this;
            case 0:
                return new MenuPrincipal(scanner);
            default:
                System.out.println("Opción no válida. Por favor, seleccione nuevamente.");
                return this; 
        }
    }
    
    void registrarDiligencia() {

        if(this.chequearPrecondiciones()){
            System.out.println("Es necesario que existan al menos un Sumario y un instructor");
            return;
        }


        Date fecha;
        PersonalPolicial instructor;
        Sumario sumario;
        String textoCuerpo;
        String tipo;

        fecha = this.obtenerFecha();

        instructor = this.obtenerInstructor();
        sumario = this.obtenerSumario();

        textoCuerpo = this.obtenerAtributo(scanner, "texto");
        tipo = this.obtenerTipo();

        DiligenciaDAO.instancia().agregarDiligencia(fecha, instructor, textoCuerpo, tipo, sumario);
    }

    boolean chequearPrecondiciones() {
        return SumarioDAO.instancia().listarSumarios().isEmpty() || PersonalPolicialDAO.instancia().listarPersonalPolicial().isEmpty();
    }

    Date obtenerFecha() {
        Date fecha = null;
        String fechaTexto;
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");;

        while(fecha == null) {
            try {
                fechaTexto =  this.obtenerAtributo(scanner, "Fecha (dd/MM/yy)");
                fecha = dateFormat.parse(fechaTexto);
            } catch (ParseException e) {
                System.out.println("Error al parsear la fecha: " + e.getMessage());
                fecha = null;
            }
        }
        
        return fecha;

    }

    PersonalPolicial obtenerInstructor() {
        int id;
        PersonalPolicial instructor = null;

        
        System.out.println("=== Seleccione Instructor ===");
        this.listarPersonalPolicial();

        while(instructor == null) {
            try {
                id = this.obtenerNumero(this.scanner, "ID");
                instructor = PersonalPolicialDAO.instancia().obtenerPersonalPolicial(id);
            
            } catch (InputMismatchException e) {
                System.out.println("Error: Debes ingresar un valor numérico.");
                instructor = null;
                scanner.next(); // Limpia el buffer del scanner para evitar un bucle infinito
            }
        }

        return instructor;
        
    }

    Sumario obtenerSumario() {
        int id;
        Sumario sumario = null;

        
        System.out.println("=== Seleccione Sumario ===");
        this.listarSumarios();

        while(sumario == null) {
            try {
                id = this.obtenerNumero(this.scanner, "ID");
                sumario = SumarioDAO.instancia().obtenerSumario(id);
            
            } catch (InputMismatchException e) {
                System.out.println("Error: Debes ingresar un valor numérico.");
                sumario = null;
                scanner.next();
            }
        }

        return sumario;
        
    }

    String obtenerTipo() {
        
        String tipo = "";
        int opcion = -1; 

        System.out.println("=== Elija tipo de diligencia ===");
        System.out.println("1. Declaracion");
        System.out.println("2. Informe");
        System.out.println("3. Constancia");
    
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
                    tipo = "Declaracion";
                    break;
                case 2:
                    tipo = "Informe";
                    break;
                case 3:
                    tipo = "Constancia";
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, seleccione nuevamente.");
                    continue;
            }

        }

        return tipo;
    }

    void eliminarDiligencia() {
        int id;
        System.out.println("=== Eliminar Diligencia ===");
        this.listarDiligencias();

        try {
            id = this.obtenerNumero(this.scanner, "ID");
            DiligenciaDAO.instancia().eliminarDiligencia(id);
        } catch (InputMismatchException e) {
            System.out.println("Error: Debes ingresar un valor numérico.");
            scanner.next();
        }
        
    }


    void modificarDiligencia() {
        Date fecha;
        PersonalPolicial instructor;
        Sumario sumario;
        String textoCuerpo;
        String tipo;
        int id;

        if(this.chequearPrecondiciones()){
            System.out.println("Es necesario que existan al menos un Sumario y un instructor");
            return;
        }

        
        System.out.println("=== Modificar Diligencia ===");
        this.listarDiligencias();

        try {
            id = this.obtenerNumero(this.scanner, "ID");
            
            fecha = this.obtenerFecha();

            instructor = this.obtenerInstructor();
            sumario = this.obtenerSumario();
    
            textoCuerpo = this.obtenerAtributo(scanner, "texto");
            tipo = this.obtenerTipo();
    
            DiligenciaDAO.instancia().actualizarDiligencia(id, fecha, instructor, textoCuerpo, tipo, sumario);
            
            
        } catch (InputMismatchException e) {
            System.out.println("Error: Debes ingresar un valor numérico.");
            scanner.next();
        }
        
    }

    void consultarDiligencia() {

        System.out.println("=== Consulta de Diligencias ===");
        
        int opcion = -1; 

        System.out.println("=== Elija opcion de busqueda ===");
        System.out.println("1. Por fecha");
        System.out.println("2. Por instructor");
        System.out.println("3. Por tipo");
        
    
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
                    this.consultarDiligenciaPorFecha();
                    break;
                case 2:
                    this.consultarDiligenciaPorInstructor();
                    break;
                case 3:
                    this.consultarDiligenciaPorTipo();
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, seleccione nuevamente.");
                    continue;
            }

        }
        
    }

    void consultarDiligenciaPorFecha() {
        Date fecha;

        fecha = this.obtenerFecha();

        for(Diligencia diligencia : DiligenciaDAO.instancia().buscarDiligenciaPorFecha(fecha)) {
            this.imprimirDiligencia(diligencia);
        }
    }

    void consultarDiligenciaPorInstructor() {
        PersonalPolicial instructor;

        instructor = this.obtenerInstructor();

        for(Diligencia diligencia : DiligenciaDAO.instancia().buscarDiligenciaPorInstructor(instructor)) {
            this.imprimirDiligencia(diligencia);
        }
    }

    void consultarDiligenciaPorTipo() {
        String tipo;

        tipo = this.obtenerTipo();

        for(Diligencia diligencia : DiligenciaDAO.instancia().buscarDiligenciaPorTipo(tipo)) {
            this.imprimirDiligencia(diligencia);
        }
    }


}