CREATE TABLE IF NOT EXISTS users
(
    id       BIGSERIAL PRIMARY KEY,
    email    VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS author
(
    id           BIGSERIAL PRIMARY KEY,
    name         TEXT   NOT NULL,
    changed_name VARCHAR(512),
    users_id     BIGINT NOT NULL,
    FOREIGN KEY (users_id) REFERENCES users (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS book
(
    id            BIGSERIAL PRIMARY KEY,
    source_db_id  TEXT   NOT NULL,
    title         TEXT   NOT NULL,
    changed_title VARCHAR(512),
    language      VARCHAR(10),
    author_id     BIGINT NOT NULL,
    FOREIGN KEY (author_id) REFERENCES author (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS word
(
    id       BIGSERIAL PRIMARY KEY,
    word     VARCHAR(100) NOT NULL,
    language VARCHAR(10)  NOT NULL,
    users_id BIGINT       NOT NULL,
    FOREIGN KEY (users_id) REFERENCES users (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS context
(
    id                  BIGSERIAL PRIMARY KEY,
    original_sentence   TEXT   NOT NULL,
    translated_sentence TEXT,
    book_id             BIGINT NOT NULL,
    FOREIGN KEY (book_id) REFERENCES book (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS word_context
(
    word_id    BIGINT    NOT NULL,
    context_id BIGINT    NOT NULL,
    timestamp  timestamp NOT NULL,
    PRIMARY KEY (word_id, context_id),
    FOREIGN KEY (word_id) REFERENCES word (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
    FOREIGN KEY (context_id) REFERENCES context (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS card
(
    id    BIGSERIAL PRIMARY KEY,
    front TEXT,
    back  TEXT
);

