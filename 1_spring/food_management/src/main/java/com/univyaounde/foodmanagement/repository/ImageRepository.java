package com.univyaounde.foodmanagement.repository;

import com.univyaounde.foodmanagement.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    
    List<Image> findByFoodId(Long foodId);
    
    List<Image> findByTypeMime(String typeMime);
    
    @Query("SELECT i FROM Image i WHERE i.taille > :taille")
    List<Image> findByTailleGreaterThan(@Param("taille") Long taille);
    
    @Query("SELECT COUNT(i) FROM Image i WHERE i.food.id = :foodId")
    Long countByFoodId(@Param("foodId") Long foodId);
}