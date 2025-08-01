package com.foodmanagement.service;

import com.foodmanagement.entity.Food;
import com.foodmanagement.entity.Personne;
import com.foodmanagement.enums.CategorieFood;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Interface du service pour la gestion de buffets
 * Module métier pour l'exercice 3 - Gestion de buffets pour cérémonies
 */
public interface BuffetService {

    /**
     * Créer un buffet pour un événement
     * 
     * @param organisateurId ID de la personne organisatrice
     * @param nomEvenement Nom de l'événement
     * @param dateEvenement Date et heure de l'événement
     * @param nombreInvites Nombre d'invités attendus
     * @param typeEvenement Type d'événement (mariage, anniversaire, etc.)
     * @param budget Budget alloué (optionnel)
     * @return ID du buffet créé
     */
    Long creerBuffet(Long organisateurId, String nomEvenement, LocalDateTime dateEvenement, 
                    Integer nombreInvites, String typeEvenement, Double budget);

    /**
     * Planifier automatiquement un buffet
     * Génère des suggestions d'aliments basées sur le type d'événement et le nombre d'invités
     * 
     * @param buffetId ID du buffet
     * @param preferences Préférences alimentaires
     * @return Plan du buffet avec quantités
     */
    Map<CategorieFood, List<Map<String, Object>>> planifierBuffetAutomatique(Long buffetId, 
                                                                            List<CategorieFood> preferences);

    /**
     * Ajouter un aliment au buffet
     * 
     * @param buffetId ID du buffet
     * @param foodId ID de l'aliment
     * @param quantitePersonnes Nombre de personnes que cet aliment peut servir
     * @param priorite Priorité (1=haute, 2=moyenne, 3=basse)
     * @return true si ajout réussi
     */
    boolean ajouterAlimentAuBuffet(Long buffetId, Long foodId, Integer quantitePersonnes, Integer priorite);

    /**
     * Supprimer un aliment du buffet
     * 
     * @param buffetId ID du buffet
     * @param foodId ID de l'aliment
     * @return true si suppression réussie
     */
    boolean supprimerAlimentDuBuffet(Long buffetId, Long foodId);

    /**
     * Modifier la quantité d'un aliment dans le buffet
     * 
     * @param buffetId ID du buffet
     * @param foodId ID de l'aliment
     * @param nouvelleQuantitePersonnes Nouvelle quantité de personnes servies
     * @return true si modification réussie
     */
    boolean modifierQuantiteAliment(Long buffetId, Long foodId, Integer nouvelleQuantitePersonnes);

    /**
     * Calculer les quantités nécessaires pour le buffet
     * Basé sur le nombre d'invités et les portions standards
     * 
     * @param buffetId ID du buffet
     * @return Quantités par aliment avec détails
     */
    Map<String, Map<String, Object>> calculerQuantitesNecessaires(Long buffetId);

    /**
     * Estimer le coût total du buffet
     * 
     * @param buffetId ID du buffet
     * @return Coût total estimé
     */
    Double estimerCoutBuffet(Long buffetId);

    /**
     * Vérifier l'équilibre nutritionnel du buffet
     * 
     * @param buffetId ID du buffet
     * @return Rapport d'équilibre avec suggestions
     */
    Map<String, Object> verifierEquilibreNutritionnel(Long buffetId);

    /**
     * Générer une liste de courses pour le buffet
     * Inclut tous les ingrédients nécessaires avec quantités
     * 
     * @param buffetId ID du buffet
     * @return Liste des ingrédients avec quantités totales
     */
    Map<String, Double> genererListeCoursesBuffet(Long buffetId);

    /**
     * Obtenir des suggestions d'aliments pour un type d'événement
     * 
     * @param typeEvenement Type d'événement
     * @param nombreInvites Nombre d'invités
     * @param budget Budget maximum (optionnel)
     * @return Suggestions d'aliments adaptés
     */
    Map<CategorieFood, List<Food>> obtenirSuggestionsParEvenement(String typeEvenement, 
                                                                 Integer nombreInvites, 
                                                                 Double budget);

    /**
     * Calculer les portions standards par personne
     * 
     * @param categorie Catégorie d'aliment
     * @param typeEvenement Type d'événement
     * @return Portion en grammes par personne
     */
    Double calculerPortionStandard(CategorieFood categorie, String typeEvenement);

    /**
     * Optimiser le buffet selon le budget
     * Ajuste les quantités et remplace par des alternatives moins chères
     * 
     * @param buffetId ID du buffet
     * @param budgetMax Budget maximum
     * @return Buffet optimisé
     */
    Map<String, Object> optimiserBuffetParBudget(Long buffetId, Double budgetMax);

    /**
     * Planifier les temps de préparation
     * 
     * @param buffetId ID du buffet
     * @return Planning de préparation avec horaires
     */
    Map<String, Object> planifierTempsPreparation(Long buffetId);

    /**
     * Dupliquer un buffet existant
     * 
     * @param buffetId ID du buffet à dupliquer
     * @param nouveauNom Nouveau nom de l'événement
     * @param nouvelleDatete Nouvelle date
     * @param nouveauNombreInvites Nouveau nombre d'invités
     * @return ID du nouveau buffet
     */
    Long dupliquerBuffet(Long buffetId, String nouveauNom, LocalDateTime nouvelleDatete, 
                        Integer nouveauNombreInvites);

    /**
     * Générer un rapport détaillé du buffet
     * 
     * @param buffetId ID du buffet
     * @return Rapport complet (aliments, quantités, coûts, nutrition)
     */
    Map<String, Object> genererRapportBuffet(Long buffetId);

    /**
     * Marquer un buffet comme réalisé
     * Permet de sauvegarder les retours d'expérience
     * 
     * @param buffetId ID du buffet
     * @param nombreInvitesReel Nombre d'invités réel
     * @param commentaires Commentaires sur l'événement
     * @return true si marquage réussi
     */
    boolean marquerBuffetRealise(Long buffetId, Integer nombreInvitesReel, String commentaires);

    /**
     * Obtenir l'historique des buffets d'un organisateur
     * 
     * @param organisateurId ID de l'organisateur
     * @return Liste des buffets avec résumés
     */
    List<Map<String, Object>> obtenirHistoriqueBuffets(Long organisateurId);

    /**
     * Calculer les statistiques d'un organisateur
     * 
     * @param organisateurId ID de l'organisateur
     * @return Statistiques (coût moyen, aliments favoris, etc.)
     */
    Map<String, Object> calculerStatistiquesOrganisateur(Long organisateurId);

    /**
     * Valider la faisabilité d'un buffet
     * 
     * @param buffetId ID du buffet
     * @return Liste des problèmes détectés
     */
    List<String> validerFaisabiliteBuffet(Long buffetId);
}