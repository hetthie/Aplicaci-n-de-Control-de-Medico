package dao;

import conexion.ConexionBD;
import modelo.FrecuenciaDiaria;

import java.sql.*;

public class FrecuenciaDiariaDAOImpl implements FrecuenciaDiariaDAO {

    private final ConexionBD conexionBD;

    public FrecuenciaDiariaDAOImpl(ConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }

    /**
     * Inserta la frecuencia diaria y devuelve el id generado, para
     * poder usarlo como FK al crear la Frecuencia asociada.
     */
    @Override
    public int crear(FrecuenciaDiaria frecuenciaDiaria) throws SQLException {
        String sql = "INSERT INTO frecuenciadiaria (tipo_frecuencia_diaria) VALUES (?)";
        try (Connection conexion = conexionBD.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, frecuenciaDiaria.getTipoFrecuenciaDiaria());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
                throw new SQLException("No se generó id para FrecuenciaDiaria");
            }
        }
    }
}
