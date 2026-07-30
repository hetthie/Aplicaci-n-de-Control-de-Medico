package modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa el perfil de un miembro de la familia dentro de la aplicación.
 * Es la entidad central del dominio: acumula su propio historial de
 * citas, actividad física y medicamentos asignados.
 */
public class Perfil {

    private int idPerfil;
    private String nombre;
    private Relacion relacion;
    private String email;

    // Colecciones: representan el historial de este perfil en memoria,
    // una vez que el DAO las carga desde la base de datos.
    private List<Cita> citas;
    private List<ActividadRegistro> actividades;
    private List<MedicamentoAsignado> medicamentos;

    public Perfil() {
        this.citas = new ArrayList<>();
        this.actividades = new ArrayList<>();
        this.medicamentos = new ArrayList<>();
    }

    public Perfil(int idPerfil, String nombre, Relacion relacion, String email) {
        this.idPerfil = idPerfil;
        this.nombre = nombre;
        this.relacion = relacion;
        this.email = email;
        this.citas = new ArrayList<>();
        this.actividades = new ArrayList<>();
        this.medicamentos = new ArrayList<>();
    }

    public void agregarCita(Cita cita) {
        this.citas.add(cita);
    }

    public void agregarActividad(ActividadRegistro actividadRegistro) {
        this.actividades.add(actividadRegistro);
    }

    public void agregarMedicamento(MedicamentoAsignado medicamentoAsignado) {
        this.medicamentos.add(medicamentoAsignado);
    }

    public int getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(int idPerfil) {
        this.idPerfil = idPerfil;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Relacion getRelacion() {
        return relacion;
    }

    public void setRelacion(Relacion relacion) {
        this.relacion = relacion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Cita> getCitas() {
        return citas;
    }

    public void setCitas(List<Cita> citas) {
        this.citas = citas;
    }

    public List<ActividadRegistro> getActividades() {
        return actividades;
    }

    public void setActividades(List<ActividadRegistro> actividades) {
        this.actividades = actividades;
    }

    public List<MedicamentoAsignado> getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(List<MedicamentoAsignado> medicamentos) {
        this.medicamentos = medicamentos;
    }

    @Override
    public String toString() {
        return nombre + " (" + relacion + ")";
    }
}
