package dao;

import conexion.ConexionBD;
import modelo.Especialidad;
import modelo.Medico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicoDAOImpl implements MedicoDAO {

    private final ConexionBD conexionBD;

    public MedicoDAOImpl(ConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }

    @Override
    public void crear(Medico medico) throws SQLException {
        String sql = "INSERT INTO medico (nombre, id_especialidad_fk, telefono, email, direccion) VALUES (?, ?, ?, ?, ?)";
        try (Connection conexion = conexionBD.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, medico.getNombre());
            stmt.setInt(2, medico.getEspecialidad().ordinal() + 1);
            stmt.setString(3, medico.getTelefono());
            stmt.setString(4, medico.getEmail());
            stmt.setString(5, medico.getDireccion());
            stmt.executeUpdate();
        }
    }

    @Override
    public Medico buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM medico WHERE id_medico = ?";
        try (Connection conexion = conexionBD.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return mapear(rs);
                return null;
            }
        }
    }

    @Override
    public List<Medico> listarTodos() throws SQLException {
        String sql = "SELECT * FROM medico";
        List<Medico> medicos = new ArrayList<>();
        try (Connection conexion = conexionBD.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) medicos.add(mapear(rs));
        }
        return medicos;
    }

    @Override
    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM medico WHERE id_medico = ?";
        try (Connection conexion = conexionBD.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private Medico mapear(ResultSet rs) throws SQLException {
        Medico medico = new Medico();
        medico.setIdMedico(rs.getInt("id_medico"));
        medico.setNombre(rs.getString("nombre"));
        medico.setEspecialidad(Especialidad.values()[rs.getInt("id_especialidad_fk") - 1]);
        medico.setTelefono(rs.getString("telefono"));
        medico.setEmail(rs.getString("email"));
        medico.setDireccion(rs.getString("direccion"));
        return medico;
    }
}
