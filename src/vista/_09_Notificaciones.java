//@autor Haowen

package vista;

import controlador.BarraNavegacion;
import controlador.Controlador;

import javax.swing.*;
import java.awt.*;

public class _09_Notificaciones extends JFrame {
    private Controlador controlador;

    public _09_Notificaciones() {
        setTitle("09 . Notificaciones");
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



        // Contenido de la notificación
        JLabel lblTitulo = new JLabel("Detalle de Notificación");
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 22));
        lblTitulo.setBounds(460, 80, 400, 30);
        getContentPane().add(lblTitulo);

        JLabel lblImagen = new JLabel("Imagen", SwingConstants.CENTER);
        lblImagen.setBounds(100, 140, 500, 400);
        lblImagen.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        getContentPane().add(lblImagen);

        JTextField txtTitulo = new JTextField("Título");
        txtTitulo.setBounds(650, 140, 400, 30);
        getContentPane().add(txtTitulo);

        JTextArea txtDescripcion = new JTextArea("Descripción");
        JScrollPane scrollDesc = new JScrollPane(txtDescripcion);
        scrollDesc.setBounds(650, 180, 400, 200);
        getContentPane().add(scrollDesc);

        JLabel lblEstado = new JLabel("Estado:");
        lblEstado.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblEstado.setBounds(650, 400, 100, 30);
        getContentPane().add(lblEstado);

        JLabel lblEstadoValor = new JLabel("✔ En proceso");
        lblEstadoValor.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblEstadoValor.setForeground(new Color(34, 139, 34));
        lblEstadoValor.setBounds(750, 400, 200, 30);
        getContentPane().add(lblEstadoValor);

        // ✅ Botón de ayuda flotante
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
            _09_Notificaciones.this.dispose();
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
