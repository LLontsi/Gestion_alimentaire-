version: '3.8'

services:
  postgres:
    image: josee31/postgres-food:latest
    container_name: postgres_food_flask
    environment:
      POSTGRES_DB: food1_management_db
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
    ports:
      - "54323:5432"
    volumes:
      - postgres_data_flask:/var/lib/postgresql/data
    networks:
      - food_network_flask
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U postgres"]
      interval: 30s
      timeout: 10s
      retries: 3

  app:
    image: josee31/food-management-springboot:latest
    container_name: food_management_springboot_app
    ports:
      - "8082:8082"
    environment:
      SPRING_PROFILES_ACTIVE: production
      SPRING_DATASOURCE_URL: jdbc:postgresql://postgres:5432/food1_management_db
      SPRING_DATASOURCE_USERNAME: postgres
      SPRING_DATASOURCE_PASSWORD: postgres
      SERVER_PORT: 8082
    depends_on:
      postgres:
        condition: service_healthy
    networks:
      - food_network_flask
    restart: unless-stopped
    volumes:
      - app_images:/app/uploads/images

volumes:
  postgres_data_flask:
  app_images:

networks:
  food_network_flask:
    driver: bridge

# ========================================
# 🚀 FOOD MANAGEMENT SPRINGBOOT API - DOCUMENTATION COMPLÈTE
# ========================================
# 
# 🏠 URLs PRINCIPALES:
# - API Base: http://localhost:8082/api
# - Documentation Interactive: http://localhost:8082/api/documentation
# - Page de Tests: http://localhost:8082/api/test
# - Status Système: http://localhost:8082/api/status
# - Accueil: http://localhost:8082/
#
# 🧪 APIs DE TEST ET DOCUMENTATION:
#
# - GET http://localhost:8082/api/documentation
#   Description: Interface web complète de documentation des APIs avec liens interactifs
#   Réponse: Page HTML avec documentation complète et liens de test
#
# - GET http://localhost:8082/api/test
#   Description: Page interactive pour tester les APIs depuis le navigateur
#   Réponse: Interface de test avec JavaScript intégré
#
# - GET http://localhost:8082/api/status
#   Description: Statut du système et des services
#   Réponse: {
#     "statut": "OPERATIONNEL",
#     "application": "Food Management System",
#     "version": "1.0.0",
#     "timestamp": "2025-06-11T18:00:00",
#     "services": {
#       "personnes": "✅ Actif",
#       "aliments": "✅ Actif",
#       "ingredients": "✅ Actif"
#     },
#     "endpoints_disponibles": {
#       "documentation": "/api/documentation",
#       "tests": "/api/test",
#       "personnes": "/api/personnes",
#       "aliments": "/api/foods",
#       "ingredients": "/api/ingredients"
#     }
#   }
#
# - GET http://localhost:8082/api/demo-data
#   Description: Créer des données de démonstration pour tester l'application
#   Réponse: {
#     "statut": "DONNEES_DEMO_CREEES",
#     "message": "Données de démonstration créées avec succès",
#     "details": {
#       "personnes_creees": 2,
#       "ingredients_crees": 3,
#       "aliments_crees": 5
#     },
#     "liens_test": ["/api/personnes", "/api/foods", "/api/ingredients"]
#   }
#
# - GET http://localhost:8082/api/categories
#   Description: Liste des catégories et types disponibles
#   Réponse: {
#     "categories_aliments": ["LEGUMES", "FRUITS", "VIANDES", "CEREALES", "DESSERTS"],
#     "types_ingredients": ["FRAIS", "SURGELE", "CONSERVE", "SEC"],
#     "types_evenements": ["MARIAGE", "ANNIVERSAIRE", "ENTREPRISE", "COCKTAIL"]
#   }
#
# 👥 APIs GESTION DES PERSONNES:
#
# - POST http://localhost:8082/api/personnes
#   Description: Créer une nouvelle personne avec validations métier
#   Body: {"nom":"Martin","email":"jean.martin@example.com","motDePasse":"password123","telephone":"0123456789"}
#   Réponse: {
#     "id": 1,
#     "nom": "Martin",
#     "email": "jean.martin@example.com",
#     "telephone": "0123456789",
#     "dateCreation": "2025-06-11T18:00:00"
#   }
#
# - GET http://localhost:8082/api/personnes/{id}
#   Description: Obtenir une personne par ID
#   Réponse: {
#     "id": 1,
#     "nom": "Martin",
#     "email": "jean.martin@example.com",
#     "telephone": "0123456789"
#   }
#
# - GET http://localhost:8082/api/personnes
#   Description: Lister toutes les personnes
#   Réponse: [
#     {
#       "id": 1,
#       "nom": "Martin",
#       "email": "jean.martin@example.com",
#       "telephone": "0123456789"
#     }
#   ]
#
# - GET http://localhost:8082/api/personnes/recherche?nom=Martin
#   Description: Rechercher des personnes par nom (recherche partielle)
#   Réponse: [
#     {
#       "id": 1,
#       "nom": "Martin",
#       "email": "jean.martin@example.com"
#     }
#   ]
#
# - GET http://localhost:8082/api/personnes/email/jean.martin@example.com
#   Description: Obtenir une personne par email
#   Réponse: {
#     "id": 1,
#     "nom": "Martin",
#     "email": "jean.martin@example.com"
#   }
#
# - PUT http://localhost:8082/api/personnes/{id}
#   Description: Mettre à jour une personne
#   Body: {"id":1,"nom":"Martin","email":"jean.martin@example.com","motDePasse":"newpassword"}
#   Réponse: {
#     "id": 1,
#     "nom": "Martin",
#     "email": "jean.martin@example.com"
#   }
#
# - DELETE http://localhost:8082/api/personnes/{id}
#   Description: Supprimer une personne (vérifie les contraintes métier)
#   Réponse: 204 No Content
#
# - GET http://localhost:8082/api/personnes/email/disponible?email=test@example.com
#   Description: Vérifier la disponibilité d'un email
#   Réponse: {"email": "test@example.com", "disponible": true}
#
# - POST http://localhost:8082/api/personnes/authentification
#   Description: Authentifier une personne
#   Body: {"email":"jean.martin@example.com","motDePasse":"password123"}
#   Réponse: {
#     "statut": "AUTHENTIFIE",
#     "personne": {
#       "id": 1,
#       "nom": "Martin",
#       "email": "jean.martin@example.com"
#     }
#   }
#
# - POST http://localhost:8082/api/personnes/{id}/mot-de-passe
#   Description: Changer le mot de passe d'une personne
#   Body: {"ancienMotDePasse":"password123","nouveauMotDePasse":"newpassword"}
#   Réponse: {
#     "statut": "MOT_DE_PASSE_MODIFIE",
#     "message": "Mot de passe modifié avec succès"
#   }
#
# - GET http://localhost:8082/api/personnes/statistiques
#   Description: Obtenir les statistiques générales des personnes
#   Réponse: {
#     "nombre_total_personnes": 25,
#     "inscriptions_30_derniers_jours": 5,
#     "date_generation": "2025-06-11T18:00:00"
#   }
#
# - GET http://localhost:8082/api/personnes/statistiques/inscriptions?debut=2025-01-01T00:00:00&fin=2025-06-11T23:59:59
#   Description: Obtenir les statistiques d'inscription pour une période
#   Réponse: {
#     "periode_debut": "2025-01-01T00:00:00",
#     "periode_fin": "2025-06-11T23:59:59",
#     "nombre_inscriptions": 15
#   }
#
# - GET http://localhost:8082/api/personnes/actives
#   Description: Lister les personnes actives (ayant créé des aliments)
#   Réponse: [
#     {
#       "id": 1,
#       "nom": "Martin",
#       "email": "jean.martin@example.com"
#     }
#   ]
#
# - POST http://localhost:8082/api/personnes/validation
#   Description: Valider les données d'une personne sans la créer
#   Body: {"nom":"Test","email":"test@example.com","motDePasse":"123456"}
#   Réponse: {
#     "statut": "VALIDE",
#     "message": "Toutes les données sont valides"
#   }
#
# 🍎 APIs GESTION DES ALIMENTS:
#
# - POST http://localhost:8082/api/foods
#   Description: Créer un nouvel aliment avec validations
#   Body: {"nom":"Salade César","description":"Salade avec parmesan","categorie":"LEGUMES","calories":150,"prix":8.50,"tempsPreparation":"15 minutes","personne":{"id":1}}
#   Réponse: {
#     "id": 1,
#     "nom": "Salade César",
#     "description": "Salade avec parmesan",
#     "categorie": "LEGUMES",
#     "calories": 150,
#     "prix": 8.50,
#     "tempsPreparation": "15 minutes"
#   }
#
# - GET http://localhost:8082/api/foods/{id}
#   Description: Obtenir un aliment par ID
#   Réponse: {
#     "id": 1,
#     "nom": "Salade César",
#     "categorie": "LEGUMES",
#     "calories": 150,
#     "prix": 8.50
#   }
#
# - GET http://localhost:8082/api/foods
#   Description: Lister tous les aliments
#   Réponse: [
#     {
#       "id": 1,
#       "nom": "Salade César",
#       "categorie": "LEGUMES",
#       "calories": 150
#     }
#   ]
#
# - GET http://localhost:8082/api/foods/recherche?nom=salade
#   Description: Rechercher des aliments par nom
#   Réponse: [
#     {
#       "id": 1,
#       "nom": "Salade César",
#       "categorie": "LEGUMES"
#     }
#   ]
#
# - GET http://localhost:8082/api/foods/categorie/LEGUMES
#   Description: Rechercher des aliments par catégorie
#   Réponse: [
#     {
#       "id": 1,
#       "nom": "Salade César",
#       "categorie": "LEGUMES",
#       "calories": 150
#     }
#   ]
#
# - GET http://localhost:8082/api/foods/createur/{personneId}
#   Description: Rechercher des aliments par créateur
#   Réponse: [
#     {
#       "id": 1,
#       "nom": "Salade César",
#       "categorie": "LEGUMES"
#     }
#   ]
#
# - PUT http://localhost:8082/api/foods/{id}
#   Description: Mettre à jour un aliment
#   Body: {"id":1,"nom":"Salade César Améliorée","calories":180,"personne":{"id":1}}
#   Réponse: {
#     "id": 1,
#     "nom": "Salade César Améliorée",
#     "calories": 180
#   }
#
# - DELETE http://localhost:8082/api/foods/{id}
#   Description: Supprimer un aliment (supprime aussi images et associations)
#   Réponse: 204 No Content
#
# - POST http://localhost:8082/api/foods/{foodId}/ingredients
#   Description: Ajouter un ingrédient à un aliment
#   Body: {"ingredientId":1,"quantite":200,"unite":"grammes"}
#   Réponse: {
#     "statut": "INGREDIENT_AJOUTE",
#     "message": "Ingrédient ajouté avec succès"
#   }
#
# - DELETE http://localhost:8082/api/foods/{foodId}/ingredients/{ingredientId}
#   Description: Supprimer un ingrédient d'un aliment
#   Réponse: {
#     "statut": "INGREDIENT_SUPPRIME",
#     "message": "Ingrédient supprimé avec succès"
#   }
#
# - GET http://localhost:8082/api/foods/{id}/calories
#   Description: Calculer les calories totales d'un aliment
#   Réponse: {
#     "food_id": 1,
#     "calories_totales": 350.5,
#     "date_calcul": "2025-06-11T18:00:00"
#   }
#
# - GET http://localhost:8082/api/foods/calories?min=100&max=300
#   Description: Rechercher des aliments par tranche de calories
#   Réponse: [
#     {
#       "id": 1,
#       "nom": "Salade César",
#       "calories": 150
#     }
#   ]
#
# - GET http://localhost:8082/api/foods/prix?min=5&max=15
#   Description: Rechercher des aliments par tranche de prix
#   Réponse: [
#     {
#       "id": 1,
#       "nom": "Salade César",
#       "prix": 8.50
#     }
#   ]
#
# - GET http://localhost:8082/api/foods/populaires?limite=5
#   Description: Obtenir les aliments les plus populaires
#   Réponse: [
#     {
#       "id": 1,
#       "nom": "Salade César",
#       "categorie": "LEGUMES"
#     }
#   ]
#
# - GET http://localhost:8082/api/foods/ingredient/{ingredientId}
#   Description: Rechercher des aliments contenant un ingrédient
#   Réponse: [
#     {
#       "id": 1,
#       "nom": "Salade César"
#     }
#   ]
#
# - GET http://localhost:8082/api/foods/nom/disponible?nom=Salade&personneId=1
#   Description: Vérifier la disponibilité d'un nom d'aliment pour un créateur
#   Réponse: {
#     "nom": "Salade",
#     "personne_id": 1,
#     "disponible": true
#   }
#
# - POST http://localhost:8082/api/foods/{id}/dupliquer
#   Description: Dupliquer un aliment pour un autre créateur
#   Body: {"nouveauCreateurId":2}
#   Réponse: {
#     "id": 2,
#     "nom": "Copie de Salade César",
#     "categorie": "LEGUMES"
#   }
#
# - GET http://localhost:8082/api/foods/statistiques
#   Description: Obtenir les statistiques des aliments
#   Réponse: {
#     "nombre_total_aliments": 45,
#     "date_generation": "2025-06-11T18:00:00"
#   }
#
# - POST http://localhost:8082/api/foods/validation
#   Description: Valider les données d'un aliment
#   Body: {"nom":"Test","categorie":"LEGUMES","personne":{"id":1}}
#   Réponse: {
#     "statut": "VALIDE",
#     "message": "Toutes les données sont valides"
#   }
#
# 🥕 APIs GESTION DES INGRÉDIENTS:
#
# - POST http://localhost:8082/api/ingredients
#   Description: Créer un nouvel ingrédient
#   Body: {"nom":"Tomate cerise","description":"Petites tomates rouges","type":"FRAIS","quantite":500,"unite":"grammes"}
#   Réponse: {
#     "id": 1,
#     "nom": "Tomate cerise",
#     "description": "Petites tomates rouges",
#     "type": "FRAIS",
#     "quantite": 500,
#     "unite": "grammes"
#   }
#
# - GET http://localhost:8082/api/ingredients/{id}
#   Description: Obtenir un ingrédient par ID
#   Réponse: {
#     "id": 1,
#     "nom": "Tomate cerise",
#     "type": "FRAIS",
#     "quantite": 500
#   }
#
# - GET http://localhost:8082/api/ingredients/nom/{nom}
#   Description: Obtenir un ingrédient par nom exact
#   Réponse: {
#     "id": 1,
#     "nom": "Tomate cerise",
#     "type": "FRAIS"
#   }
#
# - GET http://localhost:8082/api/ingredients
#   Description: Lister tous les ingrédients
#   Réponse: [
#     {
#       "id": 1,
#       "nom": "Tomate cerise",
#       "description": "Petites tomates rouges",
#       "unite": "grammes"
#     }
#   ]
#
# - GET http://localhost:8082/api/ingredients/recherche?nom=tomate
#   Description: Rechercher des ingrédients par nom (partiel)
#   Réponse: [
#     {
#       "id": 1,
#       "nom": "Tomate cerise"
#     }
#   ]
#
# - GET http://localhost:8082/api/ingredients/type/FRAIS
#   Description: Rechercher des ingrédients par type
#   Réponse: [
#     {
#       "id": 1,
#       "nom": "Tomate cerise",
#       "type": "FRAIS"
#     }
#   ]
#
# - GET http://localhost:8082/api/ingredients/unite/grammes
#   Description: Rechercher des ingrédients par unité
#   Réponse: [
#     {
#       "id": 1,
#       "nom": "Tomate cerise",
#       "unite": "grammes"
#     }
#   ]
#
# - PUT http://localhost:8082/api/ingredients/{id}
#   Description: Mettre à jour un ingrédient
#   Body: {"id":1,"nom":"Tomate cerise bio","quantite":750,"type":"FRAIS"}
#   Réponse: {
#     "id": 1,
#     "nom": "Tomate cerise bio",
#     "quantite": 750
#   }
#
# - DELETE http://localhost:8082/api/ingredients/{id}
#   Description: Supprimer un ingrédient (vérifie qu'il n'est pas utilisé)
#   Réponse: 204 No Content
#
# - GET http://localhost:8082/api/ingredients/quantite?min=100&max=1000
#   Description: Rechercher des ingrédients par tranche de quantité
#   Réponse: [
#     {
#       "id": 1,
#       "nom": "Tomate cerise",
#       "quantite": 500
#     }
#   ]
#
# - GET http://localhost:8082/api/ingredients/utilises
#   Description: Lister les ingrédients utilisés dans des aliments
#   Réponse: [
#     {
#       "id": 1,
#       "nom": "Tomate cerise"
#     }
#   ]
#
# - GET http://localhost:8082/api/ingredients/non-utilises
#   Description: Lister les ingrédients non utilisés
#   Réponse: [
#     {
#       "id": 2,
#       "nom": "Épice rare"
#     }
#   ]
#
# - GET http://localhost:8082/api/ingredients/populaires?limite=10
#   Description: Obtenir les ingrédients les plus utilisés
#   Réponse: [
#     {
#       "id": 1,
#       "nom": "Tomate cerise"
#     }
#   ]
#
# - GET http://localhost:8082/api/ingredients/aliment/{foodId}
#   Description: Rechercher les ingrédients d'un aliment spécifique
#   Réponse: [
#     {
#       "id": 1,
#       "nom": "Tomate cerise"
#     }
#   ]
#
# - GET http://localhost:8082/api/ingredients/nom/disponible?nom=Basilic
#   Description: Vérifier la disponibilité d'un nom d'ingrédient
#   Réponse: {
#     "nom": "Basilic",
#     "disponible": true
#   }
#
# - GET http://localhost:8082/api/ingredients/suggestions?nom=tom&type=FRAIS
#   Description: Suggérer des ingrédients similaires
#   Réponse: [
#     {
#       "id": 1,
#       "nom": "Tomate cerise"
#     }
#   ]
#
# - GET http://localhost:8082/api/ingredients/statistiques
#   Description: Obtenir les statistiques des ingrédients
#   Réponse: {
#     "nombre_total_ingredients": 25,
#     "repartition_par_type": {
#       "FRAIS": 15,
#       "SEC": 10
#     },
#     "date_generation": "2025-06-11T18:00:00"
#   }
#
# - GET http://localhost:8082/api/ingredients/statistiques/type/FRAIS
#   Description: Compter les ingrédients par type
#   Réponse: {
#     "type": "FRAIS",
#     "nombre_ingredients": 15
#   }
#
# - POST http://localhost:8082/api/ingredients/validation
#   Description: Valider les données d'un ingrédient
#   Body: {"nom":"Test","type":"FRAIS"}
#   Réponse: {
#     "statut": "VALIDE",
#     "message": "Toutes les données sont valides"
#   }
#
# 🖼️ APIs GESTION DES IMAGES:
#
# - POST http://localhost:8082/api/images/food/{foodId}
#   Description: Uploader une image pour un aliment (multipart/form-data)
#   Form-Data: file=image.jpg, typeImage=PRINCIPALE
#   Réponse: {
#     "statut": "IMAGE_UPLOADEE",
#     "message": "Image uploadée avec succès",
#     "image": {
#       "id": 1,
#       "nomFichier": "uuid_timestamp.jpg",
#       "typeImage": "PRINCIPALE",
#       "tailleFichier": 245760
#     },
#     "url_acces": "/api/images/fichier/uuid_timestamp.jpg"
#   }
#
# - POST http://localhost:8082/api/images/ingredient/{ingredientId}
#   Description: Uploader une image pour un ingrédient
#   Form-Data: file=image.jpg, typeImage=PRINCIPALE
#   Réponse: {
#     "statut": "IMAGE_UPLOADEE",
#     "message": "Image uploadée avec succès",
#     "image": {
#       "id": 1,
#       "nomFichier": "uuid_timestamp.jpg"
#     }
#   }
#
# - GET http://localhost:8082/api/images/{id}
#   Description: Obtenir les métadonnées d'une image
#   Réponse: {
#     "id": 1,
#     "nomFichier": "uuid_timestamp.jpg",
#     "typeImage": "PRINCIPALE",
#     "tailleFichier": 245760,
#     "dateUpload": "2025-06-11T18:00:00"
#   }
#
# - GET http://localhost:8082/api/images/fichier/{nomFichier}
#   Description: Servir le fichier image (contenu binaire)
#   Réponse: Fichier image (JPEG, PNG, etc.)
#
# - GET http://localhost:8082/api/images
#   Description: Lister toutes les images
#   Réponse: [
#     {
#       "id": 1,
#       "nomFichier": "uuid_timestamp.jpg",
#       "typeImage": "PRINCIPALE"
#     }
#   ]
#
# - GET http://localhost:8082/api/images/type/PRINCIPALE
#   Description: Rechercher des images par type
#   Réponse: [
#     {
#       "id": 1,
#       "nomFichier": "uuid_timestamp.jpg",
#       "typeImage": "PRINCIPALE"
#     }
#   ]
#
# - GET http://localhost:8082/api/images/food/{foodId}
#   Description: Obtenir toutes les images d'un aliment
#   Réponse: [
#     {
#       "id": 1,
#       "nomFichier": "uuid_timestamp.jpg",
#       "typeImage": "PRINCIPALE"
#     }
#   ]
#
# - GET http://localhost:8082/api/images/food/{foodId}/principale
#   Description: Obtenir l'image principale d'un aliment
#   Réponse: {
#     "id": 1,
#     "nomFichier": "uuid_timestamp.jpg",
#     "typeImage": "PRINCIPALE"
#   }
#
# - GET http://localhost:8082/api/images/ingredient/{ingredientId}
#   Description: Obtenir l'image d'un ingrédient
#   Réponse: {
#     "id": 1,
#     "nomFichier": "uuid_timestamp.jpg"
#   }
#
# - PUT http://localhost:8082/api/images/{id}
#   Description: Mettre à jour les métadonnées d'une image
#   Body: {"typeImage":"GALERIE","description":"Image mise à jour"}
#   Réponse: {
#     "id": 1,
#     "typeImage": "GALERIE",
#     "description": "Image mise à jour"
#   }
#
# - DELETE http://localhost:8082/api/images/{id}
#   Description: Supprimer une image (fichier + enregistrement)
#   Réponse: {
#     "statut": "IMAGE_SUPPRIMEE",
#     "message": "Image supprimée avec succès"
#   }
#
# - DELETE http://localhost:8082/api/images/food/{foodId}
#   Description: Supprimer toutes les images d'un aliment
#   Réponse: {
#     "statut": "IMAGES_SUPPRIMEES",
#     "message": "Images supprimées avec succès",
#     "nombre_images_supprimees": 3
#   }
#
# - POST http://localhost:8082/api/images/{id}/type
#   Description: Changer le type d'une image
#   Body: {"nouveauType":"MINIATURE"}
#   Réponse: {
#     "statut": "TYPE_MODIFIE",
#     "message": "Type d'image modifié avec succès"
#   }
#
# - POST http://localhost:8082/api/images/{id}/principale
#   Description: Définir une image comme principale
#   Réponse: {
#     "statut": "IMAGE_PRINCIPALE_DEFINIE",
#     "message": "Image définie comme principale avec succès"
#   }
#
# - GET http://localhost:8082/api/images/taille?min=100000&max=5000000
#   Description: Rechercher des images par tranche de taille
#   Réponse: [
#     {
#       "id": 1,
#       "nomFichier": "uuid_timestamp.jpg",
#       "tailleFichier": 245760
#     }
#   ]
#
# - GET http://localhost:8082/api/images/periode?debut=2025-01-01T00:00:00&fin=2025-06-11T23:59:59
#   Description: Rechercher des images par période d'upload
#   Réponse: [
#     {
#       "id": 1,
#       "nomFichier": "uuid_timestamp.jpg",
#       "dateUpload": "2025-06-11T18:00:00"
#     }
#   ]
#
# - GET http://localhost:8082/api/images/statistiques
#   Description: Obtenir les statistiques des images
#   Réponse: {
#     "nombre_total_images": 15,
#     "taille_totale_octets": 25600000,
#     "taille_totale_mb": 24.41,
#     "repartition_par_type": {
#       "PRINCIPALE": 8,
#       "GALERIE": 5,
#       "MINIATURE": 2
#     },
#     "taille_moyenne_octets": 1706666,
#     "date_generation": "2025-06-11T18:00:00"
#   }
#
# - POST http://localhost:8082/api/images/validation
#   Description: Valider un fichier image avant upload
#   Form-Data: file=image.jpg
#   Réponse: {
#     "statut": "VALIDE",
#     "message": "Le fichier est valide",
#     "nom_fichier": "image.jpg",
#     "taille_octets": 245760,
#     "type_mime": "image/jpeg"
#   }
#
# - POST http://localhost:8082/api/images/nettoyage
#   Description: Nettoyer les fichiers orphelins
#   Réponse: {
#     "statut": "NETTOYAGE_TERMINE",
#     "message": "Nettoyage des fichiers orphelins terminé",
#     "fichiers_supprimes": 3
#   }
#
# - GET http://localhost:8082/api/images/nom-unique?original=mon_image.jpg
#   Description: Générer un nom de fichier unique
#   Réponse: {
#     "nom_original": "mon_image.jpg",
#     "nom_unique": "uuid_1702554000_mon_image.jpg"
#   }
#
# 🤖 APIs CHATBOT INTELLIGENT:
#
# - GET http://localhost:8082/api/chatbot/demo
#   Description: Interface de démonstration du chatbot (page HTML interactive)
#   Réponse: Page HTML complète avec interface de chat
#
# - POST http://localhost:8082/api/chatbot/message
#   Description: Envoyer un message au chatbot
#   Body: {"message":"Bonjour, comment créer un plat ?","userId":1}
#   Réponse: {
#     "statut": "SUCCESS",
#     "reponse": "Pour créer un plat, vous devez utiliser l'endpoint POST /api/foods...",
#     "intention_detectee": "RECHERCHE_ALIMENT",
#     "confiance": 0.85,
#     "timestamp": "2025-06-11T18:00:00"
#   }
#
# - POST http://localhost:8082/api/chatbot/analyser-intention
#   Description: Analyser l'intention d'un message
#   Body: {"message":"Je cherche des recettes avec des tomates"}
#   Réponse: {
#     "message_analyse": "Je cherche des recettes avec des tomates",
#     "analyse": {
#       "intention": "RECHERCHE_ALIMENT",
#       "confiance": 0.75,
#       "scores_detailles": {
#         "RECHERCHE_ALIMENT": 0.75,
#         "INGREDIENT": 0.45
#       }
#     },
#     "timestamp": "2025-06-11T18:00:00"
#   }
#
# - POST http://localhost:8082/api/chatbot/suggestions/aliments
#   Description: Obtenir des suggestions d'aliments
#   Body: {"categorie":"LEGUMES","calories_max":200}
#   Réponse: {
#     "statut": "SUCCESS",
#     "reponse": "Voici mes suggestions d'aliments :",
#     "suggestions": [
#       {
#         "nom": "Salade César",
#         "categorie": "LEGUMES",
#         "calories": "150 kcal"
#       }
#     ],
#     "nombre_suggestions": 1
#   }
#
# - POST http://localhost:8082/api/chatbot/conseils-nutrition
#   Description: Obtenir des conseils nutritionnels
#   Body: {"objectif":"perte de poids"}
#   Réponse: {
#     "statut": "SUCCESS",
#     "reponse": "Voici mes conseils nutritionnels personnalisés :",
#     "conseils": [
#       "🥗 Mangez au moins 5 fruits et légumes par jour",
#       "💧 Buvez 1,5 à 2 litres d'eau par jour",
#       "⚖️ Créez un déficit calorique modéré"
#     ],
#     "disclaimer": "⚠️ Ces conseils sont généraux. Consultez un professionnel pour des recommandations personnalisées."
#   }
#
# - POST http://localhost:8082/api/chatbot/aide-planification
#   Description: Aider à la planification de repas
#   Body: {"preferences":{"max_calories":600},"nombreJours":7,"userId":1}
#   Réponse: {
#     "statut": "SUCCESS",
#     "reponse": "Voici mes conseils pour bien planifier vos repas :",
#     "conseils_planification": [
#       "📅 Planifiez vos repas à l'avance pour éviter l'improvisation",
#       "🛒 Préparez votre liste de courses en fonction de votre plan"
#     ],
#     "types_repas": ["Petit-déjeuner", "Déjeuner", "Dîner", "Collation"]
#   }
#
# - GET http://localhost:8082/api/chatbot/info-aliment/tomate
#   Description: Obtenir des informations sur un aliment
#   Réponse: {
#     "statut": "SUCCESS",
#     "reponse": "Voici les informations sur Tomate :",
#     "aliment": {
#       "nom": "Tomate",
#       "categorie": "LEGUMES",
#       "calories": "18 kcal"
#     },
#     "autres_resultats": 2
#   }
#
# - POST http://localhost:8082/api/chatbot/alternatives
#   Description: Suggérer des alternatives à un aliment
#   Body: {"nomAliment":"beurre","raison":"calories"}
#   Réponse: {
#     "statut": "SUCCESS",
#     "reponse": "Voici des alternatives à beurre :",
#     "aliment_original": {
#       "nom": "Beurre",
#       "calories": "750 kcal"
#     },
#     "alternatives": [
#       {
#         "nom": "Huile d'olive",
#         "calories": "900 kcal"
#       }
#     ],
#     "raison": "calories"
#   }
#
# - POST http://localhost:8082/api/chatbot/calculer-nutrition
#   Description: Calculer la nutrition d'une liste d'aliments
#   Body: {"aliments":[{"nom":"Salade","calories":"150"},{"nom":"Poulet","calories":"250"}]}
#   Réponse: {
#     "statut": "SUCCESS",
#     "reponse": "Voici l'analyse nutritionnelle de votre sélection :",
#     "calories_totales": 400,
#     "nombre_aliments": 2,
#     "conseils": [
#       "✅ Bonne présence de légumes",
#       "✅ Apport calorique équilibré"
#     ]
#   }
#
# - POST http://localhost:8082/api/chatbot/aide-buffet
#   Description: Aider à l'organisation d'un buffet
#   Body: {"typeEvenement":"MARIAGE","nombreInvites":50,"budget":2500}
#   Réponse: {
#     "statut": "SUCCESS",
#     "reponse": "Voici mes conseils pour organiser votre mariage :",
#     "conseils": [
#       "📋 Planifiez 2-3 semaines à l'avance",
#       "💒 Pour un mariage : privilégiez l'élégance et la variété"
#     ],
#     "type_evenement": "MARIAGE",
#     "nombre_invites": 50,
#     "budget_par_personne": 50
#   }
#
# - POST http://localhost:8082/api/chatbot/rechercher-recettes
#   Description: Rechercher des recettes par ingrédients
#   Body: {"ingredients":["tomate","fromage","basilic"]}
#   Réponse: {
#     "statut": "SUCCESS",
#     "reponse": "Voici des recettes que vous pouvez réaliser avec vos ingrédients :",
#     "ingredients_recherches": ["tomate", "fromage", "basilic"],
#     "recettes": [
#       {
#         "nom": "Pizza Margherita",
#         "temps_preparation": "30 minutes",
#         "ingredient_correspondant": "tomate"
#       }
#     ],
#     "nombre_recettes": 1
#   }
#
# - GET http://localhost:8082/api/chatbot/historique/{userId}?limite=20
#   Description: Obtenir l'historique de conversation
#   Réponse: {
#     "user_id": 1,
#     "historique": [
#       {
#         "message": "Bonjour",
#         "expediteur": "USER",
#         "timestamp": "2025-06-11T18:00:00"
#       }
#     ],
#     "nombre_messages": 1,
#     "limite": 20
#   }
#
# - POST http://localhost:8082/api/chatbot/reinitialiser/{userId}
#   Description: Réinitialiser le contexte de conversation
#   Réponse: {
#     "statut": "CONTEXTE_REINITIALISE",
#     "message": "Le contexte de conversation a été réinitialisé",
#     "user_id": 1,
#     "timestamp": "2025-06-11T18:00:00"
#   }
#
# - GET http://localhost:8082/api/chatbot/statistiques
#   Description: Obtenir les statistiques d'utilisation du chatbot
#   Réponse: {
#     "statistiques": {
#       "messages_traites": 150,
#       "utilisateurs_actifs": 25,
#       "services_disponibles": {
#         "foodService": true,
#         "ingredientService": true
#       }
#     },
#     "date_generation": "2025-06-11T18:00:00"
#   }
#
# - GET http://localhost:8082/api/chatbot/test
#   Description: Test rapide du chatbot
#   Réponse: {
#     "statut": "SUCCESS",
#     "message": "Chatbot opérationnel",
#     "test_reponse": {
#       "reponse": "👋 Bonjour ! Je suis votre assistant culinaire."
#     },
#     "timestamp": "2025-06-11T18:00:00"
#   }
#
# - GET http://localhost:8082/api/chatbot/aide
#   Description: Obtenir l'aide sur les fonctionnalités disponibles
#   Réponse: {
#     "fonctionnalites": {
#       "recherche_aliments": "Rechercher des informations sur les aliments",
#       "conseils_nutrition": "Obtenir des conseils nutritionnels personnalisés",
#       "planification_repas": "Aide à la planification de menus"
#     },
#     "exemples_questions": [
#       "Peux-tu me donner des infos sur les épinards ?",
#       "Comment bien manger pour perdre du poids ?"
#     ],
#     "conseils_utilisation": [
#       "Soyez précis dans vos questions",
#       "N'hésitez pas à donner du contexte"
#     ]
#   }
#
# 🍽️ APIs GESTION DES BUFFETS:
#
# - POST http://localhost:8082/api/buffets
#   Description: Créer un nouveau buffet pour un événement
#   Body: {"organisateurId":1,"nomEvenement":"Mariage Sophie","dateEvenement":"2024-07-15T18:00:00","nombreInvites":80,"typeEvenement":"MARIAGE","budget":3000}
#   Réponse: {
#     "statut": "BUFFET_CREE",
#     "message": "Buffet créé avec succès",
#     "buffet_id": 1,
#     "organisateur_id": 1,
#     "nom_evenement": "Mariage Sophie",
#     "date_evenement": "2024-07-15T18:00:00",
#     "nombre_invites": 80,
#     "type_evenement": "MARIAGE",
#     "budget": 3000
#   }
#
# - POST http://localhost:8082/api/buffets/{buffetId}/planification-automatique
#   Description: Planifier automatiquement un buffet
#   Body: {"preferences":["VIANDES","DESSERTS"]}
#   Réponse: {
#     "statut": "BUFFET_PLANIFIE",
#     "message": "Buffet planifié automatiquement avec succès",
#     "buffet_id": 1,
#     "plan_buffet": {
#       "VIANDES": [
#         {
#           "food": {
#             "nom": "Bœuf bourguignon",
#             "categorie": "VIANDES"
#           },
#           "quantite_personnes": 80,
#           "portion_par_personne": 225,
#           "quantite_totale_grammes": 18000
#         }
#       ]
#     }
#   }
#
# - POST http://localhost:8082/api/buffets/{buffetId}/aliments
#   Description: Ajouter un aliment au buffet
#   Body: {"foodId":1,"quantitePersonnes":50,"priorite":2}
#   Réponse: {
#     "statut": "ALIMENT_AJOUTE",
#     "message": "Aliment ajouté au buffet avec succès",
#     "buffet_id": 1,
#     "food_id": 1,
#     "quantite_personnes": 50,
#     "priorite": 2
#   }
#
# - DELETE http://localhost:8082/api/buffets/{buffetId}/aliments/{foodId}
#   Description: Supprimer un aliment du buffet
#   Réponse: {
#     "statut": "ALIMENT_SUPPRIME",
#     "message": "Aliment supprimé du buffet avec succès"
#   }
#
# - PUT http://localhost:8082/api/buffets/{buffetId}/aliments/{foodId}/quantite
#   Description: Modifier la quantité d'un aliment dans le buffet
#   Body: {"nouvelleQuantitePersonnes":75}
#   Réponse: {
#     "statut": "QUANTITE_MODIFIEE",
#     "message": "Quantité modifiée avec succès",
#     "buffet_id": 1,
#     "food_id": 1,
#     "nouvelle_quantite": 75
#   }
#
# - GET http://localhost:8082/api/buffets/{buffetId}/quantites
#   Description: Calculer les quantités nécessaires pour le buffet
#   Réponse: {
#     "buffet_id": 1,
#     "quantites": {
#       "Bœuf bourguignon": {
#         "nom_aliment": "Bœuf bourguignon",
#         "categorie": "VIANDES",
#         "personnes_servies": 80,
#         "portion_par_personne_g": 225,
#         "quantite_totale_g": 18000,
#         "quantite_totale_kg": 18,
#         "priorite": 2,
#         "cout_estime": 360
#       }
#     },
#     "date_calcul": "2025-06-11T18:00:00"
#   }
#
# - GET http://localhost:8082/api/buffets/{buffetId}/cout
#   Description: Estimer le coût total du buffet
#   Réponse: {
#     "buffet_id": 1,
#     "cout_estime": 2850.0,
#     "devise": "EUR",
#     "date_calcul": "2025-06-11T18:00:00"
#   }
#
# - GET http://localhost:8082/api/buffets/{buffetId}/equilibre-nutritionnel
#   Description: Vérifier l'équilibre nutritionnel du buffet
#   Réponse: {
#     "buffet_id": 1,
#     "rapport_nutritionnel": {
#       "nombre_aliments_total": 8,
#       "repartition_categories": {
#         "VIANDES": 3,
#         "LEGUMES": 2,
#         "DESSERTS": 3
#       },
#       "calories_totales": 45600,
#       "calories_par_personne": 570,
#       "suggestions": [
#         "✅ Bonne présence de légumes",
#         "✅ Apport calorique équilibré"
#       ],
#       "equilibre_score": 85
#     },
#     "date_analyse": "2025-06-11T18:00:00"
#   }
#
# - GET http://localhost:8082/api/buffets/{buffetId}/liste-courses
#   Description: Générer une liste de courses pour le buffet
#   Réponse: {
#     "buffet_id": 1,
#     "liste_courses": {
#       "Bœuf (kg)": 12.5,
#       "Carottes (kg)": 3.2,
#       "Oignons (kg)": 2.1,
#       "Vin rouge (L)": 1.5
#     },
#     "nombre_ingredients": 4,
#     "date_generation": "2025-06-11T18:00:00"
#   }
#
# - GET http://localhost:8082/api/buffets/suggestions?typeEvenement=MARIAGE&nombreInvites=50&budget=2000
#   Description: Obtenir des suggestions d'aliments pour un type d'événement
#   Réponse: {
#     "type_evenement": "MARIAGE",
#     "nombre_invites": 50,
#     "budget": 2000,
#     "suggestions": {
#       "VIANDES": [
#         {
#           "nom": "Saumon grillé",
#           "categorie": "VIANDES",
#           "prix": 18.50
#         }
#       ],
#       "LEGUMES": [
#         {
#           "nom": "Ratatouille",
#           "categorie": "LEGUMES",
#           "prix": 8.50
#         }
#       ]
#     }
#   }
#
# - GET http://localhost:8082/api/buffets/portions?categorie=VIANDES&typeEvenement=MARIAGE
#   Description: Calculer les portions standards
#   Réponse: {
#     "categorie": "VIANDES",
#     "type_evenement": "MARIAGE",
#     "portion_standard_grammes": 225,
#     "unite": "grammes par personne"
#   }
#
# - POST http://localhost:8082/api/buffets/{buffetId}/optimisation
#   Description: Optimiser le buffet selon le budget
#   Body: {"budgetMax":2500}
#   Réponse: {
#     "buffet_id": 1,
#     "optimisation": {
#       "cout_actuel": 2850.0,
#       "budget_max": 2500.0,
#       "economie_necessaire": 350.0,
#       "statut": "OPTIMISATION_NECESSAIRE",
#       "recommandations": [
#         "Réduction nécessaire : 350.00€ (12.3%)",
#         "Réduire les quantités de 10-15%",
#         "Remplacer les aliments les plus chers par des alternatives"
#       ]
#     },
#     "date_optimisation": "2025-06-11T18:00:00"
#   }
#
# - GET http://localhost:8082/api/buffets/{buffetId}/planning-preparation
#   Description: Planifier les temps de préparation
#   Réponse: {
#     "buffet_id": 1,
#     "planning_preparation": {
#       "date_evenement": "2024-07-15T18:00:00",
#       "taches": [
#         {
#           "aliment": "Bœuf bourguignon",
#           "priorite": 1,
#           "duree_minutes": 180,
#           "heure_debut": "2024-07-15T14:00:00",
#           "heure_fin": "2024-07-15T17:00:00"
#         }
#       ],
#       "duree_totale_minutes": 360,
#       "heure_debut_preparation": "2024-07-15T14:00:00",
#       "temps_avant_evenement_heures": 4
#     },
#     "date_generation": "2025-06-11T18:00:00"
#   }
#
# - POST http://localhost:8082/api/buffets/{buffetId}/dupliquer
#   Description: Dupliquer un buffet existant
#   Body: {"nouveauNom":"Anniversaire Tom","nouvelleDatete":"2024-08-15T16:00:00","nouveauNombreInvites":25}
#   Réponse: {
#     "statut": "BUFFET_DUPLIQUE",
#     "message": "Buffet dupliqué avec succès",
#     "buffet_original_id": 1,
#     "nouveau_buffet_id": 2,
#     "nouveau_nom": "Anniversaire Tom",
#     "nouvelle_date": "2024-08-15T16:00:00",
#     "nouveau_nombre_invites": 25
#   }
#
# - GET http://localhost:8082/api/buffets/{buffetId}/rapport
#   Description: Générer un rapport détaillé du buffet
#   Réponse: {
#     "buffet_id": 1,
#     "rapport_detaille": {
#       "informations_generales": {
#         "nom_evenement": "Mariage Sophie",
#         "organisateur": "Martin",
#         "date_evenement": "2024-07-15T18:00:00",
#         "nombre_invites": 80,
#         "type_evenement": "MARIAGE",
#         "budget_prevu": 3000
#       },
#       "quantites": {...},
#       "equilibre_nutritionnel": {...},
#       "analyse_financiere": {
#         "cout_estime": 2850.0,
#         "cout_par_personne": 35.625
#       },
#       "planning_preparation": {...},
#       "liste_courses": {...},
#       "problemes_detectes": [],
#       "date_generation_rapport": "2025-06-11T18:00:00"
#     }
#   }
#
# - POST http://localhost:8082/api/buffets/{buffetId}/realiser
#   Description: Marquer un buffet comme réalisé
#   Body: {"nombreInvitesReel":78,"commentaires":"Excellent événement"}
#   Réponse: {
#     "statut": "BUFFET_REALISE",
#     "message": "Buffet marqué comme réalisé avec succès",
#     "buffet_id": 1,
#     "nombre_invites_reel": 78,
#     "date_realisation": "2025-06-11T18:00:00"
#   }
#
# - GET http://localhost:8082/api/buffets/organisateur/{organisateurId}/historique
#   Description: Obtenir l'historique des buffets d'un organisateur
#   Réponse: {
#     "organisateur_id": 1,
#     "historique_buffets": [
#       {
#         "id": 1,
#         "nom_evenement": "Mariage Sophie",
#         "date_evenement": "2024-07-15T18:00:00",
#         "nombre_invites": 80,
#         "type_evenement": "MARIAGE",
#         "statut": "REALISE",
#         "cout_estime": 2850.0
#       }
#     ],
#     "nombre_buffets": 1
#   }
#
# - GET http://localhost:8082/api/buffets/organisateur/{organisateurId}/statistiques
#   Description: Calculer les statistiques d'un organisateur
#   Réponse: {
#     "organisateur_id": 1,
#     "statistiques": {
#       "nombre_buffets_total": 5,
#       "nombre_buffets_realises": 4,
#       "cout_moyen_buffet": 2650.0,
#       "nombre_invites_moyen": 65.4,
#       "types_evenements_favoris": {
#         "MARIAGE": 3,
#         "ANNIVERSAIRE": 2
#       }
#     },
#     "date_generation": "2025-06-11T18:00:00"
#   }
#
# - GET http://localhost:8082/api/buffets/{buffetId}/validation
#   Description: Valider la faisabilité d'un buffet
#   Réponse: {
#     "statut": "BUFFET_VALIDE",
#     "message": "Le buffet est faisable",
#     "buffet_id": 1,
#     "problemes": []
#   }
#
# - GET http://localhost:8082/api/buffets/types-evenements
#   Description: Obtenir les types d'événements supportés
#   Réponse: {
#     "types_evenements": ["MARIAGE", "ANNIVERSAIRE", "ENTREPRISE", "COCKTAIL"],
#     "details": {
#       "MARIAGE": {
#         "description": "Mariage avec repas complet",
#         "portions_multiplicateur": 1.5,
#         "cout_moyen_personne": 45.0
#       },
#       "ANNIVERSAIRE": {
#         "description": "Fête d'anniversaire",
#         "portions_multiplicateur": 1.2,
#         "cout_moyen_personne": 25.0
#       }
#     },
#     "nombre_types": 4
#   }
#
# 📊 EXEMPLES D'UTILISATION COMPLÈTE:
#
# 1️⃣ Créer une personne:
# curl -X POST http://localhost:8082/api/personnes \
#   -H "Content-Type: application/json" \
#   -d '{"nom":"Dupont","email":"marie.dupont@example.com","motDePasse":"password123","telephone":"0123456789"}'
#
# 2️⃣ Créer un aliment:
# curl -X POST http://localhost:8082/api/foods \
#   -H "Content-Type: application/json" \
#   -d '{"nom":"Ratatouille","description":"Légumes mijotés provençaux","categorie":"LEGUMES","calories":180,"personne":{"id":1}}'
#
# 3️⃣ Créer un ingrédient:
# curl -X POST http://localhost:8082/api/ingredients \
#   -H "Content-Type: application/json" \
#   -d '{"nom":"Courgette","type":"FRAIS","quantite":1000,"unite":"grammes"}'
#
# 4️⃣ Upload une image:
# curl -X POST http://localhost:8082/api/images/food/1 \
#   -F "file=@mon_plat.jpg" \
#   -F "typeImage=PRINCIPALE"
#
# 5️⃣ Créer un buffet:
# curl -X POST http://localhost:8082/api/buffets \
#   -H "Content-Type: application/json" \
#   -d '{"organisateurId":1,"nomEvenement":"Anniversaire Tom","dateEvenement":"2024-08-15T16:00:00","nombreInvites":25,"typeEvenement":"ANNIVERSAIRE","budget":800}'
#
# 6️⃣ Utiliser le chatbot:
# curl -X POST http://localhost:8082/api/chatbot/message \
#   -H "Content-Type: application/json" \
#   -d '{"message":"Comment créer un plan de repas équilibré ?","userId":1}'
#
# 7️⃣ Rechercher des aliments:
# curl -X GET "http://localhost:8082/api/foods/recherche?nom=salade"
#
# ========================================
# 🐳 COMMANDES DOCKER UTILES:
# ========================================
#
# Démarrer l'application:
# docker-compose up -d
#
# Voir les logs de l'application:
# docker-compose logs -f app
#
# Arrêter l'application:
# docker-compose down
#
# Reconstruire et redémarrer:
# docker-compose up --build -d
#
# Accéder à la base de données:
# docker exec -it postgres_food_flask psql -U postgres -d food1_management_db
#
# Vérifier l'état des conteneurs:
# docker-compose ps
#
# Supprimer tout (conteneurs, volumes, réseaux):
# docker-compose down -v --remove-orphans
#
# ========================================
# 🔧 CONFIGURATION ET TROUBLESHOOTING:
# ========================================
#
# Variables d'environnement importantes:
# - SPRING_PROFILES_ACTIVE: Profil Spring (production/development)
# - SPRING_DATASOURCE_URL: URL de connexion à la base de données
# - SERVER_PORT: Port du serveur Spring Boot (8082)
#
# Ports utilisés:
# - Application Spring Boot: 8082
# - Base de données PostgreSQL: 54323 (mappé sur 5432 dans le conteneur)
#
# Volumes persistants:
# - postgres_data_flask: Données de la base PostgreSQL
# - app_images: Images uploadées dans l'application
#
# Problèmes courants et solutions:
# - API ne répond pas: Vérifier que le conteneur app est démarré et en bonne santé
# - Erreur de base de données: Vérifier que PostgreSQL est démarré et accessible
# - Upload d'images échoue: Vérifier les permissions du volume app_images
# - Timeout sur les APIs: Vérifier les logs avec docker-compose logs -f app
#
# ========================================
# 🔒 SÉCURITÉ ET BONNES PRATIQUES:
# ========================================
#
# • Changez les mots de passe par défaut en production
# • Utilisez HTTPS en production avec un reverse proxy
# • Implémentez une authentification JWT si nécessaire
# • Validez toutes les entrées utilisateur côté serveur
# • Limitez la taille des fichiers uploadés
# • Configurez des logs de sécurité
# • Utilisez des requêtes préparées pour éviter les injections SQL
# • Scannez régulièrement les vulnérabilités des dépendances
# • Implémentez du rate limiting pour éviter les abus
# • Sauvegardez régulièrement la base de données
#
# ========================================
