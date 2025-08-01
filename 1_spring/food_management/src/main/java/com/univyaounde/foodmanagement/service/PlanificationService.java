package com.univyaounde.foodmanagement.service;

import com.univyaounde.foodmanagement.model.Food;
import com.univyaounde.foodmanagement.model.Personne;
import com.univyaounde.foodmanagement.service.FoodService;
import com.univyaounde.foodmanagement.service.PersonneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PlanificationService {
    
    @Autowired
    private FoodService foodService;
    
    @Autowired
    private PersonneService personneService;
    
    public Map<String, Object> creerPlanHebdomadaire(Long personneId, LocalDate dateDebut) {
        Map<String, Object> planHebdomadaire = new HashMap<>();
        
        Optional<Personne> personne = personneService.getPersonneById(personneId);
        if (!personne.isPresent()) {
            throw new RuntimeException("Personne introuvable");
        }
        
        List<Food> alimentsDisponibles = foodService.getAllFoods();
        
        // Créer un plan pour 7 jours
        Map<String, Map<String, List<Food>>> planning = new HashMap<>();
        
        for (int i = 0; i < 7; i++) {
            LocalDate date = dateDebut.plusDays(i);
            String jour = date.getDayOfWeek().name();
            
            Map<String, List<Food>> repasJour = new HashMap<>();
            repasJour.put("PETIT_DEJEUNER", selectionnerAliments(alimentsDisponibles, "PETIT_DEJEUNER"));
            repasJour.put("DEJEUNER", selectionnerAliments(alimentsDisponibles, "DEJEUNER"));
            repasJour.put("DINER", selectionnerAliments(alimentsDisponibles, "DINER"));
            
            planning.put(jour, repasJour);
        }
        
        planHebdomadaire.put("personne", personne.get());
        planHebdomadaire.put("dateDebut", dateDebut);
        planHebdomadaire.put("dateFin", dateDebut.plusDays(6));
        planHebdomadaire.put("planning", planning);
        planHebdomadaire.put("caloriesTotal", calculerCaloriesTotales(planning));
        
        return planHebdomadaire;
    }
    
    private List<Food> selectionnerAliments(List<Food> alimentsDisponibles, String typeRepas) {
        List<Food> selection = new ArrayList<>();
        
        // Logique de sélection basée sur le type de repas
        switch (typeRepas) {
            case "PETIT_DEJEUNER":
                selection = alimentsDisponibles.stream()
                    .filter(food -> food.getCategorie() == Food.CategorieAliment.CEREALES || 
                                   food.getCategorie() == Food.CategorieAliment.PRODUITS_LAITIERS ||
                                   food.getCategorie() == Food.CategorieAliment.FRUITS)
                    .limit(3)
                    .collect(Collectors.toList());
                break;
            case "DEJEUNER":
                selection = alimentsDisponibles.stream()
                    .filter(food -> food.getCategorie() == Food.CategorieAliment.PROTEINES ||
                                   food.getCategorie() == Food.CategorieAliment.LEGUMES ||
                                   food.getCategorie() == Food.CategorieAliment.CEREALES)
                    .limit(4)
                    .collect(Collectors.toList());
                break;
            case "DINER":
                selection = alimentsDisponibles.stream()
                    .filter(food -> food.getCategorie() == Food.CategorieAliment.LEGUMES ||
                                   food.getCategorie() == Food.CategorieAliment.PROTEINES)
                    .limit(3)
                    .collect(Collectors.toList());
                break;
        }
        
        return selection;
    }
    
    private Double calculerCaloriesTotales(Map<String, Map<String, List<Food>>> planning) {
        return planning.values().stream()
            .flatMap(repasJour -> repasJour.values().stream())
            .flatMap(List::stream)
            .mapToDouble(food -> food.getCalories() != null ? food.getCalories() : 0.0)
            .sum();
    }
    
    public List<Food> obtenirRecommandations(Long personneId, String typeRepas) {
        List<Food> alimentsDisponibles = foodService.getAllFoods();
        return selectionnerAliments(alimentsDisponibles, typeRepas);
    }
    
    public Map<String, Object> analyserPlanNutritionnel(Map<String, Object> plan) {
        Map<String, Object> analyse = new HashMap<>();
        
        @SuppressWarnings("unchecked")
        Map<String, Map<String, List<Food>>> planning = 
            (Map<String, Map<String, List<Food>>>) plan.get("planning");
        
        // Analyse par catégorie
        Map<String, Integer> repartitionCategories = new HashMap<>();
        List<Food> tousLesAliments = planning.values().stream()
            .flatMap(repasJour -> repasJour.values().stream())
            .flatMap(List::stream)
            .collect(Collectors.toList());
        
        for (Food.CategorieAliment categorie : Food.CategorieAliment.values()) {
            long count = tousLesAliments.stream()
                .filter(food -> food.getCategorie() == categorie)
                .count();
            repartitionCategories.put(categorie.name(), (int) count);
        }
        
        analyse.put("repartitionCategories", repartitionCategories);
        analyse.put("nombreTotalAliments", tousLesAliments.size());
        analyse.put("caloriesMoyennesParJour", (Double) plan.get("caloriesTotal") / 7);
        
        return analyse;
    }
}