CREATE TABLE IF NOT EXISTS menus (
    uid VARCHAR(64) PRIMARY KEY,
    name VARCHAR(100),
    description TEXT,
    icon VARCHAR(255),
    url VARCHAR(255),
    restricted VARCHAR(100),
    parent_uid VARCHAR(64),
    menu_order INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

CREATE INDEX IF NOT EXISTS idx_menus_search ON menus (name, created_at);
CREATE INDEX IF NOT EXISTS idx_menus_parent ON menus (parent_uid);


CREATE TABLE IF NOT EXISTS users (
    uid VARCHAR(64) PRIMARY KEY,
    name VARCHAR(100),
    nickname VARCHAR(100),
    birth_date DATE,
    user_id VARCHAR(100) UNIQUE,
    password VARCHAR(255),
    role VARCHAR(50),
    state VARCHAR(50),
    approved BOOLEAN DEFAULT FALSE,
    approve_date TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

CREATE INDEX IF NOT EXISTS idx_user_search ON users (
    created_at, role, state, name, nickname
);

CREATE TABLE IF NOT EXISTS config (
    uid VARCHAR(64) PRIMARY KEY,
    type VARCHAR(100),
    config_value TEXT,
    sub_type VARCHAR(100),
    name VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

CREATE INDEX IF NOT EXISTS idx_config_search ON config (
    type, sub_type
);
