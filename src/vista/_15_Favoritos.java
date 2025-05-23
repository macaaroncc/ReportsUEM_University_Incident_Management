package vista;

import controlador.Controlador;
import modelo.ConexionBD;
import modelo.Modelo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class _15_Favoritos extends JFrame {
	private Controlador controlador;
	private JTable table;
	private DefaultTableModel modelo;

	public _15_Favoritos() {
		setTitle("15 . Mis Favoritos");
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

		// Título
		JLabel lblTitulo = new JLabel("Mis Favoritos");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 26));
		lblTitulo.setBounds(40, 80, 400, 40);
		getContentPane().add(lblTitulo);

		// Tabla de favoritos
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(40, 140, 1100, 550);
		getContentPane().add(scrollPane);

		modelo = new DefaultTableModel(new Object[][] {},
				new String[] { "incidencias_id_incidencia", "USERS_USR" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return String.class;
			}
		};

		table = new JTable(modelo);
		table.setRowHeight(40);
		table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

		// Ancho y alineación de columnas
		table.getColumnModel().getColumn(0).setPreferredWidth(200); // incidencias_id_incidencia
		table.getColumnModel().getColumn(1).setPreferredWidth(200); // USERS_USR

		scrollPane.setViewportView(table);

		// Cargar datos de favoritos
		cargarFavoritosDesdeBD();

		// Botón Eliminar
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(40, 720, 100, 30);
		btnEliminar.setEnabled(false);
		btnEliminar.addActionListener(e -> eliminarFavorito());
		getContentPane().add(btnEliminar);

		table.getSelectionModel().addListSelectionListener(e -> {
			btnEliminar.setEnabled(table.getSelectedRow() != -1);
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

	private void cargarFavoritosDesdeBD() {
		String usuario = Modelo.usuarioActual;

		if (usuario == null || usuario.trim().isEmpty()) {
		    JOptionPane.showMessageDialog(this, "Usuario no identificado.", "Error", JOptionPane.ERROR_MESSAGE);
		    return;
		}

		// Concatenar sufijo @ueuropea.es al usuario
		String usuarioConDominio = usuario + "@ueuropea.es";

		String sql = "SELECT incidencias_id_incidencia, USERS_USR FROM favoritos WHERE USERS_USR = ?";

		try (Connection conexion = ConexionBD.conectar();
		     PreparedStatement stmt = conexion.prepareStatement(sql)) {

		    stmt.setString(1, usuarioConDominio);  // Pasa usuario + dominio aquí
		    ResultSet rs = stmt.executeQuery();

		    modelo.setRowCount(0);
		    while (rs.next()) {
		        String incidenciaId = rs.getString("incidencias_id_incidencia");
		        String user = rs.getString("USERS_USR");

		        modelo.addRow(new Object[]{incidenciaId, user});
		    }
		    rs.close();
		} catch (SQLException e) {
		    JOptionPane.showMessageDialog(this,
		        "Error al cargar favoritos: " + e.getMessage(),
		        "Error de base de datos", JOptionPane.ERROR_MESSAGE);
		    e.printStackTrace();
		}

	}

	private void eliminarFavorito() {
		int fila = table.getSelectedRow();
		if (fila != -1) {
			int confirmacion = JOptionPane.showConfirmDialog(this,
					"¿Estás seguro de eliminar este favorito?", "Confirmar eliminación",
					JOptionPane.YES_NO_OPTION);

			if (confirmacion == JOptionPane.YES_OPTION) {
				// Aquí se puede añadir lógica de eliminación de la base de datos
				modelo.removeRow(fila);
			}
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
