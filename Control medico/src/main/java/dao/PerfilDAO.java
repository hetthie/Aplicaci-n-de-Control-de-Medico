package dao;

import modelo.Perfil;
import java.sql.SQLException;
import java.util.List;

public interface PerfilDAO {
    void crear(Perfil perfil) throws SQLException;
    Perfil buscarPorId(int id) throws SQLException;
    List<Perfil> listarTodos() throws SQLException;
    void actualizar(Perfil perfil) throws SQLException;
    void eliminar(int id) throws SQLException;
}
