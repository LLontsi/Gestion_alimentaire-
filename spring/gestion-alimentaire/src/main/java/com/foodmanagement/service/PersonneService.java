package com.foodmanagement.service;

import com.foodmanagement.entity.Personne;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Interface du service pour l'entité Personne
 * Définit la logique métier pour la gestion des personnes
 * 
 * Cette couche contient :
 * - Validations métier
 * - Règles de gestion
 * - Orchestration des opérations
 * - Gestion des transactions
 */
public interface PersonneService {

    /**
     * Créer une nouvelle personne
     * Inclut les validations métier (email unique, format, etc.)
     * 
     * @param personne Personne à créer
     * @return Personne créée avec son ID
     * @throws IllegalArgumentException Si les données sont invalides
     * @throws RuntimeException Si l'email existe déjà
     */
    Personne creerPersonne(Personne personne);

    /**
     * Rechercher une personne par ID
     * 
     * @param id Identifiant de la personne
     * @return Optional contenant la personne si trouvée
     */
    Optional<Personne> obtenirPersonneParId(Long id);

    /**
     * Rechercher une personne par email
     * 
     * @param email Email de la personne
     * @return Optional contenant la personne si trouvée
     */
    Optional<Personne> obtenirPersonneParEmail(String email);

    /**
     * Lister toutes les personnes
     * 
     * @return Liste de toutes les personnes
     */
    List<Personne> listerToutesLesPersonnes();

    /**
     * Rechercher des personnes par nom (recherche partielle)
     * 
     * @param nom Nom ou partie du nom à rechercher
     * @return Liste des personnes trouvées
     */
    List<Personne> rechercherPersonnesParNom(String nom);

    /**
     * Mettre à jour une personne existante
     * Inclut les validations métier
     * 
     * @param personne Personne avec les nouvelles données
     * @return Personne mise à jour
     * @throws RuntimeException Si la personne n'existe pas
     */
    Personne modifierPersonne(Personne personne);

    /**
     * Supprimer une personne par ID
     * Vérifie les contraintes métier (pas d'aliments associés, etc.)
     * 
     * @param id Identifiant de la personne à supprimer
     * @return true si suppression réussie
     * @throws RuntimeException Si la personne a des aliments associés
     */
    boolean supprimerPersonne(Long id);

    /**
     * Vérifier si un email est disponible
     * 
     * @param email Email à vérifier
     * @return true si l'email est disponible
     */
    boolean verifierEmailDisponible(String email);

    /**
     * Authentifier une personne
     * Vérifie email et mot de passe
     * 
     * @param email Email de connexion
     * @param motDePasse Mot de passe
     * @return Optional contenant la personne si authentification réussie
     */
    Optional<Personne> authentifierPersonne(String email, String motDePasse);

    /**
     * Changer le mot de passe d'une personne
     * Inclut la validation de l'ancien mot de passe
     * 
     * @param personneId ID de la personne
     * @param ancienMotDePasse Ancien mot de passe
     * @param nouveauMotDePasse Nouveau mot de passe
     * @return true si changement réussi
     */
    boolean changerMotDePasse(Long personneId, String ancienMotDePasse, String nouveauMotDePasse);

    /**
     * Compter le nombre total de personnes
     * 
     * @return Nombre de personnes inscrites
     */
    long compterPersonnes();

    /**
     * Obtenir les statistiques d'inscription
     * 
     * @param dateDebut Date de début de la période
     * @param dateFin Date de fin de la période
     * @return Nombre de personnes inscrites dans cette période
     */
    long obtenirStatistiquesInscription(LocalDateTime dateDebut, LocalDateTime dateFin);

    /**
     * Lister les personnes ayant créé des aliments
     * 
     * @return Liste des personnes actives (avec des aliments)
     */
    List<Personne> listerPersonnesActives();

    /**
     * Valider les données d'une personne
     * Méthode utilitaire pour les validations métier
     * 
     * @param personne Personne à valider
     * @throws IllegalArgumentException Si les données sont invalides
     */
    void validerDonneesPersonne(Personne personne);
}