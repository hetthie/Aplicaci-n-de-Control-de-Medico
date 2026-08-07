package dao;

import conexion.ConexionBD;
import modelo.Medicamento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de {@link MedicamentoDAO} que ejecuta el SQL
 * correspondiente contra PostgreSQL, usando {@link ConexionBD} para
 * obtener cada conexión.
 */
public class MedicamentoDAOImpl implements MedicamentoDAO {

    private final ConexionBD conexionBD;

    public MedicamentoDAOImpl(ConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }

    @Override
    public void crear(Medicamento medicamento) throws SQLException {
        String sql = "INSERT INTO medicamento (nombre, cantidad_disp) VALUES (?, ?)";

        try (Connection conexion = conexionBD.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(1, medicamento.getNombre());
            stmt.setInt(2, medicamento.getCantidadDisponible());
            stmt.executeUpdate();
        }
    }

    @Override
    public Medicamento buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM medicamento WHERE id_medicamento = ?";

        try (Connection conexion = conexionBD.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearMedicamento(rs);
                }
                return null;
            }
        }
    }

    @Override
    public List<Medicamento> listarTodos() throws SQLException {
        String sql = "SELECT * FROM medicamento";
        List<Medicamento> medicamentos = new ArrayList<>();

        try (Connection conexion = conexionBD.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                medicamentos.add(mapearMedicamento(rs));
            }
        }
        return medicamentos;
    }

    @Override
    public void actualizar(Medicamento medicamento) throws SQLException {
        String sql = "UPDATE medicamento SET nombre = ?, cantidad_disp = ? WHERE id_medicamento = ?";

        try (Connection conexion = conexionBD.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(1, medicamento.getNombre());
            stmt.setInt(2, medicamento.getCantidadDisponible());
            stmt.setInt(3, medicamento.getIdMedicamento());
            stmt.executeUpdate();
        }
    }

    @Override
    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM medicamento WHERE id_medicamento = ?";

        try (Connection conexion = conexionBD.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    /**
     * Convierte la fila actual de un ResultSet en un objeto Medicamento.
     */
    private Medicamento mapearMedicamento(ResultSet rs) throws SQLException {
        Medicamento medicamento = new Medicamento();
        medicamento.setIdMedicamento(rs.getInt("id_medicamento"));
        medicamento.setNombre(rs.getString("nombre"));
        medicamento.setCantidadDisponible(rs.getInt("cantidad_disp"));
        return medicamento;
    }
}
