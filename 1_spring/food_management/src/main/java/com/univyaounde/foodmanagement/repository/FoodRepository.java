package com.univyaounde.foodmanagement.repository;

import com.univyaounde.foodmanagement.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
    
    List<Food> findByCategorie(Food.CategorieAliment categorie);
    
    List<Food> findByNomContainingIgnoreCase(String nom);
    
    List<Food> findByPersonneId(Long personneId);
    
    @Query("SELECT f FROM Food f WHERE f.calories BETWEEN :min AND :max")
    List<Food> findByCaloriesBetween(@Param("min") Double min, @Param("max") Double max);
    
    @Query("SELECT f FROM Food f JOIN f.ingredients i WHERE i.nom = :ingredientNom")
    List<Food> findByIngredientNom(@Param("ingredientNom") String ingredientNom);
    
    @Query("SELECT COUNT(f) FROM Food f WHERE f.personne.id = :personneId")
    Long countByPersonneId(@Param("personneId") Long personneId);
}