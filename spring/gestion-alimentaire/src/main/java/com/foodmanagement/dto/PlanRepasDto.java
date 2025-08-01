package com.foodmanagement.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * DTO pour un plan de repas
 */
public class PlanRepasDto {
    
    private Long personneId;
    private String personneNom;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private int nombreJours;
    
    // Plan organisé par date puis par type de repas
    private Map<LocalDate, Map<String, List<FoodDto>>> planRepas;
    
    // Statistiques du plan
    private Double caloriesTotales;
    private Double caloriesMoyennesParJour;
    private int nombreAlimentsTotal;
    private Map<String, Integer> repartitionCategories;

    // Constructeurs
    public PlanRepasDto() {}

    public PlanRepasDto(Long personneId, LocalDate dateDebut, LocalDate dateFin) {
        this.personneId = personneId;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.nombreJours = (int) dateDebut.until(dateFin).getDays() + 1;
    }

    // Getters et Setters
    public Long getPersonneId() {
        return personneId;
    }

    public void setPersonneId(Long personneId) {
        this.personneId = personneId;
    }

    public String getPersonneNom() {
        return personneNom;
    }

    public void setPersonneNom(String personneNom) {
        this.personneNom = personneNom;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public int getNombreJours() {
        return nombreJours;
    }

    public void setNombreJours(int nombreJours) {
        this.nombreJours = nombreJours;
    }

    public Map<LocalDate, Map<String, List<FoodDto>>> getPlanRepas() {
        return planRepas;
    }

    public void setPlanRepas(Map<LocalDate, Map<String, List<FoodDto>>> planRepas) {
        this.planRepas = planRepas;
    }

    public Double getCaloriesTotales() {
        return caloriesTotales;
    }

    public void setCaloriesTotales(Double caloriesTotales) {
        this.caloriesTotales = caloriesTotales;
    }

    public Double getCaloriesMoyennesParJour() {
        return caloriesMoyennesParJour;
    }

    public void setCaloriesMoyennesParJour(Double caloriesMoyennesParJour) {
        this.caloriesMoyennesParJour = caloriesMoyennesParJour;
    }

    public int getNombreAlimentsTotal() {
        return nombreAlimentsTotal;
    }

    public void setNombreAlimentsTotal(int nombreAlimentsTotal) {
        this.nombreAlimentsTotal = nombreAlimentsTotal;
    }

    public Map<String, Integer> getRepartitionCategories() {
        return repartitionCategories;
    }

    public void setRepartitionCategories(Map<String, Integer> repartitionCategories) {
        this.repartitionCategories = repartitionCategories;
    }
}