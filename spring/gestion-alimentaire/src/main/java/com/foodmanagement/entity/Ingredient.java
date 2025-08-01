package com.foodmanagement.entity;

import com.foodmanagement.enums.TypeIngredient;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Entité JPA représentant un ingrédient dans le système
 * Correspond à la table "ingredient" en base de données
 */
@Entity
@Table(name = "ingredient")
public class Ingredient {

    /**
     * Identifiant unique de l'ingrédient
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nom de l'ingrédient
     */
    @Column(nullable = false, length = 100)
    @NotBlank(message = "Le nom de l'ingrédient est obligatoire")
    @Size(min = 2, max = 100, message = "Le nom doit contenir entre 2 et 100 caractères")
    private String nom;

    /**
     * Description de l'ingrédient
     */
    @Column(length = 300)
    @Size(max = 300, message = "La description ne peut pas dépasser 300 caractères")
    private String description;

    /**
     * Type de l'ingrédient (frais, surgelé, etc.)
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @NotNull(message = "Le type d'ingrédient est obligatoire")
    private TypeIngredient type;

    /**
     * Quantité de base (pour 1 unité)
     */
    @Column
    @Positive(message = "La quantité doit être positive")
    private Double quantite;

    /**
     * Unité de mesure (grammes, litres, pièces, etc.)
     */
    @Column(length = 20)
    @Size(max = 20, message = "L'unité ne peut pas dépasser 20 caractères")
    private String unite;

    /**
     * Date de création de l'enregistrement
     */
    @Column(name = "date_creation", nullable = false, updatable = false)
    private LocalDateTime dateCreation;

    /**
     * Relation One-to-Many avec FoodIngredient
     * Un ingrédient peut être utilisé dans plusieurs aliments
     */
    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FoodIngredient> foodIngredients;

    /**
     * Relation One-to-One avec Image (optionnelle)
     * Un ingrédient peut avoir une image
     */
    @OneToOne(mappedBy = "ingredient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Image image;

    /**
     * Constructeur par défaut
     */
    public Ingredient() {
    }

    /**
     * Constructeur avec paramètres essentiels
     */
    public Ingredient(String nom, String description, TypeIngredient type) {
        this.nom = nom;
        this.description = description;
        this.type = type;
        this.dateCreation = LocalDateTime.now();
    }

    /**
     * Méthode appelée avant la persistance
     */
    @PrePersist
    protected void onCreate() {
        this.dateCreation = LocalDateTime.now();
    }

    // ================ GETTERS ET SETTERS ================

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TypeIngredient getType() {
        return type;
    }

    public void setType(TypeIngredient type) {
        this.type = type;
    }

    public Double getQuantite() {
        return quantite;
    }

    public void setQuantite(Double quantite) {
        this.quantite = quantite;
    }

    public String getUnite() {
        return unite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public List<FoodIngredient> getFoodIngredients() {
        return foodIngredients;
    }

    public void setFoodIngredients(List<FoodIngredient> foodIngredients) {
        this.foodIngredients = foodIngredients;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", type=" + type +
                ", quantite=" + quantite +
                ", unite='" + unite + '\'' +
                ", dateCreation=" + dateCreation +
                '}';
    }
}