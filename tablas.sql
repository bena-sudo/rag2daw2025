DROP TABLE IF EXISTS pregunta_documentchunk;
DROP TABLE IF EXISTS documentchunks;
DROP TABLE IF EXISTS preguntas;
DROP TABLE IF EXISTS chats;



-- Tablas de calidad
CREATE TABLE chats (
    id_chat BIGSERIAL PRIMARY KEY,
    "user" VARCHAR NOT NULL,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    contexto INT NOT NULL
);

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

--Tablas de documentacion



CREATE TABLE documentos (
    id SERIAL PRIMARY KEY,
    id_doc_rag INT,
    id_usuario INTEGER NOT NULL,
    nombre_fichero VARCHAR(255) NOT NULL,
    comentario TEXT,
    base64_documento TEXT,
    extension_documento VARCHAR(5),
    content_type_documento VARCHAR(100),
    tipo_documento VARCHAR(50),
    estado_documento VARCHAR(20), -- "pendiente", "aprobado", "denegado"
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_revision TIMESTAMP,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id) ON DELETE SET NULL
);

CREATE TABLE documentos_chunks (
    id SERIAL PRIMARY KEY,
    id_documento SERIAL NOT NULL REFERENCES documentos(id) ON DELETE CASCADE,
    id_doc_rag INT,
    chunk_order INTEGER NOT NULL,
    chunk_text TEXT NOT NULL,
    chunked_by BIGINT,
    estado VARCHAR(20) DEFAULT 'PENDIENTE',
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion TIMESTAMP
);


CREATE TABLE etiquetas (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE documentos_etiquetas (
    id_documento SERIAL NOT NULL REFERENCES documentos(id) ON DELETE CASCADE,
    id_etiqueta INT NOT NULL REFERENCES etiquetas(id) ON DELETE CASCADE,
    PRIMARY KEY (id_documento, id_etiqueta)
);

CREATE TABLE estadistica_documental (
    id SERIAL PRIMARY KEY,
    id_documento SERIAL REFERENCES documentos(id) ON DELETE SET NULL,
    id_chunk SERIAL REFERENCES documentos_chunks(id) ON DELETE SET NULL,
    tiempo_revision INT, -- Tiempo total en segundos
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    usuario BIGINT, -- Usuario que realizó la revisión
    estado_final VARCHAR(20) -- Estado final después de la revisión
);

--Tablas mixtas

--Documental + qualitat

CREATE TABLE pregunta_documentchunk (
    id_pregunta BIGINT NOT NULL REFERENCES preguntas(id_pregunta) ON DELETE CASCADE,
    id_documentchunk BIGINT NOT NULL REFERENCES documentos_chunks(id) ON DELETE CASCADE,
    PRIMARY KEY (id_pregunta, id_documentchunk)
);

-- GRUPO ACREDITACION
CREATE TABLE tipo_pregunta (
    id SERIAL PRIMARY KEY,            
    nombre VARCHAR(50) NOT NULL, 
    descripcion TEXT
);

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
    FOREIGN KEY (siguiente_si) REFERENCES preguntas(id), 
    FOREIGN KEY (siguiente_no) REFERENCES preguntas(id),
    FOREIGN KEY (cuestionario_id) REFERENCES cuestionarios(id)  
);

CREATE TABLE respuestas (
    id SERIAL PRIMARY KEY,
    pregunta_id INTEGER NOT NULL,
    usuario_id INTEGER,  
    respuesta TEXT NOT NULL,
    FOREIGN KEY (pregunta_id) REFERENCES preguntas(id) ON DELETE CASCADE,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE SET NULL
);

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
