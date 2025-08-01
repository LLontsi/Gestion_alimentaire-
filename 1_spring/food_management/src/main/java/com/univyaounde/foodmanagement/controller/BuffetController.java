package com.univyaounde.foodmanagement.controller;

import com.univyaounde.foodmanagement.service.BuffetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/buffet")
@CrossOrigin(origins = "*")
public class BuffetController {
    
    @Autowired
    private BuffetService buffetService;
    
    @PostMapping("/creer")
    public ResponseEntity<Map<String, Object>> creerBuffet(
            @RequestParam int nombreInvites,
            @RequestParam String typeCeremonie,
            @RequestParam Double budgetEstime,
            @RequestParam Long organisateurId) {
        try {
            Map<String, Object> buffet = buffetService.creerBuffetCeremonie(
                nombreInvites, typeCeremonie, budgetEstime, organisateurId);
            return ResponseEntity.ok(buffet);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/modeles")
    public ResponseEntity<?> obtenirModeles() {
        try {
            return ResponseEntity.ok(buffetService.obtenirModelesBuffet());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("/optimiser")
    public ResponseEntity<Map<String, Object>> optimiserBuffet(
            @RequestBody Map<String, Object> buffet,
            @RequestParam Double budgetMax) {
        try {
            Map<String, Object> buffetOptimise = buffetService.optimiserBuffet(buffet, budgetMax);
            return ResponseEntity.ok(buffetOptimise);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}