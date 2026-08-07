package servicio;

import dao.MedicoDAO;
import modelo.Medico;

import java.sql.SQLException;
import java.util.List;

/**
 * Lógica de negocio relacionada a médicos. Por ahora es una capa
 * delgada sobre el DAO, porque agregar/listar médicos no requiere
 * coordinar varias tablas ni aplicar reglas adicionales.
 */
public class ServicioMedico {

    private final MedicoDAO medicoDAO;

    public ServicioMedico(MedicoDAO medicoDAO) {
        this.medicoDAO = medicoDAO;
    }

    public void agregarMedico(Medico medico) throws SQLException {
        medicoDAO.crear(medico);
    }

    public List<Medico> listarMedicos() throws SQLException {
        return medicoDAO.listarTodos();
    }

    public void eliminarMedico(int id) throws SQLException {
        medicoDAO.eliminar(id);
    }
}
