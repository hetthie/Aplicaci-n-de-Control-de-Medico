package dao;

import conexion.ConexionBD;
import modelo.Frecuencia;

import java.sql.*;

public class FrecuenciaDAOImpl implements FrecuenciaDAO {

    private final ConexionBD conexionBD;

    public FrecuenciaDAOImpl(ConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }

    @Override
    public int crear(Frecuencia frecuencia, int idFrecuenciaDiaria) throws SQLException {
        String sql = "INSERT INTO frecuencia (tipo_frecuencia, id_frecuencia_diaria_fk) VALUES (?, ?)";
        try (Connection conexion = conexionBD.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, frecuencia.getTipoFrecuencia());
            stmt.setInt(2, idFrecuenciaDiaria);
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
                throw new SQLException("No se generó id para Frecuencia");
            }
        }
    }
}
