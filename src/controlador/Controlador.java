package controlador;

import modelo.Modelo;
import vista._01_PGSinLogin;
import vista._02_Login;
import vista._03_CrearCuenta;
import vista._05_RestContrasena;
import vista._06_PaginaPrincipal;
import vista._07_MisIncidencias;
import vista._08_CrearIncidencia;
import vista._09_Notificaciones;
import vista._10_PerfilUsuario;
import vista._12_PaginaAdmin;
import vista._14_Ayuda;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;

import modelo.ConexionBD;

/**
 * Clase Controlador.
 * Representa la clase Controlador.
 */
public class Controlador {

	private Modelo modelo;
	private _02_Login _02_login;
	private _03_CrearCuenta _03_crearCuenta;
	private int intentosFallidos = 0;
	private boolean usuarioLogueado = false; // ✅ NUEVO: estado del usuario

/**
 * Establece el valor de modelo.
 * @param modelo Modelo de datos utilizado en la aplicación.
 */
	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}
	

/**
 * Establece el valor de vista.
 * @param vista Vista gráfica que se mostrará al usuario.
 * @param crearCuenta Parámetro de tipo _03_CrearCuenta.
 */
	public void setVista(_02_Login vista, _03_CrearCuenta crearCuenta) {
		this._02_login = vista;
		this._03_crearCuenta = crearCuenta;
	}

/**
 * Establece el valor de usuariologueado.
 * @param logueado Parámetro de tipo boolean.
 */
	public void setUsuarioLogueado(boolean logueado) {
		this.usuarioLogueado = logueado;
	}

	// ✅ Mostrar cualquier ventana
/**
 * Muestra una ventana o componente gráfico.
 * @param vista Vista gráfica que se mostrará al usuario.
 */
	private void mostrarVentana(JFrame vista) {
		vista.setVisible(true);
	}

	// ✅ Volver atrás según estado
	
	//cambio
/**
 * Vuelve a la ventana anterior o al estado anterior.
 * @param ventanaActual Ventana actual que se cierra o cambia.
 */
	public void volverAtras(JFrame ventanaActual) {
	    if (usuarioLogueado) {
	        _06_PaginaPrincipal vista = new _06_PaginaPrincipal();
	        vista.setControlador(this);
	        vista.setVisible(true);
	    } else {
	        _01_PGSinLogin vista = new _01_PGSinLogin();
	        vista.setControlador(this);
	        vista.setVisible(true);
	    }
	    if (ventanaActual != null) {
	        ventanaActual.dispose();
	    }
	}



	// ✅ Cerrar sesión
/**
 * Realiza la acción correspondiente.
 */
	public void cerrarSesion() {
		setUsuarioLogueado(false);
		_01_PGSinLogin vista = new _01_PGSinLogin();
		vista.setControlador(this);
		mostrarVentana(vista);
	}

	// 🔄 Métodos de navegación
/**
 * Realiza la acción correspondiente.
 * @param ventanaActual Ventana actual que se cierra o cambia.
 */
	public void abrirPaginaPrincipal(JFrame ventanaActual) {
	    _06_PaginaPrincipal pagina = new _06_PaginaPrincipal();
	    pagina.setControlador(this);
	    pagina.setVisible(true);
	    if (ventanaActual != null) {
	        ventanaActual.dispose();
	    }
	}


/**
 * Realiza la acción correspondiente.
 * @param ventanaActual Ventana actual que se cierra o cambia.
 */
	public void abrirMisIncidencias(JFrame ventanaActual) {
	    _07_MisIncidencias vista = new _07_MisIncidencias();
	    vista.setControlador(this);
	    vista.setVisible(true);
	    if (ventanaActual != null) {
	        ventanaActual.dispose();
	    }
	}


/**
 * Crea una nueva entidad o interfaz.
 * @param ventanaActual Ventana actual que se cierra o cambia.
 */
	public void abrirCrearIncidencia(JFrame ventanaActual) {
	    _08_CrearIncidencia vista = new _08_CrearIncidencia();
	    vista.setControlador(this);
	    vista.setVisible(true);
	    if (ventanaActual != null) {
	        ventanaActual.dispose();
	    }
	}
