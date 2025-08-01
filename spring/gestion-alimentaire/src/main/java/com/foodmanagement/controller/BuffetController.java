package com.foodmanagement.controller;

import com.foodmanagement.entity.Food;
import com.foodmanagement.enums.CategorieFood;
import com.foodmanagement.service.BuffetService;
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
 * Contrôleur REST pour la gestion de buffets
 * Module métier pour l'exercice 3 - Gestion de buffets pour cérémonies
 */
@RestController
@RequestMapping("/buffets")
@CrossOrigin(origins = "*")
public class BuffetController {

    @Autowired
    private BuffetService buffetService;

    /**
     * Créer un nouveau buffet
     * POST /api/buffets
     */
    @PostMapping
    public ResponseEntity<?> creerBuffet(@RequestBody Map<String, Object> buffetData) {
        try {
            Long organisateurId = Long.valueOf(buffetData.get("organisateurId").toString());
            String nomEvenement = buffetData.get("nomEvenement").toString();
            LocalDateTime dateEvenement = LocalDateTime.parse(buffetData.get("dateEvenement").toString());
            Integer nombreInvites = Integer.valueOf(buffetData.get("nombreInvites").toString());
            String typeEvenement = buffetData.get("typeEvenement").toString();
            
            Double budget = null;
            if (buffetData.containsKey("budget") && buffetData.get("budget") != null) {
                budget = Double.valueOf(buffetData.get("budget").toString());
            }
            
            Long buffetId = buffetService.creerBuffet(organisateurId, nomEvenement, dateEvenement, 
                                                    nombreInvites, typeEvenement, budget);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "statut", "BUFFET_CREE",
                "message", "Buffet créé avec succès",
                "buffet_id", buffetId,
                "organisateur_id", organisateurId,
                "nom_evenement", nomEvenement,
                "date_evenement", dateEvenement,
                "nombre_invites", nombreInvites,
                "type_evenement", typeEvenement,
                "budget", budget
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Paramètres invalides",
                "message", e.getMessage()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Erreur de création du buffet",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Planifier automatiquement un buffet
     * POST /api/buffets/{buffetId}/planification-automatique
     */
    @PostMapping("/{buffetId}/planification-automatique")
    public ResponseEntity<?> planifierBuffetAutomatique(@PathVariable Long buffetId,
                                                       @RequestBody(required = false) Map<String, Object> data) {
        try {
            List<CategorieFood> preferences = null;
            if (data != null && data.containsKey("preferences")) {
                List<String> preferencesStr = (List<String>) data.get("preferences");
                preferences = preferencesStr.stream()
                    .map(CategorieFood::valueOf)
                    .toList();
            }
            
            Map<CategorieFood, List<Map<String, Object>>> planBuffet = 
                buffetService.planifierBuffetAutomatique(buffetId, preferences);
            
            return ResponseEntity.ok(Map.of(
                "statut", "BUFFET_PLANIFIE",
                "message", "Buffet planifié automatiquement avec succès",
                "buffet_id", buffetId,
                "plan_buffet", planBuffet,
                "preferences_appliquees", preferences
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Paramètres invalides",
                "message", e.getMessage()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Erreur de planification",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Ajouter un aliment au buffet
     * POST /api/buffets/{buffetId}/aliments
     */
    /**
     * Ajouter un aliment au buffet
     * POST /api/buffets/{buffetId}/aliments
     */
    @PostMapping("/{buffetId}/aliments")
    public ResponseEntity<?> ajouterAlimentAuBuffet(@PathVariable Long buffetId,
                                                   @RequestBody Map<String, Object> alimentData) {
        try {
            Long foodId = Long.valueOf(alimentData.get("foodId").toString());
            Integer quantitePersonnes = Integer.valueOf(alimentData.get("quantitePersonnes").toString());
            Integer priorite = Integer.valueOf(alimentData.get("priorite").toString());
            
            boolean ajoute = buffetService.ajouterAlimentAuBuffet(buffetId, foodId, quantitePersonnes, priorite);
            
            if (ajoute) {
                return ResponseEntity.ok(Map.of(
                    "statut", "ALIMENT_AJOUTE",
                    "message", "Aliment ajouté au buffet avec succès",
                    "buffet_id", buffetId,
                    "food_id", foodId,
                    "quantite_personnes", quantitePersonnes,
                    "priorite", priorite
                ));
            } else {
                return ResponseEntity.badRequest().body(Map.of(
                    "erreur", "Échec de l'ajout",
                    "message", "Impossible d'ajouter l'aliment au buffet"
                ));
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Paramètres invalides",
                "message", e.getMessage()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Erreur d'ajout",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Supprimer un aliment du buffet
     * DELETE /api/buffets/{buffetId}/aliments/{foodId}
     */
    @DeleteMapping("/{buffetId}/aliments/{foodId}")
    public ResponseEntity<?> supprimerAlimentDuBuffet(@PathVariable Long buffetId,
                                                     @PathVariable Long foodId) {
        try {
            boolean supprime = buffetService.supprimerAlimentDuBuffet(buffetId, foodId);
            
            if (supprime) {
                return ResponseEntity.ok(Map.of(
                    "statut", "ALIMENT_SUPPRIME",
                    "message", "Aliment supprimé du buffet avec succès"
                ));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Erreur de suppression",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Modifier la quantité d'un aliment dans le buffet
     * PUT /api/buffets/{buffetId}/aliments/{foodId}/quantite
     */
    @PutMapping("/{buffetId}/aliments/{foodId}/quantite")
    public ResponseEntity<?> modifierQuantiteAliment(@PathVariable Long buffetId,
                                                    @PathVariable Long foodId,
                                                    @RequestBody Map<String, Integer> data) {
        try {
            Integer nouvelleQuantite = data.get("nouvelleQuantitePersonnes");
            
            boolean modifie = buffetService.modifierQuantiteAliment(buffetId, foodId, nouvelleQuantite);
            
            if (modifie) {
                return ResponseEntity.ok(Map.of(
                    "statut", "QUANTITE_MODIFIEE",
                    "message", "Quantité modifiée avec succès",
                    "buffet_id", buffetId,
                    "food_id", foodId,
                    "nouvelle_quantite", nouvelleQuantite
                ));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Quantité invalide",
                "message", e.getMessage()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Erreur de modification",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Calculer les quantités nécessaires pour le buffet
     * GET /api/buffets/{buffetId}/quantites
     */
    @GetMapping("/{buffetId}/quantites")
    public ResponseEntity<?> calculerQuantitesNecessaires(@PathVariable Long buffetId) {
        try {
            Map<String, Map<String, Object>> quantites = buffetService.calculerQuantitesNecessaires(buffetId);
            
            return ResponseEntity.ok(Map.of(
                "buffet_id", buffetId,
                "quantites", quantites,
                "date_calcul", LocalDateTime.now()
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "ID buffet invalide",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Estimer le coût total du buffet
     * GET /api/buffets/{buffetId}/cout
     */
    @GetMapping("/{buffetId}/cout")
    public ResponseEntity<?> estimerCoutBuffet(@PathVariable Long buffetId) {
        try {
            Double cout = buffetService.estimerCoutBuffet(buffetId);
            
            return ResponseEntity.ok(Map.of(
                "buffet_id", buffetId,
                "cout_estime", cout,
                "devise", "EUR",
                "date_calcul", LocalDateTime.now()
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "ID buffet invalide",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Vérifier l'équilibre nutritionnel du buffet
     * GET /api/buffets/{buffetId}/equilibre-nutritionnel
     */
    @GetMapping("/{buffetId}/equilibre-nutritionnel")
    public ResponseEntity<?> verifierEquilibreNutritionnel(@PathVariable Long buffetId) {
        try {
            Map<String, Object> rapport = buffetService.verifierEquilibreNutritionnel(buffetId);
            
            return ResponseEntity.ok(Map.of(
                "buffet_id", buffetId,
                "rapport_nutritionnel", rapport,
                "date_analyse", LocalDateTime.now()
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "ID buffet invalide",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Générer une liste de courses pour le buffet
     * GET /api/buffets/{buffetId}/liste-courses
     */
    @GetMapping("/{buffetId}/liste-courses")
    public ResponseEntity<?> genererListeCoursesBuffet(@PathVariable Long buffetId) {
        try {
            Map<String, Double> listeCourses = buffetService.genererListeCoursesBuffet(buffetId);
            
            return ResponseEntity.ok(Map.of(
                "buffet_id", buffetId,
                "liste_courses", listeCourses,
                "nombre_ingredients", listeCourses.size(),
                "date_generation", LocalDateTime.now()
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "ID buffet invalide",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Obtenir des suggestions d'aliments pour un type d'événement
     * GET /api/buffets/suggestions?typeEvenement={type}&nombreInvites={nombre}&budget={budget}
     */
    @GetMapping("/suggestions")
    public ResponseEntity<?> obtenirSuggestionsParEvenement(@RequestParam String typeEvenement,
                                                          @RequestParam Integer nombreInvites,
                                                          @RequestParam(required = false) Double budget) {
        try {
            Map<CategorieFood, List<Food>> suggestions = 
                buffetService.obtenirSuggestionsParEvenement(typeEvenement, nombreInvites, budget);
            
            return ResponseEntity.ok(Map.of(
                "type_evenement", typeEvenement,
                "nombre_invites", nombreInvites,
                "budget", budget,
                "suggestions", suggestions
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Paramètres invalides",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Calculer les portions standards
     * GET /api/buffets/portions?categorie={categorie}&typeEvenement={type}
     */
    @GetMapping("/portions")
    public ResponseEntity<?> calculerPortionStandard(@RequestParam CategorieFood categorie,
                                                    @RequestParam String typeEvenement) {
        try {
            Double portion = buffetService.calculerPortionStandard(categorie, typeEvenement);
            
            return ResponseEntity.ok(Map.of(
                "categorie", categorie,
                "type_evenement", typeEvenement,
                "portion_standard_grammes", portion,
                "unite", "grammes par personne"
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Paramètres invalides",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Optimiser le buffet selon le budget
     * POST /api/buffets/{buffetId}/optimisation
     */
    @PostMapping("/{buffetId}/optimisation")
    public ResponseEntity<?> optimiserBuffetParBudget(@PathVariable Long buffetId,
                                                     @RequestBody Map<String, Double> data) {
        try {
            Double budgetMax = data.get("budgetMax");
            
            Map<String, Object> optimisation = buffetService.optimiserBuffetParBudget(buffetId, budgetMax);
            
            return ResponseEntity.ok(Map.of(
                "buffet_id", buffetId,
                "optimisation", optimisation,
                "date_optimisation", LocalDateTime.now()
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Budget invalide",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Planifier les temps de préparation
     * GET /api/buffets/{buffetId}/planning-preparation
     */
    @GetMapping("/{buffetId}/planning-preparation")
    public ResponseEntity<?> planifierTempsPreparation(@PathVariable Long buffetId) {
        try {
            Map<String, Object> planning = buffetService.planifierTempsPreparation(buffetId);
            
            return ResponseEntity.ok(Map.of(
                "buffet_id", buffetId,
                "planning_preparation", planning,
                "date_generation", LocalDateTime.now()
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "ID buffet invalide",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Dupliquer un buffet existant
     * POST /api/buffets/{buffetId}/dupliquer
     */
    @PostMapping("/{buffetId}/dupliquer")
    public ResponseEntity<?> dupliquerBuffet(@PathVariable Long buffetId,
                                           @RequestBody Map<String, Object> data) {
        try {
            String nouveauNom = data.get("nouveauNom").toString();
            LocalDateTime nouvelleDatete = LocalDateTime.parse(data.get("nouvelleDatete").toString());
            Integer nouveauNombreInvites = Integer.valueOf(data.get("nouveauNombreInvites").toString());
            
            Long nouveauBuffetId = buffetService.dupliquerBuffet(buffetId, nouveauNom, 
                                                               nouvelleDatete, nouveauNombreInvites);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "statut", "BUFFET_DUPLIQUE",
                "message", "Buffet dupliqué avec succès",
                "buffet_original_id", buffetId,
                "nouveau_buffet_id", nouveauBuffetId,
                "nouveau_nom", nouveauNom,
                "nouvelle_date", nouvelleDatete,
                "nouveau_nombre_invites", nouveauNombreInvites
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
     * Générer un rapport détaillé du buffet
     * GET /api/buffets/{buffetId}/rapport
     */
    @GetMapping("/{buffetId}/rapport")
    public ResponseEntity<?> genererRapportBuffet(@PathVariable Long buffetId) {
        try {
            Map<String, Object> rapport = buffetService.genererRapportBuffet(buffetId);
            
            return ResponseEntity.ok(Map.of(
                "buffet_id", buffetId,
                "rapport_detaille", rapport
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "ID buffet invalide",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Marquer un buffet comme réalisé
     * POST /api/buffets/{buffetId}/realiser
     */
    @PostMapping("/{buffetId}/realiser")
    public ResponseEntity<?> marquerBuffetRealise(@PathVariable Long buffetId,
                                                 @RequestBody Map<String, Object> data) {
        try {
            Integer nombreInvitesReel = Integer.valueOf(data.get("nombreInvitesReel").toString());
            String commentaires = data.get("commentaires") != null ? data.get("commentaires").toString() : "";
            
            boolean marque = buffetService.marquerBuffetRealise(buffetId, nombreInvitesReel, commentaires);
            
            if (marque) {
                return ResponseEntity.ok(Map.of(
                    "statut", "BUFFET_REALISE",
                    "message", "Buffet marqué comme réalisé avec succès",
                    "buffet_id", buffetId,
                    "nombre_invites_reel", nombreInvitesReel,
                    "date_realisation", LocalDateTime.now()
                ));
            } else {
                return ResponseEntity.badRequest().body(Map.of(
                    "erreur", "Échec du marquage",
                    "message", "Impossible de marquer le buffet comme réalisé"
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
     * Obtenir l'historique des buffets d'un organisateur
     * GET /api/buffets/organisateur/{organisateurId}/historique
     */
    @GetMapping("/organisateur/{organisateurId}/historique")
    public ResponseEntity<?> obtenirHistoriqueBuffets(@PathVariable Long organisateurId) {
        try {
            List<Map<String, Object>> historique = buffetService.obtenirHistoriqueBuffets(organisateurId);
            
            return ResponseEntity.ok(Map.of(
                "organisateur_id", organisateurId,
                "historique_buffets", historique,
                "nombre_buffets", historique.size()
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "ID organisateur invalide",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Calculer les statistiques d'un organisateur
     * GET /api/buffets/organisateur/{organisateurId}/statistiques
     */
    @GetMapping("/organisateur/{organisateurId}/statistiques")
    public ResponseEntity<?> calculerStatistiquesOrganisateur(@PathVariable Long organisateurId) {
        try {
            Map<String, Object> statistiques = buffetService.calculerStatistiquesOrganisateur(organisateurId);
            
            return ResponseEntity.ok(Map.of(
                "organisateur_id", organisateurId,
                "statistiques", statistiques,
                "date_generation", LocalDateTime.now()
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "ID organisateur invalide",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Valider la faisabilité d'un buffet
     * GET /api/buffets/{buffetId}/validation
     */
    @GetMapping("/{buffetId}/validation")
    public ResponseEntity<?> validerFaisabiliteBuffet(@PathVariable Long buffetId) {
        try {
            List<String> problemes = buffetService.validerFaisabiliteBuffet(buffetId);
            
            if (problemes.isEmpty()) {
                return ResponseEntity.ok(Map.of(
                    "statut", "BUFFET_VALIDE",
                    "message", "Le buffet est faisable",
                    "buffet_id", buffetId,
                    "problemes", problemes
                ));
            } else {
                return ResponseEntity.ok(Map.of(
                    "statut", "BUFFET_AVEC_PROBLEMES",
                    "message", "Le buffet contient des problèmes à résoudre",
                    "buffet_id", buffetId,
                    "problemes", problemes,
                    "nombre_problemes", problemes.size()
                ));
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "ID buffet invalide",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Obtenir les types d'événements supportés
     * GET /api/buffets/types-evenements
     */
    @GetMapping("/types-evenements")
    public ResponseEntity<Map<String, Object>> obtenirTypesEvenements() {
        List<String> typesEvenements = List.of("MARIAGE", "ANNIVERSAIRE", "ENTREPRISE", "COCKTAIL");
        
        Map<String, Object> details = Map.of(
            "MARIAGE", Map.of(
                "description", "Mariage avec repas complet",
                "portions_multiplicateur", 1.5,
                "cout_moyen_personne", 45.0
            ),
            "ANNIVERSAIRE", Map.of(
                "description", "Fête d'anniversaire",
                "portions_multiplicateur", 1.2,
                "cout_moyen_personne", 25.0
            ),
            "ENTREPRISE", Map.of(
                "description", "Événement d'entreprise",
                "portions_multiplicateur", 1.0,
                "cout_moyen_personne", 30.0
            ),
            "COCKTAIL", Map.of(
                "description", "Cocktail apéritif",
                "portions_multiplicateur", 0.8,
                "cout_moyen_personne", 20.0
            )
        );
        
        return ResponseEntity.ok(Map.of(
            "types_evenements", typesEvenements,
            "details", details,
            "nombre_types", typesEvenements.size()
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
            "timestamp", LocalDateTime.now()
        ));
    }
}