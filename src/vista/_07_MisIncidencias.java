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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

		JLabel lblTitulo = new JLabel("Mis Incidencias");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 26));
		lblTitulo.setBounds(40, 80, 400, 40);
		getContentPane().add(lblTitulo);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(40, 140, 1100, 550);
		getContentPane().add(scrollPane);

		// 👇 Se agregó la columna "ID" para abrir los detalles
		modelo = new DefaultTableModel(new Object[][] {},
				new String[] { "ID", "Descripción", "Estado", "Edificio", "Aula", "Fecha" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				if (columnIndex == 5)
					return Date.class;
				return String.class;
			}
		};

		table = new JTable(modelo);
		table.setRowHeight(40);
		table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

		table.getTableHeader().setReorderingAllowed(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(40); // ID
		table.getColumnModel().getColumn(1).setPreferredWidth(400); // Descripción
		table.getColumnModel().getColumn(2).setPreferredWidth(100); // Estado
		table.getColumnModel().getColumn(3).setPreferredWidth(80); // Edificio
		table.getColumnModel().getColumn(4).setPreferredWidth(80); // Aula
		table.getColumnModel().getColumn(5).setPreferredWidth(100); // Fecha

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);

		scrollPane.setViewportView(table);

		// 🧠 Abrir vista detalle al hacer clic en una fila
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int fila = table.getSelectedRow();
				if (fila != -1) {
					int idIncidencia = Integer.parseInt(table.getValueAt(fila, 0).toString());
					new _17_DetalleIncidencia(idIncidencia).setVisible(true);
				}
			}
		});

		cargarIncidenciasDesdeBD();

		// Botón Crear Incidencia redondeado
		JButton btnNuevaIncidencia = new JButton("Crear Incidencia") {
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
		btnNuevaIncidencia.setBounds(40, 720, 150, 30);
		btnNuevaIncidencia.setBackground(new Color(128, 0, 0));
		btnNuevaIncidencia.setForeground(Color.WHITE);
		btnNuevaIncidencia.setFont(new Font("Arial", Font.BOLD, 14));
		btnNuevaIncidencia.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
		btnNuevaIncidencia.setContentAreaFilled(false);
		btnNuevaIncidencia.addActionListener(e -> {
			if (controlador != null) {
				controlador.abrirCrearIncidencia(this);
				dispose();
			}
		});
		getContentPane().add(btnNuevaIncidencia);

		// Botón Ayuda redondeado (círculo perfecto)
		JButton btnAyuda = new JButton("?") {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(getBackground());
				g2.fillOval(0, 0, getWidth(), getHeight());

				// Texto centrado
				g2.setColor(getForeground());
				FontMetrics fm = g2.getFontMetrics();
				int textWidth = fm.stringWidth(getText());
				int textHeight = fm.getAscent();
				g2.drawString(getText(), (getWidth() - textWidth) / 2, (getHeight() + textHeight) / 2 - 2);
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

		String sql = "SELECT id_incidencia, descripcion, estado, edificio, aula, fecha FROM incidencias WHERE USERS_USR = ?";

		try (Connection conexion = ConexionBD.conectar(); PreparedStatement stmt = conexion.prepareStatement(sql)) {

			stmt.setString(1, usuarioConDominio);
			ResultSet rs = stmt.executeQuery();
			modelo.setRowCount(0);

			while (rs.next()) {
				int id = rs.getInt("id_incidencia");
				String descripcion = rs.getString("descripcion");
				String estado = rs.getString("estado");
				String edificio = rs.getString("edificio");
				String aula = rs.getString("aula");
				Date fecha = rs.getDate("fecha");

				modelo.addRow(new Object[] { id, descripcion, estado, edificio, aula, fecha });
			}
			rs.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Error al cargar incidencias: " + e.getMessage(),
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
