//@autor Aaron y Chen

package vista;

import controlador.Controlador;
import modelo.Modelo;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class _05_RestContrasena extends JFrame {
	private Controlador controlador;
	private String usuario;

	private String origen;

	public _05_RestContrasena(String origen) {
		this.origen = origen;
		
		if (origen.equals("perfil")) {
			usuario = Modelo.usuarioActual;
		}
			
		
		
		setTitle("05. Restablecer contraseña");
		setSize(1200, 900);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// fondo
		JLabel backgroundLabel = new JLabel(new ImageIcon(_02_Login.class.getResource("/img/fondo.jpg")));
		backgroundLabel.setLayout(new BorderLayout());
		setContentPane(backgroundLabel);

		JPanel outerPanel = new JPanel(null);
		outerPanel.setOpaque(false);
		getContentPane().add(outerPanel, BorderLayout.CENTER);

		// Panel tipo "tarjeta"
		JPanel cardPanel = new JPanel(null);
		cardPanel.setBounds(420, 200, 360, 320);
		cardPanel.setBackground(new Color(255, 255, 252, 230));
		cardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

		// Botón Atrás
		JButton btnAtras = new JButton("◀ Atrás");
		btnAtras.setForeground(Color.BLACK);
		btnAtras.setBackground(new Color(255, 255, 252));
		btnAtras.setFocusPainted(false);
		btnAtras.setBounds(10, 11, 90, 30);
		outerPanel.add(btnAtras);
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

		// Título
		JLabel lblTitulo = new JLabel("Restablecer contraseña", SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
		lblTitulo.setBounds(30, 20, 300, 30);
		cardPanel.add(lblTitulo);

		// Campo Nueva contraseña
		JLabel lblNueva = new JLabel("Nueva contraseña:");
		lblNueva.setBounds(40, 70, 200, 20);
		cardPanel.add(lblNueva);

		JPasswordField txtNueva = new JPasswordField();
		txtNueva.setBounds(40, 90, 280, 30);
		cardPanel.add(txtNueva);

		// Campo Repetir contraseña
		JLabel lblRepetir = new JLabel("Repita la contraseña:");
		lblRepetir.setBounds(40, 130, 200, 20);
		cardPanel.add(lblRepetir);

		JPasswordField txtRepetir = new JPasswordField();
		txtRepetir.setBounds(40, 150, 280, 30);
		cardPanel.add(txtRepetir);

		// Botón Restablecer
		JButton btnRestablecer = new JButton("Restablecer");
		btnRestablecer.setForeground(Color.WHITE);
		btnRestablecer.setBackground(new Color(128, 0, 0));
		btnRestablecer.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnRestablecer.setFocusPainted(false);
		btnRestablecer.setBounds(60, 210, 240, 35);
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

		outerPanel.add(cardPanel);
	}

	public void setControlador(Controlador controlador) {
		this.controlador = controlador;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
}
