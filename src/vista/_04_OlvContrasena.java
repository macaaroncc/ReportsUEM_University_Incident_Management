package vista;

import controlador.Controlador;

import javax.swing.*;
import java.awt.*;

public class _04_OlvContrasena extends JFrame {
    private Controlador controlador;

    private JTextField txtCorreo;
    private JLabel lblPregunta1, lblPregunta2;
    private JTextField txtRespuesta1, txtRespuesta2;
    private JButton btnValidarCorreo, btnComprobar;

    public _04_OlvContrasena() {
        setTitle("04 - Olvido de contraseña");
        setSize(1200, 900);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        getContentPane().setBackground(new Color(255, 255, 252));

        // Barra navegación si tienes (opcional)
        // BarraNavegacion barra = new BarraNavegacion();
        // barra.setUsuarioLogueado(false);
        // barra.setControlador(controlador);
        // barra.setBounds(0, 0, 1200, 59);
        // getContentPane().add(barra);

        // Botón Atrás
        JButton btnAtras = new JButton("◀ Atrás");
        btnAtras.setBounds(10, 10, 90, 30);
        btnAtras.setBackground(new Color(255, 255, 252));
        btnAtras.setFocusPainted(false);
        btnAtras.addActionListener(e -> {
            if (controlador != null) {
                controlador.abrirLogin();
            }
            dispose();
        });
        getContentPane().add(btnAtras);

        // Etiqueta y campo para correo
        JLabel lblCorreo = new JLabel("Correo electrónico:");
        lblCorreo.setBounds(400, 150, 200, 30);
        lblCorreo.setFont(new Font("Tahoma", Font.BOLD, 16));
        getContentPane().add(lblCorreo);

        txtCorreo = new JTextField();
        txtCorreo.setBounds(400, 190, 350, 35);
        txtCorreo.setFont(new Font("Tahoma", Font.PLAIN, 16));
        getContentPane().add(txtCorreo);

        btnValidarCorreo = new JButton("Validar Correo");
        btnValidarCorreo.setBounds(400, 240, 350, 40);
        btnValidarCorreo.setBackground(new Color(128, 0, 0));
        btnValidarCorreo.setForeground(Color.WHITE);
        btnValidarCorreo.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnValidarCorreo.setFocusPainted(false);
        getContentPane().add(btnValidarCorreo);

        // Pregunta 1
        lblPregunta1 = new JLabel("");
        lblPregunta1.setBounds(400, 320, 400, 25);
        lblPregunta1.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblPregunta1.setVisible(false);
        getContentPane().add(lblPregunta1);

        txtRespuesta1 = new JTextField();
        txtRespuesta1.setBounds(400, 350, 350, 30);
        txtRespuesta1.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtRespuesta1.setVisible(false);
        getContentPane().add(txtRespuesta1);

        // Pregunta 2
        lblPregunta2 = new JLabel("");
        lblPregunta2.setBounds(400, 400, 400, 25);
        lblPregunta2.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblPregunta2.setVisible(false);
        getContentPane().add(lblPregunta2);

        txtRespuesta2 = new JTextField();
        txtRespuesta2.setBounds(400, 430, 350, 30);
        txtRespuesta2.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtRespuesta2.setVisible(false);
        getContentPane().add(txtRespuesta2);

        // Botón comprobar respuestas
        btnComprobar = new JButton("Comprobar Respuestas");
        btnComprobar.setBounds(400, 490, 350, 40);
        btnComprobar.setBackground(new Color(128, 0, 0));
        btnComprobar.setForeground(Color.WHITE);
        btnComprobar.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnComprobar.setFocusPainted(false);
        btnComprobar.setVisible(false);
        getContentPane().add(btnComprobar);

        // Acción botón validar correo
        btnValidarCorreo.addActionListener(e -> {
            String email = txtCorreo.getText().trim();
            if (email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, introduce tu correo electrónico.");
                return;
            }
            controlador.obtenerPreguntasSeguridad(email, lblPregunta1, lblPregunta2, txtRespuesta1, txtRespuesta2, btnComprobar);
        });

        // Acción botón comprobar respuestas
        btnComprobar.addActionListener(e -> {
            String email = txtCorreo.getText().trim();
            String respuesta1 = txtRespuesta1.getText().trim();
            String respuesta2 = txtRespuesta2.getText().trim();

            if (respuesta1.isEmpty() || respuesta2.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, responde ambas preguntas de seguridad.");
                return;
            }

            controlador.comprobarPreguntasSeguridad(email, respuesta1, respuesta2, this);
        });
    }

    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }
}
