package dao;

import modelo.ActividadRegistro;
import java.sql.SQLException;
import java.util.List;

public interface ActividadRegistroDAO {
    void crear(ActividadRegistro registro, int idPerfil, int idActividad) throws SQLException;
    List<ActividadRegistro> listarPorPerfil(int idPerfil) throws SQLException;
}
