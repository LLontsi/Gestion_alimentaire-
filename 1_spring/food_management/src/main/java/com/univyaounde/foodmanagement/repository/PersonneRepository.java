package com.univyaounde.foodmanagement.repository;

import com.univyaounde.foodmanagement.model.Personne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PersonneRepository extends JpaRepository<Personne, Long> {
    
    Optional<Personne> findByEmail(String email);
    
    List<Personne> findByNomContainingIgnoreCase(String nom);
    
    List<Personne> findByPrenomContainingIgnoreCase(String prenom);
    
    @Query("SELECT p FROM Personne p WHERE p.nom LIKE %:nom% AND p.prenom LIKE %:prenom%")
    List<Personne> findByNomAndPrenom(@Param("nom") String nom, @Param("prenom") String prenom);
    
    boolean existsByEmail(String email);
}