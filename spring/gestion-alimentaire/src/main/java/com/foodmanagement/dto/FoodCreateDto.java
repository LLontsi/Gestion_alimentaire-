package com.foodmanagement.dto;

import com.foodmanagement.enums.CategorieFood;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * DTO pour la création d'un aliment
 */
public class FoodCreateDto {
    
    @NotBlank(message = "Le nom de l'aliment est obligatoire")
    @Size(min = 2, max = 100, message = "Le nom doit contenir entre 2 et 100 caractères")
    private String nom;
    
    @Size(max = 500, message = "La description ne peut pas dépasser 500 caractères")
    private String description;
    
    @NotNull(message = "La catégorie est obligatoire")
    private CategorieFood categorie;
    
    @Positive(message = "Les calories doivent être positives")
    private Double calories;
    
    @Positive(message = "Le prix doit être positif")
    private Double prix;
    
    @Size(max = 50, message = "Le temps de préparation ne peut pas dépasser 50 caractères")
    private String tempsPreparation;
    
    @NotNull(message = "L'ID du créateur est obligatoire")
    private Long createurId;

    // Constructeurs
    public FoodCreateDto() {}

    public FoodCreateDto(String nom, CategorieFood categorie, Long createurId) {
        this.nom = nom;
        this.categorie = categorie;
        this.createurId = createurId;
    }

    // Getters et Setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CategorieFood getCategorie() {
        return categorie;
    }

    public void setCategorie(CategorieFood categorie) {
        this.categorie = categorie;
    }

    public Double getCalories() {
        return calories;
    }

    public void setCalories(Double calories) {
        this.calories = calories;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public String getTempsPreparation() {
        return tempsPreparation;
    }

    public void setTempsPreparation(String tempsPreparation) {
        this.tempsPreparation = tempsPreparation;
    }

    public Long getCreateurId() {
        return createurId;
    }

    public void setCreateurId(Long createurId) {
        this.createurId = createurId;
    }
}