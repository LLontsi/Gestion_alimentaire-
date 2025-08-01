package com.foodmanagement.controller;

import com.foodmanagement.entity.Food;
import com.foodmanagement.enums.CategorieFood;
import com.foodmanagement.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Contrôleur REST pour la gestion des aliments
 * Expose les API pour toutes les opérations CRUD et fonctionnalités métier
 */
@RestController
@RequestMapping("/foods")
@CrossOrigin(origins = "*")
public class FoodController {

    @Autowired
    private FoodService foodService;

    /**
     * Créer un nouvel aliment
     * POST /api/foods
     */
    @PostMapping
    public ResponseEntity<?> creerAliment(@Valid @RequestBody Food food) {
        try {
            Food nouvelAliment = foodService.creerAliment(food);
            return ResponseEntity.status(HttpStatus.CREATED).body(nouvelAliment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Données invalides",
                "message", e.getMessage()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Erreur de création",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Obtenir un aliment par ID
     * GET /api/foods/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenirAliment(@PathVariable Long id) {
        try {
            Optional<Food> food = foodService.obtenirAlimentParId(id);
            
            if (food.isPresent()) {
                return ResponseEntity.ok(food.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "ID invalide",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Lister tous les aliments
     * GET /api/foods
     */
    @GetMapping
    public ResponseEntity<List<Food>> listerAliments() {
        List<Food> foods = foodService.listerTousLesAliments();
        return ResponseEntity.ok(foods);
    }

    /**
     * Rechercher des aliments par nom
     * GET /api/foods/recherche?nom={nom}
     */
    @GetMapping("/recherche")
    public ResponseEntity<?> rechercherAliments(@RequestParam String nom) {
        try {
            List<Food> foods = foodService.rechercherAlimentsParNom(nom);
            return ResponseEntity.ok(foods);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Paramètre invalide",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Rechercher des aliments par catégorie
     * GET /api/foods/categorie/{categorie}
     */
    @GetMapping("/categorie/{categorie}")
    public ResponseEntity<?> rechercherAlimentsParCategorie(@PathVariable CategorieFood categorie) {
        try {
            List<Food> foods = foodService.rechercherAlimentsParCategorie(categorie);
            return ResponseEntity.ok(foods);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Catégorie invalide",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Rechercher des aliments par créateur
     * GET /api/foods/createur/{personneId}
     */
    @GetMapping("/createur/{personneId}")
    public ResponseEntity<?> rechercherAlimentsParCreateur(@PathVariable Long personneId) {
        try {
            List<Food> foods = foodService.rechercherAlimentsParCreateur(personneId);
            return ResponseEntity.ok(foods);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "ID créateur invalide",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Mettre à jour un aliment
     * PUT /api/foods/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> modifierAliment(@PathVariable Long id, 
                                           @Valid @RequestBody Food food) {
        try {
            if (!id.equals(food.getId())) {
                food.setId(id);
            }
            
            Food alimentModifie = foodService.modifierAliment(food);
            return ResponseEntity.ok(alimentModifie);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Données invalides",
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
     * Supprimer un aliment
     * DELETE /api/foods/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> supprimerAliment(@PathVariable Long id) {
        try {
            boolean supprime = foodService.supprimerAliment(id);
            
            if (supprime) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "ID invalide",
                "message", e.getMessage()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Erreur de suppression",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Ajouter un ingrédient à un aliment
     * POST /api/foods/{foodId}/ingredients
     */
    @PostMapping("/{foodId}/ingredients")
    public ResponseEntity<?> ajouterIngredient(@PathVariable Long foodId,
                                             @RequestBody Map<String, Object> ingredientData) {
        try {
            Long ingredientId = Long.valueOf(ingredientData.get("ingredientId").toString());
            Double quantite = Double.valueOf(ingredientData.get("quantite").toString());
            String unite = ingredientData.get("unite").toString();
            
            boolean ajoute = foodService.ajouterIngredient(foodId, ingredientId, quantite, unite);
            
            if (ajoute) {
                return ResponseEntity.ok(Map.of(
                    "statut", "INGREDIENT_AJOUTE",
                    "message", "Ingrédient ajouté avec succès"
                ));
            } else {
                return ResponseEntity.badRequest().body(Map.of(
                    "erreur", "Échec de l'ajout",
                    "message", "Impossible d'ajouter l'ingrédient"
                ));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Erreur d'ajout",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Supprimer un ingrédient d'un aliment
     * DELETE /api/foods/{foodId}/ingredients/{ingredientId}
     */
    @DeleteMapping("/{foodId}/ingredients/{ingredientId}")
    public ResponseEntity<?> supprimerIngredient(@PathVariable Long foodId,
                                               @PathVariable Long ingredientId) {
        try {
            boolean supprime = foodService.supprimerIngredient(foodId, ingredientId);
            
            if (supprime) {
                return ResponseEntity.ok(Map.of(
                    "statut", "INGREDIENT_SUPPRIME",
                    "message", "Ingrédient supprimé avec succès"
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
     * Calculer les calories totales d'un aliment
     * GET /api/foods/{id}/calories
     */
    @GetMapping("/{id}/calories")
    public ResponseEntity<?> calculerCalories(@PathVariable Long id) {
        try {
            Double calories = foodService.calculerCaloriesTotales(id);
            
            return ResponseEntity.ok(Map.of(
                "food_id", id,
                "calories_totales", calories,
                "date_calcul", LocalDateTime.now()
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "ID invalide",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Rechercher des aliments par tranche de calories
     * GET /api/foods/calories?min={min}&max={max}
     */
    @GetMapping("/calories")
    public ResponseEntity<?> rechercherParCalories(@RequestParam Double min,
                                                  @RequestParam Double max) {
        try {
            List<Food> foods = foodService.rechercherAlimentsParCalories(min, max);
            return ResponseEntity.ok(foods);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Paramètres invalides",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Rechercher des aliments par tranche de prix
     * GET /api/foods/prix?min={min}&max={max}
     */
    @GetMapping("/prix")
    public ResponseEntity<?> rechercherParPrix(@RequestParam Double min,
                                              @RequestParam Double max) {
        try {
            List<Food> foods = foodService.rechercherAlimentsParPrix(min, max);
            return ResponseEntity.ok(foods);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Paramètres invalides",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Obtenir les aliments populaires
     * GET /api/foods/populaires?limite={limite}
     */
    @GetMapping("/populaires")
    public ResponseEntity<?> obtenirAlimentsPopulaires(@RequestParam(defaultValue = "10") int limite) {
        try {
            List<Food> foods = foodService.obtenirAlimentsPopulaires(limite);
            return ResponseEntity.ok(foods);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Limite invalide",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Rechercher des aliments contenant un ingrédient
     * GET /api/foods/ingredient/{ingredientId}
     */
    @GetMapping("/ingredient/{ingredientId}")
    public ResponseEntity<?> rechercherAvecIngredient(@PathVariable Long ingredientId) {
        try {
            List<Food> foods = foodService.rechercherAlimentsAvecIngredient(ingredientId);
            return ResponseEntity.ok(foods);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "ID ingrédient invalide",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Vérifier la disponibilité d'un nom d'aliment
     * GET /api/foods/nom/disponible?nom={nom}&personneId={personneId}
     */
    @GetMapping("/nom/disponible")
    public ResponseEntity<Map<String, Object>> verifierNomDisponible(@RequestParam String nom,
                                                                    @RequestParam Long personneId) {
        boolean disponible = foodService.verifierNomAlimentDisponible(nom, personneId);
        
        return ResponseEntity.ok(Map.of(
            "nom", nom,
            "personne_id", personneId,
            "disponible", disponible
        ));
    }

    /**
     * Dupliquer un aliment
     * POST /api/foods/{id}/dupliquer
     */
    @PostMapping("/{id}/dupliquer")
    public ResponseEntity<?> dupliquerAliment(@PathVariable Long id,
                                            @RequestBody Map<String, Long> data) {
        try {
            Long nouveauCreateurId = data.get("nouveauCreateurId");
            Food alimentDuplique = foodService.dupliquerAliment(id, nouveauCreateurId);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(alimentDuplique);
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
     * Obtenir les statistiques des aliments
     * GET /api/foods/statistiques
     */
    @GetMapping("/statistiques")
    public ResponseEntity<Map<String, Object>> obtenirStatistiques() {
        long nombreTotal = foodService.compterAliments();
        
        Map<String, Object> statistiques = Map.of(
            "nombre_total_aliments", nombreTotal,
            "date_generation", LocalDateTime.now()
        );
        
        return ResponseEntity.ok(statistiques);
    }

    /**
     * Valider les données d'un aliment
     * POST /api/foods/validation
     */
    @PostMapping("/validation")
    public ResponseEntity<?> validerDonneesAliment(@RequestBody Food food) {
        try {
            foodService.validerDonneesAliment(food);
            
            return ResponseEntity.ok(Map.of(
                "statut", "VALIDE",
                "message", "Toutes les données sont valides"
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "statut", "INVALIDE",
                "erreur", "Données invalides",
                "message", e.getMessage()
            ));
        }
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