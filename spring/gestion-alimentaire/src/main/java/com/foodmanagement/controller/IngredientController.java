package com.foodmanagement.controller;

import com.foodmanagement.entity.Ingredient;
import com.foodmanagement.enums.TypeIngredient;
import com.foodmanagement.service.IngredientService;
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
 * Contrôleur REST pour la gestion des ingrédients
 */
@RestController
@RequestMapping("/ingredients")
@CrossOrigin(origins = "*")
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;

    /**
     * Créer un nouvel ingrédient
     * POST /api/ingredients
     */
    @PostMapping
    public ResponseEntity<?> creerIngredient(@Valid @RequestBody Ingredient ingredient) {
        try {
            Ingredient nouvelIngredient = ingredientService.creerIngredient(ingredient);
            return ResponseEntity.status(HttpStatus.CREATED).body(nouvelIngredient);
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
     * Obtenir un ingrédient par ID
     * GET /api/ingredients/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenirIngredient(@PathVariable Long id) {
        try {
            Optional<Ingredient> ingredient = ingredientService.obtenirIngredientParId(id);
            
            if (ingredient.isPresent()) {
                return ResponseEntity.ok(ingredient.get());
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
     * Obtenir un ingrédient par nom
     * GET /api/ingredients/nom/{nom}
     */
    @GetMapping("/nom/{nom}")
    public ResponseEntity<?> obtenirIngredientParNom(@PathVariable String nom) {
        try {
            Optional<Ingredient> ingredient = ingredientService.obtenirIngredientParNom(nom);
            
            if (ingredient.isPresent()) {
                return ResponseEntity.ok(ingredient.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Nom invalide",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Lister tous les ingrédients
     * GET /api/ingredients
     */
    @GetMapping
    public ResponseEntity<List<Ingredient>> listerIngredients() {
        List<Ingredient> ingredients = ingredientService.listerTousLesIngredients();
        return ResponseEntity.ok(ingredients);
    }

    /**
     * Rechercher des ingrédients par nom
     * GET /api/ingredients/recherche?nom={nom}
     */
    @GetMapping("/recherche")
    public ResponseEntity<?> rechercherIngredients(@RequestParam String nom) {
        try {
            List<Ingredient> ingredients = ingredientService.rechercherIngredientsParNom(nom);
            return ResponseEntity.ok(ingredients);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Paramètre invalide",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Rechercher des ingrédients par type
     * GET /api/ingredients/type/{type}
     */
    @GetMapping("/type/{type}")
    public ResponseEntity<?> rechercherIngredientsParType(@PathVariable TypeIngredient type) {
        try {
            List<Ingredient> ingredients = ingredientService.rechercherIngredientsParType(type);
            return ResponseEntity.ok(ingredients);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Type invalide",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Rechercher des ingrédients par unité
     * GET /api/ingredients/unite/{unite}
     */
    @GetMapping("/unite/{unite}")
    public ResponseEntity<?> rechercherIngredientsParUnite(@PathVariable String unite) {
        try {
            List<Ingredient> ingredients = ingredientService.rechercherIngredientsParUnite(unite);
            return ResponseEntity.ok(ingredients);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Unité invalide",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Mettre à jour un ingrédient
     * PUT /api/ingredients/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> modifierIngredient(@PathVariable Long id,
                                              @Valid @RequestBody Ingredient ingredient) {
        try {
            if (!id.equals(ingredient.getId())) {
                ingredient.setId(id);
            }
            
            Ingredient ingredientModifie = ingredientService.modifierIngredient(ingredient);
            return ResponseEntity.ok(ingredientModifie);
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
     * Supprimer un ingrédient
     * DELETE /api/ingredients/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> supprimerIngredient(@PathVariable Long id) {
        try {
            boolean supprime = ingredientService.supprimerIngredient(id);
            
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
     * Rechercher des ingrédients par tranche de quantité
     * GET /api/ingredients/quantite?min={min}&max={max}
     */
    @GetMapping("/quantite")
    public ResponseEntity<?> rechercherParQuantite(@RequestParam Double min,
                                                  @RequestParam Double max) {
        try {
            List<Ingredient> ingredients = ingredientService.rechercherIngredientsParQuantite(min, max);
            return ResponseEntity.ok(ingredients);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Paramètres invalides",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Lister les ingrédients utilisés dans des aliments
     * GET /api/ingredients/utilises
     */
    @GetMapping("/utilises")
    public ResponseEntity<List<Ingredient>> listerIngredientsUtilises() {
        List<Ingredient> ingredients = ingredientService.listerIngredientsUtilises();
        return ResponseEntity.ok(ingredients);
    }

    /**
     * Lister les ingrédients non utilisés
     * GET /api/ingredients/non-utilises
     */
    @GetMapping("/non-utilises")
    public ResponseEntity<List<Ingredient>> listerIngredientsNonUtilises() {
        List<Ingredient> ingredients = ingredientService.listerIngredientsNonUtilises();
        return ResponseEntity.ok(ingredients);
    }

    /**
     * Obtenir les ingrédients populaires
     * GET /api/ingredients/populaires?limite={limite}
     */
    @GetMapping("/populaires")
    public ResponseEntity<?> obtenirIngredientsPopulaires(@RequestParam(defaultValue = "10") int limite) {
        try {
            List<Ingredient> ingredients = ingredientService.obtenirIngredientsPopulaires(limite);
            return ResponseEntity.ok(ingredients);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Limite invalide",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Rechercher les ingrédients d'un aliment
     * GET /api/ingredients/aliment/{foodId}
     */
    @GetMapping("/aliment/{foodId}")
    public ResponseEntity<?> rechercherIngredientsParAliment(@PathVariable Long foodId) {
        try {
            List<Ingredient> ingredients = ingredientService.rechercherIngredientsParAliment(foodId);
            return ResponseEntity.ok(ingredients);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "ID aliment invalide",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Vérifier la disponibilité d'un nom d'ingrédient
     * GET /api/ingredients/nom/disponible?nom={nom}
     */
    @GetMapping("/nom/disponible")
    public ResponseEntity<Map<String, Object>> verifierNomDisponible(@RequestParam String nom) {
        boolean disponible = ingredientService.verifierNomIngredientDisponible(nom);
        
        return ResponseEntity.ok(Map.of(
            "nom", nom,
            "disponible", disponible
        ));
    }

    /**
     * Obtenir des suggestions d'ingrédients
     * GET /api/ingredients/suggestions?nom={nom}&type={type}
     */
    @GetMapping("/suggestions")
    public ResponseEntity<?> suggererIngredients(@RequestParam String nom,
                                                @RequestParam(required = false) TypeIngredient type) {
        try {
            List<Ingredient> suggestions = ingredientService.suggererIngredients(nom, type);
            return ResponseEntity.ok(suggestions);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Paramètres invalides",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Obtenir les statistiques des ingrédients
     * GET /api/ingredients/statistiques
     */
    @GetMapping("/statistiques")
    public ResponseEntity<Map<String, Object>> obtenirStatistiques() {
        long nombreTotal = ingredientService.compterIngredients();
        
        // Statistiques par type
        Map<String, Long> statistiquesParType = Map.of(
            "FRAIS", ingredientService.compterIngredientsParType(TypeIngredient.FRAIS),
            "SURGELE", ingredientService.compterIngredientsParType(TypeIngredient.SURGELE),
            "CONSERVE", ingredientService.compterIngredientsParType(TypeIngredient.CONSERVE),
            "SEC", ingredientService.compterIngredientsParType(TypeIngredient.SEC)
        );
        
        Map<String, Object> statistiques = Map.of(
            "nombre_total_ingredients", nombreTotal,
            "repartition_par_type", statistiquesParType,
            "date_generation", LocalDateTime.now()
        );
        
        return ResponseEntity.ok(statistiques);
    }

    /**
     * Compter les ingrédients par type
     * GET /api/ingredients/statistiques/type/{type}
     */
    @GetMapping("/statistiques/type/{type}")
    public ResponseEntity<?> compterIngredientsParType(@PathVariable TypeIngredient type) {
        try {
            long count = ingredientService.compterIngredientsParType(type);
            
            return ResponseEntity.ok(Map.of(
                "type", type,
                "nombre_ingredients", count
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Type invalide",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Valider les données d'un ingrédient
     * POST /api/ingredients/validation
     */
    @PostMapping("/validation")
    public ResponseEntity<?> validerDonneesIngredient(@RequestBody Ingredient ingredient) {
        try {
            ingredientService.validerDonneesIngredient(ingredient);
            
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