package com.foodmanagement.service;

import com.foodmanagement.entity.Food;
import com.foodmanagement.enums.CategorieFood;
import java.util.List;
import java.util.Optional;

/**
 * Interface du service pour l'entité Food
 * Définit la logique métier pour la gestion des aliments
 */
public interface FoodService {

    /**
     * Créer un nouvel aliment
     * Inclut les validations métier et vérifications d'unicité
     * 
     * @param food Aliment à créer
     * @return Aliment créé avec son ID
     * @throws IllegalArgumentException Si les données sont invalides
     * @throws RuntimeException Si un aliment avec le même nom existe pour ce créateur
     */
    Food creerAliment(Food food);

    /**
     * Rechercher un aliment par ID
     * 
     * @param id Identifiant de l'aliment
     * @return Optional contenant l'aliment si trouvé
     */
    Optional<Food> obtenirAlimentParId(Long id);

    /**
     * Lister tous les aliments
     * 
     * @return Liste de tous les aliments
     */
    List<Food> listerTousLesAliments();

    /**
     * Rechercher des aliments par nom
     * 
     * @param nom Nom ou partie du nom à rechercher
     * @return Liste des aliments trouvés
     */
    List<Food> rechercherAlimentsParNom(String nom);

    /**
     * Rechercher des aliments par catégorie
     * 
     * @param categorie Catégorie d'aliment
     * @return Liste des aliments de cette catégorie
     */
    List<Food> rechercherAlimentsParCategorie(CategorieFood categorie);

    /**
     * Rechercher des aliments par créateur
     * 
     * @param personneId ID du créateur
     * @return Liste des aliments créés par cette personne
     */
    List<Food> rechercherAlimentsParCreateur(Long personneId);

    /**
     * Mettre à jour un aliment
     * 
     * @param food Aliment avec les nouvelles données
     * @return Aliment mis à jour
     * @throws RuntimeException Si l'aliment n'existe pas
     */
    Food modifierAliment(Food food);

    /**
     * Supprimer un aliment par ID
     * Supprime aussi les images et associations avec ingrédients
     * 
     * @param id Identifiant de l'aliment à supprimer
     * @return true si suppression réussie
     */
    boolean supprimerAliment(Long id);

    /**
     * Ajouter un ingrédient à un aliment
     * 
     * @param foodId ID de l'aliment
     * @param ingredientId ID de l'ingrédient
     * @param quantite Quantité utilisée
     * @param unite Unité de mesure
     * @return true si ajout réussi
     */
    boolean ajouterIngredient(Long foodId, Long ingredientId, Double quantite, String unite);

    /**
     * Supprimer un ingrédient d'un aliment
     * 
     * @param foodId ID de l'aliment
     * @param ingredientId ID de l'ingrédient
     * @return true si suppression réussie
     */
    boolean supprimerIngredient(Long foodId, Long ingredientId);

    /**
     * Calculer les calories totales d'un aliment
     * Basé sur ses ingrédients et leurs quantités
     * 
     * @param foodId ID de l'aliment
     * @return Nombre de calories calculées
     */
    Double calculerCaloriesTotales(Long foodId);

    /**
     * Rechercher des aliments par tranche de calories
     * 
     * @param caloriesMin Calories minimum
     * @param caloriesMax Calories maximum
     * @return Liste des aliments dans cette tranche
     */
    List<Food> rechercherAlimentsParCalories(Double caloriesMin, Double caloriesMax);

    /**
     * Rechercher des aliments par tranche de prix
     * 
     * @param prixMin Prix minimum
     * @param prixMax Prix maximum
     * @return Liste des aliments dans cette tranche
     */
    List<Food> rechercherAlimentsParPrix(Double prixMin, Double prixMax);

    /**
     * Obtenir les aliments les plus populaires
     * Basé sur le nombre d'utilisations ou vues
     * 
     * @param limite Nombre maximum d'aliments à retourner
     * @return Liste des aliments populaires
     */
    List<Food> obtenirAlimentsPopulaires(int limite);

    /**
     * Rechercher des aliments contenant un ingrédient spécifique
     * 
     * @param ingredientId ID de l'ingrédient
     * @return Liste des aliments contenant cet ingrédient
     */
    List<Food> rechercherAlimentsAvecIngredient(Long ingredientId);

    /**
     * Vérifier si un nom d'aliment est disponible pour un créateur
     * 
     * @param nom Nom de l'aliment
     * @param personneId ID du créateur
     * @return true si le nom est disponible
     */
    boolean verifierNomAlimentDisponible(String nom, Long personneId);

    /**
     * Compter le nombre d'aliments
     * 
     * @return Nombre total d'aliments
     */
    long compterAliments();

    /**
     * Valider les données d'un aliment
     * 
     * @param food Aliment à valider
     * @throws IllegalArgumentException Si les données sont invalides
     */
    void validerDonneesAliment(Food food);

    /**
     * Dupliquer un aliment pour un autre créateur
     * 
     * @param foodId ID de l'aliment à dupliquer
     * @param nouveauCreateurId ID du nouveau créateur
     * @return Aliment dupliqué
     */
    Food dupliquerAliment(Long foodId, Long nouveauCreateurId);
}