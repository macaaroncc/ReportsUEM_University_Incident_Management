package vista;

import controlador.Controlador;
import modelo.Modelo;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class _05_RestContrasena extends JFrame {
    private Controlador controlador;
    private String usuario;
    private String origen;

    // Clases internas para componentes redondeados (consistentes con las otras pantallas)
    static class RoundedButton extends JButton {
        private final int radius;
        private Color backgroundColor;
        private Color borderColor;

        public RoundedButton(String text, int radius, Color backgroundColor, Color borderColor) {
            super(text);
            this.radius = radius;
            this.backgroundColor = backgroundColor;
            this.borderColor = borderColor;
            setContentAreaFilled(false);
            setFocusPainted(false);
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
            setForeground(Color.BLACK);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(backgroundColor);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            super.paintComponent(g2);
            g2.dispose();
        }

        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(borderColor);
            g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, radius, radius);
            g2.dispose();
        }
    }

    static class RoundedPasswordField extends JPasswordField {
        private final int radius;

        public RoundedPasswordField(int radius) {
            super();
            this.radius = radius;
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground() != null ? getBackground() : Color.WHITE);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            super.paintComponent(g2);
            g2.dispose();
        }

        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.GRAY);
            g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, radius, radius);
            g2.dispose();
        }
    }

    static class RoundedPanel extends JPanel {
        private final int radius;

        public RoundedPanel(int radius) {
            super(null);
            this.radius = radius;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            super.paintComponent(g2);
            g2.dispose();
        }

        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.BLACK);
            g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, radius, radius);
            g2.dispose();
        }
    }

    public _05_RestContrasena(String origen) {
        this.origen = origen;

        if (origen.equals("perfil")) {
            usuario = Modelo.usuarioActual;
        }

        setTitle("05. Restablecer contraseña");
        setSize(1200, 900);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Fondo con imagen
        JLabel backgroundLabel = new JLabel(new ImageIcon(_02_Login.class.getResource("/img/fondo.jpg")));
        backgroundLabel.setLayout(new BorderLayout());
        setContentPane(backgroundLabel);

        // Panel transparente encima del fondo
        JPanel outerPanel = new JPanel(null);
        outerPanel.setOpaque(false);
        backgroundLabel.add(outerPanel, BorderLayout.CENTER);

        // Panel tarjeta con esquinas redondeadas
        RoundedPanel cardPanel = new RoundedPanel(25);
        cardPanel.setBounds(420, 200, 360, 320);
        cardPanel.setBackground(new Color(255, 255, 252));
        outerPanel.add(cardPanel);

        // Botón Atrás redondeado
        RoundedButton btnAtras = new RoundedButton("◀ Atrás", 15, new Color(255, 255, 252), Color.BLACK);
        btnAtras.setBounds(10, 11, 90, 30);
        btnAtras.addActionListener(e -> {
            if (origen.equals("login")) {
                _04_OlvContrasena rest = new _04_OlvContrasena(origen);
                rest.setControlador(controlador);
                rest.setVisible(true);
            } else if (origen.equals("perfil")) {
                _10_PerfilUsuario perfil = new _10_PerfilUsuario();
                perfil.setControlador(controlador);
                perfil.setVisible(true);
            }
            dispose();
        });
        outerPanel.add(btnAtras);

        // Título
        JLabel lblTitulo = new JLabel("Restablecer contraseña", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setBounds(30, 20, 300, 30);
        cardPanel.add(lblTitulo);

        // Campo Nueva contraseña
        JLabel lblNueva = new JLabel("Nueva contraseña:");
        lblNueva.setBounds(40, 70, 200, 20);
        cardPanel.add(lblNueva);

        RoundedPasswordField txtNueva = new RoundedPasswordField(15);
        txtNueva.setBounds(40, 90, 280, 35);
        txtNueva.setBackground(Color.WHITE);
        txtNueva.setEchoChar('•');
        cardPanel.add(txtNueva);

        // Campo Repetir contraseña
        JLabel lblRepetir = new JLabel("Repita la contraseña:");
        lblRepetir.setBounds(40, 130, 200, 20);
        cardPanel.add(lblRepetir);

        RoundedPasswordField txtRepetir = new RoundedPasswordField(15);
        txtRepetir.setBounds(40, 150, 280, 35);
        txtRepetir.setBackground(Color.WHITE);
        txtRepetir.setEchoChar('•');
        cardPanel.add(txtRepetir);

        // Botón Restablecer redondeado
        RoundedButton btnRestablecer = new RoundedButton("Restablecer", 20, new Color(128, 0, 0), new Color(128, 0, 0));
        btnRestablecer.setForeground(Color.WHITE);
        btnRestablecer.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnRestablecer.setBounds(60, 210, 240, 40);
        cardPanel.add(btnRestablecer);

        btnRestablecer.addActionListener(e -> {
            String nueva = new String(txtNueva.getPassword());
            String repetir = new String(txtRepetir.getPassword());

            if (nueva.isEmpty() || repetir.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, rellene ambos campos.", "Campos vacíos",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!nueva.equals(repetir)) {
                JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (usuario != null && controlador != null) {
                controlador.restablecerContrasena(usuario, nueva, this);
            } else {
                JOptionPane.showMessageDialog(this, "Error interno: usuario no definido.");
            }
        });

        // KeyAdapter para restablecer con Enter
        KeyAdapter enterRestablecer = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    btnRestablecer.doClick();
                }
            }
        };

        txtNueva.addKeyListener(enterRestablecer);
        txtRepetir.addKeyListener(enterRestablecer);
    }

    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}