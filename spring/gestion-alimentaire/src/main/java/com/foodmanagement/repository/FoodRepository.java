package com.foodmanagement.repository;

import com.foodmanagement.entity.Food;
import com.foodmanagement.enums.CategorieFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository pour l'entité Food
 * Fournit les opérations CRUD et les recherches personnalisées pour les aliments
 */
@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

    /**
     * Rechercher des aliments par catégorie
     * Méthode générée automatiquement
     * 
     * @param categorie Catégorie à rechercher
     * @return Liste des aliments de cette catégorie
     */
    List<Food> findByCategorie(CategorieFood categorie);

    /**
     * Rechercher des aliments par nom (recherche partielle, insensible à la casse)
     * 
     * @param nom Nom ou partie du nom à rechercher
     * @return Liste des aliments trouvés
     */
    @Query("SELECT f FROM Food f WHERE UPPER(f.nom) LIKE UPPER(CONCAT('%', :nom, '%'))")
    List<Food> findByNomContainingIgnoreCase(@Param("nom") String nom);

    /**
     * Rechercher des aliments par créateur (personne)
     * Utilise l'ID de la personne
     * 
     * @param personneId ID de la personne
     * @return Liste des aliments créés par cette personne
     */
    @Query("SELECT f FROM Food f WHERE f.personne.id = :personneId ORDER BY f.dateCreation DESC")
    List<Food> findByPersonneId(@Param("personneId") Long personneId);

    /**
     * Rechercher des aliments par email du créateur
     * 
     * @param email Email du créateur
     * @return Liste des aliments créés par cette personne
     */
    @Query("SELECT f FROM Food f WHERE f.personne.email = :email ORDER BY f.dateCreation DESC")
    List<Food> findByPersonneEmail(@Param("email") String email);

    /**
     * Rechercher des aliments par tranche de calories
     * 
     * @param caloriesMin Calories minimum
     * @param caloriesMax Calories maximum
     * @return Liste des aliments dans cette tranche
     */
    @Query("SELECT f FROM Food f WHERE f.calories BETWEEN :caloriesMin AND :caloriesMax ORDER BY f.calories ASC")
    List<Food> findByCaloriesBetween(@Param("caloriesMin") Double caloriesMin, 
                                   @Param("caloriesMax") Double caloriesMax);

    /**
     * Rechercher des aliments par tranche de prix
     * 
     * @param prixMin Prix minimum
     * @param prixMax Prix maximum
     * @return Liste des aliments dans cette tranche de prix
     */
    List<Food> findByPrixBetweenOrderByPrixAsc(Double prixMin, Double prixMax);

    /**
     * Rechercher des aliments ayant des images
     * Utilise une jointure avec la table image
     * 
     * @return Liste des aliments ayant au moins une image
     */
    @Query("SELECT DISTINCT f FROM Food f JOIN f.images i")
    List<Food> findFoodsWithImages();

    /**
     * Rechercher des aliments contenant un ingrédient spécifique
     * 
     * @param ingredientId ID de l'ingrédient
     * @return Liste des aliments contenant cet ingrédient
     */
    @Query("SELECT DISTINCT f FROM Food f JOIN f.foodIngredients fi WHERE fi.ingredient.id = :ingredientId")
    List<Food> findByIngredientId(@Param("ingredientId") Long ingredientId);

    /**
     * Rechercher des aliments par catégorie et créateur
     * Combinaison de critères
     * 
     * @param categorie Catégorie d'aliment
     * @param personneId ID du créateur
     * @return Liste des aliments correspondants
     */
    @Query("SELECT f FROM Food f WHERE f.categorie = :categorie AND f.personne.id = :personneId")
    List<Food> findByCategorieAndPersonneId(@Param("categorie") CategorieFood categorie, 
                                          @Param("personneId") Long personneId);

    /**
     * Compter le nombre d'aliments par catégorie
     * 
     * @param categorie Catégorie à compter
     * @return Nombre d'aliments dans cette catégorie
     */
    long countByCategorie(CategorieFood categorie);

    /**
     * Rechercher les aliments les plus récents
     * Limitée aux 20 derniers
     * 
     * @return Liste des 20 aliments les plus récents
     */
    @Query("SELECT f FROM Food f ORDER BY f.dateCreation DESC LIMIT 20")
    List<Food> findTop20ByOrderByDateCreationDesc();

    /**
     * Rechercher des aliments par temps de préparation
     * 
     * @param tempsPreparation Temps de préparation
     * @return Liste des aliments avec ce temps de préparation
     */
    List<Food> findByTempsPreparation(String tempsPreparation);

    /**
     * Vérifier si un nom d'aliment existe déjà pour un créateur
     * Évite les doublons pour le même utilisateur
     * 
     * @param nom Nom de l'aliment
     * @param personneId ID du créateur
     * @return true si le nom existe déjà pour ce créateur
     */
    @Query("SELECT COUNT(f) > 0 FROM Food f WHERE f.nom = :nom AND f.personne.id = :personneId")
    boolean existsByNomAndPersonneId(@Param("nom") String nom, @Param("personneId") Long personneId);

    /**
     * Rechercher les aliments les moins caloriques
     * Utile pour des recommandations santé
     * 
     * @return Liste des aliments triés par calories croissantes
     */
    @Query("SELECT f FROM Food f WHERE f.calories IS NOT NULL ORDER BY f.calories ASC LIMIT 10")
    List<Food> findTop10LowCalorieFoods();

    /**
     * Rechercher des aliments par plusieurs catégories
     * 
     * @param categories Liste des catégories
     * @return Liste des aliments de ces catégories
     */
    @Query("SELECT f FROM Food f WHERE f.categorie IN :categories ORDER BY f.nom ASC")
    List<Food> findByCategorieIn(@Param("categories") List<CategorieFood> categories);
}