import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

// Clase para interactuar con la persistencia de sumarios mediante el patrón DAO (Objeto de Acceso a Datos)
// En esta iteracion los objetos son almacenados en memoria con un Map. En la proxima iteracion se implementara 
// el uso de una base de datos SQL.
public class SumarioDAO {

    private static SumarioDAO instancia;
    private Map<Integer, Sumario> sumarios;
    private static int proximoID = 1;

    public static int generarID() {
        return proximoID++;
    }

     // Constructor privado para evitar la instanciación directa
     private SumarioDAO() {
        this.sumarios = new HashMap<>();
    }

    // Método estático para obtener la instancia única de SumarioDAO
    public static synchronized SumarioDAO instancia() {
        if (instancia == null) {
            instancia = new SumarioDAO();
        }
        return instancia;
    }

    
    public Sumario obtenerSumario(int id) {
        return this.sumarios.get(id);
    }

    public List<Diligencia> obtenerDiligenciasSumario(int idSumario) {
        Sumario sumario = this.obtenerSumario(idSumario);

        return sumario.getDiligencias();
    }

    
    public void agregarSumario(int numero, String juzgado, String fiscalia, String juez, String fiscal, String imputados, String damnificados) {
        Sumario sumario = new Sumario(SumarioDAO.generarID(), numero, juzgado, fiscalia, juez, fiscal, imputados, damnificados);
        this.sumarios.put(sumario.getId(), sumario);
    }

    
    public void actualizarSumario(int id, int numero, String juzgado, String fiscalia, String juez, String fiscal, String imputados, String damnificados) {
        Sumario sumario = this.obtenerSumario(id);
        if(sumario != null) {
            sumario.setNumero(numero);
            sumario.setJuzgado(juzgado);
            sumario.setFiscalia(fiscalia);
            sumario.setJuez(juez);
            sumario.setFiscal(fiscal);
            sumario.setImputados(imputados);
            sumario.setDamnificados(damnificados);
            this.sumarios.put(sumario.getId(), sumario);
        }
    }

    
    public void eliminarSumario(int id) {
        this.sumarios.remove(id);
    }

    public void eliminarDiligencia(int id) {
        Diligencia diligencia = DiligenciaDAO.instancia().obtenerDiligencia(id);

        for(Sumario sumario : this.listarSumarios()) {
            if (sumario.getDiligencias().contains(diligencia)) {
                sumario.eliminarDiligencia(diligencia);
            }
        }
    }

    public Sumario buscarSumarioPorDiligencia(Diligencia diligencia) {

        for(Sumario sumario : this.listarSumarios()) {
            if (sumario.getDiligencias().contains(diligencia)) {
                return sumario;
            }
        }

        return null;
    }


    public List<Sumario> listarSumarios() {
        return List.copyOf(sumarios.values());
    }


    public List<Sumario> buscarSumarioPorNumero(int numero) {
        List<Sumario> resultado = new ArrayList<Sumario>();

        for(Sumario sumario : this.listarSumarios()) {
            if (sumario.getNumero() == numero) {
                resultado.add(sumario);
            }
        }
        return resultado;
    }
    public List<Sumario> buscarSumarioPorJuzgado(String juzgado) {
        List<Sumario> resultado = new ArrayList<Sumario>();

        for(Sumario sumario : this.listarSumarios()) {
            if (sumario.getJuzgado().equals(juzgado)) {
                resultado.add(sumario);
            }
        }
        return resultado;
    }

    public List<Sumario> buscarSumarioPorFiscalia(String fiscalia) {
        List<Sumario> resultado = new ArrayList<Sumario>();

        for(Sumario sumario : this.listarSumarios()) {
            if (sumario.getFiscalia().equals(fiscalia)) {
                resultado.add(sumario);
            }
        }
        return resultado;
    }

    public List<Sumario> buscarSumarioPorJuez(String juez) {
        List<Sumario> resultado = new ArrayList<Sumario>();

        for(Sumario sumario : this.listarSumarios()) {
            if (sumario.getJuez().equals(juez)) {
                resultado.add(sumario);
            }
        }
        return resultado;
    }

    public List<Sumario> buscarSumarioPorFiscal(String fiscal) {
        List<Sumario> resultado = new ArrayList<Sumario>();

        for(Sumario sumario : this.listarSumarios()) {
            if (sumario.getFiscal().equals(fiscal)) {
                resultado.add(sumario);
            }
        }
        return resultado;
    }

    public List<Sumario> buscarSumarioPorImputados(String imputados) {
        List<Sumario> resultado = new ArrayList<Sumario>();

        for(Sumario sumario : this.listarSumarios()) {
            if (sumario.getImputados().equals(imputados)) {
                resultado.add(sumario);
            }
        }
        return resultado;
    }

    public List<Sumario> buscarSumarioPorDamnificados(String damnificados) {
        List<Sumario> resultado = new ArrayList<Sumario>();

        for(Sumario sumario : this.listarSumarios()) {
            if (sumario.getDamnificados().equals(damnificados)) {
                resultado.add(sumario);
            }
        }
        return resultado;
    }
}

