package dao;

import conexion.ConexionBD;
import modelo.Perfil;
import modelo.Relacion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PerfilDAOImpl implements PerfilDAO {

    private final ConexionBD conexionBD;

    public PerfilDAOImpl(ConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }

    @Override
    public void crear(Perfil perfil) throws SQLException {
        String sql = "INSERT INTO perfil (nombre, id_relacion_fk, email) VALUES (?, ?, ?)";
        try (Connection conexion = conexionBD.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, perfil.getNombre());
            stmt.setInt(2, perfil.getRelacion().ordinal() + 1);
            stmt.setString(3, perfil.getEmail());
            stmt.executeUpdate();
        }
    }

    @Override
    public Perfil buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM perfil WHERE id_perfil = ?";
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
    public List<Perfil> listarTodos() throws SQLException {
        String sql = "SELECT * FROM perfil";
        List<Perfil> perfiles = new ArrayList<>();
        try (Connection conexion = conexionBD.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) perfiles.add(mapear(rs));
        }
        return perfiles;
    }

    @Override
    public void actualizar(Perfil perfil) throws SQLException {
        String sql = "UPDATE perfil SET nombre = ?, id_relacion_fk = ?, email = ? WHERE id_perfil = ?";
        try (Connection conexion = conexionBD.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, perfil.getNombre());
            stmt.setInt(2, perfil.getRelacion().ordinal() + 1);
            stmt.setString(3, perfil.getEmail());
            stmt.setInt(4, perfil.getIdPerfil());
            stmt.executeUpdate();
        }
    }

    @Override
    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM perfil WHERE id_perfil = ?";
        try (Connection conexion = conexionBD.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private Perfil mapear(ResultSet rs) throws SQLException {
        Perfil perfil = new Perfil();
        perfil.setIdPerfil(rs.getInt("id_perfil"));
        perfil.setNombre(rs.getString("nombre"));
        perfil.setRelacion(Relacion.values()[rs.getInt("id_relacion_fk") - 1]);
        perfil.setEmail(rs.getString("email"));
        return perfil;
    }
}
