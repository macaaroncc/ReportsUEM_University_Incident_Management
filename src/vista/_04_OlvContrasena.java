package vista;

import controlador.Controlador;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class _04_OlvContrasena extends JFrame {
    private Controlador controlador;
    private String origen;
    private String usuario;
    
    private RoundedTextField txtCorreo;
    private JLabel lblPregunta1, lblPregunta2;
    private RoundedTextField txtRespuesta1, txtRespuesta2;
    private RoundedButton btnValidarCorreo, btnComprobar;
    private RoundedPanel cardPanel;
    private JPanel outerPanel;
    private JLabel backgroundLabel;

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

    static class RoundedTextField extends JTextField {
        private final int radius;

        public RoundedTextField(int radius) {
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

    public _04_OlvContrasena(String origen) {
        this.origen = origen;
        setTitle("04 - Olvido de contraseña");
        setSize(1200, 900);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Fondo con imagen
        backgroundLabel = new JLabel(new ImageIcon(_02_Login.class.getResource("/img/fondo.jpg")));
        backgroundLabel.setLayout(new BorderLayout());
        setContentPane(backgroundLabel);

        // Panel transparente encima del fondo
        outerPanel = new JPanel(null);
        outerPanel.setOpaque(false);
        backgroundLabel.add(outerPanel, BorderLayout.CENTER);

        // Botón Atrás redondeado
        RoundedButton btnAtras = new RoundedButton("◀ Atrás", 15, new Color(255, 255, 252), Color.BLACK);
        btnAtras.setBounds(10, 10, 90, 30);
        btnAtras.addActionListener(e -> {
            if ("login".equals(origen)) {
                _02_Login login = new _02_Login();
                login.setControlador(controlador);
                login.setVisible(true);
            } else if ("perfil".equals(origen)) {
                _10_PerfilUsuario perfil = new _10_PerfilUsuario();
                perfil.setControlador(controlador);
                perfil.setVisible(true);
            }
            dispose();
        });
        outerPanel.add(btnAtras);

        // Panel tarjeta con esquinas redondeadas
        cardPanel = new RoundedPanel(25);
        cardPanel.setBounds(400, 150, 400, 500); // Aumentado un poco la altura
        cardPanel.setBackground(new Color(255, 255, 252));
        outerPanel.add(cardPanel);

        // Título
        JLabel lblTitulo = new JLabel("Recuperar contraseña", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setBounds(50, 30, 300, 30);
        cardPanel.add(lblTitulo);

        // Label y campo correo
        JLabel lblCorreo = new JLabel("Correo electrónico:");
        lblCorreo.setBounds(40, 80, 320, 25);
        cardPanel.add(lblCorreo);

        txtCorreo = new RoundedTextField(15);
        txtCorreo.setBounds(40, 110, 320, 35);
        txtCorreo.setBackground(Color.WHITE);
        cardPanel.add(txtCorreo);

        // Botón validar correo
        btnValidarCorreo = new RoundedButton("Validar Correo", 20, new Color(128, 0, 0), new Color(128, 0, 0));
        btnValidarCorreo.setForeground(Color.WHITE);
        btnValidarCorreo.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnValidarCorreo.setBounds(40, 160, 320, 40);
        cardPanel.add(btnValidarCorreo);

        // Preguntas seguridad (ocultas inicialmente)
        lblPregunta1 = new JLabel("");
        lblPregunta1.setBounds(40, 220, 320, 25);
        lblPregunta1.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblPregunta1.setVisible(false);
        cardPanel.add(lblPregunta1);

        txtRespuesta1 = new RoundedTextField(15);
        txtRespuesta1.setBounds(40, 250, 320, 35);
        txtRespuesta1.setBackground(Color.WHITE);
        txtRespuesta1.setVisible(false);
        cardPanel.add(txtRespuesta1);

        lblPregunta2 = new JLabel("");
        lblPregunta2.setBounds(40, 300, 320, 25);
        lblPregunta2.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblPregunta2.setVisible(false);
        cardPanel.add(lblPregunta2);

        txtRespuesta2 = new RoundedTextField(15);
        txtRespuesta2.setBounds(40, 330, 320, 35);
        txtRespuesta2.setBackground(Color.WHITE);
        txtRespuesta2.setVisible(false);
        cardPanel.add(txtRespuesta2);

        // Botón comprobar respuestas (oculto inicialmente)
        btnComprobar = new RoundedButton("Comprobar Respuestas", 20, new Color(128, 0, 0), new Color(128, 0, 0));
        btnComprobar.setForeground(Color.WHITE);
        btnComprobar.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnComprobar.setBounds(40, 390, 320, 40);
        btnComprobar.setVisible(false);
        cardPanel.add(btnComprobar);

        // Acciones botones
        btnValidarCorreo.addActionListener(e -> {
            String email = txtCorreo.getText().trim();
            if (email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, introduce tu correo electrónico.");
                return;
            }
            controlador.obtenerPreguntasSeguridad(email, lblPregunta1, lblPregunta2, txtRespuesta1, txtRespuesta2,
                    btnComprobar);
        });

        btnComprobar.addActionListener(e -> {
            String email = txtCorreo.getText().trim();
            String resp1 = txtRespuesta1.getText().trim();
            String resp2 = txtRespuesta2.getText().trim();
            if (resp1.isEmpty() || resp2.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, responde ambas preguntas.");
                return;
            }
            controlador.comprobarPreguntasSeguridad(email, resp1, resp2, this, origen);
        });

        // KeyAdapter para validar correo con Enter
        KeyAdapter enterValidarCorreo = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER && btnValidarCorreo.isEnabled()) {
                    btnValidarCorreo.doClick();
                }
            }
        };

        // KeyAdapter para comprobar respuestas con Enter
        KeyAdapter enterComprobarRespuestas = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER && btnComprobar.isEnabled()) {
                    btnComprobar.doClick();
                }
            }
        };

        // Añadir key listeners
        txtCorreo.addKeyListener(enterValidarCorreo);
        txtRespuesta1.addKeyListener(enterComprobarRespuestas);
        txtRespuesta2.addKeyListener(enterComprobarRespuestas);
    }

    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }

    public String getOrigen() {
        return origen;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}