package Vistas;
import Metodos.UsuarioMet;
import Modelo.Usuarios;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class vistaLogin extends JFrame  {
    private JPanel panelPrincipal;
    private JTextField txtUsuarioMail;
    private JTextField txtPassword;
    private JButton btnIngresar;
    private JPanel panelLogin;

    public vistaLogin() {
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        btnIngresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UsuarioMet usuarioMet = new UsuarioMet();
                String email = txtUsuarioMail.getText();
                String pass = txtPassword.getText();

                Usuarios usuarios = usuarioMet.login(email, pass);
                if (usuarios != null) {
                    JOptionPane.showMessageDialog(null,"Bienvenido " + usuarios.getNombre());
                    String rol = usuarios.getRol();
                    if (rol.equalsIgnoreCase("admin")) {
                        vistaUsuario vista = new vistaUsuario();
                        vista.setVisible(true);
                        dispose();
                    } else if (rol.equalsIgnoreCase("cajero")) {
                        vistaFacturacion vista = new vistaFacturacion();
                        vista.setVisible(true);
                        dispose();
                    } else if (rol.equalsIgnoreCase("deposito")) {
                        vistaProveedor vista = new vistaProveedor();
                        vista.setVisible(true);
                        dispose();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
                }
            }
        });
    }
}
