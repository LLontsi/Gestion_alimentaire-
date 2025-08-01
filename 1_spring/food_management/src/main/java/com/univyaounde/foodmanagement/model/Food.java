package com.univyaounde.foodmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "foods")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Food {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Le nom de l'aliment est obligatoire")
    @Column(nullable = false, length = 100)
    private String nom;
    
    @Column(length = 500)
    private String description;
    
    @NotNull(message = "La catégorie est obligatoire")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategorieAliment categorie;
    
    @Positive(message = "Les calories doivent être positives")
    private Double calories;
    
    @Column(name = "date_creation")
    private LocalDateTime dateCreation;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personne_id")
    @JsonIgnore 
    private Personne personne;
    
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "food_ingredients",
        joinColumns = @JoinColumn(name = "food_id"),
        inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    @JsonIgnore 
    private List<Ingredient> ingredients;
    
    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore 
    private List<Image> images;
    
    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
    }
    
    public enum CategorieAliment {
        FRUITS, LEGUMES, CEREALES, PROTEINES, PRODUITS_LAITIERS
    }
}