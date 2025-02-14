
CREATE TABLE documentos (
    id SERIAL PRIMARY KEY,
    id_doc_rag INT,
    id_usuario BIGSERIAL NOT NULL,
    nombre_fichero VARCHAR(255) NOT NULL,
    comentario TEXT,
    base64_documento TEXT,
    extension_documento VARCHAR(5),
    content_type_documento VARCHAR(100),
    tipo_documento VARCHAR(50),
    estado_documento VARCHAR(20), -- "pendiente", "aprobado", "denegado"
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_revision TIMESTAMP
);

CREATE TABLE etiquetas (
    id INTEGER PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE documentos_etiquetas (
    id_documento SERIAL NOT NULL REFERENCES documentos(id) ON DELETE CASCADE,
    id_etiqueta INT NOT NULL REFERENCES etiquetas(id) ON DELETE CASCADE,
    PRIMARY KEY (id_documento, id_etiqueta)
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

CREATE TABLE estadistica_documental (
    id SERIAL PRIMARY KEY,
    id_documento SERIAL REFERENCES documentos(id) ON DELETE SET NULL,
    id_chunk SERIAL REFERENCES documentos_chunks(id) ON DELETE SET NULL,
    tiempo_revision INT, -- Tiempo total en segundos
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    usuario BIGINT, -- Usuario que realizó la revisión
    estado_final VARCHAR(20) -- Estado final después de la revisión
);