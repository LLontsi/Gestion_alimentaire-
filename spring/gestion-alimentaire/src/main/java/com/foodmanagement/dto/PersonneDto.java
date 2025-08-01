package com.foodmanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * DTO pour l'affichage des informations d'une personne
 * Utilisé pour les réponses API (ne contient pas le mot de passe)
 */
public class PersonneDto {
    
    private Long id;
    
    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 2, max = 100, message = "Le nom doit contenir entre 2 et 100 caractères")
    private String nom;
    
    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format d'email invalide")
    private String email;
    
    @Size(max = 20, message = "Le téléphone ne peut pas dépasser 20 caractères")
    private String telephone;
    
    private LocalDateTime dateCreation;
    
    private int nombreAlimentsCreés;

    // Constructeurs
    public PersonneDto() {}

    public PersonneDto(Long id, String nom, String email, String telephone, LocalDateTime dateCreation) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.telephone = telephone;
        this.dateCreation = dateCreation;
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

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public int getNombreAlimentsCreés() {
        return nombreAlimentsCreés;
    }

    public void setNombreAlimentsCreés(int nombreAlimentsCreés) {
        this.nombreAlimentsCreés = nombreAlimentsCreés;
    }
}