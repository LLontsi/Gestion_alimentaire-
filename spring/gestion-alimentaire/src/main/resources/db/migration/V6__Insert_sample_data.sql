-- ===================================================================
-- Script d'insertion de données d'exemple
-- Fournit des données de base pour tester l'application
-- ===================================================================

-- Insertion de personnes d'exemple
INSERT INTO personne (nom, email, mot_de_passe, telephone, date_creation) VALUES
('Alice Martin', 'alice.martin@email.com', 'password123', '0123456789', CURRENT_TIMESTAMP),
('Bob Dupont', 'bob.dupont@email.com', 'password456', '0987654321', CURRENT_TIMESTAMP),
('Claire Bernard', 'claire.bernard@email.com', 'password789', '0654321987', CURRENT_TIMESTAMP),
('David Petit', 'david.petit@email.com', 'password321', '0147258369', CURRENT_TIMESTAMP),
('Emma Rousseau', 'emma.rousseau@email.com', 'password654', '0369258147', CURRENT_TIMESTAMP)
ON CONFLICT (email) DO NOTHING;

-- Insertion d'ingrédients d'exemple (sans ON CONFLICT car pas de contrainte unique sur nom)
INSERT INTO ingredient (nom, description, type, quantite, unite, date_creation) VALUES
-- Légumes
('Tomate', 'Tomate rouge fraîche', 'FRAIS', 100, 'grammes', CURRENT_TIMESTAMP),
('Carotte', 'Carotte orange croquante', 'FRAIS', 100, 'grammes', CURRENT_TIMESTAMP),
('Oignon', 'Oignon jaune classique', 'FRAIS', 100, 'grammes', CURRENT_TIMESTAMP),
('Pomme de terre', 'Pomme de terre à chair ferme', 'FRAIS', 100, 'grammes', CURRENT_TIMESTAMP),
('Salade', 'Salade verte fraîche', 'FRAIS', 100, 'grammes', CURRENT_TIMESTAMP),

-- Fruits
('Pomme', 'Pomme rouge croquante', 'FRAIS', 1, 'pièce', CURRENT_TIMESTAMP),
('Banane', 'Banane jaune mûre', 'FRAIS', 1, 'pièce', CURRENT_TIMESTAMP),
('Orange', 'Orange juteuse', 'FRAIS', 1, 'pièce', CURRENT_TIMESTAMP),
('Fraise', 'Fraise rouge sucrée', 'FRAIS', 100, 'grammes', CURRENT_TIMESTAMP),
('Raisin', 'Raisin blanc ou rouge', 'FRAIS', 100, 'grammes', CURRENT_TIMESTAMP),

-- Viandes
('Poulet', 'Blanc de poulet', 'FRAIS', 100, 'grammes', CURRENT_TIMESTAMP),
('Bœuf', 'Steak de bœuf', 'FRAIS', 100, 'grammes', CURRENT_TIMESTAMP),
('Porc', 'Côte de porc', 'FRAIS', 100, 'grammes', CURRENT_TIMESTAMP),
('Poisson', 'Filet de poisson blanc', 'FRAIS', 100, 'grammes', CURRENT_TIMESTAMP),
('Jambon', 'Jambon blanc en tranches', 'CONSERVE', 100, 'grammes', CURRENT_TIMESTAMP),

-- Céréales
('Riz', 'Riz blanc long grain', 'SEC', 100, 'grammes', CURRENT_TIMESTAMP),
('Pâtes', 'Pâtes italiennes', 'SEC', 100, 'grammes', CURRENT_TIMESTAMP),
('Pain', 'Pain de mie complet', 'FRAIS', 1, 'tranche', CURRENT_TIMESTAMP),
('Farine', 'Farine de blé T65', 'SEC', 100, 'grammes', CURRENT_TIMESTAMP),
('Avoine', 'Flocons d''avoine', 'SEC', 100, 'grammes', CURRENT_TIMESTAMP),

