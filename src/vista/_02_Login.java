//@autor Haowen

package vista;

import controlador.Controlador;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;

public class _02_Login extends JFrame {
	private JTextField txtEmail;
	private JPasswordField txtPassword;
	private Controlador controlador;
	private JButton btnConfiguracion;
	private JPanel cardPanel;
	private JButton btnAtras;
	private JPanel outerPanel;
	private JLabel backgroundLabel;

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
		cardPanel = new JPanel(null);
		cardPanel.setBounds(420, 180, 360, 440);
		cardPanel.setBackground(new Color(255, 255, 252));
		cardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		// Banner

		// Banner
		try {

			// Cargar imagen desde recursos
			Image logo = ImageIO.read(getClass().getResource("/img/Logo6.png"));
			JLabel logoLabel = new JLabel(new ImageIcon(logo));
			logoLabel.setBackground(new Color(255, 255, 252));

			logoLabel.setBounds(80, -89, 210, 334);

			cardPanel.add(logoLabel);
			cardPanel.repaint();
		} catch (IOException e) {
			e.printStackTrace();
		}

		JLabel lblTitulo = new JLabel("Login", SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
		lblTitulo.setBounds(80, 150, 200, 30);
		cardPanel.add(lblTitulo);

		// Email
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(40, 190, 100, 20);
		cardPanel.add(lblEmail);

		txtEmail = new JTextField();
		txtEmail.setBounds(40, 215, 280, 30);
		cardPanel.add(txtEmail);

		// Contraseña
		JLabel lblPassword = new JLabel("Contraseña:");
		lblPassword.setBounds(40, 255, 100, 20);
		cardPanel.add(lblPassword);

		txtPassword = new JPasswordField();
		txtPassword.setBounds(40, 280, 280, 30);
		txtPassword.setEchoChar('•'); // Asegurar que el carácter de ocultación esté activado
		cardPanel.add(txtPassword);

		// Botón mostrar/ocultar contraseña
		JButton btnMostrarPwd = new JButton("👁");
		btnMostrarPwd.setBounds(325, 280, 30, 30);
		btnMostrarPwd.setFocusable(false);
		btnMostrarPwd.setMargin(new Insets(0, 0, 0, 0));
		btnMostrarPwd.setFont(new Font("Dialog", Font.PLAIN, 10));
		cardPanel.add(btnMostrarPwd);

		final boolean[] pwdVisible = { false };
		btnMostrarPwd.addActionListener(e -> {
			if (pwdVisible[0]) {
				txtPassword.setEchoChar('•'); // Ocultar
			} else {
				txtPassword.setEchoChar((char) 0); // Mostrar
			}
			pwdVisible[0] = !pwdVisible[0];
		});

		// Botón Login
		JButton btnLogin = new JButton("Iniciar Sesión");
		btnLogin.setForeground(Color.WHITE);
		btnLogin.setBackground(new Color(128, 0, 0));
		btnLogin.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnLogin.setBounds(60, 330, 240, 35);
		btnLogin.setFocusPainted(false);
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
		crearCuenta.setBounds(200, 380, 140, 20);
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
		olvidoPwd.setBounds(40, 380, 160, 20);
		cardPanel.add(olvidoPwd);
		olvidoPwd.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				_04_OlvContrasena vista = new _04_OlvContrasena();
				vista.setControlador(controlador);
				vista.setVisible(true);
				_02_Login.this.dispose();
			}
		});

		// Botón atrás
		btnAtras = new JButton("◀ Atrás");
		btnAtras.setForeground(Color.BLACK);
		btnAtras.setBackground(new Color(255, 255, 252));
		btnAtras.setBounds(10, 11, 90, 30);
		btnAtras.setFocusPainted(false);
		outerPanel.add(btnAtras);
		btnAtras.addActionListener(e -> {
			if (controlador != null) {
				controlador.volverAtras(this);
			}
			_02_Login.this.dispose();
		});

		outerPanel.add(cardPanel);
		getContentPane().add(outerPanel, BorderLayout.CENTER);

		// Botón Configuración
		btnConfiguracion = new JButton("Configuración");
		btnConfiguracion.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnConfiguracion.setForeground(Color.BLACK);
		btnConfiguracion.setBackground(new Color(255, 255, 255));
		btnConfiguracion.setBounds(1064, 11, 106, 30);
		btnConfiguracion.setFocusPainted(false);
		outerPanel.add(btnConfiguracion);

		btnConfiguracion.addActionListener(e -> {
			new Configuracion(); // ✅ Abrir la ventana de configuración
		});

	}

	public void setControlador(Controlador controlador) {
		this.controlador = controlador;
	}

	public String getEmail() {
		return txtEmail.getText().trim();
	}

	public String getPwd() {
		return new String(txtPassword.getPassword());
	}

	public void actualizar(String resultado) {
		// Puedes usar este método más adelante si quieres mostrar info
	}
}
