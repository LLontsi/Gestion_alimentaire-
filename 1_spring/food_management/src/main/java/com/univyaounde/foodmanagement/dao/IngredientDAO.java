package com.univyaounde.foodmanagement.dao;

import com.univyaounde.foodmanagement.model.Ingredient;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class IngredientDAO {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public Ingredient save(Ingredient ingredient) {
        if (ingredient.getId() == null) {
            entityManager.persist(ingredient);
            return ingredient;
        } else {
            return entityManager.merge(ingredient);
        }
    }
    
    public Optional<Ingredient> findById(Long id) {
        Ingredient ingredient = entityManager.find(Ingredient.class, id);
        return Optional.ofNullable(ingredient);
    }
    
    public List<Ingredient> findAll() {
        TypedQuery<Ingredient> query = entityManager.createQuery(
            "SELECT i FROM Ingredient i", Ingredient.class);
        return query.getResultList();
    }
    
    public void deleteById(Long id) {
        Ingredient ingredient = entityManager.find(Ingredient.class, id);
        if (ingredient != null) {
            entityManager.remove(ingredient);
        }
    }
    
    public List<Ingredient> findMostUsedIngredients() {
        TypedQuery<Ingredient> query = entityManager.createQuery(
            "SELECT i FROM Ingredient i LEFT JOIN i.aliments f " +
            "GROUP BY i ORDER BY COUNT(f) DESC", Ingredient.class);
        return query.getResultList();
    }
    
    public List<Ingredient> findIngredientsByTypeAndName(String type, String nom) {
        StringBuilder jpql = new StringBuilder("SELECT i FROM Ingredient i WHERE 1=1");
        
        if (type != null && !type.isEmpty()) {
            jpql.append(" AND i.type = :type");
        }
        if (nom != null && !nom.isEmpty()) {
            jpql.append(" AND LOWER(i.nom) LIKE LOWER(:nom)");
        }
        
        TypedQuery<Ingredient> query = entityManager.createQuery(jpql.toString(), Ingredient.class);
        
        if (type != null && !type.isEmpty()) {
            query.setParameter("type", Ingredient.TypeIngredient.valueOf(type));
        }
        if (nom != null && !nom.isEmpty()) {
            query.setParameter("nom", "%" + nom + "%");
        }
        
        return query.getResultList();
    }
}