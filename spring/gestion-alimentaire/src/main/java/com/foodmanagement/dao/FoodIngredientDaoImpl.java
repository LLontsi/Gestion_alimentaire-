package com.foodmanagement.dao;

import com.foodmanagement.entity.Food;
import com.foodmanagement.entity.FoodIngredient;
import com.foodmanagement.entity.Ingredient;
import com.foodmanagement.enums.CategorieFood;
import com.foodmanagement.enums.TypeIngredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implémentation JDBC de FoodIngredientDao
 */
@Repository
public class FoodIngredientDaoImpl implements FoodIngredientDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * RowMapper pour convertir les résultats SQL en objets FoodIngredient
     * Inclut les jointures avec Food et Ingredient pour avoir toutes les infos
     */
    private final RowMapper<FoodIngredient> foodIngredientRowMapper = (rs, rowNum) -> {
        FoodIngredient foodIngredient = new FoodIngredient();
        
        // Données de l'association
        foodIngredient.setQuantiteUtilisee(rs.getDouble("quantite_utilisee"));
        foodIngredient.setUnite(rs.getString("unite"));
        
        // Création de l'objet Food avec les données de base
        Food food = new Food();
        food.setId(rs.getLong("food_id"));
        food.setNom(rs.getString("food_nom"));
        food.setCategorie(CategorieFood.valueOf(rs.getString("food_categorie")));
        foodIngredient.setFood(food);
        
        // Création de l'objet Ingredient avec les données de base
        Ingredient ingredient = new Ingredient();
        ingredient.setId(rs.getLong("ingredient_id"));
        ingredient.setNom(rs.getString("ingredient_nom"));
        ingredient.setType(TypeIngredient.valueOf(rs.getString("ingredient_type")));
        foodIngredient.setIngredient(ingredient);
        
        // Création de l'ID composite
        FoodIngredient.FoodIngredientId id = new FoodIngredient.FoodIngredientId(
            food.getId(), ingredient.getId()
        );
        foodIngredient.setId(id);
        
        return foodIngredient;
    };

    /**
     * Créer une nouvelle association food-ingredient
     */
    @Override
    public FoodIngredient create(FoodIngredient foodIngredient) {
        String sql = "INSERT INTO food_ingredient (food_id, ingredient_id, quantite_utilisee, unite) " +
                     "VALUES (?, ?, ?, ?)";

        jdbcTemplate.update(sql,
            foodIngredient.getFood().getId(),
            foodIngredient.getIngredient().getId(),
            foodIngredient.getQuantiteUtilisee(),
            foodIngredient.getUnite()
        );

        // Création de l'ID composite
        FoodIngredient.FoodIngredientId id = new FoodIngredient.FoodIngredientId(
            foodIngredient.getFood().getId(),
            foodIngredient.getIngredient().getId()
        );
        foodIngredient.setId(id);

        return foodIngredient;
    }

    /**
     * Rechercher une association par food et ingredient
     */
    @Override
    public Optional<FoodIngredient> findByFoodIdAndIngredientId(Long foodId, Long ingredientId) {
        String sql = "SELECT fi.food_id, fi.ingredient_id, fi.quantite_utilisee, fi.unite, " +
                     "f.nom as food_nom, f.categorie as food_categorie, " +
                     "i.nom as ingredient_nom, i.type as ingredient_type " +
                     "FROM food_ingredient fi " +
                     "JOIN food f ON fi.food_id = f.id " +
                     "JOIN ingredient i ON fi.ingredient_id = i.id " +
                     "WHERE fi.food_id = ? AND fi.ingredient_id = ?";

        try {
            FoodIngredient foodIngredient = jdbcTemplate.queryForObject(sql, foodIngredientRowMapper, 
                                                                       foodId, ingredientId);
            return Optional.of(foodIngredient);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    /**
     * Rechercher tous les ingrédients d'un aliment
     */
    @Override
    public List<FoodIngredient> findByFoodId(Long foodId) {
        String sql = "SELECT fi.food_id, fi.ingredient_id, fi.quantite_utilisee, fi.unite, " +
                     "f.nom as food_nom, f.categorie as food_categorie, " +
                     "i.nom as ingredient_nom, i.type as ingredient_type " +
                     "FROM food_ingredient fi " +
                     "JOIN food f ON fi.food_id = f.id " +
                     "JOIN ingredient i ON fi.ingredient_id = i.id " +
                     "WHERE fi.food_id = ? " +
                     "ORDER BY i.nom";

        return jdbcTemplate.query(sql, foodIngredientRowMapper, foodId);
    }

    /**
     * Rechercher tous les aliments utilisant un ingrédient
     */
    @Override
    public List<FoodIngredient> findByIngredientId(Long ingredientId) {
        String sql = "SELECT fi.food_id, fi.ingredient_id, fi.quantite_utilisee, fi.unite, " +
                     "f.nom as food_nom, f.categorie as food_categorie, " +
                     "i.nom as ingredient_nom, i.type as ingredient_type " +
                     "FROM food_ingredient fi " +
                     "JOIN food f ON fi.food_id = f.id " +
                     "JOIN ingredient i ON fi.ingredient_id = i.id " +
                     "WHERE fi.ingredient_id = ? " +
                     "ORDER BY f.nom";

        return jdbcTemplate.query(sql, foodIngredientRowMapper, ingredientId);
    }

    /**
     * Rechercher toutes les associations
     */
    @Override
    public List<FoodIngredient> findAll() {
        String sql = "SELECT fi.food_id, fi.ingredient_id, fi.quantite_utilisee, fi.unite, " +
                     "f.nom as food_nom, f.categorie as food_categorie, " +
                     "i.nom as ingredient_nom, i.type as ingredient_type " +
                     "FROM food_ingredient fi " +
                     "JOIN food f ON fi.food_id = f.id " +
                     "JOIN ingredient i ON fi.ingredient_id = i.id " +
                     "ORDER BY f.nom, i.nom";

        return jdbcTemplate.query(sql, foodIngredientRowMapper);
    }

    /**
     * Mettre à jour une association
     */
    @Override
    public FoodIngredient update(FoodIngredient foodIngredient) {
        String sql = "UPDATE food_ingredient SET quantite_utilisee = ?, unite = ? " +
                     "WHERE food_id = ? AND ingredient_id = ?";

        int rowsAffected = jdbcTemplate.update(sql,
            foodIngredient.getQuantiteUtilisee(),
            foodIngredient.getUnite(),
            foodIngredient.getFood().getId(),
            foodIngredient.getIngredient().getId()
        );

        if (rowsAffected == 0) {
            throw new RuntimeException("Aucune association trouvée pour Food ID: " + 
                                     foodIngredient.getFood().getId() + 
                                     " et Ingredient ID: " + foodIngredient.getIngredient().getId());
        }

        return foodIngredient;
    }

    /**
     * Supprimer une association spécifique
     */
    @Override
    public boolean deleteByFoodIdAndIngredientId(Long foodId, Long ingredientId) {
        String sql = "DELETE FROM food_ingredient WHERE food_id = ? AND ingredient_id = ?";
        int rowsAffected = jdbcTemplate.update(sql, foodId, ingredientId);
        return rowsAffected > 0;
    }

    /**
     * Supprimer toutes les associations d'un aliment
     */
    @Override
    public int deleteByFoodId(Long foodId) {
        String sql = "DELETE FROM food_ingredient WHERE food_id = ?";
        return jdbcTemplate.update(sql, foodId);
    }

    /**
     * Supprimer toutes les associations d'un ingrédient
     */
    @Override
    public int deleteByIngredientId(Long ingredientId) {
        String sql = "DELETE FROM food_ingredient WHERE ingredient_id = ?";
        return jdbcTemplate.update(sql, ingredientId);
    }

    /**
     * Vérifier si une association existe
     */
    @Override
    public boolean existsByFoodIdAndIngredientId(Long foodId, Long ingredientId) {
        String sql = "SELECT COUNT(*) FROM food_ingredient WHERE food_id = ? AND ingredient_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, foodId, ingredientId);
        return count != null && count > 0;
    }

    /**
     * Compter le nombre d'ingrédients dans un aliment
     */
    @Override
    public long countIngredientsByFoodId(Long foodId) {
        String sql = "SELECT COUNT(*) FROM food_ingredient WHERE food_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, foodId);
        return count != null ? count.longValue() : 0;
    }

    /**
     * Compter le nombre d'aliments utilisant un ingrédient
     */
    @Override
    public long countFoodsByIngredientId(Long ingredientId) {
        String sql = "SELECT COUNT(*) FROM food_ingredient WHERE ingredient_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, ingredientId);
        return count != null ? count.longValue() : 0;
    }

    /**
     * Mettre à jour la quantité d'un ingrédient dans un aliment
     */
    @Override
    public boolean updateQuantiteAndUnite(Long foodId, Long ingredientId, Double nouvelleQuantite, String nouvelleUnite) {
        String sql = "UPDATE food_ingredient SET quantite_utilisee = ?, unite = ? " +
                     "WHERE food_id = ? AND ingredient_id = ?";

        int rowsAffected = jdbcTemplate.update(sql, nouvelleQuantite, nouvelleUnite, foodId, ingredientId);
        return rowsAffected > 0;
    }
}