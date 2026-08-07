package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Encargada exclusivamente de abrir una conexión JDBC hacia la base de
 * datos PostgreSQL del proyecto. No sabe nada de SQL de negocio ni de
 * DAOs: su única responsabilidad es entregar una {@link Connection}
 * lista para usar.
 */
public class ConexionBD {

    // Datos de conexión a la base de datos local ya creada con psql.
    private static final String URL = "jdbc:postgresql://localhost:5432/control_medicamentos";
    private static final String USUARIO = "medicamento";
    private static final String PASSWORD = "medicamento";

    /**
     * Abre y devuelve una nueva conexión activa hacia la base de datos.
     *
     * No atrapa la excepción con try/catch a propósito: quien llama a
     * este método (por ejemplo, un DAO) es quien debe decidir qué hacer
     * si la conexión falla (reintentar, informar al usuario, cancelar
     * la operación, etc.). Esta clase solo sabe conectar.
     *
     * @return una Connection activa hacia control_medicamentos
     * @throws SQLException si la conexión no pudo establecerse
     *         (BD apagada, credenciales incorrectas, etc.)
     */
    public Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, PASSWORD);
    }
}