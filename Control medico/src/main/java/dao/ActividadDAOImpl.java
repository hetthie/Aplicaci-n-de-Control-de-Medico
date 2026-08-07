package dao;

import conexion.ConexionBD;
import modelo.Actividad;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActividadDAOImpl implements ActividadDAO {

    private final ConexionBD conexionBD;

    public ActividadDAOImpl(ConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }

    @Override
    public void crear(Actividad actividad) throws SQLException {
        String sql = "INSERT INTO actividad (tipo_actividad) VALUES (?)";
        try (Connection conexion = conexionBD.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, actividad.getTipoActividad());
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Actividad> listarTodos() throws SQLException {
        String sql = "SELECT * FROM actividad";
        List<Actividad> actividades = new ArrayList<>();
        try (Connection conexion = conexionBD.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Actividad actividad = new Actividad();
                actividad.setIdActividad(rs.getInt("id_actividad"));
                actividad.setTipoActividad(rs.getString("tipo_actividad"));
                actividades.add(actividad);
            }
        }
        return actividades;
    }
}
