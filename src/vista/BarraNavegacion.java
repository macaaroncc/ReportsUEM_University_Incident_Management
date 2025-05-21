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
	public JLabel lblCrearIncidencia;
	public JLabel lblNotificaciones;
	public JLabel lblUsuario;
	public JButton btnAtras;

	public void setUsuarioLogueado(boolean logueado) {
	    this.usuarioLogueado = logueado;
	}

	public BarraNavegacion() {
		setLayout(null);
		setBackground(new Color(128, 0, 0));
		setBounds(0, 0, 1200, 59);

		btnAtras = new JButton("◀ Atrás");
		btnAtras.setBounds(10, 17, 100, 30);
		btnAtras.setFocusPainted(false);
		btnAtras.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		btnAtras.setBackground(new Color(255, 255, 252));
		btnAtras.setForeground(Color.BLACK);
		add(btnAtras);
		
		//cambio 
		btnAtras.addActionListener(e -> {
			if (controlador != null) {
				JFrame ventanaActual = (JFrame) SwingUtilities.getWindowAncestor(this);
				controlador.volverAtras(ventanaActual);
			}
		});


		lblPGNPrincipal = crearLink("Página Principal", 130);
		lblMisIncidencias = crearLink("Mis Incidencias", 290);
		lblCrearIncidencia = crearLink("Crear Incidencia", 460);
		lblNotificaciones = crearLink("Notificaciones", 642);

		lblUsuario = new JLabel("Usuario");
		lblUsuario.setForeground(Color.WHITE);
		lblUsuario.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblUsuario.setBounds(1109, 20, 70, 20);
		lblUsuario.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		add(lblUsuario);

		// Eventos
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
		            // Obtener la ventana actual donde está el label
		            JFrame ventanaActual = (JFrame) SwingUtilities.getWindowAncestor(lblMisIncidencias);
		            controlador.abrirMisIncidencias(ventanaActual);
		        }
		    }
		});


		lblCrearIncidencia.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        if (controlador != null) {
		            JFrame ventanaActual = (JFrame) SwingUtilities.getWindowAncestor(lblCrearIncidencia);
		            controlador.abrirCrearIncidencia(ventanaActual);
		        }
		    }
		});


		lblNotificaciones.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        if (controlador != null) {
		            // Obtener la ventana actual (el JFrame que contiene este label)
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

		lblUsuario.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (controlador != null) {
					controlador.abrirPerfilUsuario(); // ✅ Método correcto
				}
			}
		});
	}
}
