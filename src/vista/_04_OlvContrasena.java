//@autor Aaron

package vista;

import controlador.Controlador;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class _04_OlvContrasena extends JFrame {
	private Controlador controlador;

	public _04_OlvContrasena() {
		setTitle("04 - Olvido de contraseña");
		setSize(1200, 900);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Fondo
		JLabel backgroundLabel = new JLabel(new ImageIcon(_02_Login.class.getResource("/img/fondo.jpg")));
		backgroundLabel.setLayout(new BorderLayout());
		setContentPane(backgroundLabel);

		JPanel outerPanel = new JPanel(null);
		outerPanel.setOpaque(false);
		getContentPane().add(outerPanel, BorderLayout.CENTER);

		// Panel tipo tarjeta
		JPanel cardPanel = new JPanel(null);
		cardPanel.setBounds(400, 180, 400, 370);
		cardPanel.setBackground(new Color(255, 255, 252, 230));
		cardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

		// Botón Atrás
		JButton btnAtras = new JButton("◀ Atrás");
		btnAtras.setForeground(Color.BLACK);
		btnAtras.setBackground(new Color(255, 255, 252));
		btnAtras.setFocusPainted(false);
		btnAtras.setBounds(10, 11, 90, 30);
		btnAtras.addActionListener(e -> {
			if (controlador != null) {
				_02_Login login = new _02_Login();
				login.setControlador(controlador);
				login.setVisible(true);
			}
			dispose();
		});

		outerPanel.add(btnAtras);

		// Título
		JLabel lblTitulo = new JLabel("Restablecer contraseña", SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
		lblTitulo.setBounds(50, 20, 300, 30);
		cardPanel.add(lblTitulo);

		// Correo
		JLabel lblCorreo = new JLabel("Correo electrónico:");
		lblCorreo.setBounds(40, 65, 300, 20);
		cardPanel.add(lblCorreo);

		JTextField txtCorreo = new JTextField();
		txtCorreo.setBounds(40, 85, 320, 30);
		cardPanel.add(txtCorreo);

		// Pregunta 1
		JLabel lblPregunta1 = new JLabel("Pregunta de seguridad 1:");
		lblPregunta1.setBounds(40, 125, 300, 20);
		cardPanel.add(lblPregunta1);

		JTextField txtRespuesta1 = new JTextField();
		txtRespuesta1.setBounds(40, 145, 320, 30);
		cardPanel.add(txtRespuesta1);

		// Pregunta 2
		JLabel lblPregunta2 = new JLabel("Pregunta de seguridad 2:");
		lblPregunta2.setBounds(40, 185, 300, 20);
		cardPanel.add(lblPregunta2);

		JTextField txtRespuesta2 = new JTextField();
		txtRespuesta2.setBounds(40, 205, 320, 30);
		cardPanel.add(txtRespuesta2);

		// Botón comprobar
		JButton btnComprobar = new JButton("Comprobar");
		btnComprobar.setForeground(Color.WHITE);
		btnComprobar.setBackground(new Color(217, 2, 2));
		btnComprobar.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnComprobar.setFocusPainted(false);
		btnComprobar.setBounds(80, 270, 240, 35);
		cardPanel.add(btnComprobar);

		btnComprobar.addActionListener(e -> {
			String email = txtCorreo.getText().trim();
			String respuesta1 = txtRespuesta1.getText().trim();
			String respuesta2 = txtRespuesta2.getText().trim();

			if (email.isEmpty() || respuesta1.isEmpty() || respuesta2.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Completa todos los campos.");
				return;
			}

			controlador.comprobarPreguntasSeguridad(email, respuesta1, respuesta2, this);
		});

		outerPanel.add(cardPanel);
	}

	public void setControlador(Controlador controlador) {
		this.controlador = controlador;
	}
}
