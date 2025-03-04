DROP TABLE IF EXISTS documentos_chunks CASCADE;
DROP TABLE IF EXISTS documentos_etiquetas CASCADE;
DROP TABLE IF EXISTS estadistica_documental CASCADE;
DROP TABLE IF EXISTS etiquetas CASCADE;
DROP TABLE IF EXISTS pregunta_documentchunk CASCADE;
DROP TABLE IF EXISTS documentchunks CASCADE;
DROP TABLE IF EXISTS preguntas CASCADE;
DROP TABLE IF EXISTS chats CASCADE;
DROP TABLE IF EXISTS usuarios_roles CASCADE;
DROP TABLE IF EXISTS usuarios_permisos CASCADE;
DROP TABLE IF EXISTS rol_permisos CASCADE;
DROP TABLE IF EXISTS bloqueo_cuentas CASCADE;
DROP TABLE IF EXISTS sesiones_activas CASCADE;
DROP TABLE IF EXISTS auditoria_eventos CASCADE;
DROP TABLE IF EXISTS intentos_login CASCADE;
DROP TABLE IF EXISTS refresh_tokens CASCADE;
DROP TABLE IF EXISTS verificationtoken CASCADE;
DROP TABLE IF EXISTS permisos CASCADE;
DROP TABLE IF EXISTS usuarios CASCADE;
DROP TABLE IF EXISTS roles CASCADE;
DROP TABLE IF EXISTS unidades_competencia CASCADE;
DROP TABLE IF EXISTS cuestionarios CASCADE;
DROP TABLE IF EXISTS respuestas CASCADE;
DROP TABLE IF EXISTS modulos CASCADE;
DROP TABLE IF EXISTS sectores CASCADE;
DROP TABLE IF EXISTS documentos CASCADE;
DROP TABLE IF EXISTS estado_acreditacion CASCADE;
DROP TABLE IF EXISTS tipo_pregunta CASCADE;
DROP TABLE IF EXISTS preguntas_cuestionarios CASCADE;
DROP TABLE IF EXISTS mensajes CASCADE; 
--DROP TABLE IF EXISTS categorias;

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
-- implementacion de tabla categorias por si en el proyecto 2025-2026 es necessario
-- -----------------------------------------------------
-- Tabla `categorias`
-- -----------------------------------------------------
-- CREATE TABLE IF NOT EXISTS categorias (
--     id SERIAL PRIMARY KEY,
--     nombre VARCHAR(100) UNIQUE NOT NULL
-- );

-- -----------------------------------------------------
-- Tabla `permisos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS permisos (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) UNIQUE NOT NULL,
    descripcion TEXT
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
-- Tabla `auditoria_eventos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS auditoria_eventos (
    id SERIAL PRIMARY KEY,
    usuario_id BIGINT,
    tipo_evento VARCHAR(20) CHECK (tipo_evento IN ('actividad', 'modificacion', 'login', 'seguridad', 'creacion', 'eliminacion')) NOT NULL,
    tabla_afectada VARCHAR(100) NULL,
    dato_anterior TEXT NULL,
    dato_nuevo TEXT NULL,
    descripcion TEXT NOT NULL,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE SET NULL
);
CREATE TABLE IF NOT EXISTS refresh_tokens (
    id SERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    token VARCHAR(500) UNIQUE NOT NULL,
    expiracion TIMESTAMP NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);

CREATE TABLE chats (
    id_chat BIGSERIAL PRIMARY KEY,
    "user" VARCHAR NOT NULL,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    contexto INT NOT NULL
);



CREATE TABLE etiquetas (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL UNIQUE
);

-- 2. Tablas con dependencias de las anteriores
CREATE TABLE preguntas (
    id_pregunta BIGSERIAL PRIMARY KEY,
    "user" VARCHAR NOT NULL,
    texto_pregunta TEXT NOT NULL,
    texto_respuesta TEXT DEFAULT ' ',
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    feedback VARCHAR(255) DEFAULT 'NORMAL',
    valorado BOOLEAN DEFAULT 'false', 
    id_chat BIGINT NOT NULL REFERENCES chats(id_chat) ON DELETE CASCADE
);


-- GRUPO ACREDITACION
CREATE TABLE sectores (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL
);

-- Tabla de módulos
CREATE TABLE modulos (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    sector_id INTEGER NOT NULL,
    nivel INTEGER CHECK (nivel BETWEEN 1 AND 3),  -- Nivel 1, 2 o 3
    FOREIGN KEY (sector_id) REFERENCES sectores(id) ON DELETE CASCADE
);

