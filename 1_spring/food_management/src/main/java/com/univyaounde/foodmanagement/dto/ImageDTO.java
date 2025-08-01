package com.univyaounde.foodmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageDTO {
    
    private Long id;
    
    @NotBlank(message = "Le nom du fichier est obligatoire")
    private String nomFichier;
    
    @NotBlank(message = "Le chemin est obligatoire")
    private String chemin;
    
    private String typeMime;
    
    private Long taille;
    
    private String description;
    
    private LocalDateTime dateUpload;
    
    private Long foodId;
    
    private String foodNom;
}