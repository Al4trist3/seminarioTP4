import java.util.InputMismatchException;
import java.util.Scanner;



// Clase para controlar el flujo del programa mediante el patron State. Cada estado
// es un menu diferente
public class MenuContexto {
    private MenuState currentState;
    private Scanner scanner;

    public MenuContexto(Scanner scanner) {
        this.scanner = scanner;
        this.currentState = new MenuPrincipal(scanner);
    }

    public void ejecutar() {
        int opcion;
        do {
            currentState.mostrarMenu();
            System.out.print("Ingrese opción: ");
            try {
                opcion = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Error: Debes ingresar un valor numérico.");
                scanner.next(); 
                opcion = -1;
            }
            currentState = currentState.procesarOpcion(opcion);
        } while (currentState != null); // Continúa hasta que el estado actual sea null (se sale del programa)
    }
}
