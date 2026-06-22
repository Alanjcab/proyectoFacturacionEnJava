package Vistas;
import Metodos.UsuarioMet;
import Modelo.Usuarios;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Modelo.SesionUsuario;


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
                String email = txtUsuarioMail.getText().trim();
                String pass = txtPassword.getText().trim();

                if (email.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "ingrese el email");
                    return;
                }
                if (pass.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "ingrese la contraseña");
                    return;
                }

                UsuarioMet usuarioMet = new UsuarioMet();
                Usuarios usuarios = usuarioMet.login(email, pass);

                if (usuarios != null) {
                    SesionUsuario.setRol(usuarios.getRol());
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
                    } else {
                        JOptionPane.showMessageDialog(null, "no se encontro el rol");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "usuario o contraseña incorrecta");
                }
            }
        });
    }
}
