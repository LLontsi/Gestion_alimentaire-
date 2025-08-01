package com.univyaounde.foodmanagement.controller;

import com.univyaounde.foodmanagement.service.PlanificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/planification")
@CrossOrigin(origins = "*")
public class PlanificationController {
    
    @Autowired
    private PlanificationService planificationService;
    
    @PostMapping("/plan-hebdomadaire")
    public ResponseEntity<Map<String, Object>> creerPlanHebdomadaire(
            @RequestParam Long personneId,
            @RequestParam LocalDate dateDebut) {
        try {
            Map<String, Object> plan = planificationService.creerPlanHebdomadaire(personneId, dateDebut);
            return ResponseEntity.ok(plan);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/recommandations")
    public ResponseEntity<?> obtenirRecommandations(
            @RequestParam Long personneId,
            @RequestParam String typeRepas) {
        try {
            return ResponseEntity.ok(planificationService.obtenirRecommandations(personneId, typeRepas));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("/analyser")
    public ResponseEntity<Map<String, Object>> analyserPlan(@RequestBody Map<String, Object> plan) {
        try {
            Map<String, Object> analyse = planificationService.analyserPlanNutritionnel(plan);
            return ResponseEntity.ok(analyse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}