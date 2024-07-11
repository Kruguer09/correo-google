/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;
import Modelo.Email;
import correoencriptado.AESSimpleManager;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionListener;
import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableCellRenderer;

public class EmailViewer extends JFrame {
    private JTable table;
    private JButton openButton;
    private JButton downloadButton;
    AESSimpleManager des = new AESSimpleManager();

    public EmailViewer(List<Email> emails) {
        setTitle("Bandeja de Entrada");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 2) { // Suponiendo que la columna de fecha es la tercera (índice 2)
                    return Date.class;
                } else {
                    return super.getColumnClass(columnIndex);
                }
            }
        };
        model.addColumn("De");
        model.addColumn("Asunto");
        model.addColumn("Fecha");
        model.addColumn("Adjuntos");

        for (Email email : emails) {
            Object[] row = new Object[4];
            row[0] = email.getFrom();
            row[1] = email.getSubject();
            row[2] = email.getDate(); // La fecha se establece directamente
            row[3] = email.getAttachments().isEmpty() ? "No" : "Sí";
            
//            String body=email.getMessage();
//            Key clave = AESSimpleManager.obtenerClave("abcdefghijklmn123456", 16);
//            try {
//                String textoLimpio = des.descifrar(body,clave);
//            } catch (Exception ex) {
//                Logger.getLogger(EmailViewer.class.getName()).log(Level.SEVERE, null, ex);
//            }
            model.addRow(row);
        }

        table = new JTable(model);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        openButton = new JButton("Abrir");
        buttonPanel.add(openButton);

        downloadButton = new JButton("Descargar Adjunto");
        buttonPanel.add(downloadButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Renderizador de celdas para formatear la fecha
        TableCellRenderer renderer = new DefaultTableCellRenderer() {
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                if (value instanceof Date) {
                    value = format.format(value); // Formatear la fecha
                }
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        };
        table.getColumnModel().getColumn(2).setCellRenderer(renderer); // Aplicar el renderizador a la columna de fecha

        getContentPane().add(panel);
    }

    public int getSelectedRow() {
        return table.getSelectedRow();
    }

    public void addOpenButtonListener(ActionListener listener) {
        openButton.addActionListener(listener);
        //emails[0].
    }

    public void addDownloadButtonListener(ActionListener listener) {
        downloadButton.addActionListener(listener);
    }

    public void display() {
        setVisible(true);
    }
}
