package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
	private static final String URL = "jdbc:mysql://localhost:3306/proyecto_integrador"; // ajusta si es necesario
	private static final String USUARIO = "root"; // cambia si usas otro
	private static final String CONTRASENA = ""; // pon tu contraseña aquí

	public static Connection conectar() throws SQLException {
		return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
	}
}
