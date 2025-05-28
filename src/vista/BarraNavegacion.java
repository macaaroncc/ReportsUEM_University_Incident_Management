package vista;

import javax.swing.*;
import controlador.Controlador;
import modelo.Modelo;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BarraNavegacion extends JPanel {
	private boolean usuarioLogueado = true;
	private Controlador controlador;
	public JLabel lblPGNPrincipal;
	public JLabel lblMisIncidencias;
	public JLabel lblNotificaciones;
	public JLabel lblFavoritos;
	public JLabel lblUsuario;
	private JLabel lblAdminPanel;
	private JLabel label;

	public void setUsuarioLogueado(boolean logueado) {
		this.usuarioLogueado = logueado;
	}

	public BarraNavegacion() {
		setLayout(null);
		ImageIcon icon = new ImageIcon(getClass().getResource("/img/LogoBlanco.png")); // Asegúrate de que la ruta sea correcta
		Image imagenEscalada = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		JLabel lblLogo = new JLabel(new ImageIcon(imagenEscalada));
		
		lblLogo.setBounds(10, 10, 40, 40); // Posición y tamaño
		add(lblLogo);
		setBackground(new Color(128, 0, 0));
		setBounds(0, 0, 1200, 59);
		
		lblPGNPrincipal = crearLink("Página Principal", 80);
		lblMisIncidencias = crearLink("Mis Incidencias", 240);
		lblNotificaciones = crearLink("Notificaciones", 410);

		lblFavoritos = crearLink("Favoritos", 580);
		add(lblFavoritos);

		lblAdminPanel = crearLink("Panel Administrador", 680);
		lblAdminPanel.setVisible(false);
		add(lblAdminPanel);

		lblUsuario = new JLabel("Usuario");
		lblUsuario.setForeground(Color.WHITE);
		lblUsuario.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblUsuario.setBounds(1109, 20, 70, 20);
		lblUsuario.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		add(lblUsuario);

		// Listeners de navegación
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

		lblFavoritos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (controlador != null) {
					JFrame ventanaActual = (JFrame) SwingUtilities.getWindowAncestor(lblFavoritos);
					controlador.abrirFavoritos(ventanaActual);
				}
			}
		});

		lblAdminPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (controlador != null) {
					JFrame ventanaActual = (JFrame) SwingUtilities.getWindowAncestor(lblAdminPanel);
					controlador.abrirPaginaAdmin(ventanaActual);
				}
			}
		});
	}

	private JLabel crearLink(String texto, int x) {
		label = new JLabel(texto);
		label.setFont(new Font("Tahoma", Font.BOLD, 14));
		label.setForeground(Color.WHITE);
		label.setBounds(x, 20, 160, 20);
		label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		add(label);
		return label;
	}

	public void setControlador(Controlador controlador) {
		this.controlador = controlador;

		if (this.controlador != null && Modelo.usuarioActual != null) {
			lblUsuario.setText(Modelo.usuarioActual);
			lblAdminPanel.setVisible(controlador.usuarioEsAdmin());
		}

		lblUsuario.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (controlador != null) {
					JFrame ventanaActual = (JFrame) SwingUtilities.getWindowAncestor(lblUsuario);
					controlador.abrirPerfilUsuario(ventanaActual);
				}
			}
		});
	}
}
