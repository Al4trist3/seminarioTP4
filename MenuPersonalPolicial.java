import java.util.InputMismatchException;
import java.util.Scanner;

// Clase para representar un estado del patron State para cambiar entre menus de la aplicacion
// Cuarto estado: Menú de PersonalPolicial
class MenuPersonalPolicial implements MenuState {
    private Scanner scanner;

    public MenuPersonalPolicial(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void mostrarMenu() {
        System.out.println("=== Menú de Personal Policial ===");
        System.out.println("1. Registrar Personal Policial");
        System.out.println("2. Modificar Personal Policial");
        System.out.println("3. Eliminar Personal Policial");
        System.out.println("4. Consultar Personal Policial");
        System.out.println("5. Listar Personal Policial");
        System.out.println("0. Volver al Menú Principal");
    }

    @Override
    public MenuState procesarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                this.registrarPersonalPolicial();
                return this;
            case 2:
                this.modificarPersonalPolicial();
                return this;
            case 3:
                this.eliminarPersonalPolicial();
                return this;
            case 4:
                this.consultarPersonalPolicial();
                return this;
            case 5:
                this.listarPersonalPolicial();
                return this;
            case 0:
                return new MenuPrincipal(scanner);
            default:
                System.out.println("Opción no válida. Por favor, seleccione nuevamente.");
                return this;
        }
    }

    void registrarPersonalPolicial() {

        String nombre;
        String jerarquia;
        
        nombre = this.obtenerAtributo(this.scanner, "Nombre");
        jerarquia = this.obtenerJerarquia();
        PersonalPolicialDAO.instancia().agregarPersonalPolicial(nombre, jerarquia);
    }



    String obtenerJerarquia() {
        
        String jerarquia = "";
        int opcion = -1; 

        System.out.println("=== Elija jerarquia ===");
        System.out.println("1. Suboficial");
        System.out.println("2. Oficial");
        System.out.println("3. Oficial Jefe");
    
        while (jerarquia.isBlank()) {

            try {
                opcion = leerNumero(scanner);
            } catch (InputMismatchException e) {
                System.out.println("Error: Debes ingresar un valor numérico.");
                scanner.next();
                opcion = -1;
            }
            switch (opcion) {
                case 1:
                    jerarquia = "Suboficial";
                    break;
                case 2:
                    jerarquia = "Oficial";
                    break;
                case 3:
                    jerarquia = "Oficial Jefe";
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, seleccione nuevamente.");
                    continue;
            }

        }

        return jerarquia;
    }


    void eliminarPersonalPolicial() {
        int id;
        System.out.println("=== Eliminar Personal Policial ===");
        this.listarPersonalPolicial();

        try {
            id = this.obtenerNumero(this.scanner, "ID");
            PersonalPolicialDAO.instancia().eliminarPersonalPolicial(id);
        } catch (InputMismatchException e) {
            System.out.println("Error: Debes ingresar un valor numérico.");
            scanner.next();
        }
        
    }

    void modificarPersonalPolicial() {
        int id;
        String nombre;
        String jerarquia;
        
        System.out.println("=== Modificar Personal Policial ===");
        this.listarPersonalPolicial();

        try {
            id = this.obtenerNumero(this.scanner, "ID");
            nombre = this.obtenerAtributo(this.scanner, "Nombre");
            jerarquia = this.obtenerJerarquia();
            PersonalPolicialDAO.instancia().actualizarPersonalPolicial(id, nombre, jerarquia);
            
        } catch (InputMismatchException e) {
            System.out.println("Error: Debes ingresar un valor numérico.");
            scanner.next();
        }
        
    }

    void consultarPersonalPolicial() {

        System.out.println("=== Consulta de Personal Policial por nombre o Jerarquia ===");
        
        int opcion = -1; 

        System.out.println("=== Elija opcion de busqueda ===");
        System.out.println("1. Por nombre");
        System.out.println("2. Por jerarquia");
    
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
                this.consultarPersonalPolicialPorNombre();
                    break;
                case 2:
                this.consultarPersonalPolicialPorJerarquia();
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, seleccione nuevamente.");
                    continue;
            }

        }
        
    }

    void consultarPersonalPolicialPorNombre() {
        String nombre;

        nombre = this.obtenerAtributo(this.scanner, "Nombre");

        for(PersonalPolicial personal : PersonalPolicialDAO.instancia().buscarPersonalPolicialPorNombre(nombre)) {
            this.imprimirPersonal(personal);
        }
    }

    void consultarPersonalPolicialPorJerarquia() {
        String jerarquia;

        jerarquia = this.obtenerJerarquia();

        for(PersonalPolicial personal : PersonalPolicialDAO.instancia().buscarPersonalPolicialPorJerarquia(jerarquia)) {
            this.imprimirPersonal(personal);
        }
    }

}