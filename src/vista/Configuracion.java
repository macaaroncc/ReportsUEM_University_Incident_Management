package vista;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Configuracion extends JFrame {
    private JTextField usrField;
    private JTextField pwdField;
    private JTextField urlField;
    private JButton guardarButton;

    private static final String CONFIG_PATH = "config/config.ini";

    public Configuracion() {
        setTitle("Configuración de Base de Datos");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(4, 2, 10, 10));

        usrField = new JTextField();
        pwdField = new JTextField();
        urlField = new JTextField();
        guardarButton = new JButton("Guardar");

        add(new JLabel("Usuario:"));
        add(usrField);
        add(new JLabel("Contraseña:"));
        add(pwdField);
        add(new JLabel("URL:"));
        add(urlField);
        add(new JLabel()); // espacio vacío
        add(guardarButton);

        cargarDatos();

        guardarButton.addActionListener(e -> {
			try {
				guardarDatos();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

        setVisible(true);
    }

    private void cargarDatos() {
        try (BufferedReader br = new BufferedReader(new FileReader(CONFIG_PATH))) {
            usrField.setText(br.readLine().split(":")[1].trim());
            pwdField.setText(br.readLine().split(":")[1].trim());
            urlField.setText(br.readLine().split(":")[1].trim());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar el archivo de configuración: " + e.getMessage());
        }
    }

    private void guardarDatos() throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CONFIG_PATH))) {
            bw.write("usr : " + usrField.getText() + "\n");
            bw.write("pwd : " + pwdField.getText() + "\n");
            bw.write("url : " + urlField.getText() + "\n");
            JOptionPane.showMessageDialog(this, "Configuración guardada correctamente.");
        } }}