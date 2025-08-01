package com.foodmanagement.entity;

import com.foodmanagement.enums.TypeImage;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Entité JPA représentant une image dans le système
 * Correspond à la table "image" en base de données
 * Gère les images pour les aliments et ingrédients
 */
@Entity
@Table(name = "image")
public class Image {

    /**
     * Identifiant unique de l'image
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nom du fichier image
     */
    @Column(name = "nom_fichier", nullable = false, length = 255)
    @NotBlank(message = "Le nom du fichier est obligatoire")
    @Size(max = 255, message = "Le nom du fichier ne peut pas dépasser 255 caractères")
    private String nomFichier;

    /**
     * Chemin complet vers le fichier sur le serveur
     */
    @Column(name = "chemin_fichier", nullable = false, length = 500)
    @NotBlank(message = "Le chemin du fichier est obligatoire")
    @Size(max = 500, message = "Le chemin ne peut pas dépasser 500 caractères")
    private String cheminFichier;

    /**
     * Type de l'image (principale, galerie, miniature)
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "type_image", nullable = false, length = 20)
    @NotNull(message = "Le type d'image est obligatoire")
    private TypeImage typeImage;

    /**
     * Taille du fichier en octets
     */
    @Column(name = "taille_fichier")
    @Positive(message = "La taille du fichier doit être positive")
    private Long tailleFichier;

    /**
     * Date d'upload de l'image
     */
    @Column(name = "date_upload", nullable = false, updatable = false)
    private LocalDateTime dateUpload;

    /**
     * Relation Many-to-One avec Food (optionnelle)
     * Plusieurs images peuvent appartenir à un aliment
     * L'image peut appartenir soit à un Food, soit à un Ingredient, soit à aucun
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id")
    private Food food;

    /**
     * Relation One-to-One avec Ingredient (optionnelle)
     * Une image peut appartenir à un ingrédient
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    /**
     * Constructeur par défaut
     */
    public Image() {
    }

    /**
     * Constructeur pour image d'aliment
     */
    public Image(String nomFichier, String cheminFichier, TypeImage typeImage, Long tailleFichier, Food food) {
        this.nomFichier = nomFichier;
        this.cheminFichier = cheminFichier;
        this.typeImage = typeImage;
        this.tailleFichier = tailleFichier;
        this.food = food;
        this.dateUpload = LocalDateTime.now();
    }

    /**
     * Constructeur pour image d'ingrédient
     */
    public Image(String nomFichier, String cheminFichier, TypeImage typeImage, Long tailleFichier, Ingredient ingredient) {
        this.nomFichier = nomFichier;
        this.cheminFichier = cheminFichier;
        this.typeImage = typeImage;
        this.tailleFichier = tailleFichier;
        this.ingredient = ingredient;
        this.dateUpload = LocalDateTime.now();
    }

    /**
     * Méthode appelée avant la persistance
     */
    @PrePersist
    protected void onCreate() {
        this.dateUpload = LocalDateTime.now();
    }

    /**
     * Méthode utilitaire pour vérifier si l'image appartient à un aliment
     */
    public boolean isImageFood() {
        return this.food != null;
    }

    /**
     * Méthode utilitaire pour vérifier si l'image appartient à un ingrédient
     */
    public boolean isImageIngredient() {
        return this.ingredient != null;
    }

    /**
     * Méthode pour obtenir l'URL complète de l'image
     * Utilisé pour l'affichage dans l'interface
     */
    public String getImageUrl() {
        return "/api/images/" + this.nomFichier;
    }

    // ================ GETTERS ET SETTERS ================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomFichier() {
        return nomFichier;
    }

    public void setNomFichier(String nomFichier) {
        this.nomFichier = nomFichier;
    }

    public String getCheminFichier() {
        return cheminFichier;
    }

    public void setCheminFichier(String cheminFichier) {
        this.cheminFichier = cheminFichier;
    }

    public TypeImage getTypeImage() {
        return typeImage;
    }

    public void setTypeImage(TypeImage typeImage) {
        this.typeImage = typeImage;
    }

    public Long getTailleFichier() {
        return tailleFichier;
    }

    public void setTailleFichier(Long tailleFichier) {
        this.tailleFichier = tailleFichier;
    }

    public LocalDateTime getDateUpload() {
        return dateUpload;
    }

    public void setDateUpload(LocalDateTime dateUpload) {
        this.dateUpload = dateUpload;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", nomFichier='" + nomFichier + '\'' +
                ", cheminFichier='" + cheminFichier + '\'' +
                ", typeImage=" + typeImage +
                ", tailleFichier=" + tailleFichier +
                ", dateUpload=" + dateUpload +
                '}';
    }
}