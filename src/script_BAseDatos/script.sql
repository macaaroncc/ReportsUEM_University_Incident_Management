-- Creamos la base de datos primero
CREATE DATABASE IF NOT EXISTS proyecto_integrador;
USE proyecto_integrador;

-- Desactivamos restricciones de claves foráneas temporalmente
SET foreign_key_checks = 0;

-- Borramos las tablas si existen 
DROP TABLE IF EXISTS favoritos;
DROP TABLE IF EXISTS notificar;
DROP TABLE IF EXISTS SEGURIDAD;
DROP TABLE IF EXISTS PREGUNTAS;
DROP TABLE IF EXISTS incidencias;
DROP TABLE IF EXISTS USERS;

-- Creamos las tablas
CREATE TABLE USERS (
    USR VARCHAR(200) PRIMARY KEY,
    NICKNAME VARCHAR(200) NOT NULL, 
    ROL CHAR(1) NOT NULL,
    campus VARCHAR(200), 
    PWD VARCHAR(200) NOT NULL,
    fecha DATE,
    foto VARCHAR(2000)
);

CREATE TABLE PREGUNTAS (
    id_pregunta INT PRIMARY KEY,
    TEXTO VARCHAR(2000) NOT NULL 
);

CREATE TABLE SEGURIDAD (
    USERS_USR VARCHAR(200),
    PREG1 INT NOT NULL,
    RESP1 VARCHAR(2000) NOT NULL,
    PREG2 INT NOT NULL,
    RESP2 VARCHAR(2000) NOT NULL,
    PRIMARY KEY (USERS_USR),
    FOREIGN KEY (USERS_USR) REFERENCES USERS(USR),
    FOREIGN KEY (PREG1) REFERENCES PREGUNTAS(id_pregunta),
    FOREIGN KEY (PREG2) REFERENCES PREGUNTAS(id_pregunta)
);

CREATE TABLE incidencias (
    id_incidencia INT PRIMARY KEY,
    estado VARCHAR(200) NOT NULL,
    edificio VARCHAR(200),
    foto VARCHAR(2000) NOT NULL,
    piso VARCHAR(200),
    descripcion VARCHAR(2000) NOT NULL,
    aula VARCHAR(200),
    justificacion VARCHAR(2000),
    fecha DATE NOT NULL,
    campus VARCHAR(200) NOT NULL,
    ranking INT NOT NULL,
    USR VARCHAR(200) NOT NULL,
    USERS_USR VARCHAR(200),
    FOREIGN KEY (USERS_USR) REFERENCES USERS(USR)
);

CREATE TABLE favoritos (
    incidencias_id_incidencia INT,
    USERS_USR VARCHAR(200),
    PRIMARY KEY (incidencias_id_incidencia, USERS_USR),
    FOREIGN KEY (USERS_USR) REFERENCES USERS(USR)
);

CREATE TABLE notificar (
    incidencias_id_incidencia INT,
    USERS_USR VARCHAR(200),
    PRIMARY KEY (incidencias_id_incidencia, USERS_USR),
    FOREIGN KEY (USERS_USR) REFERENCES USERS(USR)
);

-- Insertamos datos

-- Usuarios
INSERT INTO USERS (USR, NICKNAME, ROL, campus, PWD, fecha, foto) 
VALUES 
('juan001@ueuropea.es', 'juan001', 'N', 'Villaviciosa', 'clave123', '2005-01-15', 'FOTOJUAN(SALE FEO)'),
('sofia002@ueuropea.es', 'sofia002', 'Y', 'Alcobendas', 'clave456', NULL, NULL),
('lucia004@ueuropea.es', 'lucia004', 'N', 'Alcobendas', 'clave321', NULL, NULL),
('mario005@ueuropea.es', 'mario005', 'Y', 'Villaviciosa', 'clave654', NULL, NULL),
('ana789@ueuropea.es', 'ana789', 'N', 'Villaviciosa', 'clave888', NULL, NULL),
('luis321@ueuropea.es', 'luis321', 'N', 'Alcobendas', 'clave999', NULL, NULL),
('maria456@ueuropea.es', 'maria456', 'N', 'Alcobendas', 'clave777', NULL, NULL);

-- Preguntas de seguridad
INSERT INTO PREGUNTAS (id_pregunta, TEXTO) VALUES
(1, '¿Cuál es el nombre de tu mascota?'),
(2, '¿En qué ciudad naciste?'),
(3, '¿Cuál es el nombre de tu escuela primaria?'),
(4, '¿Cuál es tu película favorita?'),
(5, '¿Cómo se llama tu mejor amigo de la infancia?');

