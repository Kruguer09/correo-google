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
import correoencriptado.AESSimpleManager;
import correoencriptado.DescifradorAESSimple;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.mail.BodyPart;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMultipart;

public class EmailReader {
    public List<Email> leerCarpetainbox(String email, String password) throws Exception {
        List<Email> emails = new ArrayList<>();
        AESSimpleManager des = new AESSimpleManager();
        
        
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");
        Session session = Session.getInstance(props);
        
        Store store = session.getStore("imaps");
        store.connect("imap.gmail.com", email, password);
        
        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);
        
        Message[] messages = inbox.getMessages();
        for (Message message : messages) {
            String from = InternetAddress.toString(message.getFrom());
            String subject = message.getSubject();
            String body = getTextFromMessage(message);
//            Key clave = AESSimpleManager.obtenerClave("abcdefghijklmn123456", 16);
//            String textoLimpio = des.descifrar(body,clave);
            
            List<String> attachments = getAttachmentsFromMessage(message);
            Date date = message.getSentDate(); // Obtener la fecha del mensaje
            
            // Crear instancia de Email con los nuevos parámetros
            Email emailObject = new Email(from, subject, body, attachments, date);
            emails.add(emailObject);
        }
        
        inbox.close(false);
        store.close();
        
        return emails;
    }
    private List<String> getAttachmentsFromMessage(Message message) throws Exception {
    List<String> attachments = new ArrayList<>();

    if (message.getContent() instanceof Multipart) {
        Multipart multipart = (Multipart) message.getContent();
        for (int i = 0; i < multipart.getCount(); i++) {
            BodyPart bodyPart = multipart.getBodyPart(i);
            if (Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition())) {
                // Si la parte es un archivo adjunto, agrega su nombre a la lista de adjuntos
                String fileName = bodyPart.getFileName();
                if (fileName != null) {
                    attachments.add(fileName);
                }
            }
        }
    }

    return attachments;
}


    private String getTextFromMessage(Message message) throws Exception {
        StringBuilder result = new StringBuilder();
        //String result=(String) message.getContent();
        
        if (message.isMimeType("text/plain")) {
            result.append((String) message.getContent());
            
        } else if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            for (int i = 0; i < mimeMultipart.getCount()-1; i++) {
                BodyPart bodyPart = mimeMultipart.getBodyPart(i);
                if (bodyPart.isMimeType("text/plain")) {
                    result.append((String) bodyPart.getContent());
                } else if (bodyPart.isMimeType("text/html")) {
                    // Si el mensaje es de tipo HTML, puedes ignorarlo o manejarlo según tus necesidades
                    result.append("Mensaje HTML: ").append((String) bodyPart.getContent());
                } else {
//                     Si el mensaje es de otro tipo, puedes ignorarlo o manejarlo según tus necesidades
//                    result.append(bodyPart.getContent().toString());
                }
            }
        }
        return result.toString();
        
        
        
        /*
                //StringBuilder result = new StringBuilder();
        String result=(String) message.getContent();
        String textoEnClaro="";
        
        if (message.isMimeType("text/plain")) {
            
            Key clave = AESSimpleManager.obtenerClave("abcdefghijklmn123456", 16);
            textoEnClaro = AESSimpleManager.descifrar((String) message.getContent(), clave);
            
            //result.append((String) message.getContent());
            
            
        } 
//        else if (message.isMimeType("multipart/*")) {
//            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
//            for (int i = 0; i < mimeMultipart.getCount()-1; i++) {
//                BodyPart bodyPart = mimeMultipart.getBodyPart(i);
//                if (bodyPart.isMimeType("text/plain")) {
//                    result.append((String) bodyPart.getContent());
//                } else if (bodyPart.isMimeType("text/html")) {
//                    // Si el mensaje es de tipo HTML, puedes ignorarlo o manejarlo según tus necesidades
//                    result.append("Mensaje HTML: ").append((String) bodyPart.getContent());
//                } else {
////                     Si el mensaje es de otro tipo, puedes ignorarlo o manejarlo según tus necesidades
////                    result.append(bodyPart.getContent().toString());
//                }
//            }
//        }
        return textoEnClaro;//result.toString();
        */
    }
}
