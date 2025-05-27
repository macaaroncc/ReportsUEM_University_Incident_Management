//@autor: aaron

package vista;

import controlador.Controlador;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase _01_PGSinLogin. Representa la clase _01_PGSinLogin.
 */
public class _01_PGSinLogin extends JFrame {

	private Controlador controlador;
	private JTable table;
	private JComboBox<String> comboBoxEstado, comboBoxOrden, comboBoxFecha;
	private JTextField campoBusqueda;
	private JButton botonBuscar, btnAyuda;
	private JScrollPane scrollPane;
	

	public _01_PGSinLogin() {
		setTitle("01 . Página Principal sin login");
		setSize(1200, 900);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		getContentPane().setBackground(new Color(255, 255, 252));

		JPanel barra = new JPanel(null);
		barra.setBackground(new Color(128, 0, 0));
		barra.setBounds(0, 0, 1200, 59);
		getContentPane().add(barra);

		Font fuente = new Font("Tahoma", Font.BOLD, 13);

		JLabel lblPGNPrincipal = crearNavLabel("Página Principal", 20, fuente);
		JLabel lblMisIncidencias = crearNavLabel("Mis Incidencias", 180, fuente);
		JLabel lblNotificaciones = crearNavLabel("Notificaciones", 350, fuente);
		JLabel lblUsuario = crearNavLabel("Usuario", 1109, fuente);

		barra.add(lblPGNPrincipal);
		barra.add(lblMisIncidencias);
		barra.add(lblNotificaciones);
		barra.add(lblUsuario);

		MouseAdapter abrirLogin = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (controlador != null)
					controlador.abrirLogin();
				dispose();
			}
		};

		lblPGNPrincipal.addMouseListener(abrirLogin);
		lblMisIncidencias.addMouseListener(abrirLogin);
		lblNotificaciones.addMouseListener(abrirLogin);
		lblUsuario.addMouseListener(abrirLogin);

		comboBoxEstado = new JComboBox<>(new String[] { "Estado", "Pendiente", "Solucionada", "En revisión"  });
		comboBoxEstado.setBounds(40, 70, 150, 30);
		getContentPane().add(comboBoxEstado);

		comboBoxOrden = new JComboBox<>(new String[] { "Orden de Relevancia", "Más relevante primero",
				"Menos relevante primero", "Más reciente primero" });
		comboBoxOrden.setBounds(200, 70, 180, 30);
		getContentPane().add(comboBoxOrden);

		comboBoxFecha = new JComboBox<>();
		comboBoxFecha.setBounds(390, 70, 150, 30);
		comboBoxFecha.addItem("Fecha");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		comboBoxFecha.addItem("Hoy - " + sdf.format(new Date()));
		for (int i = 1; i <= 6; i++) {
			Date date = new Date(System.currentTimeMillis() - (i * 24L * 60 * 60 * 1000));
			comboBoxFecha.addItem("Hace " + i + " día(s) - " + sdf.format(date));
		}
		comboBoxFecha.addItem("Personalizado...");
		getContentPane().add(comboBoxFecha);

		campoBusqueda = new JTextField();
		campoBusqueda.setBounds(750, 70, 250, 30);
		getContentPane().add(campoBusqueda);

		botonBuscar = new JButton("Buscar");
		botonBuscar.setBounds(1010, 70, 138, 30);
		botonBuscar.setBackground(new Color(128, 0, 0));
		botonBuscar.setForeground(Color.WHITE);
		botonBuscar.setFont(new Font("Tahoma", Font.BOLD, 12));
		botonBuscar.setFocusPainted(false);
		getContentPane().add(botonBuscar);

		DefaultTableModel model = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				if (columnIndex == 6)
					return Date.class;
				if (columnIndex == 8)
					return Integer.class;
				return String.class;
			}
		};

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
		table.getColumnModel().getColumn(3).setPreferredWidth(200);
		table.getColumnModel().getColumn(5).setPreferredWidth(150);

		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(40, 120, 1110, 600);
		getContentPane().add(scrollPane);

		btnAyuda = new JButton("?");
		btnAyuda.setBounds(1120, 740, 50, 50);
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

		// 🔍 Acción del botón Buscar con filtros
		botonBuscar.addActionListener(e -> {
			DefaultTableModel m = (DefaultTableModel) table.getModel();
			cargarIncidenciasFiltradas(m);
		});
	}

	public void setControlador(Controlador controlador) {
		this.controlador = controlador;
	}

	private void cargarIncidenciasDesdeBD(DefaultTableModel model) {
		java.sql.Connection conexion = null;
		java.sql.Statement stmt = null;
		java.sql.ResultSet rs = null;

		try {
			conexion = modelo.ConexionBD.conectar();
			String consulta = "SELECT estado, edificio, piso, descripcion, aula, justificacion, fecha, campus, ranking, USR FROM incidencias";
			stmt = conexion.createStatement();
			rs = stmt.executeQuery(consulta);
			model.setRowCount(0);

			while (rs.next()) {
				model.addRow(new Object[] { rs.getString("estado"), rs.getString("edificio"), rs.getString("piso"),
						rs.getString("descripcion"), rs.getString("aula"), rs.getString("justificacion"),
						rs.getDate("fecha"), rs.getString("campus"), rs.getInt("ranking"), rs.getString("USR") });
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Error de base de datos:\n" + e.getMessage(), "Error SQL",
					JOptionPane.ERROR_MESSAGE);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conexion != null)
					conexion.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private void cargarIncidenciasFiltradas(DefaultTableModel model) {
		java.sql.Connection conexion = null;
		java.sql.Statement stmt = null;
		java.sql.ResultSet rs = null;

		try {
			conexion = modelo.ConexionBD.conectar();
			String consulta = construirConsultaFiltrada();
			stmt = conexion.createStatement();
			rs = stmt.executeQuery(consulta);
			model.setRowCount(0);

			while (rs.next()) {
				model.addRow(new Object[] {
						rs.getString("estado"), rs.getString("edificio"), rs.getString("piso"),
						rs.getString("descripcion"), rs.getString("aula"), rs.getString("justificacion"),
						rs.getDate("fecha"), rs.getString("campus"), rs.getInt("ranking"), rs.getString("USR")
				});
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Error al filtrar incidencias:\n" + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
		} finally {
			try { if (rs != null) rs.close(); } catch (SQLException e) {}
			try { if (stmt != null) stmt.close(); } catch (SQLException e) {}
			try { if (conexion != null) conexion.close(); } catch (SQLException e) {}
		}
	}

	private String construirConsultaFiltrada() {
		StringBuilder consulta = new StringBuilder("SELECT estado, edificio, piso, descripcion, aula, justificacion, fecha, campus, ranking, USR FROM incidencias WHERE 1=1");

		String estado = comboBoxEstado.getSelectedItem().toString();
		if (!estado.equals("Estado")) {
			consulta.append(" AND estado = '").append(estado).append("'");
		}

		String fechaSeleccionada = comboBoxFecha.getSelectedItem().toString();
		if (!fechaSeleccionada.equals("Fecha") && !fechaSeleccionada.equals("Personalizado...")) {
			String[] partes = fechaSeleccionada.split(" - ");
			if (partes.length == 2) {
				consulta.append(" AND fecha = '").append(partes[1]).append("'");
			}
		}

		String busqueda = campoBusqueda.getText().trim();
		if (!busqueda.isEmpty()) {
			consulta.append(" AND (descripcion LIKE '%").append(busqueda)
					.append("%' OR aula LIKE '%").append(busqueda)
					.append("%' OR edificio LIKE '%").append(busqueda).append("%')");
		}

		String orden = comboBoxOrden.getSelectedItem().toString();
		switch (orden) {
			case "Más relevante primero":
				consulta.append(" ORDER BY ranking DESC");
				break;
			case "Menos relevante primero":
				consulta.append(" ORDER BY ranking ASC");
				break;
			case "Más reciente primero":
				consulta.append(" ORDER BY fecha DESC");
				break;
			default:
				consulta.append(" ORDER BY fecha DESC");
				break;
		}

		return consulta.toString();
	}

	private JLabel crearNavLabel(String texto, int x, Font fuente) {
		JLabel lbl = new JLabel(texto);
		lbl.setFont(fuente);
		lbl.setForeground(Color.WHITE);
		lbl.setBounds(x, 18, 150, 20);
		lbl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		return lbl;
	}
}
