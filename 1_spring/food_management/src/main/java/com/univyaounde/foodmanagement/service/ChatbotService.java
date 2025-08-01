package com.univyaounde.foodmanagement.service;

import com.univyaounde.foodmanagement.model.Food;
import com.univyaounde.foodmanagement.model.Ingredient;
import com.univyaounde.foodmanagement.model.Personne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChatbotService {
    
    @Autowired
    private FoodService foodService;
    
    @Autowired
    private IngredientService ingredientService;
    
    @Autowired
    private PersonneService personneService;
    
    @Autowired
    private PlanificationService planificationService;
    
    @Autowired
    private BuffetService buffetService;
    
    public Map<String, Object> traiterMessage(String message, Long userId) {
        Map<String, Object> response = new HashMap<>();
        String messageLower = message.toLowerCase();
        
        // Analyser l'intention du message
        String intention = detecterIntention(messageLower);
        
        switch (intention) {
            case "RECHERCHE_ALIMENT":
                response = rechercherAliment(extractNomAliment(messageLower));
                break;
            case "CONSEIL_NUTRITION":
                response = donnerConseilNutrition(new HashMap<>());
                break;
            case "PLANIFICATION":
                response = aiderPlanification(userId);
                break;
            case "RECETTE":
                response = suggererRecette(extractIngredients(messageLower));
                break;
            case "BUFFET":
                response = aiderBuffet(extractNombreInvites(messageLower));
                break;
            case "CALORIES":
                response = calculerCalories(extractNomAliment(messageLower));
                break;
            default:
                response = reponseGenerale(messageLower);
        }
        
        response.put("intention", intention);
        response.put("timestamp", new Date());
        return response;
    }
    
    private String detecterIntention(String message) {
        if (message.contains("aliment") || message.contains("nourriture") || message.contains("plat")) {
            return "RECHERCHE_ALIMENT";
        }
        if (message.contains("nutrition") || message.contains("santé") || message.contains("équilibré")) {
            return "CONSEIL_NUTRITION";
        }
        if (message.contains("plan") || message.contains("menu") || message.contains("semaine")) {
            return "PLANIFICATION";
        }
        if (message.contains("recette") || message.contains("cuisiner") || message.contains("ingrédient")) {
            return "RECETTE";
        }
        if (message.contains("buffet") || message.contains("fête") || message.contains("invité")) {
            return "BUFFET";
        }
        if (message.contains("calorie") || message.contains("énergie")) {
            return "CALORIES";
        }
        return "GENERAL";
    }
    
    private Map<String, Object> rechercherAliment(String nomAliment) {
        Map<String, Object> response = new HashMap<>();
        
        if (nomAliment != null && !nomAliment.isEmpty()) {
            List<Food> foods = foodService.searchFoodsByName(nomAliment);
            
            if (!foods.isEmpty()) {
                Food food = foods.get(0);
                response.put("message", "J'ai trouvé cet aliment : " + food.getNom());
                response.put("details", Map.of(
                    "nom", food.getNom(),
                    "description", food.getDescription(),
                    "categorie", food.getCategorie(),
                    "calories", food.getCalories()
                ));
                response.put("suggestions", foods.stream()
                    .limit(5)
                    .map(f -> f.getNom())
                    .collect(Collectors.toList()));
            } else {
                response.put("message", "Je n'ai pas trouvé d'aliment avec ce nom. Voulez-vous que je vous suggère des alternatives ?");
                response.put("alternatives", suggererAlternatives(nomAliment));
            }
        } else {
            response.put("message", "Quel aliment recherchez-vous ?");
        }
        
        return response;
    }
    
    public Map<String, Object> donnerConseilNutrition(Map<String, Object> criteres) {
        Map<String, Object> response = new HashMap<>();
        List<String> conseils = new ArrayList<>();
        
        // Obtenir des aliments par catégorie pour les conseils
        List<Food> legumes = foodService.getFoodsByCategory(Food.CategorieAliment.LEGUMES);
        List<Food> fruits = foodService.getFoodsByCategory(Food.CategorieAliment.FRUITS);
        List<Food> proteines = foodService.getFoodsByCategory(Food.CategorieAliment.PROTEINES);
        
        conseils.add("🥬 Incluez au moins 5 portions de légumes par jour");
        conseils.add("🍎 Consommez 3 fruits différents quotidiennement");
        conseils.add("🍗 Variez vos sources de protéines");
        conseils.add("💧 N'oubliez pas de boire suffisamment d'eau");
        
        if (!legumes.isEmpty()) {
            conseils.add("Légumes recommandés : " + 
                legumes.stream().limit(3).map(Food::getNom).collect(Collectors.joining(", ")));
        }
        
        if (!fruits.isEmpty()) {
            conseils.add("Fruits de saison : " + 
                fruits.stream().limit(3).map(Food::getNom).collect(Collectors.joining(", ")));
        }
        
        response.put("message", "Voici mes conseils nutritionnels personnalisés :");
        response.put("conseils", conseils);
        response.put("type", "nutrition");
        
        return response;
    }
    
    private Map<String, Object> aiderPlanification(Long userId) {
        Map<String, Object> response = new HashMap<>();
        
        if (userId != null) {
            try {
                List<Food> recommandations = planificationService.obtenirRecommandations(userId, "DEJEUNER");
                response.put("message", "Voici quelques suggestions pour votre planification :");
                response.put("recommandations", recommandations.stream()
                    .limit(5)
                    .map(food -> Map.of(
                        "nom", food.getNom(),
                        "categorie", food.getCategorie(),
                        "calories", food.getCalories()
                    ))
                    .collect(Collectors.toList()));
            } catch (Exception e) {
                response.put("message", "Pour vous aider avec la planification, j'aurais besoin de votre ID utilisateur.");
            }
        } else {
            response.put("message", "Pour une planification personnalisée, connectez-vous d'abord !");
            response.put("conseil", "Je peux quand même vous donner des conseils généraux de planification.");
        }
        
        return response;
    }
    
    private Map<String, Object> suggererRecette(List<String> ingredients) {
        Map<String, Object> response = new HashMap<>();
        
        if (!ingredients.isEmpty()) {
            // Rechercher des aliments contenant ces ingrédients
            List<Food> foodsCorrespondants = new ArrayList<>();
            
            for (String ingredient : ingredients) {
                List<Food> foods = foodService.searchFoodsByName(ingredient);
                foodsCorrespondants.addAll(foods);
            }
            
            response.put("message", "Avec ces ingrédients, voici ce que vous pouvez préparer :");
            response.put("recettes", foodsCorrespondants.stream()
                .limit(3)
                .map(food -> Map.of(
                    "nom", food.getNom(),
                    "description", food.getDescription(),
                    "categorie", food.getCategorie()
                ))
                .collect(Collectors.toList()));
        } else {
            response.put("message", "Quels ingrédients avez-vous à disposition ?");
        }
        
        return response;
    }
    
    private Map<String, Object> aiderBuffet(Integer nombreInvites) {
        Map<String, Object> response = new HashMap<>();
        
        if (nombreInvites != null && nombreInvites > 0) {
            List<Map<String, Object>> modeles = buffetService.obtenirModelesBuffet();
            
            response.put("message", "Pour " + nombreInvites + " invités, voici mes recommandations :");
            response.put("modeles", modeles);
            response.put("conseil", "Comptez environ 300g de nourriture par personne pour un buffet complet.");
        } else {
            response.put("message", "Combien d'invités attendez-vous pour votre événement ?");
        }
        
        return response;
    }
    
    private Map<String, Object> calculerCalories(String nomAliment) {
        Map<String, Object> response = new HashMap<>();
        
        if (nomAliment != null) {
            List<Food> foods = foodService.searchFoodsByName(nomAliment);
            
            if (!foods.isEmpty()) {
                Food food = foods.get(0);
                response.put("message", food.getNom() + " contient " + food.getCalories() + " calories");
                response.put("details", Map.of(
                    "aliment", food.getNom(),
                    "calories", food.getCalories(),
                    "categorie", food.getCategorie()
                ));
            } else {
                response.put("message", "Je ne trouve pas les informations caloriques pour cet aliment.");
            }
        }
        
        return response;
    }
    
    private Map<String, Object> reponseGenerale(String message) {
        Map<String, Object> response = new HashMap<>();
        
        List<String> reponsesGenerales = Arrays.asList(
            "Je suis votre assistant culinaire ! Je peux vous aider avec :",
            "🔍 Rechercher des aliments et leurs informations nutritionnelles",
            "📅 Planifier vos repas de la semaine",
            "🍽️ Organiser des buffets et événements",
            "💡 Donner des conseils nutritionnels",
            "📊 Calculer les calories",
            "🥘 Suggérer des recettes selon vos ingrédients"
        );
        
        response.put("message", String.join("\n", reponsesGenerales));
        response.put("type", "aide");
        
        return response;
    }
    
    public Map<String, Object> obtenirSuggestions(String domaine) {
        Map<String, Object> suggestions = new HashMap<>();
        
        switch (domaine.toLowerCase()) {
            case "aliments":
                List<Food> foods = foodService.getAllFoods();
                suggestions.put("suggestions", foods.stream()
                    .limit(10)
                    .map(Food::getNom)
                    .collect(Collectors.toList()));
                break;
            case "ingredients":
                List<Ingredient> ingredients = ingredientService.getAllIngredients();
                suggestions.put("suggestions", ingredients.stream()
                    .limit(10)
                    .map(Ingredient::getNom)
                    .collect(Collectors.toList()));
                break;
            default:
                suggestions.put("suggestions", Arrays.asList(
                    "Quel est votre aliment préféré ?",
                    "Avez-vous des restrictions alimentaires ?",
                    "Voulez-vous planifier vos repas ?",
                    "Organisez-vous un événement ?"
                ));
        }
        
        return suggestions;
    }
    
    // Méthodes utilitaires pour extraire des informations du message
    private String extractNomAliment(String message) {
        // Logique simple pour extraire le nom d'un aliment
        String[] mots = message.split(" ");
        for (int i = 0; i < mots.length; i++) {
            if (mots[i].equals("aliment") || mots[i].equals("plat") || mots[i].equals("nourriture")) {
                if (i + 1 < mots.length) {
                    return mots[i + 1];
                }
            }
        }
        return null;
    }
    
    private List<String> extractIngredients(String message) {
        // Logique pour extraire les ingrédients mentionnés
        List<String> ingredients = new ArrayList<>();
        List<Ingredient> tousIngredients = ingredientService.getAllIngredients();
        
        for (Ingredient ingredient : tousIngredients) {
            if (message.contains(ingredient.getNom().toLowerCase())) {
                ingredients.add(ingredient.getNom());
            }
        }
        
        return ingredients;
    }
    
    private Integer extractNombreInvites(String message) {
        // Extraire un nombre du message
        String[] mots = message.split(" ");
        for (String mot : mots) {
            try {
                return Integer.parseInt(mot);
            } catch (NumberFormatException e) {
                // Continue
            }
        }
        return null;
    }
    
    private List<String> suggererAlternatives(String nomAliment) {
        // Suggérer des alternatives basées sur la similarité
        List<Food> foods = foodService.getAllFoods();
        return foods.stream()
            .filter(food -> food.getNom().toLowerCase().contains(nomAliment.substring(0, Math.min(3, nomAliment.length()))))
            .limit(5)
            .map(Food::getNom)
            .collect(Collectors.toList());
    }
}