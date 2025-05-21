//autor aaron

package vista;

import controlador.Controlador;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase _06_PaginaPrincipal.
 * Representa la clase _06_PaginaPrincipal.
 */
public class _06_PaginaPrincipal extends JFrame {
    private JPanel contentPane;
    private JTable table;
    private JComboBox<String> comboBoxEstado, comboBoxOrden, comboBoxFecha;
    private JTextField campoBusqueda;
    private JButton botonBuscar, btnCrearIncidenciaInferior, btnAyuda;
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

        // ✅ Barra de navegación
        BarraNavegacion barra = new BarraNavegacion();
        barra.setUsuarioLogueado(true);
        barra.setControlador(controlador);
        barra.setBounds(0, 0, 1200, 59);
        contentPane.add(barra);

        // --- Filtros y buscador ---
        comboBoxEstado = new JComboBox<>(new String[]{"Estado", "Pendiente", "Resuelta", "En proceso"});
        comboBoxEstado.setBounds(40, 70, 150, 30);
        contentPane.add(comboBoxEstado);

        comboBoxOrden = new JComboBox<>(new String[]{"Orden de Relevancia", "Más relevante primero", 
            "Menos relevante primero", "Más reciente primero"});
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
        comboBoxFecha.addItem("Personalizado...");
        contentPane.add(comboBoxFecha);

        campoBusqueda = new JTextField();
        campoBusqueda.setBounds(750, 70, 250, 30);
        contentPane.add(campoBusqueda);

        botonBuscar = new JButton("Buscar");
        botonBuscar.setBounds(1010, 70, 100, 30);
        botonBuscar.setBackground(new Color(128, 0, 0));
        botonBuscar.setForeground(Color.WHITE);
        botonBuscar.setFont(new Font("Tahoma", Font.BOLD, 12));
        botonBuscar.setFocusPainted(false);
        contentPane.add(botonBuscar);

        // --- Tabla de incidencias ---
        DefaultTableModel model = new DefaultTableModel() {
            @Override
/**
 * Realiza la acción correspondiente.
 * @param row Valor numérico entero.
 * @param column Valor numérico entero.
 */
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 6) return Date.class; // Columna de fecha
                if (columnIndex == 8) return Integer.class; // Columna de ranking
                return String.class;
            }
        };

        // Definir columnas (todas excepto 'foto')
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

        // Cargar datos
        cargarIncidenciasDesdeBD(model);

        // Configurar tabla
        table = new JTable(model);
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Ajustar anchos de columnas
        table.getColumnModel().getColumn(3).setPreferredWidth(250); // Descripción
        table.getColumnModel().getColumn(5).setPreferredWidth(200); // Justificación
        table.getColumnModel().getColumn(9).setPreferredWidth(100); // Usuario

        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(40, 120, 1110, 600);
        contentPane.add(scrollPane);

        // --- Botón Crear Incidencia ---
        btnCrearIncidenciaInferior = new JButton("Crear Incidencia");
        btnCrearIncidenciaInferior.setBounds((1200 - 200) / 2, 740, 200, 40);
        btnCrearIncidenciaInferior.setBackground(new Color(128, 0, 0));
        btnCrearIncidenciaInferior.setForeground(Color.WHITE);
        btnCrearIncidenciaInferior.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnCrearIncidenciaInferior.setFocusPainted(false);
        btnCrearIncidenciaInferior.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnCrearIncidenciaInferior.addActionListener(e -> {
            if (controlador != null) controlador.abrirCrearIncidencia(this);
        });
        contentPane.add(btnCrearIncidenciaInferior);

        // --- Botón Ayuda ---
        btnAyuda = new JButton("?");
        btnAyuda.setBounds(1120, 740, 50, 50);
        btnAyuda.setBackground(new Color(128, 0, 0));
        btnAyuda.setForeground(Color.WHITE);
        btnAyuda.setFont(new Font("Arial", Font.BOLD, 20));
        btnAyuda.setFocusPainted(false);
        btnAyuda.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnAyuda.addActionListener(e -> {
            if (controlador != null) controlador.abrirAyuda();
        });
        contentPane.add(btnAyuda);
    }

/**
 * Realiza la acción correspondiente.
 * @param model Parámetro de tipo DefaultTableModel.
 */
    private void cargarIncidenciasDesdeBD(DefaultTableModel model) {
        try (Connection conexion = modelo.ConexionBD.conectar();
             java.sql.Statement stmt = conexion.createStatement();
             java.sql.ResultSet rs = stmt.executeQuery(
                 "SELECT estado, edificio, piso, descripcion, aula, justificacion, fecha, campus, ranking, USR " +
                 "FROM incidencias ORDER BY fecha DESC")) {
            
            model.setRowCount(0); // Limpiar tabla existente
            
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("estado"),
                    rs.getString("edificio"),
                    rs.getString("piso"),
                    rs.getString("descripcion"),
                    rs.getString("aula"),
                    rs.getString("justificacion"),
                    rs.getDate("fecha"),
                    rs.getString("campus"),
                    rs.getInt("ranking"),
                    rs.getString("USR")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Error al cargar incidencias:\n" + e.getMessage(),
                "Error de base de datos", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();

            // Datos de ejemplo en caso de error
            model.addRow(new Object[]{"Error", "Error", "Error", "No se pudo conectar a la BD", 
                "Error", "Error", new Date(), "Error", 0, "Error"});
        }
    }

/**
 * Establece el valor de controlador.
 * @param controlador Controlador principal que gestiona la lógica de navegación.
 */
    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
        for (Component c : getContentPane().getComponents()) {
            if (c instanceof BarraNavegacion) {
                ((BarraNavegacion) c).setControlador(controlador);
            }
        }
    }
}