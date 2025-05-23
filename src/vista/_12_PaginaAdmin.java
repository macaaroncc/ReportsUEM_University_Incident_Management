package vista;

import controlador.Controlador;

import javax.swing.*;
import java.awt.*;

public class _12_PaginaAdmin extends JFrame {
	private Controlador controlador;
	private JTextArea txtDescripcion;
	private JTextField txtTitulo;
	private JComboBox<String> comboEstado;

	public _12_PaginaAdmin() {
		setTitle("12 . Panel de Administración");
		setSize(1200, 900);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		getContentPane().setBackground(new Color(255, 255, 252));
		getContentPane().setLayout(null);

		// ✅ Barra de navegación reutilizable
		BarraNavegacion barra = new BarraNavegacion();
		barra.setUsuarioLogueado(true); // Habilita enlaces funcionales
		barra.setControlador(controlador); // Asigna listeners y lógica
		barra.setBounds(0, 0, 1200, 59); // Asegura que se vea bien
		getContentPane().add(barra);

		// Etiqueta
		JLabel lblTitulo = new JLabel("Gestión de Incidencias");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblTitulo.setBounds(460, 80, 300, 30);
		getContentPane().add(lblTitulo);

		// Placeholder de imagen
		JLabel lblImagen = new JLabel("Imagen");
		lblImagen.setBounds(50, 130, 300, 300);
		lblImagen.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblImagen);

		// Campo de título
		txtTitulo = new JTextField("Título");
		txtTitulo.setBounds(380, 130, 300, 30);
		getContentPane().add(txtTitulo);

		// Campo de descripción
		txtDescripcion = new JTextArea("Descripción de la incidencia...");
		txtDescripcion.setLineWrap(true);
		txtDescripcion.setWrapStyleWord(true);
		JScrollPane scrollDescripcion = new JScrollPane(txtDescripcion);
		scrollDescripcion.setBounds(380, 170, 300, 120);
		getContentPane().add(scrollDescripcion);

		// Combo de estado
		comboEstado = new JComboBox<>(new String[] { "Estado", "Pendiente", "En proceso", "Resuelta" });
		comboEstado.setBounds(380, 310, 150, 30);
		getContentPane().add(comboEstado);

		// Botones de acción
		JButton btnEliminar = new JButton("Eliminar Incidencia");
		btnEliminar.setBounds(380, 360, 180, 30);
		getContentPane().add(btnEliminar);

		JButton btnActualizar = new JButton("Actualizar");
		btnActualizar.setBounds(570, 360, 110, 30);
		getContentPane().add(btnActualizar);

		// Botón ayuda flotante
		JButton btnAyuda = new JButton("?");
		btnAyuda.setBounds(1120, 750, 50, 50);
		btnAyuda.setBackground(new Color(128, 0, 0));
		btnAyuda.setForeground(Color.WHITE);
		btnAyuda.setFont(new Font("Arial", Font.BOLD, 20));
		btnAyuda.setFocusPainted(false);
		btnAyuda.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		getContentPane().add(btnAyuda);
		btnAyuda.addActionListener(e -> {
			if (controlador != null)
				controlador.abrirAyuda();
			dispose();
		});
	}

	public void setControlador(Controlador controlador) {
		this.controlador = controlador;
		Component[] components = getContentPane().getComponents();
		for (Component c : components) {
			if (c instanceof BarraNavegacion) {
				((BarraNavegacion) c).setControlador(controlador);
			}
		}
	}
}
