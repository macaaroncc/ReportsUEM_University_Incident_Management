package vista;

import javax.swing.*;
import controlador.Controlador;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BarraNavegacion extends JPanel {
	private boolean usuarioLogueado = true;
	private Controlador controlador;
	public JLabel lblPGNPrincipal;
	public JLabel lblMisIncidencias;
	public JLabel lblNotificaciones;
	public JLabel lblUsuario;
	private JLabel lblAdminPanel;

	public void setUsuarioLogueado(boolean logueado) {
		this.usuarioLogueado = logueado;
	}

	public BarraNavegacion() {
		setLayout(null);
		setBackground(new Color(128, 0, 0));
		setBounds(0, 0, 1200, 59);

		lblPGNPrincipal = crearLink("Página Principal", 20);
		lblMisIncidencias = crearLink("Mis Incidencias", 180);
		lblNotificaciones = crearLink("Notificaciones", 350);

		lblAdminPanel = crearLink("Panel Administrador", 520);
		lblAdminPanel.setVisible(false);
		add(lblAdminPanel);

		lblAdminPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (controlador != null) {
					controlador.abrirPaginaAdmin();
				}
			}
		});

		lblUsuario = new JLabel("Usuario");
		lblUsuario.setForeground(Color.WHITE);
		lblUsuario.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblUsuario.setBounds(1109, 20, 70, 20);
		lblUsuario.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		add(lblUsuario);

		lblPGNPrincipal.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (controlador != null) {
					JFrame ventanaActual = (JFrame) SwingUtilities.getWindowAncestor(lblPGNPrincipal);
					controlador.abrirPaginaPrincipal(ventanaActual);
				}
			}
		});

		lblMisIncidencias.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (controlador != null) {
					JFrame ventanaActual = (JFrame) SwingUtilities.getWindowAncestor(lblMisIncidencias);
					controlador.abrirMisIncidencias(ventanaActual);
				}
			}
		});

		lblNotificaciones.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (controlador != null) {
					JFrame ventanaActual = (JFrame) SwingUtilities.getWindowAncestor(lblNotificaciones);
					controlador.abrirNotificaciones(ventanaActual);
				}
			}
		});
	}

	private JLabel crearLink(String texto, int x) {
		JLabel label = new JLabel(texto);
		label.setFont(new Font("Tahoma", Font.BOLD, 14));
		label.setForeground(Color.WHITE);
		label.setBounds(x, 20, 160, 20);
		label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		add(label);
		return label;
	}

	public void setControlador(Controlador controlador) {
	    this.controlador = controlador;

	    if (this.controlador != null && controlador.getUsuarioActual() != null) {
	    	lblUsuario.setText(controlador.getUsuarioActual());
	    	lblAdminPanel.setVisible(controlador.usuarioEsAdmin());
	    }

	    lblUsuario.addMouseListener(new MouseAdapter() {
	        @Override
	        public void mouseClicked(MouseEvent e) {
	            if (controlador != null) {
	                controlador.abrirPerfilUsuario();
	            }
	        }
	    });
	}
}
