package com.univyaounde.foodmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "personnes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Personne {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 2, max = 50, message = "Le nom doit contenir entre 2 et 50 caractères")
    @Column(nullable = false, length = 50)
    private String nom;
    
    @NotBlank(message = "Le prénom est obligatoire")
    @Size(min = 2, max = 50, message = "Le prénom doit contenir entre 2 et 50 caractères")
    @Column(nullable = false, length = 50)
    private String prenom;
    
    @Email(message = "L'email doit être valide")
    @Column(unique = true)
    private String email;
    
    @Column(name = "date_naissance")
    private LocalDate dateNaissance;
    
    @Column(length = 15)
    private String telephone;
    
    @OneToMany(mappedBy = "personne", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore 
    private List<Food> aliments;

   
    
}