package com.univyaounde.foodmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientDTO {
    
    private Long id;
    
    @NotBlank(message = "Le nom de l'ingrédient est obligatoire")
    @Size(max = 100, message = "Le nom ne peut pas dépasser 100 caractères")
    private String nom;
    
    @Size(max = 300, message = "La description ne peut pas dépasser 300 caractères")
    private String description;
    
    // Type comme String pour le frontend, sera converti en enum côté backend
    private String type;
    
    // Constructeur pour la création rapide
    public IngredientDTO(String nom, String description, String type) {
        this.nom = nom;
        this.description = description;
        this.type = type;
    }
}