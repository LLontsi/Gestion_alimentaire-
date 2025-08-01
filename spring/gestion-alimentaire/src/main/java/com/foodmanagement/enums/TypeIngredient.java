package com.foodmanagement.enums;

/**
 * Énumération des types d'ingrédients
 * Classification par mode de conservation
 */
public enum TypeIngredient {
    FRAIS("Frais"),
    SURGELE("Surgelé"),
    CONSERVE("Conserve"), 
    SEC("Sec");

    private final String libelle;

    /**
     * Constructeur de l'énumération
     * @param libelle Le nom affiché du type
     */
    TypeIngredient(String libelle) {
        this.libelle = libelle;
    }

    /**
     * Getter pour récupérer le libellé
     * @return Le libellé du type d'ingrédient
     */
    public String getLibelle() {
        return libelle;
    }
}