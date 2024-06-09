
import java.util.Vector;

//clase para representar instancias de sumarios
public class Sumario {
    private int id;
    private int numero;
    private String juzgado;
    private String fiscalia;
    private String juez;
    private String fiscal;
    private String imputados;
    private String damnificados;
    private Vector<Diligencia> diligencias;

    public Sumario(int id, int numero, String juzgado, String fiscalia, String juez, String fiscal, String imputados, String damnificados) {
        this.id = id;
        this.numero = numero;
        this.juzgado = juzgado;
        this.fiscalia = fiscalia;
        this.juez = juez;
        this.fiscal = fiscal;
        this.imputados = imputados;
        this.damnificados = damnificados;
        this.diligencias = new Vector<>();
    }


    // Getters y setters
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getJuzgado() {
        return juzgado;
    }

    public void setJuzgado(String juzgado) {
        this.juzgado = juzgado;
    }


    public String getFiscalia() {
        return fiscalia;
    }

    public void setFiscalia(String fiscalia) {
        this.fiscalia = fiscalia;
    }


    public String getJuez() {
        return juez;
    }

    public void setJuez(String juez) {
        this.juez = juez;
    }

    public String getFiscal() {
        return fiscal;
    }

    public void setFiscal(String fiscal) {
        this.fiscal = fiscal;
    }

    public String getImputados() {
        return imputados;
    }

    public void setImputados(String imputados) {
        this.imputados = imputados;
    }

    public String getDamnificados() {
        return damnificados;
    }

    public void setDamnificados(String damnificados) {
        this.damnificados = damnificados;
    }

    public Vector<Diligencia> getDiligencias() {
        return diligencias;
    }

    public void setDiligencias(Vector<Diligencia> diligencias) {
        this.diligencias = diligencias;
    }

    public void agregarDiligencia(Diligencia diligencia) {
        this.diligencias.add(diligencia);
    }

    public void eliminarDiligencia(Diligencia diligencia) {
        this.diligencias.remove(diligencia);
    }

    public String generarCaratula() {
        String caratula =
            "Numero: " + this.getNumero() + "\n"
            + "Juzgado: " + this.getJuzgado() + "\n"
            + "Fiscalia: " + this.getFiscalia() + "\n"
            + "Juez: " + this.getJuez() + "\n"
            + "Fiscal: " + this.getFiscal() + "\n" 
            +"Imputados: " + this.getImputados() + "\n"
            + "Damnificados: " + this.getDamnificados();
        return caratula;
    }

    public void imprimirSumario() {
        System.out.println("============================================");
        System.out.println(this.generarCaratula());
        for(Diligencia diligencia: this.diligencias) {
            System.out.println("-----------------------------------------");
            System.out.println(diligencia.generarTextoCuerpo());
        }
    }
}

