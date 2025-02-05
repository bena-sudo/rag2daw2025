DROP TABLE IF EXISTS pregunta_documentchunk;
DROP TABLE IF EXISTS documentchunks;
DROP TABLE IF EXISTS preguntas;
DROP TABLE IF EXISTS chats;

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

CREATE TABLE documentchunks (
    id_documentchunk BIGSERIAL PRIMARY KEY,
    id_doc_source BIGINT NOT NULL,
    contenido TEXT DEFAULT ' '
);

CREATE TABLE pregunta_documentchunk (
    id_pregunta BIGINT NOT NULL REFERENCES preguntas(id_pregunta) ON DELETE CASCADE,
    id_documentchunk BIGINT NOT NULL REFERENCES documentchunks(id_documentchunk) ON DELETE CASCADE,
    PRIMARY KEY (id_pregunta, id_documentchunk)
);
