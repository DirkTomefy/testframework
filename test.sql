CREATE DATABASE coursServlet;
use coursServlet;

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL UNIQUE
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