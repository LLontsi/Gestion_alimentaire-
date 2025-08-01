package com.foodmanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO pour la création d'une personne
 * Contient tous les champs nécessaires à la création, y compris le mot de passe
 */
public class PersonneCreateDto {
    
    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 2, max = 100, message = "Le nom doit contenir entre 2 et 100 caractères")
    private String nom;
    
    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format d'email invalide")
    private String email;
    
    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 6, message = "Le mot de passe doit contenir au moins 6 caractères")
    private String motDePasse;
    
    @Size(max = 20, message = "Le téléphone ne peut pas dépasser 20 caractères")
    private String telephone;

    // Constructeurs
    public PersonneCreateDto() {}

    public PersonneCreateDto(String nom, String email, String motDePasse, String telephone) {
        this.nom = nom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.telephone = telephone;
    }

    // Getters et Setters
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

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}