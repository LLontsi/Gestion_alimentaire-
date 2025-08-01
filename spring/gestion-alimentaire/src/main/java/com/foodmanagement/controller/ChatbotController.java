package com.foodmanagement.controller;

import com.foodmanagement.service.ChatbotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Contrôleur REST pour le chatbot alimentaire
 * Expose les APIs pour l'interaction avec l'assistant intelligent
 */
@RestController
@RequestMapping("/chatbot")
@CrossOrigin(origins = "*")
public class ChatbotController {

    @Autowired
    private ChatbotService chatbotService;

    /**
     * Interface de test du chatbot
     * GET /api/chatbot/demo
     */
    @GetMapping(value = "/demo", produces = "text/html")
    public String pageDemo() {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <title>🤖 Assistant Culinaire - Food Management</title>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <style>
                    * { box-sizing: border-box; margin: 0; padding: 0; }
                    
                    body {
                        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                        min-height: 100vh;
                        display: flex;
                        align-items: center;
                        justify-content: center;
                        padding: 20px;
                    }
                    
                    .chatbot-container {
                        background: white;
                        border-radius: 20px;
                        box-shadow: 0 25px 50px rgba(0,0,0,0.15);
                        width: 100%;
                        max-width: 800px;
                        height: 600px;
                        display: flex;
                        flex-direction: column;
                        overflow: hidden;
                    }
                    
                    .chatbot-header {
                        background: linear-gradient(135deg, #2c3e50, #3498db);
                        color: white;
                        padding: 20px;
                        text-align: center;
                    }
                    
                    .chatbot-header h1 {
                        font-size: 1.8em;
                        margin-bottom: 5px;
                    }
                    
                    .chatbot-header p {
                        opacity: 0.9;
                        font-size: 0.9em;
                    }
                    
                    .chat-messages {
                        flex: 1;
                        overflow-y: auto;
                        padding: 20px;
                        background: #f8f9fa;
                        display: flex;
                        flex-direction: column;
                        gap: 15px;
                    }
                    
                    .message {
                        max-width: 80%;
                        padding: 12px 16px;
                        border-radius: 18px;
                        word-wrap: break-word;
                        animation: fadeIn 0.3s ease-in;
                    }
                    
                    .message.user {
                        background: #3498db;
                        color: white;
                        align-self: flex-end;
                        border-bottom-right-radius: 5px;
                    }
                    
                    .message.bot {
                        background: white;
                        color: #2c3e50;
                        align-self: flex-start;
                        border: 1px solid #e9ecef;
                        border-bottom-left-radius: 5px;
                        box-shadow: 0 2px 5px rgba(0,0,0,0.1);
                    }
                    
                    .message .timestamp {
                        font-size: 0.7em;
                        opacity: 0.7;
                        margin-top: 5px;
                        display: block;
                    }
                    
                    .suggestions {
                        display: flex;
                        flex-wrap: wrap;
                        gap: 8px;
                        margin-top: 10px;
                    }
                    
                    .suggestion-btn {
                        background: #3498db;
                        color: white;
                        border: none;
                        padding: 6px 12px;
                        border-radius: 15px;
                        font-size: 0.8em;
                        cursor: pointer;
                        transition: all 0.3s;
                    }
                    
                    .suggestion-btn:hover {
                        background: #2980b9;
                        transform: translateY(-1px);
                    }
                    
                    .chat-input-container {
                        background: white;
                        padding: 20px;
                        border-top: 1px solid #e9ecef;
                        display: flex;
                        gap: 10px;
                        align-items: center;
                    }
                    
                    .chat-input {
                        flex: 1;
                        border: 2px solid #e9ecef;
                        border-radius: 25px;
                        padding: 12px 20px;
                        font-size: 14px;
                        outline: none;
                        transition: border-color 0.3s;
                    }
                    
                    .chat-input:focus {
                        border-color: #3498db;
                    }
                    
                    .send-btn {
                        background: #3498db;
                        color: white;
                        border: none;
                        border-radius: 50%;
                        width: 45px;
                        height: 45px;
                        cursor: pointer;
                        transition: all 0.3s;
                        display: flex;
                        align-items: center;
                        justify-content: center;
                        font-size: 18px;
                    }
                    
                    .send-btn:hover {
                        background: #2980b9;
                        transform: scale(1.05);
                    }
                    
                    .send-btn:disabled {
                        background: #bdc3c7;
                        cursor: not-allowed;
                        transform: none;
                    }
                    
                    .typing-indicator {
                        display: none;
                        align-self: flex-start;
                        background: white;
                        border: 1px solid #e9ecef;
                        border-radius: 18px;
                        padding: 12px 16px;
                        box-shadow: 0 2px 5px rgba(0,0,0,0.1);
                    }
                    
                    .typing-dots {
                        display: flex;
                        gap: 4px;
                    }
                    
                    .typing-dots span {
                        width: 8px;
                        height: 8px;
                        border-radius: 50%;
                        background: #bdc3c7;
                        animation: typing 1.4s infinite;
                    }
                    
                    .typing-dots span:nth-child(2) { animation-delay: 0.2s; }
                    .typing-dots span:nth-child(3) { animation-delay: 0.4s; }
                    
                    @keyframes typing {
                        0%, 60%, 100% { transform: translateY(0); }
                        30% { transform: translateY(-10px); }
                    }
                    
                    @keyframes fadeIn {
                        from { opacity: 0; transform: translateY(10px); }
                        to { opacity: 1; transform: translateY(0); }
                    }
                    
                    .quick-actions {
                        background: #ecf0f1;
                        padding: 15px;
                        border-bottom: 1px solid #e9ecef;
                    }
                    
                    .quick-actions h4 {
                        margin-bottom: 10px;
                        color: #2c3e50;
                        font-size: 0.9em;
                    }
                    
                    .quick-actions-grid {
                        display: grid;
                        grid-template-columns: repeat(auto-fit, minmax(160px, 1fr));
                        gap: 10px;
                    }
                    
                    .quick-action {
                        background: white;
                        border: 1px solid #e9ecef;
                        border-radius: 8px;
                        padding: 10px;
                        text-align: center;
                        cursor: pointer;
                        transition: all 0.3s;
                        font-size: 0.8em;
                    }
                    
                    .quick-action:hover {
                        background: #3498db;
                        color: white;
                        transform: translateY(-2px);
                    }
                    
                    @media (max-width: 768px) {
                        .chatbot-container {
                            height: 100vh;
                            border-radius: 0;
                        }
                        .message {
                            max-width: 90%;
                        }
                    }
                </style>
            </head>
            <body>
                <div class="chatbot-container">
                    <div class="chatbot-header">
                        <h1>🤖 Assistant Culinaire</h1>
                        <p>Votre compagnon intelligent pour tout ce qui concerne l'alimentation</p>
                    </div>
                    
                    <div class="quick-actions">
                        <h4>Actions rapides :</h4>
                        <div class="quick-actions-grid">
                            <div class="quick-action" onclick="sendQuickMessage('Bonjour, comment ça va ?')">
                                👋 Saluer
                            </div>
                            <div class="quick-action" onclick="sendQuickMessage('Peux-tu me suggérer des aliments sains ?')">
                                🥗 Aliments sains
                            </div>
                            <div class="quick-action" onclick="sendQuickMessage('Comment planifier mes repas de la semaine ?')">
                                📅 Planifier repas
                            </div>
                            <div class="quick-action" onclick="sendQuickMessage('Aide-moi à organiser un buffet pour 20 personnes')">
                                🍽️ Organiser buffet
                            </div>
                        </div>
                    </div>
                    
                    <div class="chat-messages" id="chatMessages">
                        <div class="message bot">
                            👋 Bonjour ! Je suis votre assistant culinaire intelligent. 
                            <br><br>
                            Je peux vous aider avec :
                            <br>🍎 Recherche d'aliments et recettes
                            <br>💡 Conseils nutritionnels personnalisés  
                            <br>📅 Planification de repas
                            <br>🍽️ Organisation de buffets
                            <br>🥕 Informations sur les ingrédients
                            <br><br>
                            Comment puis-je vous aider aujourd'hui ?
                            <span class="timestamp">${new Date().toLocaleTimeString()}</span>
                        </div>
                    </div>
                    
                    <div class="typing-indicator" id="typingIndicator">
                        <div class="typing-dots">
                            <span></span>
                            <span></span>
                            <span></span>
                        </div>
                    </div>
                    
                    <div class="chat-input-container">
                        <input 
                            type="text" 
                            class="chat-input" 
                            id="messageInput" 
                            placeholder="Tapez votre message..."
                            onkeypress="handleKeyPress(event)"
                        >
                        <button class="send-btn" id="sendBtn" onclick="sendMessage()">
                            ➤
                        </button>
                    </div>
                </div>
                
                <script>
                    const chatMessages = document.getElementById('chatMessages');
                    const messageInput = document.getElementById('messageInput');
                    const sendBtn = document.getElementById('sendBtn');
                    const typingIndicator = document.getElementById('typingIndicator');
                    
                    // Focus automatique sur l'input
                    messageInput.focus();
                    
                    function handleKeyPress(event) {
                        if (event.key === 'Enter' && !event.shiftKey) {
                            event.preventDefault();
                            sendMessage();
                        }
                    }
                    
                    function sendQuickMessage(message) {
                        messageInput.value = message;
                        sendMessage();
                    }
                    
                    async function sendMessage() {
                        const message = messageInput.value.trim();
                        if (!message) return;
                        
                        // Afficher le message utilisateur
                        addMessage(message, 'user');
                        
                        // Vider l'input et désactiver le bouton
                        messageInput.value = '';
                        sendBtn.disabled = true;
                        
                        // Afficher l'indicateur de frappe
                        showTyping();
                        
                        try {
                            // Appel à l'API du chatbot
                            const response = await fetch('/api/chatbot/message', {
                                method: 'POST',
                                headers: {
                                    'Content-Type': 'application/json',
                                },
                                body: JSON.stringify({
                                    message: message,
                                    userId: 1 // ID utilisateur par défaut pour la démo
                                })
                            });
                            
                            // Masquer l'indicateur de frappe
                            hideTyping();
                            
                            if (response.ok) {
                                const data = await response.json();
                                
                                // Vérifier si on a une réponse valide
                                if (data.reponse) {
                                    addMessage(data.reponse, 'bot', data);
                                } else if (data.message) {
                                    addMessage(data.message, 'bot', data);
                                } else {
                                    addMessage('J\\'ai reçu votre message mais je n\\'ai pas pu générer une réponse appropriée.', 'bot');
                                }
                            } else {
                                // Tenter de lire le message d'erreur
                                try {
                                    const errorData = await response.json();
                                    const errorMessage = errorData.message || errorData.reponse || 'Erreur inconnue';
                                    addMessage('Désolé, une erreur s\\'est produite: ' + errorMessage, 'bot');
                                } catch (e) {
                                    addMessage('Désolé, une erreur s\\'est produite (Code: ' + response.status + '). Veuillez réessayer.', 'bot');
                                }
                            }
                            
                        } catch (error) {
                            hideTyping();
                            console.error('Erreur de connexion:', error);
                            addMessage('😔 Erreur de connexion au serveur. Vérifiez que le serveur est démarré et que l\\'API chatbot est configurée.', 'bot');
                        }
                        
                        // Réactiver le bouton
                        sendBtn.disabled = false;
                        messageInput.focus();
                    }
                    
                    function addMessage(text, sender, data = null) {
                        const messageDiv = document.createElement('div');
                        messageDiv.className = 'message ' + sender;
                        
                        let content = text;
                        
                        // Ajouter des suggestions si disponibles
                        if (data && data.suggestions) {
                            content += '<div class="suggestions">';
                            data.suggestions.forEach(suggestion => {
                                content += '<button class="suggestion-btn" onclick="sendQuickMessage(\\''+suggestion+'\\')">'+suggestion+'</button>';
                            });
                            content += '</div>';
                        }
                        
                        // Ajouter l'heure
                        content += '<span class="timestamp">' + new Date().toLocaleTimeString() + '</span>';
                        
                        messageDiv.innerHTML = content;
                        
                        chatMessages.appendChild(messageDiv);
                        chatMessages.scrollTop = chatMessages.scrollHeight;
                    }
                    
                    function showTyping() {
                        typingIndicator.style.display = 'flex';
                        chatMessages.scrollTop = chatMessages.scrollHeight;
                    }
                    
                    function hideTyping() {
                        typingIndicator.style.display = 'none';
                    }
                    
                    // Auto-resize du textarea (si on voulait en utiliser un)
                    messageInput.addEventListener('input', function() {
                        if (this.value.length > 100) {
                            this.style.height = 'auto';
                            this.style.height = Math.min(this.scrollHeight, 120) + 'px';
                        }
                    });
                </script>
            </body>
            </html>
            """;
    }

    /**
     * Envoyer un message au chatbot
     * POST /api/chatbot/message
     */
    @PostMapping("/message")
    public ResponseEntity<?> envoyerMessage(@RequestBody Map<String, Object> data) {
        try {
            String message = data.get("message").toString();
            Long userId = data.containsKey("userId") ? 
                Long.valueOf(data.get("userId").toString()) : null;
            
            if (!org.springframework.util.StringUtils.hasText(message)) {
                return ResponseEntity.badRequest().body(Map.of(
                    "erreur", "Message vide",
                    "message", "Le message ne peut pas être vide"
                ));
            }
            
            Map<String, Object> reponse = chatbotService.traiterMessage(message, userId);
            
            return ResponseEntity.ok(reponse);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "erreur", "Erreur interne",
                "message", "Une erreur s'est produite lors du traitement : " + e.getMessage(),
                "timestamp", LocalDateTime.now()
            ));
        }
    }

    /**
     * Analyser l'intention d'un message
     * POST /api/chatbot/analyser-intention
     */
    @PostMapping("/analyser-intention")
    public ResponseEntity<?> analyserIntention(@RequestBody Map<String, String> data) {
        try {
            String message = data.get("message");
            
            if (!org.springframework.util.StringUtils.hasText(message)) {
                return ResponseEntity.badRequest().body(Map.of(
                    "erreur", "Message vide",
                    "message", "Le message à analyser ne peut pas être vide"
                ));
            }
            
            Map<String, Object> intention = chatbotService.analyserIntention(message);
            
            return ResponseEntity.ok(Map.of(
                "message_analyse", message,
                "analyse", intention,
                "timestamp", LocalDateTime.now()
            ));
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Erreur d'analyse",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Obtenir des suggestions d'aliments
     * POST /api/chatbot/suggestions/aliments
     */
    @PostMapping("/suggestions/aliments")
    public ResponseEntity<?> suggererAliments(@RequestBody(required = false) Map<String, Object> criteres) {
        try {
            Map<String, Object> criteresFiltered = criteres != null ? criteres : Map.of();
            Map<String, Object> suggestions = chatbotService.suggererAliments(criteresFiltered);
            
            return ResponseEntity.ok(suggestions);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Erreur de suggestion",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Obtenir des conseils nutritionnels
     * POST /api/chatbot/conseils-nutrition
     */
    @PostMapping("/conseils-nutrition")
    public ResponseEntity<?> obtenirConseilsNutritionnels(@RequestBody(required = false) Map<String, Object> contexte) {
        try {
            Map<String, Object> contexteFiltered = contexte != null ? contexte : Map.of();
            Map<String, Object> conseils = chatbotService.donnerConseilsNutritionnels(contexteFiltered);
            
            return ResponseEntity.ok(conseils);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Erreur de conseil",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Aider à la planification de repas
     * POST /api/chatbot/aide-planification
     */
    @PostMapping("/aide-planification")
    public ResponseEntity<?> aiderPlanification(@RequestBody Map<String, Object> data) {
        try {
            Map<String, Object> preferences = (Map<String, Object>) data.getOrDefault("preferences", Map.of());
            Integer nombreJours = data.containsKey("nombreJours") ? 
                Integer.valueOf(data.get("nombreJours").toString()) : 7;
            Long userId = data.containsKey("userId") ? 
                Long.valueOf(data.get("userId").toString()) : null;
            
            Map<String, Object> aide = chatbotService.aiderPlanificationRepas(preferences, nombreJours, userId);
            
            return ResponseEntity.ok(aide);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Erreur de planification",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Obtenir des informations sur un aliment
     * GET /api/chatbot/info-aliment/{nomAliment}
     */
    @GetMapping("/info-aliment/{nomAliment}")
    public ResponseEntity<?> obtenirInfosAliment(@PathVariable String nomAliment) {
        try {
            Map<String, Object> infos = chatbotService.obtenirInfosAliment(nomAliment);
            return ResponseEntity.ok(infos);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Erreur de recherche",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Suggérer des alternatives à un aliment
     * POST /api/chatbot/alternatives
     */
    @PostMapping("/alternatives")
    public ResponseEntity<?> suggererAlternatives(@RequestBody Map<String, String> data) {
        try {
            String nomAliment = data.get("nomAliment");
            String raison = data.getOrDefault("raison", "substitution");
            
            if (!org.springframework.util.StringUtils.hasText(nomAliment)) {
                return ResponseEntity.badRequest().body(Map.of(
                    "erreur", "Nom d'aliment manquant",
                    "message", "Le nom de l'aliment est obligatoire"
                ));
            }
            
            Map<String, Object> alternatives = chatbotService.suggererAlternatives(nomAliment, raison);
            
            return ResponseEntity.ok(alternatives);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Erreur de suggestion",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Calculer la nutrition d'une liste d'aliments
     * POST /api/chatbot/calculer-nutrition
     */
    @PostMapping("/calculer-nutrition")
    public ResponseEntity<?> calculerNutrition(@RequestBody Map<String, List<Map<String, Object>>> data) {
        try {
            List<Map<String, Object>> aliments = data.get("aliments");
            
            if (aliments == null || aliments.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of(
                    "erreur", "Liste d'aliments vide",
                    "message", "Veuillez fournir une liste d'aliments"
                ));
            }
            
            Map<String, Object> nutrition = chatbotService.calculerNutrition(aliments);
            
            return ResponseEntity.ok(nutrition);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Erreur de calcul",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Aider à l'organisation d'un buffet
     * POST /api/chatbot/aide-buffet
     */
    @PostMapping("/aide-buffet")
    public ResponseEntity<?> aiderBuffet(@RequestBody Map<String, Object> data) {
        try {
            String typeEvenement = data.get("typeEvenement").toString();
            Integer nombreInvites = Integer.valueOf(data.get("nombreInvites").toString());
            Double budget = data.containsKey("budget") ? 
                Double.valueOf(data.get("budget").toString()) : null;
            
            Map<String, Object> aide = chatbotService.aiderOrganisationBuffet(typeEvenement, nombreInvites, budget);
            
            return ResponseEntity.ok(aide);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Erreur d'aide buffet",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Rechercher des recettes par ingrédients
     * POST /api/chatbot/rechercher-recettes
     */
    @PostMapping("/rechercher-recettes")
    public ResponseEntity<?> rechercherRecettes(@RequestBody Map<String, List<String>> data) {
        try {
            List<String> ingredients = data.get("ingredients");
            
            if (ingredients == null || ingredients.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of(
                    "erreur", "Liste d'ingrédients vide",
                    "message", "Veuillez fournir une liste d'ingrédients"
                ));
            }
            
            Map<String, Object> recettes = chatbotService.rechercherRecettes(ingredients);
            
            return ResponseEntity.ok(recettes);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Erreur de recherche",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Obtenir l'historique de conversation
     * GET /api/chatbot/historique/{userId}?limite={limite}
     */
    @GetMapping("/historique/{userId}")
    public ResponseEntity<?> obtenirHistorique(@PathVariable Long userId,
                                             @RequestParam(defaultValue = "20") Integer limite) {
        try {
            List<Map<String, Object>> historique = 
                chatbotService.obtenirHistoriqueConversation(userId, limite);
            
            return ResponseEntity.ok(Map.of(
                "user_id", userId,
                "historique", historique,
                "nombre_messages", historique.size(),
                "limite", limite
            ));
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Erreur d'historique",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Réinitialiser le contexte de conversation
     * POST /api/chatbot/reinitialiser/{userId}
     */
    @PostMapping("/reinitialiser/{userId}")
    public ResponseEntity<?> reinitialiserContexte(@PathVariable Long userId) {
        try {
            boolean reinitialise = chatbotService.reinitialiserContexte(userId);
            
            if (reinitialise) {
                return ResponseEntity.ok(Map.of(
                    "statut", "CONTEXTE_REINITIALISE",
                    "message", "Le contexte de conversation a été réinitialisé",
                    "user_id", userId,
                    "timestamp", LocalDateTime.now()
                ));
            } else {
                return ResponseEntity.badRequest().body(Map.of(
                    "erreur", "Échec de réinitialisation",
                    "message", "Impossible de réinitialiser le contexte"
                ));
            }
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Erreur de réinitialisation",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Obtenir les statistiques d'utilisation du chatbot
     * GET /api/chatbot/statistiques
     */
    @GetMapping("/statistiques")
    public ResponseEntity<Map<String, Object>> obtenirStatistiques() {
        Map<String, Object> statistiques = chatbotService.obtenirStatistiquesUtilisation();
        
        return ResponseEntity.ok(Map.of(
            "statistiques", statistiques,
            "date_generation", LocalDateTime.now()
        ));
    }

    /**
     * Test rapide du chatbot
     * GET /api/chatbot/test
     */
    @GetMapping("/test")
    public ResponseEntity<Map<String, Object>> testerChatbot() {
        try {
            Map<String, Object> testMessage = chatbotService.traiterMessage("Bonjour", null);
            
            return ResponseEntity.ok(Map.of(
                "statut", "SUCCESS",
                "message", "Chatbot opérationnel",
                "test_reponse", testMessage,
                "timestamp", LocalDateTime.now()
            ));
            
        } catch (Exception e) {
            return ResponseEntity.ok(Map.of(
                "statut", "ERROR",
                "message", "Erreur dans le chatbot : " + e.getMessage(),
                "timestamp", LocalDateTime.now()
            ));
        }
    }

    /**
     * Obtenir l'aide sur les fonctionnalités disponibles
     * GET /api/chatbot/aide
     */
    @GetMapping("/aide")
    public ResponseEntity<Map<String, Object>> obtenirAide() {
        return ResponseEntity.ok(Map.of(
            "fonctionnalites", Map.of(
                "recherche_aliments", "Rechercher des informations sur les aliments",
                "conseils_nutrition", "Obtenir des conseils nutritionnels personnalisés",
                "planification_repas", "Aide à la planification de menus",
                "organisation_buffets", "Conseils pour organiser des buffets",
                "alternatives_alimentaires", "Suggérer des alternatives à un aliment",
                "calculs_nutritionnels", "Calculer les apports nutritionnels",
                "recherche_recettes", "Trouver des recettes par ingrédients"
            ),
            "exemples_questions", List.of(
                "Peux-tu me donner des infos sur les épinards ?",
                "Comment bien manger pour perdre du poids ?",
                "Aide-moi à planifier mes repas de la semaine",
                "Comment organiser un buffet pour 50 personnes ?",
                "Que puis-je manger à la place du beurre ?",
                "Calcule les calories de mon petit-déjeuner",
                "Quelles recettes puis-je faire avec des tomates et du fromage ?"
            ),
            "conseils_utilisation", List.of(
                "Soyez précis dans vos questions",
                "N'hésitez pas à donner du contexte (allergies, objectifs, etc.)",
                "Vous pouvez me demander des clarifications",
                "Je peux vous aider étape par étape"
            )
        ));
    }

    /**
     * Gestion globale des erreurs pour ce contrôleur
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> gererErreurGenerique(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
            "erreur", "Erreur interne du serveur",
            "message", e.getMessage(),
            "timestamp", LocalDateTime.now(),
            "suggestion", "Vérifiez que tous les services sont correctement configurés"
        ));
    }
}
