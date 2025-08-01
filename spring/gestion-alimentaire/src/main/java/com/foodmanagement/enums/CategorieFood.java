package com.foodmanagement.enums;

/**
 * Énumération des catégories d'aliments
 * Correspond aux 5 catégories d'images à télécharger pour le TP
 */
public enum CategorieFood {
    LEGUMES("Légumes"),
    FRUITS("Fruits"), 
    VIANDES("Viandes"),
    CEREALES("Céréales"),
    DESSERTS("Desserts");

    // Attribut pour stocker le libellé affiché
    private final String libelle;

    /**
     * Constructeur de l'énumération
     * @param libelle Le nom affiché de la catégorie
     */
    CategorieFood(String libelle) {
        this.libelle = libelle;
    }

    /**
     * Getter pour récupérer le libellé
     * @return Le libellé de la catégorie
     */
    public String getLibelle() {
        return libelle;
    }
}