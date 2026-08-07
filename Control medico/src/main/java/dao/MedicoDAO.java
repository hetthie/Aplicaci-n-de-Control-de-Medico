package dao;

import modelo.Medico;
import java.sql.SQLException;
import java.util.List;

public interface MedicoDAO {
    void crear(Medico medico) throws SQLException;
    Medico buscarPorId(int id) throws SQLException;
    List<Medico> listarTodos() throws SQLException;
    void eliminar(int id) throws SQLException;
}
