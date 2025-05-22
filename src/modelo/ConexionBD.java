package modelo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

	private static String URL = "";
	private static String USUARIO = "";
	private static String CONTRASENA = "";

	private static final String CONFIG_PATH = "src/config/config.ini";

	// Se ejecuta una vez cuando se carga la clase
	static {
		cargarConfiguracion();
	}

	// Método para cargar el archivo INI
	private static void cargarConfiguracion() {
		try (BufferedReader br = new BufferedReader(new FileReader(CONFIG_PATH))) {
			String linea;
			while ((linea = br.readLine()) != null) {
				if (linea.startsWith("usr")) {
					USUARIO = linea.substring(linea.indexOf(":") + 1).trim();
				} else if (linea.startsWith("pwd")) {
					CONTRASENA = linea.substring(linea.indexOf(":") + 1).trim();
				} else if (linea.startsWith("url")) {
					URL = linea.substring(linea.indexOf(":") + 1).trim();
				}
			}
		} catch (IOException e) {
			System.err.println("⚠ Error al leer el archivo de configuración: " + e.getMessage());
		}
	}

	// Método para obtener la conexión a la base de datos
	public static Connection conectar() throws SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.err.println("⚠ No se encontró el driver JDBC de MySQL.");
			e.printStackTrace();
		}

		if (URL.isEmpty() || USUARIO.isEmpty()) {
			throw new SQLException("La configuración de la base de datos no se ha cargado correctamente.");
		}

		System.out.println("Conectando con: " + URL);
		return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
	}

	// Si quieres permitir recargar los datos (ej. después de editar el INI)
	public static void recargarConfiguracion() {
		cargarConfiguracion();
	}
}