package Modelo;

public class Cliente {

    private int idCliente;
    private String nombre;
    private String apellido;
    private String dniCliente;
    private String emailCliente;
    private boolean activo;

    public Cliente() {
    }

    // constructor para registrar
    public Cliente(String nombre, String apellido, String dniCliente, String emailCliente) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dniCliente = dniCliente;
        this.emailCliente = emailCliente;
        this.activo = true;
    }

    // constructor para select
    public Cliente(int idCliente, String nombre, String apellido, String dniCliente, String emailCliente, boolean activo) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dniCliente = dniCliente;
        this.emailCliente = emailCliente;
        this.activo = activo;
    }

    public int getIdCliente() {
        return idCliente;
    }
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public String getDniCliente() {
        return dniCliente;
    }
    public void setDniCliente(String dniCliente) {
        this.dniCliente = dniCliente;
    }
    public String getEmailCliente() {
        return emailCliente;
    }
    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }
    public boolean isActivo() {
        return activo;
    }
    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}