package modelo;

/**
 * Representa cuántas veces al día debe tomarse un medicamento
 * (ej: una vez al día, dos veces al día).
 */
public class FrecuenciaDiaria {

    private int idFrecuenciaDiaria;
    private String tipoFrecuenciaDiaria;

    public FrecuenciaDiaria() {
    }

    public FrecuenciaDiaria(int idFrecuenciaDiaria, String tipoFrecuenciaDiaria) {
        this.idFrecuenciaDiaria = idFrecuenciaDiaria;
        this.tipoFrecuenciaDiaria = tipoFrecuenciaDiaria;
    }

    public int getIdFrecuenciaDiaria() {
        return idFrecuenciaDiaria;
    }

    public void setIdFrecuenciaDiaria(int idFrecuenciaDiaria) {
        this.idFrecuenciaDiaria = idFrecuenciaDiaria;
    }

    public String getTipoFrecuenciaDiaria() {
        return tipoFrecuenciaDiaria;
    }

    public void setTipoFrecuenciaDiaria(String tipoFrecuenciaDiaria) {
        this.tipoFrecuenciaDiaria = tipoFrecuenciaDiaria;
    }

    @Override
    public String toString() {
        return tipoFrecuenciaDiaria;
    }
}
