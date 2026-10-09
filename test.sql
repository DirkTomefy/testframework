-- =========================================================
-- init.sql  —  script de test pour dirkfw
-- Compatible H2 (mode MySQL/PostgreSQL) et MySQL.
-- =========================================================

DROP TABLE IF EXISTS messages;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS users;

-- ---------------------------------------------------------
-- Utilisateurs
-- ---------------------------------------------------------
CREATE TABLE users (
    id      BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name    VARCHAR(100) NOT NULL,
    email   VARCHAR(150) NOT NULL UNIQUE
);

-- ---------------------------------------------------------
-- Catégories
-- ---------------------------------------------------------
CREATE TABLE categories (
    id      BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name    VARCHAR(100) NOT NULL UNIQUE
);

-- ---------------------------------------------------------
-- Messages
-- ---------------------------------------------------------
CREATE TABLE messages (
    id          BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id     BIGINT,
    category_id BIGINT,
    title       VARCHAR(200) NOT NULL,
    content     TEXT         NOT NULL,
    created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_messages_user     FOREIGN KEY (user_id)     REFERENCES users(id),
    CONSTRAINT fk_messages_category FOREIGN KEY (category_id) REFERENCES categories(id)
);

-- ---------------------------------------------------------
-- Jeu de données
-- ---------------------------------------------------------
INSERT INTO users (name, email) VALUES
    ('Alice Martin',  'alice@example.com'),
    ('Bob Durand',    'bob@example.com'),
    ('Carol Petit',   'carol@example.com');

INSERT INTO categories (name) VALUES
    ('Général'),
    ('Technique'),
    ('Annonce');

INSERT INTO messages (user_id, category_id, title, content) VALUES
    (1, 1, 'Bienvenue',        'Premier message du forum.'),
    (2, 2, 'Bug signalé',      'Le bouton ne répond pas au clic.'),
    (3, 3, 'Maintenance',      'Une maintenance est prévue vendredi.'),
    (1, 2, 'Question JDBC',    'Comment configurer un pool de connexions ?'),
    (2, 1, 'Merci',            'Merci pour vos réponses rapides !');