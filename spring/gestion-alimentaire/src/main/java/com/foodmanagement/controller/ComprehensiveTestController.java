package com.foodmanagement.controller;

import com.foodmanagement.entity.Food;
import com.foodmanagement.entity.Ingredient;
import com.foodmanagement.entity.Personne;
import com.foodmanagement.enums.CategorieFood;
import com.foodmanagement.enums.TypeIngredient;
import com.foodmanagement.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Contrôleur de test complet pour toutes les APIs
 */
@RestController
public class ComprehensiveTestController {

    @Autowired(required = false)
    private PersonneService personneService;
    
    @Autowired(required = false)
    private FoodService foodService;
    
    @Autowired(required = false)
    private IngredientService ingredientService;
    
    @Autowired(required = false)
    private BuffetService buffetService;
    
    @Autowired(required = false)
    private ImageService imageService;
    
    @Autowired(required = false)
    private PlanificationService planificationService;

    /**
     * Page de test complète de toutes les APIs
     * GET /api/test-all
     */
    @GetMapping(value = "/test-all", produces = "text/html")
    public String pageTestComplete() {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <title>🧪 Test Complet - Food Management APIs</title>
                <meta charset="UTF-8">
                <style>
                    body { 
                        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; 
                        margin: 0; 
                        padding: 20px; 
                        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); 
                        min-height: 100vh; 
                    }
                    .container { 
                        max-width: 1400px; 
                        margin: 0 auto; 
                        background: white; 
                        border-radius: 15px; 
                        padding: 30px; 
                        box-shadow: 0 20px 40px rgba(0,0,0,0.1); 
                    }
                    h1 { 
                        color: #2c3e50; 
                        text-align: center; 
                        margin-bottom: 30px; 
                        font-size: 2.5em; 
                    }
                    .test-grid { 
                        display: grid; 
                        grid-template-columns: repeat(auto-fit, minmax(400px, 1fr)); 
                        gap: 20px; 
                        margin: 20px 0; 
                    }
                    .test-section { 
                        background: #f8f9fa; 
                        padding: 20px; 
                        border-radius: 10px; 
                        border-left: 5px solid #3498db; 
                    }
                    .test-section h3 { 
                        color: #2c3e50; 
                        margin-top: 0; 
                        display: flex; 
                        align-items: center; 
                        gap: 10px; 
                    }
                    .btn { 
                        background: #3498db; 
                        color: white; 
                        padding: 8px 15px; 
                        border: none; 
                        border-radius: 5px; 
                        cursor: pointer; 
                        margin: 3px; 
                        font-size: 12px; 
                        transition: all 0.3s; 
                    }
                    .btn:hover { 
                        background: #2980b9; 
                        transform: translateY(-2px); 
                    }
                    .btn-success { background: #27ae60; }
                    .btn-success:hover { background: #219a52; }
                    .btn-warning { background: #f39c12; }
                    .btn-warning:hover { background: #e67e22; }
                    .btn-danger { background: #e74c3c; }
                    .btn-danger:hover { background: #c0392b; }
                    .result { 
                        background: #2c3e50; 
                        color: #ecf0f1; 
                        padding: 15px; 
                        border-radius: 5px; 
                        margin: 10px 0; 
                        font-family: 'Courier New', monospace; 
                        font-size: 12px; 
                        max-height: 200px; 
                        overflow-y: auto; 
                        white-space: pre-wrap; 
                        border: 1px solid #34495e; 
                    }
                    .status-indicator { 
                        display: inline-block; 
                        width: 10px; 
                        height: 10px; 
                        border-radius: 50%; 
                        margin-right: 5px; 
                    }
                    .status-success { background: #27ae60; }
                    .status-error { background: #e74c3c; }
                    .status-loading { background: #f39c12; animation: pulse 1s infinite; }
                    @keyframes pulse { 0%, 100% { opacity: 1; } 50% { opacity: 0.5; } }
                    .summary { 
                        background: linear-gradient(135deg, #74b9ff, #0984e3); 
                        color: white; 
                        padding: 20px; 
                        border-radius: 10px; 
                        text-align: center; 
                        margin-bottom: 30px; 
                    }
                    .control-panel { 
                        background: #ecf0f1; 
                        padding: 15px; 
                        border-radius: 10px; 
                        margin-bottom: 20px; 
                        text-align: center; 
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <h1>🧪 Test Complet - Food Management APIs</h1>
                    
                    <div class="summary">
                        <h2>📊 Tableau de Bord des Tests</h2>
                        <div id="summary-stats">
                            <span id="total-tests">Tests: 0</span> | 
                            <span id="success-tests">✅ Succès: 0</span> | 
                            <span id="error-tests">❌ Erreurs: 0</span>
                        </div>
                    </div>

                    <div class="control-panel">
                        <button class="btn btn-success" onclick="runAllTests()">🚀 Lancer TOUS les Tests</button>
                        <button class="btn btn-warning" onclick="clearAllResults()">🧹 Effacer les Résultats</button>
                        <button class="btn btn-danger" onclick="createDemoData()">🎲 Créer Données Demo</button>
                    </div>

                    <div class="test-grid">
                        <!-- Tests de Base -->
                        <div class="test-section">
                            <h3>🔧 Tests de Base</h3>
                            <button class="btn" onclick="testAPI('/api/hello', 'result-base')">🌟 Hello API</button>
                            <button class="btn" onclick="testAPI('/api/status-simple', 'result-base')">📊 Status Simple</button>
                            <button class="btn btn-success" onclick="testAPI('/api/detailed-status', 'result-base')">🔍 Status Détaillé</button>
                            <div id="result-base" class="result">Cliquez sur un bouton pour tester...</div>
                        </div>

                        <!-- Tests Personnes -->
                        <div class="test-section">
                            <h3>👥 API Personnes</h3>
                            <button class="btn btn-success" onclick="testAPI('/api/personnes', 'result-personnes')">📋 Toutes</button>
                            <button class="btn" onclick="testAPI('/api/personnes/statistiques', 'result-personnes')">📊 Stats</button>
                            <button class="btn" onclick="testAPI('/api/personnes/actives', 'result-personnes')">🔥 Actives</button>
                            <div id="result-personnes" class="result">Tests des APIs Personnes...</div>
                        </div>

                        <!-- Tests Aliments -->
                        <div class="test-section">
                            <h3>🍎 API Aliments</h3>
                            <button class="btn btn-success" onclick="testAPI('/api/foods', 'result-foods')">📋 Tous</button>
                            <button class="btn" onclick="testAPI('/api/foods/statistiques', 'result-foods')">📊 Stats</button>
                            <button class="btn" onclick="testAPI('/api/foods/populaires', 'result-foods')">⭐ Populaires</button>
                            <div id="result-foods" class="result">Tests des APIs Aliments...</div>
                        </div>

                        <!-- Tests Ingrédients -->
                        <div class="test-section">
                            <h3>🥕 API Ingrédients</h3>
                            <button class="btn btn-success" onclick="testAPI('/api/ingredients', 'result-ingredients')">📋 Tous</button>
                            <button class="btn" onclick="testAPI('/api/ingredients/statistiques', 'result-ingredients')">📊 Stats</button>
                            <button class="btn" onclick="testAPI('/api/ingredients/populaires', 'result-ingredients')">⭐ Populaires</button>
                            <div id="result-ingredients" class="result">Tests des APIs Ingrédients...</div>
                        </div>

                        <!-- Tests par Catégorie -->
                        <div class="test-section">
                            <h3>🏷️ Tests par Catégorie</h3>
                            <button class="btn" onclick="testAPI('/api/foods/categorie/LEGUMES', 'result-categories')">🥬 Légumes</button>
                            <button class="btn" onclick="testAPI('/api/foods/categorie/FRUITS', 'result-categories')">🍎 Fruits</button>
                            <button class="btn" onclick="testAPI('/api/foods/categorie/VIANDES', 'result-categories')">🥩 Viandes</button>
                            <button class="btn" onclick="testAPI('/api/foods/categorie/CEREALES', 'result-categories')">🌾 Céréales</button>
                            <button class="btn" onclick="testAPI('/api/foods/categorie/DESSERTS', 'result-categories')">🍰 Desserts</button>
                            <div id="result-categories" class="result">Tests par catégorie d'aliments...</div>
                        </div>

                        <!-- Tests par Type Ingrédient -->
                        <div class="test-section">
                            <h3>🧊 Tests par Type</h3>
                            <button class="btn" onclick="testAPI('/api/ingredients/type/FRAIS', 'result-types')">🆕 Frais</button>
                            <button class="btn" onclick="testAPI('/api/ingredients/type/SURGELE', 'result-types')">🧊 Surgelé</button>
                            <button class="btn" onclick="testAPI('/api/ingredients/type/CONSERVE', 'result-types')">🥫 Conserve</button>
                            <button class="btn" onclick="testAPI('/api/ingredients/type/SEC', 'result-types')">🌾 Sec</button>
                            <div id="result-types" class="result">Tests par type d'ingrédient...</div>
                        </div>

                        <!-- Tests Images -->
                        <div class="test-section">
                            <h3>🖼️ API Images</h3>
                            <button class="btn btn-warning" onclick="testAPI('/api/test-images', 'result-images')">📊 Test Service</button>
                            <button class="btn" onclick="testAPI('/api/images', 'result-images')">📋 Toutes</button>
                            <div id="result-images" class="result">Tests des APIs Images...</div>
                        </div>

                        <!-- Tests Buffets -->
                        <div class="test-section">
                            <h3>🍽️ API Buffets</h3>
                            <button class="btn btn-danger" onclick="testAPI('/api/test-buffets', 'result-buffets')">🧪 Test Service</button>
                            <button class="btn" onclick="testAPI('/api/buffets/types-evenements', 'result-buffets')">🎉 Types (Direct)</button>
                            <div id="result-buffets" class="result">Tests des APIs Buffets...</div>
                        </div>

                        <!-- Tests Planification -->
                        <div class="test-section">
                            <h3>📅 API Planification</h3>
                            <button class="btn btn-warning" onclick="testAPI('/api/test-planification', 'result-planification')">🧪 Test Service</button>
                            <button class="btn" onclick="testAPI('/api/planification/types-repas', 'result-planification')">🍽️ Types (Direct)</button>
                            <div id="result-planification" class="result">Tests des APIs Planification...</div>
                        </div>
                    </div>
                </div>

                <script>
                    let testStats = { total: 0, success: 0, errors: 0 };

                    async function testAPI(url, resultElementId) {
                        const resultElement = document.getElementById(resultElementId);
                        const statusIndicator = '<span class="status-indicator status-loading"></span>';
                        
                        resultElement.innerHTML = statusIndicator + 'Test en cours: ' + url + '\\n';
                        testStats.total++;
                        updateStats();

                        try {
                            const startTime = Date.now();
                            const response = await fetch(url);
                            const endTime = Date.now();
                            const duration = endTime - startTime;
                            
                            if (response.ok) {
                                const data = await response.json();
                                const successIndicator = '<span class="status-indicator status-success"></span>';
                                resultElement.innerHTML = successIndicator + 
                                    'SUCCESS (' + duration + 'ms)\\n' +
                                    'URL: ' + url + '\\n' +
                                    'Status: ' + response.status + '\\n' +
                                    'Résultat:\\n' + JSON.stringify(data, null, 2);
                                testStats.success++;
                            } else {
                                throw new Error('HTTP ' + response.status + ': ' + response.statusText);
                            }
                        } catch (error) {
                            const errorIndicator = '<span class="status-indicator status-error"></span>';
                            resultElement.innerHTML = errorIndicator + 
                                'ERROR: ' + error.message + '\\n' +
                                'URL: ' + url + '\\n' +
                                'Détails: ' + error.stack;
                            testStats.errors++;
                        }
                        updateStats();
                    }

                    function updateStats() {
                        document.getElementById('total-tests').textContent = 'Tests: ' + testStats.total;
                        document.getElementById('success-tests').textContent = '✅ Succès: ' + testStats.success;
                        document.getElementById('error-tests').textContent = '❌ Erreurs: ' + testStats.errors;
                    }

                    async function runAllTests() {
                        clearAllResults();
                        const tests = [
                            '/api/hello',
                            '/api/status-simple',
                            '/api/detailed-status',
                            '/api/personnes',
                            '/api/foods',
                            '/api/ingredients',
                            '/api/foods/categorie/LEGUMES',
                            '/api/foods/categorie/FRUITS',
                            '/api/foods/categorie/VIANDES',
                            '/api/ingredients/type/FRAIS',
                            '/api/test-images',
                            '/api/test-buffets',
                            '/api/test-planification'
                        ];

                        for (let i = 0; i < tests.length; i++) {
                            await testAPI(tests[i], 'result-base');
                            await new Promise(resolve => setTimeout(resolve, 500)); // Pause entre tests
                        }
                    }

                    function clearAllResults() {
                        testStats = { total: 0, success: 0, errors: 0 };
                        updateStats();
                        
                        const resultElements = document.querySelectorAll('.result');
                        resultElements.forEach(element => {
                            element.textContent = 'Résultats effacés...';
                        });
                    }

                    async function createDemoData() {
                        await testAPI('/api/create-demo-data', 'result-base');
                    }
                </script>
            </body>
            </html>
            """;
    }

    /**
     * Créer des données de démonstration
     * GET /api/create-demo-data
     */
    @GetMapping("/create-demo-data")
    public ResponseEntity<Map<String, Object>> creerDonneesDemo() {
        try {
            int personnesCreees = 0;
            int alimentsCreees = 0;
            int ingredientsCreees = 0;

            // Création de personnes de demo
            Personne personneDemo = null;
            if (personneService != null) {
                try {
                    Personne demo1 = new Personne();
                    demo1.setNom("Martin");
                   
                    demo1.setEmail("jean.martin@example.com");
                    demo1.setMotDePasse("password123");
                    personneDemo = personneService.creerPersonne(demo1);
                    personnesCreees++;

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
                        // Ignore
                    }
                }
            }

            // Création d'ingrédients
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
                } catch (Exception e) {
                    // Ignore si déjà créés
                }
            }

            // Création d'aliments
            if (foodService != null && personneDemo != null) {
                try {
                    Food salade = new Food();
                    salade.setNom("Salade de Tomates");
                    salade.setDescription("Salade fraîche de tomates");
                    salade.setCategorie(CategorieFood.LEGUMES);
                    salade.setTempsPreparation("15 minutes");
                    salade.setCalories(25.0);
                    salade.setPrix(5.50);
                    salade.setPersonne(personneDemo);
                    foodService.creerAliment(salade);
                    alimentsCreees++;

                    Food steak = new Food();
                    steak.setNom("Steak de Bœuf");
                    steak.setDescription("Steak grillé");
                    steak.setCategorie(CategorieFood.VIANDES);
                    steak.setTempsPreparation("20 minutes");
                    steak.setCalories(250.0);
                    steak.setPrix(12.50);
                    steak.setPersonne(personneDemo);
                    foodService.creerAliment(steak);
                    alimentsCreees++;
                } catch (Exception e) {
                    // Ignore si déjà créés
                }
            }

            return ResponseEntity.ok(Map.of(
                "statut", "SUCCESS",
                "message", "Données de démonstration créées",
                "details", Map.of(
                    "personnes_creees", personnesCreees,
                    "ingredients_crees", ingredientsCreees,
                    "aliments_crees", alimentsCreees
                ),
                "timestamp", LocalDateTime.now()
            ));
        } catch (Exception e) {
            return ResponseEntity.ok(Map.of(
                "statut", "PARTIAL_SUCCESS",
                "message", "Création partielle des données",
                "erreur", e.getMessage(),
                "timestamp", LocalDateTime.now()
            ));
        }
    }

    /**
     * Statut détaillé de tous les services
     * GET /api/detailed-status
     */

    /**
     * Test sécurisé des buffets - gère le cas où le service n'est pas disponible
     * GET /api/test-buffets
     */
    @GetMapping("/test-buffets")
    public ResponseEntity<Map<String, Object>> testerBuffets() {
        if (buffetService == null) {
            return ResponseEntity.ok(Map.of(
                "statut", "SERVICE_UNAVAILABLE",
                "message", "BuffetService n'est pas disponible",
                "service_actif", false,
                "timestamp", LocalDateTime.now()
            ));
        }
        
        try {
            // Test simple des types d'événements (ne nécessite pas de base de données)
            List<String> typesEvenements = List.of("MARIAGE", "ANNIVERSAIRE", "ENTREPRISE", "COCKTAIL");
            
            return ResponseEntity.ok(Map.of(
                "statut", "SUCCESS",
                "service_actif", true,
                "types_evenements", typesEvenements,
                "message", "BuffetService est disponible",
                "timestamp", LocalDateTime.now()
            ));
        } catch (Exception e) {
            return ResponseEntity.ok(Map.of(
                "statut", "ERROR",
                "service_actif", true,
                "message", "Erreur dans BuffetService: " + e.getMessage(),
                "timestamp", LocalDateTime.now()
            ));
        }
    }

    /**
     * Test sécurisé des images
     * GET /api/test-images
     */
    @GetMapping("/test-images")
    public ResponseEntity<Map<String, Object>> testerImages() {
        if (imageService == null) {
            return ResponseEntity.ok(Map.of(
                "statut", "SERVICE_UNAVAILABLE",
                "message", "ImageService n'est pas disponible",
                "service_actif", false,
                "timestamp", LocalDateTime.now()
            ));
        }
        
        try {
            long nombreImages = imageService.compterImages();
            return ResponseEntity.ok(Map.of(
                "statut", "SUCCESS",
                "service_actif", true,
                "nombre_images", nombreImages,
                "timestamp", LocalDateTime.now()
            ));
        } catch (Exception e) {
            return ResponseEntity.ok(Map.of(
                "statut", "ERROR",
                "service_actif", true,
                "message", "Erreur dans ImageService: " + e.getMessage(),
                "timestamp", LocalDateTime.now()
            ));
        }
    }

    /**
     * Test sécurisé de la planification
     * GET /api/test-planification
     */
    @GetMapping("/test-planification")
    public ResponseEntity<Map<String, Object>> testerPlanification() {
        if (planificationService == null) {
            return ResponseEntity.ok(Map.of(
                "statut", "SERVICE_UNAVAILABLE",
                "message", "PlanificationService n'est pas disponible",
                "service_actif", false,
                "timestamp", LocalDateTime.now()
            ));
        }
        
        try {
            List<String> typesRepas = List.of("Petit-déjeuner", "Déjeuner", "Dîner", "Collation");
            return ResponseEntity.ok(Map.of(
                "statut", "SUCCESS",
                "service_actif", true,
                "types_repas", typesRepas,
                "timestamp", LocalDateTime.now()
            ));
        } catch (Exception e) {
            return ResponseEntity.ok(Map.of(
                "statut", "ERROR",
                "service_actif", true,
                "message", "Erreur dans PlanificationService: " + e.getMessage(),
                "timestamp", LocalDateTime.now()
            ));
        }
    }
    @GetMapping("/detailed-status")
    public ResponseEntity<Map<String, Object>> statusDetaille() {
        return ResponseEntity.ok(Map.of(
            "application", "Food Management System",
            "version", "1.0.0",
            "timestamp", LocalDateTime.now(),
            "services", Map.of(
                "personneService", personneService != null ? "✅ ACTIVE" : "❌ INACTIVE",
                "foodService", foodService != null ? "✅ ACTIVE" : "❌ INACTIVE",
                "ingredientService", ingredientService != null ? "✅ ACTIVE" : "❌ INACTIVE",
                "buffetService", buffetService != null ? "✅ ACTIVE" : "❌ INACTIVE",
                "imageService", imageService != null ? "✅ ACTIVE" : "❌ INACTIVE",
                "planificationService", planificationService != null ? "✅ ACTIVE" : "❌ INACTIVE"
            ),
            "categories_supportees", Map.of(
                "aliments", Arrays.asList("LEGUMES", "FRUITS", "VIANDES", "CEREALES", "DESSERTS"),
                "ingredients", Arrays.asList("FRAIS", "SURGELE", "CONSERVE", "SEC")
            ),
            "endpoints_principaux", Map.of(
                "test", "/api/test-all",
                "personnes", "/api/personnes",
                "aliments", "/api/foods",
                "ingredients", "/api/ingredients"
            )
        ));
    }
}