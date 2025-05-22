package modelo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
	private static String URL;
	private static String USUARIO;
	private static String CONTRASENA;

	static {
		cargarConfiguracion();
	}

	private static void cargarConfiguracion() {
		try (BufferedReader br = new BufferedReader(new FileReader("config/dbconfig.ini"))) {
			USUARIO = br.readLine().split(":")[1].trim();
			CONTRASENA = br.readLine().split(":")[1].trim();
			URL = br.readLine().split(":")[1].trim();
		} catch (IOException e) {
			System.err.println("Error al leer el archivo de configuración: " + e.getMessage());
		}
	}

	public static Connection conectar() throws SQLException {
		return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
	}
}
