// @Autor: Bea 

package vista;

import controlador.Controlador;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import javax.swing.table.DefaultTableCellRenderer;

public class _07_MisIncidencias extends JFrame {
	private Controlador controlador;
	private JTable table;
	private DefaultTableModel modelo;

	public _07_MisIncidencias() {
		setTitle("07 . Mis Incidencias");
		setSize(1200, 900);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		getContentPane().setBackground(new Color(255, 255, 252));

		// ✅ Barra de navegación reutilizable
		BarraNavegacion barra = new BarraNavegacion();
		barra.btnAtras.addActionListener(e -> {
			if (controlador != null)
				controlador.volverAtras(this);
			dispose();
		});
		barra.setUsuarioLogueado(true);
		barra.setControlador(controlador);
		barra.setBounds(0, 0, 1200, 59);
		getContentPane().add(barra);

		// Título
		JLabel lblTitulo = new JLabel("Mis Incidencias");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 26));
		lblTitulo.setBounds(40, 80, 400, 40);
		getContentPane().add(lblTitulo);

		// Tabla de incidencias
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(40, 140, 1100, 550);
		getContentPane().add(scrollPane);

		// Modelo de tabla con columnas: Descripción, Estado, Edificio, Aula, Fecha
		modelo = new DefaultTableModel(new Object[][] {},
				new String[] { "Descripción", "Estado", "Edificio", "Aula", "Fecha" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Hacer que la tabla no sea editable
			}

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				if (columnIndex == 4)
					return Date.class; // Para la columna de fecha
				return String.class;
			}
		};

		table = new JTable(modelo);
		table.setRowHeight(40);
		table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

		// Personalizar ancho de columnas
		table.getColumnModel().getColumn(0).setPreferredWidth(400); // Descripción
		table.getColumnModel().getColumn(1).setPreferredWidth(100); // Estado
		table.getColumnModel().getColumn(2).setPreferredWidth(80); // Edificio
		table.getColumnModel().getColumn(3).setPreferredWidth(80); // Aula
		table.getColumnModel().getColumn(4).setPreferredWidth(100); // Fecha

		// Centrar el contenido de algunas columnas
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer); // Estado
		table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer); // Edificio
		table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer); // Aula
		table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer); // Fecha

		scrollPane.setViewportView(table);

		// Cargar datos de la base de datos
		cargarIncidenciasDesdeBD();

		// Botones de acción
		JButton btnNuevaIncidencia = new JButton("Nueva Incidencia");
		btnNuevaIncidencia.setBounds(40, 720, 150, 30);
		btnNuevaIncidencia.addActionListener(e -> {
			if (controlador != null) {
				controlador.abrirCrearIncidencia(this);
				dispose();
			}
		});
		getContentPane().add(btnNuevaIncidencia);

		JButton btnEditar = new JButton("Editar");
		btnEditar.setBounds(200, 720, 100, 30);
		btnEditar.setEnabled(false);
		btnEditar.addActionListener(e -> editarIncidencia());
		getContentPane().add(btnEditar);

		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(310, 720, 100, 30);
		btnEliminar.setEnabled(false);
		btnEliminar.addActionListener(e -> eliminarIncidencia());
		getContentPane().add(btnEliminar);

		// Listener para habilitar/deshabilitar botones según selección
		table.getSelectionModel().addListSelectionListener(e -> {
			boolean rowSelected = table.getSelectedRow() != -1;
			btnEditar.setEnabled(rowSelected);
			btnEliminar.setEnabled(rowSelected);
		});

		// Botón ayuda flotante
		JButton btnAyuda = new JButton("?");
		btnAyuda.setBounds(1120, 740, 50, 50);
		btnAyuda.setBackground(new Color(128, 0, 0));
		btnAyuda.setForeground(Color.WHITE);
		btnAyuda.setFont(new Font("Arial", Font.BOLD, 20));
		btnAyuda.setFocusPainted(false);
		btnAyuda.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAyuda.addActionListener(e -> {
			if (controlador != null) {
				controlador.abrirAyuda();
				dispose();
			}
		});
		getContentPane().add(btnAyuda);
	}

	private void cargarIncidenciasDesdeBD() {
		String URL = "jdbc:mysql://localhost:3306/proyecto_integrador";
		String user = "root";
		String password = "";

		try (Connection conn = DriverManager.getConnection(URL, user, password)) {
			String query = "SELECT estado, edificio, aula, descripcion, fecha FROM incidencias";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			// Limpiar la tabla antes de cargar nuevos datos
			modelo.setRowCount(0);

			while (rs.next()) {
				String estado = rs.getString("estado");
				String edificio = rs.getString("edificio");
				String aula = rs.getString("aula");
				String descripcion = rs.getString("descripcion");
				Date fecha = rs.getDate("fecha");

				// Añadir fila a la tabla
				modelo.addRow(new Object[] { descripcion, estado, edificio, aula, fecha });
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Error al cargar incidencias: " + e.getMessage(),
					"Error de base de datos", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	private void editarIncidencia() {
		int fila = table.getSelectedRow();
		if (fila != -1) {
			String descripcion = (String) modelo.getValueAt(fila, 0);
			String estado = (String) modelo.getValueAt(fila, 1);
			String edificio = (String) modelo.getValueAt(fila, 2);
			String aula = (String) modelo.getValueAt(fila, 3);
			Date fecha = (Date) modelo.getValueAt(fila, 4);

			// Aquí iría la lógica para editar la incidencia
			JOptionPane.showMessageDialog(this,
					"Editando incidencia:\n" + "Descripción: " + descripcion + "\n" + "Estado: " + estado + "\n"
							+ "Edificio: " + edificio + "\n" + "Aula: " + aula + "\n" + "Fecha: " + fecha.toString(),
					"Editar Incidencia", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void eliminarIncidencia() {
		int fila = table.getSelectedRow();
		if (fila != -1) {
			int confirmacion = JOptionPane.showConfirmDialog(this, "¿Estás seguro de eliminar esta incidencia?",
					"Confirmar eliminación", JOptionPane.YES_NO_OPTION);

			if (confirmacion == JOptionPane.YES_OPTION) {
				// Aquí iría el código para eliminar de la base de datos
				// Por ahora solo eliminamos de la tabla
				modelo.removeRow(fila);
			}
		}
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