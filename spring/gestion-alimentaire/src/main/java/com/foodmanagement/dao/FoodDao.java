package com.foodmanagement.dao;

import com.foodmanagement.entity.Food;
import com.foodmanagement.enums.CategorieFood;
import java.util.List;
import java.util.Optional;

/**
 * Interface DAO pour l'entité Food
 * Définit les opérations CRUD pour les aliments avec JDBC
 */
public interface FoodDao {

    /**
     * Créer un nouvel aliment
     * 
     * @param food Aliment à créer
     * @return Aliment créé avec son ID
     */
    Food create(Food food);

    /**
     * Rechercher un aliment par ID
     * 
     * @param id Identifiant de l'aliment
     * @return Optional contenant l'aliment si trouvé
     */
    Optional<Food> findById(Long id);

    /**
     * Rechercher tous les aliments
     * 
     * @return Liste de tous les aliments
     */
    List<Food> findAll();

    /**
     * Rechercher des aliments par nom
     * 
     * @param nom Nom ou partie du nom
     * @return Liste des aliments trouvés
     */
    List<Food> findByNomContaining(String nom);

    /**
     * Rechercher des aliments par catégorie
     * 
     * @param categorie Catégorie d'aliment
     * @return Liste des aliments de cette catégorie
     */
    List<Food> findByCategorie(CategorieFood categorie);

    /**
     * Rechercher des aliments par créateur
     * 
     * @param personneId ID du créateur
     * @return Liste des aliments créés par cette personne
     */
    List<Food> findByPersonneId(Long personneId);

    /**
     * Mettre à jour un aliment
     * 
     * @param food Aliment avec les nouvelles données
     * @return Aliment mis à jour
     */
    Food update(Food food);

    /**
     * Supprimer un aliment par ID
     * 
     * @param id Identifiant de l'aliment
     * @return true si suppression réussie
     */
    boolean deleteById(Long id);

    /**
     * Vérifier si un nom d'aliment existe pour un créateur
     * 
     * @param nom Nom de l'aliment
     * @param personneId ID du créateur
     * @return true si le nom existe déjà
     */
    boolean existsByNomAndPersonneId(String nom, Long personneId);

    /**
     * Compter le nombre d'aliments
     * 
     * @return Nombre total d'aliments
     */
    long count();

    /**
     * Rechercher des aliments par tranche de calories
     * 
     * @param caloriesMin Calories minimum
     * @param caloriesMax Calories maximum
     * @return Liste des aliments dans cette tranche
     */
    List<Food> findByCaloriesBetween(Double caloriesMin, Double caloriesMax);
}