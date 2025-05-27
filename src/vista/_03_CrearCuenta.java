// @Autor Beatriz

package vista;

import controlador.Controlador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class _03_CrearCuenta extends JFrame {
	private Controlador controlador;
	private JPanel outerPanel;
	private JButton btnAtras;
	private JPanel cardPanel;
	private JLabel lblTitulo;
	private JLabel lblEmail;
	private JTextField txtEmail;
	private JLabel lblPwd;
	private JPasswordField txtPwd;
	private JLabel lblRepPwd;
	private JPasswordField txtRepPwd;
	private JComboBox<String> pregunta1;
	private JTextField respuesta1;
	private JComboBox<String> pregunta2;
	private JTextField respuesta2;
	private JCheckBox checkEdad;
	private JCheckBox checkCaptcha;
	private JCheckBox checkAdmin;
	private JLabel lblAdmin;
	private JTextField txtCodigo;
	private JButton btnCrear;

	public _03_CrearCuenta() {
		setTitle("03 . Crear cuenta");
		setSize(1200, 900);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		JLabel backgroundLabel = new JLabel(new ImageIcon(_02_Login.class.getResource("/img/fondo.jpg")));
		backgroundLabel.setLayout(new BorderLayout());
		setContentPane(backgroundLabel);

		getContentPane().setLayout(new BorderLayout());
		outerPanel = new JPanel(null);
		outerPanel.setOpaque(false);

		cardPanel = new JPanel(null);
		cardPanel.setBounds(420, 100, 360, 620);
		cardPanel.setBackground(new Color(255, 255, 252, 230));
		cardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

		btnAtras = new JButton("◀ Atrás");
		btnAtras.setForeground(Color.BLACK);
		btnAtras.setBackground(new Color(255, 255, 252));
		btnAtras.setFocusPainted(false);
		btnAtras.setBounds(10, 11, 90, 30);
		outerPanel.add(btnAtras);
		btnAtras.addActionListener(e -> {
			if (controlador != null) {
				controlador.abrirLogin();
			}
			_03_CrearCuenta.this.dispose();
		});

		lblTitulo = new JLabel("Crear Cuenta", SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
		lblTitulo.setBounds(80, 20, 200, 30);
		cardPanel.add(lblTitulo);

		int x = 40;
		int width = 280;
		int alturaCampo = 30;
		int y = 70;
		int espacio = 40;

		lblEmail = new JLabel("Email:");
		lblEmail.setBounds(x, y, width, 20);
		cardPanel.add(lblEmail);

		txtEmail = new JTextField();
		txtEmail.setBounds(x, y += 20, width, alturaCampo);
		cardPanel.add(txtEmail);

		lblPwd = new JLabel("Contraseña:");
		lblPwd.setBounds(x, y += espacio, width, 20);
		cardPanel.add(lblPwd);

		txtPwd = new JPasswordField();
		txtPwd.setBounds(x, y += 20, width, alturaCampo);
		cardPanel.add(txtPwd);

		lblRepPwd = new JLabel("Repetir contraseña:");
		lblRepPwd.setBounds(x, y += espacio, width, 20);
		cardPanel.add(lblRepPwd);

		txtRepPwd = new JPasswordField();
		txtRepPwd.setBounds(x, y += 20, width, alturaCampo);
		cardPanel.add(txtRepPwd);

		pregunta1 = new JComboBox<>(
				new String[] { "¿Nombre de tu primera mascota?", "¿Ciudad donde naciste?", "¿Comida favorita?" });
		pregunta1.setBounds(x, y += espacio + 10, width, alturaCampo);
		cardPanel.add(pregunta1);

		respuesta1 = new JTextField("Su respuesta");
		respuesta1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				respuesta1.setText("");
			}
		});
		respuesta1.setBounds(x, y += espacio, width, alturaCampo);
		cardPanel.add(respuesta1);

		pregunta2 = new JComboBox<>(
				new String[] { "¿Nombre de tu mejor amigo?", "¿Color favorito?", "¿Nombre de tu escuela primaria?" });
		pregunta2.setBounds(x, y += espacio, width, alturaCampo);
		cardPanel.add(pregunta2);

		respuesta2 = new JTextField("Su respuesta");
		respuesta2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				respuesta2.setText("");
			}
		});
		respuesta2.setBounds(x, y += espacio, width, alturaCampo);
		cardPanel.add(respuesta2);

		checkEdad = new JCheckBox("Soy mayor de 16 años");
		checkEdad.setBounds(x, y += espacio, 180, 30);
		cardPanel.add(checkEdad);

		checkCaptcha = new JCheckBox("CAPTCHA");
		checkCaptcha.setBounds(x + 180, y, 120, 30);
		cardPanel.add(checkCaptcha);

		checkAdmin = new JCheckBox("Soy administrador");
		checkAdmin.setBounds(x, y += espacio, 180, 30);
		cardPanel.add(checkAdmin);

		lblAdmin = new JLabel("Código Admin:");
		lblAdmin.setBounds(x, y += espacio, 100, 20);
		lblAdmin.setVisible(false); // Invisible al inicio
		cardPanel.add(lblAdmin);

		txtCodigo = new JTextField();
		txtCodigo.setBounds(x + 100, y, 100, alturaCampo);
		txtCodigo.setVisible(false); // Invisible al inicio
		cardPanel.add(txtCodigo);

		checkAdmin.addActionListener(e -> {
			boolean isSelected = checkAdmin.isSelected();
			lblAdmin.setVisible(isSelected);
			txtCodigo.setVisible(isSelected);

			if (!isSelected) {
				txtCodigo.setText("");
			}
		});

		btnCrear = new JButton("Crear Cuenta");
		btnCrear.setForeground(Color.WHITE);
		btnCrear.setBackground(new Color(128, 0, 0));
		btnCrear.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnCrear.setFocusPainted(false);
		btnCrear.setBounds(x + 20, y + 50, 240, 35);
		cardPanel.add(btnCrear);

		// Lógica al pulsar "Crear Cuenta"
		btnCrear.addActionListener(e -> {
			String email = txtEmail.getText().trim();
			String pwd = new String(txtPwd.getPassword());
			String repPwd = new String(txtRepPwd.getPassword());
			String codigo = txtCodigo.getText().trim();

			int preg1 = pregunta1.getSelectedIndex() + 1;
			int preg2 = pregunta2.getSelectedIndex() + 1;

			String resp1 = respuesta1.getText().trim();
			String resp2 = respuesta2.getText().trim();

			if (!email.endsWith("@ueuropea.es")) {
				JOptionPane.showMessageDialog(this,
						"Solo se admiten correos proporcionados por la universidad (@ueuropea.es)", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			// Nueva validación para mínimo 8 caracteres en contraseña
			if (pwd.length() < 8 || repPwd.length() < 8) {
				JOptionPane.showMessageDialog(this, "La contraseña debe tener al menos 8 caracteres", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (!pwd.equals(repPwd)) {
				JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (!checkEdad.isSelected()) {
				JOptionPane.showMessageDialog(this, "Debes ser mayor de 16 años", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (!checkCaptcha.isSelected()) {
				JOptionPane.showMessageDialog(this, "Debes marcar el CAPTCHA", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			controlador.registrarUsuario(email, pwd, codigo, preg1, preg2, resp1, resp2, this);
		});

		outerPanel.add(cardPanel);
		getContentPane().add(outerPanel, BorderLayout.CENTER);
	}

	public void setControlador(Controlador controlador) {
		this.controlador = controlador;
	}
}
