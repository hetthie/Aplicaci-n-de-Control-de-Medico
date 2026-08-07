package servicio;

import dao.*;
import modelo.*;

import java.sql.SQLException;
import java.util.List;

/**
 * Lógica de negocio para medicamentos: asignación a un perfil, registro
 * de tomas (consumo) y recarga de inventario. Coordina varios DAOs
 * porque un MedicamentoAsignado depende de que ya existan una
 * Frecuencia y una FrecuenciaDiaria creadas.
 */
public class ServicioMedicamento {

    private final MedicamentoDAO medicamentoDAO;
    private final FrecuenciaDiariaDAO frecuenciaDiariaDAO;
    private final FrecuenciaDAO frecuenciaDAO;
    private final MedicamentoAsignadoDAO medicamentoAsignadoDAO;
    private final RegistroDAO registroDAO;

    public ServicioMedicamento(MedicamentoDAO medicamentoDAO,
                                FrecuenciaDiariaDAO frecuenciaDiariaDAO,
                                FrecuenciaDAO frecuenciaDAO,
                                MedicamentoAsignadoDAO medicamentoAsignadoDAO,
                                RegistroDAO registroDAO) {
        this.medicamentoDAO = medicamentoDAO;
        this.frecuenciaDiariaDAO = frecuenciaDiariaDAO;
        this.frecuenciaDAO = frecuenciaDAO;
        this.medicamentoAsignadoDAO = medicamentoAsignadoDAO;
        this.registroDAO = registroDAO;
    }

    /**
     * Da de alta un medicamento nuevo en el catálogo general (no
     * asignado todavía a ningún perfil). Corresponde a la opción 1.2
     * del enunciado, primer paso: registrar el medicamento en sí.
     */
    public void agregarMedicamentoAlCatalogo(Medicamento medicamento) throws SQLException {
        medicamentoDAO.crear(medicamento);
    }

    public List<Medicamento> listarCatalogoMedicamentos() throws SQLException {
        return medicamentoDAO.listarTodos();
    }

    /**
     * Asigna un medicamento ya existente a un perfil, creando en orden
     * la FrecuenciaDiaria, la Frecuencia y el MedicamentoAsignado, y
     * finalmente vinculando todo al perfil. Es la operación completa
     * de la opción 1.2 del enunciado.
     */
    public void asignarMedicamentoAPerfil(int idPerfil, MedicamentoAsignado medicamentoAsignado,
                                           FrecuenciaDiaria frecuenciaDiaria,
                                           Frecuencia frecuencia) throws SQLException {

        int idFrecuenciaDiaria = frecuenciaDiariaDAO.crear(frecuenciaDiaria);
        int idFrecuencia = frecuenciaDAO.crear(frecuencia, idFrecuenciaDiaria);
        int idMedicamentoAsignado = medicamentoAsignadoDAO.crear(medicamentoAsignado, idFrecuencia);

        // vincularAPerfil no está en la interfaz porque es un detalle
        // propio de esta implementación (la tabla intermedia); por eso
        // se hace un cast puntual acá, dentro del servicio.
        ((dao.MedicamentoAsignadoDAOImpl) medicamentoAsignadoDAO)
                .vincularAPerfil(idPerfil, idMedicamentoAsignado);
    }

    public List<MedicamentoAsignado> listarMedicamentosDePerfil(int idPerfil) throws SQLException {
        return medicamentoAsignadoDAO.listarPorPerfil(idPerfil);
    }

    /**
     * Registra la toma de un medicamento (consumo) y descuenta una
     * unidad del inventario. Corresponde a la opción 1.4 del enunciado.
     */
    public void registrarToma(MedicamentoAsignado medicamentoAsignado, Registro registro) throws SQLException {
        registroDAO.crear(registro, medicamentoAsignado.getIdMedicamentoAsignado());

        int nuevaCantidad = medicamentoAsignado.getCantidad() - 1;
        medicamentoAsignadoDAO.actualizarCantidad(
                medicamentoAsignado.getIdMedicamentoAsignado(),
                nuevaCantidad,
                medicamentoAsignado.getCantidadCargada()
        );
    }

    /**
     * Recarga el inventario de un medicamento asignado. Opción 1.5.
     */
    public void recargarMedicamento(MedicamentoAsignado medicamentoAsignado, int cantidadAgregada) throws SQLException {
        int nuevaCantidad = medicamentoAsignado.getCantidad() + cantidadAgregada;
        medicamentoAsignadoDAO.actualizarCantidad(
                medicamentoAsignado.getIdMedicamentoAsignado(),
                nuevaCantidad,
                cantidadAgregada
        );
    }

    /**
     * Indica si un medicamento asignado llegó a su umbral mínimo,
     * para el recordatorio de recarga (opción 1.6).
     */
    public boolean necesitaRecordatorioDeRecarga(MedicamentoAsignado medicamentoAsignado) {
        return medicamentoAsignado.necesitaRecarga();
    }
}