-- Produits de base
('Huile d''olive', 'Huile d''olive extra vierge', 'CONSERVE', 15, 'ml', CURRENT_TIMESTAMP),
('Sel', 'Sel fin de cuisine', 'SEC', 1, 'pincée', CURRENT_TIMESTAMP),
('Poivre', 'Poivre noir moulu', 'SEC', 1, 'pincée', CURRENT_TIMESTAMP),
('Beurre', 'Beurre doux', 'FRAIS', 10, 'grammes', CURRENT_TIMESTAMP),
('Œuf', 'Œuf de poule frais', 'FRAIS', 1, 'pièce', CURRENT_TIMESTAMP);

-- Insertion d'aliments d'exemple (sans ON CONFLICT car pas de contrainte unique sur nom, personne_id)
INSERT INTO food (nom, description, categorie, calories, prix, temps_preparation, personne_id, date_creation) VALUES
-- Aliments créés par Alice (ID = 1)
('Salade de tomates', 'Salade fraîche avec tomates et oignons', 'LEGUMES', 45, 3.50, '10 minutes', 2, CURRENT_TIMESTAMP),
('Carottes râpées', 'Carottes fraîches râpées avec vinaigrette', 'LEGUMES', 35, 2.80, '5 minutes', 3, CURRENT_TIMESTAMP),
('Salade verte', 'Salade composée de légumes frais', 'LEGUMES', 25, 4.20, '15 minutes', 4, CURRENT_TIMESTAMP),

-- Aliments créés par Bob (ID = 2)
('Salade de fruits', 'Mélange de fruits frais de saison', 'FRUITS', 85, 5.50, '15 minutes', 2, CURRENT_TIMESTAMP),
('Compote de pommes', 'Compote maison sans sucre ajouté', 'FRUITS', 65, 3.20, '20 minutes', 2, CURRENT_TIMESTAMP),

-- Aliments créés par Claire (ID = 3)
('Poulet grillé', 'Blanc de poulet grillé aux herbes', 'VIANDES', 165, 8.50, '25 minutes', 3, CURRENT_TIMESTAMP),
('Steak de bœuf', 'Steak de bœuf grillé saignant', 'VIANDES', 250, 12.80, '15 minutes', 3, CURRENT_TIMESTAMP),

-- Aliments créés par David (ID = 4)
('Riz pilaf', 'Riz parfumé aux légumes', 'CEREALES', 130, 4.50, '30 minutes', 4, CURRENT_TIMESTAMP),
('Pâtes carbonara', 'Pâtes italiennes à la crème', 'CEREALES', 320, 6.80, '20 minutes', 4, CURRENT_TIMESTAMP),

-- Aliments créés par Emma (ID = 5)
('Tarte aux pommes', 'Tarte traditionnelle aux pommes', 'DESSERTS', 280, 8.20, '45 minutes', 5, CURRENT_TIMESTAMP),
('Mousse au chocolat', 'Mousse légère au chocolat noir', 'DESSERTS', 195, 4.50, '30 minutes', 5, CURRENT_TIMESTAMP);

-- Récupération des IDs réels des aliments insérés pour les relations
-- Note: Les IDs peuvent ne pas être séquentiels si d'autres données existent déjà

-- Insertion des relations food_ingredient avec des sous-requêtes pour récupérer les vrais IDs
-- Relations pour la salade de tomates
INSERT INTO food_ingredient (food_id, ingredient_id, quantite_utilisee, unite) 
SELECT f.id, i.id, 200, 'grammes'
FROM food f, ingredient i 
WHERE f.nom = 'Salade de tomates' AND f.personne_id = 1 AND i.nom = 'Tomate';

INSERT INTO food_ingredient (food_id, ingredient_id, quantite_utilisee, unite) 
SELECT f.id, i.id, 50, 'grammes'
FROM food f, ingredient i 
WHERE f.nom = 'Salade de tomates' AND f.personne_id = 1 AND i.nom = 'Oignon';

