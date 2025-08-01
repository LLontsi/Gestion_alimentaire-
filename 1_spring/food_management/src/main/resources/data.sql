-- Insérer des données de test
INSERT INTO personnes (nom, prenom, email, telephone) VALUES
('Doe', 'John', 'john.doe@email.com', '123456789'),
('Smith', 'Jane', 'jane.smith@email.com', '987654321'),
('Mbarga', 'Paul', 'paul.mbarga@email.com', '696123456'),
('Ngono', 'Marie', 'marie.ngono@email.com', '677987654');

INSERT INTO ingredients (nom, description, type) VALUES
('Farine', 'Farine de blé', 'NATUREL'),
('Sucre', 'Sucre blanc raffiné', 'NATUREL'),
('Sel', 'Sel de cuisine', 'NATUREL'),
('Huile', 'Huile de tournesol', 'NATUREL'),
('Levure', 'Levure de boulanger', 'NATUREL'),
('Vanille', 'Extrait de vanille', 'AROME'),
('Colorant rouge', 'Colorant alimentaire rouge', 'COLORANT'),
('Conservateur E200', 'Acide sorbique', 'CONSERVATEUR');

INSERT INTO foods (nom, description, categorie, calories, personne_id) VALUES
('Pain', 'Pain de mie blanc', 'CEREALES', 250.0, 1),
('Pomme', 'Pomme rouge du Cameroun', 'FRUITS', 52.0, 2),
('Banane', 'Banane plantain', 'FRUITS', 89.0, 1),
('Riz', 'Riz jasmin', 'CEREALES', 130.0, 3),
('Poulet', 'Blanc de poulet fermier', 'PROTEINES', 165.0, 4),
('Lait', 'Lait entier frais', 'PRODUITS_LAITIERS', 61.0, 2),
('Tomate', 'Tomate fraîche', 'LEGUMES', 18.0, 3),
('Carotte', 'Carotte bio', 'LEGUMES', 25.0, 1);

-- Associer des ingrédients aux aliments
INSERT INTO food_ingredients (food_id, ingredient_id) VALUES
(1, 1), -- Pain - Farine
(1, 2), -- Pain - Sucre
(1, 3), -- Pain - Sel
(1, 5), -- Pain - Levure
(5, 3), -- Poulet - Sel
(6, 8); -- Lait - Conservateur

INSERT INTO images (nom_fichier, chemin, type_mime, taille, description, food_id) VALUES
('pain.jpg', '/images/foods/pain.jpg', 'image/jpeg', 245760, 'Photo du pain de mie', 1),
('pomme.png', '/images/foods/pomme.png', 'image/png', 156780, 'Photo de la pomme rouge', 2),
('banane.jpg', '/images/foods/banane.jpg', 'image/jpeg', 198450, 'Photo de la banane plantain', 3),
('riz.webp', '/images/foods/riz.webp', 'image/webp', 89340, 'Photo du riz jasmin', 4);