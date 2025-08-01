-- ===================================================================
-- Script de création de la table IMAGE
-- Gère les images associées aux aliments et ingrédients
-- ===================================================================

-- Création du type énuméré pour les types d'images
CREATE TYPE type_image AS ENUM ('PRINCIPALE', 'GALERIE', 'MINIATURE');

-- Création de la table image
CREATE TABLE IF NOT EXISTS image (
    id BIGSERIAL PRIMARY KEY,
    nom_fichier VARCHAR(255) NOT NULL UNIQUE,
    chemin_fichier VARCHAR(500) NOT NULL,
    type_image type_image NOT NULL,
    taille_fichier BIGINT,
    food_id BIGINT,
    ingredient_id BIGINT,
    date_upload TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    
    -- Clés étrangères
    CONSTRAINT fk_image_food 
        FOREIGN KEY (food_id) 
        REFERENCES food(id) 
        ON DELETE CASCADE,
        
    CONSTRAINT fk_image_ingredient 
        FOREIGN KEY (ingredient_id) 
        REFERENCES ingredient(id) 
        ON DELETE CASCADE,
    
    -- Contrainte : une image doit être associée soit à un aliment, soit à un ingrédient
    CONSTRAINT chk_image_association 
        CHECK (
            (food_id IS NOT NULL AND ingredient_id IS NULL) OR 
            (food_id IS NULL AND ingredient_id IS NOT NULL)
        )
);

-- Index pour optimiser les recherches par nom de fichier
CREATE INDEX IF NOT EXISTS idx_image_nom_fichier ON image(nom_fichier);

-- Index pour optimiser les recherches par type d'image
CREATE INDEX IF NOT EXISTS idx_image_type ON image(type_image);

-- Index pour optimiser les recherches par aliment
CREATE INDEX IF NOT EXISTS idx_image_food_id ON image(food_id);

-- Index pour optimiser les recherches par ingrédient
CREATE INDEX IF NOT EXISTS idx_image_ingredient_id ON image(ingredient_id);

-- Index pour optimiser les recherches par date d'upload
CREATE INDEX IF NOT EXISTS idx_image_date_upload ON image(date_upload);

-- Index pour optimiser les recherches par taille
CREATE INDEX IF NOT EXISTS idx_image_taille_fichier ON image(taille_fichier);

-- Contrainte d'unicité : une seule image principale par aliment
CREATE UNIQUE INDEX IF NOT EXISTS idx_image_principale_food 
    ON image(food_id) 
    WHERE type_image = 'PRINCIPALE' AND food_id IS NOT NULL;

-- Contraintes de validation
ALTER TABLE image 
    ADD CONSTRAINT chk_image_taille_positive 
    CHECK (taille_fichier IS NULL OR taille_fichier > 0);

-- Commentaires sur la table et les colonnes
COMMENT ON TABLE image IS 'Table des images associées aux aliments et ingrédients';
COMMENT ON COLUMN image.id IS 'Identifiant unique de l\'image';
COMMENT ON COLUMN image.nom_fichier IS 'Nom unique du fichier image';
COMMENT ON COLUMN image.chemin_fichier IS 'Chemin complet vers le fichier sur le serveur';
COMMENT ON COLUMN image.type_image IS 'Type d\'image (principale, galerie, miniature)';
COMMENT ON COLUMN image.taille_fichier IS 'Taille du fichier en octets';
COMMENT ON COLUMN image.food_id IS 'Identifiant de l\'aliment associé (optionnel)';
COMMENT ON COLUMN image.ingredient_id IS 'Identifiant de l\'ingrédient associé (optionnel)';
COMMENT ON COLUMN image.date_upload IS 'Date et heure d\'upload de l\'image';