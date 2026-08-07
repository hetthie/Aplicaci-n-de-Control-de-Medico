package dao;

import modelo.Actividad;
import java.sql.SQLException;
import java.util.List;

public interface ActividadDAO {
    void crear(Actividad actividad) throws SQLException;
    List<Actividad> listarTodos() throws SQLException;
}