/**
 * Realiza la acción correspondiente.
 */
	public void abrirPerfilUsuario() {
		_10_PerfilUsuario perfil = new _10_PerfilUsuario();
		perfil.setControlador(this);
		mostrarVentana(perfil);
	}


/**
 * Realiza la acción correspondiente.
 */
	public void abrirPaginaAdmin() {
		_12_PaginaAdmin vista = new _12_PaginaAdmin();
		vista.setControlador(this);
		mostrarVentana(vista);
	}

/**
 * Realiza la acción correspondiente.
 * @param ventanaActual Ventana actual que se cierra o cambia.
 */
	public void abrirNotificaciones(JFrame ventanaActual) {
	    _09_Notificaciones vista = new _09_Notificaciones();
	    vista.setControlador(this);
	    vista.setVisible(true);
	    ventanaActual.dispose(); // Cierra la ventana actual
	}

/**
 * Realiza la acción correspondiente.
 */
	public void abrirAyuda() {
		_14_Ayuda vista = new _14_Ayuda();
		vista.setControlador(this);
		mostrarVentana(vista);
	}

/**
 * Realiza la acción correspondiente.
 */
	public void abrirLogin() {
		_02_Login login = new _02_Login();
		login.setControlador(this);
		setVista(login, null);
		mostrarVentana(login);
	}

/**
 * Realiza la acción correspondiente.
 */
	public void abrirRestContrasena() {
		_05_RestContrasena rest = new _05_RestContrasena();
		rest.setControlador(this);
		mostrarVentana(rest);
	}

/**
 * Realiza la acción correspondiente.
 * @param ventanaActual Ventana actual que se cierra o cambia.
 */
	public void abrirPerfilUsuario(JFrame ventanaActual) {
		_10_PerfilUsuario perfil = new _10_PerfilUsuario();
		perfil.setControlador(this);
		perfil.setVisible(true);
		if (ventanaActual != null) ventanaActual.dispose();
	}

	// ✅ LOGIN con lógica de rol
/**
 * Valida la información o credenciales del usuario.
 * @param email Correo electrónico del usuario.
 * @param password Contraseña del usuario.
 * @param vistaActual Vista gráfica que se mostrará al usuario.
 */
	public void validarLogin(String email, String password, JFrame vistaActual) {
		try (Connection conn = ConexionBD.conectar()) {
			String sql = "SELECT ROL FROM USERS WHERE USR = ? AND PWD = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, email);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				String rol = rs.getString("ROL");
				intentosFallidos = 0;

				setUsuarioLogueado(true); // ✅ marcaremos como logueado
				vistaActual.dispose();

				if (rol.equalsIgnoreCase("administrador")) {
					abrirPaginaAdmin();
				} else {
					abrirPaginaPrincipal(vistaActual);
				}
			} else {
				intentosFallidos++;
				JOptionPane.showMessageDialog(vistaActual,
						"Usuario o contraseña incorrectos. Intento " + intentosFallidos + " de 3.");
				if (intentosFallidos >= 3) {
					JOptionPane.showMessageDialog(vistaActual, "Demasiados intentos fallidos. Cerrando aplicación.");
					System.exit(0);
				}
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(vistaActual, "Error de conexión:\n" + ex.getMessage());
			ex.printStackTrace();
		}
	}

