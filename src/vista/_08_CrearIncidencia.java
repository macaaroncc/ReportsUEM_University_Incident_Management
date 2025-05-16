// Vista actualizada: _08_CrearIncidencia.java
package vista;

import controlador.BarraNavegacion;
import controlador.Controlador;

import javax.swing.*;
import java.awt.*;

public class _08_CrearIncidencia extends JFrame {
    private Controlador controlador;

    private JTextField txtTitulo;
    private JTextArea txtDescripcion;
    private JTextArea txtAula;
    private JComboBox<String> comboCampus, comboEdificio, comboPiso;

    public _08_CrearIncidencia() {
        setTitle("08 . Crear Incidencia");
        setSize(1200, 900);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        getContentPane().setBackground(new Color(255, 255, 252));

        // ✅ Barra de navegación reutilizable
        BarraNavegacion barra = new BarraNavegacion(controlador);
        barra.setUsuarioLogueado(true);              // Habilita enlaces funcionales
        barra.setControlador(controlador);           // Asigna listeners y lógica
        barra.setBounds(0, 0, 1200, 59);             // Asegura que se vea bien
        getContentPane().add(barra);



        // Acción del botón atrás
        barra.btnAtras.addActionListener(e -> {
            if (controlador != null) controlador.volverAtras();
            dispose();
        });

        // Título
        JLabel lblTitulo = new JLabel("Crear incidencia");
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblTitulo.setBounds(100, 80, 300, 30);
        getContentPane().add(lblTitulo);

        // Imagen (placeholder)
        JLabel lblImagen = new JLabel("Imagen");
        lblImagen.setBounds(100, 130, 500, 400);
        lblImagen.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        getContentPane().add(lblImagen);

        // Título de la incidencia
        txtTitulo = new JTextField("Título");
        txtTitulo.setBounds(650, 130, 400, 35);
        getContentPane().add(txtTitulo);

        // Descripción
        txtDescripcion = new JTextArea("Descripción");
        txtDescripcion.setBounds(650, 180, 400, 200);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        txtDescripcion.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        getContentPane().add(txtDescripcion);

        // Campus
        comboCampus = new JComboBox<>(new String[]{"Campus"});
        comboCampus.setBounds(650, 400, 400, 35);
        getContentPane().add(comboCampus);

        // Edificio y Piso
        comboEdificio = new JComboBox<>(new String[]{"Edificio"});
        comboEdificio.setBounds(650, 450, 190, 35);
        getContentPane().add(comboEdificio);

        comboPiso = new JComboBox<>(new String[]{"Piso"});
        comboPiso.setBounds(860, 450, 190, 35);
        getContentPane().add(comboPiso);

        // Aula
        txtAula = new JTextArea("Aula:");
        txtAula.setBounds(650, 500, 400, 35);
        txtAula.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        getContentPane().add(txtAula);

        // Botón Crear
        JButton btnCrear = new JButton("Crear Incidencia");
        btnCrear.setBounds(650, 560, 400, 45);
        btnCrear.setBackground(new Color(128, 0, 0));
        btnCrear.setForeground(Color.WHITE);
        btnCrear.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnCrear.setFocusPainted(false);
        getContentPane().add(btnCrear);

        // Botón Ayuda flotante
        JButton btnAyuda = new JButton("?");
        btnAyuda.setBounds(1120, 740, 50, 50);
        btnAyuda.setBackground(new Color(128, 0, 0));
        btnAyuda.setForeground(Color.WHITE);
        btnAyuda.setFont(new Font("Arial", Font.BOLD, 20));
        btnAyuda.setFocusPainted(false);
        btnAyuda.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        getContentPane().add(btnAyuda);

        btnAyuda.addActionListener(e -> {
            if (controlador != null) controlador.abrirAyuda();
            dispose();
        });
    }

    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
        Component[] components = getContentPane().getComponents();
        for (Component c : components) {
            if (c instanceof BarraNavegacion) {
                ((BarraNavegacion) c).setControlador(controlador);
            }
        }
    }
}
