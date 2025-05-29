//@autor chen
package vista;

import controlador.Controlador;
import modelo.Modelo;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.awt.image.BufferedImage;

public class _10_PerfilUsuario extends JFrame {
	private Controlador controlador;
	private JTextField txtNombre, txtfecha, txtCampus;
	private byte[] imagenBytes = null;
	private JLabel lblFoto;

	public _10_PerfilUsuario() {
		setTitle("10 . Perfil de Usuario");
		setSize(1200, 900);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		getContentPane().setBackground(new Color(255, 255, 252));

		// ✅ Barra de navegación
		BarraNavegacion barra = new BarraNavegacion();
		barra.setUsuarioLogueado(true);
		barra.setBounds(0, 0, 1200, 59);
		getContentPane().add(barra);

		// 📋 Título
		JLabel lblTitulo = new JLabel("Perfil de usuario", SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblTitulo.setBounds(0, 80, 1200, 30);
		getContentPane().add(lblTitulo);

		JPanel panel = new JPanel(null);
		panel.setBounds(150, 140, 900, 500);
		panel.setBackground(new Color(245, 245, 245));
		panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		getContentPane().add(panel);

		lblFoto = new JLabel("Foto");
		lblFoto.setBounds(40, 40, 182, 181);
		lblFoto.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		lblFoto.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblFoto);

		JButton btnEditarFoto = new JButton("Editar Foto");
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
						Image imagenEscalada = imagen.getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(), Image.SCALE_SMOOTH);
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

		JLabel lblNombre = new JLabel("Correo electrónico:");
		lblNombre.setBounds(labelX, y, 150, 30);
		panel.add(lblNombre);

		txtNombre = new JTextField();
		txtNombre.setBounds(fieldX, y, 300, 30);
		txtNombre.setEditable(false);
		panel.add(txtNombre);

		y += rowHeight;
		JLabel lblfecha = new JLabel("Fecha Nacimiento:");
		lblfecha.setBounds(labelX, y, 150, 30);
		panel.add(lblfecha);

		txtfecha = new JTextField();
		txtfecha.setBounds(fieldX, y, 300, 30);
		panel.add(txtfecha);

		y += rowHeight;
		JLabel lblCampus = new JLabel("Campus:");
		lblCampus.setBounds(labelX, y, 150, 30);
		panel.add(lblCampus);

		txtCampus = new JTextField();
		txtCampus.setBounds(fieldX, y, 300, 30);
		panel.add(txtCampus);

		JButton btnGuardar = new JButton("Guardar Cambios");
		btnGuardar.setBounds(590, 449, 300, 40);
		btnGuardar.setBackground(new Color(34, 139, 34));
		btnGuardar.setForeground(Color.WHITE);
		btnGuardar.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel.add(btnGuardar);

		btnGuardar.addActionListener(e -> {
			if (controlador != null) {
				String fecha = txtfecha.getText();
				String campus = txtCampus.getText();
				controlador.actualizarPerfilUsuario(fecha, campus, imagenBytes); // 👈 pasa imagenBytes
				JOptionPane.showMessageDialog(this, "Perfil actualizado correctamente.");
			}
		});

		JButton btnCambiarContrasena = new JButton("Cambiar Contraseña");
		btnCambiarContrasena.setBounds(10, 449, 300, 40);
		btnCambiarContrasena.setBackground(Color.GRAY);
		btnCambiarContrasena.setForeground(Color.WHITE);
		btnCambiarContrasena.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel.add(btnCambiarContrasena);
		btnCambiarContrasena.addActionListener(e -> {
			if (controlador != null) {
				controlador.abrirRestContrasena("perfil");
			}
			dispose();
		});

		JButton btnCerrarSesion = new JButton("Cerrar Sesión");
		btnCerrarSesion.setBounds(590, 398, 300, 40);
		btnCerrarSesion.setBackground(new Color(178, 34, 34));
		btnCerrarSesion.setForeground(Color.WHITE);
		btnCerrarSesion.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel.add(btnCerrarSesion);

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

		JButton btnAyuda = new JButton("?");
		btnAyuda.setBounds(1120, 750, 50, 50);
		btnAyuda.setBackground(new Color(128, 0, 0));
		btnAyuda.setForeground(Color.WHITE);
		btnAyuda.setFont(new Font("Arial", Font.BOLD, 20));
		btnAyuda.setFocusPainted(false);
		getContentPane().add(btnAyuda);
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

			// ✅ Cargar y mostrar foto del usuario
			imagenBytes = Modelo.obtenerFotoUsuario();
			if (imagenBytes != null && imagenBytes.length > 0) {
				try {
					ByteArrayInputStream bis = new ByteArrayInputStream(imagenBytes);
					BufferedImage img = ImageIO.read(bis);
					if (img != null) {
						Image imagenEscalada = img.getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(), Image.SCALE_SMOOTH);
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
