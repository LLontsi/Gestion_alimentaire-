package com.foodmanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * Entité JPA représentant la relation Many-to-Many entre Food et Ingredient
 * Table de liaison avec attributs supplémentaires (quantité, unité)
 * Correspond à la table "food_ingredient" en base de données
 */
@Entity
@Table(name = "food_ingredient")
public class FoodIngredient {

    /**
     * Clé primaire composite
     * @EmbeddedId : Utilise une classe séparée pour la clé composite
     * Alternativement, on peut utiliser @IdClass ou deux @Id séparés
     */
    @EmbeddedId
    private FoodIngredientId id;

    /**
     * Référence vers l'aliment
     * @MapsId : Indique que cette relation fait partie de la clé primaire
     * "foodId" correspond au champ dans FoodIngredientId
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("foodId")
    @JoinColumn(name = "food_id")
    private Food food;

    /**
     * Référence vers l'ingrédient
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("ingredientId")
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    /**
     * Quantité utilisée de cet ingrédient dans l'aliment
     */
    @Column(name = "quantite_utilisee", nullable = false)
    @NotNull(message = "La quantité utilisée est obligatoire")
    @Positive(message = "La quantité doit être positive")
    private Double quantiteUtilisee;

    /**
     * Unité de la quantité (grammes, litres, pièces, etc.)
     */
    @Column(nullable = false, length = 20)
    @NotNull(message = "L'unité est obligatoire")
    @Size(max = 20, message = "L'unité ne peut pas dépasser 20 caractères")
    private String unite;

    /**
     * Constructeur par défaut
     */
    public FoodIngredient() {
    }

    /**
     * Constructeur avec paramètres
     */
    public FoodIngredient(Food food, Ingredient ingredient, Double quantiteUtilisee, String unite) {
        this.food = food;
        this.ingredient = ingredient;
        this.quantiteUtilisee = quantiteUtilisee;
        this.unite = unite;
        this.id = new FoodIngredientId(food.getId(), ingredient.getId());
    }

    // ================ GETTERS ET SETTERS ================

    public FoodIngredientId getId() {
        return id;
    }

    public void setId(FoodIngredientId id) {
        this.id = id;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
        if (this.id == null) {
            this.id = new FoodIngredientId();
        }
        this.id.setFoodId(food.getId());
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
        if (this.id == null) {
            this.id = new FoodIngredientId();
        }
        this.id.setIngredientId(ingredient.getId());
    }

    public Double getQuantiteUtilisee() {
        return quantiteUtilisee;
    }

    public void setQuantiteUtilisee(Double quantiteUtilisee) {
        this.quantiteUtilisee = quantiteUtilisee;
    }

    public String getUnite() {
        return unite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    @Override
    public String toString() {
        return "FoodIngredient{" +
                "id=" + id +
                ", quantiteUtilisee=" + quantiteUtilisee +
                ", unite='" + unite + '\'' +
                '}';
    }

    /**
     * Classe pour la clé primaire composite
     * Implémente Serializable (obligatoire pour les clés composites JPA)
     */
    @Embeddable
    public static class FoodIngredientId implements java.io.Serializable {

        @Column(name = "food_id")
        private Long foodId;

        @Column(name = "ingredient_id")
        private Long ingredientId;

        /**
         * Constructeur par défaut
         */
        public FoodIngredientId() {
        }

        /**
         * Constructeur avec paramètres
         */
        public FoodIngredientId(Long foodId, Long ingredientId) {
            this.foodId = foodId;
            this.ingredientId = ingredientId;
        }

        // Getters et Setters
        public Long getFoodId() {
            return foodId;
        }

        public void setFoodId(Long foodId) {
            this.foodId = foodId;
        }

        public Long getIngredientId() {
            return ingredientId;
        }

        public void setIngredientId(Long ingredientId) {
            this.ingredientId = ingredientId;
        }

        /**
         * Méthodes equals et hashCode obligatoires pour les clés composites
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            FoodIngredientId that = (FoodIngredientId) obj;
            return foodId.equals(that.foodId) && ingredientId.equals(that.ingredientId);
        }

        @Override
        public int hashCode() {
            return foodId.hashCode() + ingredientId.hashCode();
        }

        @Override
        public String toString() {
            return "FoodIngredientId{" +
                    "foodId=" + foodId +
                    ", ingredientId=" + ingredientId +
                    '}';
        }
    }
}