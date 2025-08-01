-- ===================================================================
-- Script de création de la table INGREDIENT
-- Gère les ingrédients utilisés dans les recettes
-- ===================================================================

-- Création du type énuméré pour les types d'ingrédients
CREATE TYPE type_ingredient AS ENUM ('FRAIS', 'SURGELE', 'CONSERVE', 'SEC');

-- Création de la table ingredient
CREATE TABLE IF NOT EXISTS ingredient (
    id BIGSERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(300),
    type type_ingredient NOT NULL,
    quantite DECIMAL(10,2),
    unite VARCHAR(20),
    date_creation TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Index pour optimiser les recherches par nom
CREATE INDEX IF NOT EXISTS idx_ingredient_nom ON ingredient(nom);

-- Index pour optimiser les recherches par type
CREATE INDEX IF NOT EXISTS idx_ingredient_type ON ingredient(type);

-- Index pour optimiser les recherches par quantité
CREATE INDEX IF NOT EXISTS idx_ingredient_quantite ON ingredient(quantite);

-- Index pour optimiser les recherches par date de création
CREATE INDEX IF NOT EXISTS idx_ingredient_date_creation ON ingredient(date_creation);

-- Contraintes de validation
ALTER TABLE ingredient 
    ADD CONSTRAINT chk_ingredient_nom_length 
    CHECK (length(nom) >= 2);

ALTER TABLE ingredient 
    ADD CONSTRAINT chk_ingredient_quantite_positive 
    CHECK (quantite IS NULL OR quantite > 0);

-- Commentaires sur la table et les colonnes
COMMENT ON TABLE ingredient IS 'Table des ingrédients utilisés dans les recettes';
COMMENT ON COLUMN ingredient.id IS 'Identifiant unique de l\'ingrédient';
COMMENT ON COLUMN ingredient.nom IS 'Nom unique de l\'ingrédient';
COMMENT ON COLUMN ingredient.description IS 'Description détaillée de l\'ingrédient';
COMMENT ON COLUMN ingredient.type IS 'Type de conservation de l\'ingrédient';
COMMENT ON COLUMN ingredient.quantite IS 'Quantité de base de l\'ingrédient';
COMMENT ON COLUMN ingredient.unite IS 'Unité de mesure de la quantité';
COMMENT ON COLUMN ingredient.date_creation IS 'Date et heure de création de l\'ingrédient';