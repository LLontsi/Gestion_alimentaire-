package com.foodmanagement.repository;

import com.foodmanagement.entity.Ingredient;
import com.foodmanagement.enums.TypeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository pour l'entité Ingredient
 * Gère les opérations CRUD et recherches pour les ingrédients
 */
@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    /**
     * Rechercher un ingrédient par son nom exact
     * 
     * @param nom Nom de l'ingrédient
     * @return Optional contenant l'ingrédient si trouvé
     */
    Optional<Ingredient> findByNom(String nom);

    /**
     * Rechercher des ingrédients par type
     * 
     * @param type Type d'ingrédient
     * @return Liste des ingrédients de ce type
     */
    List<Ingredient> findByType(TypeIngredient type);

    /**
     * Rechercher des ingrédients par nom (recherche partielle)
     * 
     * @param nom Nom ou partie du nom
     * @return Liste des ingrédients trouvés
     */
    @Query("SELECT i FROM Ingredient i WHERE UPPER(i.nom) LIKE UPPER(CONCAT('%', :nom, '%'))")
    List<Ingredient> findByNomContainingIgnoreCase(@Param("nom") String nom);

    /**
     * Rechercher des ingrédients par unité
     * 
     * @param unite Unité de mesure
     * @return Liste des ingrédients avec cette unité
     */
    List<Ingredient> findByUnite(String unite);

    /**
     * Rechercher des ingrédients par type et unité
     * Combinaison de critères
     * 
     * @param type Type d'ingrédient
     * @param unite Unité de mesure
     * @return Liste des ingrédients correspondants
     */
    @Query("SELECT i FROM Ingredient i WHERE i.type = :type AND i.unite = :unite")
    List<Ingredient> findByTypeAndUnite(@Param("type") TypeIngredient type, @Param("unite") String unite);

    /**
     * Rechercher des ingrédients ayant une image
     * 
     * @return Liste des ingrédients avec image
     */
    @Query("SELECT i FROM Ingredient i WHERE i.image IS NOT NULL")
    List<Ingredient> findIngredientsWithImage();

    /**
     * Rechercher des ingrédients utilisés dans des aliments
     * 
     * @return Liste des ingrédients utilisés
     */
    @Query("SELECT DISTINCT i FROM Ingredient i JOIN i.foodIngredients fi")
    List<Ingredient> findIngredientsUsedInFoods();

    /**
     * Rechercher des ingrédients non utilisés
     * Utile pour le nettoyage de données
     * 
     * @return Liste des ingrédients non utilisés
     */
    @Query("SELECT i FROM Ingredient i WHERE i.foodIngredients IS EMPTY")
    List<Ingredient> findUnusedIngredients();

    /**
     * Compter le nombre d'ingrédients par type
     * 
     * @param type Type d'ingrédient
     * @return Nombre d'ingrédients de ce type
     */
    long countByType(TypeIngredient type);

    /**
     * Rechercher des ingrédients par quantité
     * 
     * @param quantiteMin Quantité minimum
     * @param quantiteMax Quantité maximum
     * @return Liste des ingrédients dans cette tranche
     */
    @Query("SELECT i FROM Ingredient i WHERE i.quantite BETWEEN :quantiteMin AND :quantiteMax ORDER BY i.quantite ASC")
    List<Ingredient> findByQuantiteBetween(@Param("quantiteMin") Double quantiteMin, 
                                         @Param("quantiteMax") Double quantiteMax);

    /**
     * Vérifier si un nom d'ingrédient existe déjà
     * 
     * @param nom Nom à vérifier
     * @return true si le nom existe
     */
    boolean existsByNom(String nom);

    /**
     * Rechercher les ingrédients les plus récents
     * 
     * @return Liste des 15 ingrédients les plus récents
     */
    @Query("SELECT i FROM Ingredient i ORDER BY i.dateCreation DESC LIMIT 15")
    List<Ingredient> findTop15ByOrderByDateCreationDesc();

    /**
     * Rechercher des ingrédients utilisés dans un aliment spécifique
     * 
     * @param foodId ID de l'aliment
     * @return Liste des ingrédients de cet aliment
     */
    @Query("SELECT i FROM Ingredient i JOIN i.foodIngredients fi WHERE fi.food.id = :foodId")
    List<Ingredient> findByFoodId(@Param("foodId") Long foodId);

    /**
     * Rechercher les ingrédients les plus utilisés
     * Classés par nombre d'utilisations
     * 
     * @return Liste des ingrédients triés par popularité
     */
    @Query("SELECT i FROM Ingredient i LEFT JOIN i.foodIngredients fi GROUP BY i ORDER BY COUNT(fi) DESC LIMIT 10")
    List<Ingredient> findTop10MostUsedIngredients();

    /**
     * Rechercher des ingrédients par plusieurs types
     * 
     * @param types Liste des types
     * @return Liste des ingrédients de ces types
     */
    @Query("SELECT i FROM Ingredient i WHERE i.type IN :types ORDER BY i.nom ASC")
    List<Ingredient> findByTypeIn(@Param("types") List<TypeIngredient> types);
}