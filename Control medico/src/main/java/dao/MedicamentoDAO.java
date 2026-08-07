package dao;

import modelo.Medicamento;

import java.sql.SQLException;
import java.util.List;

/**
 * Define las operaciones de acceso a datos disponibles para la entidad
 * {@link Medicamento}. La implementación concreta decide cómo se
 * ejecutan estas operaciones contra la base de datos.
 */
public interface MedicamentoDAO {

    void crear(Medicamento medicamento) throws SQLException;

    Medicamento buscarPorId(int id) throws SQLException;

    List<Medicamento> listarTodos() throws SQLException;

    void actualizar(Medicamento medicamento) throws SQLException;

    void eliminar(int id) throws SQLException;
}
