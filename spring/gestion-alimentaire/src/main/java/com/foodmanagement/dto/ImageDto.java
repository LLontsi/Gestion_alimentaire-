package com.foodmanagement.dto;

import com.foodmanagement.enums.TypeImage;
import java.time.LocalDateTime;

/**
 * DTO pour l'affichage des informations d'une image
 */
public class ImageDto {
    
    private Long id;
    private String nomFichier;
    private String urlAcces;
    private TypeImage typeImage;
    private Long tailleFichier;
    private String tailleFichierFormatee;
    private LocalDateTime dateUpload;
    
    // Informations sur l'association
    private Long foodId;
    private String foodNom;
    private Long ingredientId;
    private String ingredientNom;

    // Constructeurs
    public ImageDto() {}

    public ImageDto(Long id, String nomFichier, TypeImage typeImage, Long tailleFichier) {
        this.id = id;
        this.nomFichier = nomFichier;
        this.typeImage = typeImage;
        this.tailleFichier = tailleFichier;
        this.urlAcces = "/api/images/fichier/" + nomFichier;
        this.tailleFichierFormatee = formatTailleFichier(tailleFichier);
    }

    /**
     * Formater la taille du fichier en unités lisibles
     */
    private String formatTailleFichier(Long taille) {
        if (taille == null) return "0 B";
        
        if (taille < 1024) {
            return taille + " B";
        } else if (taille < 1024 * 1024) {
            return String.format("%.1f KB", taille / 1024.0);
        } else {
            return String.format("%.1f MB", taille / (1024.0 * 1024.0));
        }
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomFichier() {
        return nomFichier;
    }

    public void setNomFichier(String nomFichier) {
        this.nomFichier = nomFichier;
        this.urlAcces = "/api/images/fichier/" + nomFichier;
    }

    public String getUrlAcces() {
        return urlAcces;
    }

    public void setUrlAcces(String urlAcces) {
        this.urlAcces = urlAcces;
    }

    public TypeImage getTypeImage() {
        return typeImage;
    }

    public void setTypeImage(TypeImage typeImage) {
        this.typeImage = typeImage;
    }

    public Long getTailleFichier() {
        return tailleFichier;
    }

    public void setTailleFichier(Long tailleFichier) {
        this.tailleFichier = tailleFichier;
        this.tailleFichierFormatee = formatTailleFichier(tailleFichier);
    }

    public String getTailleFichierFormatee() {
        return tailleFichierFormatee;
    }

    public void setTailleFichierFormatee(String tailleFichierFormatee) {
        this.tailleFichierFormatee = tailleFichierFormatee;
    }

    public LocalDateTime getDateUpload() {
        return dateUpload;
    }

    public void setDateUpload(LocalDateTime dateUpload) {
        this.dateUpload = dateUpload;
    }

    public Long getFoodId() {
        return foodId;
    }

    public void setFoodId(Long foodId) {
        this.foodId = foodId;
    }

    public String getFoodNom() {
        return foodNom;
    }

    public void setFoodNom(String foodNom) {
        this.foodNom = foodNom;
    }

    public Long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Long ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getIngredientNom() {
        return ingredientNom;
    }

    public void setIngredientNom(String ingredientNom) {
        this.ingredientNom = ingredientNom;
    }
}