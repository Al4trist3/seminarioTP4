import java.util.HashMap;
import java.util.Map;

//clase se encarga de la instanciacion de objetos tipo TipoCuerpoDiligencia mediantes el patron factory
public class TipoCuerpoDiligenciaFactory {
    private static final Map<String, TipoCuerpoDiligencia> tipoDiligencias = new HashMap<>();

    static {
        tipoDiligencias.put("Declaracion", new Declaracion());
        tipoDiligencias.put("Informe", new Informe());
        tipoDiligencias.put("Constancia", new Constancia());
    }

    public static TipoCuerpoDiligencia obtenerTipoDiligencia(String tipo) {
        return tipoDiligencias.getOrDefault(tipo, null);
    }
}