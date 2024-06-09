import java.util.Scanner;
import java.util.InputMismatchException;

// Interfaz para definir el comportamiento de los distintos estados definidos para el patron State
interface MenuState {
    void mostrarMenu();
    MenuState procesarOpcion(int opcion);

    default String obtenerAtributo(Scanner scanner, String nombreAtributo) {
        String atributo;
        
        System.out.println("=== Ingrese " + nombreAtributo + " ===");

        atributo = scanner.nextLine();

        return atributo;
    }

    default int obtenerNumero(Scanner scanner, String nombreAtributo) throws InputMismatchException {
        int atributo;
        
        System.out.println("=== Ingrese " + nombreAtributo + " ===");
        atributo = leerNumero(scanner);

        return atributo;
    }

    default int leerNumero(Scanner scanner) throws InputMismatchException {
        int numero;
        numero = scanner.nextInt();
        scanner.nextLine();

        return numero;
    }

    default void listarSumarios() {
        System.out.println("=== Listado Sumarios ===");
        for(Sumario sumario : SumarioDAO.instancia().listarSumarios()) {
            this.imprimirSumario(sumario);
        }
    }

    default void imprimirSumario(Sumario sumario) {
        System.out.println(
            "ID: " + sumario.getId() + " | Numero: " + sumario.getNumero() + " | Juzgado: " + sumario.getJuzgado()
            + " | Fiscalia: " + sumario.getFiscalia() + " | Juez: " + sumario.getJuez() + " | Fiscal: " + sumario.getFiscal()
            + " | Imputados: " + sumario.getImputados() + " | Damnificados: " + sumario.getDamnificados()
            );
    }

    default void listarPersonalPolicial() {
        System.out.println("=== Listado Personal Policial ===");
        for(PersonalPolicial personal : PersonalPolicialDAO.instancia().listarPersonalPolicial()) {
            this.imprimirPersonal(personal);
        }
    }

    default void imprimirPersonal(PersonalPolicial personal) {
        System.out.println("ID: " + personal.getId() + " | Nombre: " + personal.getNombre() + " | Jerarquia: " + personal.getJerarquia());
    }

    default void listarDiligencias() {
        System.out.println("=== Listado Diligencias ===");
        for(Diligencia diligencia : DiligenciaDAO.instancia().listarDiligencias()) {
            this.imprimirDiligencia(diligencia);
        }
    }

    default void imprimirDiligencia(Diligencia diligencia) {
        System.out.println(
            "ID: " + diligencia.getId() + " | Fecha: " + diligencia.getFecha()
            + " | Instructor: " + diligencia.getInstructor().getNombre() + " " + diligencia.getInstructor().getJerarquia()
            + " | Tipo: " + diligencia.getTipo().nombreTipo()
            );
    }

}