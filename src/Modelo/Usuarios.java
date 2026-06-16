package Modelo;

public class Usuarios {
    private int id;
    private String nombre;
    private String apellido;
    private int edad;
    private String dni;
    private String email;
    private String pass;
    private String rol;
    private boolean activo;

    public Usuarios() {
    }

    //constructor para reggistrar usuario
    public Usuarios(String nombre, String apellido, int edad, String dni, String email, String pass, String rol) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.dni = dni;
        this.email = email;
        this.pass = pass;
        this.rol = rol;
        this.activo = true;
    }

    //constructor para el select
    public Usuarios(int id, String nombre, String apellido, int edad, String dni, String email, String pass, String rol, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.dni = dni;
        this.email = email;
        this.pass = pass;
        this.rol = rol;
        this.activo = activo;
    }

    public int getId(){
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNombre(){
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellido(){
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public int getEdad(){
        return edad;
    }
    public void setEdad(int edad) {
        this.edad = edad;
    }
    public String getDni(){
        return dni;
    }
    public void setDni(String dni) {
        this.dni = dni;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPass(){
        return pass;
    }
    public void setPass(String pass) {
        this.pass = pass;
    }
    public String getRol(){
        return rol;
    }
    public void setRol(String rol) {
        this.rol = rol;
    }
    public boolean isActivo(){
        return activo;
    }
    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
