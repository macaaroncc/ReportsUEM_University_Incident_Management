package vista;

import controlador.Controlador;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class _01_PGSinLogin extends JFrame {

    private Controlador controlador;
    private JTable table;
    private JComboBox<String> comboBoxEstado, comboBoxOrden, comboBoxFecha;
    private JTextField campoBusqueda;
    private JButton botonBuscar, btnAyuda;
    private JScrollPane scrollPane;

    public _01_PGSinLogin() {
        // Configuración básica de la ventana
        setTitle("Página Principal - Visitante");
        setSize(1200, 900);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        getContentPane().setBackground(new Color(255, 255, 252));
        
        // Configuración de esquinas redondeadas para los componentes
        UIManager.put("Button.arc", 20);
        UIManager.put("Component.arc", 20);
        UIManager.put("TextComponent.arc", 15);
        UIManager.put("ComboBox.arc", 15);

        // Barra de navegación superior
        JPanel barra = new JPanel(null);
        barra.setBackground(new Color(128, 0, 0));
        barra.setBounds(0, 0, 1200, 59);
        getContentPane().add(barra);

        // Logo con esquinas redondeadas
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/img/LogoBlanco.png"));
            Image imagenEscalada = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            JLabel lblLogo = new JLabel(new ImageIcon(imagenEscalada)) {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setClip(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 10, 10));
                    super.paintComponent(g2);
                    g2.dispose();
                }
            };
            lblLogo.setBounds(15, 10, 40, 40);
            barra.add(lblLogo);
        } catch (Exception e) {
            System.err.println("Error cargando logo: " + e.getMessage());
        }

        // Elementos de navegación
        Font fuenteNav = new Font("Tahoma", Font.BOLD, 13);
        JLabel lblPGNPrincipal = crearNavLabel("Página Principal", 80, fuenteNav);
        JLabel lblMisIncidencias = crearNavLabel("Mis Incidencias", 240, fuenteNav);
        JLabel lblNotificaciones = crearNavLabel("Notificaciones", 410, fuenteNav);
        JLabel lblUsuario = crearNavLabel("Iniciar Sesión", 1080, fuenteNav);

        barra.add(lblPGNPrincipal);
        barra.add(lblMisIncidencias);
        barra.add(lblNotificaciones);
        barra.add(lblUsuario);

        // Listeners para redirección al login
        MouseAdapter abrirLogin = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (controlador != null) {
                    controlador.abrirLogin();
                    dispose();
                }
            }
        };
        lblPGNPrincipal.addMouseListener(abrirLogin);
        lblMisIncidencias.addMouseListener(abrirLogin);
        lblNotificaciones.addMouseListener(abrirLogin);
        lblUsuario.addMouseListener(abrirLogin);

        // Panel de filtros con bordes redondeados
        JPanel panelFiltros = new JPanel(null) {
            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(220, 220, 220));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
                g2.dispose();
            }
        };
        panelFiltros.setBounds(30, 65, 1140, 50);
        panelFiltros.setBackground(Color.WHITE);
        panelFiltros.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(panelFiltros);

        // Componentes de filtrado con esquinas redondeadas
        comboBoxEstado = new JComboBox<>(new String[]{"Estado", "Pendiente", "Solucionada", "En revisión"});
        comboBoxEstado.setBounds(10, 10, 150, 30);
        panelFiltros.add(comboBoxEstado);

        comboBoxOrden = new JComboBox<>(new String[]{"Orden de Relevancia", "Más relevante primero", 
            "Menos relevante primero", "Más reciente primero"});
        comboBoxOrden.setBounds(170, 10, 180, 30);
        panelFiltros.add(comboBoxOrden);

        comboBoxFecha = new JComboBox<>();
        comboBoxFecha.setBounds(360, 10, 180, 30);
        comboBoxFecha.addItem("Fecha");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        comboBoxFecha.addItem("Hoy - " + sdf.format(new Date()));
        for (int i = 1; i <= 6; i++) {
            Date date = new Date(System.currentTimeMillis() - (i * 24L * 60 * 60 * 1000));
            comboBoxFecha.addItem("Hace " + i + " día(s) - " + sdf.format(date));
        }
        comboBoxFecha.addItem("Personalizado...");
        panelFiltros.add(comboBoxFecha);

        campoBusqueda = new JTextField() {
            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(200, 200, 200));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 20, 20);
                g2.dispose();
            }
        };
        campoBusqueda.setBounds(550, 10, 250, 30);
        campoBusqueda.setOpaque(false);
        campoBusqueda.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        panelFiltros.add(campoBusqueda);

        botonBuscar = new JButton("Buscar") {
            @Override
            protected void paintComponent(Graphics g) {
                if (!isOpaque()) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(getBackground());
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                    g2.dispose();
                }
                super.paintComponent(g);
            }
            
            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground().darker());
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 20, 20);
                g2.dispose();
            }
        };
        botonBuscar.setBounds(810, 10, 120, 30);
        botonBuscar.setBackground(new Color(128, 0, 0));
        botonBuscar.setForeground(Color.WHITE);
        botonBuscar.setFont(new Font("Tahoma", Font.BOLD, 12));
        botonBuscar.setFocusPainted(false);
        botonBuscar.setContentAreaFilled(false);
        botonBuscar.setOpaque(false);
        botonBuscar.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        panelFiltros.add(botonBuscar);

        // Tabla de incidencias con scroll redondeado
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 7) return Date.class;
                if (columnIndex == 9) return Integer.class;
                return String.class;
            }
        };

        // Columnas de la tabla
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

        table = new JTable(model) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (isRowSelected(row)) {
                    c.setBackground(new Color(220, 230, 240));
                } else {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(248, 248, 248));
                }
                return c;
            }
        };
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.getColumnModel().getColumn(4).setPreferredWidth(250);
        table.getColumnModel().getColumn(6).setPreferredWidth(200);

        // Listener para ver detalles de incidencia
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

        scrollPane = new JScrollPane(table) {
            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(220, 220, 220));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
                g2.dispose();
            }
        };
        scrollPane.setBounds(30, 130, 1140, 600);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setViewportBorder(BorderFactory.createEmptyBorder());
        getContentPane().add(scrollPane);

        // Botón de ayuda redondeado
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
        btnAyuda.setFont(new Font("Arial", Font.BOLD, 20));
        btnAyuda.setFocusPainted(false);
        btnAyuda.setBorder(BorderFactory.createEmptyBorder());
        getContentPane().add(btnAyuda);
        btnAyuda.addActionListener(e -> {
            if (controlador != null) controlador.abrirAyuda();
        });

        // Acción del botón buscar
        botonBuscar.addActionListener(e -> {
            DefaultTableModel m = (DefaultTableModel) table.getModel();
            cargarIncidenciasFiltradas(m);
        });
    }

    public void setControlador(Controlador controlador) {
		this.controlador = controlador;
	}

	private void cargarIncidenciasDesdeBD(DefaultTableModel model) {
		try (Connection conexion = modelo.ConexionBD.conectar();
				Statement stmt = conexion.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM incidencias")) {

			model.setRowCount(0);
			while (rs.next()) {
				model.addRow(new Object[] { rs.getInt("id_incidencia"), rs.getString("estado"),
						rs.getString("edificio"), rs.getString("piso"), rs.getString("descripcion"),
						rs.getString("aula"), rs.getString("justificacion"), rs.getDate("fecha"),
						rs.getString("campus"), rs.getInt("ranking"), rs.getString("USR") });
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Error SQL: " + e.getMessage());
		}
	}

	private void cargarIncidenciasFiltradas(DefaultTableModel model) {
		try (Connection conexion = modelo.ConexionBD.conectar();
				Statement stmt = conexion.createStatement();
				ResultSet rs = stmt.executeQuery(construirConsultaFiltrada())) {

			model.setRowCount(0);
			while (rs.next()) {
				model.addRow(new Object[] { rs.getInt("id_incidencia"), rs.getString("estado"),
						rs.getString("edificio"), rs.getString("piso"), rs.getString("descripcion"),
						rs.getString("aula"), rs.getString("justificacion"), rs.getDate("fecha"),
						rs.getString("campus"), rs.getInt("ranking"), rs.getString("USR") });
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Error al filtrar: " + e.getMessage());
		}
	}

	private String construirConsultaFiltrada() {
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