// Vista mejorada: _14_Ayuda.java - estilo por secciones
package vista;

import controlador.Controlador;
import controlador.BarraNavegacion;

import javax.swing.*;
import java.awt.*;

public class _14_Ayuda extends JFrame {
    private Controlador controlador;

    public _14_Ayuda() {
        setTitle("Centro de Ayuda - UrbanFixer");
        setSize(1200, 900);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(255, 255, 252));
        getContentPane().setLayout(null);

        // Barra de navegación reutilizable
        BarraNavegacion barra = new BarraNavegacion(null);
        barra.setBounds(0, 0, 1200, 59);
        getContentPane().add(barra);
        barra.btnAtras.addActionListener(e -> {
            if (controlador != null) controlador.volverAtras();
            dispose();
        });

        JLabel lblTitulo = new JLabel("Centro de Ayuda");
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 26));
        lblTitulo.setBounds(40, 80, 400, 40);
        getContentPane().add(lblTitulo);

        // Sección: Olvido de contraseña
        JPanel panel1 = crearPanelAyuda(" Olvido de contraseña", "Haz clic en '¿Olvidaste tu contraseña?' en la pantalla de login y responde tus preguntas de seguridad.", 140);
        getContentPane().add(panel1);

        // Sección: Reportar incidencia
        JPanel panel2 = crearPanelAyuda(" Reportar incidencia", "Accede a 'Crear Incidencia' desde la barra superior y describe tu problema.", 270);
        getContentPane().add(panel2);

        // Sección: Registro
        JPanel panel3 = crearPanelAyuda(" Registro", "Desde la pantalla de login, pulsa 'Crear cuenta' e ingresa tu información personal y preguntas de seguridad.", 400);
        getContentPane().add(panel3);

        // Botón ayuda inferior derecha
        JButton btnAyuda = new JButton("?");
        btnAyuda.setBounds(1120, 740, 50, 50);
        btnAyuda.setBackground(new Color(128, 0, 0));
        btnAyuda.setForeground(Color.WHITE);
        btnAyuda.setFont(new Font("Arial", Font.BOLD, 20));
        btnAyuda.setFocusPainted(false);
        btnAyuda.addActionListener(e -> JOptionPane.showMessageDialog(this, "Esta es la sección de ayuda. Elige una sección para ver más detalles."));
        getContentPane().add(btnAyuda);
    }

    private JPanel crearPanelAyuda(String titulo, String contenido, int y) {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(40, y, 1100, 100);
        panel.setBackground(new Color(245, 245, 245));
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setBounds(20, 10, 800, 25);
        panel.add(lblTitulo);

        JButton btnVer = new JButton("Ver más");
        btnVer.setBounds(970, 30, 100, 30);
        btnVer.setFocusPainted(false);
        btnVer.setBackground(new Color(128, 0, 0));
        btnVer.setForeground(Color.WHITE);
        btnVer.addActionListener(e -> JOptionPane.showMessageDialog(this, contenido));
        panel.add(btnVer);

        return panel;
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