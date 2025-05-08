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

CREATE TABLE IF NOT EXISTS board (
    uid VARCHAR(64) PRIMARY KEY,
    name VARCHAR(100),
    full_path_index VARCHAR(64),
    content TEXT,
    good INT,
    is_notice BOOLEAN DEFAULT FALSE,
    creator_uid VARCHAR(64),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modified_uid VARCHAR(64),
    last_modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    ) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

CREATE INDEX IF NOT EXISTS idx_board_search ON board (name, created_at);

CREATE TABLE IF NOT EXISTS geo (
    uid VARCHAR(64) PRIMARY KEY,
    country_name VARCHAR(100),
    country_code VARCHAR(100),
    notes TEXT,
    longitude VARCHAR(100),
    latitude VARCHAR(100),
    creator_uid VARCHAR(64),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    ) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

CREATE INDEX IF NOT EXISTS idx_geo_search ON geo (country_name, creator_uid, created_at);

