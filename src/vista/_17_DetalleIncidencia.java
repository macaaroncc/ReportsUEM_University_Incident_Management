package vista;

import javax.swing.*;
import controlador.Controlador;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.sql.*;
import javax.imageio.ImageIO;

public class _17_DetalleIncidencia extends JFrame {
    private Controlador controlador;

    public _17_DetalleIncidencia(int idIncidencia) {
        setTitle("Detalle de la Incidencia");
        setSize(700, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);
        getContentPane().setBackground(new Color(255, 255, 252));

        // Banda decorativa superior
        JPanel panelSuperior = new JPanel();
        panelSuperior.setBackground(new Color(128, 0, 0));
        panelSuperior.setBounds(0, 0, 700, 60);
        panelSuperior.setLayout(null);
        getContentPane().add(panelSuperior);

        JLabel lblTitulo = new JLabel("Detalle de la incidencia");
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 22));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(20, 15, 400, 30);
        panelSuperior.add(lblTitulo);

        // Imagen centrada
        JLabel lblImagen = new JLabel();
        lblImagen.setBounds((700 - 300) / 2, 80, 300, 200);
        lblImagen.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        getContentPane().add(lblImagen);

        // Coordenadas base
        int labelX = 30;
        int fieldX = 160;
        int labelWidth = 110;
        int fieldWidth = 490;
        int y = 300;
        int height = 30;
        int gap = 35;

        Font fuente = new Font("Tahoma", Font.PLAIN, 13);

        String[] campos = {
            "Descripción", "Estado", "Edificio", "Piso", "Aula",
            "Campus", "Fecha", "Me gusta", "Usuario"
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

        // Panel de botones inferiores
        JPanel panelBotones = new JPanel();
        panelBotones.setBounds((700 - 240) / 2, 630, 240, 50);
        panelBotones.setOpaque(false);
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        getContentPane().add(panelBotones);

        String[] simbolos = { "\u2714", "\u21BB", "\u2605" };  // ✔ ↻ ★
        String[] tooltips = {
            "Marcar como resuelta",
            "Confirmar incidencia",
            "Añadir a favoritos"
        };

        for (int i = 0; i < 3; i++) {
            JButton btn = new JButton(simbolos[i]);
            btn.setPreferredSize(new Dimension(50, 50));
            btn.setBackground(new Color(128, 0, 0));
            btn.setForeground(Color.WHITE);
            // Cambia aquí la fuente según la que tengas instalada
            btn.setFont(new Font("Arial Unicode MS", Font.BOLD, 20));
            btn.setToolTipText(tooltips[i]);
            btn.setBorderPainted(false);
            btn.setFocusPainted(false);
            panelBotones.add(btn);
        }


        // Cargar datos de la incidencia
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/proyecto_integrador", "root", "");
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM incidencias WHERE id_incidencia = ?")) {

            stmt.setInt(1, idIncidencia);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Mostrar imagen si existe
            	// Mostrar imagen si existe
            	byte[] imagenBytes = rs.getBytes("foto");
            	if (imagenBytes != null && imagenBytes.length > 0) {
            	    try {
            	        BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imagenBytes));
            	        if (bufferedImage != null) {
            	            Image imagenEscalada = bufferedImage.getScaledInstance(lblImagen.getWidth(), lblImagen.getHeight(), Image.SCALE_SMOOTH);
            	            lblImagen.setIcon(new ImageIcon(imagenEscalada));
            	        } else {
            	            // bufferedImage es null, poner imagen blanca vacía
            	            BufferedImage imagenBlanca = new BufferedImage(lblImagen.getWidth(), lblImagen.getHeight(), BufferedImage.TYPE_INT_RGB);
            	            Graphics2D g2d = imagenBlanca.createGraphics();
            	            g2d.setColor(Color.WHITE);
            	            g2d.fillRect(0, 0, imagenBlanca.getWidth(), imagenBlanca.getHeight());
            	            g2d.dispose();
            	            lblImagen.setIcon(new ImageIcon(imagenBlanca));
            	        }
            	    } catch (Exception e) {
            	        // En caso de error, poner imagen blanca vacía
            	        BufferedImage imagenBlanca = new BufferedImage(lblImagen.getWidth(), lblImagen.getHeight(), BufferedImage.TYPE_INT_RGB);
            	        Graphics2D g2d = imagenBlanca.createGraphics();
            	        g2d.setColor(Color.WHITE);
            	        g2d.fillRect(0, 0, imagenBlanca.getWidth(), imagenBlanca.getHeight());
            	        g2d.dispose();
            	        lblImagen.setIcon(new ImageIcon(imagenBlanca));
            	    }
            	} else {
            	    // No hay imagen, poner imagen blanca vacía
            	    BufferedImage imagenBlanca = new BufferedImage(lblImagen.getWidth(), lblImagen.getHeight(), BufferedImage.TYPE_INT_RGB);
            	    Graphics2D g2d = imagenBlanca.createGraphics();
            	    g2d.setColor(Color.WHITE);
            	    g2d.fillRect(0, 0, imagenBlanca.getWidth(), imagenBlanca.getHeight());
            	    g2d.dispose();
            	    lblImagen.setIcon(new ImageIcon(imagenBlanca));
            	}


                // Llenar los campos
                textFields[0].setText(rs.getString("descripcion"));
                textFields[1].setText(rs.getString("estado"));
                textFields[2].setText(rs.getString("edificio"));
                textFields[3].setText(rs.getString("piso"));
                textFields[4].setText(rs.getString("aula"));
                textFields[5].setText(rs.getString("campus"));
                textFields[6].setText(rs.getDate("fecha").toString());
                textFields[7].setText(String.valueOf(rs.getInt("ranking")));
                textFields[8].setText(rs.getString("USR"));

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
