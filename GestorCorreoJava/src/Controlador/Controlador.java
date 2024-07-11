/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import correoencriptado.AESSimpleManager;
import java.io.File;
import java.security.Key;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JOptionPane;

/**
 *
 * @author DANIEL
 */
public class Controlador {

    public String from = "proyectopsp2024@gmail.com";
    public String host = "smtp.gmail.com";
    public String password = "xnffbbkqwflvkeig";
    public Session session;
    private String textoCifrado = "";

    public Controlador() {
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        session = Session.getDefaultInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });
    }

    //Método usado para enviar un mensaje SIN archivo adjunto
    public void enviarMensaje(String destinatario, String asunto, String contenido) {

        Key clave = AESSimpleManager.obtenerClave("abcdefghijklmn123456", 16);
        try {
            textoCifrado = AESSimpleManager.cifrar(contenido, clave);
            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(from));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
                message.setSubject(asunto);
                message.setText(textoCifrado);

                Transport.send(message);

                JOptionPane.showMessageDialog(null, "Email enviado correctamente:"+ " Cifrado: "+textoCifrado+". Sin cifrar: "+contenido);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //Método usado para enviar un mensaje CON archivo adjunto (al pasárselo por parámetro se ejecuta este método
    //directamente aunque tengan el mismo nombre
    public void enviarMensajeYAdjunto(String destinatario, String asunto, String contenido, String rutaArchivo) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
            message.setSubject(asunto);
            message.setText(contenido);

            if (rutaArchivo.length() > 0) {
                Multipart multipart = new MimeMultipart();

                BodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setText(contenido);

                multipart.addBodyPart(messageBodyPart);

                messageBodyPart = new MimeBodyPart();

                String ruta = rutaArchivo;
                File file = new File(ruta);
                DataSource source = new FileDataSource(file);
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName(file.getName());

                multipart.addBodyPart(messageBodyPart);
                message.setContent(multipart);
            }

            Transport.send(message);

            JOptionPane.showMessageDialog(null, "Email enviado correctamente.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