INSERT INTO food_ingredient (food_id, ingredient_id, quantite_utilisee, unite) 
SELECT f.id, i.id, 10, 'ml'
FROM food f, ingredient i 
WHERE f.nom = 'Salade de tomates' AND f.personne_id = 1 AND i.nom = 'Huile d''olive';

INSERT INTO food_ingredient (food_id, ingredient_id, quantite_utilisee, unite) 
SELECT f.id, i.id, 1, 'pincée'
FROM food f, ingredient i 
WHERE f.nom = 'Salade de tomates' AND f.personne_id = 1 AND i.nom = 'Sel';

-- Relations pour les carottes râpées
INSERT INTO food_ingredient (food_id, ingredient_id, quantite_utilisee, unite) 
SELECT f.id, i.id, 150, 'grammes'
FROM food f, ingredient i 
WHERE f.nom = 'Carottes râpées' AND f.personne_id = 1 AND i.nom = 'Carotte';

INSERT INTO food_ingredient (food_id, ingredient_id, quantite_utilisee, unite) 
SELECT f.id, i.id, 8, 'ml'
FROM food f, ingredient i 
WHERE f.nom = 'Carottes râpées' AND f.personne_id = 1 AND i.nom = 'Huile d''olive';

INSERT INTO food_ingredient (food_id, ingredient_id, quantite_utilisee, unite) 
SELECT f.id, i.id, 1, 'pincée'
FROM food f, ingredient i 
WHERE f.nom = 'Carottes râpées' AND f.personne_id = 1 AND i.nom = 'Sel';

-- Relations pour la salade de fruits
INSERT INTO food_ingredient (food_id, ingredient_id, quantite_utilisee, unite) 
SELECT f.id, i.id, 1, 'pièce'
FROM food f, ingredient i 
WHERE f.nom = 'Salade de fruits' AND f.personne_id = 2 AND i.nom = 'Pomme';

INSERT INTO food_ingredient (food_id, ingredient_id, quantite_utilisee, unite) 
SELECT f.id, i.id, 1, 'pièce'
FROM food f, ingredient i 
WHERE f.nom = 'Salade de fruits' AND f.personne_id = 2 AND i.nom = 'Banane';

INSERT INTO food_ingredient (food_id, ingredient_id, quantite_utilisee, unite) 
SELECT f.id, i.id, 1, 'pièce'
FROM food f, ingredient i 
WHERE f.nom = 'Salade de fruits' AND f.personne_id = 2 AND i.nom = 'Orange';

INSERT INTO food_ingredient (food_id, ingredient_id, quantite_utilisee, unite) 
SELECT f.id, i.id, 100, 'grammes'
FROM food f, ingredient i 
WHERE f.nom = 'Salade de fruits' AND f.personne_id = 2 AND i.nom = 'Fraise';

-- Relations pour le poulet grillé
INSERT INTO food_ingredient (food_id, ingredient_id, quantite_utilisee, unite) 
SELECT f.id, i.id, 150, 'grammes'
FROM food f, ingredient i 
WHERE f.nom = 'Poulet grillé' AND f.personne_id = 3 AND i.nom = 'Poulet';

INSERT INTO food_ingredient (food_id, ingredient_id, quantite_utilisee, unite) 
SELECT f.id, i.id, 5, 'ml'
FROM food f, ingredient i 
WHERE f.nom = 'Poulet grillé' AND f.personne_id = 3 AND i.nom = 'Huile d''olive';

INSERT INTO food_ingredient (food_id, ingredient_id, quantite_utilisee, unite) 
SELECT f.id, i.id, 1, 'pincée'
FROM food f, ingredient i 
WHERE f.nom = 'Poulet grillé' AND f.personne_id = 3 AND i.nom = 'Sel';

INSERT INTO food_ingredient (food_id, ingredient_id, quantite_utilisee, unite) 
SELECT f.id, i.id, 1, 'pincée'
FROM food f, ingredient i 
WHERE f.nom = 'Poulet grillé' AND f.personne_id = 3 AND i.nom = 'Poivre';

