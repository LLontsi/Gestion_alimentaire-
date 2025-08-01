package com.foodmanagement.dao;

import com.foodmanagement.entity.FoodIngredient;
import java.util.List;
import java.util.Optional;

/**
 * Interface DAO pour l'entité FoodIngredient
 * Gère la relation Many-to-Many entre Food et Ingredient avec JDBC
 */
public interface FoodIngredientDao {

    /**
     * Créer une nouvelle association food-ingredient
     * 
     * @param foodIngredient Association à créer
     * @return Association créée
     */
    FoodIngredient create(FoodIngredient foodIngredient);

    /**
     * Rechercher une association par food et ingredient
     * 
     * @param foodId ID de l'aliment
     * @param ingredientId ID de l'ingrédient
     * @return Optional contenant l'association si trouvée
     */
    Optional<FoodIngredient> findByFoodIdAndIngredientId(Long foodId, Long ingredientId);

    /**
     * Rechercher tous les ingrédients d'un aliment
     * 
     * @param foodId ID de l'aliment
     * @return Liste des associations pour cet aliment
     */
    List<FoodIngredient> findByFoodId(Long foodId);

    /**
     * Rechercher tous les aliments utilisant un ingrédient
     * 
     * @param ingredientId ID de l'ingrédient
     * @return Liste des associations pour cet ingrédient
     */
    List<FoodIngredient> findByIngredientId(Long ingredientId);

    /**
     * Rechercher toutes les associations
     * 
     * @return Liste de toutes les associations
     */
    List<FoodIngredient> findAll();

    /**
     * Mettre à jour une association
     * 
     * @param foodIngredient Association avec les nouvelles données
     * @return Association mise à jour
     */
    FoodIngredient update(FoodIngredient foodIngredient);

    /**
     * Supprimer une association spécifique
     * 
     * @param foodId ID de l'aliment
     * @param ingredientId ID de l'ingrédient
     * @return true si suppression réussie
     */
    boolean deleteByFoodIdAndIngredientId(Long foodId, Long ingredientId);

    /**
     * Supprimer toutes les associations d'un aliment
     * 
     * @param foodId ID de l'aliment
     * @return Nombre d'associations supprimées
     */
    int deleteByFoodId(Long foodId);

    /**
     * Supprimer toutes les associations d'un ingrédient
     * 
     * @param ingredientId ID de l'ingrédient
     * @return Nombre d'associations supprimées
     */
    int deleteByIngredientId(Long ingredientId);

    /**
     * Vérifier si une association existe
     * 
     * @param foodId ID de l'aliment
     * @param ingredientId ID de l'ingrédient
     * @return true si l'association existe
     */
    boolean existsByFoodIdAndIngredientId(Long foodId, Long ingredientId);

    /**
     * Compter le nombre d'ingrédients dans un aliment
     * 
     * @param foodId ID de l'aliment
     * @return Nombre d'ingrédients
     */
    long countIngredientsByFoodId(Long foodId);

    /**
     * Compter le nombre d'aliments utilisant un ingrédient
     * 
     * @param ingredientId ID de l'ingrédient
     * @return Nombre d'aliments
     */
    long countFoodsByIngredientId(Long ingredientId);

    /**
     * Mettre à jour la quantité d'un ingrédient dans un aliment
     * 
     * @param foodId ID de l'aliment
     * @param ingredientId ID de l'ingrédient
     * @param nouvelleQuantite Nouvelle quantité
     * @param nouvelleUnite Nouvelle unité
     * @return true si mise à jour réussie
     */
    boolean updateQuantiteAndUnite(Long foodId, Long ingredientId, Double nouvelleQuantite, String nouvelleUnite);
}