/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

/**
 *
 * @author Usuario
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {
    private JTextField correoField;
    private JPasswordField contrasenaField;
    private JButton iniciarSesionButton;

    public LoginView() {
        setTitle("Inicio de Sesión");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        panel.add(new JLabel("Dirección de Correo:"));
        
        correoField = new JTextField();
        correoField.setText("proyectopsp2024@gmail.com");
        panel.add(correoField);

        panel.add(new JLabel("Contraseña:"));
        contrasenaField = new JPasswordField();
        contrasenaField.setText("prbl esqm ftmc dzxo");
        panel.add(contrasenaField);

        iniciarSesionButton = new JButton("Iniciar Sesión");
        panel.add(iniciarSesionButton);

        getContentPane().add(panel);
    }

    public String getCorreo() {
        return correoField.getText();
    }

    public String getContrasena() {
        return new String(contrasenaField.getPassword());
    }

    public void addIniciarSesionListener(ActionListener listener) {
        iniciarSesionButton.addActionListener(listener);
    }

    public void display() {
        setVisible(true);
    }
}

