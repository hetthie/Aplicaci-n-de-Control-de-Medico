package modelo;

/**
 * Representa un medicamento genérico registrado en el sistema,
 * con su inventario disponible.
 */
public class Medicamento {

    private int idMedicamento;
    private String nombre;
    private int cantidadDisponible;

    public Medicamento() {
    }

    public Medicamento(int idMedicamento, String nombre, int cantidadDisponible) {
        this.idMedicamento = idMedicamento;
        this.nombre = nombre;
        this.cantidadDisponible = cantidadDisponible;
    }

    public int getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(int idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(int cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
