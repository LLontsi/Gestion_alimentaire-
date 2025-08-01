package com.foodmanagement.dao;

import com.foodmanagement.entity.Ingredient;
import com.foodmanagement.enums.TypeIngredient;
import java.util.List;
import java.util.Optional;

/**
 * Interface DAO pour l'entité Ingredient
 * Définit les opérations CRUD pour les ingrédients avec JDBC
 */
public interface IngredientDao {

    /**
     * Créer un nouvel ingrédient
     * 
     * @param ingredient Ingrédient à créer
     * @return Ingrédient créé avec son ID
     */
    Ingredient create(Ingredient ingredient);

    /**
     * Rechercher un ingrédient par ID
     * 
     * @param id Identifiant de l'ingrédient
     * @return Optional contenant l'ingrédient si trouvé
     */
    Optional<Ingredient> findById(Long id);

    /**
     * Rechercher un ingrédient par nom exact
     * 
     * @param nom Nom de l'ingrédient
     * @return Optional contenant l'ingrédient si trouvé
     */
    Optional<Ingredient> findByNom(String nom);

    /**
     * Rechercher tous les ingrédients
     * 
     * @return Liste de tous les ingrédients
     */
    List<Ingredient> findAll();

    /**
     * Rechercher des ingrédients par nom (recherche partielle)
     * 
     * @param nom Nom ou partie du nom
     * @return Liste des ingrédients trouvés
     */
    List<Ingredient> findByNomContaining(String nom);

    /**
     * Rechercher des ingrédients par type
     * 
     * @param type Type d'ingrédient
     * @return Liste des ingrédients de ce type
     */
    List<Ingredient> findByType(TypeIngredient type);

    /**
     * Rechercher des ingrédients par unité
     * 
     * @param unite Unité de mesure
     * @return Liste des ingrédients avec cette unité
     */
    List<Ingredient> findByUnite(String unite);

    /**
     * Mettre à jour un ingrédient
     * 
     * @param ingredient Ingrédient avec les nouvelles données
     * @return Ingrédient mis à jour
     */
    Ingredient update(Ingredient ingredient);

    /**
     * Supprimer un ingrédient par ID
     * 
     * @param id Identifiant de l'ingrédient
     * @return true si suppression réussie
     */
    boolean deleteById(Long id);

    /**
     * Vérifier si un nom d'ingrédient existe
     * 
     * @param nom Nom à vérifier
     * @return true si le nom existe
     */
    boolean existsByNom(String nom);

    /**
     * Compter le nombre d'ingrédients
     * 
     * @return Nombre total d'ingrédients
     */
    long count();

    /**
     * Rechercher des ingrédients par tranche de quantité
     * 
     * @param quantiteMin Quantité minimum
     * @param quantiteMax Quantité maximum
     * @return Liste des ingrédients dans cette tranche
     */
    List<Ingredient> findByQuantiteBetween(Double quantiteMin, Double quantiteMax);

    /**
     * Rechercher des ingrédients utilisés dans des aliments
     * 
     * @return Liste des ingrédients utilisés
     */
    List<Ingredient> findIngredientsUsedInFoods();
}