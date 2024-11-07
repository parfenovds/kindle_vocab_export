-- Schema kindle_to_anki
CREATE SCHEMA IF NOT EXISTS kindle_to_anki;

-- Table `users`
CREATE TABLE IF NOT EXISTS users
(
    id    BIGINT PRIMARY KEY,
    login VARCHAR(45) NOT NULL
);

-- Table `author`
CREATE TABLE IF NOT EXISTS author
(
    id           BIGINT PRIMARY KEY,
    name         VARCHAR(45) NOT NULL,
    changed_name VARCHAR(45),
    users_id     BIGINT      NOT NULL,
    FOREIGN KEY (users_id) REFERENCES users (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

-- Table `book`
CREATE TABLE IF NOT EXISTS book
(
    id            BIGINT PRIMARY KEY,
    title         VARCHAR(45) NOT NULL,
    changed_title VARCHAR(45),
    language      VARCHAR(45),
    author_id     BIGINT      NOT NULL,
    FOREIGN KEY (author_id) REFERENCES author (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

-- Table `word`
CREATE TABLE IF NOT EXISTS word
(
    id       BIGINT PRIMARY KEY,
    word     VARCHAR(45) NOT NULL,
    language VARCHAR(45) NOT NULL,
    users_id BIGINT      NOT NULL,
    FOREIGN KEY (users_id) REFERENCES users (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

-- Table `context`
CREATE TABLE IF NOT EXISTS context
(
    id                  BIGINT PRIMARY KEY,
    original_sentence   VARCHAR(255) NOT NULL,
    translated_sentence VARCHAR(255),
    book_id             BIGINT       NOT NULL,
    FOREIGN KEY (book_id) REFERENCES book (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

-- Table `word_context`
CREATE TABLE IF NOT EXISTS word_context
(
    word_id    BIGINT      NOT NULL,
    context_id BIGINT      NOT NULL,
    timestamp  VARCHAR(45) NOT NULL,
    PRIMARY KEY (word_id, context_id),
    FOREIGN KEY (word_id) REFERENCES word (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
    FOREIGN KEY (context_id) REFERENCES context (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

-- Table `card`
CREATE TABLE IF NOT EXISTS card
(
    id                      BIGINT PRIMARY KEY,
    front                   TEXT,
    back                    TEXT,
    word_context_word_id    BIGINT,
    word_context_context_id BIGINT,
    FOREIGN KEY (word_context_word_id, word_context_context_id) REFERENCES word_context (word_id, context_id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

