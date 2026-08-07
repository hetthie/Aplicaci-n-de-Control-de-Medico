package dao;

import conexion.ConexionBD;
import modelo.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicamentoAsignadoDAOImpl implements MedicamentoAsignadoDAO {

    private final ConexionBD conexionBD;

    public MedicamentoAsignadoDAOImpl(ConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }

    /**
     * Crea el MedicamentoAsignado y también lo vincula al perfil mediante
     * la tabla intermedia perfil_medicamento.
     */
    @Override
    public int crear(MedicamentoAsignado ma, int idFrecuencia) throws SQLException {
        String sql = "INSERT INTO medicamentoasignado " +
                "(id_medicamento_fk, id_presentacion_fk, id_frecuencia_fk, cantidad, cantidad_cargada, cantidad_minima) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conexion = conexionBD.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, ma.getMedicamento().getIdMedicamento());
            stmt.setInt(2, ma.getPresentacion().ordinal() + 1);
            stmt.setInt(3, idFrecuencia);
            stmt.setInt(4, ma.getCantidad());
            stmt.setInt(5, ma.getCantidadCargada());
            stmt.setInt(6, ma.getCantidadMinima());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
                throw new SQLException("No se generó id para MedicamentoAsignado");
            }
        }
    }

    public void vincularAPerfil(int idPerfil, int idMedicamentoAsignado) throws SQLException {
        String sql = "INSERT INTO perfil_medicamento (id_perfil_fk, id_medicamento_asignado_fk) VALUES (?, ?)";
        try (Connection conexion = conexionBD.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idPerfil);
            stmt.setInt(2, idMedicamentoAsignado);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<MedicamentoAsignado> listarPorPerfil(int idPerfil) throws SQLException {
        String sql = "SELECT ma.*, med.nombre, med.cantidad_disp FROM medicamentoasignado ma " +
                "JOIN perfil_medicamento pm ON ma.id_medicamento_asignado = pm.id_medicamento_asignado_fk " +
                "JOIN medicamento med ON ma.id_medicamento_fk = med.id_medicamento " +
                "WHERE pm.id_perfil_fk = ?";
        List<MedicamentoAsignado> lista = new ArrayList<>();
        try (Connection conexion = conexionBD.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idPerfil);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Medicamento medicamento = new Medicamento();
                    medicamento.setIdMedicamento(rs.getInt("id_medicamento_fk"));
                    medicamento.setNombre(rs.getString("nombre"));
                    medicamento.setCantidadDisponible(rs.getInt("cantidad_disp"));

                    MedicamentoAsignado ma = new MedicamentoAsignado();
                    ma.setIdMedicamentoAsignado(rs.getInt("id_medicamento_asignado"));
                    ma.setMedicamento(medicamento);
                    ma.setPresentacion(Presentacion.values()[rs.getInt("id_presentacion_fk") - 1]);
                    ma.setCantidad(rs.getInt("cantidad"));
                    ma.setCantidadCargada(rs.getInt("cantidad_cargada"));
                    ma.setCantidadMinima(rs.getInt("cantidad_minima"));
                    lista.add(ma);
                }
            }
        }
        return lista;
    }

    @Override
    public void actualizarCantidad(int id, int nuevaCantidad, int cantidadCargada) throws SQLException {
        String sql = "UPDATE medicamentoasignado SET cantidad = ?, cantidad_cargada = ? WHERE id_medicamento_asignado = ?";
        try (Connection conexion = conexionBD.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, nuevaCantidad);
            stmt.setInt(2, cantidadCargada);
            stmt.setInt(3, id);
            stmt.executeUpdate();
        }
    }
}
