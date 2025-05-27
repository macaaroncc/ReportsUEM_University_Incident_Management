package vista;

import modelo.ConexionBD;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class _16_EditarIncidencia extends JFrame {
	private JComboBox<String> comboEstado;
	private JTextArea txtJustificacion;
	private JTextField txtRanking;
	private int idIncidencia;

	public _16_EditarIncidencia(int idIncidencia, String estado, String justificacion, int ranking) {
		this.idIncidencia = idIncidencia;

		setTitle("Editar Incidencia");
		setSize(400, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		getContentPane().setBackground(new Color(255, 255, 252));

		// Estado
		JLabel lblEstado = new JLabel("Estado:");
		lblEstado.setBounds(30, 30, 100, 25);
		getContentPane().add(lblEstado);

		comboEstado = new JComboBox<>(new String[] { "Pendiente", "En Revisión", "Resuelta" });
		comboEstado.setBounds(30, 60, 300, 30);
		comboEstado.setSelectedItem(estado);
		getContentPane().add(comboEstado);

		// Justificación
		JLabel lblJustificacion = new JLabel("Justificación:");
		lblJustificacion.setBounds(30, 110, 150, 25);
		getContentPane().add(lblJustificacion);

		txtJustificacion = new JTextArea(justificacion);
		txtJustificacion.setLineWrap(true);
		txtJustificacion.setWrapStyleWord(true);
		JScrollPane scrollJustificacion = new JScrollPane(txtJustificacion);
		scrollJustificacion.setBounds(30, 140, 300, 150);
		getContentPane().add(scrollJustificacion);

		// Ranking
		JLabel lblRanking = new JLabel("Ranking:");
		lblRanking.setBounds(30, 310, 100, 25);
		getContentPane().add(lblRanking);

		txtRanking = new JTextField(String.valueOf(ranking));
		txtRanking.setBounds(30, 340, 300, 30);
		getContentPane().add(txtRanking);

		// Botón actualizar
		JButton btnActualizar = new JButton("Actualizar Incidencia");
		btnActualizar.setBackground(new Color(128, 0, 0));
		btnActualizar.setForeground(Color.WHITE);
		btnActualizar.setBounds(30, 400, 300, 40);
		btnActualizar.setFocusPainted(false);
		getContentPane().add(btnActualizar);

		btnActualizar.addActionListener(e -> actualizarIncidencia());
	}

	private void actualizarIncidencia() {
		String nuevoEstado = comboEstado.getSelectedItem().toString();
		String nuevaJustificacion = txtJustificacion.getText();
		int nuevoRanking;

		try {
			nuevoRanking = Integer.parseInt(txtRanking.getText());
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Ranking debe ser un número entero.");
			return;
		}

		String sql = "UPDATE incidencias SET estado = ?, justificacion = ?, ranking = ? WHERE id_incidencia = ?";

		try (Connection conexion = ConexionBD.conectar();
			 PreparedStatement stmt = conexion.prepareStatement(sql)) {

			stmt.setString(1, nuevoEstado);
			stmt.setString(2, nuevaJustificacion);
			stmt.setInt(3, nuevoRanking);
			stmt.setInt(4, idIncidencia);

			int filasActualizadas = stmt.executeUpdate();

			if (filasActualizadas > 0) {
				JOptionPane.showMessageDialog(this, "Incidencia actualizada correctamente.");
				dispose(); // Cierra la ventana
			} else {
				JOptionPane.showMessageDialog(this, "No se pudo actualizar la incidencia.");
			}

		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, "Error al actualizar incidencia: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}
