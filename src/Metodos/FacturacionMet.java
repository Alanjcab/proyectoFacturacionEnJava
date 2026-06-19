package Metodos;

import Conexion.Conexion;
import Modelo.DetalleFactura;
import Modelo.Facturacion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class FacturacionMet {
    //metodo para registrar la factura
    public int registrarFactura(Facturacion factura) {
        int idFacturaGenerado = 0;
        String sql = "insert into facturas(idCliente, total, subtotal, descuentoGeneral) values (?, ?, ?, ?)";
        try {
            Connection con = Conexion.conectar();

            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, factura.getIdCliente());
            ps.setDouble(2, factura.getTotal());
            ps.setDouble(3, factura.getSubtotal());
            ps.setDouble(4, factura.getDescuentoGeneral());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                idFacturaGenerado = rs.getInt(1);
            }
            rs.close();
            ps.close();
            con.close();
        } catch (Exception e) {
            System.out.println("error al registrar factura" + e.getMessage());
        }

        return idFacturaGenerado;
    }
    //metodo para el detalle de la factura
    public boolean registrarDetalleFactura(DetalleFactura detalle) {
        String sql = "insert into detalleFactura(idFactura, idProducto, cantidad, precioUnitario, subtotal, descuentoProducto) values (?, ?, ?, ?, ?, ?)";
        try {
            Connection con = Conexion.conectar();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, detalle.getIdFactura());
            ps.setInt(2, detalle.getIdProducto());
            ps.setInt(3, detalle.getCantidad());
            ps.setDouble(4, detalle.getPrecioUnitario());
            ps.setDouble(5, detalle.getSubtotal());
            ps.setDouble(6, detalle.getDescuentoProducto());

            ps.executeUpdate();
            ps.close();
            con.close();
            return true;
        } catch (Exception e) {
            System.out.println("error al registrar detalle" + e.getMessage());
            return false;
        }
    }
    //metodo para descontar stock una vez que se haya finalizado la compra
    public boolean descontarStock(int idProducto, int cantidad) {
        String sql = "update productos set stock = stock - ? where idProducto = ?";
        try {
            Connection con = Conexion.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, cantidad);
            ps.setInt(2, idProducto);
            ps.executeUpdate();

            ps.close();
            con.close();
            return true;
        } catch (Exception e) {
            System.out.println("error al descontar stock " + e.getMessage());
            return false;
        }
    }
}