package vista;

import controlador.Controlador;
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

        // Barra de navegación
        BarraNavegacion barra = new BarraNavegacion();
        barra.setBounds(0, 0, 1200, 59);
        getContentPane().add(barra);

        JLabel lblTitulo = new JLabel("Centro de Ayuda");
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 26));
        lblTitulo.setBounds(40, 80, 400, 40);
        getContentPane().add(lblTitulo);

        // Secciones de ayuda
        int yPos = 140;
        getContentPane().add(crearPanelAyuda("Olvido de contraseña",
            "1. Haz clic en '¿Olvidaste tu contraseña?' en el login\n" +
            "2. Ingresa tu correo @ueuropea.es\n" +
            "3. Responde tus preguntas de seguridad\n" +
            "4. Establece una nueva contraseña", yPos));

        yPos += 130;
        getContentPane().add(crearPanelAyuda("Reportar incidencia", 
            "1. Accede a 'Crear Incidencia' desde el menú\n" +
            "2. Selecciona el tipo de incidencia\n" +
            "3. Describe el problema con detalles\n" +
            "4. Adjunta fotos si es necesario\n" +
            "5. Haz clic en 'Enviar'", yPos));

        yPos += 130;
        getContentPane().add(crearPanelAyuda("Registro de usuario",
            "1. Desde el login, pulsa 'Crear cuenta'\n" +
            "2. Usa tu correo @ueuropea.es\n" +
            "3. Establece una contraseña segura\n" +
            "4. Responde 2 preguntas de seguridad\n" +
            "5. Acepta los términos y condiciones", yPos));

        yPos += 130;
        getContentPane().add(crearPanelAyuda("Editar perfil",
            "1. Accede a tu perfil desde el menú\n" +
            "2. Modifica tu fecha de nacimiento\n" +
            "3. Actualiza tu campus\n" +
            "4. Cambia tu foto de perfil\n" +
            "5. Guarda los cambios", yPos));

        yPos += 130;
        getContentPane().add(crearPanelAyuda("Seguimiento de incidencias",
            "1. Ve a 'Mis Incidencias' en el menú\n" +
            "2. Filtra por estado (Pendiente/Resuelta)\n" +
            "3. Revisa los comentarios del administrador\n" +
            "4. Recibirás notificaciones por email", yPos));

        yPos += 130;
        getContentPane().add(crearPanelAyuda("Acceso administrador",
            "1. Inicia sesión con credenciales de admin\n" +
            "2. Usa el código de administrador\n" +
            "3. Accede al panel de control\n" +
            "4. Gestiona incidencias y usuarios", yPos));
    }

    private JPanel crearPanelAyuda(String titulo, String contenido, int y) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(245, 245, 245));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2.setColor(new Color(200, 200, 200));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
                g2.dispose();
            }
        };
        panel.setLayout(null);
        panel.setBounds(40, y, 1100, 110);
        panel.setOpaque(false);

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setBounds(20, 15, 800, 25);
        panel.add(lblTitulo);

        JTextArea txtContenido = new JTextArea(contenido);
        txtContenido.setBounds(20, 45, 900, 50);
        txtContenido.setEditable(false);
        txtContenido.setOpaque(false);
        txtContenido.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(txtContenido);

        RoundedButton btnVer = new RoundedButton("Ver más", 15, new Color(128, 0, 0));
        btnVer.setBounds(970, 40, 100, 30);
        btnVer.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, 
                "<html><div style='width:300px;'><h2>" + titulo + "</h2><p>" + 
                contenido.replace("\n", "<br>") + "</p></div></html>", 
                "Ayuda: " + titulo, JOptionPane.INFORMATION_MESSAGE);
        });
        panel.add(btnVer);

        return panel;
    }

    // Clase para botón redondeado (consistente con el diseño)
    static class RoundedButton extends JButton {
        private final int radius;
        private Color bgColor;
        
        public RoundedButton(String text, int radius, Color bgColor) {
            super(text);
            this.radius = radius;
            this.bgColor = bgColor;
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
            setForeground(Color.WHITE);
            setFont(new Font("Tahoma", Font.BOLD, 12));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(bgColor);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            super.paintComponent(g2);
        }

        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(bgColor.darker());
            g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, radius, radius);
        }
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