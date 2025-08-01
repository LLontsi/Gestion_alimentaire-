package com.univyaounde.foodmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ingredients")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Le nom de l'ingrédient est obligatoire")
    @Column(nullable = false, unique = true, length = 100)
    private String nom;
    
    @Column(length = 300)
    private String description;
    
    @Enumerated(EnumType.STRING)
    private TypeIngredient type;
    
    @Column(name = "valeur_nutritive")
    private String valeurNutritive;
    @JsonIgnore 
    @ManyToMany(mappedBy = "ingredients")
    private List<Food> aliments;
    
    public enum TypeIngredient {
        NATUREL, CONSERVATEUR, COLORANT, AROME, AUTRE
    }
}