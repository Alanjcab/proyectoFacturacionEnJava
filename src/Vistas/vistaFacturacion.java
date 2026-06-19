package Vistas;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;

import Modelo.*;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import Metodos.FacturacionMet;
import Metodos.ClienteMet;
import Metodos.ProductoMet;

public class vistaFacturacion extends JFrame{
    private JPanel panelFacturacion;
    private JPanel panelVistas;
    private JButton btnVistaRegistrar;
    private JButton btnVistaCliente;
    private JButton btnVistaProveedor;
    private JButton btnVistaProducto;
    private JPanel panelDatosCliente;
    private JPanel panelDatosProducto;
    private JPanel panelAcciones;
    private JButton btnAgregar;
    private JButton btnEliminarProducto;
    private JTextField txtDniCliente;
    private JButton btnBuscarCliente;
    private JTextField txtCodigoProducto;
    private JButton btnBuscarProducto;
    private JTextField txtDescuentoProducto;
    private JTextField txtCantidad;
    private JPanel panelTabla;
    private JTable tablaDetalleCompra;
    private JPanel panelTotal;
    private JButton btnConfirmar;
    private JTextField txtDescuentoTotal;
    private JLabel lbNombreCliente;
    private JLabel lbApellidoCliente;
    private JLabel lbDniCliente;
    private JLabel lbDescripcionProducto;
    private JLabel lbPrecioProducto;
    private JLabel lbStockProducto;
    private JLabel lbSubtotal;
    private JLabel lbTotal;

    private int idClienteSeleccionado = 0;
    private int idProductoSeleccionado = 0;
    private String codigoProductoSeleccionado = "";
    private String descripcionProductoSeleccionado = "";
    private double precioProductoSeleccionado = 0;
    private int stockProductoSeleccionado = 0;

