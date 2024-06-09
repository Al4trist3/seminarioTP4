import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

// Clase para interactuar con la persistencia de personal policial mediante el patrón DAO (Objeto de Acceso a Datos)
// En esta iteracion los objetos son almacenados en memoria con un Map. En la proxima iteracion se implementara 
// el uso de una base de datos SQL.
public class PersonalPolicialDAO {

    private static PersonalPolicialDAO instancia;
    private Map<Integer, PersonalPolicial> personal;
    private static int proximoID = 1;

    public static int generarID() {
        return proximoID++;
    }


     // Constructor privado para evitar la instanciación directa
     private PersonalPolicialDAO() {
        this.personal = new HashMap<>();
    }

    // Método estático para obtener la instancia única de SumarioDAO
    public static synchronized PersonalPolicialDAO instancia() {
        if (instancia == null) {
            instancia = new PersonalPolicialDAO();
        }
        return instancia;
    }

    
    public PersonalPolicial obtenerPersonalPolicial(int id) {
        return this.personal.get(id);
    }

    
    public void agregarPersonalPolicial(String nombre, String jerarquia) {
        PersonalPolicial personal = new PersonalPolicial(PersonalPolicialDAO.generarID(), nombre, jerarquia);
        this.personal.put(personal.getId(), personal);
    }

    
    public void actualizarPersonalPolicial(int id, String nombre, String jerarquia) {
        PersonalPolicial personal = this.obtenerPersonalPolicial(id);
        if(personal != null) {
            personal.setNombre(nombre);
            personal.setJerarquia(jerarquia);
            this.personal.put(personal.getId(), personal);
        }
    }

    
    public void eliminarPersonalPolicial(int id) {
        this.personal.remove(id);
    }

    public List<PersonalPolicial> listarPersonalPolicial() {
        return List.copyOf(this.personal.values());
    }

    public List<PersonalPolicial> buscarPersonalPolicialPorNombre(String nombre) {
        List<PersonalPolicial> resultado = new ArrayList<PersonalPolicial>();

        for(PersonalPolicial personal : this.listarPersonalPolicial()) {
            if (personal.getNombre().equals(nombre)) {
                resultado.add(personal);
            }
        }
        return resultado;
    }

    public List<PersonalPolicial> buscarPersonalPolicialPorJerarquia(String jerarquia) {
        List<PersonalPolicial> resultado = new ArrayList<PersonalPolicial>();

        for(PersonalPolicial personal : this.listarPersonalPolicial()) {
            if (personal.getJerarquia().equals(jerarquia)) {
                resultado.add(personal);
            }
        }
        return resultado;
    }

}