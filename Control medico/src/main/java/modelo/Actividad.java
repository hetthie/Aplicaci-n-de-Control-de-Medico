package modelo;

/**
 * Representa un tipo de actividad física disponible en el sistema
 * (ej: caminar, trotar, nadar).
 */
public class Actividad {

    private int idActividad;
    private String tipoActividad;

    public Actividad() {
    }

    public Actividad(int idActividad, String tipoActividad) {
        this.idActividad = idActividad;
        this.tipoActividad = tipoActividad;
    }

    public int getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
    }

    public String getTipoActividad() {
        return tipoActividad;
    }

    public void setTipoActividad(String tipoActividad) {
        this.tipoActividad = tipoActividad;
    }

    @Override
    public String toString() {
        return tipoActividad;
    }
}
