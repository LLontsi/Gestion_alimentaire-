package com.foodmanagement.dto;

import com.foodmanagement.enums.TypeIngredient;
import java.time.LocalDateTime;

/**
 * DTO pour l'affichage des informations d'un ingrédient
 */
public class IngredientDto {
    
    private Long id;
    private String nom;
    private String description;
    private TypeIngredient type;
    private Double quantite;
    private String unite;
    private LocalDateTime dateCreation;
    
    // Informations sur l'utilisation
    private int nombreAlimentsUtilisants;
    private String imageUrl;

    // Constructeurs
    public IngredientDto() {}

    public IngredientDto(Long id, String nom, String description, TypeIngredient type) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.type = type;
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

    public TypeIngredient getType() {
        return type;
    }

    public void setType(TypeIngredient type) {
        this.type = type;
    }

    public Double getQuantite() {
        return quantite;
    }

    public void setQuantite(Double quantite) {
        this.quantite = quantite;
    }

    public String getUnite() {
        return unite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public int getNombreAlimentsUtilisants() {
        return nombreAlimentsUtilisants;
    }

    public void setNombreAlimentsUtilisants(int nombreAlimentsUtilisants) {
        this.nombreAlimentsUtilisants = nombreAlimentsUtilisants;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}