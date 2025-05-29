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
            new String[] { "ID", "Estado", "Edificio", "Foto", "Piso", "Descripción", "Aula", "Fecha", "Campus", "Ranking", "Usuario" }) {
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

        // Ocultar columna ID
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setWidth(0);

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

        String usuarioConDominio = usuario + "@ueuropea.es";

        String sql = "SELECT i.id_incidencia, i.estado, i.edificio, i.foto, i.piso, i.descripcion, i.aula, i.fecha, i.campus, i.ranking, i.USR " +
                     "FROM favoritos f " +
                     "JOIN incidencias i ON f.incidencias_id_incidencia = i.id_incidencia " +
                     "WHERE f.USERS_USR = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(1, usuarioConDominio);
            ResultSet rs = stmt.executeQuery();

            modelo.setRowCount(0);

            while (rs.next()) {
                int idIncidencia = rs.getInt("id_incidencia");
                String estado = rs.getString("estado");
                String edificio = rs.getString("edificio");
                String foto = rs.getString("foto");
                String piso = rs.getString("piso");
                String descripcion = rs.getString("descripcion");
                String aula = rs.getString("aula");
                String fecha = rs.getString("fecha");
                String campus = rs.getString("campus");
                String ranking = rs.getString("ranking");
                String usr = rs.getString("USR");

                modelo.addRow(new Object[] {
                    idIncidencia, estado, edificio, foto, piso, descripcion, aula, fecha, campus, ranking, usr
                });
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
                int idIncidencia = (int) modelo.getValueAt(fila, 0); // Columna 0: ID
                String usuario = Modelo.usuarioActual + "@ueuropea.es";

                if (controlador != null && controlador.eliminarFavorito(String.valueOf(idIncidencia), usuario)) {
                    modelo.removeRow(fila);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "No se pudo eliminar el favorito de la base de datos.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
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
