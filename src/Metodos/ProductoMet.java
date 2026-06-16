package Metodos;
import Modelo.Producto;
import Conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;


public class ProductoMet {
    //metodo para registrar productos
    public boolean registrarProducto(Producto producto) {

        String sql = "insert into productos(codigo, descripcion, precio, stock, idProveedor) values (?, ?, ?, ?, ?)";
        try {
            Connection con = Conexion.conectar();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, producto.getCodigo());
            ps.setString(2, producto.getDescripcion());
            ps.setDouble(3, producto.getPrecio());
            ps.setInt(4, producto.getStock());
            ps.setInt(5, producto.getIdProveedor());
            ps.executeUpdate();
            ps.close();
            con.close();
            return true;
        } catch (Exception e) {
            System.out.println("error al registrar producto" + e.getMessage());
            return false;
        }
    }
}