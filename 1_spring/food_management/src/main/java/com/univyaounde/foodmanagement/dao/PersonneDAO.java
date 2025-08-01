package com.univyaounde.foodmanagement.dao;

import com.univyaounde.foodmanagement.model.Personne;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class PersonneDAO {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public Personne save(Personne personne) {
        if (personne.getId() == null) {
            entityManager.persist(personne);
            return personne;
        } else {
            return entityManager.merge(personne);
        }
    }
    
    public Optional<Personne> findById(Long id) {
        Personne personne = entityManager.find(Personne.class, id);
        return Optional.ofNullable(personne);
    }
    
    public List<Personne> findAll() {
        TypedQuery<Personne> query = entityManager.createQuery(
            "SELECT p FROM Personne p", Personne.class);
        return query.getResultList();
    }
    
    public void deleteById(Long id) {
        Personne personne = entityManager.find(Personne.class, id);
        if (personne != null) {
            entityManager.remove(personne);
        }
    }
    
    public List<Personne> findPersonnesWithMostFoods() {
        TypedQuery<Personne> query = entityManager.createQuery(
            "SELECT p FROM Personne p LEFT JOIN p.aliments f " +
            "GROUP BY p ORDER BY COUNT(f) DESC", Personne.class);
        return query.getResultList();
    }
    
    public List<Personne> findPersonnesByFoodCategory(String categorie) {
        TypedQuery<Personne> query = entityManager.createQuery(
            "SELECT DISTINCT p FROM Personne p JOIN p.aliments f " +
            "WHERE f.categorie = :categorie", Personne.class);
        query.setParameter("categorie", categorie);
        return query.getResultList();
    }
}