/**
 * Realiza la acción correspondiente.
 * @param email Correo electrónico del usuario.
 * @param password Contraseña del usuario.
 * @param codigoAdmin Código de verificación o autenticación.
 * @param preg1 Valor numérico entero.
 * @param preg2 Valor numérico entero.
 * @param resp1 Cadena de texto.
 * @param resp2 Cadena de texto.
 * @param vistaActual Vista gráfica que se mostrará al usuario.
 */
	public void registrarUsuario(String email, String password, String codigoAdmin, int preg1, int preg2, String resp1, String resp2, JFrame vistaActual) {
        if (email.isEmpty() || password.isEmpty() || resp1.isEmpty() || resp2.isEmpty()) {
            JOptionPane.showMessageDialog(vistaActual, "Debe rellenar todos los campos");
            return;
        }


		try (Connection conn = ConexionBD.conectar()) {
			String checkSQL = "SELECT USR FROM USERS WHERE USR = ?";
			PreparedStatement checkStmt = conn.prepareStatement(checkSQL);
			checkStmt.setString(1, email);
			ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(vistaActual, "El usuario ya existe");
            } else {
                String rol = codigoAdmin.equals("ADMIN123") ? "Y" : "N";
                String nickname = email.contains("@") ? email.substring(0, email.indexOf("@")) : email;

                // Insertar el nuevo usuario en la base de datos
                String insertUserSQL = "INSERT INTO USERS (USR, PWD, ROL, NICKNAME) VALUES (?, ?, ?, ?)";
                PreparedStatement insertUserStmt = conn.prepareStatement(insertUserSQL);
                insertUserStmt.setString(1, email);
                insertUserStmt.setString(2, password);
                insertUserStmt.setString(3, rol);
                insertUserStmt.setString(4, nickname);
                insertUserStmt.executeUpdate();

                // Insertar respuestas de seguridad y las preguntas seleccionadas
                String insertSeguridadSQL = "INSERT INTO SEGURIDAD (USERS_USR, PREG1, RESP1, PREG2, RESP2) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement insertSeguridadStmt = conn.prepareStatement(insertSeguridadSQL);
                insertSeguridadStmt.setString(1, email);
                insertSeguridadStmt.setInt(2, preg1);
                insertSeguridadStmt.setInt(4, preg2);
                insertSeguridadStmt.setString(3, resp1);
                insertSeguridadStmt.setString(5, resp2);
                insertSeguridadStmt.executeUpdate();

                JOptionPane.showMessageDialog(vistaActual, "Cuenta creada correctamente");
                abrirLogin();
                vistaActual.dispose();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vistaActual, "Error al registrar:\n" + ex.getMessage());
            ex.printStackTrace();
        }
    }

/**
 * Realiza la acción correspondiente.
 * @param email Correo electrónico del usuario.
 * @param resp1 Cadena de texto.
 * @param resp2 Cadena de texto.
 * @param vistaActual Vista gráfica que se mostrará al usuario.
 */
	public void comprobarPreguntasSeguridad(String email, String resp1, String resp2, JFrame vistaActual) {
		try (Connection conn = ConexionBD.conectar()) {
			String sql = "SELECT * FROM USERS WHERE USR = ? AND RESP1 = ? AND RESP2 = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, email);
			stmt.setString(2, resp1);
			stmt.setString(3, resp2);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				_05_RestContrasena rest = new _05_RestContrasena();
				rest.setControlador(this);
				rest.setUsuario(email);
				rest.setVisible(true);
				vistaActual.dispose();
			} else {
				JOptionPane.showMessageDialog(vistaActual, "Datos incorrectos. No se puede recuperar la contraseña.");
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(vistaActual, "Error de conexión:\n" + ex.getMessage());
			ex.printStackTrace();
		}
	}

/**
 * Realiza la acción correspondiente.
 * @param email Correo electrónico del usuario.
 * @param nuevaPwd Cadena de texto.
 * @param vistaActual Vista gráfica que se mostrará al usuario.
 */
	public void restablecerContrasena(String email, String nuevaPwd, JFrame vistaActual) {
		try (Connection conn = ConexionBD.conectar()) {
			String sql = "UPDATE USERS SET PWD = ? WHERE USR = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, nuevaPwd);
			stmt.setString(2, email);
			int filas = stmt.executeUpdate();

			if (filas > 0) {
				JOptionPane.showMessageDialog(vistaActual, "¡Contraseña actualizada correctamente!");
				abrirLogin();
				vistaActual.dispose();
			} else {
				JOptionPane.showMessageDialog(vistaActual, "No se pudo actualizar la contraseña.");
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(vistaActual, "Error al actualizar:\n" + ex.getMessage());
			ex.printStackTrace();
		}
	}
}
