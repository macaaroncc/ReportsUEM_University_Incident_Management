// Vista actualizada: _06_PaginaPrincipal.java
package vista;

import controlador.BarraNavegacion;
import controlador.Controlador;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class _06_PaginaPrincipal extends JFrame {
    private JPanel contentPane;
    private JTable table;
    private JComboBox<String> comboBoxEstado;
    private JComboBox<String> comboBoxOrden;
    private JComboBox<String> comboBoxFecha;
    private Controlador controlador;
    private JScrollPane scrollPane;
    private JButton btnCrearIncidenciaInferior;
    private JButton btnAyuda;

    public _06_PaginaPrincipal() {
        setTitle("Página Principal - Usuario");
        setSize(1200, 900);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        // ✅ Agregamos la barra de navegación reutilizable
        BarraNavegacion barra = new BarraNavegacion(controlador);
        barra.setUsuarioLogueado(true);
        barra.setControlador(controlador);
        barra.setBounds(0, 0, 1200, 59);
        getContentPane().add(barra);



        // --- Filtros ---
        comboBoxEstado = new JComboBox<>(new String[]{"Estado", "Pendiente", "Resuelta", "En proceso"});
        comboBoxEstado.setBounds(73, 80, 150, 30);
        contentPane.add(comboBoxEstado);

        comboBoxOrden = new JComboBox<>(new String[]{"Orden de Relevancia", "Más relevante primero", "Menos relevante primero", "Más reciente primero"});
        comboBoxOrden.setBounds(250, 80, 200, 30);
        contentPane.add(comboBoxOrden);

        comboBoxFecha = new JComboBox<>();
        comboBoxFecha.setBounds(480, 80, 150, 30);
        comboBoxFecha.addItem("Fecha");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        comboBoxFecha.addItem("Hoy - " + sdf.format(new Date()));
        for (int i = 1; i <= 6; i++) {
            Date date = new Date(System.currentTimeMillis() - (i * 24L * 60 * 60 * 1000));
            comboBoxFecha.addItem("Hace " + i + " día(s) - " + sdf.format(date));
        }
        comboBoxFecha.addItem("Personalizado...");
        contentPane.add(comboBoxFecha);

        // --- Tabla de incidencias ---
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
        scrollPane.setBounds(57, 130, 1080, 600);
        contentPane.add(scrollPane);

        // --- Botón inferior "Crear Incidencia" ---
        btnCrearIncidenciaInferior = new JButton("Crear Incidencia");
        btnCrearIncidenciaInferior.setBounds((1200 - 200) / 2, 740, 200, 40);
        btnCrearIncidenciaInferior.setBackground(new Color(128, 0, 0));
        btnCrearIncidenciaInferior.setForeground(Color.WHITE);
        btnCrearIncidenciaInferior.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnCrearIncidenciaInferior.setFocusPainted(false);
        btnCrearIncidenciaInferior.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        contentPane.add(btnCrearIncidenciaInferior);

        btnCrearIncidenciaInferior.addActionListener(e -> {
            if (controlador != null) controlador.abrirCrearIncidencia();
        });

        // --- Botón ayuda ---
        btnAyuda = new JButton("?");
        btnAyuda.setBounds(1120, 740, 50, 50);
        btnAyuda.setBackground(new Color(128, 0, 0));
        btnAyuda.setForeground(Color.WHITE);
        btnAyuda.setFont(new Font("Arial", Font.BOLD, 20));
        btnAyuda.setFocusPainted(false);
        btnAyuda.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        contentPane.add(btnAyuda);

        btnAyuda.addActionListener(e -> {
            if (controlador != null) controlador.abrirAyuda();
        });
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
