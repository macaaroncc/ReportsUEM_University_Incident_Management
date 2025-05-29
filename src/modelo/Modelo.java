// @Author: Beatriz
package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Clase Modelo.
 * Representa la clase Modelo.
 */
public class Modelo {
    private int intentosFallidos = 0;
    
	public static String usuarioActual = null;
    
/**
 * Realiza la acción correspondiente.
 * @param email Correo electrónico del usuario.
 * @param pwd Cadena de texto.
 */
    public String autenticar(String email, String pwd) {
        // Simulación de base de datos con roles
        if (email.equalsIgnoreCase("admin@ejemplo.com") && pwd.equals("1234")) {
            intentosFallidos = 0;
            return "admin";
        }
        if (email.equalsIgnoreCase("usuario@ejemplo.com") && pwd.equals("1234")) {
            intentosFallidos = 0;
            return "usuario";
        }
        
        intentosFallidos++;
        if (intentosFallidos >= 3) {
            return "bloqueado";
        }
        return "incorrecto";
    }
    
	public String getUsuarioActual() {
		return usuarioActual;
	}
	
	public static byte[] obtenerFotoUsuario() {
	    try (Connection conn = ConexionBD.conectar()) {
	        String sql = "SELECT FOTO FROM USERS WHERE USR = ?";
	        PreparedStatement stmt = conn.prepareStatement(sql);
	        stmt.setString(1, usuarioActual + "@ueuropea.es");
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            return rs.getBytes("FOTO");
	        }
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	    return null;
	}


}
