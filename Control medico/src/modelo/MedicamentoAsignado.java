package modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa la asignación concreta de un {@link Medicamento} a un
 * {@link Perfil}, con su presentación, frecuencia y control de inventario.
 * También mantiene el historial de {@link Registro} (tomas/consumo).
 */
public class MedicamentoAsignado {

    private int idMedicamentoAsignado;
    private Medicamento medicamento;
    private Presentacion presentacion;
    private Frecuencia frecuencia;
    private int cantidad;
    private int cantidadCargada;
    private int cantidadMinima;
    private List<Registro> registros;

    public MedicamentoAsignado() {
        this.registros = new ArrayList<>();
    }

    public MedicamentoAsignado(int idMedicamentoAsignado, Medicamento medicamento,
                                Presentacion presentacion, Frecuencia frecuencia,
                                int cantidad, int cantidadCargada, int cantidadMinima) {
        this.idMedicamentoAsignado = idMedicamentoAsignado;
        this.medicamento = medicamento;
        this.presentacion = presentacion;
        this.frecuencia = frecuencia;
        this.cantidad = cantidad;
        this.cantidadCargada = cantidadCargada;
        this.cantidadMinima = cantidadMinima;
        this.registros = new ArrayList<>();
    }

    /**
     * Indica si la cantidad actual llegó al umbral mínimo definido,
     * para disparar el recordatorio de recarga (enunciado 1.6).
     */
    public boolean necesitaRecarga() {
        return cantidad <= cantidadMinima;
    }

    /**
     * Aumenta la cantidad disponible y actualiza el valor de la última
     * recarga (sin historial de recargas, según lo decidido en el diseño).
     */
    public void recargar(int cantidadAgregada) {
        this.cantidad += cantidadAgregada;
        this.cantidadCargada = cantidadAgregada;
    }

    /**
     * Registra una toma (consumo) de este medicamento, descontando
     * inventario y agregando el registro al historial en memoria.
     */
    public void registrarConsumo(Registro registro) {
        this.registros.add(registro);
        this.cantidad--;
    }

    public int getIdMedicamentoAsignado() {
        return idMedicamentoAsignado;
    }

    public void setIdMedicamentoAsignado(int idMedicamentoAsignado) {
        this.idMedicamentoAsignado = idMedicamentoAsignado;
    }

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
    }

    public Presentacion getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(Presentacion presentacion) {
        this.presentacion = presentacion;
    }

    public Frecuencia getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(Frecuencia frecuencia) {
        this.frecuencia = frecuencia;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getCantidadCargada() {
        return cantidadCargada;
    }

    public void setCantidadCargada(int cantidadCargada) {
        this.cantidadCargada = cantidadCargada;
    }

    public int getCantidadMinima() {
        return cantidadMinima;
    }

    public void setCantidadMinima(int cantidadMinima) {
        this.cantidadMinima = cantidadMinima;
    }

    public List<Registro> getRegistros() {
        return registros;
    }

    public void setRegistros(List<Registro> registros) {
        this.registros = registros;
    }

    @Override
    public String toString() {
        return medicamento + " - " + presentacion + " (" + cantidad + " unidades)";
    }
}
