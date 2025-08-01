-- Supprimer les tables si elles existent
DROP TABLE IF EXISTS food_ingredients CASCADE;
DROP TABLE IF EXISTS images CASCADE;
DROP TABLE IF EXISTS foods CASCADE;
DROP TABLE IF EXISTS ingredients CASCADE;
DROP TABLE IF EXISTS personnes CASCADE;

-- Créer la table des personnes
CREATE TABLE personnes (
    id BIGSERIAL PRIMARY KEY,
    nom VARCHAR(50) NOT NULL,
    prenom VARCHAR(50) NOT NULL,
    email VARCHAR(255) UNIQUE,
    date_naissance DATE,
    telephone VARCHAR(15)
);

-- Créer la table des ingrédients
CREATE TABLE ingredients (
    id BIGSERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(300),
    type VARCHAR(50),
    valeur_nutritive VARCHAR(500)
);

-- Créer la table des aliments
CREATE TABLE foods (
    id BIGSERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    categorie VARCHAR(50) NOT NULL,
    calories DECIMAL(10,2),
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    personne_id BIGINT,
    FOREIGN KEY (personne_id) REFERENCES personnes(id)
);

-- Créer la table de liaison aliments-ingrédients
CREATE TABLE food_ingredients (
    food_id BIGINT NOT NULL,
    ingredient_id BIGINT NOT NULL,
    PRIMARY KEY (food_id, ingredient_id),
    FOREIGN KEY (food_id) REFERENCES foods(id) ON DELETE CASCADE,
    FOREIGN KEY (ingredient_id) REFERENCES ingredients(id) ON DELETE CASCADE
);

-- Créer la table des images
CREATE TABLE images (
    id BIGSERIAL PRIMARY KEY,
    nom_fichier VARCHAR(255) NOT NULL,
    chemin VARCHAR(500) NOT NULL,
    type_mime VARCHAR(100),
    taille BIGINT,
    description VARCHAR(500),
    date_upload TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    food_id BIGINT,
    FOREIGN KEY (food_id) REFERENCES foods(id)
);