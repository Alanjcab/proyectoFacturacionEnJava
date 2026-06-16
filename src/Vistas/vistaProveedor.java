package Vistas;
import Metodos.ProveedorMet;
import Modelo.Proveedor;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class vistaProveedor extends JFrame{
    private JPanel panelProveedor;
    private JPanel panelBotones;
    private JPanel panelDatos;
    private JPanel panelAcciones;
    private JPanel panelBusqueda;
    private JPanel panelTabla;
    private JTable tablaProveedores;
    private JButton btnVistaUsuario;
    private JButton btnVistaCliente;
    private JButton btnVistaProducto;
    private JButton btnVistaFacturacion;
    private JTextField txtIdProveedor;
    private JTextField txtNombreProveedor;
    private JTextField txtCuitProveedor;
    private JTextField txtEmailProveedor;
    private JTextField txtBuscarPorCuit;
    private JButton btnBuscarPorCuit;
    private JButton btnRegistrarProveedor;
    private JButton btnActualizarProveedor;
    private JButton btnHabilitarProveedor;
    private JButton btnDeshabilitarProveedor;

    public vistaProveedor() {
        setContentPane(panelProveedor);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

        txtIdProveedor.setEditable(false);

        btnHabilitarProveedor.setEnabled(false);
        btnDeshabilitarProveedor.setEnabled(false);
        btnActualizarProveedor.setEnabled(false);

        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Cuit");
        modelo.addColumn("Email");
        modelo.addColumn("Activo");
        tablaProveedores.setModel(modelo);

        cargarTablaProveedores();
        btnRegistrarProveedor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = txtNombreProveedor.getText();
                String cuit = txtCuitProveedor.getText();
                String email = txtEmailProveedor.getText();

                Proveedor proveedor = new Proveedor(nombre, cuit, email);

                ProveedorMet proveedorMet = new ProveedorMet();

                boolean registrado = proveedorMet.registrarProveedor(proveedor);

                if (registrado) {
                    JOptionPane.showMessageDialog(null, "oroveedor registrado");
                    limpiarCampos();
                    cargarTablaProveedores();
                } else {
                    JOptionPane.showMessageDialog(null, "error al registrar proveedor");
                }
            }
        });
        btnBuscarPorCuit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cuit = txtBuscarPorCuit.getText();
                ProveedorMet proveedorMet = new ProveedorMet();
                Proveedor proveedor = proveedorMet.buscarProveedorPorCuit(cuit);
                if (proveedor != null) {
                    txtIdProveedor.setText(String.valueOf(proveedor.getIdProveedor()));
                    txtNombreProveedor.setText(proveedor.getNombreProveedor());
                    txtCuitProveedor.setText(proveedor.getCuit());
                    txtEmailProveedor.setText(proveedor.getEmailProveedor());
                    btnActualizarProveedor.setEnabled(true);
                    if (proveedor.isActivo()) {
                        btnHabilitarProveedor.setEnabled(false);
                        btnDeshabilitarProveedor.setEnabled(true);
                    } else {
                        btnHabilitarProveedor.setEnabled(true);
                        btnDeshabilitarProveedor.setEnabled(false);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "no se encontro proveedor");
                }
            }
        });
        btnActualizarProveedor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Proveedor proveedor = new Proveedor();
                proveedor.setIdProveedor(Integer.parseInt(txtIdProveedor.getText()));
                proveedor.setNombreProveedor(txtNombreProveedor.getText());
                proveedor.setCuit(txtCuitProveedor.getText());
                proveedor.setEmailProveedor(txtEmailProveedor.getText());

                ProveedorMet proveedorMet = new ProveedorMet();

                if (proveedorMet.actualizarProveedor(proveedor)) {
                    JOptionPane.showMessageDialog(null, "proveedor actualizado");
                    limpiarCampos();
                    cargarTablaProveedores();
                } else {
                    JOptionPane.showMessageDialog(null, "error al actualizar proveedor");
                }
            }
        });
        btnHabilitarProveedor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(txtIdProveedor.getText());
                ProveedorMet proveedorMet = new ProveedorMet();
                if (proveedorMet.habilitarProveedor(id)) {
                    JOptionPane.showMessageDialog(null, "proveedor habilitado ");
                    limpiarCampos();
                    cargarTablaProveedores();
                    btnHabilitarProveedor.setEnabled(false);
                    btnDeshabilitarProveedor.setEnabled(false);
                }
            }
        });
        btnDeshabilitarProveedor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(txtIdProveedor.getText());
                ProveedorMet proveedorMet = new ProveedorMet();
                if (proveedorMet.deshabilitarProveedor(id)) {
                    JOptionPane.showMessageDialog(null, "oroveedor deshabilitado");
                    limpiarCampos();
                    cargarTablaProveedores();
                    btnHabilitarProveedor.setEnabled(false);
                    btnDeshabilitarProveedor.setEnabled(false);
                }
            }
        });
    }
    //cargo los proveedores
    private void cargarTablaProveedores() {
        try {
            DefaultTableModel modelo = (DefaultTableModel) tablaProveedores.getModel();
            modelo.setRowCount(0);
            ProveedorMet proveedorMet = new ProveedorMet();
            ResultSet rs = proveedorMet.mostrarProveedoresActivos();
            while (rs.next()) {
                modelo.addRow(new Object[]{
                        rs.getInt("idProveedor"),
                        rs.getString("nombreProveedor"),
                        rs.getString("cuit"),
                        rs.getString("emailProveedor"),
                        rs.getBoolean("activo") ? "SI" : "NO"
                });
            }
        } catch (Exception e) {
            System.out.println("Error al cargar tabla proveedores: " + e.getMessage());
        }
    }
    private void limpiarCampos() {
        txtIdProveedor.setText("");
        txtNombreProveedor.setText("");
        txtCuitProveedor.setText("");
        txtEmailProveedor.setText("");
        //uso setEnabled paara anular botones
        btnActualizarProveedor.setEnabled(false);
        btnHabilitarProveedor.setEnabled(false);
        btnDeshabilitarProveedor.setEnabled(false);
    }
}
