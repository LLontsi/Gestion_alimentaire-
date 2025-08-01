package com.univyaounde.foodmanagement.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestModulesController {

    @GetMapping(value = "/test-modules", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> testModules() {
        String html = """
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Test des Modules - Planification & Buffet</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f5f5f5;
        }
        .container {
            max-width: 1200px;
            margin: 0 auto;
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        .module-section {
            margin-bottom: 40px;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 5px;
            background-color: #fafafa;
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
            color: #333;
        }
        input, select, textarea {
            width: 100%;
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 14px;
            box-sizing: border-box;
        }
        button {
            background-color: #007bff;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
            margin-right: 10px;
            margin-bottom: 10px;
        }
        button:hover {
            background-color: #0056b3;
        }
        .btn-success {
            background-color: #28a745;
        }
        .btn-success:hover {
            background-color: #1e7e34;
        }
        .btn-warning {
            background-color: #ffc107;
            color: #212529;
        }
        .btn-warning:hover {
            background-color: #e0a800;
        }
        .result {
            margin-top: 20px;
            padding: 15px;
            border-radius: 4px;
            white-space: pre-wrap;
            font-family: 'Courier New', monospace;
            font-size: 12px;
            max-height: 400px;
            overflow-y: auto;
            display: none;
        }
        .result.success {
            background-color: #d4edda;
            border: 1px solid #c3e6cb;
            color: #155724;
        }
        .result.error {
            background-color: #f8d7da;
            border: 1px solid #f5c6cb;
            color: #721c24;
        }
        .grid {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 20px;
        }
        h1 {
            color: #007bff;
            text-align: center;
            margin-bottom: 30px;
        }
        h2 {
            color: #495057;
            border-bottom: 2px solid #007bff;
            padding-bottom: 10px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>🍽️ Test des Modules - Planification & Buffet</h1>
        
        <div class="grid">
            <!-- MODULE PLANIFICATION -->
            <div class="module-section">
                <h2>📅 Module Planification Alimentaire</h2>
                
                <h3>Créer un Plan Hebdomadaire</h3>
                <form id="planForm">
                    <div class="form-group">
                        <label for="personneId">ID Personne:</label>
                        <input type="number" id="personneId" name="personneId" value="1" required>
                    </div>
                    <div class="form-group">
                        <label for="dateDebut">Date de début:</label>
                        <input type="date" id="dateDebut" name="dateDebut" required>
                    </div>
                    <button type="button" onclick="creerPlanHebdomadaire()">Créer Plan</button>
                </form>
                
                <h3>Obtenir Recommandations</h3>
                <form id="recommendationForm">
                    <div class="form-group">
                        <label for="personneIdReco">ID Personne:</label>
                        <input type="number" id="personneIdReco" name="personneIdReco" value="1" required>
                    </div>
                    <div class="form-group">
                        <label for="typeRepas">Type de Repas:</label>
                        <select id="typeRepas" name="typeRepas" required>
                            <option value="PETIT_DEJEUNER">Petit Déjeuner</option>
                            <option value="DEJEUNER">Déjeuner</option>
                            <option value="DINER">Dîner</option>
                        </select>
                    </div>
                    <button type="button" onclick="obtenirRecommandations()">Obtenir Recommandations</button>
                </form>
                
                <div id="planificationResult" class="result"></div>
            </div>
            
            <!-- MODULE BUFFET -->
            <div class="module-section">
                <h2>🍰 Module Gestion de Buffet</h2>
                
                <h3>Créer un Buffet</h3>
                <form id="buffetForm">
                    <div class="form-group">
                        <label for="nombreInvites">Nombre d'Invités:</label>
                        <input type="number" id="nombreInvites" name="nombreInvites" value="50" required>
                    </div>
                    <div class="form-group">
                        <label for="typeCeremonie">Type de Cérémonie:</label>
                        <select id="typeCeremonie" name="typeCeremonie" required>
                            <option value="mariage">Mariage</option>
                            <option value="anniversaire">Anniversaire</option>
                            <option value="bapteme">Baptême</option>
                            <option value="conference">Conférence</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="budgetEstime">Budget Estimé (€):</label>
                        <input type="number" id="budgetEstime" name="budgetEstime" value="1000" step="0.01" required>
                    </div>
                    <div class="form-group">
                        <label for="organisateurId">ID Organisateur:</label>
                        <input type="number" id="organisateurId" name="organisateurId" value="1" required>
                    </div>
                    <button type="button" onclick="creerBuffet()">Créer Buffet</button>
                </form>
                
                <h3>Actions Buffet</h3>
                <button type="button" class="btn-success" onclick="obtenirModeles()">Voir Modèles</button>
                <button type="button" class="btn-warning" onclick="optimiserDernierBuffet()">Optimiser Dernier Buffet</button>
                
                <div id="buffetResult" class="result"></div>
            </div>
        </div>
        
        <!-- SECTION TESTS RAPIDES -->
        <div class="module-section">
            <h2>⚡ Tests Rapides</h2>
            <button type="button" onclick="testerTousLesEndpoints()">Tester Tous les Endpoints</button>
            <button type="button" onclick="viderResultats()">Vider Résultats</button>
            
            <div id="testsResult" class="result"></div>
        </div>
    </div>

    <script>
        const API_BASE = '/api';
        let dernierBuffet = null;
        
        // Initialiser la date d'aujourd'hui
        document.getElementById('dateDebut').valueAsDate = new Date();
        
        function afficherResultat(elementId, data, isError = false) {
            const element = document.getElementById(elementId);
            element.style.display = 'block';
            element.className = `result ${isError ? 'error' : 'success'}`;
            element.textContent = typeof data === 'object' ? JSON.stringify(data, null, 2) : data;
        }
        
        async function creerPlanHebdomadaire() {
            try {
                const personneId = document.getElementById('personneId').value;
                const dateDebut = document.getElementById('dateDebut').value;
                
                const response = await fetch(`${API_BASE}/planification/plan-hebdomadaire?personneId=${personneId}&dateDebut=${dateDebut}`, {
                    method: 'POST'
                });
                
                if (response.ok) {
                    const data = await response.json();
                    afficherResultat('planificationResult', data);
                } else {
                    afficherResultat('planificationResult', `Erreur ${response.status}: ${response.statusText}`, true);
                }
            } catch (error) {
                afficherResultat('planificationResult', `Erreur: ${error.message}`, true);
            }
        }
        
        async function obtenirRecommandations() {
            try {
                const personneId = document.getElementById('personneIdReco').value;
                const typeRepas = document.getElementById('typeRepas').value;
                
                const response = await fetch(`${API_BASE}/planification/recommandations?personneId=${personneId}&typeRepas=${typeRepas}`);
                
                if (response.ok) {
                    const data = await response.json();
                    afficherResultat('planificationResult', data);
                } else {
                    afficherResultat('planificationResult', `Erreur ${response.status}: ${response.statusText}`, true);
                }
            } catch (error) {
                afficherResultat('planificationResult', `Erreur: ${error.message}`, true);
            }
        }
        
        async function creerBuffet() {
            try {
                const nombreInvites = document.getElementById('nombreInvites').value;
                const typeCeremonie = document.getElementById('typeCeremonie').value;
                const budgetEstime = document.getElementById('budgetEstime').value;
                const organisateurId = document.getElementById('organisateurId').value;
                
                const response = await fetch(`${API_BASE}/buffet/creer?nombreInvites=${nombreInvites}&typeCeremonie=${typeCeremonie}&budgetEstime=${budgetEstime}&organisateurId=${organisateurId}`, {
                    method: 'POST'
                });
                
                if (response.ok) {
                    const data = await response.json();
                    dernierBuffet = data;
                    afficherResultat('buffetResult', data);
                } else {
                    afficherResultat('buffetResult', `Erreur ${response.status}: ${response.statusText}`, true);
                }
            } catch (error) {
                afficherResultat('buffetResult', `Erreur: ${error.message}`, true);
            }
        }
        
        async function obtenirModeles() {
            try {
                const response = await fetch(`${API_BASE}/buffet/modeles`);
                
                if (response.ok) {
                    const data = await response.json();
                    afficherResultat('buffetResult', data);
                } else {
                    afficherResultat('buffetResult', `Erreur ${response.status}: ${response.statusText}`, true);
                }
            } catch (error) {
                afficherResultat('buffetResult', `Erreur: ${error.message}`, true);
            }
        }
        
        async function optimiserDernierBuffet() {
            if (!dernierBuffet) {
                afficherResultat('buffetResult', 'Veuillez d\\'abord créer un buffet', true);
                return;
            }
            
            try {
                const budgetMax = prompt('Entrez le budget maximum pour l\\'optimisation:', '800');
                if (!budgetMax) return;
                
                const response = await fetch(`${API_BASE}/buffet/optimiser?budgetMax=${budgetMax}`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(dernierBuffet)
                });
                
                if (response.ok) {
                    const data = await response.json();
                    afficherResultat('buffetResult', data);
                } else {
                    afficherResultat('buffetResult', `Erreur ${response.status}: ${response.statusText}`, true);
                }
            } catch (error) {
                afficherResultat('buffetResult', `Erreur: ${error.message}`, true);
            }
        }
        
        async function testerTousLesEndpoints() {
            let resultats = "=== TEST COMPLET DES ENDPOINTS ===\\n\\n";
            
            try {
                // Test 1: Statut API
                resultats += "1. Test du statut de l'API...\\n";
                const statusResponse = await fetch('/api/test/status');
                if (statusResponse.ok) {
                    const statusData = await statusResponse.json();
                    resultats += `✅ Statut API: OK - ${JSON.stringify(statusData)}\\n\\n`;
                } else {
                    resultats += `❌ Statut API: Erreur ${statusResponse.status}\\n\\n`;
                }
                
                // Test 2: Plan hebdomadaire
                resultats += "2. Test plan hebdomadaire...\\n";
                const planResponse = await fetch(`${API_BASE}/planification/plan-hebdomadaire?personneId=1&dateDebut=${new Date().toISOString().split('T')[0]}`, {
                    method: 'POST'
                });
                if (planResponse.ok) {
                    resultats += "✅ Plan hebdomadaire: OK\\n\\n";
                } else {
                    resultats += `❌ Plan hebdomadaire: Erreur ${planResponse.status}\\n\\n`;
                }
                
                // Test 3: Recommandations
                resultats += "3. Test recommandations...\\n";
                const recoResponse = await fetch(`${API_BASE}/planification/recommandations?personneId=1&typeRepas=DEJEUNER`);
                if (recoResponse.ok) {
                    resultats += "✅ Recommandations: OK\\n\\n";
                } else {
                    resultats += `❌ Recommandations: Erreur ${recoResponse.status}\\n\\n`;
                }
                
                // Test 4: Modèles buffet
                resultats += "4. Test modèles buffet...\\n";
                const modelesResponse = await fetch(`${API_BASE}/buffet/modeles`);
                if (modelesResponse.ok) {
                    resultats += "✅ Modèles buffet: OK\\n\\n";
                } else {
                    resultats += `❌ Modèles buffet: Erreur ${modelesResponse.status}\\n\\n`;
                }
                
                // Test 5: Création buffet
                resultats += "5. Test création buffet...\\n";
                const buffetResponse = await fetch(`${API_BASE}/buffet/creer?nombreInvites=30&typeCeremonie=anniversaire&budgetEstime=500&organisateurId=1`, {
                    method: 'POST'
                });
                if (buffetResponse.ok) {
                    resultats += "✅ Création buffet: OK\\n\\n";
                } else {
                    resultats += `❌ Création buffet: Erreur ${buffetResponse.status}\\n\\n`;
                }
                
                resultats += "=== FIN DES TESTS ===";
                
            } catch (error) {
                resultats += `❌ Erreur générale: ${error.message}`;
            }
            
            afficherResultat('testsResult', resultats);
        }
        
        function viderResultats() {
            ['planificationResult', 'buffetResult', 'testsResult'].forEach(id => {
                document.getElementById(id).style.display = 'none';
            });
        }
    </script>
</body>
</html>
        """;
        
        return ResponseEntity.ok(html);
    }
   
}