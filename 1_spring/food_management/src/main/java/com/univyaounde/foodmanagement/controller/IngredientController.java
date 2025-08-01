package com.univyaounde.foodmanagement.controller;

import com.univyaounde.foodmanagement.dto.IngredientDTO;
import com.univyaounde.foodmanagement.model.Ingredient;
import com.univyaounde.foodmanagement.model.Ingredient.TypeIngredient;
import com.univyaounde.foodmanagement.service.IngredientService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/ingredients")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class IngredientController {
    
    private static final Logger logger = LoggerFactory.getLogger(IngredientController.class);
    
    @Autowired
    private IngredientService ingredientService;
    
    // GET - Obtenir tous les ingrédients
    @GetMapping
    public ResponseEntity<List<IngredientDTO>> getAllIngredients() {
        logger.info("GET /api/ingredients - Récupération de tous les ingrédients");
        try {
            List<Ingredient> ingredients = ingredientService.getAllIngredients();
            List<IngredientDTO> ingredientDTOs = new ArrayList<>();
            
            for (Ingredient ingredient : ingredients) {
                IngredientDTO dto = convertToDTO(ingredient);
                ingredientDTOs.add(dto);
            }
            
            logger.info("Nombre d'ingrédients retournés: {}", ingredientDTOs.size());
            return ResponseEntity.ok(ingredientDTOs);
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des ingrédients", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
        }
    }
    
    // GET - Obtenir un ingrédient par ID
    @GetMapping("/{id}")
    public ResponseEntity<IngredientDTO> getIngredientById(@PathVariable Long id) {
        logger.info("GET /api/ingredients/{} - Récupération de l'ingrédient", id);
        try {
            Optional<Ingredient> ingredient = ingredientService.getIngredientById(id);
            if (ingredient.isPresent()) {
                IngredientDTO dto = convertToDTO(ingredient.get());
                return ResponseEntity.ok(dto);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération de l'ingrédient {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // POST - Créer un nouvel ingrédient
    @PostMapping
    public ResponseEntity<IngredientDTO> createIngredient(@Valid @RequestBody IngredientDTO ingredientDTO) {
        logger.info("POST /api/ingredients - Création d'un nouvel ingrédient: {}", ingredientDTO.getNom());
        try {
            // Convertir DTO vers entité
            Ingredient ingredient = new Ingredient();
            ingredient.setNom(ingredientDTO.getNom());
            ingredient.setDescription(ingredientDTO.getDescription());
            
            // Convertir le type String en enum TypeIngredient
            if (ingredientDTO.getType() != null && !ingredientDTO.getType().isEmpty()) {
                try {
                    TypeIngredient typeEnum = TypeIngredient.valueOf(ingredientDTO.getType().toUpperCase());
                    ingredient.setType(typeEnum);
                } catch (IllegalArgumentException e) {
                    logger.warn("Type d'ingrédient invalide: {}. Utilisation de AUTRE par défaut.", ingredientDTO.getType());
                    ingredient.setType(TypeIngredient.AUTRE);
                }
            } else {
                ingredient.setType(TypeIngredient.AUTRE); // Valeur par défaut
            }
            
            // Sauvegarder
            Ingredient savedIngredient = ingredientService.saveIngredient(ingredient);
            IngredientDTO responseDTO = convertToDTO(savedIngredient);
            
            logger.info("Ingrédient créé avec succès - ID: {}", savedIngredient.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        } catch (Exception e) {
            logger.error("Erreur lors de la création de l'ingrédient", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    // PUT - Mettre à jour un ingrédient
    @PutMapping("/{id}")
    public ResponseEntity<IngredientDTO> updateIngredient(@PathVariable Long id, @Valid @RequestBody IngredientDTO ingredientDTO) {
        logger.info("PUT /api/ingredients/{} - Mise à jour de l'ingrédient", id);
        try {
            Optional<Ingredient> existingIngredient = ingredientService.getIngredientById(id);
            if (!existingIngredient.isPresent()) {
                return ResponseEntity.notFound().build();
            }
            
            // Mettre à jour l'entité existante
            Ingredient ingredient = existingIngredient.get();
            ingredient.setNom(ingredientDTO.getNom());
            ingredient.setDescription(ingredientDTO.getDescription());
            
            // Convertir le type String en enum TypeIngredient
            if (ingredientDTO.getType() != null && !ingredientDTO.getType().isEmpty()) {
                try {
                    TypeIngredient typeEnum = TypeIngredient.valueOf(ingredientDTO.getType().toUpperCase());
                    ingredient.setType(typeEnum);
                } catch (IllegalArgumentException e) {
                    logger.warn("Type d'ingrédient invalide: {}. Conservation du type existant.", ingredientDTO.getType());
                }
            }
            
            // Sauvegarder
            Ingredient updatedIngredient = ingredientService.saveIngredient(ingredient);
            IngredientDTO responseDTO = convertToDTO(updatedIngredient);
            
            logger.info("Ingrédient mis à jour avec succès - ID: {}", id);
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            logger.error("Erreur lors de la mise à jour de l'ingrédient {}", id, e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    // DELETE - Supprimer un ingrédient
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable Long id) {
        logger.info("DELETE /api/ingredients/{} - Suppression de l'ingrédient", id);
        try {
            Optional<Ingredient> existingIngredient = ingredientService.getIngredientById(id);
            if (!existingIngredient.isPresent()) {
                return ResponseEntity.notFound().build();
            }
            
            ingredientService.deleteIngredient(id);
            logger.info("Ingrédient supprimé avec succès - ID: {}", id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Erreur lors de la suppression de l'ingrédient {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // GET - Obtenir les types d'ingrédients disponibles
    @GetMapping("/types")
    public ResponseEntity<String[]> getIngredientTypes() {
        try {
            String[] types = new String[TypeIngredient.values().length];
            for (int i = 0; i < TypeIngredient.values().length; i++) {
                types[i] = TypeIngredient.values()[i].name();
            }
            return ResponseEntity.ok(types);
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des types d'ingrédients", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // Méthode utilitaire pour convertir en DTO
    private IngredientDTO convertToDTO(Ingredient ingredient) {
        IngredientDTO dto = new IngredientDTO();
        dto.setId(ingredient.getId());
        dto.setNom(ingredient.getNom());
        dto.setDescription(ingredient.getDescription());
        // Convertir l'enum en String pour le DTO
        dto.setType(ingredient.getType() != null ? ingredient.getType().name() : null);
        return dto;
    }
}