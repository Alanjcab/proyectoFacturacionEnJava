package Modelo;

public class Facturacion {
    private int idFactura;
    private int idCliente;
    private double total;
    private double subtotal;
    private double descuentoGeneral;
    private boolean activo;

    public Facturacion() {
    }
    public Facturacion(int idCliente, double total, double subtotal, double descuentoGeneral) {
        this.idCliente = idCliente;
        this.total = total;
        this.subtotal = subtotal;
        this.descuentoGeneral = descuentoGeneral;
        this.activo = true;
    }
    public int getIdFactura() {
        return idFactura;
    }
    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }
    public int getIdCliente() {
        return idCliente;
    }
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
    public double getTotal() {
        return total;
    }
    public void setTotal(double total) {
        this.total = total;
    }
    public double getSubtotal() {
        return subtotal;
    }
    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
    public double getDescuentoGeneral() {
        return descuentoGeneral;
    }
    public void setDescuentoGeneral(double descuentoGeneral) {
        this.descuentoGeneral = descuentoGeneral;
    }
    public boolean isActivo() {
        return activo;
    }
    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
