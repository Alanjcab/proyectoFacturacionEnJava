package Metodos;
import Modelo.Producto;
import Conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
    //metodo para mostrar los productos
    public ResultSet mostrarProductosActivos() {
        ResultSet rs = null;
        String sql = "select p.idProducto, p.codigo, p.descripcion, p.precio, p.stock, pr.nombreProveedor, p.activo from productos p inner join proveedores pr on p.idProveedor = pr.idProveedor where p.activo = 1";
        try {
            Connection con = Conexion.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
        } catch (Exception e) {
            System.out.println("error al mostrar productos" + e.getMessage());
        }
        return rs;
    }
    //busco el producto por codigo
    public Producto buscarProductoPorCodigo(String codigo) {
        Producto producto = null;
        String sql = "select * from productos where codigo = ?";
        try {
            Connection con = Conexion.conectar();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, codigo);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                producto = new Producto(
                        rs.getInt("idProducto"),
                        rs.getString("codigo"),
                        rs.getString("descripcion"),
                        rs.getDouble("precio"),
                        rs.getInt("stock"),
                        rs.getInt("idProveedor"),
                        rs.getBoolean("activo")
                );
            }
            rs.close();
            ps.close();
            con.close();
        } catch (Exception e) {
            System.out.println("error al buscar producto: " + e.getMessage());
        }
        return producto;
    }
    //metodo para actualizar dats del producto
    public boolean actualizarProducto(Producto producto) {
        String sql = "update productos set codigo = ?, descripcion = ?, precio = ?, stock = ?, idProveedor = ? where idProducto = ?";
        try {
            Connection con = Conexion.conectar();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, producto.getCodigo());
            ps.setString(2, producto.getDescripcion());
            ps.setDouble(3, producto.getPrecio());
            ps.setInt(4, producto.getStock());
            ps.setInt(5, producto.getIdProveedor());
            ps.setInt(6, producto.getIdProducto());

            ps.executeUpdate();

            ps.close();
            con.close();
            return true;
        } catch (Exception e) {
            System.out.println("error al actualizar producto " + e.getMessage());
            return false;
        }
    }
    //deshabilito prodcuto
    public boolean deshabilitarProducto(int idProducto) {
        String sql = "update productos set activo = 0 where idProducto = ?";
        try {
            Connection con = Conexion.conectar();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, idProducto);
            ps.executeUpdate();

            ps.close();
            con.close();
            return true;
        } catch (Exception e) {
            System.out.println("error al deshabilitar producto" + e.getMessage());
            return false;
        }
    }
    //habilito producto
    public boolean habilitarProducto(int idProducto) {
        String sql = "update productos set activo = 1 where idProducto = ?";
        try {
            Connection con = Conexion.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idProducto);
            ps.executeUpdate();

            ps.close();
            con.close();
            return true;
        } catch (Exception e) {
            System.out.println("error al habilitar producto: " + e.getMessage());
            return false;
        }
    }
}