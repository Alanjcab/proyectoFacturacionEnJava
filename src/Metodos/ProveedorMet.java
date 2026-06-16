package Metodos;
import Conexion.Conexion;
import Modelo.Proveedor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProveedorMet {
    //registrar proveedor
    public boolean registrarProveedor(Proveedor proveedor) {
        String sql = "insert into proveedores(nombreProveedor, cuit, emailProveedor) values (?, ?, ?)";
        try {
            Connection con = Conexion.conectar();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, proveedor.getNombreProveedor());
            ps.setString(2, proveedor.getCuit());
            ps.setString(3, proveedor.getEmailProveedor());
            ps.executeUpdate();

            ps.close();
            con.close();

            return true;
        } catch (Exception e) {
            System.out.println("error al registrar proveedor: " + e.getMessage());
            return false;
        }
    }
    //mostrar proveedores
    public ResultSet mostrarProveedoresActivos() {
        ResultSet rs = null;

        String sql = "select * from proveedores where activo = 1";

        try {
            Connection con = Conexion.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
        } catch (Exception e) {
            System.out.println("Error al listar proveedores: " + e.getMessage());
        }
        return rs;
    }
    //buscar proveedor por nuemro de cuit
    public Proveedor buscarProveedorPorCuit(String cuit) {
        Proveedor proveedor = null;
        String sql = "select * from proveedores where cuit = ?";
        try {
            Connection con = Conexion.conectar();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, cuit);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                proveedor = new Proveedor(
                        rs.getInt("idProveedor"),
                        rs.getString("nombreProveedor"),
                        rs.getString("cuit"),
                        rs.getString("emailProveedor"),
                        rs.getBoolean("activo")
                );
            }
            rs.close();
            ps.close();
            con.close();
        } catch (Exception e) {
            System.out.println("error al buscar proveedor" + e.getMessage());
        }
        return proveedor;
    }
    //actualizo datos del proveedor
    public boolean actualizarProveedor(Proveedor proveedor) {
        String sql = "update proveedores set nombreProveedor = ?, cuit = ?, emailProveedor = ? where idProveedor = ?";
        try {
            Connection con = Conexion.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, proveedor.getNombreProveedor());
            ps.setString(2, proveedor.getCuit());
            ps.setString(3, proveedor.getEmailProveedor());
            ps.setInt(4, proveedor.getIdProveedor());
            ps.executeUpdate();
            ps.close();
            con.close();
            return true;
        } catch (Exception e) {
            System.out.println("error al actualizar proveedor " + e.getMessage());
            return false;
        }
    }
    //deshabilito proveedor
    public boolean deshabilitarProveedor(int idProveedor) {
        String sql = "update proveedores set activo = 0 where idProveedor = ?";
        try {
            Connection con = Conexion.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idProveedor);
            ps.executeUpdate();
            ps.close();
            con.close();
            return true;
        } catch (Exception e) {
            System.out.println("error al deshabilitar proveedor" + e.getMessage());
            return false;
        }
    }
    //habilto proveedor
    public boolean habilitarProveedor(int idProveedor) {
        String sql = "update proveedores set activo = 1 where idProveedor = ?";
        try {
            Connection con = Conexion.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idProveedor);
            ps.executeUpdate();
            ps.close();
            con.close();
            return true;
        } catch (Exception e) {
            System.out.println("error al habilitar proveedor" + e.getMessage());
            return false;
        }
    }
}
