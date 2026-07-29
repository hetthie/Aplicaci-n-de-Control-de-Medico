package modelo;

/**
 * Representa un usuario con acceso a la aplicación, vinculado a un
 * {@link Perfil}. Pendiente de definir su uso concreto (login),
 * según lo conversado: se deja la estructura base preparada.
 */
public class Usuario {

    private int idUsuario;
    private String nombre;
    private Perfil perfil;
    private String email;

    public Usuario() {
    }

    public Usuario(int idUsuario, String nombre, Perfil perfil, String email) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.perfil = perfil;
        this.email = email;
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

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
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
