ALTER TABLE users ADD COLUMN reset_token VARCHAR(255) DEFAULT NULL UNIQUE;