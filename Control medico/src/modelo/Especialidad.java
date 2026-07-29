package modelo;

/**
 * Representa la especialidad médica de un {@link Medico}.
 */
public class Especialidad {

    private int idEspecialidad;
    private String tipoEspecialidad;

    public Especialidad() {
    }

    public Especialidad(int idEspecialidad, String tipoEspecialidad) {
        this.idEspecialidad = idEspecialidad;
        this.tipoEspecialidad = tipoEspecialidad;
    }

    public int getIdEspecialidad() {
        return idEspecialidad;
    }

    public void setIdEspecialidad(int idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }

    public String getTipoEspecialidad() {
        return tipoEspecialidad;
    }

    public void setTipoEspecialidad(String tipoEspecialidad) {
        this.tipoEspecialidad = tipoEspecialidad;
    }

    @Override
    public String toString() {
        return tipoEspecialidad;
    }
}
