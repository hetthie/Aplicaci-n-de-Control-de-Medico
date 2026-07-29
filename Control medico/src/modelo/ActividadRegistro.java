package modelo;

import java.time.LocalDate;

/**
 * Representa el registro puntual de una actividad física realizada
 * por un {@link Perfil} en una fecha determinada.
 */
public class ActividadRegistro {

    private int idActividadRegistro;
    private Actividad actividad;
    private LocalDate fecha;
    private int duracion;
    private Horario horario;

    public ActividadRegistro() {
    }

    public ActividadRegistro(int idActividadRegistro, Actividad actividad,
                              LocalDate fecha, int duracion, Horario horario) {
        this.idActividadRegistro = idActividadRegistro;
        this.actividad = actividad;
        this.fecha = fecha;
        this.duracion = duracion;
        this.horario = horario;
    }

    /**
     * Valida que la fecha del registro no sea futura, según lo exige
     * el enunciado del proyecto (4.2: "no puede ser fecha futura").
     */
    public boolean esFechaValida() {
        return !fecha.isAfter(LocalDate.now());
    }

    public int getIdActividadRegistro() {
        return idActividadRegistro;
    }

    public void setIdActividadRegistro(int idActividadRegistro) {
        this.idActividadRegistro = idActividadRegistro;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    @Override
    public String toString() {
        return fecha + " - " + actividad + " (" + duracion + " min, " + horario + ")";
    }
}
