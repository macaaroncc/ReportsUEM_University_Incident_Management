package controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import modelo.ConexionBD;
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
import vista._15_Favoritos;

public class Controlador {

	private Modelo modelo;
	private _02_Login _02_login;
	private _03_CrearCuenta _03_crearCuenta;
	private int intentosFallidos = 0;
	private boolean usuarioLogueado = false;





	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

	public void setVista(_02_Login vista, _03_CrearCuenta crearCuenta) {
		this._02_login = vista;
		this._03_crearCuenta = crearCuenta;
	}

	public void setUsuarioLogueado(boolean logueado) {
		this.usuarioLogueado = logueado;
	}

	private void mostrarVentana(JFrame vista) {
		vista.setVisible(true);
	}

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

	public void abrirPaginaPrincipal(JFrame ventanaActual) {
		_06_PaginaPrincipal pagina = new _06_PaginaPrincipal();
		pagina.setControlador(this);
		pagina.setVisible(true);
		if (ventanaActual != null) {
			ventanaActual.dispose();
		}
	}

	public void abrirMisIncidencias(JFrame ventanaActual) {
		_07_MisIncidencias vista = new _07_MisIncidencias();
		vista.setControlador(this);
		vista.setVisible(true);
		if (ventanaActual != null) {
			ventanaActual.dispose();
		}
	}

	public void abrirCrearIncidencia(JFrame ventanaActual) {
		_08_CrearIncidencia vista = new _08_CrearIncidencia();
		vista.setControlador(this);
		vista.setVisible(true);
		if (ventanaActual != null) {
			ventanaActual.dispose();
		}
	}

	public void abrirPaginaAdmin(JFrame ventanaActual) {
		_12_PaginaAdmin vista = new _12_PaginaAdmin();
		vista.setControlador(this);
		mostrarVentana(vista);
		ventanaActual.dispose();
	}

	public void abrirNotificaciones(JFrame ventanaActual) {
		_09_Notificaciones vista = new _09_Notificaciones();
		vista.setControlador(this);
		vista.setVisible(true);
		ventanaActual.dispose();
	}

	public void abrirAyuda() {
		_14_Ayuda vista = new _14_Ayuda();
		vista.setControlador(this);
		mostrarVentana(vista);
	}

	public void abrirLogin() {
		_02_Login login = new _02_Login();
		login.setControlador(this);
		setVista(login, null);
		mostrarVentana(login);
	}

	public void abrirRestContrasena() {
		_05_RestContrasena rest = new _05_RestContrasena();
		rest.setControlador(this);
		mostrarVentana(rest);
	}

	public void abrirPerfilUsuario(JFrame ventanaActual) {
		_10_PerfilUsuario perfil = new _10_PerfilUsuario();
		perfil.setControlador(this);
		perfil.setVisible(true);
		if (ventanaActual != null)
			ventanaActual.dispose();
	}

	public void validarLogin(String email, String password, JFrame vistaActual) {
		try {
			ConexionBD.recargarConfiguracion();

			try (Connection conn = ConexionBD.conectar()) {
				String sql = "SELECT ROL FROM USERS WHERE USR = ? AND PWD = ?";
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setString(1, email);
				stmt.setString(2, password);
				ResultSet rs = stmt.executeQuery();

				if (rs.next()) {
					intentosFallidos = 0;

					setUsuarioLogueado(true);

					if (email.contains("@")) {
						Modelo.usuarioActual = email.substring(0, email.indexOf('@'));
					} else {
						Modelo.usuarioActual = email;
					}

					vistaActual.dispose();

						abrirPaginaPrincipal(vistaActual);
				} else {
					intentosFallidos++;
					JOptionPane.showMessageDialog(vistaActual,
							"Usuario o contraseña incorrectos. Intento " + intentosFallidos + " de 3.");
					if (intentosFallidos >= 3) {
						JOptionPane.showMessageDialog(vistaActual,
								"Demasiados intentos fallidos. Cerrando aplicación.");
						System.exit(0);
					}
				}
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(vistaActual, "Error de conexión:\n" + ex.getMessage());
			ex.printStackTrace();
		}
	}
	

	public void registrarUsuario(String email, String password, String codigoAdmin, int preg1, int preg2, String resp1,
			String resp2, JFrame vistaActual) {
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

				String insertUserSQL = "INSERT INTO USERS (USR, PWD, ROL, NICKNAME) VALUES (?, ?, ?, ?)";
				PreparedStatement insertUserStmt = conn.prepareStatement(insertUserSQL);
				insertUserStmt.setString(1, email);
				insertUserStmt.setString(2, password);
				insertUserStmt.setString(3, rol);
				insertUserStmt.setString(4, nickname);
				insertUserStmt.executeUpdate();

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

	public boolean usuarioEsAdmin() {
		try (Connection conn = ConexionBD.conectar()) {
			String sql = "SELECT ROL FROM USERS WHERE NICKNAME = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, Modelo.usuarioActual);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return "Y".equalsIgnoreCase(rs.getString("ROL"));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

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

	// -------------------- NUEVOS MÉTODOS AÑADIDOS --------------------

	public String[] obtenerDatosPerfil() {
		try (Connection conn = ConexionBD.conectar()) {
			String sql = "SELECT FECHA, CAMPUS, USR FROM USERS WHERE NICKNAME = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, Modelo.usuarioActual); // El nickname

			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				String fecha = rs.getString("FECHA");
				String campus = rs.getString("CAMPUS");
				String email = rs.getString("USR");
				return new String[] { fecha, campus, email };
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new String[] { "", "", "" }; // Valores por defecto si falla
	}

	public void cerrarSesion() {
		setUsuarioLogueado(false);
		Modelo.usuarioActual = null;

		_02_Login login = new _02_Login();
		login.setControlador(this);
		login.setVisible(true);
	}

	public void actualizarPerfilUsuario(String fecha, String campus) {
		try (Connection conn = ConexionBD.conectar()) {
			String sql = "UPDATE USERS SET FECHA = ?, CAMPUS = ? WHERE USR = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);

			// FECHA: si está vacía, insertar NULL
			if (fecha == null || fecha.trim().isEmpty()) {
				stmt.setNull(1, java.sql.Types.DATE);
			} else {
				stmt.setDate(1, java.sql.Date.valueOf(fecha)); // formato YYYY-MM-DD
			}

			// CAMPUS: si está vacío, insertar NULL
			if (campus == null || campus.trim().isEmpty()) {
				stmt.setNull(2, java.sql.Types.VARCHAR);
			} else {
				stmt.setString(2, campus);
			}

			stmt.setString(3, Modelo.usuarioActual + "@gmail.com"); // o como sea el email completo
			stmt.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al actualizar perfil:\n" + ex.getMessage());
		}
	}
	
	public void abrirFavoritos(JFrame ventanaActual) {
	    ventanaActual.dispose();
	    _15_Favoritos favoritos = new _15_Favoritos();
	    favoritos.setControlador(this);
	    favoritos.setVisible(true);
	}


}
