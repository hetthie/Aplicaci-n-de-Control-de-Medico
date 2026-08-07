package servicio;

import dao.ActividadDAO;
import dao.ActividadRegistroDAO;
import modelo.Actividad;
import modelo.ActividadRegistro;

import java.sql.SQLException;
import java.util.List;

/**
 * Lógica de negocio para el registro de actividad física.
 */
public class ServicioActividad {

    private final ActividadDAO actividadDAO;
    private final ActividadRegistroDAO actividadRegistroDAO;

    public ServicioActividad(ActividadDAO actividadDAO, ActividadRegistroDAO actividadRegistroDAO) {
        this.actividadDAO = actividadDAO;
        this.actividadRegistroDAO = actividadRegistroDAO;
    }

    public List<Actividad> listarTiposDeActividad() throws SQLException {
        return actividadDAO.listarTodos();
    }

    /**
     * Registra una actividad física para un perfil, validando primero
     * que la fecha no sea futura (regla del enunciado 4.2).
     *
     * @throws IllegalArgumentException si la fecha es futura
     */
    public void registrarActividad(ActividadRegistro registro, int idPerfil, int idActividad) throws SQLException {
        if (!registro.esFechaValida()) {
            throw new IllegalArgumentException("La fecha de la actividad no puede ser futura.");
        }
        actividadRegistroDAO.crear(registro, idPerfil, idActividad);
    }

    public List<ActividadRegistro> listarActividadesDePerfil(int idPerfil) throws SQLException {
        return actividadRegistroDAO.listarPorPerfil(idPerfil);
    }
}
