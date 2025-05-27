package controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import controlador.Controlador;
import modelo.Modelo;
import vista._01_PGSinLogin;
import vista._02_Login;
import vista._06_PaginaPrincipal;
import vista._07_MisIncidencias;
import vista._08_CrearIncidencia;
import vista._09_Notificaciones;
import vista._12_PaginaAdmin;
import vista._13_Estadisticas;
import vista._14_Ayuda;

public class Main {
    public static void main(String[] args) {
    	// Conexión sql
    	String hostname = "localhost";
		String port = "";
		String database = "";
		String usrname = "root";
		String pwd = "";

		// try catch
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection Conexion = DriverManager.getConnection(
					"jdbc:mysql://" + hostname + ":" + port + "/" + database + "?useSSL=false", usrname, pwd);
			System.out.println("Todo bien");
		} catch (ClassNotFoundException e) {
			System.err.println("Error del diver");
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("Error de conexion");
			e.printStackTrace();
		}
		
        Modelo modelo = new Modelo();
        Controlador controlador = new Controlador();

        _13_Estadisticas vistaInicial = new _13_Estadisticas();
        vistaInicial.setControlador(controlador);

        controlador.setModelo(modelo);
        // Puedes crear más setters en Controlador si necesitas rastrear la vista actual

        vistaInicial.setVisible(true);
    }
}
