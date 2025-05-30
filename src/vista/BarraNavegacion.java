package vista;

import javax.swing.*;
import controlador.Controlador;
import modelo.Modelo;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Barra de navegación superior de la aplicación.
 * Proporciona acceso a las principales secciones de la aplicación
 * y muestra información del usuario logueado.
 */
public class BarraNavegacion extends JPanel {
    // Estado de autenticación del usuario
    private boolean usuarioLogueado = true;
    
    // Referencia al controlador
    private Controlador controlador;
    
    // Componentes de la interfaz
    public JLabel lblPGNPrincipal;
    public JLabel lblMisIncidencias;
    public JLabel lblNotificaciones;
    public JLabel lblFavoritos;
    public JLabel lblUsuario;
    private JLabel lblAdminPanel;
    private JLabel label;

    /**
     * Establece el estado de autenticación del usuario.
     * @param logueado true si el usuario está logueado, false en caso contrario
     */
    public void setUsuarioLogueado(boolean logueado) {
        this.usuarioLogueado = logueado;
    }

    /**
     * Constructor que inicializa la barra de navegación.
     * Configura los elementos visuales y sus listeners.
     */
    public BarraNavegacion() {
        setLayout(null);
        
        // Configuración del logo
        ImageIcon icon = new ImageIcon(getClass().getResource("/img/LogoBlanco.png"));
        Image imagenEscalada = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        JLabel lblLogo = new JLabel(new ImageIcon(imagenEscalada));
        lblLogo.setBounds(10, 10, 40, 40);
        add(lblLogo);
        
        // Estilo de la barra
        setBackground(new Color(128, 0, 0)); // Color vino
        setBounds(0, 0, 1200, 59);

        // Creación de los enlaces de navegación
        lblPGNPrincipal = crearLink("Página Principal", 80);
        lblMisIncidencias = crearLink("Mis Incidencias", 240);
        lblNotificaciones = crearLink("Notificaciones", 410);
        lblFavoritos = crearLink("Favoritos", 580);
        lblAdminPanel = crearLink("Panel Administrador", 700);
        lblAdminPanel.setVisible(false); // Solo visible para admins
        
        // Configuración del área de usuario
        lblUsuario = new JLabel("Usuario");
        lblUsuario.setForeground(Color.WHITE);
        lblUsuario.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblUsuario.setBounds(1109, 20, 70, 20);
        lblUsuario.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(lblUsuario);

        // Configuración de listeners para navegación
        configurarListeners();
    }

    /**
     * Crea un enlace navegable con estilo consistente.
     * @param texto Texto a mostrar en el enlace
     * @param x Posición horizontal del enlace
     * @return JLabel configurado como enlace
     */
    private JLabel crearLink(String texto, int x) {
        label = new JLabel(texto);
        label.setFont(new Font("Tahoma", Font.BOLD, 14));
        label.setForeground(Color.WHITE);
        label.setBounds(x, 20, 160, 20);
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(label);
        return label;
    }

    /**
     * Configura los listeners para los eventos de clic en los enlaces.
     */
    private void configurarListeners() {
        lblPGNPrincipal.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                navegar(controlador::abrirPaginaPrincipal, lblPGNPrincipal);
            }
        });

        lblMisIncidencias.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                navegar(controlador::abrirMisIncidencias, lblMisIncidencias);
            }
        });

        lblNotificaciones.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                navegar(controlador::abrirNotificaciones, lblNotificaciones);
            }
        });

        lblFavoritos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                navegar(controlador::abrirFavoritos, lblFavoritos);
            }
        });

        lblAdminPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                navegar(controlador::abrirPaginaAdmin, lblAdminPanel);
            }
        });
    }

    /**
     * Método helper para manejar la navegación.
     * @param accion Método del controlador a ejecutar
     * @param componente Componente que originó el evento
     */
    private void navegar(java.util.function.Consumer<JFrame> accion, JLabel componente) {
        if (controlador != null) {
            JFrame ventanaActual = (JFrame) SwingUtilities.getWindowAncestor(componente);
            accion.accept(ventanaActual);
        }
    }

    /**
     * Establece el controlador y configura el usuario actual.
     * @param controlador Referencia al controlador principal
     */
    public void setControlador(Controlador controlador) {
        this.controlador = controlador;

        // Configura el nombre de usuario y visibilidad del panel admin
        if (this.controlador != null && Modelo.usuarioActual != null) {
            lblUsuario.setText(Modelo.usuarioActual);
            lblAdminPanel.setVisible(controlador.usuarioEsAdmin());
        }

        // Listener para el área de usuario
        lblUsuario.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (controlador != null) {
                    navegar(controlador::abrirPerfilUsuario, lblUsuario);
                } 
            }
        });
    }
}