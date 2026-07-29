package modelo;

/**
 * Representa cada cuánto debe tomarse un medicamento
 * (ej: cada día, cada dos días, días específicos de la semana).
 */
public class Frecuencia {

    private int idFrecuencia;
    private String tipoFrecuencia;
    private FrecuenciaDiaria frecuenciaDiaria;

    public Frecuencia() {
    }

    public Frecuencia(int idFrecuencia, String tipoFrecuencia, FrecuenciaDiaria frecuenciaDiaria) {
        this.idFrecuencia = idFrecuencia;
        this.tipoFrecuencia = tipoFrecuencia;
        this.frecuenciaDiaria = frecuenciaDiaria;
    }

    public int getIdFrecuencia() {
        return idFrecuencia;
    }

    public void setIdFrecuencia(int idFrecuencia) {
        this.idFrecuencia = idFrecuencia;
    }

    public String getTipoFrecuencia() {
        return tipoFrecuencia;
    }

    public void setTipoFrecuencia(String tipoFrecuencia) {
        this.tipoFrecuencia = tipoFrecuencia;
    }

    public FrecuenciaDiaria getFrecuenciaDiaria() {
        return frecuenciaDiaria;
    }

    public void setFrecuenciaDiaria(FrecuenciaDiaria frecuenciaDiaria) {
        this.frecuenciaDiaria = frecuenciaDiaria;
    }

    @Override
    public String toString() {
        return tipoFrecuencia + " - " + frecuenciaDiaria;
    }
}
