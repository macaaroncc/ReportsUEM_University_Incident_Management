// @Author: Beatriz
package modelo;

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

}
