package com.foodmanagement.entity;

import com.foodmanagement.enums.CategorieFood;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Entité JPA représentant un aliment/plat dans le système
 * Correspond à la table "food" en base de données
 */
@Entity
@Table(name = "food")
public class Food {

    /**
     * Identifiant unique de l'aliment
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nom de l'aliment
     */
    @Column(nullable = false, length = 100)
    @NotBlank(message = "Le nom de l'aliment est obligatoire")
    @Size(min = 2, max = 100, message = "Le nom doit contenir entre 2 et 100 caractères")
    private String nom;

    /**
     * Description de l'aliment
     */
    @Column(length = 500)
    @Size(max = 500, message = "La description ne peut pas dépasser 500 caractères")
    private String description;

    /**
     * Catégorie de l'aliment (énumération)
     * @Enumerated : Spécifie comment stocker l'enum en base
     * EnumType.STRING : Stocke le nom de l'enum (recommandé vs ORDINAL)
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @NotNull(message = "La catégorie est obligatoire")
    private CategorieFood categorie;

    /**
     * Nombre de calories pour 100g
     */
    @Column
    @Positive(message = "Les calories doivent être positives")
    private Double calories;

    /**
     * Prix de l'aliment en euros
     */
    @Column
    @Positive(message = "Le prix doit être positif")
    private Double prix;

    /**
     * Temps de préparation en minutes
     */
    @Column(name = "temps_preparation", length = 50)
    private String tempsPreparation;

    /**
     * Date de création de l'enregistrement
     */
    @Column(name = "date_creation", nullable = false, updatable = false)
    private LocalDateTime dateCreation;

    /**
     * Relation Many-to-One avec Personne
     * Plusieurs aliments peuvent être créés par une personne
     * @JoinColumn : Spécifie la clé étrangère
     * fetch = LAZY : Chargement paresseux de la personne
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personne_id", nullable = false)
    @NotNull(message = "La personne créatrice est obligatoire")
    private Personne personne;

    /**
     * Relation One-to-Many avec Image
     * Un aliment peut avoir plusieurs images
     */
    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Image> images;

    /**
     * Relation One-to-Many avec FoodIngredient
     * Un aliment peut contenir plusieurs ingrédients
     */
    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FoodIngredient> foodIngredients;

    /**
     * Constructeur par défaut
     */
    public Food() {
    }

    /**
     * Constructeur avec paramètres essentiels
     */
    public Food(String nom, String description, CategorieFood categorie, Personne personne) {
        this.nom = nom;
        this.description = description;
        this.categorie = categorie;
        this.personne = personne;
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

    public CategorieFood getCategorie() {
        return categorie;
    }

    public void setCategorie(CategorieFood categorie) {
        this.categorie = categorie;
    }

    public Double getCalories() {
        return calories;
    }

    public void setCalories(Double calories) {
        this.calories = calories;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public String getTempsPreparation() {
        return tempsPreparation;
    }

    public void setTempsPreparation(String tempsPreparation) {
        this.tempsPreparation = tempsPreparation;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Personne getPersonne() {
        return personne;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<FoodIngredient> getFoodIngredients() {
        return foodIngredients;
    }

    public void setFoodIngredients(List<FoodIngredient> foodIngredients) {
        this.foodIngredients = foodIngredients;
    }

    @Override
    public String toString() {
        return "Food{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", categorie=" + categorie +
                ", calories=" + calories +
                ", prix=" + prix +
                ", tempsPreparation='" + tempsPreparation + '\'' +
                ", dateCreation=" + dateCreation +
                '}';
    }
}