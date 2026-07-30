package modelo;
import java.util.ArrayList;
import java.util.List;
/**
 * Representa un usuario con acceso a la aplicación, vinculado a un
 * {@link Perfil}. Pendiente de definir su uso concreto (login),
 * según lo conversado: se deja la estructura base preparada.
 */
public class Usuario {

    private int idUsuario;
    private String nombre;
    private List<Perfil> perfiles;
    private String email;

    public Usuario() {
	this.perfiles = new ArrayList<>();
    }

    public Usuario(int idUsuario, String nombre, String email) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.perfiles = new ArrayList<>();
        this.email = email;
    }

   public void agregarPerfil(Perfil perfil){
	this.perfiles.add(perfil);
   }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Perfil> getPerfiles() {
        return perfiles;
    }

    public void setPerfil(List<Perfil> perfiles) {
        this.perfiles = perfiles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
