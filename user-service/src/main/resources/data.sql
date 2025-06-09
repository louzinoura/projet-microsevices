CREATE TABLE IF NOT EXISTS users (
    id VARCHAR(255) PRIMARY KEY,
    nom VARCHAR(100),
    prenom VARCHAR(100),
    email VARCHAR(255),
    mot_de_passe VARCHAR(255),
    telephone VARCHAR(20),
    role VARCHAR(50),
    date_inscription TIMESTAMP,
    adresse VARCHAR(255),
    date_naissance DATE
);

INSERT INTO users (id, nom, prenom, email, mot_de_passe, telephone, role, date_inscription, adresse, date_naissance) VALUES
('550e8400-e29b-41d4-a716-446655440001', 'Dupont', 'Jean', 'jean.dupont@email.com', 'password123', '0123456789', 'CLIENT', CURRENT_TIMESTAMP, '123 Rue de la Paix, Paris', '1990-05-15'),
('550e8400-e29b-41d4-a716-446655440002', 'Martin', 'Sophie', 'sophie.martin@email.com', 'password123', '0987654321', 'PRESTATAIRE', CURRENT_TIMESTAMP, '456 Avenue Republique, Lyon', '1995-10-30');
