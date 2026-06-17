package Vistas;
import Metodos.ClienteMet;
import Modelo.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class vistaCliente extends JFrame{
    private JPanel panelCliente;
    private JPanel panelVistas;
    private JButton btnVistaRegistrar;
    private JButton btnVistaProducto;
    private JButton btnVistaFacturacion;
    private JButton btnVistaProveedor;
    private JPanel panelDatos;
    private JPanel panelAcciones;
    private JPanel panelBusqueda;
    private JPanel panelTabla;
    private JTable tablaClientes;
    private JTextField txtIdCliente;
    private JTextField txtNombreCliente;
    private JTextField txtApellidoCliente;
    private JTextField txtDniCliente;
    private JTextField txtEmailCliente;
    private JButton btnRegistrarCliente;
    private JButton btnActualizarCliente;
    private JButton btnHabilitarCliente;
    private JButton btnDeshabilitarCliente;
    private JTextField txtBuscarPorDni;
    private JButton btnBuscarPorDni;

    public vistaCliente() {
        setContentPane(panelCliente);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

        txtIdCliente.setEditable(false);

        btnActualizarCliente.setEnabled(false);
        btnHabilitarCliente.setEnabled(false);
        btnDeshabilitarCliente.setEnabled(false);

        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("DNI");
        modelo.addColumn("Email");
        modelo.addColumn("Activo");

        tablaClientes.setModel(modelo);

        cargarTablaClientes();

        btnRegistrarCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cliente cliente = new Cliente(
                        txtNombreCliente.getText(),
                        txtApellidoCliente.getText(),
                        txtDniCliente.getText(),
                        txtEmailCliente.getText()
                );

                ClienteMet clienteMet = new ClienteMet();

                if (clienteMet.registrarCliente(cliente)) {
                    JOptionPane.showMessageDialog(null, "cliente registrado");
                    limpiarCampos();
                    cargarTablaClientes();
                } else {
                    JOptionPane.showMessageDialog(null, "error al registrar cliente");
                }
            }
        });

        btnBuscarPorDni.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClienteMet clienteMet = new ClienteMet();

                Cliente cliente = clienteMet.buscarClientePorDni(txtBuscarPorDni.getText());

                if (cliente != null) {
                    cargarClienteEnCampos(cliente);
                    btnActualizarCliente.setEnabled(true);
                    if (cliente.isActivo()) {
                        btnHabilitarCliente.setEnabled(false);
                        btnDeshabilitarCliente.setEnabled(true);
                    } else {
                        btnHabilitarCliente.setEnabled(true);
                        btnDeshabilitarCliente.setEnabled(false);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "no existe un cliente con el dni ingresado");
                    limpiarCampos();
                }
            }
        });

        tablaClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int fila = tablaClientes.getSelectedRow();

                if (fila >= 0) {
                    txtIdCliente.setText(tablaClientes.getValueAt(fila, 0).toString());
                    txtNombreCliente.setText(tablaClientes.getValueAt(fila, 1).toString());
                    txtApellidoCliente.setText(tablaClientes.getValueAt(fila, 2).toString());
                    txtDniCliente.setText(tablaClientes.getValueAt(fila, 3).toString());
                    txtEmailCliente.setText(tablaClientes.getValueAt(fila, 4).toString());

                    txtBuscarPorDni.setText("");

                    btnActualizarCliente.setEnabled(true);
                    btnHabilitarCliente.setEnabled(false);
                    btnDeshabilitarCliente.setEnabled(true);
                }
            }
        });

        btnActualizarCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cliente cliente = new Cliente();

                cliente.setIdCliente(Integer.parseInt(txtIdCliente.getText()));
                cliente.setNombre(txtNombreCliente.getText());
                cliente.setApellido(txtApellidoCliente.getText());
                cliente.setDniCliente(txtDniCliente.getText());
                cliente.setEmailCliente(txtEmailCliente.getText());

                ClienteMet clienteMet = new ClienteMet();

                if (clienteMet.actualizarCliente(cliente)) {
                    JOptionPane.showMessageDialog(null, "cliente actualizado correctamente");
                    limpiarCampos();
                    cargarTablaClientes();
                } else {
                    JOptionPane.showMessageDialog(null, "error al actualizar cliente");
                }
            }
        });

        btnDeshabilitarCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(txtIdCliente.getText());
                ClienteMet clienteMet = new ClienteMet();
                if (clienteMet.deshabilitarCliente(id)) {
                    JOptionPane.showMessageDialog(null, "cliente deshabilitado");
                    limpiarCampos();
                    cargarTablaClientes();
                }
            }
        });

        btnHabilitarCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(txtIdCliente.getText());
                ClienteMet clienteMet = new ClienteMet();
                if (clienteMet.habilitarCliente(id)) {
                    JOptionPane.showMessageDialog(null, "cliente habilitado");
                    limpiarCampos();
                    cargarTablaClientes();
                }
            }
        });
    }

    private void cargarTablaClientes() {
        try {
            DefaultTableModel modelo = (DefaultTableModel) tablaClientes.getModel();
            modelo.setRowCount(0);
            ClienteMet clienteMet = new ClienteMet();
            ResultSet rs = clienteMet.mostrarClientesActivos();
            while (rs.next()) {
                modelo.addRow(new Object[]{
                        rs.getInt("idCliente"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("dniCliente"),
                        rs.getString("emailCliente"),
                        rs.getBoolean("activo") ? "SI" : "NO"
                });
            }
        } catch (Exception e) {
            System.out.println("error al cargar tabla clientes" + e.getMessage());
        }
    }
    private void cargarClienteEnCampos(Cliente cliente) {
        txtIdCliente.setText(String.valueOf(cliente.getIdCliente()));
        txtNombreCliente.setText(cliente.getNombre());
        txtApellidoCliente.setText(cliente.getApellido());
        txtDniCliente.setText(cliente.getDniCliente());
        txtEmailCliente.setText(cliente.getEmailCliente());
    }
    private void limpiarCampos() {
        txtIdCliente.setText("");
        txtNombreCliente.setText("");
        txtApellidoCliente.setText("");
        txtDniCliente.setText("");
        txtEmailCliente.setText("");
        txtBuscarPorDni.setText("");

        btnActualizarCliente.setEnabled(false);
        btnHabilitarCliente.setEnabled(false);
        btnDeshabilitarCliente.setEnabled(false);
    }
}
