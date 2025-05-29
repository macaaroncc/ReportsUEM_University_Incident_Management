package vista;

import controlador.Controlador;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class _06_PaginaPrincipal extends JFrame {
	private JPanel contentPane;
	private JTable table;
	private JComboBox<String> comboBoxEstado, comboBoxOrden, comboBoxFecha;
	private JTextField campoBusqueda;
	private JButton botonBuscar, btnAyuda;
	private JScrollPane scrollPane;
	private Controlador controlador;

	public _06_PaginaPrincipal() {
		setTitle("Página Principal - Usuario");
		setSize(1200, 900);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		contentPane.setBackground(new Color(255, 255, 252));

		// Configurar bordes redondeados
		UIManager.put("Button.arc", 20);
		UIManager.put("TextComponent.arc", 15);
		UIManager.put("ComboBox.arc", 15);

		BarraNavegacion barra = new BarraNavegacion();
		barra.setUsuarioLogueado(true);
		barra.setControlador(controlador);
		barra.setBounds(0, 0, 1200, 59);
		contentPane.add(barra);

		comboBoxEstado = new JComboBox<>(new String[] { "Estado", "Pendiente", "Solucionada", "En revisión" });
		comboBoxEstado.setBounds(40, 70, 150, 30);
		contentPane.add(comboBoxEstado);

		comboBoxOrden = new JComboBox<>(
				new String[] { "Orden de Relevancia", "Más votaciones", "Menos votaciones", "Más reciente" });
		comboBoxOrden.setBounds(200, 70, 180, 30);
		contentPane.add(comboBoxOrden);

		comboBoxFecha = new JComboBox<>();
		comboBoxFecha.setBounds(390, 70, 150, 30);
		comboBoxFecha.addItem("Fecha");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		comboBoxFecha.addItem("Hoy - " + sdf.format(new Date()));
		for (int i = 1; i <= 6; i++) {
			Date date = new Date(System.currentTimeMillis() - (i * 24L * 60 * 60 * 1000));
			comboBoxFecha.addItem("Hace " + i + " día(s) - " + sdf.format(date));
		}
		contentPane.add(comboBoxFecha);

		// Campo de búsqueda redondeado
		campoBusqueda = new JTextField() {
			@Override
			protected void paintBorder(Graphics g) {
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(new Color(200, 200, 200));
				g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
				g2.dispose();
			}
		};
		campoBusqueda.setBounds(750, 70, 250, 30);
		campoBusqueda.setOpaque(false);
		campoBusqueda.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
		contentPane.add(campoBusqueda);

		// Botón Buscar redondeado
		botonBuscar = new JButton("Buscar") {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(getBackground());
				g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
				super.paintComponent(g2);
				g2.dispose();
			}
		};
		botonBuscar.setBounds(1010, 70, 138, 30);
		botonBuscar.setBackground(new Color(128, 0, 0));
		botonBuscar.setForeground(Color.WHITE);
		botonBuscar.setFont(new Font("Tahoma", Font.BOLD, 12));
		botonBuscar.setFocusPainted(false);
		botonBuscar.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
		botonBuscar.setContentAreaFilled(false);
		contentPane.add(botonBuscar);

		DefaultTableModel model = new DefaultTableModel() {
			public boolean isCellEditable(int row, int column) {
				return false;
			}

			public Class<?> getColumnClass(int columnIndex) {
				if (columnIndex == 7)
					return Date.class;
				if (columnIndex == 9)
					return Integer.class;
				return String.class;
			}
		};

		model.addColumn("ID");
		model.addColumn("Estado");
		model.addColumn("Edificio");
		model.addColumn("Piso");
		model.addColumn("Descripción");
		model.addColumn("Aula");
		model.addColumn("Justificación");
		model.addColumn("Fecha");
		model.addColumn("Campus");
		model.addColumn("Ranking");
		model.addColumn("Usuario");

		cargarIncidenciasDesdeBD(model);

		table = new JTable(model);
		table.setRowHeight(30);
		table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
		table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getColumnModel().getColumn(4).setPreferredWidth(250);
		table.getColumnModel().getColumn(6).setPreferredWidth(200);

		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int fila = table.getSelectedRow();
				if (fila != -1) {
					try {
						Object valorID = table.getValueAt(fila, 0);
						int idIncidencia = Integer.parseInt(valorID.toString().trim());
						new _17_DetalleIncidencia(idIncidencia).setVisible(true);
					} catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(null, "⚠ ID inválido: " + table.getValueAt(fila, 0));
						ex.printStackTrace();
					}
				}
			}
		});

		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(40, 120, 1110, 600);
		contentPane.add(scrollPane);

		// Botón Ayuda redondeado
		btnAyuda = new JButton("?") {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(getBackground());
				g2.fillOval(0, 0, getWidth(), getHeight());
				super.paintComponent(g2);
				g2.dispose();
			}
		};
		btnAyuda.setBounds(1120, 740, 50, 50);
		btnAyuda.setBackground(new Color(128, 0, 0));
		btnAyuda.setForeground(Color.WHITE);
		btnAyuda.setFont(new Font("Arial", Font.BOLD, 24));
		btnAyuda.setFocusPainted(false);
		btnAyuda.setBorder(BorderFactory.createEmptyBorder());
		btnAyuda.setContentAreaFilled(false);
		btnAyuda.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAyuda.addActionListener(e -> {
			if (controlador != null)
				controlador.abrirAyuda();
			dispose();
		});
		contentPane.add(btnAyuda);

		botonBuscar.addActionListener(e -> {
			DefaultTableModel m = (DefaultTableModel) table.getModel();
			cargarIncidenciasFiltradas(m);
		});
	}

	private void cargarIncidenciasDesdeBD(DefaultTableModel model) {
		try (Connection conexion = modelo.ConexionBD.conectar();
				Statement stmt = conexion.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM incidencias ORDER BY fecha DESC")) {

			model.setRowCount(0);
			while (rs.next()) {
				model.addRow(new Object[] { rs.getInt("id_incidencia"), rs.getString("estado"),
						rs.getString("edificio"), rs.getString("piso"), rs.getString("descripcion"),
						rs.getString("aula"), rs.getString("justificacion"), rs.getDate("fecha"),
						rs.getString("campus"), rs.getInt("ranking"), rs.getString("USR") });
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Error al cargar incidencias:\n" + e.getMessage(),
					"Error de base de datos", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	private void cargarIncidenciasFiltradas(DefaultTableModel model) {
		StringBuilder consulta = new StringBuilder("SELECT * FROM incidencias WHERE 1=1");

		String estado = comboBoxEstado.getSelectedItem().toString();
		if (!estado.equals("Estado")) {
			consulta.append(" AND estado = '").append(estado).append("'");
		}

		String fecha = comboBoxFecha.getSelectedItem().toString();
		if (!fecha.equals("Fecha") && !fecha.equals("Personalizado...")) {
			String[] partes = fecha.split(" - ");
			if (partes.length == 2) {
				consulta.append(" AND fecha = '").append(partes[1]).append("'");
			}
		}

		String busqueda = campoBusqueda.getText().trim();
		if (!busqueda.isEmpty()) {
			consulta.append(" AND (descripcion LIKE '%").append(busqueda).append("%' OR aula LIKE '%").append(busqueda)
					.append("%' OR edificio LIKE '%").append(busqueda).append("%')");
		}

		String orden = comboBoxOrden.getSelectedItem().toString();
		switch (orden) {
		case "Más votaciones":
			consulta.append(" ORDER BY ranking DESC");
			break;
		case "Menos votaciones":
			consulta.append(" ORDER BY ranking ASC");
			break;
		case "Más reciente":
			consulta.append(" ORDER BY fecha DESC");
			break;
		default:
			consulta.append(" ORDER BY fecha DESC");
			break;
		}

		try (Connection conexion = modelo.ConexionBD.conectar();
				Statement stmt = conexion.createStatement();
				ResultSet rs = stmt.executeQuery(consulta.toString())) {

			model.setRowCount(0);
			while (rs.next()) {
				model.addRow(new Object[] { rs.getInt("id_incidencia"), rs.getString("estado"),
						rs.getString("edificio"), rs.getString("piso"), rs.getString("descripcion"),
						rs.getString("aula"), rs.getString("justificacion"), rs.getDate("fecha"),
						rs.getString("campus"), rs.getInt("ranking"), rs.getString("USR") });
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Error al filtrar incidencias:\n" + e.getMessage(), "Error SQL",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public void setControlador(Controlador controlador) {
		this.controlador = controlador;
		for (Component c : getContentPane().getComponents()) {
			if (c instanceof BarraNavegacion) {
				((BarraNavegacion) c).setControlador(controlador);
			}
		}
	}
}