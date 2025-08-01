package com.foodmanagement.service;

import com.foodmanagement.entity.Food;
import com.foodmanagement.entity.Personne;
import com.foodmanagement.enums.CategorieFood;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Interface du service pour la planification des repas
 * Module métier pour l'exercice 3 - Planification hebdomadaire des repas
 */
public interface PlanificationService {

    /**
     * Créer un plan de repas hebdomadaire pour une personne
     * 
     * @param personneId ID de la personne
     * @param dateDebut Date de début de la semaine
     * @param preferences Préférences alimentaires (catégories préférées)
     * @return Plan de repas organisé par jour
     */
    Map<LocalDate, Map<String, List<Food>>> creerPlanHebdomadaire(Long personneId, 
                                                                LocalDate dateDebut, 
                                                                List<CategorieFood> preferences);

    /**
     * Générer un plan de repas automatique
     * Basé sur les aliments disponibles et les préférences
     * 
     * @param personneId ID de la personne
     * @param dateDebut Date de début
     * @param nombreJours Nombre de jours à planifier
     * @return Plan automatique
     */
    Map<LocalDate, Map<String, List<Food>>> genererPlanAutomatique(Long personneId, 
                                                                  LocalDate dateDebut, 
                                                                  int nombreJours);

    /**
     * Ajouter un aliment à un repas spécifique
     * 
     * @param personneId ID de la personne
     * @param date Date du repas
     * @param typeRepas Type de repas (petit-déjeuner, déjeuner, dîner)
     * @param foodId ID de l'aliment à ajouter
     * @return true si ajout réussi
     */
    boolean ajouterAlimentAuPlan(Long personneId, LocalDate date, String typeRepas, Long foodId);

    /**
     * Supprimer un aliment d'un repas
     * 
     * @param personneId ID de la personne
     * @param date Date du repas
     * @param typeRepas Type de repas
     * @param foodId ID de l'aliment à supprimer
     * @return true si suppression réussie
     */
    boolean supprimerAlimentDuPlan(Long personneId, LocalDate date, String typeRepas, Long foodId);

    /**
     * Obtenir le plan de repas d'une personne pour une période
     * 
     * @param personneId ID de la personne
     * @param dateDebut Date de début
     * @param dateFin Date de fin
     * @return Plan de repas pour la période
     */
    Map<LocalDate, Map<String, List<Food>>> obtenirPlanRepas(Long personneId, 
                                                            LocalDate dateDebut, 
                                                            LocalDate dateFin);

    /**
     * Calculer les calories totales d'un jour
     * 
     * @param planJour Plan d'une journée
     * @return Nombre total de calories
     */
    Double calculerCaloriesJour(Map<String, List<Food>> planJour);

    /**
     * Calculer les calories totales d'une semaine
     * 
     * @param planSemaine Plan de la semaine
     * @return Calories par jour et total
     */
    Map<String, Double> calculerCaloriesSemaine(Map<LocalDate, Map<String, List<Food>>> planSemaine);

    /**
     * Suggérer des alternatives pour un repas
     * Basé sur les calories et la catégorie
     * 
     * @param alimentOriginal Aliment à remplacer
     * @param categoriePreferee Catégorie préférée (optionnel)
     * @return Liste d'alternatives
     */
    List<Food> suggererAlternatives(Food alimentOriginal, CategorieFood categoriePreferee);

    /**
     * Valider un plan de repas
     * Vérifications nutritionnelles et cohérence
     * 
     * @param planSemaine Plan à valider
     * @return Liste des problèmes détectés
     */
    List<String> validerPlanRepas(Map<LocalDate, Map<String, List<Food>>> planSemaine);

    /**
     * Dupliquer un plan de semaine
     * 
     * @param personneId ID de la personne
     * @param semaineSources Date de la semaine à dupliquer
     * @param semaineDestination Date de la semaine de destination
     * @return Plan dupliqué
     */
    Map<LocalDate, Map<String, List<Food>>> dupliquerPlanSemaine(Long personneId, 
                                                               LocalDate semaineSources, 
                                                               LocalDate semaineDestination);

    /**
     * Générer une liste de courses
     * Basée sur le plan de repas
     * 
     * @param planSemaine Plan de la semaine
     * @return Liste des ingrédients avec quantités
     */
    Map<String, Double> genererListeCourses(Map<LocalDate, Map<String, List<Food>>> planSemaine);

    /**
     * Obtenir les statistiques de planification
     * 
     * @param personneId ID de la personne
     * @param mois Mois de référence
     * @return Statistiques (calories moyennes, aliments favoris, etc.)
     */
    Map<String, Object> obtenirStatistiquesPlanification(Long personneId, LocalDate mois);
}