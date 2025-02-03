CREATE TABLE documento (
    id SERIAL PRIMARY KEY,
    dni VARCHAR(9) NOT NULL REFERENCES alumnos(dni) ON DELETE CASCADE,
    nombre_fichero VARCHAR(255) NOT NULL,
    comentario TEXT,
    base64_documento TEXT,
    extension_documento VARCHAR(5),
    content_type_documento VARCHAR(50),
    estado_documento VARCHAR(20) DEFAULT 'pendiente', -- "pendiente", "revisado", "aprobado", "denegado"
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_revision TIMESTAMP
);

CREATE TABLE etiqueta (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE documento_etiqueta (
    id_documento INT NOT NULL REFERENCES documento(id) ON DELETE CASCADE,
    id_etiqueta INT NOT NULL REFERENCES etiqueta(id) ON DELETE CASCADE,
    PRIMARY KEY (id_documento, id_etiqueta)
);

CREATE TABLE DocumentChunk (
    id SERIAL PRIMARY KEY,
    id_documento INT NOT NULL REFERENCES documento(id) ON DELETE CASCADE,
    chunk_order NUMERIC NOT NULL,
    chunk_text TEXT NOT NULL,
    chunked_by VARCHAR(255), -- Usuario que procesó el chunk
    estado VARCHAR(20) DEFAULT 'pendiente', -- "pendiente", "aprobado", "denegado", "modificado"
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion TIMESTAMP,
    numero_palabras INT -- Se calculará en la aplicación o con un trigger
);

CREATE TABLE estadistica (
    id SERIAL PRIMARY KEY,
    id_documento INT REFERENCES documento(id) ON DELETE SET NULL,
    id_chunk INT REFERENCES DocumentChunk(id) ON DELETE SET NULL,
    tiempo_revision INT, -- Tiempo total en segundos
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    usuario VARCHAR(255), -- Usuario que realizó la revisión
    tipo VARCHAR(50) CHECK (tipo IN ('documento', 'chunk')), -- Tipo de estadística
    estado_final VARCHAR(20) -- Estado final después de la revisión
);
