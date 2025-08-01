package com.foodmanagement.service;

import java.util.List;
import java.util.Map;

/**
 * Interface du service chatbot pour l'assistance alimentaire
 * Chatbot intelligent capable de répondre aux questions sur le domaine alimentaire
 * et d'interagir avec les autres services de l'application
 */
public interface ChatbotService {

    /**
     * Traiter un message de l'utilisateur et générer une réponse
     * 
     * @param message Message de l'utilisateur
     * @param userId ID de l'utilisateur (optionnel pour personnalisation)
     * @return Réponse du chatbot avec contexte
     */
    Map<String, Object> traiterMessage(String message, Long userId);

    /**
     * Analyser l'intention d'un message utilisateur
     * 
     * @param message Message à analyser
     * @return Intention détectée et confiance
     */
    Map<String, Object> analyserIntention(String message);

    /**
     * Suggérer des aliments selon des critères
     * 
     * @param criteres Critères de recherche (catégorie, calories, etc.)
     * @return Suggestions d'aliments avec explications
     */
    Map<String, Object> suggererAliments(Map<String, Object> criteres);

    /**
     * Donner des conseils nutritionnels
     * 
     * @param contexte Contexte (âge, objectifs, restrictions, etc.)
     * @return Conseils personnalisés
     */
    Map<String, Object> donnerConseilsNutritionnels(Map<String, Object> contexte);

    /**
     * Aider à la planification de repas
     * 
     * @param preferences Préférences alimentaires
     * @param nombreJours Nombre de jours à planifier
     * @param userId ID de l'utilisateur
     * @return Plan de repas suggéré
     */
    Map<String, Object> aiderPlanificationRepas(Map<String, Object> preferences, 
                                               Integer nombreJours, Long userId);

    /**
     * Donner des informations sur un aliment spécifique
     * 
     * @param nomAliment Nom de l'aliment
     * @return Informations détaillées sur l'aliment
     */
    Map<String, Object> obtenirInfosAliment(String nomAliment);

    /**
     * Suggérer des alternatives à un aliment
     * 
     * @param nomAliment Aliment à remplacer
     * @param raison Raison du remplacement (allergie, goût, calories, etc.)
     * @return Alternatives suggérées
     */
    Map<String, Object> suggererAlternatives(String nomAliment, String raison);

    /**
     * Calculer des informations nutritionnelles
     * 
     * @param aliments Liste d'aliments avec quantités
     * @return Analyse nutritionnelle
     */
    Map<String, Object> calculerNutrition(List<Map<String, Object>> aliments);

    /**
     * Aider à l'organisation d'un buffet
     * 
     * @param typeEvenement Type d'événement
     * @param nombreInvites Nombre d'invités
     * @param budget Budget disponible
     * @return Conseils pour l'organisation du buffet
     */
    Map<String, Object> aiderOrganisationBuffet(String typeEvenement, 
                                               Integer nombreInvites, Double budget);

    /**
     * Rechercher des recettes selon des ingrédients disponibles
     * 
     * @param ingredients Liste d'ingrédients disponibles
     * @return Recettes possibles
     */
    Map<String, Object> rechercherRecettes(List<String> ingredients);

    /**
     * Obtenir l'historique des conversations d'un utilisateur
     * 
     * @param userId ID de l'utilisateur
     * @param limite Nombre maximum de messages à retourner
     * @return Historique des conversations
     */
    List<Map<String, Object>> obtenirHistoriqueConversation(Long userId, Integer limite);

    /**
     * Réinitialiser le contexte de conversation
     * 
     * @param userId ID de l'utilisateur
     * @return Confirmation de la réinitialisation
     */
    boolean reinitialiserContexte(Long userId);

    /**
     * Obtenir des statistiques d'utilisation du chatbot
     * 
     * @return Statistiques d'usage
     */
    Map<String, Object> obtenirStatistiquesUtilisation();
}
