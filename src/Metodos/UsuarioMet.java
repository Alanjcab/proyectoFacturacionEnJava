package Metodos;
import Conexion.Conexion;
import Modelo.Usuarios;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuarioMet {
    //metodo login
    public Usuarios login(String email, String pass) {
        Usuarios usuarios = null;
        String sql = "select * from usuarios where email = ? and pass = ? and activo = 1";
        try {
            Connection con = Conexion.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                usuarios = new Usuarios(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getInt("edad"),
                        rs.getString("dni"),
                        rs.getString("email"),
                        rs.getString("pass"),
                        rs.getString("rol"),
                        rs.getBoolean("activo")
                );
            }
            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            System.out.println("error en login: " + e.getMessage());
        }

        return usuarios;
    }

    public boolean registrarUsuario(Usuarios usuario) {

        String sql = "insert into usuarios(nombre, apellido, edad, dni, email, pass, rol) values (?, ?, ?, ?, ?, ?, ?)";

        try {
            Connection con = Conexion.conectar();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setInt(3, usuario.getEdad());
            ps.setString(4, usuario.getDni());
            ps.setString(5, usuario.getEmail());
            ps.setString(6, usuario.getPass());
            ps.setString(7, usuario.getRol());

            ps.executeUpdate();
            ps.close();
            con.close();
            return true;
        } catch (Exception e) {
            System.out.println("Error al registrar usuario " + e.getMessage());
            return false;
        }
    }

    public ResultSet cargarUsuariosActivos() {
        ResultSet rs = null;
        String sql = "select * from usuarios where activo = 1";
        try {
            Connection con = Conexion.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
        } catch (Exception e) {
            System.out.println("Error al cagar usuarios " + e.getMessage());
        }
        return rs;
    }
    //metodo para buscar usuarios por dni
    public Usuarios buscarUsuarioPorDni(String dni) {
        Usuarios usuario = null;
        String sql = "select * from usuarios where dni = ?";
        try {
            Connection con = Conexion.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, dni);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                usuario = new Usuarios(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getInt("edad"),
                        rs.getString("dni"),
                        rs.getString("email"),
                        rs.getString("pass"),
                        rs.getString("rol"),
                        rs.getBoolean("activo")
                );
            }
            rs.close();
            ps.close();
            con.close();
        } catch (Exception e) {
            System.out.println("error al buscar usuario por dni " + e.getMessage());
        }
        return usuario;
    }
    //metodo para actualizar datos del usuario
    public boolean actualizarUsuario(Usuarios usuario) {
        String sql = "update usuarios set nombre=?, apellido=?, edad=?, dni=?, email=?, rol=? where id=?";
        try {
            Connection con = Conexion.conectar();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setInt(3, usuario.getEdad());
            ps.setString(4, usuario.getDni());
            ps.setString(5, usuario.getEmail());
            ps.setString(6, usuario.getRol());
            ps.setInt(7, usuario.getId());
            ps.executeUpdate();
            ps.close();
            con.close();
            return true;
        } catch (Exception e) {
            System.out.println("error al actualizar usuario" + e.getMessage());
            return false;
        }
    }
    //metodo para deshabilitar usuario
    public boolean deshabilitarUsuario(int id) {
        String sql = "update usuarios set activo = 0 where id = ?";
        try {
            Connection con = Conexion.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
            con.close();
            return true;
        } catch (Exception e) {
            System.out.println("error al deshabilitar usuario" + e.getMessage());
            return false;
        }
    }
    //metodo para habilitar usuario
    public boolean habilitarUsuario(int id) {
        String sql = "update usuarios set activo = 1 where id = ?";
        try {
            Connection con = Conexion.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
            con.close();
            return true;
        } catch (Exception e) {
            System.out.println("error al habilitar usuario" + e.getMessage());
            return false;
        }
    }
}