-- Tabla de unidades de competencia
CREATE TABLE unidades_competencia (
    id VARCHAR(10) PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    modulo_id INTEGER NOT NULL,
    FOREIGN KEY (modulo_id) REFERENCES modulos(id) ON DELETE CASCADE
);

-- Tabla de cuestionarios
CREATE TABLE cuestionarios (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    unidad_competencia_id VARCHAR(10),
    FOREIGN KEY (unidad_competencia_id) REFERENCES unidades_competencia(id) ON DELETE SET NULL
);

CREATE TABLE tipo_pregunta (
    id SERIAL PRIMARY KEY,            
    nombre VARCHAR(50) NOT NULL, 
    descripcion TEXT
);

-- Tabla de preguntas
CREATE TABLE preguntas_cuestionarios (
    id SERIAL PRIMARY KEY,            
    texto TEXT NOT NULL,                  
    tipo_id INT NOT NULL,                          
    siguiente_si INT,                          
    siguiente_no INT,                             
    final_si BOOLEAN DEFAULT FALSE,               
    final_no BOOLEAN DEFAULT FALSE,               
    explicacion_si TEXT,                          
    explicacion_no TEXT,                          
    orden INT NOT NULL,                           
    cuestionario_id INT NOT NULL,  
    FOREIGN KEY (tipo_id) REFERENCES tipo_pregunta(id), 
    FOREIGN KEY (siguiente_si) REFERENCES preguntas_cuestionarios(id), 
    FOREIGN KEY (siguiente_no) REFERENCES preguntas_cuestionarios(id),
    FOREIGN KEY (cuestionario_id) REFERENCES cuestionarios(id)  
);

-- Tabla de respuestas
CREATE TABLE respuestas (
    id SERIAL PRIMARY KEY,
    pregunta_id INTEGER NOT NULL,
    usuario_id INTEGER,  
    respuesta TEXT NOT NULL,
    FOREIGN KEY (pregunta_id) REFERENCES preguntas_cuestionarios(id) ON DELETE CASCADE,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE SET NULL
);

CREATE TABLE documentos (
    id SERIAL PRIMARY KEY,
    id_doc_rag INT,
    id_usuario INTEGER NOT NULL,
    nombre_fichero VARCHAR(500) NOT NULL,
    comentario TEXT,
    base64_documento TEXT,
    tipo_documento VARCHAR(100),
    extension_documento VARCHAR(5),
    content_type_documento VARCHAR(100),
    estado_documento VARCHAR(20) DEFAULT 'pendiente',
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_revision TIMESTAMP,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id) ON DELETE SET NULL
);
--Tabla
-- Tabla de estado de acreditación
CREATE TABLE estado_acreditacion (
    id SERIAL PRIMARY KEY,
    usuario_id INTEGER,
    asesor_id INTEGER,
    modulo_id INTEGER NOT NULL,
    estado VARCHAR(20) CHECK (estado IN ('pendiente', 'aprobado', 'rechazado')) NOT NULL,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    FOREIGN KEY (modulo_id) REFERENCES modulos(id) ON DELETE CASCADE
);

