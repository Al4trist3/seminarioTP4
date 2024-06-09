import java.util.Date;

//clase para representar instancias de Diligencias
public class Diligencia {
    private int id;
    private Date fecha;
    private PersonalPolicial instructor;
    private String textoCuerpo;
    private TipoCuerpoDiligencia tipo;

    public Diligencia(int id, Date fecha, PersonalPolicial instructor, String textoCuerpo, String tipo) {
        this.id = id;
        this.fecha = fecha;
        this.instructor = instructor;
        this.textoCuerpo = textoCuerpo;
        this.tipo = TipoCuerpoDiligenciaFactory.obtenerTipoDiligencia(tipo);
    }

    public String generarTextoCuerpo() {
        return tipo.generarTextoCuerpo(this);
    }

    // Getters y setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public PersonalPolicial getInstructor() {
        return instructor;
    }

    public void setInstructor(PersonalPolicial instructor) {
        this.instructor = instructor;
    }

    public String getTextoCuerpo() {
        return textoCuerpo;
    }

    public void setTextoCuerpo(String textoCuerpo) {
        this.textoCuerpo = textoCuerpo;
    }

    public TipoCuerpoDiligencia getTipo() {
        return tipo;
    }

    public void setTipo(TipoCuerpoDiligencia tipo) {
        this.tipo = tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = TipoCuerpoDiligenciaFactory.obtenerTipoDiligencia(tipo);
    }

    public boolean esTipo(String tipo) {
        return this.tipo.equals(TipoCuerpoDiligenciaFactory.obtenerTipoDiligencia(tipo));
    }

    public Sumario sumario() {
        return SumarioDAO.instancia().buscarSumarioPorDiligencia(this);
    }
}
