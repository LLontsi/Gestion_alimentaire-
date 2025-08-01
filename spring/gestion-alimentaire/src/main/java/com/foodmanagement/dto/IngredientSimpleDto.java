package com.foodmanagement.dto;

import com.foodmanagement.enums.TypeIngredient;

/**
 * DTO simplifié pour l'ingrédient
 * Utilisé dans les listes et références
 */
public class IngredientSimpleDto {
    
    private Long id;
    private String nom;
    private TypeIngredient type;
    private Double quantiteUtilisee;
    private String unite;

    // Constructeurs
    public IngredientSimpleDto() {}

    public IngredientSimpleDto(Long id, String nom, TypeIngredient type) {
        this.id = id;
        this.nom = nom;
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

    public TypeIngredient getType() {
        return type;
    }

    public void setType(TypeIngredient type) {
        this.type = type;
    }

    public Double getQuantiteUtilisee() {
        return quantiteUtilisee;
    }

    public void setQuantiteUtilisee(Double quantiteUtilisee) {
        this.quantiteUtilisee = quantiteUtilisee;
    }

    public String getUnite() {
        return unite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }
}