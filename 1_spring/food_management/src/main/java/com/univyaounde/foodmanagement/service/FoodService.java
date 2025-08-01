package com.univyaounde.foodmanagement.service;

import com.univyaounde.foodmanagement.dao.FoodDAO;
import com.univyaounde.foodmanagement.model.Food;
import com.univyaounde.foodmanagement.model.Ingredient;
import com.univyaounde.foodmanagement.repository.FoodRepository;
import com.univyaounde.foodmanagement.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FoodService {
    
    @Autowired
    private FoodRepository foodRepository;
    
    @Autowired
    private FoodDAO foodDAO;
    
    @Autowired
    private IngredientRepository ingredientRepository;
    
    public Food createFood(Food food) {
        // Vérifier et associer les ingrédients existants
        if (food.getIngredients() != null) {
            List<Ingredient> managedIngredients = food.getIngredients().stream()
                .map(ingredient -> {
                    if (ingredient.getId() != null) {
                        return ingredientRepository.findById(ingredient.getId())
                            .orElseThrow(() -> new RuntimeException("Ingrédient non trouvé: " + ingredient.getId()));
                    }
                    return ingredient;
                })
                .toList();
            food.setIngredients(managedIngredients);
        }
        return foodDAO.save(food);
    }
    
    public Optional<Food> getFoodById(Long id) {
        return foodDAO.findById(id);
    }
    
    public List<Food> getAllFoods() {
        return foodDAO.findAll();
    }
    
    public Food updateFood(Long id, Food foodDetails) {
        Optional<Food> optionalFood = foodDAO.findById(id);
        if (optionalFood.isPresent()) {
            Food food = optionalFood.get();
            food.setNom(foodDetails.getNom());
            food.setDescription(foodDetails.getDescription());
            food.setCategorie(foodDetails.getCategorie());
            food.setCalories(foodDetails.getCalories());
            
            if (foodDetails.getIngredients() != null) {
                List<Ingredient> managedIngredients = foodDetails.getIngredients().stream()
                    .map(ingredient -> {
                        if (ingredient.getId() != null) {
                            return ingredientRepository.findById(ingredient.getId())
                                .orElseThrow(() -> new RuntimeException("Ingrédient non trouvé: " + ingredient.getId()));
                        }
                        return ingredient;
                    })
                    .toList();
                food.setIngredients(managedIngredients);
            }
            
            return foodDAO.save(food);
        }
        throw new RuntimeException("Aliment non trouvé avec l'id: " + id);
    }
    
    public void deleteFood(Long id) {
        if (foodDAO.findById(id).isPresent()) {
            foodDAO.deleteById(id);
        } else {
            throw new RuntimeException("Aliment non trouvé avec l'id: " + id);
        }
    }
    
    public List<Food> getFoodsByCategory(Food.CategorieAliment categorie) {
        return foodRepository.findByCategorie(categorie);
    }
    
    public List<Food> searchFoodsByName(String nom) {
        return foodRepository.findByNomContainingIgnoreCase(nom);
    }
    
    public List<Food> getFoodsByPersonne(Long personneId) {
        return foodRepository.findByPersonneId(personneId);
    }
    
    public List<Food> getFoodsByCaloriesRange(Double min, Double max) {
        return foodRepository.findByCaloriesBetween(min, max);
    }
    
    public List<Food> getFoodsWithMostIngredients() {
        return foodDAO.findFoodsWithMostIngredients();
    }
    
    public List<Food> searchFoodsAdvanced(String nom, String categorie, Double minCalories) {
        return foodDAO.findFoodsByMultipleCriteria(nom, categorie, minCalories);
    }
}