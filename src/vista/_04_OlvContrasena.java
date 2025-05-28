package vista;

import controlador.Controlador;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class _04_OlvContrasena extends JFrame {
	private Controlador controlador;
	private String origen;
	private String usuario;

	private JTextField txtCorreo;
	private JLabel lblPregunta1, lblPregunta2;
	private JTextField txtRespuesta1, txtRespuesta2;
	private JButton btnValidarCorreo, btnComprobar;
	private JPanel cardPanel, outerPanel;
	private JLabel backgroundLabel;

	// Botón redondeado igual que en Login
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
			setBorder(new EmptyBorder(5, 15, 5, 15));
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
			g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
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

		// Botón Atrás
		RoundedButton btnAtras = new RoundedButton("◀ Atrás", 15, new Color(255, 255, 252), Color.BLACK);
		btnAtras.setBounds(10, 10, 90, 30);
		btnAtras.setFocusPainted(false);
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
		cardPanel = new JPanel(null);
		cardPanel.setBounds(400, 150, 400, 440);
		cardPanel.setBackground(new Color(255, 255, 252)); // blanco casi puro, opaco
		cardPanel.setOpaque(true);
		cardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		outerPanel.add(cardPanel);

		// Título
		JLabel lblTitulo = new JLabel("Recuperar contraseña", SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
		lblTitulo.setBounds(50, 20, 300, 30);
		cardPanel.add(lblTitulo);

		// Label y campo correo
		JLabel lblCorreo = new JLabel("Correo electrónico:");
		lblCorreo.setBounds(40, 70, 300, 25);
		cardPanel.add(lblCorreo);

		txtCorreo = new JTextField();
		txtCorreo.setBounds(40, 100, 320, 35);
		cardPanel.add(txtCorreo);

		// Botón validar correo
		btnValidarCorreo = new RoundedButton("Validar Correo", 20, new Color(128, 0, 0), new Color(128, 0, 0));
		btnValidarCorreo.setForeground(Color.WHITE);
		btnValidarCorreo.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnValidarCorreo.setFocusPainted(false);
		btnValidarCorreo.setBounds(40, 150, 320, 40);
		cardPanel.add(btnValidarCorreo);

		// Preguntas seguridad (ocultas inicialmente)
		lblPregunta1 = new JLabel("");
		lblPregunta1.setBounds(40, 210, 320, 25);
		lblPregunta1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPregunta1.setVisible(false);
		cardPanel.add(lblPregunta1);

		txtRespuesta1 = new JTextField();
		txtRespuesta1.setBounds(40, 240, 320, 35);
		txtRespuesta1.setVisible(false);
		cardPanel.add(txtRespuesta1);

		lblPregunta2 = new JLabel("");
		lblPregunta2.setBounds(40, 290, 320, 25);
		lblPregunta2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPregunta2.setVisible(false);
		cardPanel.add(lblPregunta2);

		txtRespuesta2 = new JTextField();
		txtRespuesta2.setBounds(40, 320, 320, 35);
		txtRespuesta2.setVisible(false);
		cardPanel.add(txtRespuesta2);

		// Botón comprobar respuestas (oculto inicialmente)
		btnComprobar = new RoundedButton("Comprobar Respuestas", 20, new Color(128, 0, 0), new Color(128, 0, 0));
		btnComprobar.setForeground(Color.WHITE);
		btnComprobar.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnComprobar.setFocusPainted(false);
		btnComprobar.setBounds(40, 380, 320, 40);
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