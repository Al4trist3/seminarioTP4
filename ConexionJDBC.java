import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionJDBC {
    private static final String URL = "jdbc:mariadb://localhost:3306/sumarios";
    private static final String USUARIO = "tp4User";
    private static final String CONTRASENA = "tp4pass2024";
    private static Connection conexion;

    // Método estático para obtener la conexión única
    public static synchronized Connection obtenerConexion() throws SQLException {
        if (conexion == null || conexion.isClosed()) {
            conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
        }
        return conexion;
    }

    // Método para cerrar la conexión
    public static void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
