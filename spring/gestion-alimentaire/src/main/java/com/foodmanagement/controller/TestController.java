package com.foodmanagement.controller;

import com.foodmanagement.entity.Food;
import com.foodmanagement.entity.Ingredient;
import com.foodmanagement.entity.Personne;
import com.foodmanagement.enums.CategorieFood;
import com.foodmanagement.enums.TypeIngredient;
import com.foodmanagement.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Contrôleur de test pour navigateur
 * Permet de tester facilement les APIs depuis un navigateur web
 */
@RestController
@RequestMapping("/api")
public class TestController {

    @Autowired(required = false)
    private PersonneService personneService;
    
    @Autowired(required = false)
    private FoodService foodService;
    
    @Autowired(required = false)
    private IngredientService ingredientService;

    /**
     * Page d'accueil avec documentation des APIs
     * GET /api/documentation
     */
    @GetMapping(value = "/documentation", produces = MediaType.TEXT_HTML_VALUE)
    public String afficherDocumentation() {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <title>🍽️ Food Management API - Documentation</title>
                <meta charset="UTF-8">
                <style>
                    body { font-family: Arial, sans-serif; margin: 20px; background-color: #f5f5f5; }
                    .container { max-width: 1200px; margin: 0 auto; background: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
                    h1 { color: #2c3e50; border-bottom: 3px solid #3498db; padding-bottom: 10px; }
                    h2 { color: #34495e; margin-top: 30px; }
                    .endpoint { background: #ecf0f1; padding: 15px; margin: 10px 0; border-radius: 5px; border-left: 4px solid #3498db; }
                    .method { font-weight: bold; color: white; padding: 3px 8px; border-radius: 3px; font-size: 12px; }
                    .get { background-color: #27ae60; }
                    .post { background-color: #e74c3c; }
                    .put { background-color: #f39c12; }
                    .delete { background-color: #e67e22; }
                    .url { font-family: monospace; background: #34495e; color: white; padding: 5px 10px; border-radius: 3px; margin: 5px 0; display: inline-block; }
                    .description { color: #7f8c8d; margin-top: 5px; }
                    .test-link { background-color: #3498db; color: white; padding: 8px 15px; text-decoration: none; border-radius: 5px; margin: 5px; display: inline-block; }
                    .test-link:hover { background-color: #2980b9; }
                    .status { text-align: center; padding: 20px; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; border-radius: 8px; margin-bottom: 20px; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="status">
                        <h1>🍽️ Food Management System</h1>
                        <p>API de gestion alimentaire - Documentation complète</p>
                        <p>Statut : <strong>✅ Opérationnel</strong> | Version : 1.0.0 | Port : 8082</p>
                    </div>

                    <div style="text-align: center; margin: 20px 0;">
                        <a href="/api/test" class="test-link">🧪 Page de Tests</a>
                        <a href="/api/status" class="test-link">📊 Statut du Système</a>
                        <a href="/api/demo-data" class="test-link">🎲 Données de Démonstration</a>
                    </div>

                    <h2>👥 Gestion des Personnes (/api/personnes)</h2>
                    <div class="endpoint">
                        <span class="method get">GET</span> <span class="url">/api/personnes</span>
                        <div class="description">Lister toutes les personnes</div>
                    </div>
                    <div class="endpoint">
                        <span class="method get">GET</span> <span class="url">/api/personnes/{id}</span>
                        <div class="description">Obtenir une personne par ID</div>
                    </div>
                    <div class="endpoint">
                        <span class="method get">GET</span> <span class="url">/api/personnes/recherche?nom={nom}</span>
                        <div class="description">Rechercher des personnes par nom</div>
                    </div>
                    <div class="endpoint">
                        <span class="method post">POST</span> <span class="url">/api/personnes</span>
                        <div class="description">Créer une nouvelle personne</div>
                    </div>

                    <h2>🍎 Gestion des Aliments (/api/foods)</h2>
                    <div class="endpoint">
                        <span class="method get">GET</span> <span class="url">/api/foods</span>
                        <div class="description">Lister tous les aliments</div>
                    </div>
                    <div class="endpoint">
                        <span class="method get">GET</span> <span class="url">/api/foods/categorie/{categorie}</span>
                        <div class="description">Aliments par catégorie (LEGUMES, FRUITS, VIANDES, CEREALES, DESSERTS)</div>
                    </div>
                    <div class="endpoint">
                        <span class="method get">GET</span> <span class="url">/api/foods/populaires</span>
                        <div class="description">Aliments les plus populaires</div>
                    </div>

                    <h2>🥕 Gestion des Ingrédients (/api/ingredients)</h2>
                    <div class="endpoint">
                        <span class="method get">GET</span> <span class="url">/api/ingredients</span>
                        <div class="description">Lister tous les ingrédients</div>
                    </div>
                    <div class="endpoint">
                        <span class="method get">GET</span> <span class="url">/api/ingredients/type/{type}</span>
                        <div class="description">Ingrédients par type (FRAIS, SURGELE, CONSERVE, SEC)</div>
                    </div>

                    <h2>🖼️ Gestion des Images (/api/images)</h2>
                    <div class="endpoint">
                        <span class="method get">GET</span> <span class="url">/api/images</span>
                        <div class="description">Lister toutes les images</div>
                    </div>
                    <div class="endpoint">
                        <span class="method get">GET</span> <span class="url">/api/images/statistiques</span>
                        <div class="description">Statistiques des images</div>
                    </div>

                    <h2>🍽️ Gestion des Buffets (/api/buffets)</h2>
                    <div class="endpoint">
                        <span class="method get">GET</span> <span class="url">/api/buffets/suggestions?typeEvenement=MARIAGE&nombreInvites=50</span>
                        <div class="description">Suggestions d'aliments pour événements</div>
                    </div>
                    <div class="endpoint">
                        <span class="method get">GET</span> <span class="url">/api/buffets/types-evenements</span>
                        <div class="description">Types d'événements supportés</div>
                    </div>

                    <h2>📅 Planification des Repas (/api/planification)</h2>
                    <div class="endpoint">
                        <span class="method get">GET</span> <span class="url">/api/planification/types-repas</span>
                        <div class="description">Types de repas disponibles</div>
                    </div>
                    <div class="endpoint">
                        <span class="method get">GET</span> <span class="url">/api/planification/modele-plan?dateDebut=2025-01-01</span>
                        <div class="description">Modèle de plan de repas</div>
                    </div>

                    <h2>🔗 Liens Rapides pour Tests</h2>
                    <div style="background: #f8f9fa; padding: 20px; border-radius: 5px; margin: 20px 0;">
                        <a href="/api/personnes" class="test-link">👥 Toutes les Personnes</a>
                        <a href="/api/foods" class="test-link">🍎 Tous les Aliments</a>
                        <a href="/api/ingredients" class="test-link">🥕 Tous les Ingrédients</a>
                        <a href="/api/foods/categorie/LEGUMES" class="test-link">🥬 Légumes</a>
                        <a href="/api/foods/categorie/FRUITS" class="test-link">🍎 Fruits</a>
                        <a href="/api/foods/categorie/VIANDES" class="test-link">🥩 Viandes</a>
                        <a href="/api/foods/categorie/CEREALES" class="test-link">🌾 Céréales</a>
                        <a href="/api/foods/categorie/DESSERTS" class="test-link">🍰 Desserts</a>
                        <a href="/api/ingredients/type/FRAIS" class="test-link">🆕 Ingrédients Frais</a>
                        <a href="/api/buffets/types-evenements" class="test-link">🎉 Types d'Événements</a>
                    </div>

                    <div style="text-align: center; margin-top: 40px; padding: 20px; background: #ecf0f1; border-radius: 5px;">
                        <p><strong>💡 Conseil :</strong> Utilisez un outil comme Postman ou curl pour tester les endpoints POST/PUT/DELETE</p>
                        <p>Pour les tests dans le navigateur, consultez la <a href="/api/test">page de tests</a></p>
                    </div>
                </div>
            </body>
            </html>
            """;
    }

    /**
     * Page de tests interactifs
     * GET /api/test
     */
    @GetMapping(value = "/test", produces = MediaType.TEXT_HTML_VALUE)
    public String afficherPageTest() {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <title>🧪 Tests API - Food Management</title>
                <meta charset="UTF-8">
                <style>
                    body { font-family: Arial, sans-serif; margin: 20px; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; }
                    .container { max-width: 1000px; margin: 0 auto; background: white; padding: 30px; border-radius: 15px; box-shadow: 0 10px 30px rgba(0,0,0,0.2); }
                    h1 { color: #2c3e50; text-align: center; margin-bottom: 30px; }
                    .test-section { background: #f8f9fa; padding: 20px; margin: 20px 0; border-radius: 10px; border-left: 5px solid #3498db; }
                    .btn { background: #3498db; color: white; padding: 10px 20px; border: none; border-radius: 5px; cursor: pointer; margin: 5px; text-decoration: none; display: inline-block; }
                    .btn:hover { background: #2980b9; }
                    .btn-success { background: #27ae60; }
                    .btn-warning { background: #f39c12; }
                    .btn-danger { background: #e74c3c; }
                    .result { background: #34495e; color: white; padding: 15px; border-radius: 5px; margin: 10px 0; white-space: pre-wrap; font-family: monospace; max-height: 300px; overflow-y: auto; }
                    input, select { padding: 8px; margin: 5px; border: 1px solid #ddd; border-radius: 4px; }
                </style>
                <script>
                    async function testAPI(url, elementId) {
                        try {
                            const response = await fetch(url);
                            const data = await response.json();
                            document.getElementById(elementId).textContent = JSON.stringify(data, null, 2);
                        } catch (error) {
                            document.getElementById(elementId).textContent = 'Erreur: ' + error.message;
                        }
                    }
                    
                    function searchPersonnes() {
                        const nom = document.getElementById('nomPersonne').value;
                        if (nom) {
                            testAPI('/api/personnes/recherche?nom=' + encodeURIComponent(nom), 'resultPersonnes');
                        }
                    }
                    
                    function searchAliments() {
                        const categorie = document.getElementById('categorieAliment').value;
                        testAPI('/api/foods/categorie/' + categorie, 'resultAliments');
                    }
                </script>
            </head>
            <body>
                <div class="container">
                    <h1>🧪 Page de Tests API</h1>
                    
                    <div class="test-section">
                        <h3>🔄 Tests Rapides</h3>
                        <button class="btn" onclick="testAPI('/api/status', 'resultStatus')">📊 Status Système</button>
                        <button class="btn btn-success" onclick="testAPI('/api/personnes', 'resultPersonnes')">👥 Toutes les Personnes</button>
                        <button class="btn btn-success" onclick="testAPI('/api/foods', 'resultAliments')">🍎 Tous les Aliments</button>
                        <button class="btn btn-warning" onclick="testAPI('/api/ingredients', 'resultIngredients')">🥕 Tous les Ingrédients</button>
                        <div id="resultStatus" class="result"></div>
                    </div>

                    <div class="test-section">
                        <h3>🔍 Recherche de Personnes</h3>
                        <input type="text" id="nomPersonne" placeholder="Nom à rechercher...">
                        <button class="btn" onclick="searchPersonnes()">Rechercher</button>
                        <div id="resultPersonnes" class="result"></div>
                    </div>

                    <div class="test-section">
                        <h3>🍎 Aliments par Catégorie</h3>
                        <select id="categorieAliment">
                            <option value="LEGUMES">🥬 Légumes</option>
                            <option value="FRUITS">🍎 Fruits</option>
                            <option value="VIANDES">🥩 Viandes</option>
                            <option value="CEREALES">🌾 Céréales</option>
                            <option value="DESSERTS">🍰 Desserts</option>
                        </select>
                        <button class="btn" onclick="searchAliments()">Afficher</button>
                        <div id="resultAliments" class="result"></div>
                    </div>

                    <div class="test-section">
                        <h3>📊 Tests Statistiques</h3>
                        <button class="btn btn-warning" onclick="testAPI('/api/personnes/statistiques', 'resultStats')">📈 Stats Personnes</button>
                        <button class="btn btn-warning" onclick="testAPI('/api/foods/statistiques', 'resultStats')">📈 Stats Aliments</button>
                        <button class="btn btn-warning" onclick="testAPI('/api/ingredients/statistiques', 'resultStats')">📈 Stats Ingrédients</button>
                        <div id="resultStats" class="result"></div>
                    </div>

                    <div class="test-section">
                        <h3>🎉 Tests Buffets</h3>
                        <button class="btn btn-danger" onclick="testAPI('/api/buffets/types-evenements', 'resultBuffets')">🎊 Types d'Événements</button>
                        <button class="btn btn-danger" onclick="testAPI('/api/buffets/suggestions?typeEvenement=MARIAGE&nombreInvites=50', 'resultBuffets')">💒 Suggestions Mariage</button>
                        <div id="resultBuffets" class="result"></div>
                    </div>

                    <div class="test-section">
                        <h3>🔗 Liens Rapides</h3>
                        <a href="/api/documentation" class="btn">📋 Documentation Complète</a>
                        <a href="/api/demo-data" class="btn btn-success">🎲 Créer Données Demo</a>
                        <div id="resultIngredients" class="result"></div>
                    </div>
                </div>
            </body>
            </html>
            """;
    }

    /**
     * Statut du système
     * GET /api/status
     */
    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> obtenirStatus() {
        return ResponseEntity.ok(Map.of(
            "statut", "OPERATIONNEL",
            "application", "Food Management System",
            "version", "1.0.0",
            "timestamp", LocalDateTime.now(),
            "services", Map.of(
                "personnes", personneService != null ? "✅ Actif" : "❌ Inactif",
                "aliments", foodService != null ? "✅ Actif" : "❌ Inactif",
                "ingredients", ingredientService != null ? "✅ Actif" : "❌ Inactif"
            ),
            "endpoints_disponibles", Map.of(
                "documentation", "/api/documentation",
                "tests", "/api/test",
                "personnes", "/api/personnes",
                "aliments", "/api/foods",
                "ingredients", "/api/ingredients",
                "images", "/api/images",
                "buffets", "/api/buffets",
                "planification", "/api/planification"
            )
        ));
    }

    /**
     * Créer des données de démonstration
     * GET /api/demo-data
     */
    @GetMapping("/demo-data")
    public ResponseEntity<Map<String, Object>> creerDonneesDemo() {
        try {
            int personnesCreees = 0;
            int alimentsCreees = 0;
            int ingredientsCreees = 0;

            // Création de personnes de demo si le service existe
            Personne personneDemo = null;
            if (personneService != null) {
                try {
                    // Création personne 1
                    Personne demo1 = new Personne();
                    demo1.setNom("Martin");
                    
                    demo1.setEmail("jean.martin@example.com");
                    demo1.setMotDePasse("password123");
                    personneDemo = personneService.creerPersonne(demo1);
                    personnesCreees++;

                    // Création personne 2
                    Personne demo2 = new Personne();
                    demo2.setNom("Dupont");
                    
                    demo2.setEmail("marie.dupont@example.com");
                    demo2.setMotDePasse("password123");
                    personneService.creerPersonne(demo2);
                    personnesCreees++;
                } catch (Exception e) {
                    // Essayer de récupérer une personne existante
                    try {
                        var personnes = personneService.listerToutesLesPersonnes();
                        if (!personnes.isEmpty()) {
                            personneDemo = personnes.get(0);
                        }
                    } catch (Exception ex) {
                        // Ignore si pas de personnes
                    }
                }
            }

            // Création d'ingrédients de demo si le service existe
            if (ingredientService != null) {
                try {
                    Ingredient tomate = new Ingredient();
                    tomate.setNom("Tomate");
                    tomate.setDescription("Tomate rouge fraîche");
                    tomate.setType(TypeIngredient.FRAIS);
                    tomate.setUnite("kg");
                    tomate.setQuantite(1.0);
                    ingredientService.creerIngredient(tomate);
                    ingredientsCreees++;

                    Ingredient riz = new Ingredient();
                    riz.setNom("Riz");
                    riz.setDescription("Riz blanc long grain");
                    riz.setType(TypeIngredient.SEC);
                    riz.setUnite("kg");
                    riz.setQuantite(1.0);
                    ingredientService.creerIngredient(riz);
                    ingredientsCreees++;

                    Ingredient lait = new Ingredient();
                    lait.setNom("Lait");
                    lait.setDescription("Lait entier frais");
                    lait.setType(TypeIngredient.FRAIS);
                    lait.setUnite("L");
                    lait.setQuantite(1.0);
                    ingredientService.creerIngredient(lait);
                    ingredientsCreees++;
                } catch (Exception e) {
                    // Ingrédients peut-être déjà créés
                }
            }

            // Création d'aliments de demo si le service existe
            if (foodService != null && personneDemo != null) {
                try {
                    Food salade = new Food();
                    salade.setNom("Salade de Tomates");
                    salade.setDescription("Salade fraîche de tomates avec vinaigrette");
                    salade.setCategorie(CategorieFood.LEGUMES);
                    salade.setTempsPreparation("15 minutes");
                    salade.setCalories(25.0);
                    salade.setPrix(5.50);
                    salade.setPersonne(personneDemo);
                    foodService.creerAliment(salade);
                    alimentsCreees++;

                    Food risotto = new Food();
                    risotto.setNom("Risotto aux Champignons");
                    risotto.setDescription("Risotto crémeux aux champignons de saison");
                    risotto.setCategorie(CategorieFood.CEREALES);
                    risotto.setTempsPreparation("35 minutes");
                    risotto.setCalories(180.0);
                    risotto.setPrix(8.90);
                    risotto.setPersonne(personneDemo);
                    foodService.creerAliment(risotto);
                    alimentsCreees++;

                    Food steakFrites = new Food();
                    steakFrites.setNom("Steak de Bœuf");
                    steakFrites.setDescription("Steak de bœuf grillé, tendre et savoureux");
                    steakFrites.setCategorie(CategorieFood.VIANDES);
                    steakFrites.setTempsPreparation("20 minutes");
                    steakFrites.setCalories(250.0);
                    steakFrites.setPrix(12.50);
                    steakFrites.setPersonne(personneDemo);
                    foodService.creerAliment(steakFrites);
                    alimentsCreees++;

                    Food tartePommes = new Food();
                    tartePommes.setNom("Tarte aux Pommes");
                    tartePommes.setDescription("Tarte aux pommes maison avec pâte brisée");
                    tartePommes.setCategorie(CategorieFood.DESSERTS);
                    tartePommes.setTempsPreparation("45 minutes");
                    tartePommes.setCalories(320.0);
                    tartePommes.setPrix(6.50);
                    tartePommes.setPersonne(personneDemo);
                    foodService.creerAliment(tartePommes);
                    alimentsCreees++;

                    Food saladeFruits = new Food();
                    saladeFruits.setNom("Salade de Fruits");
                    saladeFruits.setDescription("Mélange de fruits frais de saison");
                    saladeFruits.setCategorie(CategorieFood.FRUITS);
                    saladeFruits.setTempsPreparation("10 minutes");
                    saladeFruits.setCalories(65.0);
                    saladeFruits.setPrix(4.50);
                    saladeFruits.setPersonne(personneDemo);
                    foodService.creerAliment(saladeFruits);
                    alimentsCreees++;
                } catch (Exception e) {
                    // Aliments peut-être déjà créés
                }
            }

            return ResponseEntity.ok(Map.of(
                "statut", "DONNEES_DEMO_CREEES",
                "message", "Données de démonstration créées avec succès",
                "details", Map.of(
                    "personnes_creees", personnesCreees,
                    "ingredients_crees", ingredientsCreees,
                    "aliments_crees", alimentsCreees
                ),
                "liens_test", Arrays.asList(
                    "/api/personnes",
                    "/api/foods",
                    "/api/ingredients"
                )
            ));
        } catch (Exception e) {
            return ResponseEntity.ok(Map.of(
                "statut", "PARTIELLEMENT_CREE",
                "message", "Certaines données de demo étaient peut-être déjà présentes",
                "erreur", e.getMessage()
            ));
        }
    }

    /**
     * Liste des catégories disponibles
     * GET /api/categories
     */
    @GetMapping("/categories")
    public ResponseEntity<Map<String, Object>> obtenirCategories() {
        return ResponseEntity.ok(Map.of(
            "categories_aliments", Arrays.asList(
                "LEGUMES", "FRUITS", "VIANDES", "CEREALES", "DESSERTS"
            ),
            "types_ingredients", Arrays.asList(
                "FRAIS", "SURGELE", "CONSERVE", "SEC"
            ),
            "types_evenements", Arrays.asList(
                "MARIAGE", "ANNIVERSAIRE", "ENTREPRISE", "COCKTAIL"
            )
        ));
    }

    /**
     * Page d'accueil redirigeant vers la documentation
     * GET /
     */
    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public String pageAccueil() {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <title>🍽️ Food Management System</title>
                <meta charset="UTF-8">
                <meta http-equiv="refresh" content="2;url=/api/documentation">
                <style>
                    body { 
                        font-family: Arial, sans-serif; 
                        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); 
                        height: 100vh; 
                        margin: 0; 
                        display: flex; 
                        justify-content: center; 
                        align-items: center; 
                        color: white; 
                        text-align: center; 
                    }
                    .welcome { 
                        background: rgba(255,255,255,0.1); 
                        padding: 40px; 
                        border-radius: 20px; 
                        backdrop-filter: blur(10px); 
                    }
                    h1 { font-size: 3em; margin-bottom: 20px; }
                    p { font-size: 1.2em; }
                    .loading { animation: pulse 2s infinite; }
                    @keyframes pulse { 0%, 100% { opacity: 1; } 50% { opacity: 0.5; } }
                </style>
            </head>
            <body>
                <div class="welcome">
                    <h1>🍽️ Food Management System</h1>
                    <p class="loading">Redirection vers la documentation...</p>
                    <p>Si la redirection ne fonctionne pas, <a href="/api/documentation" style="color: #FFD700;">cliquez ici</a></p>
                </div>
            </body>
            </html>
            """;
    }
}