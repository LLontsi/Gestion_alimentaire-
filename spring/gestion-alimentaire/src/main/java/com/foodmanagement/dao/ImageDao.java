package com.foodmanagement.dao;

import com.foodmanagement.entity.Image;
import com.foodmanagement.enums.TypeImage;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Interface DAO pour l'entité Image
 * Définit les opérations CRUD pour les images avec JDBC
 */
public interface ImageDao {

    /**
     * Créer une nouvelle image
     * 
     * @param image Image à créer
     * @return Image créée avec son ID
     */
    Image create(Image image);

    /**
     * Rechercher une image par ID
     * 
     * @param id Identifiant de l'image
     * @return Optional contenant l'image si trouvée
     */
    Optional<Image> findById(Long id);

    /**
     * Rechercher une image par nom de fichier
     * 
     * @param nomFichier Nom du fichier
     * @return Optional contenant l'image si trouvée
     */
    Optional<Image> findByNomFichier(String nomFichier);

    /**
     * Rechercher toutes les images
     * 
     * @return Liste de toutes les images
     */
    List<Image> findAll();

    /**
     * Rechercher des images par type
     * 
     * @param typeImage Type d'image
     * @return Liste des images de ce type
     */
    List<Image> findByTypeImage(TypeImage typeImage);

    /**
     * Rechercher des images d'un aliment
     * 
     * @param foodId ID de l'aliment
     * @return Liste des images de cet aliment
     */
    List<Image> findByFoodId(Long foodId);

    /**
     * Rechercher l'image principale d'un aliment
     * 
     * @param foodId ID de l'aliment
     * @return Optional contenant l'image principale
     */
    Optional<Image> findMainImageByFoodId(Long foodId);

    /**
     * Rechercher l'image d'un ingrédient
     * 
     * @param ingredientId ID de l'ingrédient
     * @return Optional contenant l'image de l'ingrédient
     */
    Optional<Image> findByIngredientId(Long ingredientId);

    /**
     * Mettre à jour une image
     * 
     * @param image Image avec les nouvelles données
     * @return Image mise à jour
     */
    Image update(Image image);

    /**
     * Supprimer une image par ID
     * 
     * @param id Identifiant de l'image
     * @return true si suppression réussie
     */
    boolean deleteById(Long id);

    /**
     * Supprimer toutes les images d'un aliment
     * 
     * @param foodId ID de l'aliment
     * @return Nombre d'images supprimées
     */
    int deleteByFoodId(Long foodId);

    /**
     * Vérifier si un nom de fichier existe
     * 
     * @param nomFichier Nom du fichier
     * @return true si le fichier existe
     */
    boolean existsByNomFichier(String nomFichier);

    /**
     * Compter le nombre d'images
     * 
     * @return Nombre total d'images
     */
    long count();

    /**
     * Rechercher des images par tranche de taille
     * 
     * @param tailleMin Taille minimum en octets
     * @param tailleMax Taille maximum en octets
     * @return Liste des images dans cette tranche
     */
    List<Image> findByTailleFichierBetween(Long tailleMin, Long tailleMax);

    /**
     * Rechercher des images uploadées entre deux dates
     * 
     * @param dateDebut Date de début
     * @param dateFin Date de fin
     * @return Liste des images uploadées dans cette période
     */
    List<Image> findByDateUploadBetween(LocalDateTime dateDebut, LocalDateTime dateFin);
}