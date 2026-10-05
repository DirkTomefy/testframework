CREATE DATABASE coursServlet;
use coursServlet;

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE messages (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);


-- ============================================
-- Données de test
-- ============================================
INSERT INTO users (username, password) VALUES
    ('alice',   'password123'),
    ('bob',     'azerty456'),
    ('charlie', 'qwerty789'),
    ('diana',   'motdepasse!'),
    ('eve',     'secret000');

INSERT INTO messages (user_id, content) VALUES
    (1, 'Hello, this is Alice!'),
    (2, 'Hi Alice, this is Bob.'),
    (3, 'Hey everyone, Charlie here.'),
    (4, 'Diana has joined the chat.'),
    (5, 'Eve is lurking in the shadows.');