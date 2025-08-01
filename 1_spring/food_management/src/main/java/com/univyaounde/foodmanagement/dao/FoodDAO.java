package com.univyaounde.foodmanagement.dao;

import com.univyaounde.foodmanagement.model.Food;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class FoodDAO {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public Food save(Food food) {
        if (food.getId() == null) {
            entityManager.persist(food);
            return food;
        } else {
            return entityManager.merge(food);
        }
    }
    
    public Optional<Food> findById(Long id) {
        Food food = entityManager.find(Food.class, id);
        return Optional.ofNullable(food);
    }
    
    public List<Food> findAll() {
        TypedQuery<Food> query = entityManager.createQuery(
            "SELECT f FROM Food f", Food.class);
        return query.getResultList();
    }
    
    public void deleteById(Long id) {
        Food food = entityManager.find(Food.class, id);
        if (food != null) {
            entityManager.remove(food);
        }
    }
    
    public List<Food> findFoodsWithMostIngredients() {
        TypedQuery<Food> query = entityManager.createQuery(
            "SELECT f FROM Food f LEFT JOIN f.ingredients i " +
            "GROUP BY f ORDER BY COUNT(i) DESC", Food.class);
        return query.getResultList();
    }
    
    public List<Food> findFoodsByMultipleCriteria(String nom, String categorie, Double minCalories) {
        StringBuilder jpql = new StringBuilder("SELECT f FROM Food f WHERE 1=1");
        
        if (nom != null && !nom.isEmpty()) {
            jpql.append(" AND LOWER(f.nom) LIKE LOWER(:nom)");
        }
        if (categorie != null && !categorie.isEmpty()) {
            jpql.append(" AND f.categorie = :categorie");
        }
        if (minCalories != null) {
            jpql.append(" AND f.calories >= :minCalories");
        }
        
        TypedQuery<Food> query = entityManager.createQuery(jpql.toString(), Food.class);
        
        if (nom != null && !nom.isEmpty()) {
            query.setParameter("nom", "%" + nom + "%");
        }
        if (categorie != null && !categorie.isEmpty()) {
            query.setParameter("categorie", Food.CategorieAliment.valueOf(categorie));
        }
        if (minCalories != null) {
            query.setParameter("minCalories", minCalories);
        }
        
        return query.getResultList();
    }
}