//clase para representar instancias de Personal policial
public class PersonalPolicial {
    private int id;
    private String nombre;
    private String jerarquia;

    public PersonalPolicial(int id, String nombre, String jerarquia) {
        this.id = id;
        this.nombre = nombre;
        this.jerarquia = jerarquia;
    }

    // Getters y setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getJerarquia() {
        return jerarquia;
    }

    public void setJerarquia(String jerarquia) {
        this.jerarquia = jerarquia;
    }
}