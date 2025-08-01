# 🍽️ Food Management System - API Documentation

## Description

Le **Food Management System** est une application complète de gestion alimentaire développée en Spring Boot avec PostgreSQL. Elle offre des fonctionnalités avancées pour la gestion des aliments, ingrédients, planification de repas, organisation de buffets et assistance via un chatbot intelligent.

## Technologies Utilisées

- **Backend**: Spring Boot 3.x, Spring Data JPA, Spring Web
- **Base de données**: PostgreSQL 15+
- **Containerisation**: Docker & Docker Compose  
- **Java**: 17+
- **Documentation**: API REST avec format JSON

## Installation et Démarrage

### Prérequis
- Docker et Docker Compose installés
- Port 8080 disponible sur votre machine

### Démarrage avec Docker Compose

```bash
docker-compose up -d
```
URLs d accès

### API Base: http://localhost:8080/api
### Interface de test: http://localhost:8080/api/test-all
### Chatbot Demo: http://localhost:8080/api/chatbot-demo
### Test des modules: http://localhost:8080/test-modules
### Base de données: localhost:54322 (PostgreSQL)


version: '3.8'

services:
  postgres:
    image: lontsi/food-postgres:latest  # Votre image PostgreSQL personnalisée
    container_name: food_postgres
    environment:
      POSTGRES_DB: food_management_db
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: password
    ports:
      - "54321:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data
    networks:
      - food_network
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U postgres"]
      interval: 30s
      timeout: 10s
      retries: 3

  app:
    image: lontsi/foot:latest  # Votre image Spring Boot
    container_name: food_app
    ports:
      - "8080:8083"  # L'application tourne sur 8083 dans le conteneur
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://postgres:5432/food_management_db
      SPRING_DATASOURCE_USERNAME: postgres
      SPRING_DATASOURCE_PASSWORD: password
      SPRING_JPA_HIBERNATE_DDL_AUTO: update
    depends_on:
      postgres:
        condition: service_healthy
    networks:
      - food_network
    restart: unless-stopped

volumes:
  postgres_data:

networks:
  food_network:
    driver: bridge

