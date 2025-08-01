package com.foodmanagement.controller;

import com.foodmanagement.entity.Food;
import com.foodmanagement.enums.CategorieFood;
import com.foodmanagement.service.PlanificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Contrôleur REST pour la planification des repas
 * Module métier pour l'exercice 3 - Planification hebdomadaire des repas
 */
@RestController
@RequestMapping("/planification")
@CrossOrigin(origins = "*")
public class PlanificationController {

    @Autowired
    private PlanificationService planificationService;

    /**
     * Créer un plan de repas hebdomadaire
     * POST /api/planification/plan-hebdomadaire
     * 
     * @param planData Données du plan (personneId, dateDebut, preferences)
     * @return Plan de repas organisé par jour
     */
    @PostMapping("/plan-hebdomadaire")
    public ResponseEntity<?> creerPlanHebdomadaire(@RequestBody Map<String, Object> planData) {
        try {
            Long personneId = Long.valueOf(planData.get("personneId").toString());
            LocalDate dateDebut = LocalDate.parse(planData.get("dateDebut").toString());
            
            // Extraction des préférences (optionnelles)
            List<CategorieFood> preferences = null;
            if (planData.containsKey("preferences") && planData.get("preferences") != null) {
                List<String> preferencesStr = (List<String>) planData.get("preferences");
                preferences = preferencesStr.stream()
                    .map(CategorieFood::valueOf)
                    .toList();
            }
            
            Map<LocalDate, Map<String, List<Food>>> plan = 
                planificationService.creerPlanHebdomadaire(personneId, dateDebut, preferences);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "statut", "PLAN_CREE",
                "message", "Plan hebdomadaire créé avec succès",
                "plan", plan,
                "personne_id", personneId,
                "date_debut", dateDebut,
                "date_creation", LocalDateTime.now()
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Paramètres invalides",
                "message", e.getMessage()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Erreur de création du plan",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Générer un plan automatique
     * POST /api/planification/plan-automatique
     */
    @PostMapping("/plan-automatique")
    public ResponseEntity<?> genererPlanAutomatique(@RequestBody Map<String, Object> planData) {
        try {
            Long personneId = Long.valueOf(planData.get("personneId").toString());
            LocalDate dateDebut = LocalDate.parse(planData.get("dateDebut").toString());
            Integer nombreJours = Integer.valueOf(planData.get("nombreJours").toString());
            
            Map<LocalDate, Map<String, List<Food>>> plan = 
                planificationService.genererPlanAutomatique(personneId, dateDebut, nombreJours);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "statut", "PLAN_AUTOMATIQUE_GENERE",
                "message", "Plan automatique généré avec succès",
                "plan", plan,
                "personne_id", personneId,
                "date_debut", dateDebut,
                "nombre_jours", nombreJours
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Paramètres invalides",
                "message", e.getMessage()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Erreur de génération du plan",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Ajouter un aliment à un repas spécifique
     * POST /api/planification/ajouter-aliment
     */
    @PostMapping("/ajouter-aliment")
    public ResponseEntity<?> ajouterAlimentAuPlan(@RequestBody Map<String, Object> data) {
        try {
            Long personneId = Long.valueOf(data.get("personneId").toString());
            LocalDate date = LocalDate.parse(data.get("date").toString());
            String typeRepas = data.get("typeRepas").toString();
            Long foodId = Long.valueOf(data.get("foodId").toString());
            
            boolean ajoute = planificationService.ajouterAlimentAuPlan(personneId, date, typeRepas, foodId);
            
            if (ajoute) {
                return ResponseEntity.ok(Map.of(
                    "statut", "ALIMENT_AJOUTE",
                    "message", "Aliment ajouté au plan avec succès",
                    "personne_id", personneId,
                    "date", date,
                    "type_repas", typeRepas,
                    "food_id", foodId
                ));
            } else {
                return ResponseEntity.badRequest().body(Map.of(
                    "erreur", "Échec de l'ajout",
                    "message", "Impossible d'ajouter l'aliment au plan"
                ));
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Paramètres invalides",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Supprimer un aliment d'un repas
     * DELETE /api/planification/supprimer-aliment
     */
    @DeleteMapping("/supprimer-aliment")
    public ResponseEntity<?> supprimerAlimentDuPlan(@RequestBody Map<String, Object> data) {
        try {
            Long personneId = Long.valueOf(data.get("personneId").toString());
            LocalDate date = LocalDate.parse(data.get("date").toString());
            String typeRepas = data.get("typeRepas").toString();
            Long foodId = Long.valueOf(data.get("foodId").toString());
            
            boolean supprime = planificationService.supprimerAlimentDuPlan(personneId, date, typeRepas, foodId);
            
            if (supprime) {
                return ResponseEntity.ok(Map.of(
                    "statut", "ALIMENT_SUPPRIME",
                    "message", "Aliment supprimé du plan avec succès"
                ));
            } else {
                return ResponseEntity.badRequest().body(Map.of(
                    "erreur", "Échec de la suppression",
                    "message", "Impossible de supprimer l'aliment du plan"
                ));
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Paramètres invalides",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Obtenir le plan de repas pour une période
     * GET /api/planification/{personneId}/plan?debut={debut}&fin={fin}
     */
    @GetMapping("/{personneId}/plan")
    public ResponseEntity<?> obtenirPlanRepas(@PathVariable Long personneId,
                                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
                                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        try {
            Map<LocalDate, Map<String, List<Food>>> plan = 
                planificationService.obtenirPlanRepas(personneId, debut, fin);
            
            return ResponseEntity.ok(Map.of(
                "plan", plan,
                "personne_id", personneId,
                "periode_debut", debut,
                "periode_fin", fin
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Paramètres invalides",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Calculer les calories d'un jour
     * POST /api/planification/calories-jour
     */
    @PostMapping("/calories-jour")
    public ResponseEntity<?> calculerCaloriesJour(@RequestBody Map<String, List<Food>> planJour) {
        try {
            Double calories = planificationService.calculerCaloriesJour(planJour);
            
            return ResponseEntity.ok(Map.of(
                "calories_totales", calories,
                "date_calcul", LocalDateTime.now()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Erreur de calcul",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Calculer les calories d'une semaine
     * POST /api/planification/calories-semaine
     */
    @PostMapping("/calories-semaine")
    public ResponseEntity<?> calculerCaloriesSemaine(@RequestBody Map<LocalDate, Map<String, List<Food>>> planSemaine) {
        try {
            Map<String, Double> calories = planificationService.calculerCaloriesSemaine(planSemaine);
            
            return ResponseEntity.ok(Map.of(
                "calories_detaillees", calories,
                "date_calcul", LocalDateTime.now()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Erreur de calcul",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Suggérer des alternatives pour un aliment
     * GET /api/planification/alternatives/{foodId}?categorie={categorie}
     */
    @GetMapping("/alternatives/{foodId}")
    public ResponseEntity<?> suggererAlternatives(@PathVariable Long foodId,
                                                 @RequestParam(required = false) CategorieFood categorie) {
        try {
            // Récupération de l'aliment original via le service Food
            // Dans un vrai projet, on injecterait FoodService
            Food alimentOriginal = new Food(); // Simulation
            alimentOriginal.setId(foodId);
            
            List<Food> alternatives = planificationService.suggererAlternatives(alimentOriginal, categorie);
            
            return ResponseEntity.ok(Map.of(
                "food_id_original", foodId,
                "categorie_preferee", categorie,
                "alternatives", alternatives,
                "nombre_alternatives", alternatives.size()
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Paramètres invalides",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Valider un plan de repas
     * POST /api/planification/validation
     */
    @PostMapping("/validation")
    public ResponseEntity<?> validerPlanRepas(@RequestBody Map<LocalDate, Map<String, List<Food>>> planSemaine) {
        try {
            List<String> problemes = planificationService.validerPlanRepas(planSemaine);
            
            if (problemes.isEmpty()) {
                return ResponseEntity.ok(Map.of(
                    "statut", "PLAN_VALIDE",
                    "message", "Le plan de repas est valide",
                    "problemes", problemes
                ));
            } else {
                return ResponseEntity.ok(Map.of(
                    "statut", "PLAN_AVEC_PROBLEMES",
                    "message", "Le plan contient des problèmes à corriger",
                    "problemes", problemes,
                    "nombre_problemes", problemes.size()
                ));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Erreur de validation",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Dupliquer un plan de semaine
     * POST /api/planification/dupliquer
     */
    @PostMapping("/dupliquer")
    public ResponseEntity<?> dupliquerPlanSemaine(@RequestBody Map<String, Object> data) {
        try {
            Long personneId = Long.valueOf(data.get("personneId").toString());
            LocalDate semaineSources = LocalDate.parse(data.get("semaineSources").toString());
            LocalDate semaineDestination = LocalDate.parse(data.get("semaineDestination").toString());
            
            Map<LocalDate, Map<String, List<Food>>> planDuplique = 
                planificationService.dupliquerPlanSemaine(personneId, semaineSources, semaineDestination);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "statut", "PLAN_DUPLIQUE",
                "message", "Plan de semaine dupliqué avec succès",
                "plan_duplique", planDuplique,
                "personne_id", personneId,
                "semaine_source", semaineSources,
                "semaine_destination", semaineDestination
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Paramètres invalides",
                "message", e.getMessage()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Erreur de duplication",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Générer une liste de courses
     * POST /api/planification/liste-courses
     */
    @PostMapping("/liste-courses")
    public ResponseEntity<?> genererListeCourses(@RequestBody Map<LocalDate, Map<String, List<Food>>> planSemaine) {
        try {
            Map<String, Double> listeCourses = planificationService.genererListeCourses(planSemaine);
            
            return ResponseEntity.ok(Map.of(
                "statut", "LISTE_GENEREE",
                "message", "Liste de courses générée avec succès",
                "liste_courses", listeCourses,
                "nombre_ingredients", listeCourses.size(),
                "date_generation", LocalDateTime.now()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Erreur de génération",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Obtenir les statistiques de planification
     * GET /api/planification/{personneId}/statistiques?mois={mois}
     */
    @GetMapping("/{personneId}/statistiques")
    public ResponseEntity<?> obtenirStatistiquesPlanification(@PathVariable Long personneId,
                                                             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate mois) {
        try {
            Map<String, Object> statistiques = 
                planificationService.obtenirStatistiquesPlanification(personneId, mois);
            
            return ResponseEntity.ok(Map.of(
                "statistiques", statistiques,
                "personne_id", personneId,
                "mois", mois,
                "date_generation", LocalDateTime.now()
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Paramètres invalides",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Obtenir les types de repas disponibles
     * GET /api/planification/types-repas
     */
    @GetMapping("/types-repas")
    public ResponseEntity<Map<String, Object>> obtenirTypesRepas() {
        List<String> typesRepas = List.of("Petit-déjeuner", "Déjeuner", "Dîner", "Collation");
        
        return ResponseEntity.ok(Map.of(
            "types_repas", typesRepas,
            "nombre_types", typesRepas.size()
        ));
    }

    /**
     * Obtenir un modèle de plan vide
     * GET /api/planification/modele-plan?dateDebut={dateDebut}&nombreJours={nombreJours}
     */
    @GetMapping("/modele-plan")
    public ResponseEntity<Map<String, Object>> obtenirModelePlan(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam(defaultValue = "7") int nombreJours) {
        
        Map<String, Object> modele = Map.of(
            "date_debut", dateDebut,
            "nombre_jours", nombreJours,
            "structure", Map.of(
                "date", "LocalDate",
                "repas", Map.of(
                    "Petit-déjeuner", "List<Food>",
                    "Déjeuner", "List<Food>",
                    "Dîner", "List<Food>",
                    "Collation", "List<Food>"
                )
            )
        );
        
        return ResponseEntity.ok(modele);
    }

    /**
     * Gestion globale des erreurs pour ce contrôleur
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> gererErreurGenerique(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
            "erreur", "Erreur interne du serveur",
            "message", e.getMessage(),
            "timestamp", LocalDateTime.now()
        ));
    }
}