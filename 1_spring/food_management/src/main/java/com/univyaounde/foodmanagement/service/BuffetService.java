package com.univyaounde.foodmanagement.service;

import com.univyaounde.foodmanagement.model.Food;
import com.univyaounde.foodmanagement.model.Personne;
import com.univyaounde.foodmanagement.service.FoodService;
import com.univyaounde.foodmanagement.service.PersonneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BuffetService {
    
    @Autowired
    private FoodService foodService;
    
    @Autowired
    private PersonneService personneService;
    
    public Map<String, Object> creerBuffetCeremonie(int nombreInvites, String typeCeremonie, 
                                                   Double budgetEstime, Long organisateurId) {
        Map<String, Object> buffet = new HashMap<>();
        
        Optional<Personne> organisateur = personneService.getPersonneById(organisateurId);
        if (!organisateur.isPresent()) {
            throw new RuntimeException("Organisateur introuvable");
        }
        
        List<Food> alimentsDisponibles = foodService.getAllFoods();
        
        // Calculer les quantités
        Map<String, Object> quantites = calculerQuantites(nombreInvites, typeCeremonie);
        
        // Sélectionner les aliments pour le buffet
        Map<String, List<Food>> menuBuffet = new HashMap<>();
        menuBuffet.put("ENTREES", selectionnerAlimentsBuffet(alimentsDisponibles, "ENTREES", nombreInvites));
        menuBuffet.put("PLATS_PRINCIPAUX", selectionnerAlimentsBuffet(alimentsDisponibles, "PLATS_PRINCIPAUX", nombreInvites));
        menuBuffet.put("ACCOMPAGNEMENTS", selectionnerAlimentsBuffet(alimentsDisponibles, "ACCOMPAGNEMENTS", nombreInvites));
        menuBuffet.put("DESSERTS", selectionnerAlimentsBuffet(alimentsDisponibles, "DESSERTS", nombreInvites));
        menuBuffet.put("BOISSONS", selectionnerAlimentsBuffet(alimentsDisponibles, "BOISSONS", nombreInvites));
        
        buffet.put("organisateur", organisateur.get());
        buffet.put("nombreInvites", nombreInvites);
        buffet.put("typeCeremonie", typeCeremonie);
        buffet.put("dateCreation", LocalDateTime.now());
        buffet.put("menuBuffet", menuBuffet);
        buffet.put("quantitesEstimees", quantites);
        buffet.put("coutEstime", calculerCoutEstime(menuBuffet, nombreInvites));
        
        return buffet;
    }
    
    private List<Food> selectionnerAlimentsBuffet(List<Food> alimentsDisponibles, String categorieBuffet, int nombreInvites) {
        List<Food> selection = new ArrayList<>();
        
        switch (categorieBuffet) {
            case "ENTREES":
                selection = alimentsDisponibles.stream()
                    .filter(food -> food.getCategorie() == Food.CategorieAliment.LEGUMES ||
                                   food.getCategorie() == Food.CategorieAliment.FRUITS)
                    .limit(3)
                    .collect(Collectors.toList());
                break;
            case "PLATS_PRINCIPAUX":
                selection = alimentsDisponibles.stream()
                    .filter(food -> food.getCategorie() == Food.CategorieAliment.PROTEINES ||
                                   food.getCategorie() == Food.CategorieAliment.CEREALES)
                    .limit(4)
                    .collect(Collectors.toList());
                break;
            case "ACCOMPAGNEMENTS":
                selection = alimentsDisponibles.stream()
                    .filter(food -> food.getCategorie() == Food.CategorieAliment.LEGUMES ||
                                   food.getCategorie() == Food.CategorieAliment.CEREALES)
                    .limit(4)
                    .collect(Collectors.toList());
                break;
            case "DESSERTS":
                selection = alimentsDisponibles.stream()
                    .filter(food -> food.getCategorie() == Food.CategorieAliment.FRUITS ||
                                   food.getCategorie() == Food.CategorieAliment.PRODUITS_LAITIERS)
                    .limit(3)
                    .collect(Collectors.toList());
                break;
            case "BOISSONS":
                selection = alimentsDisponibles.stream()
                    .filter(food -> food.getCategorie() == Food.CategorieAliment.PRODUITS_LAITIERS)
                    .limit(2)
                    .collect(Collectors.toList());
                break;
        }
        
        return selection;
    }
    
    private Map<String, Object> calculerQuantites(int nombreInvites, String typeCeremonie) {
        Map<String, Object> quantites = new HashMap<>();
        
        // Facteur multiplicateur selon le type de cérémonie
        double facteur = switch (typeCeremonie.toLowerCase()) {
            case "mariage" -> 1.5;
            case "anniversaire" -> 1.2;
            case "bapteme" -> 1.0;
            case "conference" -> 0.8;
            default -> 1.0;
        };
        
        quantites.put("entreesKg", Math.ceil(nombreInvites * 0.15 * facteur));
        quantites.put("platsPrincipauxKg", Math.ceil(nombreInvites * 0.3 * facteur));
        quantites.put("accompagnementsKg", Math.ceil(nombreInvites * 0.2 * facteur));
        quantites.put("dessertsKg", Math.ceil(nombreInvites * 0.15 * facteur));
        quantites.put("boissonsLitres", Math.ceil(nombreInvites * 1.5 * facteur));
        
        return quantites;
    }
    
    private Double calculerCoutEstime(Map<String, List<Food>> menuBuffet, int nombreInvites) {
        // Estimation simple basée sur les calories (proxy pour le coût)
        double coutTotal = menuBuffet.values().stream()
            .flatMap(List::stream)
            .mapToDouble(food -> food.getCalories() != null ? food.getCalories() * 0.01 : 10.0)
            .sum();
        
        return coutTotal * nombreInvites * 0.05; // Facteur d'ajustement
    }
    
    public List<Map<String, Object>> obtenirModelesBuffet() {
        List<Map<String, Object>> modeles = new ArrayList<>();
        
        Map<String, Object> mariageModele = new HashMap<>();
        mariageModele.put("nom", "Buffet Mariage");
        mariageModele.put("description", "Menu complet pour mariage avec entrées, plats, desserts");
        mariageModele.put("invitesMin", 50);
        mariageModele.put("invitesMax", 200);
        modeles.add(mariageModele);
        
        Map<String, Object> anniversaireModele = new HashMap<>();
        anniversaireModele.put("nom", "Buffet Anniversaire");
        anniversaireModele.put("description", "Menu festif pour anniversaire");
        anniversaireModele.put("invitesMin", 10);
        anniversaireModele.put("invitesMax", 50);
        modeles.add(anniversaireModele);
        
        Map<String, Object> conferenceModele = new HashMap<>();
        conferenceModele.put("nom", "Buffet Conférence");
        conferenceModele.put("description", "Menu léger pour pause-café et déjeuner");
        conferenceModele.put("invitesMin", 20);
        conferenceModele.put("invitesMax", 100);
        modeles.add(conferenceModele);
        
        return modeles;
    }
    
    public Map<String, Object> optimiserBuffet(Map<String, Object> buffet, Double budgetMax) {
        Map<String, Object> buffetOptimise = new HashMap<>(buffet);
        
        @SuppressWarnings("unchecked")
        Map<String, List<Food>> menuBuffet = (Map<String, List<Food>>) buffet.get("menuBuffet");
        
        Double coutActuel = (Double) buffet.get("coutEstime");
        
        if (coutActuel > budgetMax) {
            // Réduire les quantités ou remplacer par des alternatives moins chères
            Map<String, List<Food>> menuOptimise = new HashMap<>();
            
            for (Map.Entry<String, List<Food>> entry : menuBuffet.entrySet()) {
                List<Food> alimentsOptimises = entry.getValue().stream()
                    .sorted(Comparator.comparing(food -> food.getCalories() != null ? food.getCalories() : 0.0))
                    .limit(Math.max(1, entry.getValue().size() - 1))
                    .collect(Collectors.toList());
                menuOptimise.put(entry.getKey(), alimentsOptimises);
            }
            
            buffetOptimise.put("menuBuffet", menuOptimise);
            buffetOptimise.put("coutEstime", calculerCoutEstime(menuOptimise, (Integer) buffet.get("nombreInvites")));
            buffetOptimise.put("optimise", true);
        }
        
        return buffetOptimise;
    }
}