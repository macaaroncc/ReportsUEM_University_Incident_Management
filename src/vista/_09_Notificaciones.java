package vista;

import controlador.Controlador;
import vista._10_PerfilUsuario.RoundedButton;

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
        model.addColumn("Estado");
        model.addColumn("Descripción");
        model.addColumn("Justificación");

        // Cargar datos desde la base de datos
        cargarNotificacionesDesdeBD(model);

        // Configurar tabla
        table = new JTable(model);
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        // Ajustar anchos de columnas
        table.getColumnModel().getColumn(0).setPreferredWidth(150); // Estado
        table.getColumnModel().getColumn(1).setPreferredWidth(600); // Descripción
        table.getColumnModel().getColumn(2).setPreferredWidth(360); // Justificación

        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(40, 120, 1110, 600);
        getContentPane().add(scrollPane);

        // ✅ Botón de ayuda flotante
        RoundedButton btnAyuda = new RoundedButton("?", 50, new Color(128, 0, 0)); // Rojo vino
		btnAyuda.setBounds(1120, 750, 50, 50);
		btnAyuda.setFont(new Font("Arial", Font.BOLD, 20));
		getContentPane().add(btnAyuda);
        btnAyuda.addActionListener(e -> {
            if (controlador != null)
                controlador.abrirAyuda();
            dispose();
        });
        getContentPane().add(btnAyuda);
    }

    private void cargarNotificacionesDesdeBD(DefaultTableModel model) {
        String usuario = modelo.Modelo.usuarioActual;

        if (usuario == null || usuario.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Usuario no identificado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String usuarioConDominio = usuario + "@ueuropea.es";

        String sql = "SELECT i.estado, i.descripcion, i.justificacion "
                   + "FROM notificar n "
                   + "JOIN incidencias i ON n.incidencias_id_incidencia = i.id_incidencia "
                   + "WHERE n.USERS_USR = ? AND (i.estado = 'Solucionada' OR i.estado = 'Rechazada')";

        
        
        try (Connection conexion = modelo.ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(1, usuarioConDominio);
            ResultSet rs = stmt.executeQuery();

            model.setRowCount(0);
            while (rs.next()) {
                String estado = rs.getString("estado");
                String descripcion = rs.getString("descripcion");
                String justificacion = rs.getString("justificacion");

                model.addRow(new Object[] { estado, descripcion, justificacion });
            }

            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar notificaciones:\n" + e.getMessage(),
                    "Error de base de datos", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();

            model.addRow(new Object[] { "Error", "Error al cargar datos", "" });
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
