package com.foodmanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO pour la mise à jour d'une personne
 * Ne contient pas le mot de passe (modification séparée)
 */
public class PersonneUpdateDto {
    
    @NotNull(message = "L'ID est obligatoire pour la modification")
    private Long id;
    
    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 2, max = 100, message = "Le nom doit contenir entre 2 et 100 caractères")
    private String nom;
    
    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format d'email invalide")
    private String email;
    
    @Size(max = 20, message = "Le téléphone ne peut pas dépasser 20 caractères")
    private String telephone;

    // Constructeurs
    public PersonneUpdateDto() {}

    public PersonneUpdateDto(Long id, String nom, String email, String telephone) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.telephone = telephone;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}