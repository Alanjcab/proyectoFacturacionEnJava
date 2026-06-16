package Vistas;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import Metodos.ProveedorMet;
import Metodos.ProductoMet;
import Modelo.Producto;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class vistaProducto extends JFrame {
    private JPanel panelBotonesVista;
    private JPanel panelDatos;
    private JPanel panelAcciones;
    private JPanel panelBusqueda;
    private JPanel panelTabla;
    private JButton btnVistaUsuario;
    private JButton btnVistaCliente;
    private JButton btnVistaProveedor;
    private JButton btnVistaFacturacion;
    private JTextField txtIdProducto;
    private JTextField txtCodigoProducto;
    private JTextField txtDescripcionProducto;
    private JTextField txtPrecioProducto;
    private JTextField txtStockProducto;
    private JComboBox comboProveedores;
    private JButton btnActualizarProducto;
    private JButton btnHabilitarProducto;
    private JButton btnDeshabilitarProducto;
    private JButton btnRegistrarProducto;
    private JTextField txtBuscarPorCodigo;
    private JButton btnBuscarPorCodigo;
    private JTable tablaProducto;
    private JPanel panelProducto;

    public vistaProducto() {
        setContentPane(panelProducto);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

        txtIdProducto.setEditable(false);

        btnActualizarProducto.setEnabled(false);
        btnHabilitarProducto.setEnabled(false);
        btnDeshabilitarProducto.setEnabled(false);

        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        modelo.addColumn("ID");
        modelo.addColumn("Código");
        modelo.addColumn("Descripción");
        modelo.addColumn("Precio");
        modelo.addColumn("Stock");
        modelo.addColumn("Proveedor");
        modelo.addColumn("Activo");

        tablaProducto.setModel(modelo);
        cargarComboProveedores();
        btnRegistrarProducto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codigo = txtCodigoProducto.getText();
                String descripcion = txtDescripcionProducto.getText();
                double precio = Double.parseDouble(txtPrecioProducto.getText());
                int stock = Integer.parseInt(txtStockProducto.getText());

                String proveedorSeleccionado = comboProveedores.getSelectedItem().toString();
                int idProveedor = Integer.parseInt(proveedorSeleccionado.split(" - ")[0]);

                Producto producto = new Producto(codigo, descripcion, precio, stock, idProveedor);

                ProductoMet productoMet = new ProductoMet();

                if (productoMet.registrarProducto(producto)) {
                    JOptionPane.showMessageDialog(null, "Producto registrado correctamente");
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al registrar producto");
                }
            }
        });
    }
    //cargo los proveedore en el combobox
    private void cargarComboProveedores() {
        try {
            ProveedorMet proveedorMet = new ProveedorMet();
            ResultSet rs = proveedorMet.mostrarProveedoresActivos();
            comboProveedores.removeAllItems();
            while (rs.next()) {
                comboProveedores.addItem(rs.getInt("idProveedor") + " - " + rs.getString("nombreProveedor"));
            }
        } catch (Exception e) {
            System.out.println("Error al cargar proveedores: " + e.getMessage());
        }
    }
    //metodo para limpiar los campos
    private void limpiarCampos() {
        txtIdProducto.setText("");
        txtCodigoProducto.setText("");
        txtDescripcionProducto.setText("");
        txtPrecioProducto.setText("");
        txtStockProducto.setText("");
        txtBuscarPorCodigo.setText("");

        if (comboProveedores.getItemCount() > 0) {
            comboProveedores.setSelectedIndex(0);
        }

        btnActualizarProducto.setEnabled(false);
        btnHabilitarProducto.setEnabled(false);
        btnDeshabilitarProducto.setEnabled(false);
    }
}

