package servicio;

import dao.CitaDAO;
import modelo.Cita;

import java.sql.SQLException;
import java.util.List;

public class ServicioCita {

    private final CitaDAO citaDAO;

    public ServicioCita(CitaDAO citaDAO) {
        this.citaDAO = citaDAO;
    }

    public void agregarCita(Cita cita, int idPerfil) throws SQLException {
        citaDAO.crear(cita, idPerfil);
    }

    /**
     * Devuelve las citas de un perfil ordenadas de la más próxima a la
     * más antigua, según pide el enunciado (3.1).
     */
    public List<Cita> listarCitasOrdenadas(int idPerfil) throws SQLException {
        return citaDAO.listarPorPerfil(idPerfil);
    }
}
