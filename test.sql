-- ---------------------------------------------------------------------
--  Base
-- ---------------------------------------------------------------------
CREATE DATABASE IF NOT EXISTS coursServlet
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE coursServlet;

-- ---------------------------------------------------------------------
--  Ordre de création important :
--   addresses  → référencée par users
--   categories → référencée par messages
--   users      → référencée par messages
--   messages   → dépend des 3 autres
-- ---------------------------------------------------------------------

-- ---------------------------------------------------------------------
--  addresses
-- ---------------------------------------------------------------------
DROP TABLE IF EXISTS messages;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS addresses;

CREATE TABLE addresses (
    id       BIGINT       NOT NULL AUTO_INCREMENT,
    street   VARCHAR(200) NULL,
    city     VARCHAR(100) NULL,
    zip_code VARCHAR(20)  NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ---------------------------------------------------------------------
--  categories
-- ---------------------------------------------------------------------
CREATE TABLE categories (
    id   BIGINT       NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_categories_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ---------------------------------------------------------------------
--  users
-- ---------------------------------------------------------------------
CREATE TABLE users (
    id            BIGINT       NOT NULL AUTO_INCREMENT,
    name          VARCHAR(100) NOT NULL,
    email         VARCHAR(150) NOT NULL,
    cin           VARCHAR(20)  NULL,
    cin_file_path VARCHAR(255) NULL,
    address_id    BIGINT       NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_users_email (email),
    UNIQUE KEY uk_users_cin   (cin),
    KEY fk_users_address (address_id),
    CONSTRAINT fk_users_address
        FOREIGN KEY (address_id) REFERENCES addresses (id)
        ON DELETE SET NULL
        ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ---------------------------------------------------------------------
--  messages
-- ---------------------------------------------------------------------
CREATE TABLE messages (
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    user_id     BIGINT       NULL,
    category_id BIGINT       NULL,
    title       VARCHAR(200) NOT NULL,
    content     TEXT         NOT NULL,
    created_at  DATETIME     NOT NULL,
    PRIMARY KEY (id),
    KEY fk_messages_user     (user_id),
    KEY fk_messages_category (category_id),
    CONSTRAINT fk_messages_user
        FOREIGN KEY (user_id) REFERENCES users (id)
        ON DELETE SET NULL
        ON UPDATE CASCADE,
    CONSTRAINT fk_messages_category
        FOREIGN KEY (category_id) REFERENCES categories (id)
        ON DELETE SET NULL
        ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;