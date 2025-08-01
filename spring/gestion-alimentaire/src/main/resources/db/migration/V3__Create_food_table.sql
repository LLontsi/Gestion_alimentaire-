-- ===================================================================
-- Script de création de la table FOOD
-- Gère les aliments et recettes créés par les utilisateurs
-- ===================================================================

-- Création du type énuméré pour les catégories d'aliments
CREATE TYPE categorie_food AS ENUM ('LEGUMES', 'FRUITS', 'VIANDES', 'CEREALES', 'DESSERTS');

-- Création de la table food
CREATE TABLE IF NOT EXISTS food (
    id BIGSERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    categorie categorie_food NOT NULL,
    calories DECIMAL(8,2),
    prix DECIMAL(8,2),
    temps_preparation VARCHAR(50),
    personne_id BIGINT NOT NULL,
    date_creation TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    
    -- Clé étrangère vers la table personne
    CONSTRAINT fk_food_personne 
        FOREIGN KEY (personne_id) 
        REFERENCES personne(id) 
        ON DELETE CASCADE
);

-- Index pour optimiser les recherches par nom
CREATE INDEX IF NOT EXISTS idx_food_nom ON food(nom);

-- Index pour optimiser les recherches par catégorie
CREATE INDEX IF NOT EXISTS idx_food_categorie ON food(categorie);

-- Index pour optimiser les recherches par créateur
CREATE INDEX IF NOT EXISTS idx_food_personne_id ON food(personne_id);

-- Index pour optimiser les recherches par calories
CREATE INDEX IF NOT EXISTS idx_food_calories ON food(calories);

-- Index pour optimiser les recherches par prix
CREATE INDEX IF NOT EXISTS idx_food_prix ON food(prix);

-- Index pour optimiser les recherches par date de création
CREATE INDEX IF NOT EXISTS idx_food_date_creation ON food(date_creation);

-- Index composé pour l'unicité nom + créateur
CREATE UNIQUE INDEX IF NOT EXISTS idx_food_nom_personne 
    ON food(nom, personne_id);

-- Contraintes de validation
ALTER TABLE food 
    ADD CONSTRAINT chk_food_nom_length 
    CHECK (length(nom) >= 2);

ALTER TABLE food 
    ADD CONSTRAINT chk_food_calories_positive 
    CHECK (calories IS NULL OR calories >= 0);

ALTER TABLE food 
    ADD CONSTRAINT chk_food_prix_positive 
    CHECK (prix IS NULL OR prix >= 0);

-- Commentaires sur la table et les colonnes
COMMENT ON TABLE food IS 'Table des aliments et recettes créés par les utilisateurs';
COMMENT ON COLUMN food.id IS 'Identifiant unique de l\'aliment';
COMMENT ON COLUMN food.nom IS 'Nom de l\'aliment ou de la recette';
COMMENT ON COLUMN food.description IS 'Description détaillée de l\'aliment';
COMMENT ON COLUMN food.categorie IS 'Catégorie de l\'aliment (légumes, fruits, etc.)';
COMMENT ON COLUMN food.calories IS 'Nombre de calories pour 100g';
COMMENT ON COLUMN food.prix IS 'Prix de l\'aliment en euros';
COMMENT ON COLUMN food.temps_preparation IS 'Temps de préparation estimé';
COMMENT ON COLUMN food.personne_id IS 'Identifiant du créateur de l\'aliment';
COMMENT ON COLUMN food.date_creation IS 'Date et heure de création de l\'aliment';