package Vistas;
import Metodos.UsuarioMet;
import Modelo.Usuarios;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.sql.ResultSet;

public class vistaUsuario extends JFrame{
    private JPanel panelUsuario;
    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtEdad;
    private JTextField txtDni;
    private JTextField txtEmail;
    private JTextField txtPassword;
    private JComboBox comboRoles;
    private JButton btnCliente;
    private JButton btnProducto;
    private JButton btnProveedor;
    private JButton btnFacturacion;
    private JButton btnRegistrarUsuario;
    private JButton btnActualizarUsuario;
    private JButton btnHabilitarUsuario;
    private JButton btnDeshabilitarUsuario;
    private JTable tablaUsuarios;
    private JButton btnBuscarPorDni;
    private JTextField txtBuscarPorDni;
    private JPanel panelBotonesVistas;
    private JPanel panelDatos;
    private JPanel panelBusqueda;
    private JPanel panelTabla;
    private JPanel panelAcciones;

    public vistaUsuario() {
        setContentPane(panelUsuario);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        //agrego roles en el comboBox
        comboRoles.addItem("admin");
        comboRoles.addItem("cajero");
        comboRoles.addItem("deposito");
        //hago que estos campos no sean editables
        txtId.setEditable(false);
        //deshabilito los botones cuanod no tengo usuario sellecionado
        btnHabilitarUsuario.setEnabled(false);
        btnDeshabilitarUsuario.setEnabled(false);
        //agrego campos a la tabla de usuarios
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("Edad");
        modelo.addColumn("DNI");
        modelo.addColumn("Email");
        modelo.addColumn("Rol");
        modelo.addColumn("Activo");
        tablaUsuarios.setModel(modelo);

        btnRegistrarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = txtNombre.getText();
                String apellido = txtApellido.getText();
                int edad = Integer.parseInt(txtEdad.getText());
                String dni = txtDni.getText();
                String email = txtEmail.getText();
                String pass = txtPassword.getText();
                String rol = comboRoles.getSelectedItem().toString();

                Usuarios usuario = new Usuarios(nombre, apellido, edad, dni, email, pass, rol);
                UsuarioMet usuarioMet = new UsuarioMet();

                boolean registrado = usuarioMet.registrarUsuario(usuario);

                if (registrado) {
                    JOptionPane.showMessageDialog(null, "usuario registrado");
                    limpiarCampos();
                    cargarTablaUsuarios();
                } else {
                    JOptionPane.showMessageDialog(null, "error de registro");
                }
            }
        });
        //actualizo la tabla para mostrar de nuevo los usuarios activos
        cargarTablaUsuarios();

        tablaUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int fila = tablaUsuarios.getSelectedRow();
                if (fila >= 0) {
                    txtId.setText(tablaUsuarios.getValueAt(fila, 0).toString());
                    txtNombre.setText(tablaUsuarios.getValueAt(fila, 1).toString());
                    txtApellido.setText(tablaUsuarios.getValueAt(fila, 2).toString());
                    txtEdad.setText(tablaUsuarios.getValueAt(fila, 3).toString());
                    txtDni.setText(tablaUsuarios.getValueAt(fila, 4).toString());
                    txtEmail.setText(tablaUsuarios.getValueAt(fila, 5).toString());
                    txtPassword.setText("");
                    txtBuscarPorDni.setText("");
                    comboRoles.setSelectedItem(tablaUsuarios.getValueAt(fila, 6).toString());

                    btnHabilitarUsuario.setEnabled(false);
                    btnDeshabilitarUsuario.setEnabled(true);
                }
            }
        });
        btnBuscarPorDni.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dni = txtBuscarPorDni.getText();
                UsuarioMet usuarioMet = new UsuarioMet();
                Usuarios usuario = usuarioMet.buscarUsuarioPorDni(dni);

                if (usuario != null) {
                    txtId.setText(String.valueOf(usuario.getId()));
                    txtNombre.setText(usuario.getNombre());
                    txtApellido.setText(usuario.getApellido());
                    txtEdad.setText(String.valueOf(usuario.getEdad()));
                    txtDni.setText(usuario.getDni());
                    txtEmail.setText(usuario.getEmail());
                    txtPassword.setText("");
                    comboRoles.setSelectedItem(usuario.getRol());
                    if (usuario.isActivo()) {
                        btnHabilitarUsuario.setEnabled(false);
                        btnDeshabilitarUsuario.setEnabled(true);
                    } else {
                        btnHabilitarUsuario.setEnabled(true);
                        btnDeshabilitarUsuario.setEnabled(false);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "no se encontro el dni");
                }
            }
        });
        btnActualizarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Usuarios usuario = new Usuarios();
                usuario.setId(Integer.parseInt(txtId.getText()));
                usuario.setNombre(txtNombre.getText());
                usuario.setApellido(txtApellido.getText());
                usuario.setEdad(Integer.parseInt(txtEdad.getText()));
                usuario.setDni(txtDni.getText());
                usuario.setEmail(txtEmail.getText());
                usuario.setRol(comboRoles.getSelectedItem().toString());

                UsuarioMet usuarioMet = new UsuarioMet();

                if (usuarioMet.actualizarUsuario(usuario)) {
                    JOptionPane.showMessageDialog(null, "usuario actualizado");
                    limpiarCampos();
                    cargarTablaUsuarios();
                } else {
                    JOptionPane.showMessageDialog(null, "error al actualizar");
                }
            }
        });
        btnHabilitarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(txtId.getText());
                UsuarioMet usuarioMet = new UsuarioMet();
                if (usuarioMet.habilitarUsuario(id)) {
                    JOptionPane.showMessageDialog(null, "usuario habilitado");
                    limpiarCampos();
                    cargarTablaUsuarios();
                    btnHabilitarUsuario.setEnabled(false);
                    btnDeshabilitarUsuario.setEnabled(false);
                }
            }
        });
        btnDeshabilitarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(txtId.getText());
                UsuarioMet usuarioMet = new UsuarioMet();
                if (usuarioMet.deshabilitarUsuario(id)) {
                    JOptionPane.showMessageDialog(null, "usuario deshabilitado");
                    limpiarCampos();
                    cargarTablaUsuarios();
                    btnHabilitarUsuario.setEnabled(false);
                    btnDeshabilitarUsuario.setEnabled(false);
                }
            }
        });
    }
    //metodo paraa limpiar los campos cuando termino de registrar el usuario
    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtEdad.setText("");
        txtDni.setText("");
        txtEmail.setText("");
        txtPassword.setText("");
        comboRoles.setSelectedIndex(0);
    }
    //cargo los usuarios activos
    private void cargarTablaUsuarios() {
        try {
            DefaultTableModel modelo = (DefaultTableModel) tablaUsuarios.getModel();
            modelo.setRowCount(0);
            UsuarioMet usuarioMet = new UsuarioMet();
            ResultSet rs = usuarioMet.cargarUsuariosActivos();
            while (rs.next()) {
                modelo.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getInt("edad"),
                        rs.getString("dni"),
                        rs.getString("email"),
                        rs.getString("rol"),
                        rs.getBoolean("activo") ? "SI" : "NO"
                });
            }
        } catch (Exception e) {
            System.out.println("Error al cargar tabla: " + e.getMessage());
        }
    }
}