    public vistaFacturacion() {

        setContentPane(panelFacturacion);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        modelo.addColumn("ID Producto");
        modelo.addColumn("Código");
        modelo.addColumn("Descripción");
        modelo.addColumn("Precio");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Descuento");
        modelo.addColumn("Subtotal");

        tablaDetalleCompra.setModel(modelo);
        aplicarPermisosPorRol();
        btnBuscarCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dni = txtDniCliente.getText();
                ClienteMet clienteMet = new ClienteMet();
                Cliente cliente = clienteMet.buscarClientePorDni(dni);
                if (cliente != null && cliente.isActivo()) {
                    idClienteSeleccionado = cliente.getIdCliente();

                    lbNombreCliente.setText(cliente.getNombre());
                    lbApellidoCliente.setText(cliente.getApellido());
                    lbDniCliente.setText(cliente.getDniCliente());

                } else {
                    JOptionPane.showMessageDialog(null, "cliente no encontrado");
                    idClienteSeleccionado = 0;
                    lbNombreCliente.setText("");
                    lbApellidoCliente.setText("");
                    lbDniCliente.setText("");
                }
            }
        });
        btnBuscarProducto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codigo = txtCodigoProducto.getText();
                ProductoMet productoMet = new ProductoMet();
                Producto producto = productoMet.buscarProductoPorCodigo(codigo);

                if (producto != null && producto.isActivo()) {
                    idProductoSeleccionado = producto.getIdProducto();
                    codigoProductoSeleccionado = producto.getCodigo();
                    descripcionProductoSeleccionado = producto.getDescripcion();
                    precioProductoSeleccionado = producto.getPrecio();
                    stockProductoSeleccionado = producto.getStock();

                    lbDescripcionProducto.setText(producto.getDescripcion());
                    lbPrecioProducto.setText(String.valueOf(producto.getPrecio()));
                    lbStockProducto.setText(String.valueOf(producto.getStock()));
                } else {
                    JOptionPane.showMessageDialog(null, "producto no encontrado");
                    idProductoSeleccionado = 0;
                    codigoProductoSeleccionado = "";
                    descripcionProductoSeleccionado = "";
                    precioProductoSeleccionado = 0;
                    stockProductoSeleccionado = 0;

                    lbDescripcionProducto.setText("");
                    lbPrecioProducto.setText("");
                    lbStockProducto.setText("");
                }
            }
        });
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (idClienteSeleccionado == 0) {
                    JOptionPane.showMessageDialog(null, "Primero buscá un cliente");
                    return;
                }
                if (idProductoSeleccionado == 0) {
                    JOptionPane.showMessageDialog(null, "Primero buscá un producto");
                    return;
                }
                if (txtCantidad.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Ingresá una cantidad");
                    return;
                }
                int cantidad;
                try {
                    cantidad = Integer.parseInt(txtCantidad.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "La cantidad debe ser un número");
                    return;
                }
                double descuentoProducto = 0;
                if (!txtDescuentoProducto.getText().trim().isEmpty()) {
                    descuentoProducto = Double.parseDouble(txtDescuentoProducto.getText());
                }
                if (cantidad <= 0) {
                    JOptionPane.showMessageDialog(null, "La cantidad debe ser mayor a 0");
                    return;
                }
                if (cantidad > stockProductoSeleccionado) {
                    JOptionPane.showMessageDialog(null, "No hay stock suficiente");
                    return;
                }

                DefaultTableModel modelo = (DefaultTableModel) tablaDetalleCompra.getModel();

                for (int i = 0; i < tablaDetalleCompra.getRowCount(); i++) {
                    int idProductoTabla = Integer.parseInt(tablaDetalleCompra.getValueAt(i, 0).toString());
                    if (idProductoTabla == idProductoSeleccionado) {
                        int cantidadActual = Integer.parseInt(tablaDetalleCompra.getValueAt(i, 4).toString());
                        int nuevaCantidad = cantidadActual + cantidad;

                        if (nuevaCantidad > stockProductoSeleccionado) {
                            JOptionPane.showMessageDialog(null, "no hay stock suficiente");
                            return;
                        }
                        double subtotalSinDescuento = precioProductoSeleccionado * nuevaCantidad;
                        double montoDescuento = subtotalSinDescuento * descuentoProducto / 100;
                        double subtotalFinal = subtotalSinDescuento - montoDescuento;

                        tablaDetalleCompra.setValueAt(nuevaCantidad, i, 4);
                        tablaDetalleCompra.setValueAt(descuentoProducto, i, 5);
                        tablaDetalleCompra.setValueAt(subtotalFinal, i, 6);
                        recalcularTotales();
                        limpiarProductoSeleccionado();
                        return;
                    }
                }
                double subtotalSinDescuento = precioProductoSeleccionado * cantidad;
                double montoDescuento = subtotalSinDescuento * descuentoProducto / 100;
                double subtotalFinal = subtotalSinDescuento - montoDescuento;
                modelo.addRow(new Object[]{idProductoSeleccionado, codigoProductoSeleccionado, descripcionProductoSeleccionado, precioProductoSeleccionado, cantidad, descuentoProducto, subtotalFinal});
                recalcularTotales();
                limpiarProductoSeleccionado();
            }
        });

        txtDescuentoTotal.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                recalcularTotales();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                recalcularTotales();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                recalcularTotales();
            }
        });
        btnConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (idClienteSeleccionado == 0) {
                    JOptionPane.showMessageDialog(null, "tiene que seleccionar un cliente");
                    return;
                }
                if (tablaDetalleCompra.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Debe agregar productos");
                    return;
                }

                double subtotal = Double.parseDouble(lbSubtotal.getText());
                double total = Double.parseDouble(lbTotal.getText());
                double descuentoGeneral = 0;

                if (!txtDescuentoTotal.getText().trim().isEmpty()) {
                    descuentoGeneral = Double.parseDouble(txtDescuentoTotal.getText());
                }

                Facturacion factura = new Facturacion(idClienteSeleccionado, total, subtotal, descuentoGeneral);
                FacturacionMet facturacionMet = new FacturacionMet();
                int idFactura = facturacionMet.registrarFactura(factura);

                if (idFactura == 0) {
                    JOptionPane.showMessageDialog(null, "error al registrar factura");
                    return;
                }
                for (int i = 0; i < tablaDetalleCompra.getRowCount(); i++) {
                    int idProducto = Integer.parseInt(tablaDetalleCompra.getValueAt(i, 0).toString());
                    double precio = Double.parseDouble(tablaDetalleCompra.getValueAt(i, 3).toString());
                    int cantidad = Integer.parseInt(tablaDetalleCompra.getValueAt(i, 4).toString());
                    double descuentoProducto = Double.parseDouble(tablaDetalleCompra.getValueAt(i, 5).toString());
                    double subtotalDetalle = Double.parseDouble(tablaDetalleCompra.getValueAt(i, 6).toString());

                    DetalleFactura detalle =
                            new DetalleFactura(
                                    idFactura,
                                    idProducto,
                                    cantidad,
                                    precio,
                                    subtotalDetalle,
                                    descuentoProducto
                            );
                    facturacionMet.registrarDetalleFactura(detalle);
                    facturacionMet.descontarStock(idProducto, cantidad);
                }

                JOptionPane.showMessageDialog(null, "factura guardada correctamente");
                limpiarFacturacion();
            }
        });
        btnEliminarProducto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = tablaDetalleCompra.getSelectedRow();
                if (fila == -1) {
                    JOptionPane.showMessageDialog(null, "seleccioná un producto de la tabla");
                    return;
                }
                DefaultTableModel modelo = (DefaultTableModel) tablaDetalleCompra.getModel();
                modelo.removeRow(fila);
                recalcularTotales();
            }
        });
        //botones a las vistas
        btnVistaCliente.addActionListener(e -> {
            new vistaCliente().setVisible(true);
            dispose();
        });
        btnVistaProducto.addActionListener(e -> {
            new vistaProducto().setVisible(true);
            dispose();
        });
        btnVistaProveedor.addActionListener(e -> {
            new vistaProveedor().setVisible(true);
            dispose();
        });
        btnVistaRegistrar.addActionListener(e -> {
            new vistaUsuario().setVisible(true);
            dispose();
        });
    }
    //recalculo la tabla
    private void recalcularTotales() {
        double subtotal = 0;
        for (int i = 0; i < tablaDetalleCompra.getRowCount(); i++) {
            subtotal += Double.parseDouble(tablaDetalleCompra.getValueAt(i, 6).toString());
        }
        double descuentoGeneral = 0;

        if (!txtDescuentoTotal.getText().trim().isEmpty()) {
            descuentoGeneral = Double.parseDouble(txtDescuentoTotal.getText());
        }
        double montoDescuento = subtotal * descuentoGeneral / 100;
        double total = subtotal - montoDescuento;

        lbSubtotal.setText(String.valueOf(subtotal));
        lbTotal.setText(String.valueOf(total));
    }
    //limpio los campos
    private void limpiarProductoSeleccionado() {
        idProductoSeleccionado = 0;
        codigoProductoSeleccionado = "";
        descripcionProductoSeleccionado = "";
        precioProductoSeleccionado = 0;
        stockProductoSeleccionado = 0;

        txtCodigoProducto.setText("");
        txtCantidad.setText("");
        txtDescuentoProducto.setText("");

        lbDescripcionProducto.setText("");
        lbPrecioProducto.setText("");
        lbStockProducto.setText("");
    }
    //limpio los campos de la tabla
    private void limpiarFacturacion() {
        idClienteSeleccionado = 0;

        lbNombreCliente.setText("");
        lbApellidoCliente.setText("");
        lbDniCliente.setText("");
        txtDniCliente.setText("");

        limpiarProductoSeleccionado();
        DefaultTableModel modelo = (DefaultTableModel) tablaDetalleCompra.getModel();
        modelo.setRowCount(0);

        txtDescuentoTotal.setText("");
        lbSubtotal.setText("0");
        lbTotal.setText("0");
    }
    //metodo para los botones de las vistas
    private void aplicarPermisosPorRol() {
        String rol = SesionUsuario.getRol();
        if (rol.equalsIgnoreCase("admin")) {
            btnVistaCliente.setEnabled(true);
            btnVistaProducto.setEnabled(true);
            btnVistaProveedor.setEnabled(true);
            btnVistaRegistrar.setEnabled(true);
        } else if (rol.equalsIgnoreCase("cajero")) {
            btnVistaCliente.setEnabled(true);
            btnVistaProducto.setEnabled(true);
            btnVistaProveedor.setEnabled(false);
            btnVistaRegistrar.setEnabled(false);
        } else if (rol.equalsIgnoreCase("deposito")) {
            btnVistaCliente.setEnabled(false);
            btnVistaProducto.setEnabled(true);
            btnVistaProveedor.setEnabled(true);
            btnVistaRegistrar.setEnabled(false);
        }
    }
}
