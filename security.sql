-- -----------------------------------------------------
-- TABLAS NECESARIAS PARA IMPLEMENTAR LA SEGURIDAD
-- -----------------------------------------------------

-- Eliminación de tablas en el orden correcto para evitar errores de dependencia
DROP TABLE IF EXISTS usuarios_permisos;
DROP TABLE IF EXISTS usuarios_roles;
DROP TABLE IF EXISTS permisos;
DROP TABLE IF EXISTS rol_permisos;
DROP TABLE IF EXISTS bloqueo_cuentas;
DROP TABLE IF EXISTS sesiones_activas;
DROP TABLE IF EXISTS auditoria_eventos;
DROP TABLE IF EXISTS intentos_login;
DROP TABLE IF EXISTS usuarios;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS categorias;

-- -----------------------------------------------------
-- Tabla `usuarios`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS usuarios (
  id BIGSERIAL PRIMARY KEY, 
  nombre VARCHAR(255) NOT NULL,
  nickname VARCHAR(255) NOT NULL UNIQUE, 
  email VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  telefono VARCHAR(15),
  fecha_nacimiento DATE,
  estado VARCHAR(20) CHECK (estado IN ('activo', 'inactivo', 'pendiente', 'suspendido')) DEFAULT 'pendiente',
  fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- -----------------------------------------------------
-- Tabla `roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS roles (
  id SERIAL PRIMARY KEY, 
  nombre VARCHAR(255) NOT NULL UNIQUE
);

-- -----------------------------------------------------
-- Tabla `usuarios_roles` (relación muchos a muchos)
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS usuarios_roles (
  id_usuario BIGINT NOT NULL,
  id_rol INTEGER NOT NULL,
  PRIMARY KEY (id_usuario, id_rol),
  CONSTRAINT usuarios_roles_fk_usuarios FOREIGN KEY (id_usuario)
    REFERENCES usuarios (id) ON DELETE CASCADE,
  CONSTRAINT usuarios_roles_fk_roles FOREIGN KEY (id_rol)
    REFERENCES roles (id) ON DELETE CASCADE
);

-- -----------------------------------------------------
-- Tabla `categorias`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS categorias (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) UNIQUE NOT NULL
);

-- -----------------------------------------------------
-- Tabla `permisos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS permisos (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) UNIQUE NOT NULL,
    descripcion TEXT,
    id_categoria INT,
    FOREIGN KEY (id_categoria) REFERENCES categorias(id) ON DELETE SET NULL
);
-- -----------------------------------------------------
-- Tabla `rol_permisos` (relación muchos a muchos entre roles y permisos)
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS rol_permisos (
    id SERIAL PRIMARY KEY,
    rol_id INT NOT NULL,
    permiso_id INT NOT NULL,
    FOREIGN KEY (rol_id) REFERENCES roles(id) ON DELETE CASCADE,
    FOREIGN KEY (permiso_id) REFERENCES permisos(id) ON DELETE CASCADE
);

-- -----------------------------------------------------
-- Tabla `usuarios_permisos` (asignación individual de permisos)
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS usuarios_permisos (
    id SERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    permiso_id INT NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    FOREIGN KEY (permiso_id) REFERENCES permisos(id) ON DELETE CASCADE
);

-- -----------------------------------------------------
-- Tabla `bloqueo_cuentas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS bloqueo_cuentas (
    id SERIAL PRIMARY KEY,
    usuario_id BIGINT UNIQUE NOT NULL,
    intentos_fallidos INT DEFAULT 0,
    bloqueado BOOLEAN DEFAULT FALSE,
    fecha_bloqueo TIMESTAMP NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);

-- -----------------------------------------------------
-- Tabla `sesiones_activas` (detalles de sesión)
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS sesiones_activas (
    id SERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    token_sesion VARCHAR(255) UNIQUE NOT NULL,
    ip_origen VARCHAR(45), -- IPv4 o IPv6
    dispositivo VARCHAR(255), -- Información del navegador o app
    fecha_inicio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_expiracion TIMESTAMP NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);

-- -----------------------------------------------------
-- Tabla `intentos_login` (registro de intentos de inicio de sesión)
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS intentos_login (
    id SERIAL PRIMARY KEY,
    usuario_id BIGINT,
    ip_origen VARCHAR(45),
    exito BOOLEAN NOT NULL,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE SET NULL
);

-- -----------------------------------------------------
-- Tabla `auditoria_eventos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS auditoria_eventos (
    id SERIAL PRIMARY KEY,
    usuario_id BIGINT,
    tipo_evento VARCHAR(20) CHECK (tipo_evento IN ('actividad', 'modificacion', 'login', 'seguridad')) NOT NULL,
    tabla_afectada VARCHAR(100) NULL,
    dato_anterior TEXT NULL,
    dato_nuevo TEXT NULL,
    descripcion TEXT NOT NULL,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE SET NULL
);



--------------------------------------------------------------
-- INSERTAR ROLES

INSERT INTO roles (nombre) VALUES
    ('USUARIO'),
    ('ADMINISTRADOR'),
    ('ASESOR'),
    ('SUPERVISOR'),
    ('ACREDITADOR'),
    ('EVALUADOR'),
    ('PROFESOR'),
    ('JEFEDPTO'),
    ('JEFEESTUDIOS');






SELECT * FROM usuarios;
SELECT * FROM roles;
SELECT * FROM usuarios_roles;

INSERT INTO usuarios_roles VALUES (2,2)


-- prueba
INSERT INTO usuarios (nombre, nickname, email, password, password_salt, telefono, fecha_nacimiento, estado) 
VALUES (
  'Edgar Tormo', 
  'Edgar123', 
  'Edgar.tormo@example.com', 
  'Edgar1234', 
  'pepito', 
  '1234567890', 
  '1990-05-15', 
  'activo'
);





SELECT * FROM roles;


INSERT INTO roles(nombre) VALUES ('USUARIO')
INSERT INTO roles(nombre) VALUES ('ADMINISTRADOR')
ALTER TABLE usuarios DROP COLUMN fechacreacion;
ALTER TABLE usuarios DROP COLUMN fechanacimiento;
SELECT column_name FROM information_schema.columns WHERE table_name = 'usuarios';
SELECT * FROM sesiones_activas;
DELETE FROM sesiones_activas WHERE usuario_id = (SELECT id FROM usuarios WHERE email = 'christianciscar@hotmail.com');
