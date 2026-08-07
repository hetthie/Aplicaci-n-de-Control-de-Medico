package dao;

import modelo.Cita;
import java.sql.SQLException;
import java.util.List;

public interface CitaDAO {
    void crear(Cita cita, int idPerfil) throws SQLException;
    List<Cita> listarPorPerfil(int idPerfil) throws SQLException;
}
