package vista;

import controlador.Controlador;
import modelo.Modelo;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.awt.image.BufferedImage;

public class _10_PerfilUsuario extends JFrame {
	private Controlador controlador;
	private JTextField txtNombre, txtfecha, txtCampus;
	private byte[] imagenBytes = null;
	private JLabel lblFoto;

	// Clase para botones redondeados
	static class RoundedButton extends JButton {
		private final int radius;
		private Color bgColor;

		public RoundedButton(String text, int radius, Color bgColor) {
			super(text);
			this.radius = radius;
			this.bgColor = bgColor;
			setContentAreaFilled(false);
			setFocusPainted(false);
			setBorder(new EmptyBorder(5, 15, 5, 15));
			setForeground(Color.WHITE);
			setFont(new Font("Tahoma", Font.BOLD, 14));
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
			g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
		}
	}

	// Clase para campos de texto redondeados
	static class RoundedTextField extends JTextField {
		private final int radius;

		public RoundedTextField(int radius) {
			super();
			this.radius = radius;
			setOpaque(false);
			setBorder(new EmptyBorder(5, 15, 5, 15));
		}

		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setColor(getBackground());
			g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
			super.paintComponent(g2);
		}

		@Override
		protected void paintBorder(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setColor(Color.GRAY);
			g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
		}
	}

	public _10_PerfilUsuario() {
		setTitle("10 . Perfil de Usuario");
		setSize(1200, 900);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		getContentPane().setBackground(new Color(255, 255, 252));

		// Barra de navegación (sin cambios)
		BarraNavegacion barra = new BarraNavegacion();
		barra.setUsuarioLogueado(true);
		barra.setBounds(0, 0, 1200, 59);
		getContentPane().add(barra);

		// Título
		JLabel lblTitulo = new JLabel("Perfil de usuario", SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblTitulo.setBounds(0, 80, 1200, 30);
		getContentPane().add(lblTitulo);

		// Panel principal con sombra sutil
		JPanel panel = new JPanel(null) {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(new Color(230, 230, 230));
				g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
				g2.setColor(new Color(200, 200, 200));
				g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 25, 25);
				g2.dispose();
			}
		};
		panel.setBounds(150, 140, 900, 500);
		panel.setOpaque(false);
		getContentPane().add(panel);

		// Foto de perfil con borde redondeado
		lblFoto = new JLabel("Foto") {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(Color.WHITE);
				g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
				super.paintComponent(g2);
				g2.setColor(Color.GRAY);
				g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
				g2.dispose();
			}
		};
		lblFoto.setBounds(40, 40, 182, 181);
		lblFoto.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblFoto);

		// Botón Editar Foto redondeado
		RoundedButton btnEditarFoto = new RoundedButton("Editar Foto", 15, new Color(128, 0, 0));
		btnEditarFoto.setBounds(60, 232, 140, 30);
		panel.add(btnEditarFoto);

		btnEditarFoto.addActionListener(e -> {
			JFileChooser chooser = new JFileChooser();
			int result = chooser.showOpenDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {
				File archivo = chooser.getSelectedFile();
				try {
					imagenBytes = java.nio.file.Files.readAllBytes(archivo.toPath());
					BufferedImage imagen = ImageIO.read(archivo);
					if (imagen != null) {
						Image imagenEscalada = imagen.getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(),
								Image.SCALE_SMOOTH);
						lblFoto.setText("");
						lblFoto.setIcon(new ImageIcon(imagenEscalada));
					} else {
						lblFoto.setIcon(null);
						JOptionPane.showMessageDialog(this, "El archivo seleccionado no es una imagen válida.");
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(this, "Error al leer la imagen: " + ex.getMessage());
				}
			}
		});

		int labelX = 250, fieldX = 370, rowHeight = 40, y = 40;

		// Campos de texto redondeados
		JLabel lblNombre = new JLabel("Correo electrónico:");
		lblNombre.setBounds(labelX, y, 150, 30);
		panel.add(lblNombre);

		txtNombre = new RoundedTextField(15);
		txtNombre.setBounds(fieldX, y, 300, 30);
		txtNombre.setBackground(Color.WHITE);
		txtNombre.setEditable(false);
		panel.add(txtNombre);

		y += rowHeight;
		JLabel lblfecha = new JLabel("Fecha Nacimiento:");
		lblfecha.setBounds(labelX, y, 150, 30);
		panel.add(lblfecha);

		txtfecha = new RoundedTextField(15);
		txtfecha.setBounds(fieldX, y, 300, 30);
		txtfecha.setBackground(Color.WHITE);
		panel.add(txtfecha);

		y += rowHeight;
		JLabel lblCampus = new JLabel("Campus:");
		lblCampus.setBounds(labelX, y, 150, 30);
		panel.add(lblCampus);

		txtCampus = new RoundedTextField(15);
		txtCampus.setBounds(fieldX, y, 300, 30);
		txtCampus.setBackground(Color.WHITE);
		panel.add(txtCampus);

		// Botones principales con estilo redondeado
		RoundedButton btnGuardar = new RoundedButton("Guardar Cambios", 20, new Color(0, 100, 0)); // Verde oscuro
		btnGuardar.setBounds(590, 449, 300, 40);
		panel.add(btnGuardar);

		RoundedButton btnCambiarContrasena = new RoundedButton("Cambiar Contraseña", 20, new Color(128, 0, 0)); // Rojo
																												// vino
		btnCambiarContrasena.setBounds(10, 449, 300, 40);
		panel.add(btnCambiarContrasena);

		RoundedButton btnCerrarSesion = new RoundedButton("Cerrar Sesión", 20, new Color(128, 0, 0)); // Rojo más claro
		btnCerrarSesion.setBounds(590, 398, 300, 40);
		panel.add(btnCerrarSesion);

		RoundedButton btnAyuda = new RoundedButton("?", 50, new Color(128, 0, 0)); // Rojo vino
		btnAyuda.setBounds(1120, 750, 50, 50);
		btnAyuda.setFont(new Font("Arial", Font.BOLD, 20));
		getContentPane().add(btnAyuda);

		// Listeners (mantenidos exactamente igual)
		btnGuardar.addActionListener(e -> {
			if (controlador != null) {
				String fecha = txtfecha.getText();
				String campus = txtCampus.getText();
				controlador.actualizarPerfilUsuario(fecha, campus, imagenBytes);
				JOptionPane.showMessageDialog(this, "Perfil actualizado correctamente.");
			}
		});

		btnCambiarContrasena.addActionListener(e -> {
			if (controlador != null) {
				controlador.abrirRestContrasena("perfil");
			}
			dispose();
		});

		btnCerrarSesion.addActionListener(e -> {
			int confirm = JOptionPane.showConfirmDialog(this, "¿Seguro que deseas cerrar sesión?",
					"Confirmar cierre de sesión", JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				if (controlador != null) {
					controlador.cerrarSesion();
				}
				dispose();
			}
		});

		btnAyuda.addActionListener(e -> {
			if (controlador != null)
				controlador.abrirAyuda();
			dispose();
		});
	}

	public void setControlador(Controlador controlador) {
		this.controlador = controlador;

		for (Component c : getContentPane().getComponents()) {
			if (c instanceof BarraNavegacion) {
				((BarraNavegacion) c).setControlador(controlador);
			}
		}

		if (controlador != null && Modelo.usuarioActual != null) {
			String[] datos = controlador.obtenerDatosPerfil();
			txtfecha.setText(datos[0]);
			txtCampus.setText(datos[1]);
			txtNombre.setText(datos[2]);

			imagenBytes = Modelo.obtenerFotoUsuario();
			if (imagenBytes != null && imagenBytes.length > 0) {
				try {
					ByteArrayInputStream bis = new ByteArrayInputStream(imagenBytes);
					BufferedImage img = ImageIO.read(bis);
					if (img != null) {
						Image imagenEscalada = img.getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(),
								Image.SCALE_SMOOTH);
						lblFoto.setText("");
						lblFoto.setIcon(new ImageIcon(imagenEscalada));
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(this, "Error al cargar la imagen del perfil.");
				}
			}
		}
	}
}