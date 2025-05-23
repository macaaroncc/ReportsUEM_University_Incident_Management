package vista;

import controlador.Controlador;

import javax.swing.*;
import java.awt.*;

public class _10_PerfilUsuario extends JFrame {
	private Controlador controlador;
	private JTextField txtNombre, txtfecha, txtCampus;

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

		barra.btnAtras.addActionListener(e -> {
			if (controlador != null)
				controlador.volverAtras(this);
			dispose();
		});

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

		JLabel lblFoto = new JLabel("Foto");
		lblFoto.setBounds(40, 40, 180, 180);
		lblFoto.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		lblFoto.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblFoto);

		JButton btnEditarFoto = new JButton("Editar Foto");
		btnEditarFoto.setBounds(60, 230, 140, 30);
		panel.add(btnEditarFoto);

		int labelX = 250, fieldX = 370, rowHeight = 40, y = 40;

		JLabel lblNombre = new JLabel("Correo electrónico:");
		lblNombre.setBounds(labelX, y, 150, 30);
		panel.add(lblNombre);

		txtNombre = new JTextField();
		txtNombre.setBounds(fieldX, y, 300, 30);
		txtNombre.setEditable(false);  // Solo lectura
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
		btnGuardar.setBounds(fieldX, y + 60, 300, 40);
		btnGuardar.setBackground(new Color(34, 139, 34));
		btnGuardar.setForeground(Color.WHITE);
		btnGuardar.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel.add(btnGuardar);

		btnGuardar.addActionListener(e -> {
			if (controlador != null) {
				String fecha = txtfecha.getText();
				String campus = txtCampus.getText();
				controlador.actualizarPerfilUsuario(fecha, campus);
				JOptionPane.showMessageDialog(this, "Perfil actualizado correctamente.");
			}
		});

		JButton btnCambiarContrasena = new JButton("Cambiar Contraseña");
		btnCambiarContrasena.setBounds(fieldX, y + 110, 300, 40);
		btnCambiarContrasena.setBackground(Color.GRAY);
		btnCambiarContrasena.setForeground(Color.WHITE);
		btnCambiarContrasena.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel.add(btnCambiarContrasena);
		
		JButton btnCerrarSesion = new JButton("Cerrar Sesión");
		btnCerrarSesion.setBounds(fieldX, y + 160, 300, 40);
		btnCerrarSesion.setBackground(new Color(178, 34, 34));
		btnCerrarSesion.setForeground(Color.WHITE);
		btnCerrarSesion.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel.add(btnCerrarSesion);

		btnCerrarSesion.addActionListener(e -> {
			int confirm = JOptionPane.showConfirmDialog(this,
					"¿Seguro que deseas cerrar sesión?", "Confirmar cierre de sesión",
					JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				if (controlador != null) {
					controlador.cerrarSesion();  // Abrirá el login
				}
				dispose(); // Cierra esta ventana
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

		if (controlador != null && controlador.getUsuarioActual() != null) {
			// Obtener datos del perfil: fecha, campus, email
			String[] datos = controlador.obtenerDatosPerfil(); // Asegúrate de que devuelve 3 elementos
			txtfecha.setText(datos[0]);        // Fecha nacimiento
			txtCampus.setText(datos[1]);       // Campus
			txtNombre.setText(datos[2]);       // Correo electrónico
		}
	}
}
