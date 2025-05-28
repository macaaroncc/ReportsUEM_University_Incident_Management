package vista;

import javax.swing.*;

import controlador.Controlador;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.sql.*;
import javax.imageio.ImageIO;

public class _17_DetalleIncidencia extends JFrame {
    private Controlador controlador;

    public _17_DetalleIncidencia(int idIncidencia) {
        setTitle("Detalle de la Incidencia");
        setSize(900, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);
        getContentPane().setBackground(new Color(255, 255, 252));

        JLabel lblTitulo = new JLabel("Detalle de la incidencia");
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblTitulo.setBounds(50, 20, 400, 30);
        getContentPane().add(lblTitulo);

        // Imagen
        JLabel lblImagen = new JLabel();
        lblImagen.setBounds(50, 70, 300, 200);
        lblImagen.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        getContentPane().add(lblImagen);

        // Coordenadas base
        int labelX = 50;
        int fieldX = 200;
        int labelWidth = 130;
        int fieldWidth = 600;
        int y = 290;
        int height = 30;
        int gap = 40;

        Font fuente = new Font("Tahoma", Font.PLAIN, 14);

        String[] campos = {
            "Descripción", "ID", "Estado", "Edificio", "Piso", "Aula", 
            "Justificación", "Campus", "Fecha", "Ranking", "Usuario"
        };

        JTextField[] textFields = new JTextField[campos.length];

        for (int i = 0; i < campos.length; i++) {
            JLabel lbl = new JLabel(campos[i] + ":");
            lbl.setFont(fuente);
            lbl.setBounds(labelX, y, labelWidth, height);
            getContentPane().add(lbl);

            JTextField txt = new JTextField();
            txt.setEditable(false);
            txt.setFont(fuente);
            txt.setBounds(fieldX, y, fieldWidth, height);
            getContentPane().add(txt);

            textFields[i] = txt;
            y += gap;
        }

        // Cargar datos
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/proyecto_integrador", "root", "");
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM incidencias WHERE id_incidencia = ?")) {

            stmt.setInt(1, idIncidencia);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Mostrar imagen si existe
                byte[] imagenBytes = rs.getBytes("foto");
                if (imagenBytes != null && imagenBytes.length > 0) {
                    BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imagenBytes));
                    Image imagenEscalada = bufferedImage.getScaledInstance(lblImagen.getWidth(), lblImagen.getHeight(), Image.SCALE_SMOOTH);
                    lblImagen.setIcon(new ImageIcon(imagenEscalada));
                }

                // Llenar campos (nota: ahora la descripción es la primera)
                textFields[0].setText(rs.getString("descripcion"));
                textFields[1].setText(String.valueOf(rs.getInt("id_incidencia")));
                textFields[2].setText(rs.getString("estado"));
                textFields[3].setText(rs.getString("edificio"));
                textFields[4].setText(rs.getString("piso"));
                textFields[5].setText(rs.getString("aula"));
                textFields[6].setText(rs.getString("justificacion"));
                textFields[7].setText(rs.getString("campus"));
                textFields[8].setText(rs.getDate("fecha").toString());
                textFields[9].setText(String.valueOf(rs.getInt("ranking")));
                textFields[10].setText(rs.getString("USR"));

            } else {
                JOptionPane.showMessageDialog(this, "No se encontró la incidencia con ID: " + idIncidencia);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los detalles:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }
}
