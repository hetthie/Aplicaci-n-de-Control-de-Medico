package modelo;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Representa una cita médica agendada por un {@link Perfil} con un
 * {@link Medico}. Es la entidad asociativa que resuelve la relación
 * muchos a muchos entre Perfil y Medico.
 */
public class Cita {

    private int idCita;
    private Medico medico;
    private String tituloCita;
    private LocalDate fecha;
    private LocalTime hora;

    public Cita() {
    }

    public Cita(int idCita, Medico medico, String tituloCita, LocalDate fecha, LocalTime hora) {
        this.idCita = idCita;
        this.medico = medico;
        this.tituloCita = tituloCita;
        this.fecha = fecha;
        this.hora = hora;
    }

    public int getIdCita() {
        return idCita;
    }

    public void setIdCita(int idCita) {
        this.idCita = idCita;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public String getTituloCita() {
        return tituloCita;
    }

    public void setTituloCita(String tituloCita) {
        this.tituloCita = tituloCita;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    @Override
    public String toString() {
        return tituloCita + " - " + medico + " (" + fecha + " " + hora + ")";
    }
}
