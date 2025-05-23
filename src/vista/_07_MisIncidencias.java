// @Autor: Bea 

package vista;

import controlador.Controlador;
import modelo.ConexionBD;
import modelo.Modelo;

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
		JButton btnNuevaIncidencia = new JButton("Crear Incidencia");
		btnNuevaIncidencia.setBounds(40, 720, 150, 30);
		btnNuevaIncidencia.addActionListener(e -> {
			if (controlador != null) {
				controlador.abrirCrearIncidencia(this);
				dispose();
			}
		});
		getContentPane().add(btnNuevaIncidencia);

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
    String usuario = Modelo.usuarioActual;

    if (usuario == null || usuario.trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Usuario no identificado.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    String usuarioConDominio = usuario + "@ueuropea.es";


    String sql = "SELECT descripcion, estado, edificio, aula, fecha FROM incidencias WHERE USERS_USR = ?";

    try (Connection conexion = ConexionBD.conectar();
         PreparedStatement stmt = conexion.prepareStatement(sql)) {

        stmt.setString(1, usuarioConDominio);
        ResultSet rs = stmt.executeQuery();

        // Limpiar la tabla antes de cargar nuevos datos
        modelo.setRowCount(0);

        while (rs.next()) {
            String descripcion = rs.getString("descripcion");
            String estado = rs.getString("estado");
            String edificio = rs.getString("edificio");
            String aula = rs.getString("aula");
            Date fecha = rs.getDate("fecha");

            // Añadir fila a la tabla
            modelo.addRow(new Object[] { descripcion, estado, edificio, aula, fecha });
        }
        rs.close();

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this,
            "Error al cargar incidencias: " + e.getMessage(),
            "Error de base de datos", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
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