CREATE TABLE mensajes (
  id BIGSERIAL PRIMARY KEY,
  acreditacion_id BIGINT NOT NULL REFERENCES estado_acreditacion(id),
  usuario_id BIGINT NOT NULL REFERENCES usuarios(id),
  contenido TEXT NOT NULL,
  fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE documentos_chunks (
    id SERIAL PRIMARY KEY,
    id_documento INT NOT NULL REFERENCES documentos(id) ON DELETE CASCADE,
    id_doc_rag INT,
    chunk_order INTEGER NOT NULL,
    chunk_text TEXT NOT NULL,
    chunked_by BIGINT,
    estado VARCHAR(20) DEFAULT 'PENDIENTE',
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion TIMESTAMP
);

CREATE TABLE documentos_etiquetas (
    id_documento INT NOT NULL REFERENCES documentos(id) ON DELETE CASCADE,
    id_etiqueta INT NOT NULL REFERENCES etiquetas(id) ON DELETE CASCADE,
    PRIMARY KEY (id_documento, id_etiqueta)
);



--Tablas mixtas

--Documental + qualitat

CREATE TABLE pregunta_documentchunk (
    id_pregunta BIGINT NOT NULL REFERENCES preguntas(id_pregunta) ON DELETE CASCADE,
    id_documentchunk BIGINT NOT NULL REFERENCES documentos_chunks(id) ON DELETE CASCADE,
    PRIMARY KEY (id_pregunta, id_documentchunk)
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



-- Insertar permisos
INSERT INTO permisos (nombre, descripcion) VALUES
    ('EDITAR_USUARIO', 'Permite editar la información de los usuarios'),
    ('ELIMINAR_USUARIO', 'Permite eliminar usuarios'),
    ('VER_USUARIOS', 'Permite ver la lista de usuarios'),
    ('VER_USUARIO', 'Permite ver la información de un usuario'),
    ('ASIGNAR_ROLES', 'Permite asignar roles a los usuarios'),
    ('GESTIONAR_PERMISOS', 'Permite gestionar los permisos de los roles'),
    ('VER_AUDITORIA', 'Permite ver los registros de auditoría'),
    ('DESBLOQUEAR_CUENTAS', 'Permite desbloquear cuentas de usuario'),
    ('VER_CUENTAS_BLOQUEADAS', 'Permite ver las cuentas de usuario bloqueadas'),
    ('VER_PERMISOS_POR_ROL', 'Permite ver los permisos de los roles'),
    ('VER_PERMISOS', 'Permite ver los permisos existentes'),
    ('ASIGNAR_PERMISOS_A_ROL', 'Permite asignar permisos a los roles'),
    ('VER_USUARIOS_POR_ROL', 'Permite ver los usuarios que tienen un rol concreto'),
    ('CREAR_DOCUMENTO', 'Permite crear documentos'),
    ('EDITAR_DOCUMENTO', 'Permite editar documentos'),
    ('ELIMINAR_DOCUMENTO', 'Permite eliminar documentos'),
    ('VER_DOCUMENTOS', 'Permite ver documentos'),
    ('EDITAR_CHUNKS', 'Permite editar chunks'),
    ('VER_ESTADISTICAS', 'Permite ver estadísticas'),
    ('VER_ETIQUETAS', 'Permite ver etiquetas'),
    ('EDITAR_ETIQUETAS', 'Permite editar etiquetas'),
    ('ELIMINAR_ETIQUETAS', 'Permite eliminar etiquetas'),
    ('VER_SESIONES_ACTIVAS', 'Permite listar las sesiones activas'),
    ('CREAR_CHAT', 'Permite crear un chat'),
    ('VER_CHATS', 'Permite ver todos los chats'),
    ('VER_PREGUNTAS', 'Permite ver todas las preguntas'),
    ('ELIMINAR_CHAT', 'Permite eliminar un chat'),
    ('MODIFICAR_CHAT', 'Permite modificar un chat'),
    ('VER_LISTA_CONTEXTOS', 'Permite ver las listas de contextos'),
    ('CREAR_PREGUNTA', 'Permite crear preguntas'),
    ('MODIFICAR_PREGUNTA', 'Permite modificar preguntas'),
    ('CREAR_ACREDITACIONES', 'Permite crear acreditaciones'),
    ('CREAR_MENSAJE', 'Permite crear mensajes'),
    ('VER_CHUNKS', 'Permite ver chunks'),
    ('ELIMINAR_CHUNKS', 'Permite eliminar chunks'),
    ('CREAR_DOCUMENTO_USUARIO', 'Permite al usuario crear documentos'),
    ('ELIMINAR_DOCUMENTO_USUARIO', 'Permite al usuario eliminar documentos'),
    ('EDITAR_DOCUMENTO_USUARIO', 'Permite al usuario editar documentos'),
    ('VER_DOCUMENTOS_USUARIO', 'Permite al usuario ver documentos'),
    ('ASSIGNAR_ACREDITACIONES', 'Permite assignar acreditaciones'),
    ('VER_ACREDITACIONES', 'Permite ver acreditaciones');

INSERT INTO rol_permisos (rol_id, permiso_id) VALUES
    ((SELECT id FROM roles WHERE nombre = 'SUPERVISOR'), (SELECT id FROM permisos WHERE nombre = 'CREAR_CHAT')),
    ((SELECT id FROM roles WHERE nombre = 'SUPERVISOR'), (SELECT id FROM permisos WHERE nombre = 'VER_CHATS')),
    ((SELECT id FROM roles WHERE nombre = 'SUPERVISOR'), (SELECT id FROM permisos WHERE nombre = 'ELIMINAR_CHAT')),
    ((SELECT id FROM roles WHERE nombre = 'SUPERVISOR'), (SELECT id FROM permisos WHERE nombre = 'MODIFICAR_CHAT')),
    ((SELECT id FROM roles WHERE nombre = 'SUPERVISOR'), (SELECT id FROM permisos WHERE nombre = 'VER_LISTA_CONTEXTOS')),
    ((SELECT id FROM roles WHERE nombre = 'SUPERVISOR'), (SELECT id FROM permisos WHERE nombre = 'CREAR_PREGUNTA')),
    ((SELECT id FROM roles WHERE nombre = 'SUPERVISOR'), (SELECT id FROM permisos WHERE nombre = 'MODIFICAR_PREGUNTA')),
    ((SELECT id FROM roles WHERE nombre = 'SUPERVISOR'), (SELECT id FROM permisos WHERE nombre = 'VER_ESTADISTICAS')),
    ((SELECT id FROM roles WHERE nombre = 'SUPERVISOR'), (SELECT id FROM permisos WHERE nombre = 'VER_ETIQUETAS')),
    ((SELECT id FROM roles WHERE nombre = 'SUPERVISOR'), (SELECT id FROM permisos WHERE nombre = 'EDITAR_ETIQUETAS')),
    ((SELECT id FROM roles WHERE nombre = 'SUPERVISOR'), (SELECT id FROM permisos WHERE nombre = 'VER_PREGUNTAS')),
    ((SELECT id FROM roles WHERE nombre = 'SUPERVISOR'), (SELECT id FROM permisos WHERE nombre = 'VER_CHUNKS')),
    ((SELECT id FROM roles WHERE nombre = 'SUPERVISOR'), (SELECT id FROM permisos WHERE nombre = 'ELIMINAR_ETIQUETAS')),
    ((SELECT id FROM roles WHERE nombre = 'ACREDITADOR'), (SELECT id FROM permisos WHERE nombre = 'CREAR_ACREDITACIONES')),
    ((SELECT id FROM roles WHERE nombre = 'ACREDITADOR'), (SELECT id FROM permisos WHERE nombre = 'CREAR_MENSAJE')),
    ((SELECT id FROM roles WHERE nombre = 'ACREDITADOR'), (SELECT id FROM permisos WHERE nombre = 'VER_ACREDITACIONES')),
    ((SELECT id FROM roles WHERE nombre = 'USUARIO'), (SELECT id FROM permisos WHERE nombre = 'CREAR_ACREDITACIONES')),
    ((SELECT id FROM roles WHERE nombre = 'USUARIO'), (SELECT id FROM permisos WHERE nombre = 'CREAR_MENSAJE')),
    ((SELECT id FROM roles WHERE nombre = 'USUARIO'), (SELECT id FROM permisos WHERE nombre = 'VER_ACREDITACIONES')),
    ((SELECT id FROM roles WHERE nombre = 'USUARIO'), (SELECT id FROM permisos WHERE nombre = 'ELIMINAR_CHUNKS')),
    ((SELECT id FROM roles WHERE nombre = 'USUARIO'), (SELECT id FROM permisos WHERE nombre = 'VER_CHUNKS')),
    ((SELECT id FROM roles WHERE nombre = 'USUARIO'), (SELECT id FROM permisos WHERE nombre = 'EDITAR_CHUNKS')),
    ((SELECT id FROM roles WHERE nombre = 'USUARIO'), (SELECT id FROM permisos WHERE nombre = 'EDITAR_DOCUMENTO_USUARIO')),
    ((SELECT id FROM roles WHERE nombre = 'USUARIO'), (SELECT id FROM permisos WHERE nombre = 'CREAR_DOCUMENTO_USUARIO')),
    ((SELECT id FROM roles WHERE nombre = 'USUARIO'), (SELECT id FROM permisos WHERE nombre = 'ELIMINAR_DOCUMENTO_USUARIO')),
    ((SELECT id FROM roles WHERE nombre = 'USUARIO'), (SELECT id FROM permisos WHERE nombre = 'VER_DOCUMENTOS_USUARIO')),
    ((SELECT id FROM roles WHERE nombre = 'ADMINISTRADOR'), (SELECT id FROM permisos WHERE nombre = 'EDITAR_USUARIO')),
    ((SELECT id FROM roles WHERE nombre = 'ADMINISTRADOR'), (SELECT id FROM permisos WHERE nombre = 'ELIMINAR_USUARIO')),
    ((SELECT id FROM roles WHERE nombre = 'ADMINISTRADOR'), (SELECT id FROM permisos WHERE nombre = 'VER_USUARIOS')),
    ((SELECT id FROM roles WHERE nombre = 'ADMINISTRADOR'), (SELECT id FROM permisos WHERE nombre = 'VER_USUARIO')),
    ((SELECT id FROM roles WHERE nombre = 'ADMINISTRADOR'), (SELECT id FROM permisos WHERE nombre = 'ASIGNAR_ROLES')),
    ((SELECT id FROM roles WHERE nombre = 'ADMINISTRADOR'), (SELECT id FROM permisos WHERE nombre = 'GESTIONAR_PERMISOS')),
    ((SELECT id FROM roles WHERE nombre = 'ADMINISTRADOR'), (SELECT id FROM permisos WHERE nombre = 'VER_AUDITORIA')),
    ((SELECT id FROM roles WHERE nombre = 'ADMINISTRADOR'), (SELECT id FROM permisos WHERE nombre = 'DESBLOQUEAR_CUENTAS')),
    ((SELECT id FROM roles WHERE nombre = 'ADMINISTRADOR'), (SELECT id FROM permisos WHERE nombre = 'VER_CUENTAS_BLOQUEADAS')),
    ((SELECT id FROM roles WHERE nombre = 'ADMINISTRADOR'), (SELECT id FROM permisos WHERE nombre = 'VER_PERMISOS_POR_ROL')),
    ((SELECT id FROM roles WHERE nombre = 'ADMINISTRADOR'), (SELECT id FROM permisos WHERE nombre = 'VER_PERMISOS')),
    ((SELECT id FROM roles WHERE nombre = 'ADMINISTRADOR'), (SELECT id FROM permisos WHERE nombre = 'ASIGNAR_PERMISOS_A_ROL')),
    ((SELECT id FROM roles WHERE nombre = 'ADMINISTRADOR'), (SELECT id FROM permisos WHERE nombre = 'VER_USUARIOS_POR_ROL')),
    ((SELECT id FROM roles WHERE nombre = 'ADMINISTRADOR'), (SELECT id FROM permisos WHERE nombre = 'CREAR_DOCUMENTO')),
    ((SELECT id FROM roles WHERE nombre = 'ADMINISTRADOR'), (SELECT id FROM permisos WHERE nombre = 'EDITAR_DOCUMENTO')),
    ((SELECT id FROM roles WHERE nombre = 'ADMINISTRADOR'), (SELECT id FROM permisos WHERE nombre = 'ELIMINAR_DOCUMENTO')),
    ((SELECT id FROM roles WHERE nombre = 'ADMINISTRADOR'), (SELECT id FROM permisos WHERE nombre = 'VER_DOCUMENTOS')),
    ((SELECT id FROM roles WHERE nombre = 'ADMINISTRADOR'), (SELECT id FROM permisos WHERE nombre = 'VER_ESTADISTICAS')),
    ((SELECT id FROM roles WHERE nombre = 'ADMINISTRADOR'), (SELECT id FROM permisos WHERE nombre = 'VER_ETIQUETAS')),
    ((SELECT id FROM roles WHERE nombre = 'ADMINISTRADOR'), (SELECT id FROM permisos WHERE nombre = 'EDITAR_ETIQUETAS')),
    ((SELECT id FROM roles WHERE nombre = 'ADMINISTRADOR'), (SELECT id FROM permisos WHERE nombre = 'VER_SESIONES_ACTIVAS')),
    ((SELECT id FROM roles WHERE nombre = 'ADMINISTRADOR'), (SELECT id FROM permisos WHERE nombre = 'ELIMINAR_ETIQUETAS')),
    ((SELECT id FROM roles WHERE nombre = 'ADMINISTRADOR'), (SELECT id FROM permisos WHERE nombre = 'ELIMINAR_CHUNKS')),
    ((SELECT id FROM roles WHERE nombre = 'ADMINISTRADOR'), (SELECT id FROM permisos WHERE nombre = 'VER_CHUNKS')),
    ((SELECT id FROM roles WHERE nombre = 'ADMINISTRADOR'), (SELECT id FROM permisos WHERE nombre = 'EDITAR_CHUNKS')),
    ((SELECT id FROM roles WHERE nombre = 'ASESOR'), (SELECT id FROM permisos WHERE nombre = 'VER_DOCUMENTOS_USUARIO')),
    ((SELECT id FROM roles WHERE nombre = 'ASESOR'), (SELECT id FROM permisos WHERE nombre = 'ASSIGNAR_ACREDITACIONES')),
    ((SELECT id FROM roles WHERE nombre = 'ASESOR'), (SELECT id FROM permisos WHERE nombre = 'CREAR_MENSAJE'));
-- 1. Tablas base (sin dependencias)
