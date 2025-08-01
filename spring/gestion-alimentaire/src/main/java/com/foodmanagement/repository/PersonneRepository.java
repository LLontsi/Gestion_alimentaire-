package com.foodmanagement.repository;

import com.foodmanagement.entity.Personne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository pour l'entité Personne
 * Hérite de JpaRepository qui fournit les opérations CRUD de base :
 * - save() : Créer/Modifier
 * - findById() : Rechercher par ID
 * - findAll() : Lister tous
 * - deleteById() : Supprimer par ID
 * - count() : Compter le nombre d'enregistrements
 * - existsById() : Vérifier l'existence
 * 
 * JpaRepository<Personne, Long> :
 * - Personne : Type de l'entité
 * - Long : Type de la clé primaire
 */
@Repository
public interface PersonneRepository extends JpaRepository<Personne, Long> {

    /**
     * Rechercher une personne par son email
     * Méthode générée automatiquement par Spring Data JPA
     * Basée sur le nom de la méthode (findBy + nom de l'attribut)
     * 
     * @param email Email à rechercher
     * @return Optional contenant la personne si trouvée
     */
    Optional<Personne> findByEmail(String email);

    /**
     * Vérifier si un email existe déjà
     * Utile pour valider l'unicité avant création
     * 
     * @param email Email à vérifier
     * @return true si l'email existe
     */
    boolean existsByEmail(String email);

    /**
     * Rechercher des personnes par nom (recherche partielle, insensible à la casse)
     * Utilise LIKE avec % pour la recherche partielle
     * UPPER() pour ignorer la casse
     * 
     * @param nom Nom ou partie du nom à rechercher
     * @return Liste des personnes trouvées
     */
    @Query("SELECT p FROM Personne p WHERE UPPER(p.nom) LIKE UPPER(CONCAT('%', :nom, '%'))")
    List<Personne> findByNomContainingIgnoreCase(@Param("nom") String nom);

    /**
     * Rechercher des personnes créées entre deux dates
     * Utilise une requête JPQL personnalisée
     * 
     * @param dateDebut Date de début
     * @param dateFin Date de fin
     * @return Liste des personnes créées dans cette période
     */
    @Query("SELECT p FROM Personne p WHERE p.dateCreation BETWEEN :dateDebut AND :dateFin ORDER BY p.dateCreation DESC")
    List<Personne> findByDateCreationBetween(@Param("dateDebut") LocalDateTime dateDebut, 
                                           @Param("dateFin") LocalDateTime dateFin);

    /**
     * Compter le nombre de personnes créées aujourd'hui
     * Requête native SQL pour utiliser des fonctions spécifiques à PostgreSQL
     * 
     * @return Nombre de personnes créées aujourd'hui
     */
    @Query(value = "SELECT COUNT(*) FROM personne WHERE DATE(date_creation) = CURRENT_DATE", nativeQuery = true)
    long countPersonnesCreatedToday();

    /**
     * Rechercher des personnes ayant créé au moins un aliment
     * Utilise une jointure avec la table food
     * 
     * @return Liste des personnes qui ont créé des aliments
     */
    @Query("SELECT DISTINCT p FROM Personne p JOIN p.foods f")
    List<Personne> findPersonnesWithFoods();

    /**
     * Rechercher des personnes par numéro de téléphone
     * Méthode générée automatiquement
     * 
     * @param telephone Numéro de téléphone
     * @return Optional contenant la personne si trouvée
     */
    Optional<Personne> findByTelephone(String telephone);

    /**
     * Supprimer une personne par email
     * Méthode générée automatiquement
     * Attention : Cette opération est définitive !
     * 
     * @param email Email de la personne à supprimer
     */
    void deleteByEmail(String email);

    /**
     * Rechercher les 10 dernières personnes inscrites
     * Ordonnées par date de création décroissante
     * 
     * @return Liste des 10 dernières personnes
     */
    @Query("SELECT p FROM Personne p ORDER BY p.dateCreation DESC LIMIT 10")
    List<Personne> findTop10ByOrderByDateCreationDesc();
}