# ========================================
# 🚀 URLs DES APIS DISPONIBLES
# ========================================
# 
# 🏠 URLs PRINCIPALES:
# - API Base: http://localhost:8080/api
#   Description: Point d'entrée principal de l'API
#   Réponse: Redirection vers la documentation
#
# - Interface de test complète: http://localhost:8080/api/test-all
#   Description: Interface HTML pour tester toutes les APIs automatiquement
#   Réponse: Page HTML interactive avec tests automatisés
#
# - Chatbot Demo: http://localhost:8080/api/chatbot-demo
#   Description: Interface de démonstration du chatbot alimentaire
#   Réponse: Page HTML avec chat en temps réel
#
# - Test des modules: http://localhost:8080/test-modules
#   Description: Interface pour tester les modules planification et buffet
#   Réponse: Page HTML avec formulaires de test
#
# - Page d'accueil: http://localhost:8080/
#   Description: Page d'accueil du système
#   Réponse: Page HTML d'accueil avec Thymeleaf
#
# - Dashboard: http://localhost:8080/dashboard
#   Description: Tableau de bord principal
#   Réponse: Page HTML du tableau de bord
#
# 🔧 APIs DE TEST:
#
# - GET http://localhost:8080/api/test
#   Description: Test de base pour vérifier que l'API fonctionne
#   Réponse: "API fonctionne correctement !"
#
# - GET http://localhost:8080/api/test/status
#   Description: Statut détaillé de tous les services
#   Réponse: {
#     "personnes_count": 10,
#     "foods_count": 25,
#     "ingredients_count": 50,
#     "images_count": 15,
#     "status": "OK"
#   }
#
# 👥 APIs PERSONNES:
#
# - GET http://localhost:8080/api/personnes
#   Description: Récupère la liste complète de toutes les personnes
#   Réponse: [
#     {
#       "id": 1,
#       "nom": "Dupont",
#       "prenom": "Jean",
#       "email": "jean.dupont@example.com",
#       "dateNaissance": "1990-05-15",
#       "telephone": "0123456789",
#       "nombreAliments": 5
#     }
#   ]
#
# - GET http://localhost:8080/api/personnes/{id}
#   Description: Obtient une personne par son identifiant unique
#   Réponse: {
#     "id": 1,
#     "nom": "Dupont",
#     "prenom": "Jean",
#     "email": "jean.dupont@example.com",
#     "dateNaissance": "1990-05-15",
#     "telephone": "0123456789"
#   }
#
# - POST http://localhost:8080/api/personnes
#   Description: Crée une nouvelle personne dans le système
#   Body: {"nom":"Martin","prenom":"Marie","email":"marie@example.com","telephone":"0987654321"}
#   Réponse: Personne créée avec ID généré
#
# - PUT http://localhost:8080/api/personnes/{id}
#   Description: Met à jour les informations d'une personne existante
#   Body: Données modifiées de la personne
#   Réponse: Personne mise à jour
#
# - DELETE http://localhost:8080/api/personnes/{id}
#   Description: Supprime définitivement une personne
#   Réponse: 200 OK si suppression réussie
#
# 🍎 APIs ALIMENTS:
#
# - GET http://localhost:8080/api/foods
#   Description: Récupère tous les aliments avec informations nutritionnelles
#   Réponse: [
#     {
#       "id": 1,
#       "nom": "Salade de tomates",
#       "description": "Salade fraîche de tomates cerises",
#       "categorie": "LEGUMES",
#       "calories": 25.0,
#       "dateCreation": "2024-01-15T10:30:00",
#       "personneId": 1,
#       "nombreIngredients": 3
#     }
#   ]
#
# - GET http://localhost:8080/api/foods/{id}
#   Description: Obtient un aliment spécifique par son ID
#   Réponse: Détails complets de l'aliment
#
# - GET http://localhost:8080/api/foods/recherche?nom={nom}
#   Description: Recherche des aliments par nom (recherche partielle)
#   Réponse: Liste des aliments correspondants
#
# - GET http://localhost:8080/api/foods/categorie/{categorie}
#   Description: Filtre les aliments par catégorie
#   Catégories: LEGUMES, FRUITS, PROTEINES, CEREALES, PRODUITS_LAITIERS, DESSERTS
#   Réponse: Liste des aliments de la catégorie
#
# - GET http://localhost:8080/api/foods/createur/{personneId}
#   Description: Obtient tous les aliments créés par une personne
#   Réponse: Liste des aliments du créateur
#
# - GET http://localhost:8080/api/foods/calories?min={min}&max={max}
#   Description: Recherche des aliments dans une tranche de calories
#   Réponse: Aliments avec calories entre min et max
#
# 🥕 APIs INGRÉDIENTS:
#
# - GET http://localhost:8080/api/ingredients
#   Description: Liste complète de tous les ingrédients disponibles
#   Réponse: [
#     {
#       "id": 1,
#       "nom": "Tomate",
#       "description": "Tomate rouge fraîche",
#       "type": "FRAIS"
#     }
#   ]
#
# - GET http://localhost:8080/api/ingredients/{id}
#   Description: Détails d'un ingrédient spécifique
#   Réponse: Informations complètes de l'ingrédient
#
# - POST http://localhost:8080/api/ingredients
#   Description: Crée un nouvel ingrédient
#   Body: {"nom":"Basilic","description":"Herbe aromatique","type":"FRAIS"}
#   Réponse: Ingrédient créé avec ID
#
# - PUT http://localhost:8080/api/ingredients/{id}
#   Description: Modifie un ingrédient existant
#   Réponse: Ingrédient mis à jour
#
# - DELETE http://localhost:8080/api/ingredients/{id}
#   Description: Supprime un ingrédient
#   Réponse: 200 OK si suppression réussie
#
# - GET http://localhost:8080/api/ingredients/types
#   Description: Liste tous les types d'ingrédients disponibles
#   Réponse: ["FRAIS", "SURGELE", "CONSERVE", "SEC", "AUTRE"]
#
# - GET http://localhost:8080/api/ingredients/recherche?nom={nom}
#   Description: Recherche d'ingrédients par nom
#   Réponse: Liste des ingrédients correspondants
#
# - GET http://localhost:8080/api/ingredients/type/{type}
#   Description: Filtre les ingrédients par type
#   Réponse: Ingrédients du type spécifié
#
# 🖼️ APIs IMAGES:
#
# - GET http://localhost:8080/api/images
#   Description: Liste de toutes les images uploadées
#   Réponse: [
#     {
#       "id": 1,
#       "nomFichier": "salade.jpg",
#       "chemin": "/uploads/foods/salade.jpg",
#       "typeMime": "image/jpeg",
#       "taille": 245760,
#       "dateUpload": "2024-01-15T14:20:00"
#     }
#   ]
#
# - GET http://localhost:8080/api/images/{id}
#   Description: Détails d'une image spécifique
#   Réponse: Métadonnées complètes de l'image
#
# - GET http://localhost:8080/api/images/food/{foodId}
#   Description: Toutes les images associées à un aliment
#   Réponse: Liste des images de l'aliment
#
# 📅 APIs PLANIFICATION:
#
# - POST http://localhost:8080/api/planification/plan-hebdomadaire?personneId={id}&dateDebut={date}
#   Description: Crée un plan de repas complet pour 7 jours
#   Réponse: {
#     "personne": {"id": 1, "nom": "Dupont"},
#     "dateDebut": "2024-01-15",
#     "dateFin": "2024-01-21",
#     "planning": {
#       "MONDAY": {
#         "PETIT_DEJEUNER": [{"nom": "Céréales", "calories": 150}],
#         "DEJEUNER": [{"nom": "Salade", "calories": 80}],
#         "DINER": [{"nom": "Poisson", "calories": 200}]
#       }
#     },
#     "caloriesTotal": 2100
#   }
#
# - GET http://localhost:8080/api/planification/recommandations?personneId={id}&typeRepas={type}
#   Description: Obtient des recommandations alimentaires par type de repas
#   Types: PETIT_DEJEUNER, DEJEUNER, DINER
#   Réponse: Liste d'aliments recommandés pour le type de repas
#
# - POST http://localhost:8080/api/planification/analyser
#   Description: Analyse nutritionnelle complète d'un plan de repas
#   Body: Plan de repas complet
#   Réponse: {
#     "repartitionCategories": {"LEGUMES": 8, "FRUITS": 5, "PROTEINES": 6},
#     "nombreTotalAliments": 31,
#     "caloriesMoyennesParJour": 300.0
#   }
#
# 🍽️ APIs BUFFET:
#
# - POST http://localhost:8080/api/buffet/creer?nombreInvites={nb}&typeCeremonie={type}&budgetEstime={budget}&organisateurId={id}
#   Description: Crée un buffet personnalisé selon l'événement
#   Types: mariage, anniversaire, bapteme, conference
#   Réponse: {
#     "organisateur": {"id": 1, "nom": "Dupont"},
#     "nombreInvites": 50,
#     "typeCeremonie": "mariage",
#     "menuBuffet": {
#       "ENTREES": [{"nom": "Salade mixte"}],
#       "PLATS_PRINCIPAUX": [{"nom": "Poulet rôti"}],
#       "DESSERTS": [{"nom": "Tarte aux fruits"}]
#     },
#     "quantitesEstimees": {"entreesKg": 12, "platsPrincipauxKg": 23},
#     "coutEstime": 1875.50
#   }
#
# - GET http://localhost:8080/api/buffet/modeles
#   Description: Modèles prédéfinis de buffets selon le type d'événement
#   Réponse: [
#     {
#       "nom": "Buffet Mariage",
#       "description": "Menu complet pour mariage",
#       "invitesMin": 50,
#       "invitesMax": 200
#     }
#   ]
#
# - POST http://localhost:8080/api/buffet/optimiser?budgetMax={budget}
#   Description: Optimise un buffet pour respecter le budget maximum
#   Body: Buffet à optimiser
#   Réponse: Buffet optimisé avec coût réduit
#
# 🤖 APIs CHATBOT:
#
# - POST http://localhost:8080/api/chatbot/message
#   Description: Envoie un message au chatbot intelligent
#   Body: {"message": "Bonjour", "userId": 1}
#   Réponse: {
#     "message": "Bonjour ! Comment puis-je vous aider ?",
#     "intention": "SALUTATION",
#     "timestamp": "2024-01-15T17:45:00"
#   }
#
# - GET http://localhost:8080/api/chatbot/suggestions/{domaine}
#   Description: Obtient des suggestions par domaine
#   Domaines: aliments, ingredients, general
#   Réponse: {"suggestions": ["Quel est votre aliment préféré ?"]}
#
# - POST http://localhost:8080/api/chatbot/conseil-nutrition
#   Description: Conseils nutritionnels personnalisés
#   Body: {"objectif": "perte_poids", "restrictions": ["sans_gluten"]}
#   Réponse: {
#     "conseils": ["Incluez plus de légumes", "Réduisez les sucres"],
#     "type": "nutrition"
#   }
#
# 📊 EXEMPLES D'UTILISATION:
# 
# Test de connectivité:
# curl http://localhost:8080/api/test
#
# Créer une personne:
# curl -X POST http://localhost:8080/api/personnes \
#   -H "Content-Type: application/json" \
#   -d '{"nom":"Martin","prenom":"Alice","email":"alice@example.com","telephone":"0123456789"}'
#
# Obtenir toutes les personnes:
# curl http://localhost:8080/api/personnes
#
# Rechercher un aliment:
# curl http://localhost:8080/api/foods/recherche?nom=salade
#
# Créer un plan hebdomadaire:
# curl -X POST "http://localhost:8080/api/planification/plan-hebdomadaire?personneId=1&dateDebut=2024-01-15"
#
# Tester le chatbot:
# curl -X POST http://localhost:8080/api/chatbot/message \
#   -H "Content-Type: application/json" \
#   -d '{"message":"Bonjour","userId":1}'
#
# Créer un buffet:
# curl -X POST "http://localhost:8080/api/buffet/creer?nombreInvites=50&typeCeremonie=mariage&budgetEstime=2000&organisateurId=1"
#
# ========================================
# 🐳 COMMANDES DOCKER UTILES:
# ========================================
#
# Démarrer les services:
# docker-compose up -d
#
# Arrêter les services:
# docker-compose down
#
# Voir les logs:
# docker-compose logs -f app
#
# Redémarrer avec reconstruction:
# docker-compose up --build -d
#
# Accéder à la base de données:
# docker exec -it food_postgres psql -U postgres -d food_management_db
#
# Vérifier l'état des conteneurs:
# docker-compose ps
#
# ========================================