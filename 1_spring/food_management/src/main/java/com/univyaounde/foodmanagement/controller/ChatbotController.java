package com.univyaounde.foodmanagement.controller;

import com.univyaounde.foodmanagement.service.ChatbotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/chatbot")
@CrossOrigin(origins = "*")
public class ChatbotController {
    
    @Autowired
    private ChatbotService chatbotService;
    
    @PostMapping("/message")
    public ResponseEntity<Map<String, Object>> processerMessage(@RequestBody Map<String, Object> request) {
        try {
            String message = (String) request.get("message");
            Long userId = request.get("userId") != null ? 
                         Long.valueOf(request.get("userId").toString()) : null;
            
            Map<String, Object> response = chatbotService.traiterMessage(message, userId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("erreur", true);
            errorResponse.put("message", "Désolé, je n'ai pas pu traiter votre demande.");
            return ResponseEntity.ok(errorResponse);
        }
    }
    
    @GetMapping("/suggestions/{domaine}")
    public ResponseEntity<Map<String, Object>> obtenirSuggestions(@PathVariable String domaine) {
        try {
            Map<String, Object> suggestions = chatbotService.obtenirSuggestions(domaine);
            return ResponseEntity.ok(suggestions);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("/conseil-nutrition")
    public ResponseEntity<Map<String, Object>> obtenirConseilNutrition(@RequestBody Map<String, Object> criteres) {
        try {
            Map<String, Object> conseil = chatbotService.donnerConseilNutrition(criteres);
            return ResponseEntity.ok(conseil);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}