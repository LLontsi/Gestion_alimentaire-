package com.foodmanagement.dao;

import com.foodmanagement.entity.Personne;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Interface DAO pour l'entité Personne
 * Définit les opérations CRUD en utilisant l'approche DAO classique
 * Cette interface sera implémentée avec JDBC pur (sans ORM)
 * 
 * DAO = Data Access Object
 * Pattern qui encapsule l'accès aux données et cache les détails de persistance
 */
public interface PersonneDao {

    /**
     * Créer une nouvelle personne en base
     * 
     * @param personne Objet Personne à créer
     * @return Personne créée avec son ID généré
     */
    Personne create(Personne personne);

    /**
     * Rechercher une personne par son ID
     * 
     * @param id Identifiant de la personne
     * @return Optional contenant la personne si trouvée
     */
    Optional<Personne> findById(Long id);

    /**
     * Rechercher une personne par son email
     * 
     * @param email Email de la personne
     * @return Optional contenant la personne si trouvée
     */
    Optional<Personne> findByEmail(String email);

    /**
     * Rechercher toutes les personnes
     * 
     * @return Liste de toutes les personnes
     */
    List<Personne> findAll();

    /**
     * Rechercher des personnes par nom (recherche partielle)
     * 
     * @param nom Nom ou partie du nom
     * @return Liste des personnes trouvées
     */
    List<Personne> findByNomContaining(String nom);

    /**
     * Mettre à jour une personne existante
     * 
     * @param personne Personne avec les nouvelles données
     * @return Personne mise à jour
     */
    Personne update(Personne personne);

    /**
     * Supprimer une personne par son ID
     * 
     * @param id Identifiant de la personne à supprimer
     * @return true si la suppression a réussi
     */
    boolean deleteById(Long id);

    /**
     * Supprimer une personne par son email
     * 
     * @param email Email de la personne à supprimer
     * @return true si la suppression a réussi
     */
    boolean deleteByEmail(String email);

    /**
     * Vérifier si un email existe déjà
     * 
     * @param email Email à vérifier
     * @return true si l'email existe
     */
    boolean existsByEmail(String email);

    /**
     * Compter le nombre total de personnes
     * 
     * @return Nombre de personnes en base
     */
    long count();

    /**
     * Rechercher des personnes créées entre deux dates
     * 
     * @param dateDebut Date de début
     * @param dateFin Date de fin
     * @return Liste des personnes créées dans cette période
     */
    List<Personne> findByDateCreationBetween(LocalDateTime dateDebut, LocalDateTime dateFin);
}