/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

/**
 *
 * @author Usuario
 */
import Modelo.Email;
import Vista.EmailViewer;
import Vista.LoginView;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class LoginController {
    private LoginView loginView;

    public LoginController(LoginView loginView) {
        this.loginView = loginView;
        loginView.addIniciarSesionListener(new IniciarSesionListener());
    }

    public void showLoginView() {
        loginView.display();
    }

    private class IniciarSesionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String correo = loginView.getCorreo();
            String contrasena = loginView.getContrasena();

            // Intenta leer los correos electrónicos con las credenciales proporcionadas
            try {
                EmailReader emailReader = new EmailReader();
                List<Email> emails = emailReader.leerCarpetainbox(correo, contrasena);
                
                // Si se logra leer los correos electrónicos, muestra la bandeja de entrada
                if (emails != null) {
                    EmailViewer viewer = new EmailViewer(emails);
                    EmailController controller = new EmailController(viewer, emails);
                    controller.showEmailViewer();
                    loginView.dispose(); // Cierra la ventana de inicio de sesión
                } else {
                    // Si las credenciales son incorrectas o hay algún error al leer los correos electrónicos, muestra un mensaje de error
                    JOptionPane.showMessageDialog(loginView, "Inicio de sesión incorrecto");
                }
            } catch (Exception exception) {
                // En caso de una excepción, muestra un mensaje de error
                JOptionPane.showMessageDialog(loginView, "Error al intentar iniciar sesión");
                exception.printStackTrace();
            }
        }
    }
}
