package vista;

import javax.swing.*;
import controlador.Controlador;
import modelo.Modelo;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.sql.*;
import javax.imageio.ImageIO;

public class _17_DetalleIncidencia extends JFrame {
	private Controlador controlador;
	private String usuario; // Usuario logueado actual

	public _17_DetalleIncidencia(int idIncidencia) {
        this.usuario = Modelo.usuarioActual;

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

        // Coordenadas base para campos
        int labelX = 30;
        int fieldX = 160;
        int labelWidth = 110;
        int fieldWidth = 490;
        int y = 300;
        int height = 30;
        int gap = 35;

        Font fuente = new Font("Tahoma", Font.PLAIN, 13);

        // Sin "Me gusta" en los campos normales
        String[] campos = { "Descripción", "Estado", "Edificio", "Piso", "Aula", "Campus", "Fecha", "Usuario" };

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

        // Label "Me gusta: nº" abajo a la izquierda
        JLabel lblMeGusta = new JLabel("Me gusta: 0");
        lblMeGusta.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblMeGusta.setForeground(new Color(128, 0, 0));
        lblMeGusta.setBounds(30, 630, 150, 40);
        getContentPane().add(lblMeGusta);

        // Panel de botones inferiores a la derecha abajo
        JPanel panelBotones = new JPanel();
        panelBotones.setBounds((700 - 240) / 2, 630, 240, 50);
        panelBotones.setOpaque(false);
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        getContentPane().add(panelBotones);

        String[] simbolos = { "✔️", "🔄", "⭐" };
        String[] tooltips = { "Marcar como resuelta", "Añadir a Favoritos", "Dar Me gusta" };

        for (int i = 0; i < 3; i++) {
            JButton btn = new JButton(simbolos[i]);
            btn.setPreferredSize(new Dimension(50, 50));
            btn.setBackground(new Color(128, 0, 0));
            btn.setForeground(Color.WHITE);
            btn.setToolTipText(tooltips[i]);
            btn.setBorderPainted(false);
            btn.setFocusPainted(false);
            
            if (i == 0) { 
                btn.addActionListener(e -> {
                    JOptionPane.showMessageDialog(this, "Incidencia marcada como resuelta. ¡Gracias!");
                });
            }                                  
                
            
            if (i == 1) { // Botón "Añadir a Favoritos" (🔄)
                btn.addActionListener(e -> {
                    try (Connection conn = DriverManager
                            .getConnection("jdbc:mysql://localhost:3306/proyecto_integrador", "root", "")) {

                        // Concatenar @ueuropea.es al usuario
                        String usuarioEmail = Modelo.usuarioActual + "@ueuropea.es";

                        // Verificar si ya existe favorito para este usuario e incidencia
                        PreparedStatement checkStmt = conn.prepareStatement(
                                "SELECT COUNT(*) FROM favoritos WHERE USERS_USR = ? AND incidencias_id_incidencia = ?");
                        checkStmt.setString(1, usuarioEmail);
                        checkStmt.setInt(2, idIncidencia);
                        ResultSet rs = checkStmt.executeQuery();
                        rs.next();
                        int count = rs.getInt(1);

                        if (count > 0) {
                            JOptionPane.showMessageDialog(this, "Esta incidencia ya está en tus favoritos.");
                        } else {
                            // Insertar nuevo favorito
                            PreparedStatement insertStmt = conn.prepareStatement(
                                    "INSERT INTO favoritos (USERS_USR, incidencias_id_incidencia) VALUES (?, ?)");
                            insertStmt.setString(1, usuarioEmail);
                            insertStmt.setInt(2, idIncidencia);
                            insertStmt.executeUpdate();

                            JOptionPane.showMessageDialog(this, "Incidencia añadida a tus favoritos.");
                        }

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Error al añadir a favoritos:\n" + ex.getMessage());
                    }
                });
            }


            if (i == 2) { // Botón "Me gusta"
                btn.addActionListener(e -> {
                    try (Connection conn = DriverManager
                            .getConnection("jdbc:mysql://localhost:3306/proyecto_integrador", "root", "")) {
                        // Verificar si el usuario ya marcó favorito en la tabla favoritosCount
                        PreparedStatement checkStmt = conn.prepareStatement(
                                "SELECT COUNT(*) FROM favoritosCount WHERE id_usuario = ? AND id_incidencia = ?");
                        checkStmt.setString(1, usuario);
                        checkStmt.setInt(2, idIncidencia);
                        ResultSet rs = checkStmt.executeQuery();
                        rs.next();
                        int count = rs.getInt(1);

                        if (count > 0) {
                            JOptionPane.showMessageDialog(this, "Ya has marcado esta incidencia como favorita.");
                        } else {
                            // Insertar nuevo favorito en favoritosCount
                            PreparedStatement insertStmt = conn.prepareStatement(
                                    "INSERT INTO favoritosCount (id_usuario, id_incidencia) VALUES (?, ?)");
                            insertStmt.setString(1, usuario);
                            insertStmt.setInt(2, idIncidencia);
                            insertStmt.executeUpdate();

                            // Actualizar ranking de la incidencia
                            PreparedStatement updateStmt = conn.prepareStatement(
                                    "UPDATE incidencias SET ranking = ranking + 1 WHERE id_incidencia = ?");
                            updateStmt.setInt(1, idIncidencia);
                            updateStmt.executeUpdate();

                            JOptionPane.showMessageDialog(this, "¡Añadido a favoritos!");

                            // Actualizar visualmente el label "Me gusta"
                            int rankingActual = Integer.parseInt(lblMeGusta.getText().replace("Me gusta: ", ""));
                            lblMeGusta.setText("Me gusta: " + (rankingActual + 1));
                        }

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Error al marcar favorito:\n" + ex.getMessage());
                    }
                });
            }

            panelBotones.add(btn);
        }

        // Cargar datos de la incidencia
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/proyecto_integrador",
                "root", "");
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM incidencias WHERE id_incidencia = ?")) {

