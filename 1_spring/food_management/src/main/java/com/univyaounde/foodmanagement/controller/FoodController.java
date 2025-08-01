package com.univyaounde.foodmanagement.controller;

import com.univyaounde.foodmanagement.dto.FoodDTO;
import com.univyaounde.foodmanagement.model.Food;
import com.univyaounde.foodmanagement.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/foods")
@CrossOrigin(origins = "*")
public class FoodController {
    
    @Autowired
    private FoodService foodService;
    
    @GetMapping
    public ResponseEntity<List<FoodDTO>> getAllFoods() {
        try {
            List<Food> foods = foodService.getAllFoods();
            List<FoodDTO> foodDTOs = new ArrayList<>();
            
            for (Food food : foods) {
                FoodDTO dto = new FoodDTO();
                dto.setId(food.getId());
                dto.setNom(food.getNom());
                dto.setDescription(food.getDescription());
                dto.setCategorie(food.getCategorie().toString());
                dto.setCalories(food.getCalories());
                dto.setDateCreation(food.getDateCreation());
                
                // Éviter lazy loading - valeurs par défaut
                dto.setPersonneId(null);
                dto.setPersonneNom(null);
                dto.setNombreIngredients(0);
                dto.setNombreImages(0);
                
                foodDTOs.add(dto);
            }
            
            return ResponseEntity.ok(foodDTOs);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new ArrayList<>());
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<FoodDTO> getFoodById(@PathVariable Long id) {
        try {
            Optional<Food> food = foodService.getFoodById(id);
            if (food.isPresent()) {
                FoodDTO dto = convertToDTO(food.get());
                return ResponseEntity.ok(dto);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    private FoodDTO convertToDTO(Food food) {
        FoodDTO dto = new FoodDTO();
        dto.setId(food.getId());
        dto.setNom(food.getNom());
        dto.setDescription(food.getDescription());
        dto.setCategorie(food.getCategorie().toString());
        dto.setCalories(food.getCalories());
        dto.setDateCreation(food.getDateCreation());
        
        // Éviter lazy loading - valeurs par défaut
        dto.setPersonneId(null);
        dto.setPersonneNom(null);
        dto.setNombreIngredients(0);
        dto.setNombreImages(0);
        
        return dto;
    }
}