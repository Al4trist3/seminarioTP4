import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MenuContexto menuContext = new MenuContexto(scanner);
        menuContext.ejecutar();
        scanner.close();
        ConexionJDBC.cerrarConexion();
    }
}