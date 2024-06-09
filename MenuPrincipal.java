import java.util.Scanner;

// Clase para representar un estado del patron State para cambiar entre menus de la aplicacion
// Primer estado: Menú principal
class MenuPrincipal implements MenuState {
    private Scanner scanner;

    public MenuPrincipal(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void mostrarMenu() {
        System.out.println("=== Menú Principal ===");
        System.out.println("1. Menú de Sumarios");
        System.out.println("2. Menú de Diligencias");
        System.out.println("3. Menú de Personal Policial");
        System.out.println("0. Salir");
    }
    
    @Override
    public MenuState procesarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                return new MenuSumarios(scanner);
            case 2:
                return new MenuDiligencias(scanner);
            case 3:
                return new MenuPersonalPolicial(scanner);
            case 0:
                System.out.println("Saliendo del programa...");
                return null; // Termina la ejecución del programa
            default:
                System.out.println("Opción no válida. Por favor, seleccione nuevamente.");
                return this;
        }
    }
}