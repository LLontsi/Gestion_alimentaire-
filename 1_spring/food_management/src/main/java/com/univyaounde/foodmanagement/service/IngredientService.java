package com.univyaounde.foodmanagement.service;

import com.univyaounde.foodmanagement.dao.IngredientDAO;
import com.univyaounde.foodmanagement.model.Ingredient;
import com.univyaounde.foodmanagement.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class IngredientService {
    
    @Autowired
    private IngredientRepository ingredientRepository;
    
    @Autowired
    private IngredientDAO ingredientDAO;
    
    public Ingredient createIngredient(Ingredient ingredient) {
        if (ingredientRepository.existsByNom(ingredient.getNom())) {
            throw new RuntimeException("Un ingrédient avec ce nom existe déjà");
        }
        return ingredientDAO.save(ingredient);
    }
    
    public Optional<Ingredient> getIngredientById(Long id) {
        return ingredientDAO.findById(id);
    }
    
    public List<Ingredient> getAllIngredients() {
        return ingredientDAO.findAll();
    }
     
    // Sauvegarder un ingrédient (création ou mise à jour)
    public Ingredient saveIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }
    public Ingredient updateIngredient(Long id, Ingredient ingredientDetails) {
        Optional<Ingredient> optionalIngredient = ingredientDAO.findById(id);
        if (optionalIngredient.isPresent()) {
            Ingredient ingredient = optionalIngredient.get();
            ingredient.setNom(ingredientDetails.getNom());
            ingredient.setDescription(ingredientDetails.getDescription());
            ingredient.setType(ingredientDetails.getType());
            ingredient.setValeurNutritive(ingredientDetails.getValeurNutritive());
            return ingredientDAO.save(ingredient);
        }
        throw new RuntimeException("Ingrédient non trouvé avec l'id: " + id);
    }
    
    public void deleteIngredient(Long id) {
        if (ingredientDAO.findById(id).isPresent()) {
            ingredientDAO.deleteById(id);
        } else {
            throw new RuntimeException("Ingrédient non trouvé avec l'id: " + id);
        }
    }
    
    public List<Ingredient> getIngredientsByType(Ingredient.TypeIngredient type) {
        return ingredientRepository.findByType(type);
    }
    
    public List<Ingredient> searchIngredientsByName(String nom) {
        return ingredientRepository.findByNomContainingIgnoreCase(nom);
    }
    
    public Optional<Ingredient> getIngredientByName(String nom) {
        return ingredientRepository.findByNom(nom);
    }
    
    public List<Ingredient> getIngredientsByFood(Long foodId) {
        return ingredientRepository.findByFoodId(foodId);
    }
    
    public List<Ingredient> getMostUsedIngredients() {
        return ingredientDAO.findMostUsedIngredients();
    }
    
    public List<Ingredient> searchIngredientsAdvanced(String type, String nom) {
        return ingredientDAO.findIngredientsByTypeAndName(type, nom);
    }
}