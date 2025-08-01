package com.foodmanagement.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.foodmanagement.entity.*;
import com.foodmanagement.enums.CategorieFood;
import com.foodmanagement.enums.TypeIngredient;
import com.foodmanagement.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Utilitaire pour importer des données JSON d'exemple
 * Implémente CommandLineRunner pour s'exécuter au démarrage de l'application
 */
@Component
public class JsonDataImporter implements CommandLineRunner {

    @Autowired
    private PersonneService personneService;

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private FoodService foodService;

    private final ObjectMapper objectMapper;

    /**
     * Constructeur avec configuration de l'ObjectMapper
     */
    public JsonDataImporter() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    /**
     * Méthode exécutée au démarrage de l'application
     * Importe les données d'exemple si elles n'existent pas
     */
    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== Début de l'import des données d'exemple ===");
        
        try {
            // Vérifier s'il y a déjà des données
            if (personneService.compterPersonnes() > 0) {
                System.out.println("Des données existent déjà, import ignoré.");
                return;
            }

            // Import dans l'ordre des dépendances
            importerPersonnes();
            importerIngredients();
            importerAliments();
            
            System.out.println("=== Import des données terminé avec succès ===");
            
        } catch (Exception e) {
            System.err.println("Erreur lors de l'import des données : " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Importer les personnes depuis le fichier JSON
     */
    private void importerPersonnes() {
        try {
            System.out.println("Import des personnes...");
            
            ClassPathResource resource = new ClassPathResource("data/sample-personnes.json");
            InputStream inputStream = resource.getInputStream();
            
            List<Map<String, Object>> personnesData = objectMapper.readValue(
                inputStream, 
                new TypeReference<List<Map<String, Object>>>() {}
            );
            
            for (Map<String, Object> personneData : personnesData) {
                Personne personne = new Personne();
                personne.setNom((String) personneData.get("nom"));
                personne.setEmail((String) personneData.get("email"));
                personne.setMotDePasse((String) personneData.get("motDePasse"));
                personne.setTelephone((String) personneData.get("telephone"));
                
                // Vérifier si l'email existe déjà
                if (personneService.verifierEmailDisponible(personne.getEmail())) {
                    personneService.creerPersonne(personne);
                    System.out.println("Personne créée : " + personne.getNom());
                }
            }
            
            System.out.println("Import des personnes terminé.");
            
        } catch (Exception e) {
            System.err.println("Erreur lors de l'import des personnes : " + e.getMessage());
        }
    }

    /**
     * Importer les ingrédients depuis le fichier JSON
     */
    private void importerIngredients() {
        try {
            System.out.println("Import des ingrédients...");
            
            ClassPathResource resource = new ClassPathResource("data/sample-ingredients.json");
            InputStream inputStream = resource.getInputStream();
            
            List<Map<String, Object>> ingredientsData = objectMapper.readValue(
                inputStream, 
                new TypeReference<List<Map<String, Object>>>() {}
            );
            
            for (Map<String, Object> ingredientData : ingredientsData) {
                Ingredient ingredient = new Ingredient();
                ingredient.setNom((String) ingredientData.get("nom"));
                ingredient.setDescription((String) ingredientData.get("description"));
                ingredient.setType(TypeIngredient.valueOf((String) ingredientData.get("type")));
                
                if (ingredientData.get("quantite") != null) {
                    ingredient.setQuantite(((Number) ingredientData.get("quantite")).doubleValue());
                }
                
                ingredient.setUnite((String) ingredientData.get("unite"));
                
                // Vérifier si l'ingrédient existe déjà
                if (ingredientService.verifierNomIngredientDisponible(ingredient.getNom())) {
                    ingredientService.creerIngredient(ingredient);
                    System.out.println("Ingrédient créé : " + ingredient.getNom());
                }
            }
            
            System.out.println("Import des ingrédients terminé.");
            
        } catch (Exception e) {
            System.err.println("Erreur lors de l'import des ingrédients : " + e.getMessage());
        }
    }

    /**
     * Importer les aliments depuis le fichier JSON
     */
    private void importerAliments() {
        try {
            System.out.println("Import des aliments...");
            
            ClassPathResource resource = new ClassPathResource("data/sample-foods.json");
            InputStream inputStream = resource.getInputStream();
            
            List<Map<String, Object>> foodsData = objectMapper.readValue(
                inputStream, 
                new TypeReference<List<Map<String, Object>>>() {}
            );
            
            for (Map<String, Object> foodData : foodsData) {
                // Recherche du créateur par email
                String emailCreateur = (String) foodData.get("createurEmail");
                Optional<Personne> createur = personneService.obtenirPersonneParEmail(emailCreateur);
                
                if (createur.isPresent()) {
                    Food food = new Food();
                    food.setNom((String) foodData.get("nom"));
                    food.setDescription((String) foodData.get("description"));
                    food.setCategorie(CategorieFood.valueOf((String) foodData.get("categorie")));
                    
                    if (foodData.get("calories") != null) {
                        food.setCalories(((Number) foodData.get("calories")).doubleValue());
                    }
                    
                    if (foodData.get("prix") != null) {
                        food.setPrix(((Number) foodData.get("prix")).doubleValue());
                    }
                    
                    food.setTempsPreparation((String) foodData.get("tempsPreparation"));
                    food.setPersonne(createur.get());
                    
                    // Vérifier si l'aliment existe déjà pour ce créateur
                    if (foodService.verifierNomAlimentDisponible(food.getNom(), createur.get().getId())) {
                        Food alimentCree = foodService.creerAliment(food);
                        System.out.println("Aliment créé : " + alimentCree.getNom());
                        
                        // Ajouter les ingrédients si spécifiés
                        @SuppressWarnings("unchecked")
                        List<Map<String, Object>> ingredients = (List<Map<String, Object>>) foodData.get("ingredients");
                        if (ingredients != null) {
                            ajouterIngredientsAuFood(alimentCree.getId(), ingredients);
                        }
                    }
                } else {
                    System.err.println("Créateur non trouvé pour l'email : " + emailCreateur);
                }
            }
            
            System.out.println("Import des aliments terminé.");
            
        } catch (Exception e) {
            System.err.println("Erreur lors de l'import des aliments : " + e.getMessage());
        }
    }

    /**
     * Ajouter des ingrédients à un aliment
     */
    private void ajouterIngredientsAuFood(Long foodId, List<Map<String, Object>> ingredients) {
        for (Map<String, Object> ingredientData : ingredients) {
            try {
                String nomIngredient = (String) ingredientData.get("nom");
                Double quantite = ((Number) ingredientData.get("quantite")).doubleValue();
                String unite = (String) ingredientData.get("unite");
                
                // Recherche de l'ingrédient par nom
                Optional<Ingredient> ingredient = ingredientService.obtenirIngredientParNom(nomIngredient);
                
                if (ingredient.isPresent()) {
                    foodService.ajouterIngredient(foodId, ingredient.get().getId(), quantite, unite);
                    System.out.println("Ingrédient ajouté : " + nomIngredient + " (" + quantite + " " + unite + ")");
                } else {
                    System.err.println("Ingrédient non trouvé : " + nomIngredient);
                }
                
            } catch (Exception e) {
                System.err.println("Erreur lors de l'ajout d'ingrédient : " + e.getMessage());
            }
        }
    }

    /**
     * Méthode publique pour réimporter les données
     * Peut être appelée manuellement si nécessaire
     */
    public void reimporterDonnees() {
        System.out.println("=== Réimport forcé des données ===");
        
        try {
            importerPersonnes();
            importerIngredients();
            importerAliments();
            
            System.out.println("=== Réimport terminé ===");
            
        } catch (Exception e) {
            System.err.println("Erreur lors du réimport : " + e.getMessage());
        }
    }

    /**
     * Importer des données depuis un fichier JSON personnalisé
     * 
     * @param cheminFichier Chemin vers le fichier JSON
     * @param typeEntite Type d'entité à importer
     */
    public void importerDepuisFichier(String cheminFichier, String typeEntite) {
        try {
            System.out.println("Import depuis fichier : " + cheminFichier);
            
            ClassPathResource resource = new ClassPathResource(cheminFichier);
            InputStream inputStream = resource.getInputStream();
            
            switch (typeEntite.toLowerCase()) {
                case "personnes":
                    importerPersonnesDepuisFichier(inputStream);
                    break;
                case "ingredients":
                    importerIngredientsDepuisFichier(inputStream);
                    break;
                case "foods":
                    importerAlimentsDepuisFichier(inputStream);
                    break;
                default:
                    System.err.println("Type d'entité non supporté : " + typeEntite);
            }
            
        } catch (Exception e) {
            System.err.println("Erreur lors de l'import depuis fichier : " + e.getMessage());
        }
    }

    /**
     * Méthodes privées pour l'import depuis fichiers personnalisés
     */
    private void importerPersonnesDepuisFichier(InputStream inputStream) throws Exception {
        List<Map<String, Object>> data = objectMapper.readValue(
            inputStream, 
            new TypeReference<List<Map<String, Object>>>() {}
        );
        
        for (Map<String, Object> item : data) {
            // Logique d'import des personnes
        }
    }

    private void importerIngredientsDepuisFichier(InputStream inputStream) throws Exception {
        List<Map<String, Object>> data = objectMapper.readValue(
            inputStream, 
            new TypeReference<List<Map<String, Object>>>() {}
        );
        
        for (Map<String, Object> item : data) {
            // Logique d'import des ingrédients
        }
    }

    private void importerAlimentsDepuisFichier(InputStream inputStream) throws Exception {
        List<Map<String, Object>> data = objectMapper.readValue(
            inputStream, 
            new TypeReference<List<Map<String, Object>>>() {}
        );
        
        for (Map<String, Object> item : data) {
            // Logique d'import des aliments
        }
    }
}