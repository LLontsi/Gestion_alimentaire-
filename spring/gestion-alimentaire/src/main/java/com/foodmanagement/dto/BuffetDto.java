package com.foodmanagement.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * DTO pour l'affichage des informations d'un buffet
 */
public class BuffetDto {
    
    private Long id;
    private String nomEvenement;
    private LocalDateTime dateEvenement;
    private Integer nombreInvites;
    private String typeEvenement;
    private Double budget;
    private String statut;
    
    // Informations organisateur
    private Long organisateurId;
    private String organisateurNom;
    
    // Informations sur les aliments
    private int nombreAliments;
    private List<AlimentBuffetDto> aliments;
    
    // Estimations
    private Double coutEstime;
    private Double coutParPersonne;
    
    // Dates de gestion
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;

    // Constructeurs
    public BuffetDto() {}

    public BuffetDto(Long id, String nomEvenement, LocalDateTime dateEvenement, 
                     Integer nombreInvites, String typeEvenement) {
        this.id = id;
        this.nomEvenement = nomEvenement;
        this.dateEvenement = dateEvenement;
        this.nombreInvites = nombreInvites;
        this.typeEvenement = typeEvenement;
    }

    // Classe interne pour les aliments du buffet
    public static class AlimentBuffetDto {
        private Long foodId;
        private String nomAliment;
        private String categorieAliment;
        private Integer quantitePersonnes;
        private Double quantiteTotaleKg;
        private Integer priorite;
        private Double coutEstime;

        // Constructeurs, getters et setters
        public AlimentBuffetDto() {}

        public AlimentBuffetDto(Long foodId, String nomAliment, Integer quantitePersonnes, Integer priorite) {
            this.foodId = foodId;
            this.nomAliment = nomAliment;
            this.quantitePersonnes = quantitePersonnes;
            this.priorite = priorite;
        }

        // Getters et Setters pour AlimentBuffetDto
        public Long getFoodId() {
            return foodId;
        }

        public void setFoodId(Long foodId) {
            this.foodId = foodId;
        }

        public String getNomAliment() {
            return nomAliment;
        }

        public void setNomAliment(String nomAliment) {
            this.nomAliment = nomAliment;
        }

        public String getCategorieAliment() {
            return categorieAliment;
        }

        public void setCategorieAliment(String categorieAliment) {
            this.categorieAliment = categorieAliment;
        }

        public Integer getQuantitePersonnes() {
            return quantitePersonnes;
        }

        public void setQuantitePersonnes(Integer quantitePersonnes) {
            this.quantitePersonnes = quantitePersonnes;
        }

        public Double getQuantiteTotaleKg() {
            return quantiteTotaleKg;
        }

        public void setQuantiteTotaleKg(Double quantiteTotaleKg) {
            this.quantiteTotaleKg = quantiteTotaleKg;
        }

        public Integer getPriorite() {
            return priorite;
        }

        public void setPriorite(Integer priorite) {
            this.priorite = priorite;
        }

        public Double getCoutEstime() {
            return coutEstime;
        }

        public void setCoutEstime(Double coutEstime) {
            this.coutEstime = coutEstime;
        }
    }

    // Getters et Setters pour BuffetDto
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomEvenement() {
        return nomEvenement;
    }

    public void setNomEvenement(String nomEvenement) {
        this.nomEvenement = nomEvenement;
    }

    public LocalDateTime getDateEvenement() {
        return dateEvenement;
    }

    public void setDateEvenement(LocalDateTime dateEvenement) {
        this.dateEvenement = dateEvenement;
    }

    public Integer getNombreInvites() {
        return nombreInvites;
    }

    public void setNombreInvites(Integer nombreInvites) {
        this.nombreInvites = nombreInvites;
    }

    public String getTypeEvenement() {
        return typeEvenement;
    }

    public void setTypeEvenement(String typeEvenement) {
        this.typeEvenement = typeEvenement;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Long getOrganisateurId() {
        return organisateurId;
    }

    public void setOrganisateurId(Long organisateurId) {
        this.organisateurId = organisateurId;
    }

    public String getOrganisateurNom() {
        return organisateurNom;
    }

    public void setOrganisateurNom(String organisateurNom) {
        this.organisateurNom = organisateurNom;
    }

    public int getNombreAliments() {
        return nombreAliments;
    }

    public void setNombreAliments(int nombreAliments) {
        this.nombreAliments = nombreAliments;
    }

    public List<AlimentBuffetDto> getAliments() {
        return aliments;
    }

    public void setAliments(List<AlimentBuffetDto> aliments) {
        this.aliments = aliments;
    }

    public Double getCoutEstime() {
        return coutEstime;
    }

    public void setCoutEstime(Double coutEstime) {
        this.coutEstime = coutEstime;
    }

    public Double getCoutParPersonne() {
        return coutParPersonne;
    }

    public void setCoutParPersonne(Double coutParPersonne) {
        this.coutParPersonne = coutParPersonne;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDateTime getDateModification() {
        return dateModification;
    }

    public void setDateModification(LocalDateTime dateModification) {
        this.dateModification = dateModification;
    }
}