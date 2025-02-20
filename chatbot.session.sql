/********************************************************************************
* Script de creación de tablas relacionadas con usuarios y permisos: *
* Tablas usuarios, roles, permisos, usuarios_roles y roles_permisos *
*********************************************************************************/

-- Usuarios del sistema
CREATE TABLE IF NOT EXISTS usuarios (
 id BIGSERIAL PRIMARY KEY,
 nombre VARCHAR(255) NOT NULL,
 email VARCHAR(255) NOT NULL UNIQUE, -- Identificador único para login
 password VARCHAR(255) NOT NULL, -- Almacenado con bcrypt
 estado VARCHAR(50) NOT NULL DEFAULT 'PENDIENTE',
 CONSTRAINT usuarios_estado_check CHECK (nombre IN (
 'PENDIENTE','ACTIVO','DESACTIVADO', 'BAJA')),
 fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE usuarios IS 'Almacena todos los usuarios del sistema con sus credenciales';
-- Para visualizar los comentarios sobre la tabla sesiones en PostgreSQL, puedes usar la siguiente consulta:
SELECT obj_description('usuarios'::regclass, 'pg_class');

/* Si ya teneís la tabla creada y queréis adaptarla podéis hacerlo con las siguientes sentencias SQL:
ALTER TABLE usuarios
ADD COLUMN estado VARCHAR(50) NOT NULL CHECK (estado IN ('PENDIENTE', 'ACTIVO', 'DESACTIVADO', 'BAJA')) DEFAULT 'PENDIENTE';

ALTER TABLE usuarios ALTER COLUMN nickname DROP NOT NULL;

ALTER TABLE usuarios
ADD COLUMN fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
*/



/* En una aplicación real al crear el usuario se quedaría PENDIENTE de confirmar el usuario por email, 
simplificando podeís darlo de alta con ACTIVO si no da tiempo a realizar la confirmación por email.
 

 Insertando usuarios de ejemplo para cada rol. Todos con la misma contraseña.
 La contraseña 'NoTeLoDigo@1' encriptada utilizandola secret-key=firmaSeguridadSimarro1DesarrolloWebEntornoServidor 
 almacenada en application.properties es '$2a$10$sYSmlH1l3VgSrcAOGlBst.HZor33pUHZYkawAIjVdpjXcmPkyvqJe'*/
INSERT INTO usuarios (nombre, email, password, estado) VALUES
('Candidato Apellido1 Apellido2', 'candidato@example.com', '$2a$10$sYSmlH1l3VgSrcAOGlBst.HZor33pUHZYkawAIjVdpjXcmPkyvqJe', 'ACTIVO'),
('Asesor Apellido1 Apellido2', 'asesor@example.com', '$2a$10$sYSmlH1l3VgSrcAOGlBst.HZor33pUHZYkawAIjVdpjXcmPkyvqJe', 'ACTIVO'),
('Evaluador Apellido1 Apellido2', 'evaluador@example.com', '$2a$10$sYSmlH1l3VgSrcAOGlBst.HZor33pUHZYkawAIjVdpjXcmPkyvqJe', 'ACTIVO'),
('Supervisor Apellido1 Apellido2', 'supervisor@example.com', '$2a$10$sYSmlH1l3VgSrcAOGlBst.HZor33pUHZYkawAIjVdpjXcmPkyvqJe', 'ACTIVO'),
('Profesor Apellido1 Apellido2', 'profesor@example.com', '$2a$10$sYSmlH1l3VgSrcAOGlBst.HZor33pUHZYkawAIjVdpjXcmPkyvqJe', 'ACTIVO'),
('JefeDpto Apellido1 Apellido2', 'jefedpto@example.com', '$2a$10$sYSmlH1l3VgSrcAOGlBst.HZor33pUHZYkawAIjVdpjXcmPkyvqJe', 'ACTIVO'),
('JefeEstudios Apellido1 Apellido2', 'jefeestudios@example.com', 'encrypted_passwo$2a$10$sYSmlH1l3VgSrcAOGlBst.HZor33pUHZYkawAIjVdpjXcmPkyvqJerd', 'ACTIVO'),
('Administrativo Apellido1 Apellido2', 'administrativo@example.com', '$2a$10$sYSmlH1l3VgSrcAOGlBst.HZor33pUHZYkawAIjVdpjXcmPkyvqJe', 'ACTIVO'),
('Administrador Apellido1 Apellido2', 'administrador@example.com', '$2a$10$sYSmlH1l3VgSrcAOGlBst.HZor33pUHZYkawAIjVdpjXcmPkyvqJe', 'ACTIVO');


-- En un entorno real habría una tabla especializada "detalles_candidato" con los atributos propios del usuario a nivel de candidato
-- pero no lo vamos a complicar más....


-- Comprobar usuarios creados:
SELECT * FROM usuarios;

-- Roles del sistema
CREATE TABLE IF NOT EXISTS roles (
 id SERIAL PRIMARY KEY,
 nombre VARCHAR(50) NOT NULL UNIQUE,
 descripcion VARCHAR(255) NOT NULL DEFAULT ' ',
 CONSTRAINT roles_nombre_check CHECK (nombre IN (
 'ROLE_CANDIDATO', 
 'ROLE_ASESOR',
 'ROLE_EVALUADOR',
 'ROLE_SUPERVISOR',
 'ROLE_PROFESOR',
 'ROLE_JEFEDPTO',
 'ROLE_JEFEESTUDIOS',
 'ROLE_ADMINISTRATIVO',
 'ROLE_ADMINISTRADOR',
 'ROLE_USER'
 ))
);

COMMENT ON TABLE roles IS 'Roles del sistema para control de acceso';
COMMENT ON COLUMN roles.nombre IS 'Nombre único del role según normativa del sistema';

/* 
Si la constraint ya existe y queréis modificarla, podéis hacerlo con las siguientes sentencias SQL:

Primero consultamos que exista:
SELECT conname, pg_get_constraintdef(oid) 
FROM pg_constraint 
WHERE conrelid = 'roles'::regclass AND conname = 'roles_nombre_check';

Si existe la podemos borrar:
ALTER TABLE roles
DROP CONSTRAINT roles_nombre_check;

Si queremos borrar roles que no queremos en la tabla roles, podemos hacerlo con la siguiente sentencia SQL:
DELETE FROM roles where nombre IN(
 'ROLE_ADMIN','ROLE_ENTRENADOR','ROLE_JUGADOR','ROLE_ARBITRO');

Y podemos añadir de nuevo la constraint con los roles de nuestra aplicación:
ALTER TABLE roles
ADD CONSTRAINT roles_nombre_check CHECK (nombre IN (
 'ROLE_CANDIDATO', 
 'ROLE_ASESOR',
 'ROLE_EVALUADOR',
 'ROLE_SUPERVISOR',
 'ROLE_PROFESOR',
 'ROLE_JEFEDPTO',
 'ROLE_JEFEESTUDIOS',
 'ROLE_ADMINISTRATIVO',
 'ROLE_ADMINISTRADOR',
 'ROLE_USER'
));

Y también el nuevo atributo:
ALTER TABLE roles 
ADD COLUMN descripcion VARCHAR(255) NOT NULL DEFAULT ' ';
 */


-- Añadimos los roles de usuario de la aplicación
ALTER TABLE roles
ADD CONSTRAINT roles_nombre_check CHECK (nombre IN (
 'ROLE_CANDIDATO', 
 'ROLE_ASESOR',
 'ROLE_EVALUADOR',
 'ROLE_SUPERVISOR',
 'ROLE_PROFESOR',
 'ROLE_JEFEDPTO',
 'ROLE_JEFEESTUDIOS',
 'ROLE_ADMINISTRATIVO',
 'ROLE_ADMINISTRADOR',
 'ROLE_USER'
));

-- Añadimos los roles de usuario de la aplicación con descripciones
INSERT INTO roles (nombre, descripcion) VALUES 
('ROLE_CANDIDATO', 'Usuario que solicita una acreditación'),
('ROLE_ASESOR', 'Provee orientación y asesoramiento en áreas específicas'),
('ROLE_EVALUADOR', 'Responsable de la evaluación de candidatos'),
('ROLE_SUPERVISOR', 'Supervisa tareas d asesores y evaluadores en la acreditación'),
('ROLE_PROFESOR', 'Impartir clases y educar a los estudiantes'),
('ROLE_JEFEDPTO', 'Dirige un departamento dentro del IES'),
('ROLE_JEFEESTUDIOS', 'Gestiona y supervisa programas educativos'),
('ROLE_ADMINISTRATIVO', 'Realiza tareas administrativas y de soporte'),
('ROLE_ADMINISTRADOR', 'Gestiona el sistema y tiene acceso a funcionalidades especiales de la aplicación'),
('ROLE_USER', 'Rol por defecto para usuarios no autenticados');

/* Si ya estuvieran creados los roles y queremos modificarlos, podemos hacerlo con la siguiente sentencia SQL:

UPDATE roles SET descripcion = 'Usuario que solicita una acreditación' WHERE nombre = 'ROLE_CANDIDATO';
UPDATE roles SET descripcion = 'Provee orientación y asesoramiento en áreas específicas' WHERE nombre = 'ROLE_ASESOR';
UPDATE roles SET descripcion = 'Responsable de la evaluación de candidatos' WHERE nombre = 'ROLE_EVALUADOR';
UPDATE roles SET descripcion = 'Supervisa tareas de asesores y evaluadores en la acreditación' WHERE nombre = 'ROLE_SUPERVISOR';
UPDATE roles SET descripcion = 'Impartir clases y educar a los estudiantes' WHERE nombre = 'ROLE_PROFESOR';
UPDATE roles SET descripcion = 'Dirige un departamento dentro del IES' WHERE nombre = 'ROLE_JEFEDPTO';
UPDATE roles SET descripcion = 'Gestiona y supervisa programas educativos' WHERE nombre = 'ROLE_JEFEESTUDIOS';
UPDATE roles SET descripcion = 'Realiza tareas administrativas y de soporte' WHERE nombre = 'ROLE_ADMINISTRATIVO';
UPDATE roles SET descripcion = 'Gestiona el sistema y tiene acceso a funcionalidades especiales de la aplicación' WHERE nombre = 'ROLE_ADMINISTRADOR';
UPDATE roles SET descripcion = 'Rol por defecto para usuarios no autenticados' WHERE nombre = 'ROLE_USER';
*/

-- Comprobar roles creados:
SELECT * FROM roles;

-- Relación entre usuarios y roles
CREATE TABLE IF NOT EXISTS usuarios_roles (
 idUsuario BIGINT NOT NULL,
 idRol INTEGER NOT NULL,
 PRIMARY KEY (idUsuario, idRol),
 CONSTRAINT usuarios_roles_fk_usuarios FOREIGN KEY (idUsuario)
 REFERENCES usuarios (id) ON DELETE CASCADE,
 CONSTRAINT usuarios_roles_fk_roles FOREIGN KEY (idRol)
 REFERENCES roles (id) ON DELETE CASCADE
);

-- Si queremos dejar vacia la relación entre usuarios y roles, podemos hacerlo con la siguiente sentencia SQL:
DELETE FROM usuarios_roles;

-- Asignación de roles a usuarios utilizando el email del usuario y el nombre del role que queremos darle
INSERT INTO usuarios_roles (idUsuario, idRol)
VALUES
 ((SELECT id FROM usuarios WHERE email = 'candidato@example.com'), 
 (SELECT id FROM roles WHERE nombre = 'ROLE_CANDIDATO')),

 ((SELECT id FROM usuarios WHERE email = 'asesor@example.com'), 
 (SELECT id FROM roles WHERE nombre = 'ROLE_ASESOR')),

 ((SELECT id FROM usuarios WHERE email = 'evaluador@example.com'), 
 (SELECT id FROM roles WHERE nombre = 'ROLE_EVALUADOR')),

 ((SELECT id FROM usuarios WHERE email = 'supervisor@example.com'), 
 (SELECT id FROM roles WHERE nombre = 'ROLE_SUPERVISOR')),

 ((SELECT id FROM usuarios WHERE email = 'profesor@example.com'), 
 (SELECT id FROM roles WHERE nombre = 'ROLE_PROFESOR')),

 ((SELECT id FROM usuarios WHERE email = 'jefedpto@example.com'), 
 (SELECT id FROM roles WHERE nombre = 'ROLE_JEFEDPTO')),

 ((SELECT id FROM usuarios WHERE email = 'jefeestudios@example.com'), 
 (SELECT id FROM roles WHERE nombre = 'ROLE_JEFEESTUDIOS')),

 ((SELECT id FROM usuarios WHERE email = 'administrativo@example.com'), 
 (SELECT id FROM roles WHERE nombre = 'ROLE_ADMINISTRATIVO')),

 ((SELECT id FROM usuarios WHERE email = 'administrador@example.com'), 
 (SELECT id FROM roles WHERE nombre = 'ROLE_ADMINISTRADOR'));


--Comprobar lo nombres de usuarios con sus roles asociados:
SELECT r.nombre AS rol, u.nombre AS usuario
FROM usuarios u
JOIN usuarios_roles ur ON u.id = ur.idUsuario
JOIN roles r ON ur.idRol = r.id
ORDER BY r.nombre, u.nombre;


-- Cada rol tendrá una serie de permisos asociados
CREATE TABLE IF NOT EXISTS permisos (
 id SERIAL PRIMARY KEY,
 nombre VARCHAR(255) NOT NULL UNIQUE,
 descripcion TEXT
);


-- AQUI FALTAR QUE PENSEIS QUE PERMISOS QUEREIS QUE TENGA CADA
-- EN FUNCIÓN DE LAS LLAMADAS A LOS ENDPOINTS Y LO QUE HAGA CADA
-- UNA

-- Tablas de relación entre roles y permisos
CREATE TABLE IF NOT EXISTS roles_permisos (
 rol_id INT REFERENCES roles(id),
 permiso_id INT REFERENCES permisos(id),
 PRIMARY KEY (rol_id, permiso_id)
);


-- AQUI FALTAR QUE PENSEIS QUE PERMISOS QUEREIS QUE TENGA CADA
-- EN FUNCIÓN DE LAS LLAMADAS A LOS ENDPOINTS Y LO QUE HAGA CADA
-- UNA

-- OPCIONAL: Tabla de relación entre jerarquias de roles para que se hereden permisos
CREATE TABLE IF NOT EXISTS relaciones_usuarios (
 id BIGSERIAL PRIMARY KEY,
 usuario_id BIGINT NOT NULL REFERENCES usuarios(id),
 usuario_relacionado_id BIGINT NOT NULL REFERENCES usuarios(id),
 tipo_relacion VARCHAR(50) NOT NULL,
 CONSTRAINT relaciones_usuarios_tipo_relacion CHECK (tipo_relacion IN 
 ('jefeestudios-asesor','jefedpto-asesor', 'asesor-candidato','evaluador-candidato',
 'jefeestudios-evaluador','jefedpto-evaluador','supervisor-asesor','supervisor-evaluador',
 'jefedpto-profesor','jefeestudios-jefedpto')),
 fecha_inicio TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Borra todos los registros de la tabla relaciones_usuarios. Por si hace falta limpiar la tabla
delete from relaciones_usuarios;

INSERT INTO relaciones_usuarios (usuario_id, usuario_relacionado_id, tipo_relacion)
VALUES
 -- Un Asesor está asignado a un Candidato
 ((SELECT id FROM usuarios WHERE email = 'asesor@example.com'), 
 (SELECT id FROM usuarios WHERE email = 'candidato@example.com'), 
 'asesor-candidato'),

 -- Un Evaluador está asignado a un Candidato
 ((SELECT id FROM usuarios WHERE email = 'evaluador@example.com'), 
 (SELECT id FROM usuarios WHERE email = 'candidato@example.com'), 
 'evaluador-candidato'),

 -- Un Supervisor supervisa a un Asesor y a un Evaluador
 ((SELECT id FROM usuarios WHERE email = 'supervisor@example.com'), 
 (SELECT id FROM usuarios WHERE email = 'asesor@example.com'), 
 'supervisor-asesor'),

 ((SELECT id FROM usuarios WHERE email = 'supervisor@example.com'), 
 (SELECT id FROM usuarios WHERE email = 'evaluador@example.com'), 
 'supervisor-evaluador'),

 -- Jefe de Departamento supervisa a Asesor, Evaluador y Profesor
 ((SELECT id FROM usuarios WHERE email = 'jefedpto@example.com'), 
 (SELECT id FROM usuarios WHERE email = 'asesor@example.com'), 
 'jefedpto-asesor'),

 ((SELECT id FROM usuarios WHERE email = 'jefedpto@example.com'), 
 (SELECT id FROM usuarios WHERE email = 'evaluador@example.com'), 
 'jefedpto-evaluador'),

 ((SELECT id FROM usuarios WHERE email = 'jefedpto@example.com'), 
 (SELECT id FROM usuarios WHERE email = 'profesor@example.com'), 
 'jefedpto-profesor'),

 -- Jefe de Estudios supervisa a Asesor y Evaluador
 ((SELECT id FROM usuarios WHERE email = 'jefeestudios@example.com'), 
 (SELECT id FROM usuarios WHERE email = 'asesor@example.com'), 
 'jefeestudios-asesor'),

 ((SELECT id FROM usuarios WHERE email = 'jefeestudios@example.com'), 
 (SELECT id FROM usuarios WHERE email = 'evaluador@example.com'), 
 'jefeestudios-evaluador'),

 -- Jefe de Estudios supervisa al Jefe de Departamento
 ((SELECT id FROM usuarios WHERE email = 'jefeestudios@example.com'), 
 (SELECT id FROM usuarios WHERE email = 'jefedpto@example.com'), 
 'jefeestudios-jefedpto');

-- Comprobar los nombres de usuarios con sus relaciones y usuarios relacionados
SELECT ru.tipo_relacion, 
 u1.nombre AS usuario, 
 u2.nombre AS usuario_relacionado
FROM relaciones_usuarios ru
JOIN usuarios u1 ON ru.usuario_id = u1.id
JOIN usuarios u2 ON ru.usuario_relacionado_id = u2.id
ORDER BY ru.tipo_relacion,u1.nombre, u2.nombre;



/********************************************************************************
* Script de creación de tablas relacionadas con la acreditación: *
* Tablas *
*********************************************************************************/


/*********************************************
* Ejemplo de tabla con atributo JSON *
*********************************************/


CREATE TABLE mi_tabla (
 id SERIAL PRIMARY KEY,
 atributo_json JSONB
);
DELETE FROM mi_tabla; -- Limpiar tabla
INSERT INTO mi_tabla (atributo_json) VALUES ('{"clave1": "valor1", "clave2": "valor2"}');
SELECT * FROM mi_tabla;
SELECT atributo_json->>'clave1' AS clave1 FROM mi_tabla;-- Devuelve 'valor1' como resultado de mostrar el valor para la clave 'clave1' en el JSON


/**************************************
* Gestión de Sesiones *
**************************************/

/* 
Es posible generar automáticamente los tokens UUID v4 en PostgreSQL al crear un registro. 
Para hacerlo, necesitas habilitar la extensión uuid-ossp y luego usar la función uuid_generate_v4() para generar el UUID. 
Aquí tienes los pasos:
 */
-- Habilitar la extensión uuid-ossp:
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

--DROP TABLE sesiones; 

--Usar uuid_generate_v4() en la tabla sesiones para usar en el campo token:
CREATE TABLE IF NOT EXISTS sesiones (
 id BIGSERIAL PRIMARY KEY,
 token UUID DEFAULT uuid_generate_v4() UNIQUE NOT NULL, -- UUID v4
 metadata JSONB, -- Metadatos de la sesión. De momento se puede dejar en blanco y que no aparezca en el DTO de entrada
 fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 fecha_ultima_accion TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Se actualiza cada vez que el usuario realiza una acció (contestar cuestionario, enviar doc,...)
 usuario_id BIGINT REFERENCES usuarios(id), -- Se actualiza al registrar usuario
 fecha_usuario TIMESTAMP -- Cuando se asocia a usuario real
);

COMMENT ON TABLE sesiones IS 'Sesiones que permiten usuarios no autenticados';



/**************************************
* Documentos *
**************************************/


CREATE TABLE documentos (
 id SERIAL PRIMARY KEY,
 nombre_fichero VARCHAR(255) NOT NULL,
 comentario TEXT,
 base64_documento TEXT,
 extension_documento VARCHAR(5), -- Ej: pdf
 content_type_cocumento VARCHAR(50), -- Tipo MIME del documento
 fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 creado_por_id BIGINT NOT NULL REFERENCES usuarios(id), -- Usuario que ha creado el documento
 fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Se actualiza cada vez que el usuario realiza una acció (modificar, chunk,...)
 modificado_por_id BIGINT NOT NULL REFERENCES usuarios(id), -- Usuario que ha modificado el documento
 documento_rag_id BIGINT -- En caso de que el documento este incorporado al RAG 
 );

COMMENT ON TABLE documentos IS 'Tabla que almacena los metadatos de los documentos subidos al sistema.';

COMMENT ON COLUMN documentos.id IS 'Identificador único del documento.';
COMMENT ON COLUMN documentos.nombre_fichero IS 'Nombre del archivo del documento.';
COMMENT ON COLUMN documentos.comentario IS 'Comentario opcional sobre el documento.';
COMMENT ON COLUMN documentos.base64_documento IS 'Contenido del documento codificado en base64.';
COMMENT ON COLUMN documentos.extension_documento IS 'Extensión del archivo del documento (ej. pdf).';
COMMENT ON COLUMN documentos.content_type_cocumento IS 'Tipo MIME del documento.';
COMMENT ON COLUMN documentos.fecha_creacion IS 'Fecha y hora en que se creó el documento.';
COMMENT ON COLUMN documentos.creado_por_id IS 'ID del usuario que creó el documento.';
COMMENT ON COLUMN documentos.fecha_modificacion IS 'Fecha y hora de la última modificación del documento.';
COMMENT ON COLUMN documentos.modificado_por_id IS 'ID del usuario que modificó el documento por última vez.';
COMMENT ON COLUMN documentos.documento_rag_id IS 'Identificador del documento si ha sido incorporado al sistema RAG (Retrieval Augmented Generation).';

-- Comprobar documentos:
SELECT * FROM documentos

/***************************************************
* Tablas relacionadas con las Competencias *
****************************************************/

/**************************************
* Familias Profesionales *
**************************************/

CREATE TABLE IF NOT EXISTS familias_profesionales (
 id SERIAL PRIMARY KEY,
 abreviatura VARCHAR(10) NOT NULL UNIQUE, -- Ej: IFC
 nombre VARCHAR(255) NOT NULL, -- Ej: Informática y Comunicaciones
 descripcion TEXT NOT NULL
);

COMMENT ON TABLE familias_profesionales IS 'Clasificación principal de áreas profesionales';
COMMENT ON COLUMN familias_profesionales.abreviatura IS 'Código identificativo corto de la familia';


-- Crear familia profesional IFC
INSERT INTO familias_profesionales (abreviatura, nombre, descripcion) VALUES
('IFC', 
 'Informática y Comunicaciones',
 'Acreditación de competencias en TIC según marco europeo') ;

-- Comprobar familias profesionales creadas:
 SELECT * FROM familias_profesionales;

/**************************************
* Cualificaciones *
**************************************/

CREATE TABLE IF NOT EXISTS cualificaciones (
 id SERIAL PRIMARY KEY,
 familia_profesional_id INT NOT NULL REFERENCES familias_profesionales(id),
 nivel INT NOT NULL CHECK (nivel BETWEEN 1 AND 3),
 estado VARCHAR(50) NOT NULL, -- Ej: BOE, Borrador, Retirado
 codigo VARCHAR(50) NOT NULL UNIQUE, -- Ej: IFC361_1
 nombre VARCHAR(500) NOT NULL, -- Ej: Operaciones auxiliares de montaje y mantenimiento de sistemas microinformáticos
 descripcion TEXT NOT NULL,
 publicacion VARCHAR(255), -- Ej: Orden PRE/1636/2015
 referencia_normativa VARCHAR(255), -- Ej: RD 1701/2007
 doc_portada BIGINT REFERENCES documentos(id),
 doc_glosario BIGINT REFERENCES documentos(id)
);

COMMENT ON TABLE cualificaciones IS 'Agrupaciones de unidades de competencia por nivel, representando cualificaciones profesionales.';

COMMENT ON COLUMN cualificaciones.id IS 'Identificador único de la cualificación.';
COMMENT ON COLUMN cualificaciones.familia_profesional_id IS 'Referencia a la familia profesional a la que pertenece la cualificación.';
COMMENT ON COLUMN cualificaciones.nivel IS 'Nivel de cualificación (1-3 según el Marco Español de Cualificaciones).';
COMMENT ON COLUMN cualificaciones.estado IS 'Estado actual de la cualificación (Ej: BOE - publicada en el Boletín Oficial del Estado, Borrador, Retirado).';
COMMENT ON COLUMN cualificaciones.codigo IS 'Código único identificativo de la cualificación (Ej: IFC361_1).';
COMMENT ON COLUMN cualificaciones.nombre IS 'Nombre completo de la cualificación (Ej: Operaciones auxiliares de montaje y mantenimiento de sistemas microinformáticos).';
COMMENT ON COLUMN cualificaciones.descripcion IS 'Descripción detallada de la cualificación.';
COMMENT ON COLUMN cualificaciones.publicacion IS 'Referencia a la publicación oficial donde se ha promulgado la cualificación (Ej: Orden PRE/1636/2015).';
COMMENT ON COLUMN cualificaciones.referencia_normativa IS 'Referencia normativa que respalda la cualificación (Ej: RD 1701/2007 - Real Decreto).';
COMMENT ON COLUMN cualificaciones.doc_portada IS 'Identificador del documento que contiene la portada de la cualificación.';
COMMENT ON COLUMN cualificaciones.doc_glosario IS 'Identificador del documento que contiene el glosario de términos específicos de la cualificación.';

-- Recomenable crear índices para mejorar la velocidad de las consultas
CREATE INDEX idx_cualificaciones_familia ON cualificaciones(familia_profesional_id);
CREATE INDEX idx_cualificaciones_familia_estado ON cualificaciones(familia_profesional_id,estado);

-- Crear cualificación IFC361_1
INSERT INTO cualificaciones (
 familia_profesional_id,
 nivel,
 estado,
 codigo,
 nombre,
 descripcion,
 publicacion,
 referencia_normativa
) VALUES (
 (SELECT id FROM familias_profesionales WHERE abreviatura = 'IFC'),
 1,
 'BOE',
 'IFC361_1',
 'Operaciones auxiliares de montaje y mantenimiento de sistemas microinformáticos',
 'Realizar operaciones auxiliares de montaje y mantenimiento de sistemas microinformáticos, aplicando criterios de calidad y actuando en condiciones de seguridad y respeto al medio ambiente, siguiendo instrucciones y procedimientos establecidos.',
 'Orden PRE/1636/2015',
 'RD 1701/2007'
);

-- Comprobar cualificaciones creadas:
SELECT * FROM cualificaciones;

/**************************************
* Unidades de Competencia *
**************************************/

CREATE TABLE IF NOT EXISTS unidades_competencia (
 id SERIAL PRIMARY KEY,
 cualificacion_id INT NOT NULL REFERENCES cualificaciones(id),
 codigo VARCHAR(50) NOT NULL UNIQUE, -- Ej: UC1207_1
 nombre VARCHAR(500) NOT NULL, -- Ej: Realizar operaciones auxiliares de montaje de sistemas microinformáticos
 descripcion TEXT NOT NULL,
 doc_guia_evidencia BIGINT REFERENCES documentos(id),
 doc_cuestionario_autoevaluacion BIGINT REFERENCES documentos(id)
);

COMMENT ON TABLE unidades_competencia IS 'Unidades mínimas acreditables de competencia profesional que forman parte de una cualificación.';

COMMENT ON COLUMN unidades_competencia.id IS 'Identificador único de la unidad de competencia.';
COMMENT ON COLUMN unidades_competencia.cualificacion_id IS 'Referencia a la cualificación a la que pertenece esta unidad de competencia.';
COMMENT ON COLUMN unidades_competencia.codigo IS 'Código único identificativo de la unidad de competencia (Ej: UC1207_1).';
COMMENT ON COLUMN unidades_competencia.nombre IS 'Nombre descriptivo de la unidad de competencia (Ej: Realizar operaciones auxiliares de montaje de sistemas microinformáticos).';
COMMENT ON COLUMN unidades_competencia.descripcion IS 'Descripción detallada de las actividades, conocimientos y habilidades que abarca la unidad de competencia.';
COMMENT ON COLUMN unidades_competencia.doc_guia_evidencia IS 'Identificador del documento que contiene la guía para la recolección de evidencias de competencia para esta unidad.';
COMMENT ON COLUMN unidades_competencia.doc_cuestionario_autoevaluacion IS 'Identificador del documento que contiene el cuestionario de autoevaluación para esta unidad de competencia.';

-- Ejemplo de inserción de una unidad de competencia:
-- Crear unidad de competencia UC1207_1
INSERT INTO unidades_competencia (
 cualificacion_id,
 codigo,
 nombre,
 descripcion
) VALUES (
 (SELECT id FROM cualificaciones WHERE codigo = 'IFC361_1'),
 'UC1207_1',
 'Realizar operaciones auxiliares de montaje de sistemas microinformáticos',
 'La persona candidata demostrará el dominio práctico relacionado con las actividades profesionales que intervienen en Realizar operaciones auxiliares de montaje de sistemas microinformáticos, y que se indican a continuación:\n· Realizar operaciones auxiliares de montaje y sustitución de
componentes hardware de un sistema microinformático, utilizando la documentación técnica del fabricante y siguiendo instrucciones recibidas para conseguir un equipo funcional.\n· Realizar operaciones auxiliares de montaje, sustitución y
conexión de dispositivos externos conectados a un sistema microinformático, siguiendo especificaciones técnicas recibidas, para facilitar el uso y la transmisión de la información con el sistema microinformático.\n· Realizar tareas de monitorización con programas de diagnóstico,
siguiendo guías detalladas e instrucciones recibidas, para verificar la operatividad del equipo microinformático y sus dispositivos externos.\n· Técnicas de montaje, sustitución y conexión de componentes
hardware internos (memoria, procesador, entre otros) y dispositivos externos informáticos (impresora, monitor, webcam, entre otros) '
);

/********************************************
* Gestión de Acreditaciones *
*********************************************/

CREATE TABLE IF NOT EXISTS acreditaciones (
 id BIGSERIAL PRIMARY KEY,
 candidato_id BIGINT NOT NULL REFERENCES usuarios(id),
 asesor_id BIGINT REFERENCES usuarios(id),
 evaluador_id BIGINT REFERENCES usuarios(id),
 estado VARCHAR(50) NOT NULL DEFAULT 'PENDIENTE' 
 CONSTRAINT acreditaciones_estados CHECK (estado IN ('PENDIENTE', 'ACTIVA', 'APROBADA', 'DENEGADA', 'CANCELADA')),
 fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
 fecha_modificacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
 modificado_por BIGINT NOT NULL REFERENCES usuarios(id)
);

COMMENT ON TABLE acreditaciones IS 'Registro central del proceso de acreditación';
COMMENT ON COLUMN acreditaciones.candidato_id IS 'Usuario candidato (obligatorio)';
COMMENT ON COLUMN acreditaciones.asesor_id IS 'Usuario asesor asignado';
COMMENT ON COLUMN acreditaciones.evaluador_id IS 'Usuario evaluador asignado';
COMMENT ON COLUMN acreditaciones.estado IS 'Estado actual del proceso';
COMMENT ON COLUMN acreditaciones.fecha_modificacion IS 'Última actualización del estado';
COMMENT ON COLUMN acreditaciones.modificado_por IS 'Usuario que realizó la última modificación';

-- Recomenable crear índices para mejorar la velocidad de las consultas
CREATE INDEX idx_acreditaciones_estado ON acreditaciones(estado);
CREATE INDEX idx_acreditaciones_candidato ON acreditaciones(candidato_id);
CREATE INDEX idx_acreditaciones_asesor ON acreditaciones(asesor_id);
CREATE INDEX idx_acreditaciones_evaluador ON acreditaciones(evaluador_id);

INSERT INTO acreditaciones (
 candidato_id,
 asesor_id,
 modificado_por
)
VALUES (
 (SELECT id FROM usuarios WHERE email = 'candidato@example.com'),
 (SELECT id FROM usuarios WHERE email = 'asesor@example.com'),
 (SELECT id FROM usuarios WHERE email = 'candidato@example.com') -- Asumiendo que el candidato es quien modifica la acreditación inicialmente
);

-- Comprobar acreditaciones creadas:
SELECT * FROM acreditaciones;

-- Conversaciones realizadas entre los usuarios de una acreditación (candidato, asesor, evaluador) sobre la acreditación
CREATE TABLE IF NOT EXISTS conversaciones (
 id BIGSERIAL PRIMARY KEY,
 acreditacion_id BIGINT NOT NULL REFERENCES acreditaciones(id),
 usuario_id BIGINT NOT NULL REFERENCES usuarios(id),
 mensaje TEXT NOT NULL,
 fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE conversaciones IS 'Tabla que guarda las conversaciones entre usuarios relacionadas con una acreditación de un candidato.';

COMMENT ON COLUMN conversaciones.id IS 'Identificador único de cada mensaje en la conversación.';
COMMENT ON COLUMN conversaciones.acreditacion_id IS 'Identificador del expediente de acreditación sobre la cual se está conversando.';
COMMENT ON COLUMN conversaciones.usuario_id IS 'Identificador del usuario que envía el mensaje (puede ser candidato, asesor, evaluador, etc.).';
COMMENT ON COLUMN conversaciones.mensaje IS 'Contenido del mensaje enviado en la conversación.';
COMMENT ON COLUMN conversaciones.fecha IS 'Fecha y hora en que se envió el mensaje.';

-- Recomenable crear índices para mejorar la velocidad de las consultas
CREATE INDEX idx_conversaacredita ON conversaciones(acreditacion_id);

-- Ejemplo de inserción en la tabla conversaciones:
INSERT INTO conversaciones (acreditacion_id, usuario_id, mensaje) 
VALUES (
 (SELECT id FROM acreditaciones WHERE candidato_id = (SELECT id FROM usuarios WHERE email = 'candidato@example.com') LIMIT 1),
 (SELECT id FROM usuarios WHERE email = 'candidato@example.com'),
 '¿Como puedo ponerme en contacto con ústed? Mi teléfono es el 123456789 y estoy disponible a partir de las 18:00'
);

-- Comprobar conversaciones creadas:
SELECT * FROM conversaciones;

/**************************************
* Tabla Acreditaciones-Documentos *
**************************************/

CREATE TABLE IF NOT EXISTS tipos_documentos_acreditacion (
 id SERIAL PRIMARY KEY,
 nombre VARCHAR(50) NOT NULL UNIQUE,
 descripcion VARCHAR(255) NOT NULL
);

COMMENT ON TABLE tipos_documentos_acreditacion IS 'Tabla que define los tipos de documentos específicos para el proceso de acreditación, permitiendo la separación lógica de documentos por su función y aplicación en el proceso.';

COMMENT ON COLUMN tipos_documentos_acreditacion.id IS 'Identificador único para cada tipo de documento.';
COMMENT ON COLUMN tipos_documentos_acreditacion.nombre IS 'Nombre corto y único del tipo de documento, usado para referencias en otras tablas.';
COMMENT ON COLUMN tipos_documentos_acreditacion.descripcion IS 'Descripción detallada del tipo de documento, explicando su propósito o contenido esperado.';

-- Ejemplos de inserción de registros en la tabla tipos_documentos_acreditacion:

/* Importante: Se podría hacer puesto el tipo de documento relacionandolo directamente con la tabla documentos, pero a la larga ensuciará la BD.
 Si en un futuro los documentos a introducir al RAG también son de otro tipo para otra tarea, por ejemplo gestion interna del IES no 
 tiene sentido que aparezcan los mismos tipos que no aplican a la tarea que se esta gestionando. Entonces se creará otra tabla para indicar los tipos de documentos internos */

-- Insertar los tipos de documentos
INSERT INTO tipos_documentos_acreditacion (nombre, descripcion) VALUES
('DNI', 'Documento Nacional de Identidad, utilizado para verificación de identidad.'),
('VIDA_LABORAL', 'Documento que muestra el historial laboral del solicitante.'),
('CERTIFICADO_EMPRESA', 'Certificado emitido por una empresa que acredita la relación laboral o desempeño del solicitante.'),
('CUESTIONARIO_AUTOEVALUACION', 'Cuestionario que permite al solicitante autoevaluar sus competencias y conocimientos.'),
('CUESTIONARIO_GENERAL', 'Cuestionario estándar para evaluar conocimientos o habilidades.'),
('MERITO_PROFESIONAL', 'Documento que acredita méritos profesionales específicos.'),
('MERITO_FORMATIVO', 'Documento que acredita formación académica o cursos realizados.'),
('MERITO_OTROS', 'Otros tipos de méritos que no encajan en las categorías anteriores.'),
('NORMATIVA', 'Documentos relacionados con normas o legislación aplicable.'),
('PORTADA_CUALIFICACION', 'Portada de la cualificación, que puede incluir detalles básicos sobre el programa de acreditación.'),
('GLOSARIO_CUALIFICACION', 'Glosario que define términos específicos usados en la cualificación.'),
('GUIA_EVIDENCIA_COMPETENCIA', 'Guía para la recolección de evidencias de competencia.'),
('CUESTIONARIO_COMPENTENCIA', 'Cuestionario específico para evaluar competencias particulares.');

-- Comprobar tipos de documentos creados:
SELECT * FROM tipos_documentos_acreditacion;

CREATE TABLE IF NOT EXISTS acreditaciones_documentos (
 id SERIAL PRIMARY KEY,
 acreditacion_id BIGINT NOT NULL REFERENCES acreditaciones(id) ON DELETE CASCADE,
 documento_id BIGINT NOT NULL REFERENCES documentos(id) ON DELETE CASCADE,
 tipo_documento_id INTEGER NOT NULL,
 FOREIGN KEY (tipo_documento_id) REFERENCES tipos_documentos_acreditacion(id)
);

COMMENT ON TABLE acreditaciones_documentos IS 'Tabla que establece la relación entre las acreditaciones y los documentos necesarios, indicando qué tipo de documento se asocia a cada acreditación.';

COMMENT ON COLUMN acreditaciones_documentos.id IS 'Identificador único de la relación entre un documento y una acreditación.';
COMMENT ON COLUMN acreditaciones_documentos.acreditacion_id IS 'Referencia a la acreditación para la cual el documento es necesario. La eliminación de una acreditación también eliminará esta relación.';
COMMENT ON COLUMN acreditaciones_documentos.documento_id IS 'Referencia al documento específico subido al sistema. La eliminación del documento también eliminará esta relación.';
COMMENT ON COLUMN acreditaciones_documentos.tipo_documento_id IS 'Tipo específico de documento requerido para la acreditación, definido en la tabla tipos_documentos_acreditacion.';

-- Recomenable crear índices para mejorar la velocidad de las consultas
CREATE INDEX idx_acredoc ON acreditaciones_documentos(acreditacion_id);

-- Nota:
-- La estructura de esta tabla permite una gestión flexible de qué documentos se requieren para cada acreditación, manteniendo la integridad referencial a través de 
-- las claves externas. Esto significa que si se elimina una acreditación o un documento, las relaciones correspondientes en esta tabla también se eliminarán 
-- automáticamente (ON DELETE CASCADE).


/*
-- Ejemplo de inserción de un documento en una acreditación:
INSERT INTO acreditaciones_documentos (acreditacion_id, documento_id, tipo_documento_id)
VALUES (
 (SELECT id FROM acreditaciones WHERE candidato_id = (SELECT id FROM usuarios WHERE email = 'candidato@example.com') LIMIT 1),
 (SELECT id FROM documentos WHERE nombre_fichero = 'vida_laboral.pdf'),
 (SELECT id FROM tipos_documentos_acreditacion WHERE nombre = 'VIDA_LABORAL')
);
*/

/**************************************
* Gestión de Cuestionarios *
**************************************/

CREATE TABLE IF NOT EXISTS cuestionarios (
 id BIGSERIAL PRIMARY KEY,
 nombre VARCHAR(255) NOT NULL,
 version VARCHAR(50) NOT NULL, -- Versión semántica (ej: 1.0.3)
 tipo_cuestionario VARCHAR(50) CHECK (tipo_cuestionario IN ('GENERAL', 'AUTOEVALUACION')),
 instrucciones TEXT
);

COMMENT ON COLUMN cuestionarios.tipo_cuestionario IS 'Diferencia cuestionarios normales de autoevaluación';


-- Crear cuestionario de autoevaluación
INSERT INTO cuestionarios (
 nombre,
 version,
 tipo_cuestionario,
 instrucciones
) VALUES (
 'Autoevaluación família profesional «Informática y Comunicaciones»',
 '1.0',
 'AUTOEVALUACION',
 'Bienvenido/a al cuestionario de Autoevaluación...'
);



CREATE TABLE IF NOT EXISTS preguntas (
 id BIGSERIAL PRIMARY KEY,
 cuestionario_id BIGINT REFERENCES cuestionarios(id),
 texto TEXT NOT NULL,
 tipo VARCHAR(50) NOT NULL CHECK (tipo IN (
 'TEXTO_LIBRE',
 'OPCION_MULTIPLE',
 'ESCALA_LIKERT',
 'SELECCION_MULTIPLE'
 )),
 opciones JSONB, -- Ej: {"opciones": ["Si", "No"]}
 comentario_respuestas JSONB, -- Ej: {"Si": "Continúa", "No": "Finaliza"}
 precondicion JSONB, -- Ej: Para indicar si se debe de cumplir alguna condición para contestar esta pregunta
 postcondicion JSONB, -- Ej: Para indicar si se debe de cumplir alguna condición para mostrar la siguiente pregunta
 orden INT NOT NULL CHECK (orden > 0)
);

COMMENT ON COLUMN preguntas.comentario_respuestas IS 'Explicaciones asociadas a cada opción de respuesta';

-- Crear pregunta con lógica condicional
INSERT INTO preguntas (
 cuestionario_id,
 texto,
 tipo,
 opciones,
 comentario_respuestas,
 orden
) VALUES (
 (SELECT id FROM cuestionarios WHERE nombre LIKE 'Autoevaluación%'),
 '¿Tienes la nacionalidad en regla? ¿Posees alguno de los siguientes documentos?',
 'OPCION_MULTIPLE',
 '{"opciones": ["Si", "No"]}',
 '{"Si": "Continúa al siguiente paso", "No": "No cumples los requisitos básicos"}',
 1
);

-- Cp
SELECT * FROM preguntas;

/****************************************************
* Gestión de Contestación de los cuestionarios *
****************************************************/

CREATE TABLE contestaciones_cuestionarios (
 id BIGSERIAL PRIMARY KEY,
 cuestionario_id BIGINT REFERENCES cuestionarios(id),
 usuario_id BIGINT REFERENCES usuarios(id),
 sesion_id BIGINT REFERENCES sesiones(id),
 estado VARCHAR(50) NOT NULL CHECK (estado IN ('EN_PROGRESO', 'COMPLETADO', 'ABANDONADO')),
 metadata JSONB, -- Ej: Para enviar datos de control utiles para el RAG en un futuro
 fecha_inicio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 fecha_ultima_accion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,-- Se actualiza cada vez que el usuario contesta una pregunta del cuestionario
 fecha_fin TIMESTAMP
);

COMMENT ON TABLE contestaciones_cuestionarios IS 'Registro de intentos de completar cuestionarios';

CREATE TABLE contestaciones_preguntas (
 id BIGSERIAL PRIMARY KEY,
 pregunta_id BIGINT REFERENCES preguntas(id),
 contestacion_cuestionario_id BIGINT REFERENCES contestaciones_cuestionarios(id),
 usuario_id BIGINT REFERENCES usuarios(id),
 sesion_id BIGINT REFERENCES sesiones_anonimas(id),
 contestacion VARCHAR(255) NOT NULL,
 fecha_contestacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 metadata JSONB -- Ej: Para enviar datos de control utiles para el RAG en un futuro
);
-- Cada vez que se contesta a una pregunta se deberá actualizar la fecha_ultima_accion en la contestaciones_cuestionarios

COMMENT ON TABLE contestaciones_preguntas IS 'Registro de contestaciones de las preguntas';

-- FALTA ASIGNAR A UNA ACREDITACIÓN QUE UNIDADES DE COMPETENCIA
-- ACREDITARSE