-- Relations pour le riz pilaf
INSERT INTO food_ingredient (food_id, ingredient_id, quantite_utilisee, unite) 
SELECT f.id, i.id, 100, 'grammes'
FROM food f, ingredient i 
WHERE f.nom = 'Riz pilaf' AND f.personne_id = 4 AND i.nom = 'Riz';

INSERT INTO food_ingredient (food_id, ingredient_id, quantite_utilisee, unite) 
SELECT f.id, i.id, 30, 'grammes'
FROM food f, ingredient i 
WHERE f.nom = 'Riz pilaf' AND f.personne_id = 4 AND i.nom = 'Oignon';

INSERT INTO food_ingredient (food_id, ingredient_id, quantite_utilisee, unite) 
SELECT f.id, i.id, 50, 'grammes'
FROM food f, ingredient i 
WHERE f.nom = 'Riz pilaf' AND f.personne_id = 4 AND i.nom = 'Carotte';

INSERT INTO food_ingredient (food_id, ingredient_id, quantite_utilisee, unite) 
SELECT f.id, i.id, 10, 'ml'
FROM food f, ingredient i 
WHERE f.nom = 'Riz pilaf' AND f.personne_id = 4 AND i.nom = 'Huile d''olive';

INSERT INTO food_ingredient (food_id, ingredient_id, quantite_utilisee, unite) 
SELECT f.id, i.id, 1, 'pincée'
FROM food f, ingredient i 
WHERE f.nom = 'Riz pilaf' AND f.personne_id = 4 AND i.nom = 'Sel';

-- Relations pour les pâtes carbonara
INSERT INTO food_ingredient (food_id, ingredient_id, quantite_utilisee, unite) 
SELECT f.id, i.id, 100, 'grammes'
FROM food f, ingredient i 
WHERE f.nom = 'Pâtes carbonara' AND f.personne_id = 4 AND i.nom = 'Pâtes';

INSERT INTO food_ingredient (food_id, ingredient_id, quantite_utilisee, unite) 
SELECT f.id, i.id, 50, 'grammes'
FROM food f, ingredient i 
WHERE f.nom = 'Pâtes carbonara' AND f.personne_id = 4 AND i.nom = 'Jambon';

INSERT INTO food_ingredient (food_id, ingredient_id, quantite_utilisee, unite) 
SELECT f.id, i.id, 1, 'pièce'
FROM food f, ingredient i 
WHERE f.nom = 'Pâtes carbonara' AND f.personne_id = 4 AND i.nom = 'Œuf';

INSERT INTO food_ingredient (food_id, ingredient_id, quantite_utilisee, unite) 
SELECT f.id, i.id, 20, 'grammes'
FROM food f, ingredient i 
WHERE f.nom = 'Pâtes carbonara' AND f.personne_id = 4 AND i.nom = 'Beurre';

INSERT INTO food_ingredient (food_id, ingredient_id, quantite_utilisee, unite) 
SELECT f.id, i.id, 1, 'pincée'
FROM food f, ingredient i 
WHERE f.nom = 'Pâtes carbonara' AND f.personne_id = 4 AND i.nom = 'Poivre';

-- Mise à jour des séquences pour éviter les conflits d'ID
SELECT setval('personne_id_seq', (SELECT COALESCE(MAX(id), 0) FROM personne));
SELECT setval('ingredient_id_seq', (SELECT COALESCE(MAX(id), 0) FROM ingredient));
SELECT setval('food_id_seq', (SELECT COALESCE(MAX(id), 0) FROM food));

-- Commentaires sur les données insérées
COMMENT ON TABLE personne IS 'Table mise à jour avec des utilisateurs d''exemple';
COMMENT ON TABLE ingredient IS 'Table mise à jour avec des ingrédients de base';
COMMENT ON TABLE food IS 'Table mise à jour avec des aliments d''exemple';
COMMENT ON TABLE food_ingredient IS 'Table mise à jour avec les associations ingrédients-aliments';