package com.foodmanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Entité JPA représentant une personne dans le système
 * Correspond à la table "personne" en base de données
 */
@Entity
@Table(name = "personne")
public class Personne {

    /**
     * Identifiant unique de la personne
     * @Id : Clé primaire
     * @GeneratedValue : Valeur générée automatiquement
     * IDENTITY : Utilise l'auto-increment de la base de données
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nom de la personne
     * @Column : Configuration de la colonne en base
     * nullable = false : Champ obligatoire
     * length = 100 : Taille maximale
     * @NotBlank : Validation - ne peut pas être null, vide ou contenir que des espaces
     * @Size : Validation de la taille
     */
    @Column(nullable = false, length = 100)
    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 2, max = 100, message = "Le nom doit contenir entre 2 et 100 caractères")
    private String nom;

    /**
     * Adresse email de la personne
     * unique = true : Contrainte d'unicité en base
     * @Email : Validation du format email
     */
    @Column(nullable = false, unique = true, length = 150)
    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format d'email invalide")
    private String email;

    /**
     * Mot de passe de la personne
     * En production, ce champ devrait être haché
     */
    @Column(nullable = false, length = 255)
    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 6, message = "Le mot de passe doit contenir au moins 6 caractères")
    private String motDePasse;

    /**
     * Numéro de téléphone (optionnel)
     */
    @Column(length = 20)
    private String telephone;

    /**
     * Date de création du compte
     * @Column : Configuration pour ne pas permettre les updates
     * updatable = false : Empêche la modification après création
     */
    @Column(name = "date_creation", nullable = false, updatable = false)
    private LocalDateTime dateCreation;

    /**
     * Relation One-to-Many avec Food
     * Une personne peut créer plusieurs aliments
     * mappedBy : Indique que la relation est mappée par l'attribut "personne" dans Food
     * cascade : Définit les opérations en cascade
     * fetch = LAZY : Chargement paresseux (optimisation performance)
     */
    @OneToMany(mappedBy = "personne", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Food> foods;

    /**
     * Constructeur par défaut
     * Obligatoire pour JPA
     */
    public Personne() {
    }

    /**
     * Constructeur avec paramètres
     * @param nom Nom de la personne
     * @param email Email de la personne
     * @param motDePasse Mot de passe
     * @param telephone Téléphone (optionnel)
     */
    public Personne(String nom, String email, String motDePasse, String telephone) {
        this.nom = nom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.telephone = telephone;
        this.dateCreation = LocalDateTime.now();
    }

    /**
     * Méthode appelée avant la persistance en base
     * @PrePersist : Annotation JPA pour exécuter avant l'insertion
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public List<Food> getFoods() {
        return foods;
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
    }

    /**
     * Méthode toString pour l'affichage et le débogage
     * @return Représentation textuelle de l'objet
     */
    @Override
    public String toString() {
        return "Personne{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", dateCreation=" + dateCreation +
                '}';
    }

    /**
     * Méthode equals pour comparer deux objets Personne
     * Basée sur l'email (unique)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Personne personne = (Personne) obj;
        return email != null && email.equals(personne.email);
    }

    /**
     * Méthode hashCode cohérente avec equals
     */
    @Override
    public int hashCode() {
        return email != null ? email.hashCode() : 0;
    }
}