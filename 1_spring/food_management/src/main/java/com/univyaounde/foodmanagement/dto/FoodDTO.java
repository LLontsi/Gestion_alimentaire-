package com.univyaounde.foodmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodDTO {
    
    private Long id;
    
    @NotBlank(message = "Le nom de l'aliment est obligatoire")
    private String nom;
    
    private String description;
    
    @NotNull(message = "La catégorie est obligatoire")
    private String categorie;
    
    @Positive(message = "Les calories doivent être positives")
    private Double calories;
    
    private LocalDateTime dateCreation;
    
    private Long personneId;
    
    private String personneNom;
    
    private List<IngredientDTO> ingredients;
    
    private List<ImageDTO> images;
    
    private Integer nombreIngredients;
    
    private Integer nombreImages;
}