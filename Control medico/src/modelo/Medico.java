package modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa un médico registrado en el sistema, con su especialidad
 * y el historial de citas que tiene asignadas.
 */
public class Medico {

    private int idMedico;
    private String nombre;
    private Especialidad especialidad;
    private String telefono;
    private String email;
    private String direccion;
    private List<Cita> citas;

    public Medico() {
        this.citas = new ArrayList<>();
    }

    public Medico(int idMedico, String nombre, Especialidad especialidad,
                  String telefono, String email, String direccion) {
        this.idMedico = idMedico;
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
        this.citas = new ArrayList<>();
    }

    /**
     * Agrega una cita al historial de este médico.
     * Mantiene la lista de citas sincronizada en memoria.
     */
    public void agregarCita(Cita cita) {
        this.citas.add(cita);
    }

    public int getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<Cita> getCitas() {
        return citas;
    }

    public void setCitas(List<Cita> citas) {
        this.citas = citas;
    }

    @Override
    public String toString() {
        return nombre + " (" + especialidad + ")";
    }
}
