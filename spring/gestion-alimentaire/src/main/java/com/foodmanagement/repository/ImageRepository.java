package com.foodmanagement.repository;

import com.foodmanagement.entity.Image;
import com.foodmanagement.enums.TypeImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository pour l'entité Image
 * Gère les opérations CRUD et recherches pour les images
 */
@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    /**
     * Rechercher une image par son nom de fichier
     * 
     * @param nomFichier Nom du fichier
     * @return Optional contenant l'image si trouvée
     */
    Optional<Image> findByNomFichier(String nomFichier);

    /**
     * Rechercher des images par type
     * 
     * @param typeImage Type d'image
     * @return Liste des images de ce type
     */
    List<Image> findByTypeImage(TypeImage typeImage);

    /**
     * Rechercher des images d'un aliment spécifique
     * 
     * @param foodId ID de l'aliment
     * @return Liste des images de cet aliment
     */
    @Query("SELECT i FROM Image i WHERE i.food.id = :foodId ORDER BY i.dateUpload DESC")
    List<Image> findByFoodId(@Param("foodId") Long foodId);

    /**
     * Rechercher l'image principale d'un aliment
     * 
     * @param foodId ID de l'aliment
     * @return Optional contenant l'image principale
     */
    @Query("SELECT i FROM Image i WHERE i.food.id = :foodId AND i.typeImage = 'PRINCIPALE'")
    Optional<Image> findMainImageByFoodId(@Param("foodId") Long foodId);

    /**
     * Rechercher l'image d'un ingrédient
     * 
     * @param ingredientId ID de l'ingrédient
     * @return Optional contenant l'image de l'ingrédient
     */
    @Query("SELECT i FROM Image i WHERE i.ingredient.id = :ingredientId")
    Optional<Image> findByIngredientId(@Param("ingredientId") Long ingredientId);

    /**
     * Rechercher des images par tranche de taille
     * 
     * @param tailleMin Taille minimum en octets
     * @param tailleMax Taille maximum en octets
     * @return Liste des images dans cette tranche
     */
    @Query("SELECT i FROM Image i WHERE i.tailleFichier BETWEEN :tailleMin AND :tailleMax")
    List<Image> findByTailleFichierBetween(@Param("tailleMin") Long tailleMin, 
                                         @Param("tailleMax") Long tailleMax);

    /**
     * Rechercher des images uploadées entre deux dates
     * 
     * @param dateDebut Date de début
     * @param dateFin Date de fin
     * @return Liste des images uploadées dans cette période
     */
    @Query("SELECT i FROM Image i WHERE i.dateUpload BETWEEN :dateDebut AND :dateFin ORDER BY i.dateUpload DESC")
    List<Image> findByDateUploadBetween(@Param("dateDebut") LocalDateTime dateDebut, 
                                      @Param("dateFin") LocalDateTime dateFin);

    /**
     * Rechercher toutes les images d'aliments
     * 
     * @return Liste des images associées à des aliments
     */
    @Query("SELECT i FROM Image i WHERE i.food IS NOT NULL ORDER BY i.dateUpload DESC")
    List<Image> findAllFoodImages();

    /**
     * Rechercher toutes les images d'ingrédients
     * 
     * @return Liste des images associées à des ingrédients
     */
    @Query("SELECT i FROM Image i WHERE i.ingredient IS NOT NULL ORDER BY i.dateUpload DESC")
    List<Image> findAllIngredientImages();

    /**
     * Compter le nombre d'images par type
     * 
     * @param typeImage Type d'image
     * @return Nombre d'images de ce type
     */
    long countByTypeImage(TypeImage typeImage);

    /**
     * Rechercher les images les plus volumineuses
     * 
     * @return Liste des 10 images les plus lourdes
     */
    @Query("SELECT i FROM Image i ORDER BY i.tailleFichier DESC LIMIT 10")
    List<Image> findTop10LargestImages();

    /**
     * Rechercher des images par extension de fichier
     * 
     * @param extension Extension à rechercher (jpg, png, etc.)
     * @return Liste des images avec cette extension
     */
    @Query("SELECT i FROM Image i WHERE LOWER(i.nomFichier) LIKE LOWER(CONCAT('%', :extension))")
    List<Image> findByFileExtension(@Param("extension") String extension);

    /**
     * Vérifier si un nom de fichier existe déjà
     * 
     * @param nomFichier Nom du fichier
     * @return true si le fichier existe
     */
    boolean existsByNomFichier(String nomFichier);

    /**
     * Supprimer des images par aliment
     * Utile lors de la suppression d'un aliment
     * 
     * @param foodId ID de l'aliment
     */
    @Query("DELETE FROM Image i WHERE i.food.id = :foodId")
    void deleteByFoodId(@Param("foodId") Long foodId);

    /**
     * Supprimer l'image d'un ingrédient
     * 
     * @param ingredientId ID de l'ingrédient
     */
    @Query("DELETE FROM Image i WHERE i.ingredient.id = :ingredientId")
    void deleteByIngredientId(@Param("ingredientId") Long ingredientId);

    /**
     * Calculer la taille totale des images
     * 
     * @return Taille totale en octets
     */
    @Query("SELECT SUM(i.tailleFichier) FROM Image i")
    Long getTotalImagesSize();

    /**
     * Rechercher les images récemment uploadées
     * 
     * @return Liste des 20 images les plus récentes
     */
    @Query("SELECT i FROM Image i ORDER BY i.dateUpload DESC LIMIT 20")
    List<Image> findTop20ByOrderByDateUploadDesc();
}