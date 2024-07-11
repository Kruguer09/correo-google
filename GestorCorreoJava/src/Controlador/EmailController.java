/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Email;
import Vista.EmailViewer;
import correoencriptado.AESSimpleManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Key;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class EmailController {
    private EmailViewer viewer;
    private List<Email> emails;
    final int LONGITUD_BLOQUE = 16;
    final String PASSWORD = "MeLlamoSpiderman";
    String textoClaro;
    AESSimpleManager des = new AESSimpleManager();

    public EmailController(EmailViewer viewer, List<Email> emails) {
        this.viewer = viewer;
        this.emails = emails;

        viewer.addOpenButtonListener(new OpenButtonListener());
    }

    public void showEmailViewer() {
        viewer.display();
    }

    private class OpenButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = viewer.getSelectedRow();
            if (selectedRow != -1) {
                Email selectedEmail = emails.get(selectedRow);
                showEmailContent(selectedEmail);
            }
        }
    }
    private class DownloadButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = viewer.getSelectedRow();
            if (selectedRow != -1) {
                Email selectedEmail = emails.get(selectedRow);
                downloadAttachments(selectedEmail);
            }
        }
    }
    private void downloadAttachments(Email email) {
        List<String> attachments = email.getAttachments();
        if (attachments.isEmpty()) {
            JOptionPane.showMessageDialog(viewer, "No hay adjuntos para descargar");
            return;
        }

        for (String attachment : attachments) {
            try {
                // Descargar el archivo adjunto
                FileOutputStream outputStream = new FileOutputStream(attachment);
                // Aquí deberías tener la lógica para descargar el archivo desde el servidor de correo
                outputStream.close();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(viewer, "Error al descargar adjunto: " + ex.getMessage());
                ex.printStackTrace();
            }
        }

        JOptionPane.showMessageDialog(viewer, "Adjuntos descargados exitosamente");
    }

    private void showEmailContent(Email email) {
        JFrame frame = new JFrame("Contenido del Mensaje");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
   
        JTextArea textArea = new JTextArea();
        
        
        String body = email.getMessage().trim();
        String textoLimpio="";
        
        Key clave = AESSimpleManager.obtenerClave("abcdefghijklmn123456", 16);
        try {
            textoLimpio = des.descifrar(body,clave);
        } catch (Exception ex) {
            Logger.getLogger(EmailController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
        
        textArea.setText(textoLimpio); // Mostrar el mensaje del correo electrónico
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane);

        frame.setVisible(true);
    }
}

