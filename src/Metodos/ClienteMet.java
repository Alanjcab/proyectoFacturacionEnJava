package Metodos;
import Conexion.Conexion;
import Modelo.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ClienteMet {
    //insert para resgitrar un nuevo cliente
    public boolean registrarCliente(Cliente cliente) {
        String sql = "insert into clientes(nombre, apellido, dniCliente, emailCliente) values (?, ?, ?, ?)";
        try {
            Connection con = Conexion.conectar();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setString(3, cliente.getDniCliente());
            ps.setString(4, cliente.getEmailCliente());

            ps.executeUpdate();

            ps.close();
            con.close();
            return true;
        } catch (Exception e) {
            System.out.println("error al registrar cliente" + e.getMessage());
            return false;
        }
    }
    //muestro los cleinte con estado activo
    public ResultSet mostrarClientesActivos() {
        ResultSet rs = null;
        String sql = "select * from clientes where activo = 1";
        try {
            Connection con = Conexion.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
        } catch (Exception e) {
            System.out.println("error al mostrar clientes" + e.getMessage());
        }
        return rs;
    }
    //busco cleinte por numeor de dni
    public Cliente buscarClientePorDni(String dni) {
        Cliente cliente = null;
        String sql = "select * from clientes where dniCliente = ?";
        try {
            Connection con = Conexion.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, dni);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                cliente = new Cliente(
                        rs.getInt("idCliente"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("dniCliente"),
                        rs.getString("emailCliente"),
                        rs.getBoolean("activo")
                );
            }
            rs.close();
            ps.close();
            con.close();
        } catch (Exception e) {
            System.out.println("error al buscar cliente" + e.getMessage());
        }
        return cliente;
    }
    //actualizo daots del cliente
    public boolean actualizarCliente(Cliente cliente) {
        String sql = "update clientes set nombre = ?, apellido = ?, dniCliente = ?, emailCliente = ? where idCliente = ?";
        try {
            Connection con = Conexion.conectar();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setString(3, cliente.getDniCliente());
            ps.setString(4, cliente.getEmailCliente());
            ps.setInt(5, cliente.getIdCliente());

            ps.executeUpdate();

            ps.close();
            con.close();
            return true;
        } catch (Exception e) {
            System.out.println("error al actualizar cliente" + e.getMessage());
            return false;
        }
    }
    //deshabilito cliente
    public boolean deshabilitarCliente(int idCliente) {
        String sql = "update clientes set activo = 0 where idCliente = ?";
        try {
            Connection con = Conexion.conectar();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, idCliente);
            ps.executeUpdate();

            ps.close();
            con.close();
            return true;
        } catch (Exception e) {
            System.out.println("error al deshabilitar cliente" + e.getMessage());
            return false;
        }
    }
    //habilito cleinte
    public boolean habilitarCliente(int idCliente) {
        String sql = "update clientes set activo = 1 where idCliente = ?";
        try {
            Connection con = Conexion.conectar();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, idCliente);
            ps.executeUpdate();

            ps.close();
            con.close();
            return true;
        } catch (Exception e) {
            System.out.println("error al habilitar cliente" + e.getMessage());
            return false;
        }
    }

}
