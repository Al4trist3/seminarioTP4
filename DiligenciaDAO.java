import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

// Clase para interactuar con la persistencia de diligencias mediante el patrón DAO (Objeto de Acceso a Datos)
// En esta iteracion los objetos son almacenados en memoria con un Map. En la proxima iteracion se implementara 
// el uso de una base de datos SQL.


public class DiligenciaDAO {

    private static DiligenciaDAO instancia;
    private Connection conexion;
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");

    // Constructor privado para evitar la instanciación directa
    private DiligenciaDAO() {
        try {
            this.conexion = ConexionJDBC.obtenerConexion();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método estático para obtener la instancia única de DiligenciaDAO
    public static synchronized DiligenciaDAO instancia() {
        if (instancia == null) {
            instancia = new DiligenciaDAO();
        }
        return instancia;
    }

    // Métodos CRUD utilizando la conexión JDBC obtenida de ConexionJDBC

    public Diligencia obtenerDiligencia(int id) {
        String sql = "SELECT * FROM Diligencia WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                PersonalPolicial instructor = PersonalPolicialDAO.instancia().obtenerPersonalPolicial(rs.getInt("instructor_id"));
                return new Diligencia(rs.getInt("id"), rs.getDate("fecha"), instructor, rs.getString("texto_cuerpo"), rs.getString("tipo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void agregarDiligencia(Date fecha, PersonalPolicial instructor, String textoCuerpo, String tipo, Sumario sumario) {
        String sql = "INSERT INTO Diligencia (fecha, texto_cuerpo, instructor_id, sumario_id, tipo) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setDate(1, new java.sql.Date(fecha.getTime()));
            stmt.setString(2, textoCuerpo);
            stmt.setInt(3, instructor.getId());
            stmt.setInt(4, sumario.getId());
            stmt.setString(5, tipo);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarDiligencia(int id, Date fecha, PersonalPolicial instructor, String textoCuerpo, String tipo, Sumario sumario) {
        String sql = "UPDATE Diligencia SET fecha = ?, texto_cuerpo = ?, instructor_id = ?, sumario_id = ?, tipo = ? WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setDate(1, new java.sql.Date(fecha.getTime()));
            stmt.setString(2, textoCuerpo);
            stmt.setInt(3, instructor.getId());
            stmt.setInt(4, sumario.getId());
            stmt.setString(5, tipo);
            stmt.setInt(6, id);
            stmt.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarDiligencia(int id) {
        String sql = "DELETE FROM Diligencia WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Diligencia> listarDiligencias() {
        List<Diligencia> diligencias = new ArrayList<>();
        String sql = "SELECT * FROM Diligencia";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                PersonalPolicial instructor = PersonalPolicialDAO.instancia().obtenerPersonalPolicial(rs.getInt("instructor_id"));
                Diligencia diligencia = new Diligencia(rs.getInt("id"), rs.getDate("fecha"), instructor, rs.getString("texto_cuerpo"), rs.getString("tipo"));
                diligencias.add(diligencia);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return diligencias;
    }

    public List<Diligencia> buscarDiligenciaPorFecha(Date fecha) {
        List<Diligencia> resultado = new ArrayList<>();
        String sql = "SELECT * FROM Diligencia WHERE fecha = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setDate(1, new java.sql.Date(fecha.getTime()));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                PersonalPolicial instructor = PersonalPolicialDAO.instancia().obtenerPersonalPolicial(rs.getInt("instructor_id"));
                Diligencia diligencia = new Diligencia(rs.getInt("id"), rs.getDate("fecha"), instructor, rs.getString("texto_cuerpo"), rs.getString("tipo"));
                resultado.add(diligencia);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public List<Diligencia> buscarDiligenciaPorInstructor(PersonalPolicial instructor) {
        List<Diligencia> resultado = new ArrayList<>();
        String sql = "SELECT * FROM Diligencia WHERE instructor_id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, instructor.getId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Diligencia diligencia = new Diligencia(rs.getInt("id"), rs.getDate("fecha"), instructor, rs.getString("texto_cuerpo"), rs.getString("tipo"));
                resultado.add(diligencia);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public List<Diligencia> buscarDiligenciaPorTipo(String tipo) {
        List<Diligencia> resultado = new ArrayList<>();
        String sql = "SELECT * FROM Diligencia WHERE tipo = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, tipo);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                PersonalPolicial instructor = PersonalPolicialDAO.instancia().obtenerPersonalPolicial(rs.getInt("instructor_id"));
                Diligencia diligencia = new Diligencia(rs.getInt("id"), rs.getDate("fecha"), instructor, rs.getString("texto_cuerpo"), tipo);
                resultado.add(diligencia);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }

}
