import java.util.List;
import java.util.ArrayList;
import java.sql.*;

// Clase para interactuar con la persistencia de personal policial mediante el patrón DAO (Objeto de Acceso a Datos)
// En esta iteracion los objetos son almacenados en memoria con un Map. En la proxima iteracion se implementara 
// el uso de una base de datos SQL.


public class PersonalPolicialDAO {

    private static PersonalPolicialDAO instancia;
    private Connection conexion;

    // Constructor privado para evitar la instanciación directa
    private PersonalPolicialDAO() {
        try {
            this.conexion = ConexionJDBC.obtenerConexion();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método estático para obtener la instancia única de PersonalPolicialDAO
    public static synchronized PersonalPolicialDAO instancia() {
        if (instancia == null) {
            instancia = new PersonalPolicialDAO();
        }
        return instancia;
    }

    // Métodos CRUD utilizando la conexión JDBC obtenida de ConexionJDBC

    public PersonalPolicial obtenerPersonalPolicial(int id) {
        String sql = "SELECT * FROM PersonalPolicial WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new PersonalPolicial(rs.getInt("id"), rs.getString("nombre"), rs.getString("jerarquia"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void agregarPersonalPolicial(String nombre, String jerarquia) {
        String sql = "INSERT INTO PersonalPolicial (nombre, jerarquia) VALUES (?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setString(2, jerarquia);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarPersonalPolicial(int id, String nombre, String jerarquia) {
        String sql = "UPDATE PersonalPolicial SET nombre = ?, jerarquia = ? WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setString(2, jerarquia);
            stmt.setInt(3, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarPersonalPolicial(int id) {
        String sql = "DELETE FROM PersonalPolicial WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<PersonalPolicial> listarPersonalPolicial() {
        List<PersonalPolicial> personalPolicial = new ArrayList<>();
        String sql = "SELECT * FROM PersonalPolicial";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                PersonalPolicial p = new PersonalPolicial(rs.getInt("id"),
                        rs.getString("nombre"), rs.getString("jerarquia"));
                personalPolicial.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personalPolicial;
    }

    public List<PersonalPolicial> buscarPersonalPolicialPorNombre(String nombre) {
        List<PersonalPolicial> resultado = new ArrayList<>();
        String sql = "SELECT * FROM PersonalPolicial WHERE nombre LIKE ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, "%" + nombre + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                PersonalPolicial p = new PersonalPolicial(rs.getInt("id"),
                        rs.getString("nombre"), rs.getString("jerarquia"));
                resultado.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public List<PersonalPolicial> buscarPersonalPolicialPorJerarquia(String jerarquia) {
        List<PersonalPolicial> resultado = new ArrayList<>();
        String sql = "SELECT * FROM PersonalPolicial WHERE jerarquia LIKE ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, "%" + jerarquia + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                PersonalPolicial p = new PersonalPolicial(rs.getInt("id"),
                        rs.getString("nombre"), rs.getString("jerarquia"));
                resultado.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }

}
