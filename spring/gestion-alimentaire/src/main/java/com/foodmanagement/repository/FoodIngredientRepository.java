package com.foodmanagement.repository;

import com.foodmanagement.entity.FoodIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository pour l'entité FoodIngredient
 * Gère la relation Many-to-Many entre Food et Ingredient
 */
@Repository
public interface FoodIngredientRepository extends JpaRepository<FoodIngredient, FoodIngredient.FoodIngredientId> {

    /**
     * Rechercher tous les ingrédients d'un aliment
     * 
     * @param foodId ID de l'aliment
     * @return Liste des associations food-ingredient
     */
    @Query("SELECT fi FROM FoodIngredient fi WHERE fi.food.id = :foodId")
    List<FoodIngredient> findByFoodId(@Param("foodId") Long foodId);

    /**
     * Rechercher tous les aliments contenant un ingrédient
     * 
     * @param ingredientId ID de l'ingrédient
     * @return Liste des associations food-ingredient
     */
    @Query("SELECT fi FROM FoodIngredient fi WHERE fi.ingredient.id = :ingredientId")
    List<FoodIngredient> findByIngredientId(@Param("ingredientId") Long ingredientId);

    /**
     * Rechercher une association spécifique food-ingredient
     * 
     * @param foodId ID de l'aliment
     * @param ingredientId ID de l'ingrédient
     * @return Optional contenant l'association si elle existe
     */
    @Query("SELECT fi FROM FoodIngredient fi WHERE fi.food.id = :foodId AND fi.ingredient.id = :ingredientId")
    Optional<FoodIngredient> findByFoodIdAndIngredientId(@Param("foodId") Long foodId, 
                                                       @Param("ingredientId") Long ingredientId);

    /**
     * Vérifier si une association existe
     * 
     * @param foodId ID de l'aliment
     * @param ingredientId ID de l'ingrédient
     * @return true si l'association existe
     */
    @Query("SELECT COUNT(fi) > 0 FROM FoodIngredient fi WHERE fi.food.id = :foodId AND fi.ingredient.id = :ingredientId")
    boolean existsByFoodIdAndIngredientId(@Param("foodId") Long foodId, @Param("ingredientId") Long ingredientId);

    /**
     * Supprimer une association spécifique
     * @Modifying : Nécessaire pour les requêtes de modification
     * 
     * @param foodId ID de l'aliment
     * @param ingredientId ID de l'ingrédient
     */
    @Modifying
    @Query("DELETE FROM FoodIngredient fi WHERE fi.food.id = :foodId AND fi.ingredient.id = :ingredientId")
    void deleteByFoodIdAndIngredientId(@Param("foodId") Long foodId, @Param("ingredientId") Long ingredientId);

    /**
     * Supprimer toutes les associations d'un aliment
     * Utile lors de la suppression d'un aliment
     * 
     * @param foodId ID de l'aliment
     */
    @Modifying
    @Query("DELETE FROM FoodIngredient fi WHERE fi.food.id = :foodId")
    void deleteByFoodId(@Param("foodId") Long foodId);

    /**
     * Supprimer toutes les associations d'un ingrédient
     * Utile lors de la suppression d'un ingrédient
     * 
     * @param ingredientId ID de l'ingrédient
     */
    @Modifying
    @Query("DELETE FROM FoodIngredient fi WHERE fi.ingredient.id = :ingredientId")
    void deleteByIngredientId(@Param("ingredientId") Long ingredientId);

    /**
     * Rechercher des associations par unité
     * 
     * @param unite Unité de mesure
     * @return Liste des associations avec cette unité
     */
    List<FoodIngredient> findByUnite(String unite);

    /**
     * Rechercher des associations par tranche de quantité
     * 
     * @param quantiteMin Quantité minimum
     * @param quantiteMax Quantité maximum
     * @return Liste des associations dans cette tranche
     */
    @Query("SELECT fi FROM FoodIngredient fi WHERE fi.quantiteUtilisee BETWEEN :quantiteMin AND :quantiteMax")
    List<FoodIngredient> findByQuantiteUtiliseeBetween(@Param("quantiteMin") Double quantiteMin, 
                                                     @Param("quantiteMax") Double quantiteMax);

    /**
     * Compter le nombre d'ingrédients dans un aliment
     * 
     * @param foodId ID de l'aliment
     * @return Nombre d'ingrédients
     */
    @Query("SELECT COUNT(fi) FROM FoodIngredient fi WHERE fi.food.id = :foodId")
    long countIngredientsByFoodId(@Param("foodId") Long foodId);

    /**
     * Compter le nombre d'aliments utilisant un ingrédient
     * 
     * @param ingredientId ID de l'ingrédient
     * @return Nombre d'aliments
     */
    @Query("SELECT COUNT(fi) FROM FoodIngredient fi WHERE fi.ingredient.id = :ingredientId")
    long countFoodsByIngredientId(@Param("ingredientId") Long ingredientId);

    /**
     * Mettre à jour la quantité d'un ingrédient dans un aliment
     * 
     * @param foodId ID de l'aliment
     * @param ingredientId ID de l'ingrédient
     * @param nouvelleQuantite Nouvelle quantité
     * @param nouvelleUnite Nouvelle unité
     */
    @Modifying
    @Query("UPDATE FoodIngredient fi SET fi.quantiteUtilisee = :nouvelleQuantite, fi.unite = :nouvelleUnite " +
           "WHERE fi.food.id = :foodId AND fi.ingredient.id = :ingredientId")
    void updateQuantiteAndUnite(@Param("foodId") Long foodId, 
                               @Param("ingredientId") Long ingredientId,
                               @Param("nouvelleQuantite") Double nouvelleQuantite,
                               @Param("nouvelleUnite") String nouvelleUnite);
}