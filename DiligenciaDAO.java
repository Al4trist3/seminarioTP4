import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

// Clase para interactuar con la persistencia de diligencias mediante el patrón DAO (Objeto de Acceso a Datos)
// En esta iteracion los objetos son almacenados en memoria con un Map. En la proxima iteracion se implementara 
// el uso de una base de datos SQL.
public class DiligenciaDAO {

    private static DiligenciaDAO instancia;
    private Map<Integer, Diligencia> diligencias;
    private static int proximoID = 1;

    //Retorna el proximo ID a usar y lo autoincrementa
    public static int generarID() {
        return proximoID++;
    }

     // Constructor privado para evitar la instanciación directa
     private DiligenciaDAO() {
        this.diligencias = new HashMap<>();
    }

    // Método estático para obtener la instancia única de SumarioDAO
    public static synchronized DiligenciaDAO instancia() {
        if (instancia == null) {
            instancia = new DiligenciaDAO();
        }
        return instancia;
    }

    
    public Diligencia obtenerDiligencia(int id) {
        return this.diligencias.get(id);
    }
    
    public void agregarDiligencia(Date fecha, PersonalPolicial instructor, String textoCuerpo, String tipo, Sumario sumario) {
        Diligencia diligencia = new Diligencia(DiligenciaDAO.generarID(), fecha, instructor, textoCuerpo, tipo);
        this.diligencias.put(diligencia.getId(), diligencia);
        sumario.agregarDiligencia(diligencia);
    }

    
    public void actualizarDiligencia(int id, Date fecha, PersonalPolicial instructor, String textoCuerpo, String tipo, Sumario sumario) {
        Diligencia diligencia = this.obtenerDiligencia(id);

        if(diligencia != null) {
            SumarioDAO.instancia().eliminarDiligencia(id);
            
            diligencia.setFecha(fecha);
            diligencia.setInstructor(instructor);
            diligencia.setTextoCuerpo(textoCuerpo);
            diligencia.setTipo(tipo);

            this.diligencias.put(diligencia.getId(), diligencia);
            sumario.agregarDiligencia(diligencia);
        }
    }

    
    public void eliminarDiligencia(int id) {
        SumarioDAO.instancia().eliminarDiligencia(id);
        this.diligencias.remove(id);
    }

    public List<Diligencia> listarDiligencias() {
        return List.copyOf(this.diligencias.values());
    }

    public List<Diligencia> buscarDiligenciaPorFecha(Date fecha) {
        List<Diligencia> resultado = new ArrayList<Diligencia>();

        for(Diligencia diligencia : this.listarDiligencias()) {
            if (diligencia.getFecha().equals(fecha)) {
                resultado.add(diligencia);
            }
        }
        return resultado;
    }

    public List<Diligencia> buscarDiligenciaPorInstructor(PersonalPolicial instructor) {
        List<Diligencia> resultado = new ArrayList<Diligencia>();

        for(Diligencia diligencia : this.listarDiligencias()) {
            if (diligencia.getInstructor().equals(instructor)) {
                resultado.add(diligencia);
            }
        }
        return resultado;
    }

    public List<Diligencia> buscarDiligenciaPorTipo(String tipo) {
        List<Diligencia> resultado = new ArrayList<Diligencia>();

        for(Diligencia diligencia : this.listarDiligencias()) {
            if (diligencia.esTipo(tipo)) {
                resultado.add(diligencia);
            }
        }
        return resultado;
    }

}