-- Rellenar
INSERT INTO SEGURIDAD (USERS_USR, PREG1, RESP1, PREG2, RESP2) VALUES
('juan001@ueuropea.es', 1, 'Firulais', 2, 'hola'),
('sofia002@ueuropea.es', 2, 'Madrid', 2, 'marcos'),
('lucia004@ueuropea.es', 4, 'Titanic', 2, 'Madrid'),
('mario005@ueuropea.es', 5, 'Alberto', 2, 'RESP2');

-- Incidencias 
INSERT INTO incidencias (id_incidencia, estado, edificio, foto, piso, descripcion, aula, justificacion, fecha, campus, ranking, USR, USERS_USR)
VALUES 
(103, 'Pendiente', 'A', 'fotoaulaA1', '1', 'Proyector dañado', '101A', NULL, '2025-01-20', 'Villaviciosa', 4, 'juan001@ueuropea.es', 'juan001@ueuropea.es'),
(104, 'En revisión', 'B', 'fotoascensor', '3', 'Ascensor descompuesto', 'Sin aula', NULL, '2025-01-18', 'Alcobendas', 3, 'maria456@ueuropea.es', 'maria456@ueuropea.es'),
(105, 'Solucionada', 'C', 'fotobiblioteca', '2', 'Falta de sillas en biblioteca', 'Sin aula', NULL, '2025-01-15', 'Villaviciosa', 5, 'ana789@ueuropea.es', 'ana789@ueuropea.es'),
(106, 'Pendiente', 'A', 'fotocafeteria', '0', 'Fallo en máquina de café', 'Cafetería', NULL, '2025-01-22', 'Alcobendas', 2, 'luis321@ueuropea.es', 'luis321@ueuropea.es'),
(107, 'Pendiente', 'D', 'foto_ventana_rota', '1', 'Ventana rota en pasillo', 'Pasillo D1', NULL, '2025-02-01', 'Villaviciosa', 3, 'juan001@ueuropea.es', 'juan001@ueuropea.es'),
(108, 'En revisión', 'B', 'foto_fuga_agua', '2', 'Fuga de agua en baños', 'Baños 2B', 'Necesita reparación urgente', '2025-02-02', 'Alcobendas', 4, 'sofia002@ueuropea.es', 'sofia002@ueuropea.es'),
(109, 'Solucionada', 'A', 'foto_puerta_taller', '0', 'Puerta de taller no cierra bien', 'Taller A0', 'Reparada con ajuste de bisagras', '2025-01-28', 'Villaviciosa', 5, 'ana789@ueuropea.es', 'ana789@ueuropea.es'),
(110, 'Pendiente', 'C', 'foto_luz_corredor', '3', 'Luz intermitente en corredor', 'Corredor C3', NULL, '2025-02-03', 'Alcobendas', 2, 'luis321@ueuropea.es', 'luis321@ueuropea.es'),
(111, 'En revisión', 'E', 'foto_aire_acondicionado', '1', 'Aire acondicionado no funciona', 'Aula E101', 'Requiere recarga de gas', '2025-02-04', 'Villaviciosa', 4, 'maria456@ueuropea.es', 'maria456@ueuropea.es'),
(112, 'Pendiente', 'D', 'foto_mostrador', '0', 'Mostrador de recepción dañado', 'Recepción', NULL, '2025-02-05', 'Alcobendas', 3, 'mario005@ueuropea.es', 'mario005@ueuropea.es'),
(113, 'Solucionada', 'A', 'foto_silla_rota', '2', 'Silla rota en aula', 'Aula A201', 'Reemplazada por nueva', '2025-01-30', 'Villaviciosa', 5, 'juan001@ueuropea.es', 'juan001@ueuropea.es'),
(114, 'Pendiente', 'B', 'foto_pizarra', '1', 'Pizarra digital no responde', 'Aula B102', NULL, '2025-02-06', 'Alcobendas', 3, 'sofia002@ueuropea.es', 'sofia002@ueuropea.es'),
(115, 'En revisión', 'C', 'foto_techo', '2', 'Mancha de humedad en techo', 'Aula C205', 'Posible filtración de agua', '2025-02-07', 'Villaviciosa', 4, 'ana789@ueuropea.es', 'ana789@ueuropea.es'),
(116, 'Solucionada', 'D', 'foto_puerta_principal', '0', 'Puerta principal atascada', 'Entrada principal', 'Aceitada y ajustada', '2025-01-31', 'Alcobendas', 5, 'luis321@ueuropea.es', 'luis321@ueuropea.es'),
(117, 'Pendiente', 'E', 'foto_extintor', '1', 'Extintor caducado', 'Pasillo E1', NULL, '2025-02-08', 'Villaviciosa', 1, 'maria456@ueuropea.es', 'maria456@ueuropea.es'),
(118, 'En revisión', 'A', 'foto_ordenador', '3', 'Ordenador no enciende', 'Sala informática A3', 'Problema con fuente de alimentación', '2025-02-09', 'Alcobendas', 4, 'mario005@ueuropea.es', 'mario005@ueuropea.es'),
(119, 'Solucionada', 'B', 'foto_radiador', '2', 'Radiador no calienta', 'Aula B203', 'Reparado circuito de calefacción', '2025-02-01', 'Villaviciosa', 5, 'juan001@ueuropea.es', 'juan001@ueuropea.es'),
(120, 'Pendiente', 'C', 'foto_basura', '0', 'Contenedor de basura roto', 'Patio', NULL, '2025-02-10', 'Alcobendas', 2, 'sofia002@ueuropea.es', 'sofia002@ueuropea.es'),
(121, 'En revisión', 'D', 'foto_cable', '1', 'Cable pelado en pared', 'Pasillo D1', 'Riesgo eléctrico', '2025-02-11', 'Villaviciosa', 3, 'ana789@ueuropea.es', 'ana789@ueuropea.es'),
(122, 'Solucionada', 'E', 'foto_estante', '2', 'Estante de libros desmontado', 'Biblioteca E2', 'Reinstalado correctamente', '2025-02-05', 'Alcobendas', 5, 'luis321@ueuropea.es', 'luis321@ueuropea.es'),
(123, 'Pendiente', 'A', 'foto_impresora', '1', 'Impresora atascada', 'Sala de profesores', NULL, '2025-02-12', 'Villaviciosa', 3, 'maria456@ueuropea.es', 'maria456@ueuropea.es'),
(124, 'En revisión', 'B', 'foto_inodoro', '0', 'Inodoro obstruido', 'Baños planta baja', 'Requiere desatascador profesional', '2025-02-13', 'Alcobendas', 4, 'mario005@ueuropea.es', 'mario005@ueuropea.es'),
(125, 'Solucionada', 'C', 'foto_ventilador', '3', 'Ventilador de techo suelto', 'Aula C301', 'Reafirmado con nuevos tornillos', '2025-02-07', 'Villaviciosa', 5, 'juan001@ueuropea.es', 'juan001@ueuropea.es'),
(126, 'Pendiente', 'D', 'foto_patio', '0', 'Bancos del patio dañados', 'Zona exterior', NULL, '2025-02-14', 'Alcobendas', 2, 'sofia002@ueuropea.es', 'sofia002@ueuropea.es');

