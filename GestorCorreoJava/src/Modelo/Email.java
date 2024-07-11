/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author Usuario
 */
import java.util.Date;
import java.util.List;

public class Email {
    private String from;
    private String subject;
    private String message;
    private List<String> attachments;
    private Date date; // Cambiamos el tipo de dato a Date

    public Email(String from, String subject, String message, List<String> attachments, Date date) {
        this.from = from;
        this.subject = subject;
        this.message = message;
        this.attachments = attachments;
        this.date = date; // Se asigna la fecha
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<String> attachments) {
        this.attachments = attachments;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
