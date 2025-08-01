package com.foodmanagement.dto;

import com.foodmanagement.enums.CategorieFood;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO pour l'affichage des informations d'un aliment
 */
public class FoodDto {
    
    private Long id;
    private String nom;
    private String description;
    private CategorieFood categorie;
    private Double calories;
    private Double prix;
    private String tempsPreparation;
    private LocalDateTime dateCreation;
    
    // Informations du créateur
    private Long createurId;
    private String createurNom;
    
    // Informations sur les images
    private String imagePrincipaleUrl;
    private int nombreImages;
    
    // Informations sur les ingrédients
    private int nombreIngredients;
    private List<IngredientSimpleDto> ingredients;

    // Constructeurs
    public FoodDto() {}

    public FoodDto(Long id, String nom, String description, CategorieFood categorie) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.categorie = categorie;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Long getCreateurId() {
        return createurId;
    }

    public void setCreateurId(Long createurId) {
        this.createurId = createurId;
    }

    public String getCreateurNom() {
        return createurNom;
    }

    public void setCreateurNom(String createurNom) {
        this.createurNom = createurNom;
    }

    public String getImagePrincipaleUrl() {
        return imagePrincipaleUrl;
    }

    public void setImagePrincipaleUrl(String imagePrincipaleUrl) {
        this.imagePrincipaleUrl = imagePrincipaleUrl;
    }

    public int getNombreImages() {
        return nombreImages;
    }

    public void setNombreImages(int nombreImages) {
        this.nombreImages = nombreImages;
    }

    public int getNombreIngredients() {
        return nombreIngredients;
    }

    public void setNombreIngredients(int nombreIngredients) {
        this.nombreIngredients = nombreIngredients;
    }

    public List<IngredientSimpleDto> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientSimpleDto> ingredients) {
        this.ingredients = ingredients;
    }
}