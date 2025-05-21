package vista;

import controlador.Controlador;

import javax.swing.*;
import java.awt.*;

public class _10_PerfilUsuario extends JFrame {
    private Controlador controlador;
    private JTextField txtNombre, txtApellidos, txtCodigoPostal, txtCampus;

    public _10_PerfilUsuario() {
        setTitle("10 . Perfil de Usuario");
        setSize(1200, 900);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);
        getContentPane().setBackground(new Color(255, 255, 252));

        // ✅ Barra de navegación
        BarraNavegacion barra = new BarraNavegacion();
        barra.setUsuarioLogueado(true);              // Habilita enlaces funcionales
        barra.setControlador(controlador);           // Asigna listeners y lógica
        barra.setBounds(0, 0, 1200, 59);             // Asegura que se vea bien
        getContentPane().add(barra);



        barra.btnAtras.addActionListener(e -> {
            if (controlador != null) controlador.volverAtras(this);
            dispose();
        });

        // 📋 Título
        JLabel lblTitulo = new JLabel("Perfil de usuario", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblTitulo.setBounds(400, 80, 400, 30);
        getContentPane().add(lblTitulo);

        // 🖼 Imagen / avatar
        JLabel lblFoto = new JLabel("Foto");
        lblFoto.setBounds(100, 150, 150, 150);
        lblFoto.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        lblFoto.setHorizontalAlignment(SwingConstants.CENTER);
        getContentPane().add(lblFoto);

        JButton btnEditarFoto = new JButton("Editar Foto");
        btnEditarFoto.setBounds(100, 310, 150, 30);
        getContentPane().add(btnEditarFoto);

        // 📄 Campos de información
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(300, 150, 100, 30);
        getContentPane().add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(400, 150, 300, 30);
        getContentPane().add(txtNombre);

        JLabel lblApellidos = new JLabel("Apellidos:");
        lblApellidos.setBounds(300, 200, 100, 30);
        getContentPane().add(lblApellidos);

        txtApellidos = new JTextField();
        txtApellidos.setBounds(400, 200, 300, 30);
        getContentPane().add(txtApellidos);

        JLabel lblCodigoPostal = new JLabel("Código Postal:");
        lblCodigoPostal.setBounds(300, 250, 100, 30);
        getContentPane().add(lblCodigoPostal);

        txtCodigoPostal = new JTextField();
        txtCodigoPostal.setBounds(400, 250, 300, 30);
        getContentPane().add(txtCodigoPostal);

        JLabel lblCampus = new JLabel("Campus:");
        lblCampus.setBounds(300, 300, 100, 30);
        getContentPane().add(lblCampus);

        txtCampus = new JTextField();
        txtCampus.setBounds(400, 300, 300, 30);
        getContentPane().add(txtCampus);

        // 🔒 Botón cambiar contraseña
        JButton btnCambiarContrasena = new JButton("Cambiar Contraseña");
        btnCambiarContrasena.setBounds(400, 360, 300, 40);
        btnCambiarContrasena.setBackground(Color.GRAY);
        btnCambiarContrasena.setForeground(Color.WHITE);
        btnCambiarContrasena.setFont(new Font("Tahoma", Font.BOLD, 14));
        getContentPane().add(btnCambiarContrasena);

        // ❓ Ayuda flotante
        JButton btnAyuda = new JButton("?");
        btnAyuda.setBounds(1120, 750, 50, 50);
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
        Component[] components = getContentPane().getComponents();
        for (Component c : components) {
            if (c instanceof BarraNavegacion) {
                ((BarraNavegacion) c).setControlador(controlador);
            }
        }
    }
}
