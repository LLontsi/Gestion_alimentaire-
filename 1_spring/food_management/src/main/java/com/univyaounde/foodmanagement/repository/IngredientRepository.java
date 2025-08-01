package com.univyaounde.foodmanagement.repository;

import com.univyaounde.foodmanagement.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    
    Optional<Ingredient> findByNom(String nom);
    
    List<Ingredient> findByType(Ingredient.TypeIngredient type);
    
    List<Ingredient> findByNomContainingIgnoreCase(String nom);
    
    @Query("SELECT i FROM Ingredient i JOIN i.aliments f WHERE f.id = :foodId")
    List<Ingredient> findByFoodId(@Param("foodId") Long foodId);
    
    boolean existsByNom(String nom);
}