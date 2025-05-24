//@autor Haowen

package vista;

import controlador.Controlador;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class _02_Login extends JFrame {
	private JTextField txtEmail;
	private JPasswordField txtPassword;
	private Controlador controlador;
	private RoundedButton btnConfiguracion;
	private JPanel cardPanel;
	private RoundedButton btnAtras;
	private JPanel outerPanel;
	private JLabel backgroundLabel;

	// Clase interna para botones redondeados con fondo sólo dentro del borde
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
			setBorder(new EmptyBorder(5, 15, 5, 15)); // Padding interno
			setForeground(Color.BLACK);
		}

		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			// Pintar fondo redondeado sólo dentro del botón
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

	// Clase interna para JTextField con bordes redondeados
	static class RoundedTextField extends JTextField {
		private final int radius;

		public RoundedTextField(int radius) {
			super();
			this.radius = radius;
			setOpaque(false);
			setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Padding interno
		}

		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			// Fondo blanco redondeado
			g2.setColor(getBackground() != null ? getBackground() : Color.WHITE);
			g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

			super.paintComponent(g2);
			g2.dispose();
		}

		@Override
		protected void paintBorder(Graphics g) {
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			g2.setColor(getForeground());
			g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);

			g2.dispose();
		}
	}

	// Clase interna para JPasswordField con bordes redondeados
	static class RoundedPasswordField extends JPasswordField {
		private final int radius;

		public RoundedPasswordField(int radius) {
			super();
			this.radius = radius;
			setOpaque(false);
			setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Padding interno
		}

		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			// Fondo blanco redondeado
			g2.setColor(getBackground() != null ? getBackground() : Color.WHITE);
			g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

			super.paintComponent(g2);
			g2.dispose();
		}

		@Override
		protected void paintBorder(Graphics g) {
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			g2.setColor(getForeground());
			g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);

			g2.dispose();
		}
	}

	// Clase interna para JPanel con esquinas redondeadas
	static class RoundedPanel extends JPanel {
		private final int radius;

		public RoundedPanel(int radius) {
			super(null); // Usamos null layout como antes
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
			g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
			g2.dispose();
		}
	}

	public _02_Login() {
		setTitle("02 . Login");
		setSize(1200, 900);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Fondo
		backgroundLabel = new JLabel(new ImageIcon(_02_Login.class.getResource("/img/fondo.jpg")));
		backgroundLabel.setLayout(new BorderLayout());
		setContentPane(backgroundLabel);

		getContentPane().setLayout(new BorderLayout());
		outerPanel = new JPanel(null);
		outerPanel.setOpaque(false);

		// Panel de login (tarjeta)
		cardPanel = new RoundedPanel(25); // Puedes ajustar el radio como prefieras

		cardPanel.setBounds(420, 180, 360, 440);
		cardPanel.setBackground(new Color(255, 255, 252));
		cardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

		// Banner
		try {
			Image logo = ImageIO.read(getClass().getResource("/img/Logo6.png"));
			JLabel logoLabel = new JLabel(new ImageIcon(logo));
			logoLabel.setBackground(new Color(255, 255, 252));
			logoLabel.setBounds(100, 11, 160, 132);
			cardPanel.add(logoLabel);
			cardPanel.repaint();
		} catch (IOException e) {
			e.printStackTrace();
		}

		JLabel lblTitulo = new JLabel("Inicio de Sesión", SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
		lblTitulo.setBounds(86, 149, 200, 30);
		cardPanel.add(lblTitulo);

		// Email
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(40, 190, 100, 20);
		cardPanel.add(lblEmail);

		txtEmail = new RoundedTextField(15); // <--- Aquí el JTextField redondeado
		txtEmail.setBounds(40, 215, 280, 30);
		cardPanel.add(txtEmail);

		// Contraseña
		JLabel lblPassword = new JLabel("Contraseña:");
		lblPassword.setBounds(40, 255, 100, 20);
		cardPanel.add(lblPassword);

		txtPassword = new RoundedPasswordField(15); // <--- Aquí el JPasswordField redondeado
		txtPassword.setBounds(40, 280, 280, 30);
		txtPassword.setEchoChar('•');
		cardPanel.add(txtPassword);

		// Botón mostrar/ocultar contraseña
		JButton btnMostrarPwd = new JButton("👁");
		btnMostrarPwd.setBounds(325, 280, 30, 30);
		btnMostrarPwd.setFocusable(false);
		btnMostrarPwd.setBackground(new Color(255, 255, 252));
		btnMostrarPwd.setMargin(new Insets(0, 0, 0, 0));
		btnMostrarPwd.setFont(new Font("Dialog", Font.PLAIN, 10));
		cardPanel.add(btnMostrarPwd);

		final boolean[] pwdVisible = { false };
		btnMostrarPwd.addActionListener(e -> {
			if (pwdVisible[0]) {
				txtPassword.setEchoChar('•');
			} else {
				txtPassword.setEchoChar((char) 0);
			}
			pwdVisible[0] = !pwdVisible[0];
		});

		// Botón Login - redondeado
		RoundedButton btnLogin = new RoundedButton("Iniciar Sesión", 20, new Color(128, 0, 0), new Color(128, 0, 0));
		btnLogin.setForeground(Color.WHITE);
		btnLogin.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnLogin.setBounds(40, 330, 280, 35);
		btnLogin.setEnabled(false);
		cardPanel.add(btnLogin);

		// Listener para habilitar botón solo si hay texto
		KeyAdapter activarBoton = new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				boolean habilitar = !txtEmail.getText().isEmpty() && txtPassword.getPassword().length > 0;
				btnLogin.setEnabled(habilitar);
			}
		};
		txtEmail.addKeyListener(activarBoton);
		txtPassword.addKeyListener(activarBoton);

		btnLogin.addActionListener(e -> {
			String email = txtEmail.getText();
			String password = new String(txtPassword.getPassword());
			controlador.validarLogin(email, password, _02_Login.this);
		});

		// Crear cuenta
		JLabel crearCuenta = new JLabel("<HTML><U>Crear cuenta aquí</U></HTML>");
		crearCuenta.setForeground(Color.BLUE);
		crearCuenta.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		crearCuenta.setBounds(210, 380, 122, 20);
		cardPanel.add(crearCuenta);
		crearCuenta.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				_03_CrearCuenta crear = new _03_CrearCuenta();
				crear.setControlador(controlador);
				controlador.setVista(null, crear);
				crear.setVisible(true);
				_02_Login.this.dispose();
			}
		});

		// Olvidó contraseña
		JLabel olvidoPwd = new JLabel("<HTML><U>¿Olvidó su contraseña?</U></HTML>");
		olvidoPwd.setForeground(Color.BLUE);
		olvidoPwd.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		olvidoPwd.setBounds(50, 380, 160, 20);
		cardPanel.add(olvidoPwd);
		olvidoPwd.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				_04_OlvContrasena vista = new _04_OlvContrasena();
				vista.setControlador(controlador);
				vista.setVisible(true);
				_02_Login.this.dispose();
			}
		});

		// Botón atrás - redondeado
		btnAtras = new RoundedButton("◀ Atrás", 15, new Color(255, 255, 252), Color.BLACK);
		btnAtras.setBounds(10, 11, 90, 30);
		outerPanel.add(btnAtras);
		btnAtras.addActionListener(e -> {
			if (controlador != null) {
				controlador.volverAtras(this);
			}
			_02_Login.this.dispose();
		});

		outerPanel.add(cardPanel);

		// Botón Configuración - redondeado
		btnConfiguracion = new RoundedButton("Configuración", 15, new Color(255, 255, 255), Color.BLACK);
		btnConfiguracion.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnConfiguracion.setBounds(1064, 11, 106, 30);
		outerPanel.add(btnConfiguracion);
		btnConfiguracion.addActionListener(e -> {
			new Configuracion();
		});

		getContentPane().add(outerPanel, BorderLayout.CENTER);
	}

	public void setControlador(Controlador controlador) {
		this.controlador = controlador;
	}

}
