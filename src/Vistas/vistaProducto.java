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
        cargarTablaProductos();
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
                    cargarTablaProductos();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al registrar producto");
                }
            }
        });
        //para qeu se carguen los datos del producto seleccionado de la tabla
        tablaProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {

                int fila = tablaProducto.getSelectedRow();

                if (fila >= 0) {
                    txtIdProducto.setText(tablaProducto.getValueAt(fila, 0).toString());
                    txtCodigoProducto.setText(tablaProducto.getValueAt(fila, 1).toString());
                    txtDescripcionProducto.setText(tablaProducto.getValueAt(fila, 2).toString());
                    txtPrecioProducto.setText(tablaProducto.getValueAt(fila, 3).toString());
                    txtStockProducto.setText(tablaProducto.getValueAt(fila, 4).toString());

                    comboProveedores.setSelectedItem(tablaProducto.getValueAt(fila, 5).toString());

                    txtBuscarPorCodigo.setText("");

                    btnActualizarProducto.setEnabled(true);
                    btnHabilitarProducto.setEnabled(false);
                    btnDeshabilitarProducto.setEnabled(true);
                }
            }
        });
        btnBuscarPorCodigo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codigo = txtBuscarPorCodigo.getText();
                ProductoMet productoMet = new ProductoMet();
                Producto producto = productoMet.buscarProductoPorCodigo(codigo);
                if (producto != null) {
                    txtIdProducto.setText(String.valueOf(producto.getIdProducto()));
                    txtCodigoProducto.setText(producto.getCodigo());
                    txtDescripcionProducto.setText(producto.getDescripcion());
                    txtPrecioProducto.setText(String.valueOf(producto.getPrecio()));
                    txtStockProducto.setText(String.valueOf(producto.getStock()));

                    btnActualizarProducto.setEnabled(true);

                    if (producto.isActivo()) {
                        btnHabilitarProducto.setEnabled(false);
                        btnDeshabilitarProducto.setEnabled(true);
                    } else {
                        btnHabilitarProducto.setEnabled(true);
                        btnDeshabilitarProducto.setEnabled(false);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "no se encontro producto con ese codigo");
                    limpiarCampos();
                }
            }
        });
        btnActualizarProducto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Producto producto = new Producto();

                producto.setIdProducto(Integer.parseInt(txtIdProducto.getText()));
                producto.setCodigo(txtCodigoProducto.getText());
                producto.setDescripcion(txtDescripcionProducto.getText());
                producto.setPrecio(Double.parseDouble(txtPrecioProducto.getText()));
                producto.setStock(Integer.parseInt(txtStockProducto.getText()));

                String proveedorSeleccionado = comboProveedores.getSelectedItem().toString();
                int idProveedor = Integer.parseInt(proveedorSeleccionado.split(" - ")[0]);

                producto.setIdProveedor(idProveedor);

                ProductoMet productoMet = new ProductoMet();

                if (productoMet.actualizarProducto(producto)) {
                    JOptionPane.showMessageDialog(null, "Producto actualizado");
                    limpiarCampos();
                    cargarTablaProductos();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al actualizar producto");
                }
            }
        });
        btnHabilitarProducto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(txtIdProducto.getText());

                ProductoMet productoMet = new ProductoMet();

                if (productoMet.habilitarProducto(id)) {
                    JOptionPane.showMessageDialog(null, "Producto habilitado");
                    limpiarCampos();
                    cargarTablaProductos();
                }
            }
        });
        btnDeshabilitarProducto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(txtIdProducto.getText());

                ProductoMet productoMet = new ProductoMet();

                if (productoMet.deshabilitarProducto(id)) {
                    JOptionPane.showMessageDialog(null, "producto deshabilitado");
                    limpiarCampos();
                    cargarTablaProductos();
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
    //cargo los productos activos en la talbla de productos
    private void cargarTablaProductos() {
        try {
            DefaultTableModel modelo = (DefaultTableModel) tablaProducto.getModel();
            modelo.setRowCount(0);
            ProductoMet productoMet = new ProductoMet();
            ResultSet rs = productoMet.mostrarProductosActivos();
            while (rs.next()) {
                modelo.addRow(new Object[]{
                        rs.getInt("idProducto"),
                        rs.getString("codigo"),
                        rs.getString("descripcion"),
                        rs.getDouble("precio"),
                        rs.getInt("stock"),
                        rs.getString("nombreProveedor"),
                        rs.getBoolean("activo") ? "SI" : "NO"
                });
            }
        } catch (Exception e) {
            System.out.println("error al cargar tabla productos: " + e.getMessage());
        }
    }
}