-- Favoritos
INSERT INTO favoritos (incidencias_id_incidencia, USERS_USR) VALUES
(103, 'juan001@ueuropea.es'),
(104, 'sofia002@ueuropea.es'),
(105, 'lucia004@ueuropea.es'),
(106, 'mario005@ueuropea.es'),
(107, 'juan001@ueuropea.es'),
(108, 'sofia002@ueuropea.es'),
(109, 'ana789@ueuropea.es'),
(110, 'luis321@ueuropea.es'),
(111, 'maria456@ueuropea.es'),
(112, 'mario005@ueuropea.es'),
(113, 'juan001@ueuropea.es'),
(114, 'sofia002@ueuropea.es'),
(115, 'ana789@ueuropea.es'),
(116, 'luis321@ueuropea.es'),
(117, 'maria456@ueuropea.es'),
(118, 'mario005@ueuropea.es'),
(119, 'juan001@ueuropea.es'),
(120, 'sofia002@ueuropea.es'),
(121, 'ana789@ueuropea.es'),
(122, 'luis321@ueuropea.es'),
(123, 'maria456@ueuropea.es'),
(124, 'mario005@ueuropea.es'),
(125, 'juan001@ueuropea.es'),
(126, 'sofia002@ueuropea.es');

-- Notificar
INSERT INTO notificar (incidencias_id_incidencia, USERS_USR) VALUES
(103, 'juan001@ueuropea.es'),
(104, 'sofia002@ueuropea.es'),
(105, 'lucia004@ueuropea.es'),
(106, 'mario005@ueuropea.es'),
(107, 'juan001@ueuropea.es'),
(108, 'sofia002@ueuropea.es'),
(109, 'ana789@ueuropea.es'),
(110, 'luis321@ueuropea.es'),
(111, 'maria456@ueuropea.es'),
(112, 'mario005@ueuropea.es'),
(113, 'juan001@ueuropea.es'),
(114, 'sofia002@ueuropea.es'),
(115, 'ana789@ueuropea.es'),
(116, 'luis321@ueuropea.es'),
(117, 'maria456@ueuropea.es'),
(118, 'mario005@ueuropea.es'),
(119, 'juan001@ueuropea.es'),
(120, 'sofia002@ueuropea.es'),
(121, 'ana789@ueuropea.es'),
(122, 'luis321@ueuropea.es'),
(123, 'maria456@ueuropea.es'),
(124, 'mario005@ueuropea.es'),
(125, 'juan001@ueuropea.es'),
(126, 'sofia002@ueuropea.es');

-- Reactivamos las claves foráneas
SET foreign_key_checks = 1;
