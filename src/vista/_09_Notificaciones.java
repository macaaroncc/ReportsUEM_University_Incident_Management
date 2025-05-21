package vista;

import controlador.BarraNavegacion;
import controlador.Controlador;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class _09_Notificaciones extends JFrame {
    private Controlador controlador;
    private JTable table;
    private JScrollPane scrollPane;

    public _09_Notificaciones() {
        setTitle("09 . Notificaciones");
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

        // Título de la página
        JLabel lblTitulo = new JLabel("Notificaciones");
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 22));
        lblTitulo.setBounds(40, 80, 300, 30);
        getContentPane().add(lblTitulo);

        // Crear modelo de tabla
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer que la tabla no sea editable
            }
        };

        // Definir columnas
        model.addColumn("ID Incidencia");
        model.addColumn("Usuario");

        // Cargar datos desde la base de datos
        cargarNotificacionesDesdeBD(model);

        // Configurar tabla
        table = new JTable(model);
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        // Ajustar anchos de columnas
        table.getColumnModel().getColumn(0).setPreferredWidth(150); // ID Incidencia
        table.getColumnModel().getColumn(1).setPreferredWidth(300); // Usuario

        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(40, 120, 1110, 600);
        getContentPane().add(scrollPane);

        // ✅ Botón de ayuda flotante
        JButton btnAyuda = new JButton("?");
        btnAyuda.setBounds(1120, 740, 50, 50);
        btnAyuda.setBackground(new Color(128, 0, 0));
        btnAyuda.setForeground(Color.WHITE);
        btnAyuda.setFont(new Font("Arial", Font.BOLD, 20));
        btnAyuda.setFocusPainted(false);
        btnAyuda.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnAyuda.addActionListener(e -> {
            if (controlador != null) controlador.abrirAyuda();
            dispose();
        });
        getContentPane().add(btnAyuda);
    }

    private void cargarNotificacionesDesdeBD(DefaultTableModel model) {
        String url = "jdbc:mysql://localhost:3306/proyecto_integrador?useSSL=false";
        String usuario = "root";
        String contraseña = "";

        try (Connection conexion = DriverManager.getConnection(url, usuario, contraseña);
             Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT incidencias_id_incidencia, USERS_USR FROM notificar")) {
            
            model.setRowCount(0); // Limpiar tabla existente
            
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("incidencias_id_incidencia"),
                    rs.getString("USERS_USR")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Error al cargar notificaciones:\n" + e.getMessage(),
                "Error de base de datos", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            
            // Datos de ejemplo en caso de error
            model.addRow(new Object[]{0, "Error al cargar datos"});
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