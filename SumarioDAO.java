import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.sql.*;

// Clase para interactuar con la persistencia de sumarios mediante el patrón DAO (Objeto de Acceso a Datos)
// En esta iteracion los objetos son almacenados en memoria con un Map. En la proxima iteracion se implementara 
// el uso de una base de datos SQL.

public class SumarioDAO {

    private static SumarioDAO instancia;
    private Connection conexion;

    // Constructor privado para evitar la instanciación directa
    private SumarioDAO() {
        try {
            this.conexion = ConexionJDBC.obtenerConexion();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método estático para obtener la instancia única de SumarioDAO
    public static synchronized SumarioDAO instancia() {
        if (instancia == null) {
            instancia = new SumarioDAO();
        }
        return instancia;
    }

    // Métodos CRUD utilizando la conexión JDBC obtenida de ConexionJDBC

    public Sumario obtenerSumario(int id) {
        String sql = "SELECT * FROM Sumario WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Sumario sumario = new Sumario(rs.getInt("id"), rs.getInt("numero"), rs.getString("juzgado"),
                rs.getString("fiscalia"), rs.getString("juez"), rs.getString("fiscal"),
                rs.getString("imputados"), rs.getString("damnificados"));
                return sumario;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Vector<Diligencia> obtenerDiligenciasSumario(int idSumario) {
        Vector<Diligencia> diligencias = new Vector<>();
        String sql = "SELECT * FROM Diligencia WHERE sumario_id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idSumario);
            ResultSet rs = stmt.executeQuery();
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

    public void agregarSumario(int numero, String juzgado, String fiscalia, String juez, String fiscal, String imputados, String damnificados) {
        String sql = "INSERT INTO Sumario (numero, juzgado, fiscalia, juez, fiscal, imputados, damnificados) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, numero);
            stmt.setString(2, juzgado);
            stmt.setString(3, fiscalia);
            stmt.setString(4, juez);
            stmt.setString(5, fiscal);
            stmt.setString(6, imputados);
            stmt.setString(7, damnificados);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarSumario(int id, int numero, String juzgado, String fiscalia, String juez, String fiscal, String imputados, String damnificados) {
        String sql = "UPDATE Sumario SET numero = ?, juzgado = ?, fiscalia = ?, juez = ?, fiscal = ?, imputados = ?, damnificados = ? WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, numero);
            stmt.setString(2, juzgado);
            stmt.setString(3, fiscalia);
            stmt.setString(4, juez);
            stmt.setString(5, fiscal);
            stmt.setString(6, imputados);
            stmt.setString(7, damnificados);
            stmt.setInt(8, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarSumario(int id) {
        String sql = "DELETE FROM Sumario WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Sumario buscarSumarioPorDiligencia(Diligencia diligencia) {
        String sql = "SELECT s.* FROM Sumario s JOIN Diligencia d ON s.id = d.sumario_id WHERE d.id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, diligencia.getId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Sumario(rs.getInt("id"), rs.getInt("numero"), rs.getString("juzgado"),
                        rs.getString("fiscalia"), rs.getString("juez"), rs.getString("fiscal"),
                        rs.getString("imputados"), rs.getString("damnificados"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Sumario> listarSumarios() {
        List<Sumario> sumarios = new ArrayList<>();
        String sql = "SELECT * FROM Sumario";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Sumario sumario = new Sumario(rs.getInt("id"), rs.getInt("numero"), rs.getString("juzgado"),
                        rs.getString("fiscalia"), rs.getString("juez"), rs.getString("fiscal"),
                        rs.getString("imputados"), rs.getString("damnificados"));
                sumarios.add(sumario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sumarios;
    }

    public List<Sumario> buscarSumarioPorNumero(int numero) {
        List<Sumario> resultado = new ArrayList<>();
        String sql = "SELECT * FROM Sumario WHERE numero = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, numero);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Sumario sumario = new Sumario(rs.getInt("id"), rs.getInt("numero"), rs.getString("juzgado"),
                        rs.getString("fiscalia"), rs.getString("juez"), rs.getString("fiscal"),
                        rs.getString("imputados"), rs.getString("damnificados"));
                resultado.add(sumario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public List<Sumario> buscarSumarioPorJuzgado(String juzgado) {
        List<Sumario> resultado = new ArrayList<>();
        String sql = "SELECT * FROM Sumario WHERE juzgado LIKE ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, "%" + juzgado + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Sumario sumario = new Sumario(rs.getInt("id"), rs.getInt("numero"), rs.getString("juzgado"),
                        rs.getString("fiscalia"), rs.getString("juez"), rs.getString("fiscal"),
                        rs.getString("imputados"), rs.getString("damnificados"));
                resultado.add(sumario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public List<Sumario> buscarSumarioPorFiscalia(String fiscalia) {
        List<Sumario> resultado = new ArrayList<>();
        String sql = "SELECT * FROM Sumario WHERE fiscalia LIKE ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, "%" + fiscalia + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Sumario sumario = new Sumario(rs.getInt("id"), rs.getInt("numero"), rs.getString("juzgado"),
                        rs.getString("fiscalia"), rs.getString("juez"), rs.getString("fiscal"),
                        rs.getString("imputados"), rs.getString("damnificados"));
                resultado.add(sumario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public List<Sumario> buscarSumarioPorJuez(String juez) {
        List<Sumario> resultado = new ArrayList<>();
        String sql = "SELECT * FROM Sumario WHERE juez LIKE ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, "%" + juez + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Sumario sumario = new Sumario(rs.getInt("id"), rs.getInt("numero"), rs.getString("juzgado"),
                        rs.getString("fiscalia"), rs.getString("juez"), rs.getString("fiscal"),
                        rs.getString("imputados"), rs.getString("damnificados"));
                resultado.add(sumario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public List<Sumario> buscarSumarioPorFiscal(String fiscal) {
        List<Sumario> resultado = new ArrayList<>();
        String sql = "SELECT * FROM Sumario WHERE fiscal LIKE ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, "%" + fiscal + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Sumario sumario = new Sumario(rs.getInt("id"), rs.getInt("numero"), rs.getString("juzgado"),
                        rs.getString("fiscalia"), rs.getString("juez"), rs.getString("fiscal"),
                        rs.getString("imputados"), rs.getString("damnificados"));
                resultado.add(sumario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public List<Sumario> buscarSumarioPorImputados(String imputados) {
        List<Sumario> resultado = new ArrayList<>();
        String sql = "SELECT * FROM Sumario WHERE imputados LIKE ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, "%" + imputados + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Sumario sumario = new Sumario(rs.getInt("id"), rs.getInt("numero"), rs.getString("juzgado"),
                        rs.getString("fiscalia"), rs.getString("juez"), rs.getString("fiscal"),
                        rs.getString("imputados"), rs.getString("damnificados"));
                resultado.add(sumario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public List<Sumario> buscarSumarioPorDamnificados(String damnificados) {
        List<Sumario> resultado = new ArrayList<>();
        String sql = "SELECT * FROM Sumario WHERE damnificados LIKE ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, "%" + damnificados + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Sumario sumario = new Sumario(rs.getInt("id"), rs.getInt("numero"), rs.getString("juzgado"),
                        rs.getString("fiscalia"), rs.getString("juez"), rs.getString("fiscal"),
                        rs.getString("imputados"), rs.getString("damnificados"));
                resultado.add(sumario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }

}
