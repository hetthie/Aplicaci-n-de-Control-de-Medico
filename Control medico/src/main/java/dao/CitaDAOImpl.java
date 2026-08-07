package dao;

import conexion.ConexionBD;
import modelo.Cita;
import modelo.Especialidad;
import modelo.Medico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CitaDAOImpl implements CitaDAO {

    private final ConexionBD conexionBD;

    public CitaDAOImpl(ConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }

    @Override
    public void crear(Cita cita, int idPerfil) throws SQLException {
        String sql = "INSERT INTO cita (id_medico_fk, id_perfil_fk, titulo_cita, fecha, hora) VALUES (?, ?, ?, ?, ?)";
        try (Connection conexion = conexionBD.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, cita.getMedico().getIdMedico());
            stmt.setInt(2, idPerfil);
            stmt.setString(3, cita.getTituloCita());
            stmt.setDate(4, Date.valueOf(cita.getFecha()));
            stmt.setTime(5, Time.valueOf(cita.getHora()));
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Cita> listarPorPerfil(int idPerfil) throws SQLException {
        String sql = "SELECT c.*, m.nombre AS nombre_medico, m.id_especialidad_fk, " +
                     "m.telefono, m.email, m.direccion FROM cita c " +
                     "JOIN medico m ON c.id_medico_fk = m.id_medico " +
                     "WHERE c.id_perfil_fk = ? ORDER BY c.fecha, c.hora";
        List<Cita> citas = new ArrayList<>();
        try (Connection conexion = conexionBD.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idPerfil);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Medico medico = new Medico();
                    medico.setIdMedico(rs.getInt("id_medico_fk"));
                    medico.setNombre(rs.getString("nombre_medico"));
                    medico.setEspecialidad(Especialidad.values()[rs.getInt("id_especialidad_fk") - 1]);
                    medico.setTelefono(rs.getString("telefono"));
                    medico.setEmail(rs.getString("email"));
                    medico.setDireccion(rs.getString("direccion"));

                    Cita cita = new Cita();
                    cita.setIdCita(rs.getInt("id_cita"));
                    cita.setMedico(medico);
                    cita.setTituloCita(rs.getString("titulo_cita"));
                    cita.setFecha(rs.getDate("fecha").toLocalDate());
                    cita.setHora(rs.getTime("hora").toLocalTime());
                    citas.add(cita);
                }
            }
        }
        return citas;
    }
}
