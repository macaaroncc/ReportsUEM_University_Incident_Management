package vista;

import controlador.Controlador;

import javax.swing.*;
import java.awt.*;

public class _08_CrearIncidencia extends JFrame {
	private Controlador controlador;

	private JTextField txtFoto;
	private JTextField txtAula;
	private JTextField txtFecha;
	private JTextField txtRanking;
	private JTextArea txtDescripcion;
	private JComboBox<String> comboCampus, comboEdificio, comboPiso, comboEstado;

	public _08_CrearIncidencia() {
		setTitle("08 . Crear Incidencia");
		setSize(1200, 900);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		getContentPane().setBackground(new Color(255, 255, 252));

		// Barra de navegación
		BarraNavegacion barra = new BarraNavegacion();
		barra.setUsuarioLogueado(true);
		barra.setControlador(controlador);
		barra.setBounds(0, 0, 1200, 59);
		getContentPane().add(barra);

		JLabel lblTitulo = new JLabel("Crear incidencia");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblTitulo.setBounds(100, 80, 300, 30);
		getContentPane().add(lblTitulo);

		// Estado
		JLabel lblEstado = new JLabel("Estado:");
		lblEstado.setBounds(100, 130, 100, 25);
		getContentPane().add(lblEstado);

		comboEstado = new JComboBox<>(new String[] { "Pendiente", "En Progreso", "Resuelta" });
		comboEstado.setBounds(200, 130, 150, 30);
		getContentPane().add(comboEstado);

		// Edificio
		JLabel lblEdificio = new JLabel("Edificio:");
		lblEdificio.setBounds(100, 180, 100, 25);
		getContentPane().add(lblEdificio);

		comboEdificio = new JComboBox<>(new String[] { "Edificio A", "Edificio B", "Edificio C" });
		comboEdificio.setBounds(200, 180, 150, 30);
		getContentPane().add(comboEdificio);

		// Piso
		JLabel lblPiso = new JLabel("Piso:");
		lblPiso.setBounds(100, 230, 100, 25);
		getContentPane().add(lblPiso);

		comboPiso = new JComboBox<>(new String[] { "1", "2", "3", "4", "5" });
		comboPiso.setBounds(200, 230, 150, 30);
		getContentPane().add(comboPiso);

		// Foto
		JLabel lblFoto = new JLabel("Foto (URL o path):");
		lblFoto.setBounds(100, 280, 150, 25);
		getContentPane().add(lblFoto);

		txtFoto = new JTextField();
		txtFoto.setBounds(250, 280, 300, 30);
		getContentPane().add(txtFoto);

		// Aula
		JLabel lblAula = new JLabel("Aula:");
		lblAula.setBounds(100, 330, 100, 25);
		getContentPane().add(lblAula);

		txtAula = new JTextField();
		txtAula.setBounds(200, 330, 150, 30);
		getContentPane().add(txtAula);

		// Fecha (formato YYYY-MM-DD)
		JLabel lblFecha = new JLabel("Fecha (YYYY-MM-DD):");
		lblFecha.setBounds(100, 380, 150, 25);
		getContentPane().add(lblFecha);

		txtFecha = new JTextField();
		txtFecha.setBounds(250, 380, 150, 30);
		getContentPane().add(txtFecha);

		// Campus
		JLabel lblCampus = new JLabel("Campus:");
		lblCampus.setBounds(100, 430, 100, 25);
		getContentPane().add(lblCampus);

		comboCampus = new JComboBox<>(new String[] { "Campus 1", "Campus 2", "Campus 3" });
		comboCampus.setBounds(200, 430, 150, 30);
		getContentPane().add(comboCampus);

		// Ranking
		JLabel lblRanking = new JLabel("Ranking:");
		lblRanking.setBounds(100, 480, 100, 25);
		getContentPane().add(lblRanking);

		txtRanking = new JTextField();
		txtRanking.setBounds(200, 480, 100, 30);
		getContentPane().add(txtRanking);

		// Descripción
		JLabel lblDescripcion = new JLabel("Descripción:");
		lblDescripcion.setBounds(100, 530, 100, 25);
		getContentPane().add(lblDescripcion);

		txtDescripcion = new JTextArea();
		txtDescripcion.setLineWrap(true);
		txtDescripcion.setWrapStyleWord(true);
		txtDescripcion.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		JScrollPane scrollDescripcion = new JScrollPane(txtDescripcion);
		scrollDescripcion.setBounds(200, 530, 400, 150);
		getContentPane().add(scrollDescripcion);

		// Botón Crear Incidencia
		JButton btnCrear = new JButton("Crear Incidencia");
		btnCrear.setBounds(200, 700, 400, 45);
		btnCrear.setBackground(new Color(128, 0, 0));
		btnCrear.setForeground(Color.WHITE);
		btnCrear.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnCrear.setFocusPainted(false);
		getContentPane().add(btnCrear);

		btnCrear.addActionListener(e -> {
			if (controlador != null) {
				// Enviar datos para crear incidencia
				controlador.crearIncidencia(
					comboEstado.getSelectedItem().toString(),
					comboEdificio.getSelectedItem().toString(),
					txtFoto.getText(),
					comboPiso.getSelectedItem().toString(),
					txtDescripcion.getText(),
					txtAula.getText(),
					txtFecha.getText(),
					comboCampus.getSelectedItem().toString(),
					txtRanking.getText()
				);
			}
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
