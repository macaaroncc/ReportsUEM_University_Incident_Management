package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controlador.Controlador;
import modelo.ConexionBD;

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
        barra.setControlador(controlador);
        barra.setPreferredSize(new Dimension(1200, 59));
        getContentPane().add(barra, BorderLayout.NORTH);

        // Panel principal con scroll
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(new Color(255, 255, 252));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Título
        JLabel lblTitulo = new JLabel("Panel de Administrador");
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(lblTitulo);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // ---- Sección Incidencias ----
        JLabel lblIncidencias = new JLabel("INCIDENCIAS");
        lblIncidencias.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblIncidencias.setAlignmentX(Component.LEFT_ALIGNMENT);   // Alinear a la izquierda
        lblIncidencias.setBorder(new EmptyBorder(0, 15, 0, 0));    // Margen izquierdo 15px
        contentPanel.add(lblIncidencias);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Panel centrador para la tabla de incidencias
        JPanel panelTablaIncidencias = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelTablaIncidencias.setBackground(new Color(255, 255, 252));
        tablaIncidencias = new JTable();
        JScrollPane scrollIncidencias = new JScrollPane(tablaIncidencias);
        scrollIncidencias.setPreferredSize(new Dimension(1100, 400));
        scrollIncidencias.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        panelTablaIncidencias.add(scrollIncidencias);
        contentPanel.add(panelTablaIncidencias);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Panel centrado para el botón Editar
        JPanel panelBtnEditar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBtnEditar.setBackground(new Color(255, 255, 252));
        panelBtnEditar.setBorder(new EmptyBorder(0, 15, 0, 0)); // Margen izquierdo 15px
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
                int ranking = 0;
                try {
                    ranking = rankObj != null ? Integer.parseInt(rankObj.toString()) : 0;
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Ranking inválido. Se usará 0.");
                }
                new _16_EditarIncidencia(id, estado, justificacion, ranking).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona una incidencia para editar.");
            }
        });
        panelBtnEditar.add(btnEditarIncidencia);
        contentPanel.add(panelBtnEditar);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // ---- Sección Usuarios ----
        JLabel lblUsuarios = new JLabel("USUARIOS");
        lblUsuarios.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblUsuarios.setAlignmentX(Component.LEFT_ALIGNMENT);       // Alinear a la izquierda
        lblUsuarios.setBorder(new EmptyBorder(0, 15, 0, 0));       // Margen izquierdo 15px
        contentPanel.add(lblUsuarios);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Panel centrador para la tabla de usuarios
        JPanel panelTablaUsuarios = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelTablaUsuarios.setBackground(new Color(255, 255, 252));
        tablaUsuarios = new JTable();
        JScrollPane scrollUsuarios = new JScrollPane(tablaUsuarios);
        scrollUsuarios.setPreferredSize(new Dimension(1100, 400));
        scrollUsuarios.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        panelTablaUsuarios.add(scrollUsuarios);
        contentPanel.add(panelTablaUsuarios);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Panel centrado para el botón Eliminar
        JPanel panelBtnEliminar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBtnEliminar.setBackground(new Color(255, 255, 252));
        panelBtnEliminar.setBorder(new EmptyBorder(0, 15, 0, 0)); // Margen izquierdo 15px
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
                    try (Connection conexion = ConexionBD.conectar();
                         PreparedStatement stmt = conexion.prepareStatement("DELETE FROM users WHERE USR = ?")) {
                        stmt.setString(1, usuario);
                        stmt.executeUpdate();
                        JOptionPane.showMessageDialog(this, "Usuario eliminado correctamente.");
                        cargarUsuarios();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this, "Error al eliminar usuario: " + ex.getMessage());
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona un usuario para eliminar.");
            }
        });
        panelBtnEliminar.add(btnEliminarUsuario);
        contentPanel.add(panelBtnEliminar);

        // Scroll pane general que contiene contentPanel
        JScrollPane scrollGeneral = new JScrollPane(contentPanel,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        getContentPane().add(scrollGeneral, BorderLayout.CENTER);

        // Cargar datos
        cargarIncidencias();
        cargarUsuarios();
    }

    private void cargarIncidencias() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new String[]{"ID", "Estado", "Edificio", "Foto", "Piso", "Descripción",
                "Aula", "Justificación", "Fecha", "Campus", "Ranking", "Usuario"});

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement("SELECT * FROM incidencias");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                modelo.addRow(new Object[]{
                        rs.getInt("id_incidencia"),
                        rs.getString("estado"),
                        rs.getString("edificio"),
                        rs.getString("foto"),
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
            tablaIncidencias.setModel(modelo);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar incidencias: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void cargarUsuarios() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new String[]{"Usuario", "Nickname", "Rol", "Campus", "Contraseña", "Fecha", "Foto"});

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement("SELECT * FROM users");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                modelo.addRow(new Object[]{
                        rs.getString("USR"),
                        rs.getString("NICKNAME"),
                        rs.getString("ROL"),
                        rs.getString("campus"),
                        rs.getString("PWD"),
                        rs.getDate("fecha"),
                        rs.getString("foto")
                });
            }
            tablaUsuarios.setModel(modelo);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar usuarios: " + e.getMessage());
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
