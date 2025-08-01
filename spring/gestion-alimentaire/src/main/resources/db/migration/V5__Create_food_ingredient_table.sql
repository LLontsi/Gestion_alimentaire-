-- ===================================================================
-- Script de création de la table FOOD_INGREDIENT
-- Gère la relation Many-to-Many entre Food et Ingredient avec quantités
-- ===================================================================

-- Création de la table de liaison food_ingredient
CREATE TABLE IF NOT EXISTS food_ingredient (
    food_id BIGINT NOT NULL,
    ingredient_id BIGINT NOT NULL,
    quantite_utilisee DECIMAL(10,2) NOT NULL,
    unite VARCHAR(20) NOT NULL,
    
    -- Clé primaire composite
    PRIMARY KEY (food_id, ingredient_id),
    
    -- Clés étrangères
    CONSTRAINT fk_food_ingredient_food 
        FOREIGN KEY (food_id) 
        REFERENCES food(id) 
        ON DELETE CASCADE,
        
    CONSTRAINT fk_food_ingredient_ingredient 
        FOREIGN KEY (ingredient_id) 
        REFERENCES ingredient(id) 
        ON DELETE CASCADE
);

-- Index pour optimiser les recherches par aliment
CREATE INDEX IF NOT EXISTS idx_food_ingredient_food_id ON food_ingredient(food_id);

-- Index pour optimiser les recherches par ingrédient
CREATE INDEX IF NOT EXISTS idx_food_ingredient_ingredient_id ON food_ingredient(ingredient_id);

-- Index pour optimiser les recherches par quantité
CREATE INDEX IF NOT EXISTS idx_food_ingredient_quantite ON food_ingredient(quantite_utilisee);

-- Index pour optimiser les recherches par unité
CREATE INDEX IF NOT EXISTS idx_food_ingredient_unite ON food_ingredient(unite);

-- Contraintes de validation
ALTER TABLE food_ingredient 
    ADD CONSTRAINT chk_food_ingredient_quantite_positive 
    CHECK (quantite_utilisee > 0);

ALTER TABLE food_ingredient 
    ADD CONSTRAINT chk_food_ingredient_unite_not_empty 
    CHECK (length(trim(unite)) > 0);

-- Commentaires sur la table et les colonnes
COMMENT ON TABLE food_ingredient IS 'Table de liaison entre aliments et ingrédients avec quantités';
COMMENT ON COLUMN food_ingredient.food_id IS 'Identifiant de l\'aliment';
COMMENT ON COLUMN food_ingredient.ingredient_id IS 'Identifiant de l\'ingrédient';
COMMENT ON COLUMN food_ingredient.quantite_utilisee IS 'Quantité d\'ingrédient utilisée dans l\'aliment';
COMMENT ON COLUMN food_ingredient.unite IS 'Unité de mesure de la quantité';