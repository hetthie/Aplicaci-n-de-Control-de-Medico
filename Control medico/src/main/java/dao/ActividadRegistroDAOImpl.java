package dao;

import conexion.ConexionBD;
import modelo.Actividad;
import modelo.ActividadRegistro;
import modelo.Horario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActividadRegistroDAOImpl implements ActividadRegistroDAO {

    private final ConexionBD conexionBD;

    public ActividadRegistroDAOImpl(ConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }

    @Override
    public void crear(ActividadRegistro registro, int idPerfil, int idActividad) throws SQLException {
        String sql = "INSERT INTO actividadregistro (id_actividad_fk, id_perfil_fk, fecha, duracion, id_horario_fk) VALUES (?, ?, ?, ?, ?)";
        try (Connection conexion = conexionBD.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idActividad);
            stmt.setInt(2, idPerfil);
            stmt.setDate(3, Date.valueOf(registro.getFecha()));
            stmt.setInt(4, registro.getDuracion());
            stmt.setInt(5, registro.getHorario().ordinal() + 1);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<ActividadRegistro> listarPorPerfil(int idPerfil) throws SQLException {
        String sql = "SELECT ar.*, a.tipo_actividad FROM actividadregistro ar " +
                     "JOIN actividad a ON ar.id_actividad_fk = a.id_actividad " +
                     "WHERE ar.id_perfil_fk = ? ORDER BY ar.fecha DESC";
        List<ActividadRegistro> lista = new ArrayList<>();
        try (Connection conexion = conexionBD.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idPerfil);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Actividad actividad = new Actividad();
                    actividad.setIdActividad(rs.getInt("id_actividad_fk"));
                    actividad.setTipoActividad(rs.getString("tipo_actividad"));

                    ActividadRegistro ar = new ActividadRegistro();
                    ar.setIdActividadRegistro(rs.getInt("id_actividad_registro"));
                    ar.setActividad(actividad);
                    ar.setFecha(rs.getDate("fecha").toLocalDate());
                    ar.setDuracion(rs.getInt("duracion"));
                    ar.setHorario(Horario.values()[rs.getInt("id_horario_fk") - 1]);
                    lista.add(ar);
                }
            }
        }
        return lista;
    }
}
