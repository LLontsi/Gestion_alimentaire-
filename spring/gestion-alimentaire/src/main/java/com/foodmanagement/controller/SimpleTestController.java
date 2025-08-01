package com.foodmanagement.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * Contrôleur de test simple pour vérifier que Spring fonctionne
 */
@RestController
public class SimpleTestController {

    /**
     * Test de base pour vérifier que l'application fonctionne
     * GET /api/hello
     */
    @GetMapping("/hello")
    public ResponseEntity<Map<String, Object>> hello() {
        return ResponseEntity.ok(Map.of(
            "message", "🎉 Hello ! Votre application Spring Boot fonctionne !",
            "timestamp", LocalDateTime.now(),
            "status", "SUCCESS"
        ));
    }

    /**
     * Page de test simple en HTML
     * GET /api/simple-test
     */
    @GetMapping(value = "/simple-test", produces = "text/html")
    public String pageTestSimple() {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <title>Test Simple - Food Management</title>
                <meta charset="UTF-8">
                <style>
                    body { 
                        font-family: Arial, sans-serif; 
                        text-align: center; 
                        padding: 50px; 
                        background: linear-gradient(135deg, #74b9ff, #0984e3); 
                        color: white; 
                    }
                    .container { 
                        background: rgba(255,255,255,0.1); 
                        padding: 30px; 
                        border-radius: 10px; 
                        display: inline-block; 
                    }
                    .btn { 
                        background: #00b894; 
                        color: white; 
                        padding: 10px 20px; 
                        border: none; 
                        border-radius: 5px; 
                        margin: 10px; 
                        cursor: pointer; 
                        text-decoration: none; 
                        display: inline-block; 
                    }
                    .result { 
                        background: #2d3436; 
                        color: #ddd; 
                        padding: 15px; 
                        border-radius: 5px; 
                        margin: 10px 0; 
                        font-family: monospace; 
                        white-space: pre-wrap; 
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <h1>🧪 Test Simple - Food Management API</h1>
                    <p>Votre application Spring Boot fonctionne !</p>
                    
                    <div>
                        <button class="btn" onclick="testHello()">🚀 Test Hello</button>
                        <a href="/api/hello" class="btn">📄 JSON Hello</a>
                        <a href="/api/personnes" class="btn">👥 API Personnes</a>
                        <a href="/api/foods" class="btn">🍎 API Foods</a>
                    </div>
                    
                    <div id="result" class="result">Cliquez sur "Test Hello" pour tester l'API</div>
                </div>
                
                <script>
                    async function testHello() {
                        try {
                            const response = await fetch('/api/hello');
                            const data = await response.json();
                            document.getElementById('result').textContent = JSON.stringify(data, null, 2);
                        } catch (error) {
                            document.getElementById('result').textContent = 'Erreur: ' + error.message;
                        }
                    }
                </script>
            </body>
            </html>
            """;
    }

    /**
     * Statut simple du système
     * GET /api/status-simple
     */
    @GetMapping("/status-simple")
    public ResponseEntity<Map<String, Object>> statusSimple() {
        return ResponseEntity.ok(Map.of(
            "app_name", "Food Management System",
            "status", "🟢 RUNNING",
            "timestamp", LocalDateTime.now(),
            "endpoints", Map.of(
                "hello", "/api/hello",
                "test_page", "/api/simple-test",
                "personnes", "/api/personnes",
                "foods", "/api/foods",
                "ingredients", "/api/ingredients"
            )
        ));
    }
}