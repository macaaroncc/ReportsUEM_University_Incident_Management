package modelo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase para gestionar la conexión a la base de datos MySQL.
 * Lee la configuración desde un archivo externo y proporciona
 * métodos para establecer conexiones.
 */
public class ConexionBD {

    // Variables estáticas para almacenar los parámetros de conexión
    private static String URL = "";          // URL de conexión a la BD
    private static String USUARIO = "";      // Usuario de la BD 
    private static String CONTRASENA = "";   // Contraseña de la BD

    // Ruta del archivo de configuración (relativa al proyecto)
    private static final String CONFIG_PATH = "src/config/config.ini";

    // Bloque de inicialización estática que se ejecuta al cargar la clase
    static {
        cargarConfiguracion(); // Carga la configuración al iniciar
    }

    /**
     * Lee el archivo de configuración y carga los parámetros de conexión.
     * El formato esperado es:
     *   url: jdbc:mysql://servidor:puerto/basedatos
     *   usr: usuario
     *   pwd: contraseña
     */
    private static void cargarConfiguracion() {
        try (BufferedReader br = new BufferedReader(new FileReader(CONFIG_PATH))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                // Procesa cada línea del archivo de configuración
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

    /**
     * Establece y devuelve una conexión a la base de datos.
     * 
     * @return Connection objeto de conexión a la BD
     * @throws SQLException si ocurre algún error al conectar
     */
    public static Connection conectar() throws SQLException {
        // Carga el driver JDBC de MySQL
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("⚠ No se encontró el driver JDBC de MySQL.");
            e.printStackTrace();
        }

        // Valida que la configuración se cargó correctamente
        if (URL.isEmpty() || USUARIO.isEmpty()) {
            throw new SQLException("La configuración de la base de datos no se ha cargado correctamente.");
        }

        // Crea y retorna la conexión
        return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
    }

    /**
     * Vuelve a cargar la configuración desde el archivo.
     * Útil cuando se modifican los parámetros de conexión.
     */
    public static void recargarConfiguracion() {
        cargarConfiguracion();
    }
}