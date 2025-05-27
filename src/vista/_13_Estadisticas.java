package vista;

import controlador.Controlador;

import javax.swing.*;
import java.awt.*;

public class _13_Estadisticas extends JFrame {
	private Controlador controlador;

	public _13_Estadisticas() {
		setTitle("13 . Estadísticas - Admin");
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

		// --- FILTROS ---
		JLabel lblFiltros = new JLabel("Filtros:");
		lblFiltros.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblFiltros.setBounds(40, 80, 100, 30);
		getContentPane().add(lblFiltros);

		JComboBox<String> comboEstado = new JComboBox<>(new String[] { "Estado", "Pendiente", "Resuelto" });
		comboEstado.setBounds(100, 80, 150, 30);
		getContentPane().add(comboEstado);

		JComboBox<String> comboOrden = new JComboBox<>(
				new String[] { "Orden de Relevancia", "Más relevante", "Menos relevante" });
		comboOrden.setBounds(270, 80, 200, 30);
		getContentPane().add(comboOrden);

		JTextField txtFecha = new JTextField("YYYY-MM-DD");
		txtFecha.setBounds(480, 80, 150, 30);
		getContentPane().add(txtFecha);

		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(640, 80, 100, 30);
		btnBuscar.setBackground(new Color(128, 0, 0));
		btnBuscar.setForeground(Color.WHITE);
		btnBuscar.setFocusPainted(false);
		getContentPane().add(btnBuscar);
		

		// --- GRÁFICOS PLACEHOLDER ---
		
		JPanel grafico1 = crearPanelConTituloConIcono("Incidencias por mes", "/img/chart.png", 50, 140, 500, 250);
		getContentPane().add(grafico1);

		JPanel grafico2 = crearPanelConTituloConIcono("Resueltas vs Pendientes", "/img/check.png", 600, 140, 500, 250);
		getContentPane().add(grafico2);

		JPanel grafico3 = crearPanelConTituloConIcono("Distribución por edificio", "/img/building.png", 50, 420, 1050, 250);
		getContentPane().add(grafico3);

		// --- Botón ayuda flotante ---
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
	private JPanel crearPanelConTituloConIcono(String texto, String iconPath, int x, int y, int width, int height) {
	    JPanel panel = new JPanel();
	    panel.setLayout(null);
	    panel.setBounds(x, y, width, height);
	    panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

	    // 加载图标
	    ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
	    Image scaledImage = icon.getImage().getScaledInstance(22, 22, Image.SCALE_SMOOTH);
	    ImageIcon scaledIcon = new ImageIcon(scaledImage);

	    JLabel lblTitulo = new JLabel(texto, scaledIcon, JLabel.LEFT);
	    lblTitulo.setFont(new Font("Dialog", Font.BOLD, 15));
	    lblTitulo.setBounds(10, 10, width - 20, 30);
	    lblTitulo.setIconTextGap(10);

	    panel.add(lblTitulo);
	    return panel;
	}


	private JPanel crearPanelConTitulo(String titulo, int x, int y, int w, int h) {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createTitledBorder(titulo));
		panel.setBounds(x, y, w, h);
		panel.setBackground(new Color(240, 240, 240));
		return panel;
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
