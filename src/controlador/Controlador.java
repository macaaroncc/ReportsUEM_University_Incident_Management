package controlador;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

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

	public static DefaultTableModel cargarUsuarios() {
		DefaultTableModel modelo = new DefaultTableModel();
		modelo.setColumnIdentifiers(
				new String[] { "Usuario", "Nickname", "Rol", "Campus", "Contraseña", "Fecha", "Foto" });

		try (Connection conexion = ConexionBD.conectar();
				PreparedStatement stmt = conexion.prepareStatement("SELECT * FROM users");
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				modelo.addRow(new Object[] { rs.getString("USR"), rs.getString("NICKNAME"), rs.getString("ROL"),
						rs.getString("campus"), rs.getString("PWD"), rs.getDate("fecha"), rs.getString("foto") });
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return modelo;
	}

	public static DefaultTableModel cargarIncidencias() {
		DefaultTableModel modelo = new DefaultTableModel();
		modelo.setColumnIdentifiers(new String[] { "ID", "Estado", "Edificio", "Foto", "Piso", "Descripción", "Aula",
				"Justificación", "Fecha", "Campus", "Ranking", "Usuario" });

		try (Connection conexion = ConexionBD.conectar();
				PreparedStatement stmt = conexion.prepareStatement("SELECT * FROM incidencias");
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				modelo.addRow(new Object[] { rs.getInt("id_incidencia"), rs.getString("estado"),
						rs.getString("edificio"), rs.getString("foto"), rs.getString("piso"),
						rs.getString("descripcion"), rs.getString("aula"), rs.getString("justificacion"),
						rs.getDate("fecha"), rs.getString("campus"), rs.getInt("ranking"), rs.getString("USR") });
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return modelo;
	}

	public static DefaultTableModel buscarIncidencias(String estado, String usuario, String orden) {
		DefaultTableModel modelo = new DefaultTableModel();
		modelo.setColumnIdentifiers(new String[] { "ID", "Estado", "Edificio", "Foto", "Piso", "Descripción", "Aula",
				"Justificación", "Fecha", "Campus", "Ranking", "Usuario" });

		StringBuilder query = new StringBuilder("SELECT * FROM incidencias WHERE 1=1");
		List<Object> parametros = new ArrayList<>();

		if (estado != null && !estado.isEmpty()) {
			query.append(" AND estado LIKE ?");
			parametros.add("%" + estado + "%");
		}

		
		if (usuario != null && !usuario.isEmpty()) {
			query.append(" AND USR LIKE ?");
			parametros.add("%" + usuario + "%");
		}

		// Orden según el valor de 'orden'
		if (orden != null) {
			switch (orden) {
			case "Más votaciones":
				query.append(" ORDER BY ranking DESC");
				break;
			case "Menos votaciones":
				query.append(" ORDER BY ranking ASC");
				break;
			case "Más reciente":
				query.append(" ORDER BY fecha DESC");
				break;
			case "Orden de Relevancia":
				query.append(" ORDER BY fecha DESC"); // O el orden que definas como relevancia
				break;
			default:
				query.append(" ORDER BY fecha DESC"); // Orden por defecto
				break;
			}
		} else {
			query.append(" ORDER BY fecha DESC"); // Orden por defecto si orden es null
		}

		try (Connection conexion = ConexionBD.conectar();
				PreparedStatement stmt = conexion.prepareStatement(query.toString())) {

			for (int i = 0; i < parametros.size(); i++) {
				stmt.setObject(i + 1, parametros.get(i));
			}

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					modelo.addRow(new Object[] { rs.getInt("id_incidencia"), rs.getString("estado"),
							rs.getString("edificio"), rs.getString("foto"), rs.getString("piso"),
							rs.getString("descripcion"), rs.getString("aula"), rs.getString("justificacion"),
							rs.getDate("fecha"), rs.getString("campus"), rs.getInt("ranking"), rs.getString("USR") });
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return modelo;
	}

	public boolean eliminarUsuario(String usr) {
		try (Connection conexion = ConexionBD.conectar()) {
			// Desactivamos autocommit para hacer todo en una transacción
			conexion.setAutoCommit(false);

			// 1. Eliminar entradas en favoritos
			try (PreparedStatement stmtFav = conexion.prepareStatement("DELETE FROM favoritos WHERE USERS_USR = ?")) {
				stmtFav.setString(1, usr);
				stmtFav.executeUpdate();
			}

			// 2. Eliminar entradas en notificar
			try (PreparedStatement stmtNot = conexion.prepareStatement("DELETE FROM notificar WHERE USERS_USR = ?")) {
				stmtNot.setString(1, usr);
				stmtNot.executeUpdate();
			}

			// 3. Eliminar entradas en SEGURIDAD
			try (PreparedStatement stmtSeg = conexion.prepareStatement("DELETE FROM SEGURIDAD WHERE USERS_USR = ?")) {
				stmtSeg.setString(1, usr);
				stmtSeg.executeUpdate();
			}

			// 4. Actualizar la tabla incidencias para eliminar referencia del usuario o
			// poner a NULL
			// Si no quieres perder la incidencia, puedes poner USERS_USR a NULL si es
			// nullable
			try (PreparedStatement stmtInc = conexion
					.prepareStatement("UPDATE incidencias SET USERS_USR = NULL WHERE USERS_USR = ?")) {
				stmtInc.setString(1, usr);
				stmtInc.executeUpdate();
			}

			// 5. Finalmente eliminar el usuario
			try (PreparedStatement stmtUsr = conexion.prepareStatement("DELETE FROM USERS WHERE USR = ?")) {
				stmtUsr.setString(1, usr);
				int rows = stmtUsr.executeUpdate();

				conexion.commit(); // Confirmamos los cambios si todo va bien

				return rows > 0;
			} catch (SQLException e) {
				conexion.rollback(); // Si hay error, revertimos todo
				throw e;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	 public void obtenerPreguntasSeguridad(String email, JLabel lblP1, JLabel lblP2, JTextField r1, JTextField r2, JButton btnComprobar) {
	        try (Connection conn = ConexionBD.conectar();
	             PreparedStatement stmt = conn.prepareStatement("SELECT PREG1, PREG2 FROM SEGURIDAD WHERE USERS_USR = ?")) {

	            stmt.setString(1, email);
	            ResultSet rs = stmt.executeQuery();

	            if (rs.next()) {
	                int codigoPreg1 = rs.getInt("PREG1");
	                int codigoPreg2 = rs.getInt("PREG2");

	                lblP1.setText(mapearCodigoPregunta(codigoPreg1));
	                lblP2.setText(mapearCodigoPregunta(codigoPreg2));

	                lblP1.setVisible(true);
	                lblP2.setVisible(true);
	                r1.setVisible(true);
	                r2.setVisible(true);
	                btnComprobar.setVisible(true);
	            } else {
	                JOptionPane.showMessageDialog(null, "Correo no encontrado o sin preguntas de seguridad.");
	            }
	        } catch (SQLException e) {
	            JOptionPane.showMessageDialog(null, "Error al cargar preguntas:\n" + e.getMessage());
	            e.printStackTrace();
	        }
	    }

	    /**
	     * Traduce código numérico a texto descriptivo para las preguntas de seguridad.
	     */
	    private String mapearCodigoPregunta(int codigo) {
	        switch (codigo) {
	            case 1: return "¿Cuál es el nombre de tu primera mascota?";
	            case 2: return "¿En qué ciudad naciste?";
	            case 3: return "¿Cuál es el nombre de tu escuela primaria?";
	            case 4: return "¿Cuál es tu película favorita?";
	            case 5: return "¿Cómo se llama tu mejor amigo de la infancia?";
	            default: return "Pregunta desconocida";
	        }
	    }

	public void crearIncidencia(String edificio, byte[] fotoBytes, String piso, String descripcion, String aula, String campus) {
	    if (descripcion.isEmpty() || aula.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "Debe rellenar todos los campos obligatorios (descripción, aula) ");
	        return;
	    }

	    try (Connection conn = ConexionBD.conectar()) {
	        int nuevoId = obtenerMaxIdIncidencia() + 1;

	        String sql = "INSERT INTO INCIDENCIAS (id_incidencia, ESTADO, EDIFICIO, FOTO, PISO, DESCRIPCION, AULA, FECHA, CAMPUS, RANKING, USERS_USR, USR) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	        PreparedStatement stmt = conn.prepareStatement(sql);

	        stmt.setInt(1, nuevoId); // id_incidencia
	        stmt.setString(2, "En revisión");
	        stmt.setString(3, edificio);

	        if (fotoBytes != null) {
	            stmt.setBytes(4, fotoBytes); // FOTO como bytes
	        } else {
	            stmt.setNull(4, java.sql.Types.BLOB);
	        }

	        stmt.setString(5, piso);
	        stmt.setString(6, descripcion);
	        stmt.setString(7, aula);
	        stmt.setDate(8, java.sql.Date.valueOf(java.time.LocalDate.now()));
	        stmt.setString(9, campus);

	        String user = Modelo.usuarioActual != null ? Modelo.usuarioActual + "@ueuropea.es" : null;
	        stmt.setLong(10, 0); // RANKING, si no usas, lo pones null o 0
	        stmt.setString(11, user);
	        stmt.setString(12, user);

	        int filas = stmt.executeUpdate();
	        if (filas > 0) {
	            JOptionPane.showMessageDialog(null, "Incidencia creada correctamente");
	        } else {
	            JOptionPane.showMessageDialog(null, "Error al crear la incidencia.");
	        }
	    } catch (Exception ex) {
	        ex.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error al crear incidencia:\n" + ex.getMessage());
	    }
	}



	public int obtenerMaxIdIncidencia() {
		int maxId = 0;
		try (Connection conn = ConexionBD.conectar()) {
			String sql = "SELECT MAX(id_incidencia) AS max_id FROM incidencias";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				maxId = rs.getInt("max_id");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return maxId;
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

					// ** NUEVO: comprobar incidencias "Solucionada" para mostrar notificación **
					String sqlIncidenciasPendientes = "SELECT COUNT(*) FROM incidencias i "
							+ "JOIN notificar n ON i.id_incidencia = n.incidencias_id_incidencia "
							+ "WHERE n.USERS_USR = ? AND i.estado = 'Solucionada'";
					try (PreparedStatement stmtIncPend = conn.prepareStatement(sqlIncidenciasPendientes)) {
						stmtIncPend.setString(1, email);
						ResultSet rsIncPend = stmtIncPend.executeQuery();
						if (rsIncPend.next() && rsIncPend.getInt(1) > 0) {
							JOptionPane.showMessageDialog(vistaActual, "Tienes notificaciones pendientes",
									"Notificación", JOptionPane.INFORMATION_MESSAGE);
						}
						rsIncPend.close();
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
			String sql = "SELECT * FROM SEGURIDAD WHERE USERS_USR = ? AND RESP1 = ? AND RESP2 = ?";
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

	public String[] obtenerDatosPerfil() {
		try (Connection conn = ConexionBD.conectar()) {
			String sql = "SELECT FECHA, CAMPUS, USR FROM USERS WHERE USR = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, Modelo.usuarioActual + "@ueuropea.es"); // El nickname

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

	public boolean eliminarFavorito(String incidenciaId, String usuario) {
		String sql = "DELETE FROM favoritos WHERE incidencias_id_incidencia = ? AND USERS_USR = ?";

		try (Connection conexion = ConexionBD.conectar(); PreparedStatement stmt = conexion.prepareStatement(sql)) {

			stmt.setString(1, incidenciaId);
			stmt.setString(2, usuario);

			int filasAfectadas = stmt.executeUpdate();

			return filasAfectadas > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public void actualizarPerfilUsuario(String fecha, String campus) {
		try (Connection conn = ConexionBD.conectar()) {
			String sql = "UPDATE USERS SET FECHA = ?, CAMPUS = ? WHERE USR = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);

			if (fecha == null || fecha.trim().isEmpty()) {
				stmt.setNull(1, java.sql.Types.DATE);
			} else {
				stmt.setDate(1, java.sql.Date.valueOf(fecha)); // formato YYYY-MM-DD
			}

			if (campus == null || campus.trim().isEmpty()) {
				stmt.setNull(2, java.sql.Types.VARCHAR);
			} else {
				stmt.setString(2, campus);
			}

			stmt.setString(3, Modelo.usuarioActual + "@ueuropea.es");
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
