package com.univyaounde.foodmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.univyaounde.foodmanagement.service.PersonneService;
import com.univyaounde.foodmanagement.service.FoodService;
import com.univyaounde.foodmanagement.service.IngredientService;
import com.univyaounde.foodmanagement.service.ImageService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TestController {

    @Autowired
    private PersonneService personneService;

    @Autowired
    private FoodService foodService;

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private ImageService imageService;

    @GetMapping
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("API fonctionne correctement !");
    }

    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getStatus() {
        Map<String, Object> status = new HashMap<>();
        
        try {
            status.put("personnes_count", personneService.getAllPersonnes().size());
            status.put("foods_count", foodService.getAllFoods().size());
            status.put("ingredients_count", ingredientService.getAllIngredients().size());
            status.put("images_count", imageService.getAllImages().size());
            status.put("status", "OK");
        } catch (Exception e) {
            status.put("error", e.getMessage());
            status.put("status", "ERROR");
        }
        
        return ResponseEntity.ok(status);
    }
     @GetMapping(value = "/chatbot-demo", produces = MediaType.TEXT_HTML_VALUE)
public ResponseEntity<String> chatbotDemo() {
    String html = """
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chatbot Alimentaire</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 20px; background-color: #f5f5f5; }
        .chat-container { max-width: 800px; margin: 0 auto; background: white; border-radius: 8px; overflow: hidden; }
        .chat-header { background: #007bff; color: white; padding: 20px; text-align: center; }
        .chat-messages { height: 400px; overflow-y: auto; padding: 20px; }
        .message { margin-bottom: 15px; padding: 10px; border-radius: 10px; }
        .user-message { background: #e3f2fd; margin-left: 50px; }
        .bot-message { background: #f1f8e9; margin-right: 50px; }
        .chat-input { display: flex; padding: 20px; border-top: 1px solid #ddd; }
        .chat-input input { flex: 1; padding: 10px; border: 1px solid #ddd; border-radius: 5px; }
        .chat-input button { padding: 10px 20px; margin-left: 10px; background: #007bff; color: white; border: none; border-radius: 5px; cursor: pointer; }
    </style>
</head>
<body>
    <div class="chat-container">
        <div class="chat-header">
            <h2>🤖 Assistant Culinaire</h2>
            <p>Posez-moi vos questions sur l'alimentation !</p>
        </div>
        <div class="chat-messages" id="chatMessages">
            <div class="message bot-message">
                <strong>Assistant:</strong> Bonjour ! Je suis votre assistant culinaire. Comment puis-je vous aider aujourd'hui ?
            </div>
        </div>
        <div class="chat-input">
            <input type="text" id="messageInput" placeholder="Tapez votre message..." onkeypress="if(event.key==='Enter') envoyerMessage()">
            <button onclick="envoyerMessage()">Envoyer</button>
        </div>
    </div>

    <script>
        async function envoyerMessage() {
            const input = document.getElementById('messageInput');
            const message = input.value.trim();
            if (!message) return;
            
            // Afficher le message utilisateur
            ajouterMessage('Vous: ' + message, 'user-message');
            input.value = '';
            
            try {
                const response = await fetch('/api/chatbot/message', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({ message: message, userId: 1 })
                });
                
                const data = await response.json();
                ajouterMessage('Assistant: ' + data.message, 'bot-message');
                
                // Afficher des détails supplémentaires si disponibles
                if (data.details) {
                    ajouterMessage('Détails: ' + JSON.stringify(data.details, null, 2), 'bot-message');
                }
                if (data.conseils) {
                    ajouterMessage('Conseils: ' + data.conseils.join(', '), 'bot-message');
                }
                
            } catch (error) {
                ajouterMessage('Assistant: Désolé, une erreur est survenue.', 'bot-message');
            }
        }
        
        function ajouterMessage(texte, classe) {
            const messagesDiv = document.getElementById('chatMessages');
            const messageDiv = document.createElement('div');
            messageDiv.className = 'message ' + classe;
            messageDiv.innerHTML = '<strong>' + texte.split(':')[0] + ':</strong> ' + texte.split(':').slice(1).join(':');
            messagesDiv.appendChild(messageDiv);
            messagesDiv.scrollTop = messagesDiv.scrollHeight;
        }
    </script>
</body>
</html>
    """;
    
    return ResponseEntity.ok(html);
}
}