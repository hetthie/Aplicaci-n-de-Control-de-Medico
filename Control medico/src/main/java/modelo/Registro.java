package modelo;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Representa el consumo puntual (toma) de un medicamento asignado,
 * con la fecha y hora exacta en que ocurrió.
 */
public class Registro {

    private int idRegistro;
    private LocalDate fecha;
    private LocalTime hora;

    public Registro() {
    }

    public Registro(int idRegistro, LocalDate fecha, LocalTime hora) {
        this.idRegistro = idRegistro;
        this.fecha = fecha;
        this.hora = hora;
    }

    public int getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(int idRegistro) {
        this.idRegistro = idRegistro;
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
        return fecha + " " + hora;
    }
}
