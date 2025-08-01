package com.foodmanagement.service;

import com.foodmanagement.entity.Ingredient;
import com.foodmanagement.enums.TypeIngredient;
import java.util.List;
import java.util.Optional;

/**
 * Interface du service pour l'entité Ingredient
 * Définit la logique métier pour la gestion des ingrédients
 */
public interface IngredientService {

    /**
     * Créer un nouvel ingrédient
     * Inclut les validations métier et vérifications d'unicité
     * 
     * @param ingredient Ingrédient à créer
     * @return Ingrédient créé avec son ID
     * @throws IllegalArgumentException Si les données sont invalides
     * @throws RuntimeException Si un ingrédient avec le même nom existe déjà
     */
    Ingredient creerIngredient(Ingredient ingredient);

    /**
     * Rechercher un ingrédient par ID
     * 
     * @param id Identifiant de l'ingrédient
     * @return Optional contenant l'ingrédient si trouvé
     */
    Optional<Ingredient> obtenirIngredientParId(Long id);

    /**
     * Rechercher un ingrédient par nom exact
     * 
     * @param nom Nom de l'ingrédient
     * @return Optional contenant l'ingrédient si trouvé
     */
    Optional<Ingredient> obtenirIngredientParNom(String nom);

    /**
     * Lister tous les ingrédients
     * 
     * @return Liste de tous les ingrédients
     */
    List<Ingredient> listerTousLesIngredients();

    /**
     * Rechercher des ingrédients par nom (recherche partielle)
     * 
     * @param nom Nom ou partie du nom à rechercher
     * @return Liste des ingrédients trouvés
     */
    List<Ingredient> rechercherIngredientsParNom(String nom);

    /**
     * Rechercher des ingrédients par type
     * 
     * @param type Type d'ingrédient
     * @return Liste des ingrédients de ce type
     */
    List<Ingredient> rechercherIngredientsParType(TypeIngredient type);

    /**
     * Rechercher des ingrédients par unité
     * 
     * @param unite Unité de mesure
     * @return Liste des ingrédients avec cette unité
     */
    List<Ingredient> rechercherIngredientsParUnite(String unite);

    /**
     * Mettre à jour un ingrédient
     * 
     * @param ingredient Ingrédient avec les nouvelles données
     * @return Ingrédient mis à jour
     * @throws RuntimeException Si l'ingrédient n'existe pas
     */
    Ingredient modifierIngredient(Ingredient ingredient);

    /**
     * Supprimer un ingrédient par ID
     * Vérifie qu'il n'est pas utilisé dans des aliments
     * 
     * @param id Identifiant de l'ingrédient à supprimer
     * @return true si suppression réussie
     * @throws RuntimeException Si l'ingrédient est utilisé dans des aliments
     */
    boolean supprimerIngredient(Long id);

    /**
     * Vérifier si un nom d'ingrédient est disponible
     * 
     * @param nom Nom à vérifier
     * @return true si le nom est disponible
     */
    boolean verifierNomIngredientDisponible(String nom);

    /**
     * Rechercher des ingrédients par tranche de quantité
     * 
     * @param quantiteMin Quantité minimum
     * @param quantiteMax Quantité maximum
     * @return Liste des ingrédients dans cette tranche
     */
    List<Ingredient> rechercherIngredientsParQuantite(Double quantiteMin, Double quantiteMax);

    /**
     * Lister les ingrédients utilisés dans des aliments
     * 
     * @return Liste des ingrédients utilisés
     */
    List<Ingredient> listerIngredientsUtilises();

    /**
     * Lister les ingrédients non utilisés
     * Utile pour le nettoyage des données
     * 
     * @return Liste des ingrédients non utilisés
     */
    List<Ingredient> listerIngredientsNonUtilises();

    /**
     * Obtenir les ingrédients les plus utilisés
     * 
     * @param limite Nombre maximum d'ingrédients à retourner
     * @return Liste des ingrédients populaires
     */
    List<Ingredient> obtenirIngredientsPopulaires(int limite);

    /**
     * Rechercher des ingrédients d'un aliment spécifique
     * 
     * @param foodId ID de l'aliment
     * @return Liste des ingrédients de cet aliment
     */
    List<Ingredient> rechercherIngredientsParAliment(Long foodId);

    /**
     * Compter le nombre d'ingrédients
     * 
     * @return Nombre total d'ingrédients
     */
    long compterIngredients();

    /**
     * Compter le nombre d'ingrédients par type
     * 
     * @param type Type d'ingrédient
     * @return Nombre d'ingrédients de ce type
     */
    long compterIngredientsParType(TypeIngredient type);

    /**
     * Valider les données d'un ingrédient
     * 
     * @param ingredient Ingrédient à valider
     * @throws IllegalArgumentException Si les données sont invalides
     */
    void validerDonneesIngredient(Ingredient ingredient);

    /**
     * Suggérer des ingrédients similaires
     * Basé sur le nom et le type
     * 
     * @param nomPartiel Nom partiel de l'ingrédient
     * @param type Type d'ingrédient (optionnel)
     * @return Liste des suggestions
     */
    List<Ingredient> suggererIngredients(String nomPartiel, TypeIngredient type);
}