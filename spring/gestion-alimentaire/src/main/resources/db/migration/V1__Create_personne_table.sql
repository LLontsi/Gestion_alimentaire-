-- ===================================================================
-- Script de création de la table PERSONNE
-- Gère les utilisateurs du système de gestion alimentaire
-- ===================================================================

-- Création de la table personne
CREATE TABLE IF NOT EXISTS personne (
    id BIGSERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    mot_de_passe VARCHAR(255) NOT NULL,
    telephone VARCHAR(20),
    date_creation TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Index pour optimiser les recherches par email
CREATE INDEX IF NOT EXISTS idx_personne_email ON personne(email);

-- Index pour optimiser les recherches par nom
CREATE INDEX IF NOT EXISTS idx_personne_nom ON personne(nom);

-- Index pour optimiser les recherches par date de création
CREATE INDEX IF NOT EXISTS idx_personne_date_creation ON personne(date_creation);

-- Contraintes de validation
ALTER TABLE personne 
    ADD CONSTRAINT chk_personne_nom_length 
    CHECK (length(nom) >= 2);

ALTER TABLE personne 
    ADD CONSTRAINT chk_personne_email_format 
    CHECK (email ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$');

-- Commentaires sur la table et les colonnes
COMMENT ON TABLE personne IS 'Table des utilisateurs du système de gestion alimentaire';
COMMENT ON COLUMN personne.id IS 'Identifiant unique de la personne';
COMMENT ON COLUMN personne.nom IS 'Nom complet de la personne';
COMMENT ON COLUMN personne.email IS 'Adresse email unique de la personne';
COMMENT ON COLUMN personne.mot_de_passe IS 'Mot de passe hashé de la personne';
COMMENT ON COLUMN personne.telephone IS 'Numéro de téléphone (optionnel)';
COMMENT ON COLUMN personne.date_creation IS 'Date et heure de création du compte';