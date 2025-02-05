DROP TABLE IF EXISTS chats;
CREATE TABLE chats (
    id_chat BIGSERIAL PRIMARY KEY,
    "user" VARCHAR NOT NULL,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

DROP TABLE IF EXISTS preguntas;
CREATE TABLE preguntas (
    id_pregunta BIGSERIAL PRIMARY KEY,
    "user" VARCHAR NOT NULL,
    texto_pregunta TEXT NOT NULL,
    texto_respuesta TEXT NOT NULL,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    id_chat BIGINT NOT NULL REFERENCES chats(id_chat) ON DELETE CASCADE
);

DROP TABLE IF EXISTS feedbacks;
CREATE TABLE feedbacks (
    id_feedback BIGSERIAL PRIMARY KEY,
    valoracion VARCHAR(255) NOT NULL,
    "user" VARCHAR(255) NOT NULL,
    id_pregunta BIGINT NOT NULL REFERENCES preguntas(id_pregunta) ON DELETE CASCADE
);

DROP TABLE IF EXISTS documentchunks;
CREATE TABLE documentchunks (
    id_documentchunk BIGSERIAL PRIMARY KEY,
    id_doc_source BIGINT,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

DROP TABLE IF EXISTS pregunta_documentchunk;
CREATE TABLE pregunta_documentchunk (
    id_pregunta BIGINT NOT NULL REFERENCES preguntas(id_pregunta) ON DELETE CASCADE,
    id_documentchunk BIGINT NOT NULL REFERENCES documentchunks(id_documentchunk) ON DELETE CASCADE,
    PRIMARY KEY (id_pregunta, id_documentchunk)
);
