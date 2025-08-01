package com.univyaounde.foodmanagement.dao;

import com.univyaounde.foodmanagement.model.Image;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class ImageDAO {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public Image save(Image image) {
        if (image.getId() == null) {
            entityManager.persist(image);
            return image;
        } else {
            return entityManager.merge(image);
        }
    }
    
    public Optional<Image> findById(Long id) {
        Image image = entityManager.find(Image.class, id);
        return Optional.ofNullable(image);
    }
    
    public List<Image> findAll() {
        TypedQuery<Image> query = entityManager.createQuery(
            "SELECT i FROM Image i", Image.class);
        return query.getResultList();
    }
    
    public void deleteById(Long id) {
        Image image = entityManager.find(Image.class, id);
        if (image != null) {
            entityManager.remove(image);
        }
    }
    
    public List<Image> findImagesByFood(Long foodId) {
        TypedQuery<Image> query = entityManager.createQuery(
            "SELECT i FROM Image i WHERE i.food.id = :foodId", Image.class);
        query.setParameter("foodId", foodId);
        return query.getResultList();
    }
    
    public Long getTotalImageSize() {
        TypedQuery<Long> query = entityManager.createQuery(
            "SELECT SUM(i.taille) FROM Image i", Long.class);
        Long result = query.getSingleResult();
        return result != null ? result : 0L;
    }
}