            stmt.setInt(1, idIncidencia);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Mostrar imagen si existe
                byte[] imagenBytes = rs.getBytes("foto");
                if (imagenBytes != null && imagenBytes.length > 0) {
                    try {
                        BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imagenBytes));
                        if (bufferedImage != null) {
                            Image imagenEscalada = bufferedImage.getScaledInstance(lblImagen.getWidth(),
                                    lblImagen.getHeight(), Image.SCALE_SMOOTH);
                            lblImagen.setIcon(new ImageIcon(imagenEscalada));
                        } else {
                            BufferedImage imagenBlanca = new BufferedImage(lblImagen.getWidth(),
                                    lblImagen.getHeight(), BufferedImage.TYPE_INT_RGB);
                            Graphics2D g2d = imagenBlanca.createGraphics();
                            g2d.setColor(Color.WHITE);
                            g2d.fillRect(0, 0, imagenBlanca.getWidth(), imagenBlanca.getHeight());
                            g2d.dispose();
                            lblImagen.setIcon(new ImageIcon(imagenBlanca));
                        }
                    } catch (Exception e) {
                        BufferedImage imagenBlanca = new BufferedImage(lblImagen.getWidth(), lblImagen.getHeight(),
                                BufferedImage.TYPE_INT_RGB);
                        Graphics2D g2d = imagenBlanca.createGraphics();
                        g2d.setColor(Color.WHITE);
                        g2d.fillRect(0, 0, imagenBlanca.getWidth(), imagenBlanca.getHeight());
                        g2d.dispose();
                        lblImagen.setIcon(new ImageIcon(imagenBlanca));
                    }
                } else {
                    BufferedImage imagenBlanca = new BufferedImage(lblImagen.getWidth(), lblImagen.getHeight(),
                            BufferedImage.TYPE_INT_RGB);
                    Graphics2D g2d = imagenBlanca.createGraphics();
                    g2d.setColor(Color.WHITE);
                    g2d.fillRect(0, 0, imagenBlanca.getWidth(), imagenBlanca.getHeight());
                    g2d.dispose();
                    lblImagen.setIcon(new ImageIcon(imagenBlanca));
                }

                // Llenar los campos sin "Me gusta"
                textFields[0].setText(rs.getString("descripcion"));
                textFields[1].setText(rs.getString("estado"));
                textFields[2].setText(rs.getString("edificio"));
                textFields[3].setText(rs.getString("piso"));
                textFields[4].setText(rs.getString("aula"));
                textFields[5].setText(rs.getString("campus"));
                textFields[6].setText(rs.getDate("fecha").toString());
                textFields[7].setText(rs.getString("USR"));

                // Setear "Me gusta" en el label abajo
                int ranking = rs.getInt("ranking");
                lblMeGusta.setText("Me gusta: " + ranking);

            } else {
                JOptionPane.showMessageDialog(this, "No se encontró la incidencia con ID: " + idIncidencia);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los detalles:\n" + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public void setControlador(Controlador controlador) {
		this.controlador = controlador;
	}
}
