DROP TABLE IF EXISTS chats;
CREATE TABLE chats (
    id_chat BIGSERIAL PRIMARY KEY,
    "user" VARCHAR NOT NULL,
    "date" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

DROP TABLE IF EXISTS questions;
CREATE TABLE questions (
    id_question BIGSERIAL PRIMARY KEY,
    "user" VARCHAR NOT NULL,
    "text" TEXT NOT NULL,
    chat BIGINT NOT NULL REFERENCES chats(id_chat) ON DELETE CASCADE
);

DROP TABLE IF EXISTS answers;
CREATE TABLE answers (
    id_answer BIGSERIAL PRIMARY KEY,
    "text" TEXT NOT NULL,
    "user" VARCHAR NOT NULL,
    id_question BIGINT NOT NULL REFERENCES questions(id_question) ON DELETE CASCADE,
    id_chat BIGINT NOT NULL REFERENCES chats(id_chat) ON DELETE CASCADE
);

DROP TABLE IF EXISTS feedbacks;
CREATE TABLE feedbacks (
    id_feedback BIGSERIAL PRIMARY KEY,
    feedback VARCHAR NOT NULL,
    "user" VARCHAR NOT NULL,
    id_answer BIGINT NOT NULL REFERENCES answers(id_answer) ON DELETE CASCADE
);

DROP TABLE IF EXISTS answer_feedback;
CREATE TABLE answer_feedback (
    id_answer BIGINT NOT NULL REFERENCES answers(id_answer) ON DELETE CASCADE,
    id_feedback BIGINT NOT NULL REFERENCES feedbacks(id_feedback) ON DELETE CASCADE,
    PRIMARY KEY (idAnswer, id_feedback)
);

DROP TABLE IF EXISTS documentchunks;
CREATE TABLE documentchunks (
    id_document_chunk BIGSERIAL PRIMARY KEY,
    date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
