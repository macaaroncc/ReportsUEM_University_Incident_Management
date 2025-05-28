
package vista;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class _17_DetalleIncidencia extends JFrame {

    public _17_DetalleIncidencia(int idIncidencia) {
        setTitle("Detalle de la Incidencia");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        JTextArea areaDetalle = new JTextArea();
        areaDetalle.setEditable(false);
        areaDetalle.setFont(new Font("Monospaced", Font.PLAIN, 14));

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/proyecto_integrador", "root", "");
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM incidencias WHERE id_incidencia = ?")) {

            stmt.setInt(1, idIncidencia);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                StringBuilder sb = new StringBuilder();
                sb.append("ID: ").append(rs.getInt("id_incidencia")).append("\n");
                sb.append("Estado: ").append(rs.getString("estado")).append("\n");
                sb.append("Edificio: ").append(rs.getString("edificio")).append("\n");
                sb.append("Piso: ").append(rs.getString("piso")).append("\n");
                sb.append("Aula: ").append(rs.getString("aula")).append("\n");
                sb.append("Descripción: ").append(rs.getString("descripcion")).append("\n");
                sb.append("Justificación: ").append(rs.getString("justificacion")).append("\n");
                sb.append("Campus: ").append(rs.getString("campus")).append("\n");
                sb.append("Fecha: ").append(rs.getDate("fecha")).append("\n");
                sb.append("Ranking: ").append(rs.getInt("ranking")).append("\n");
                sb.append("Usuario: ").append(rs.getString("USR")).append("\n");

                areaDetalle.setText(sb.toString());
            } else {
                areaDetalle.setText("No se encontró la incidencia con ID: " + idIncidencia);
            }

        } catch (SQLException e) {
            areaDetalle.setText("Error al cargar los detalles de la incidencia:\n" + e.getMessage());
            e.printStackTrace();
        }

        getContentPane().add(new JScrollPane(areaDetalle), BorderLayout.CENTER);
    }
}
