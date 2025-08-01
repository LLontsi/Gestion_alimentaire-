package com.foodmanagement.enums;

/**
 * Énumération des types d'images
 * Pour organiser les images uploadées
 */
public enum TypeImage {
    PRINCIPALE("Image principale"),
    GALERIE("Image de galerie"),
    MINIATURE("Miniature");

    private final String libelle;

    /**
     * Constructeur de l'énumération
     * @param libelle Le nom affiché du type d'image
     */
    TypeImage(String libelle) {
        this.libelle = libelle;
    }

    /**
     * Getter pour récupérer le libellé
     * @return Le libellé du type d'image
     */
    public String getLibelle() {
        return libelle;
    }
}