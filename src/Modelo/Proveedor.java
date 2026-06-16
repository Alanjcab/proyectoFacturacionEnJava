package Modelo;

public class Proveedor {

    private int idProveedor;
    private String nombreProveedor;
    private String cuit;
    private String emailProveedor;
    private boolean activo;

    public Proveedor() {
    }

    // constructor para registrar
    public Proveedor(String nombreProveedor, String cuit, String emailProveedor) {
        this.nombreProveedor = nombreProveedor;
        this.cuit = cuit;
        this.emailProveedor = emailProveedor;
        this.activo = true;
    }

    // constructor para SELECT
    public Proveedor(int idProveedor, String nombreProveedor, String cuit, String emailProveedor, boolean activo) {
        this.idProveedor = idProveedor;
        this.nombreProveedor = nombreProveedor;
        this.cuit = cuit;
        this.emailProveedor = emailProveedor;
        this.activo = activo;
    }
    //getters y setters
    public int getIdProveedor() {
        return idProveedor;
    }
    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }
    public String getNombreProveedor() {
        return nombreProveedor;
    }
    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }
    public String getCuit() {
        return cuit;
    }
    public void setCuit(String cuit) {
        this.cuit = cuit;
    }
    public String getEmailProveedor() {
        return emailProveedor;
    }
    public void setEmailProveedor(String emailProveedor) {
        this.emailProveedor = emailProveedor;
    }
    public boolean isActivo() {
        return activo;
    }
    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}