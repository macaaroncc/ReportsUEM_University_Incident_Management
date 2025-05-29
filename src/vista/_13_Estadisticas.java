package vista;

import controlador.Controlador;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class _13_Estadisticas extends JFrame {
	private Controlador controlador;

	public _13_Estadisticas(Controlador controlador) {
		this.controlador = controlador;
		setTitle("13 . Estadísticas - Admin");
		setSize(1200, 900);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		getContentPane().setBackground(new Color(255, 255, 252));
		getContentPane().setLayout(null);

		// ✅ Barra de navegación reutilizable
		BarraNavegacion barra = new BarraNavegacion();
		barra.setUsuarioLogueado(true);
		barra.setControlador(controlador);
		barra.setBounds(0, 0, 1200, 59);
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

		Map<String, Integer> datosPorMes = new LinkedHashMap<>();
		datosPorMes.put("2025-01", 5);
		datosPorMes.put("2025-02", 3);
		datosPorMes.put("2025-03", 8);

		Map<String, Integer> datosPorEstado = new LinkedHashMap<>();
		datosPorEstado.put("Pendiente", 4);
		datosPorEstado.put("Resuelto", 6);

		Map<String, Integer> datosPorEdificio = new LinkedHashMap<>();
		datosPorEdificio.put("Edificio A", 2);
		datosPorEdificio.put("Edificio B", 5);
		datosPorEdificio.put("Edificio C", 3);

		// --- GRÁFICOS ---
		JPanel grafico1 = crearPanelConTituloConIcono("Incidencias por mes", "/img/chart.png", 50, 140, 500, 250);
		grafico1.setLayout(null);
		BarChartPanel chart1 = new BarChartPanel(datosPorMes);
		chart1.setBounds(0, 0, 500, 250);
		grafico1.add(chart1);
		getContentPane().add(grafico1);

		JPanel grafico2 = crearPanelConTituloConIcono("Resueltas vs Pendientes", "/img/check.png", 600, 140, 500, 250);
		grafico2.setLayout(null);
		PieChartPanel chart2 = new PieChartPanel(datosPorEstado);
		chart2.setBounds(0, 0, 500, 250);
		grafico2.add(chart2);
		getContentPane().add(grafico2);

		JPanel grafico3 = crearPanelConTituloConIcono("Distribución por edificio", "/img/building.png", 50, 420, 1050, 250);
		grafico3.setLayout(null);
		PieChartPanel chart3 = new PieChartPanel(datosPorEdificio);
		chart3.setBounds(0, 0, 1050, 250);
		grafico3.add(chart3);
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

class BarChartPanel extends JPanel {
	private Map<String, Integer> data;

	public BarChartPanel(Map<String, Integer> data) {
		this.data = data;
		setPreferredSize(new Dimension(500, 250));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (data == null || data.isEmpty()) return;

		Graphics2D g2 = (Graphics2D) g;
		int width = getWidth();
		int height = getHeight();
		int padding = 40;

		int maxVal = data.values().stream().max(Integer::compare).orElse(1);
		int barWidth = (width - padding * 2) / data.size();

		int i = 0;
		for (Map.Entry<String, Integer> entry : data.entrySet()) {
			int barHeight = (int) ((entry.getValue() / (double) maxVal) * (height - 2 * padding));
			int x = padding + i * barWidth;
			int y = height - padding - barHeight;

			g2.setColor(new Color(100, 150, 240));
			g2.fillRect(x, y, barWidth - 10, barHeight);

			g2.setColor(Color.BLACK);
			g2.drawString(entry.getKey(), x + 5, height - 20);
			g2.drawString(String.valueOf(entry.getValue()), x + 5, y - 5);

			i++;
		}
	}
}

class PieChartPanel extends JPanel {
	private Map<String, Integer> data;

	public PieChartPanel(Map<String, Integer> data) {
		this.data = data;
		setPreferredSize(new Dimension(500, 250));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (data == null || data.isEmpty()) return;

		Graphics2D g2 = (Graphics2D) g;
		int width = getWidth();
		int height = getHeight();
		int diameter = Math.min(width, height) - 40;
		int x = (width - diameter) / 2;
		int y = (height - diameter) / 2;

		int total = data.values().stream().mapToInt(Integer::intValue).sum();
		int startAngle = 0;
		int i = 0;
		Color[] colors = {Color.BLUE, Color.RED, Color.GREEN, Color.ORANGE, Color.MAGENTA, Color.CYAN};

		for (Map.Entry<String, Integer> entry : data.entrySet()) {
			int arcAngle = (int) Math.round(entry.getValue() * 360.0 / total);
			g2.setColor(colors[i % colors.length]);
			g2.fillArc(x, y, diameter, diameter, startAngle, arcAngle);
			startAngle += arcAngle;
			i++;
		}
	}
}
