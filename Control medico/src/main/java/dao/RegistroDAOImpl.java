package dao;

import conexion.ConexionBD;
import modelo.Registro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RegistroDAOImpl implements RegistroDAO {

    private final ConexionBD conexionBD;

    public RegistroDAOImpl(ConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }

    @Override
    public void crear(Registro registro, int idMedicamentoAsignado) throws SQLException {
        String sql = "INSERT INTO registro (id_medicamento_asignado_fk, fecha, hora) VALUES (?, ?, ?)";
        try (Connection conexion = conexionBD.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idMedicamentoAsignado);
            stmt.setDate(2, Date.valueOf(registro.getFecha()));
            stmt.setTime(3, Time.valueOf(registro.getHora()));
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Registro> listarPorMedicamentoAsignado(int idMedicamentoAsignado) throws SQLException {
        String sql = "SELECT * FROM registro WHERE id_medicamento_asignado_fk = ? ORDER BY fecha DESC, hora DESC";
        List<Registro> lista = new ArrayList<>();
        try (Connection conexion = conexionBD.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idMedicamentoAsignado);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Registro registro = new Registro();
                    registro.setIdRegistro(rs.getInt("id_registro"));
                    registro.setFecha(rs.getDate("fecha").toLocalDate());
                    registro.setHora(rs.getTime("hora").toLocalTime());
                    lista.add(registro);
                }
            }
        }
        return lista;
    }
}
