package vista;

import controlador.Controlador;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

public class _01_PGSinLogin extends JFrame {

    private Controlador controlador;
    private JTable table;
    private JComboBox<String> comboBoxEstado, comboBoxOrden, comboBoxFecha;
    private JTextField campoBusqueda;
    private JButton botonBuscar, btnCrearIncidencia, btnAyuda;
    private JScrollPane scrollPane;

    public _01_PGSinLogin() {
        setTitle("01 . Página Principal");
        setSize(1200, 900);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        getContentPane().setBackground(new Color(255, 255, 252));

        // 🔝 Barra de navegación con mismo estilo
        JPanel barra = new JPanel(null);
        barra.setBackground(new Color(128, 0, 0));
        barra.setBounds(0, 0, 1200, 59);
        getContentPane().add(barra);

        JButton btnAtras = new JButton("◀ Atrás");
        btnAtras.setBounds(10, 14, 90, 30);
        btnAtras.setFocusPainted(false);
        barra.add(btnAtras);

        Font fuente = new Font("Tahoma", Font.BOLD, 13);

        JLabel lblPGNPrincipal = crearNavLabel("Página Principal", 120, fuente);
        JLabel lblMisIncidencias = crearNavLabel("Mis Incidencias", 280, fuente);
        JLabel lblCrearIncidencia = crearNavLabel("Crear Incidencia", 440, fuente);
        JLabel lblNotificaciones = crearNavLabel("Notificaciones", 600, fuente);
        JLabel lblUsuario = crearNavLabel("Usuario", 1000, fuente);

        barra.add(lblPGNPrincipal);
        barra.add(lblMisIncidencias);
        barra.add(lblCrearIncidencia);
        barra.add(lblNotificaciones);
        barra.add(lblUsuario);

        MouseAdapter abrirLogin = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (controlador != null) controlador.abrirLogin();
                dispose();
            }
        };

        lblPGNPrincipal.addMouseListener(abrirLogin);
        lblMisIncidencias.addMouseListener(abrirLogin);
        lblCrearIncidencia.addMouseListener(abrirLogin);
        lblNotificaciones.addMouseListener(abrirLogin);
        lblUsuario.addMouseListener(abrirLogin);

        // ---------------- FILTROS Y BUSCADOR ----------------
        comboBoxEstado = new JComboBox<>(new String[]{"Estado", "Pendiente", "Resuelta", "En proceso"});
        comboBoxEstado.setBounds(40, 70, 150, 30);
        getContentPane().add(comboBoxEstado);

        comboBoxOrden = new JComboBox<>(new String[]{"Orden de Relevancia", "Más relevante primero", "Menos relevante primero", "Más reciente primero"});
        comboBoxOrden.setBounds(200, 70, 180, 30);
        getContentPane().add(comboBoxOrden);

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
        getContentPane().add(comboBoxFecha);

        campoBusqueda = new JTextField();
        campoBusqueda.setBounds(750, 70, 250, 30);
        getContentPane().add(campoBusqueda);

        botonBuscar = new JButton("Buscar");
        botonBuscar.setBounds(1010, 70, 100, 30);
        botonBuscar.setBackground(new Color(128, 0, 0));
        botonBuscar.setForeground(Color.WHITE);
        botonBuscar.setFont(new Font("Tahoma", Font.BOLD, 12));
        botonBuscar.setFocusPainted(false);
        getContentPane().add(botonBuscar);

        // ---------------- TABLA ----------------
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Imagen");
        model.addColumn("Título");
        model.addColumn("Estado");
        model.addColumn("Edificio");
        model.addColumn("Piso");
        model.addColumn("Aula");
        model.addColumn("Fecha");

        model.addRow(new Object[]{"Imagen", "Proyector dañado", "Pendiente", "A", "1", "101A", "2025-01-20"});
        model.addRow(new Object[]{"Imagen", "Silla rota", "Pendiente", "B", "2", "202B", "2025-02-15"});
        model.addRow(new Object[]{"Imagen", "Fuga de agua", "En proceso", "C", "3", "303C", "2025-03-10"});
        model.addRow(new Object[]{"Imagen", "Luz no funciona", "Resuelta", "D", "1", "104D", "2025-01-05"});

        table = new JTable(model);
        table.setRowHeight(70);
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(40, 120, 1110, 600);
        getContentPane().add(scrollPane);

        // ---------------- BOTÓN CREAR INCIDENCIA ----------------
        btnCrearIncidencia = new JButton("Crear Incidencia");
        btnCrearIncidencia.setBounds((getWidth() - 200) / 2, 740, 200, 40);
        btnCrearIncidencia.setBackground(new Color(128, 0, 0));
        btnCrearIncidencia.setForeground(Color.WHITE);
        btnCrearIncidencia.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnCrearIncidencia.setFocusPainted(false);
        getContentPane().add(btnCrearIncidencia);
        btnCrearIncidencia.addActionListener(e -> {
            if (controlador != null) controlador.abrirLogin();
            dispose();
        });

        // ---------------- BOTÓN AYUDA ----------------
        btnAyuda = new JButton("?");
        btnAyuda.setBounds(1120, 740, 50, 50);
        btnAyuda.setBackground(new Color(128, 0, 0));
        btnAyuda.setForeground(Color.WHITE);
        btnAyuda.setFont(new Font("Arial", Font.BOLD, 20));
        btnAyuda.setFocusPainted(false);
        getContentPane().add(btnAyuda);
        btnAyuda.addActionListener(e -> {
            if (controlador != null) controlador.abrirAyuda();
            dispose();
        });
    }

    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
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
