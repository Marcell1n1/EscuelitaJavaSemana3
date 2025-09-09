CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255),
    active BOOLEAN
);
INSERT INTO users (name, email, active) VALUES
('Juan Pérez', 'juan.perez@example.com', true),
('María Gómez', 'maria.gomez@example.com', true),
('Pedro Torres', 'pedro.torres@example.com', false);
