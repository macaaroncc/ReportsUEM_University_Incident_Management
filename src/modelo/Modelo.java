// @Author: Beatriz
package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Clase Modelo que representa la capa de datos de la aplicación.
 * Gestiona la autenticación de usuarios y el acceso a sus datos.
 */
public class Modelo {
    // Contador de intentos fallidos de autenticación
    private int intentosFallidos = 0;
    
    // Variable estática que almacena el usuario actualmente logueado
    public static String usuarioActual = null;
    
    /**
     * Obtiene el nombre de usuario actualmente logueado.
     * 
     * @return String con el nombre de usuario o null si no hay sesión activa
     */
    public String getUsuarioActual() {
        return usuarioActual;
    }
    
    /**
     * Obtiene la foto de perfil del usuario actual desde la base de datos.
     * 
     * @return byte[] con los bytes de la imagen o null si no hay foto o ocurre error
     */
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