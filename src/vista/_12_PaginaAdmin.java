package vista;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controlador.Controlador;

public class _12_PaginaAdmin extends JFrame {
	private Controlador controlador;
	private JTable tablaIncidencias;
	private JTable tablaUsuarios;

	public _12_PaginaAdmin() {
		setTitle("12 . Panel de Administración");
		setSize(1200, 900);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		getContentPane().setBackground(new Color(255, 255, 252));
		getContentPane().setLayout(new BorderLayout());

		// Barra de navegación arriba
		BarraNavegacion barra = new BarraNavegacion();
		barra.setUsuarioLogueado(true);
		barra.setPreferredSize(new Dimension(1200, 59));
		getContentPane().add(barra, BorderLayout.NORTH);

		// Panel principal con scroll
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.setBackground(new Color(255, 255, 252));
		contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JLabel lblTitulo = new JLabel("Panel de Administrador");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPanel.add(lblTitulo);
		contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

		JButton btnEstadisticas = new JButton("Estadísticas");
		btnEstadisticas.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnEstadisticas.setBackground(new Color(128, 0, 0));
		btnEstadisticas.setForeground(Color.WHITE);
		btnEstadisticas.setFocusPainted(false);
		btnEstadisticas.setPreferredSize(new Dimension(150, 35));
		btnEstadisticas.addActionListener(e -> {
			new _13_Estadisticas(controlador).setVisible(true);
			dispose(); // Cierra la ventana actual
		});
		contentPanel.add(btnEstadisticas);
		contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		JLabel lblIncidencias = new JLabel("INCIDENCIAS");
		lblIncidencias.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblIncidencias.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPanel.add(lblIncidencias);
		contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

		// ---- Panel de búsqueda ----
		JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelBusqueda.setBackground(new Color(255, 255, 252));
		panelBusqueda.setBorder(new EmptyBorder(0, 20, 0, 0));

		JLabel lblEstado = new JLabel("Estado:");
		String[] estados = { "", "Pendiente", "En revisión", "Solucionada" };
		JComboBox<String> comboEstado = new JComboBox<>(estados);
		comboEstado.setPreferredSize(new Dimension(100, 28));

		JComboBox<String> comboOrden = new JComboBox<>(
				new String[] { "Orden de Relevancia", "Más votaciones", "Menos votaciones", "Más reciente" });
		comboOrden.setPreferredSize(new Dimension(180, 30));

		JLabel lblUsuario = new JLabel("Usuario:");
		JTextField txtUsuario = new JTextField(15);
		txtUsuario.setPreferredSize(new Dimension(250, 28));

		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setBackground(new Color(128, 0, 0));
		btnBuscar.setForeground(Color.WHITE);
		btnBuscar.setFocusPainted(false);

		btnBuscar.addActionListener(e -> {
			String estado = (String) comboEstado.getSelectedItem();
			String orden = (String) comboOrden.getSelectedItem();
			String usuario = txtUsuario.getText().trim();

			try {
				tablaIncidencias.setModel(Controlador.buscarIncidencias(estado, usuario, orden));
				tablaIncidencias.getTableHeader().setReorderingAllowed(false);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Error al buscar incidencias: " + ex.getMessage());
			}
		});

		panelBusqueda.add(lblEstado);
		panelBusqueda.add(comboEstado);
		panelBusqueda.add(comboOrden);
		panelBusqueda.add(lblUsuario);
		panelBusqueda.add(txtUsuario);
		panelBusqueda.add(btnBuscar);

		contentPanel.add(panelBusqueda);
		contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

		JPanel panelTablaIncidencias = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelTablaIncidencias.setBackground(new Color(255, 255, 252));
		tablaIncidencias = new JTable();
		JScrollPane scrollIncidencias = new JScrollPane(tablaIncidencias);
		scrollIncidencias.setPreferredSize(new Dimension(1100, 400));
		scrollIncidencias.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panelTablaIncidencias.add(scrollIncidencias);
		contentPanel.add(panelTablaIncidencias);
		contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

		JPanel panelBtnEditar = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelBtnEditar.setBackground(new Color(255, 255, 252));
		panelBtnEditar.setBorder(new EmptyBorder(0, 20, 0, 0));
		JButton btnEditarIncidencia = new JButton("Editar");
		btnEditarIncidencia.setBackground(new Color(128, 0, 0));
		btnEditarIncidencia.setForeground(Color.WHITE);
		btnEditarIncidencia.setFocusPainted(false);
		btnEditarIncidencia.setPreferredSize(new Dimension(100, 30));
		btnEditarIncidencia.addActionListener(e -> {
			int filaSeleccionada = tablaIncidencias.getSelectedRow();
			if (filaSeleccionada != -1) {
				int id = Integer.parseInt(String.valueOf(tablaIncidencias.getValueAt(filaSeleccionada, 0)));
				String estado = String.valueOf(tablaIncidencias.getValueAt(filaSeleccionada, 1));
				Object justObj = tablaIncidencias.getValueAt(filaSeleccionada, 7);
				String justificacion = justObj != null ? justObj.toString() : "";
				Object rankObj = tablaIncidencias.getValueAt(filaSeleccionada, 10);
				int rankingVal = 0;
				try {
					rankingVal = rankObj != null ? Integer.parseInt(rankObj.toString()) : 0;
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(this, "Ranking inválido. Se usará 0.");
				}
				new _16_EditarIncidencia(id, estado, justificacion).setVisible(true);
			} else {
				JOptionPane.showMessageDialog(this, "Selecciona una incidencia para editar.");
			}
		});
		panelBtnEditar.add(btnEditarIncidencia);
		contentPanel.add(panelBtnEditar);
		contentPanel.add(Box.createRigidArea(new Dimension(0, 30)));

		JButton btnEliminarIncidencia = new JButton("Eliminar");
		btnEliminarIncidencia.setBackground(new Color(128, 0, 0));
		btnEliminarIncidencia.setForeground(Color.WHITE);
		btnEliminarIncidencia.setFocusPainted(false);
		btnEliminarIncidencia.setPreferredSize(new Dimension(100, 30));
		btnEliminarIncidencia.addActionListener(e -> {
			int filaSeleccionada = tablaIncidencias.getSelectedRow();
			if (filaSeleccionada != -1) {
				int idIncidencia = Integer.parseInt(tablaIncidencias.getValueAt(filaSeleccionada, 0).toString());

				int confirm = JOptionPane.showConfirmDialog(this,
						"¿Estás seguro de que quieres eliminar esta incidencia?", "Confirmar eliminación",
						JOptionPane.YES_NO_OPTION);
				if (confirm == JOptionPane.YES_OPTION) {
					if (controlador.eliminarIncidencia(idIncidencia)) {
						JOptionPane.showMessageDialog(this, "Incidencia eliminada correctamente.");
						cargarIncidencias(); // Recarga la tabla
					} else {
						JOptionPane.showMessageDialog(this, "Error al eliminar la incidencia.");
					}
				}
			} else {
				JOptionPane.showMessageDialog(this, "Selecciona una incidencia para eliminar.");
			}
		});
		panelBtnEditar.add(btnEliminarIncidencia);

		// ---- Sección Usuarios ----
		JLabel lblUsuarios = new JLabel("USUARIOS");
		lblUsuarios.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblUsuarios.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPanel.add(lblUsuarios);
		contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

		JPanel panelTablaUsuarios = new JPanel(new FlowLayout(FlowLayout.CENTER));
		tablaUsuarios.getTableHeader().setReorderingAllowed(false);
		panelTablaUsuarios.setBackground(new Color(255, 255, 252));
		tablaUsuarios = new JTable();
		JScrollPane scrollUsuarios = new JScrollPane(tablaUsuarios);
		scrollUsuarios.setPreferredSize(new Dimension(1100, 400));
		scrollUsuarios.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panelTablaUsuarios.add(scrollUsuarios);
		contentPanel.add(panelTablaUsuarios);
		contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

		JPanel panelBtnEliminar = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelBtnEliminar.setBackground(new Color(255, 255, 252));
		panelBtnEliminar.setBorder(new EmptyBorder(0, 20, 0, 0));
		JButton btnEliminarUsuario = new JButton("Eliminar");
		btnEliminarUsuario.setBackground(new Color(128, 0, 0));
		btnEliminarUsuario.setForeground(Color.WHITE);
		btnEliminarUsuario.setFocusPainted(false);
		btnEliminarUsuario.setPreferredSize(new Dimension(100, 30));
		btnEliminarUsuario.addActionListener(e -> {
			int selectedRow = tablaUsuarios.getSelectedRow();
			if (selectedRow != -1) {
				String usuario = tablaUsuarios.getValueAt(selectedRow, 0).toString();
				int confirm = JOptionPane.showConfirmDialog(this,
						"¿Estás seguro de que quieres eliminar al usuario \"" + usuario + "\"?",
						"Confirmar eliminación", JOptionPane.YES_NO_OPTION);
				if (confirm == JOptionPane.YES_OPTION) {
					if (controlador.eliminarUsuario(usuario)) {
						JOptionPane.showMessageDialog(this, "Usuario eliminado correctamente.");
						cargarUsuarios();
					} else {
						JOptionPane.showMessageDialog(this, "Error al eliminar usuario.");
					}
				}
			} else {
				JOptionPane.showMessageDialog(this, "Selecciona un usuario para eliminar.");
			}
		});
		panelBtnEliminar.add(btnEliminarUsuario);
		contentPanel.add(panelBtnEliminar);

		JScrollPane scrollGeneral = new JScrollPane(contentPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		getContentPane().add(scrollGeneral, BorderLayout.CENTER);

		cargarIncidencias();
		cargarUsuarios();
	}

	private void cargarIncidencias() {
		try {
			tablaIncidencias.setModel(Controlador.cargarIncidencias());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error al cargar incidencias: " + e.getMessage());
		}
	}

	private void cargarUsuarios() {
		try {
			tablaUsuarios.setModel(Controlador.cargarUsuarios());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error al cargar usuarios: " + e.getMessage());
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
