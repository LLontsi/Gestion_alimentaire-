package com.univyaounde.foodmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "images")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Image {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Le nom du fichier est obligatoire")
    @Column(nullable = false, length = 255)
    private String nomFichier;
    
    @NotBlank(message = "Le chemin est obligatoire")
    @Column(nullable = false, length = 500)
    private String chemin;
    
    @Column(name = "type_mime", length = 100)
    private String typeMime;
    
    private Long taille;
    
    @Column(length = 500)
    private String description;
    
    @Column(name = "date_upload")
    private LocalDateTime dateUpload;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id")
    @JsonIgnore 
    private Food food;
    
    @PrePersist
    protected void onCreate() {
        dateUpload = LocalDateTime.now();
    }
}