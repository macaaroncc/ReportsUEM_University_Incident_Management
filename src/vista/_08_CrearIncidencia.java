package vista;

import controlador.Controlador;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import javax.imageio.ImageIO;

public class _08_CrearIncidencia extends JFrame {
    private Controlador controlador;
    private byte[] imagenBytes = null;

    private JTextField txtAula;
    private JTextArea txtDescripcion;
    private JComboBox<String> comboCampus, comboEdificio, comboPiso;
    private JLabel lblPreview;
    private File imagenSeleccionada;

   public _08_CrearIncidencia() {
    setTitle("08 . Crear Incidencia");
    setSize(1200, 900);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    getContentPane().setLayout(null);
    getContentPane().setBackground(new Color(255, 255, 252));

    // Barra de navegación
    BarraNavegacion barra = new BarraNavegacion();
    barra.setUsuarioLogueado(true);
    barra.setControlador(controlador);
    barra.setBounds(0, 0, 1200, 59);
    getContentPane().add(barra);

    JLabel lblTitulo = new JLabel("Crear incidencia");
    lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 24));
    lblTitulo.setBounds(100, 80, 300, 30);
    getContentPane().add(lblTitulo);

    // Coordenadas base
    int leftColumnX = 100;
    int rightColumnX = 600;
    int labelWidth = 150;
    int fieldWidth = 300;
    int heightStep = 50;
    int baseY = 180;

    // ---- COLUMNA IZQUIERDA: Foto + botón ----
    JLabel lblFoto = new JLabel("Foto:");
    lblFoto.setBounds(leftColumnX, baseY, labelWidth, 25);
    getContentPane().add(lblFoto);

    lblPreview = new JLabel();
    lblPreview.setBounds(leftColumnX, baseY + 30, 400, 300);
    lblPreview.setBorder(BorderFactory.createLineBorder(Color.GRAY));
    getContentPane().add(lblPreview);

    JButton btnSeleccionarFoto = new JButton("Seleccionar Foto");
    btnSeleccionarFoto.setBounds(leftColumnX, baseY + 350, 180, 30);
    getContentPane().add(btnSeleccionarFoto);

    btnSeleccionarFoto.addActionListener(e -> {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File archivo = chooser.getSelectedFile();
            try {
                imagenBytes = Files.readAllBytes(archivo.toPath());
                BufferedImage imagen = ImageIO.read(archivo);
                if (imagen != null) {
                    Image imagenEscalada = imagen.getScaledInstance(lblPreview.getWidth(), lblPreview.getHeight(), Image.SCALE_SMOOTH);
                    lblPreview.setIcon(new ImageIcon(imagenEscalada));
                } else {
                    lblPreview.setIcon(null);
                    JOptionPane.showMessageDialog(this, "El archivo seleccionado no es una imagen válida.");
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error al leer la imagen: " + ex.getMessage());
            }
        }
    });

    // ---- COLUMNA DERECHA: Descripción, Edificio, Piso, Aula, Campus ----
    int currentY = baseY;

    JLabel lblDescripcion = new JLabel("Descripción:");
    lblDescripcion.setBounds(rightColumnX, currentY, labelWidth, 25);
    getContentPane().add(lblDescripcion);

    txtDescripcion = new JTextArea();
    txtDescripcion.setLineWrap(true);
    txtDescripcion.setWrapStyleWord(true);
    txtDescripcion.setBorder(BorderFactory.createLineBorder(Color.GRAY));
    JScrollPane scrollDescripcion = new JScrollPane(txtDescripcion);
    scrollDescripcion.setBounds(rightColumnX + labelWidth, currentY, fieldWidth, 120);
    getContentPane().add(scrollDescripcion);
    currentY += 130;

    JLabel lblEdificio = new JLabel("Edificio:");
    lblEdificio.setBounds(rightColumnX, currentY, labelWidth, 25);
    getContentPane().add(lblEdificio);

    comboEdificio = new JComboBox<>(new String[] { "Edificio A", "Edificio B", "Edificio C", "Edificio E" });
    comboEdificio.setBounds(rightColumnX + labelWidth, currentY, fieldWidth, 30);
    getContentPane().add(comboEdificio);
    currentY += heightStep;

    JLabel lblPiso = new JLabel("Piso:");
    lblPiso.setBounds(rightColumnX, currentY, labelWidth, 25);
    getContentPane().add(lblPiso);

    comboPiso = new JComboBox<>(new String[] { "0", "1", "2", "3" });
    comboPiso.setBounds(rightColumnX + labelWidth, currentY, fieldWidth, 30);
    getContentPane().add(comboPiso);
    currentY += heightStep;

    JLabel lblAula = new JLabel("Aula:");
    lblAula.setBounds(rightColumnX, currentY, labelWidth, 25);
    getContentPane().add(lblAula);

    txtAula = new JTextField();
    txtAula.setBounds(rightColumnX + labelWidth, currentY, fieldWidth, 30);
    getContentPane().add(txtAula);
    currentY += heightStep;

    JLabel lblCampus = new JLabel("Campus:");
    lblCampus.setBounds(rightColumnX, currentY, labelWidth, 25);
    getContentPane().add(lblCampus);

    comboCampus = new JComboBox<>(new String[] { "Alcobendas", "Villaviciosa" });
    comboCampus.setBounds(rightColumnX + labelWidth, currentY, fieldWidth, 30);
    getContentPane().add(comboCampus);
    currentY += heightStep + 30;

    // Botón Crear Incidencia
    JButton btnCrear = new JButton("Crear Incidencia");
    btnCrear.setBounds(rightColumnX + labelWidth, currentY, fieldWidth, 45);
    btnCrear.setBackground(new Color(128, 0, 0));
    btnCrear.setForeground(Color.WHITE);
    btnCrear.setFont(new Font("Tahoma", Font.BOLD, 14));
    btnCrear.setFocusPainted(false);
    getContentPane().add(btnCrear);

    btnCrear.addActionListener(e -> {
        if (controlador != null) {
            controlador.crearIncidencia(
                comboEdificio.getSelectedItem().toString(),
                imagenBytes,
                comboPiso.getSelectedItem().toString(),
                txtDescripcion.getText(),
                txtAula.getText(),
                comboCampus.getSelectedItem().toString()
            );
        }
    });
}

    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
        for (Component c : getContentPane().getComponents()) {
            if (c instanceof BarraNavegacion) {
                ((BarraNavegacion) c).setControlador(controlador);
            }
        }
    }
}
