package com.foodmanagement.service;

import com.foodmanagement.entity.Image;
import com.foodmanagement.enums.TypeImage;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Interface du service pour l'entité Image
 * Définit la logique métier pour la gestion des images
 */
public interface ImageService {

    /**
     * Uploader une image pour un aliment
     * Gère l'upload, la validation, et la sauvegarde du fichier
     * 
     * @param file Fichier image à uploader
     * @param foodId ID de l'aliment
     * @param typeImage Type d'image
     * @return Image créée avec les infos du fichier
     * @throws IllegalArgumentException Si le fichier ou les paramètres sont invalides
     * @throws RuntimeException Si l'upload échoue
     */
    Image uploaderImageAliment(MultipartFile file, Long foodId, TypeImage typeImage);

    /**
     * Uploader une image pour un ingrédient
     * 
     * @param file Fichier image à uploader
     * @param ingredientId ID de l'ingrédient
     * @param typeImage Type d'image
     * @return Image créée avec les infos du fichier
     */
    Image uploaderImageIngredient(MultipartFile file, Long ingredientId, TypeImage typeImage);

    /**
     * Rechercher une image par ID
     * 
     * @param id Identifiant de l'image
     * @return Optional contenant l'image si trouvée
     */
    Optional<Image> obtenirImageParId(Long id);

    /**
     * Rechercher une image par nom de fichier
     * 
     * @param nomFichier Nom du fichier
     * @return Optional contenant l'image si trouvée
     */
    Optional<Image> obtenirImageParNomFichier(String nomFichier);

    /**
     * Lister toutes les images
     * 
     * @return Liste de toutes les images
     */
    List<Image> listerToutesLesImages();

    /**
     * Rechercher des images par type
     * 
     * @param typeImage Type d'image
     * @return Liste des images de ce type
     */
    List<Image> rechercherImagesParType(TypeImage typeImage);

    /**
     * Rechercher les images d'un aliment
     * 
     * @param foodId ID de l'aliment
     * @return Liste des images de cet aliment
     */
    List<Image> obtenirImagesAliment(Long foodId);

    /**
     * Rechercher l'image principale d'un aliment
     * 
     * @param foodId ID de l'aliment
     * @return Optional contenant l'image principale
     */
    Optional<Image> obtenirImagePrincipaleAliment(Long foodId);

    /**
     * Rechercher l'image d'un ingrédient
     * 
     * @param ingredientId ID de l'ingrédient
     * @return Optional contenant l'image de l'ingrédient
     */
    Optional<Image> obtenirImageIngredient(Long ingredientId);

    /**
     * Mettre à jour une image
     * 
     * @param image Image avec les nouvelles données
     * @return Image mise à jour
     */
    Image modifierImage(Image image);

    /**
     * Supprimer une image par ID
     * Supprime aussi le fichier physique
     * 
     * @param id Identifiant de l'image à supprimer
     * @return true si suppression réussie
     */
    boolean supprimerImage(Long id);

    /**
     * Supprimer toutes les images d'un aliment
     * 
     * @param foodId ID de l'aliment
     * @return Nombre d'images supprimées
     */
    int supprimerToutesImagesAliment(Long foodId);

    /**
     * Changer le type d'une image
     * 
     * @param imageId ID de l'image
     * @param nouveauType Nouveau type d'image
     * @return Image mise à jour
     */
    Image changerTypeImage(Long imageId, TypeImage nouveauType);

    /**
     * Définir une image comme principale pour un aliment
     * Remet les autres images en galerie
     * 
     * @param imageId ID de l'image à définir comme principale
     * @return true si changement réussi
     */
    boolean definirImageCommePrincipale(Long imageId);

    /**
     * Rechercher des images par tranche de taille
     * 
     * @param tailleMin Taille minimum en octets
     * @param tailleMax Taille maximum en octets
     * @return Liste des images dans cette tranche
     */
    List<Image> rechercherImagesParTaille(Long tailleMin, Long tailleMax);

    /**
     * Rechercher des images uploadées entre deux dates
     * 
     * @param dateDebut Date de début
     * @param dateFin Date de fin
     * @return Liste des images uploadées dans cette période
     */
    List<Image> rechercherImagesParPeriode(LocalDateTime dateDebut, LocalDateTime dateFin);

    /**
     * Obtenir les statistiques des images
     * 
     * @return Taille totale des images en octets
     */
    Long obtenirTailleTotaleImages();

    /**
     * Nettoyer les fichiers orphelins
     * Supprime les fichiers physiques sans entrée en base
     * 
     * @return Nombre de fichiers supprimés
     */
    int nettoyerFichiersOrphelins();

    /**
     * Valider un fichier image avant upload
     * 
     * @param file Fichier à valider
     * @throws IllegalArgumentException Si le fichier est invalide
     */
    void validerFichierImage(MultipartFile file);

    /**
     * Générer un nom de fichier unique
     * 
     * @param nomOriginal Nom original du fichier
     * @return Nom de fichier unique
     */
    String genererNomFichierUnique(String nomOriginal);

    /**
     * Compter le nombre d'images
     * 
     * @return Nombre total d'images
     */
    long compterImages();

    /**
     * Compter le nombre d'images par type
     * 
     * @param typeImage Type d'image
     * @return Nombre d'images de ce type
     */
    long compterImagesParType(TypeImage typeImage);
}