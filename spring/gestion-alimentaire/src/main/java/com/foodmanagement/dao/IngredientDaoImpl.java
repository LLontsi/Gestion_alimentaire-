package com.foodmanagement.dao;

import com.foodmanagement.entity.Ingredient;
import com.foodmanagement.enums.TypeIngredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Implémentation JDBC de IngredientDao
 */
@Repository
public class IngredientDaoImpl implements IngredientDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * RowMapper pour convertir les résultats SQL en objets Ingredient
     */
    private final RowMapper<Ingredient> ingredientRowMapper = (rs, rowNum) -> {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(rs.getLong("id"));
        ingredient.setNom(rs.getString("nom"));
        ingredient.setDescription(rs.getString("description"));
        
        // Conversion String -> Enum pour le type
        String typeStr = rs.getString("type");
        if (typeStr != null) {
            ingredient.setType(TypeIngredient.valueOf(typeStr));
        }
        
        ingredient.setQuantite(rs.getDouble("quantite"));
        ingredient.setUnite(rs.getString("unite"));
        
        // Conversion Timestamp -> LocalDateTime
        Timestamp timestamp = rs.getTimestamp("date_creation");
        if (timestamp != null) {
            ingredient.setDateCreation(timestamp.toLocalDateTime());
        }
        
        return ingredient;
    };

    /**
     * Créer un nouvel ingrédient
     */
    @Override
    public Ingredient create(Ingredient ingredient) {
        String sql = "INSERT INTO ingredient (nom, description, type, quantite, unite, date_creation) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, ingredient.getNom());
            ps.setString(2, ingredient.getDescription());
            ps.setString(3, ingredient.getType().name()); // Enum -> String
            ps.setDouble(4, ingredient.getQuantite() != null ? ingredient.getQuantite() : 0.0);
            ps.setString(5, ingredient.getUnite());
            ps.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            return ps;
        }, keyHolder);

        Long generatedId = keyHolder.getKey().longValue();
        ingredient.setId(generatedId);
        ingredient.setDateCreation(LocalDateTime.now());

        return ingredient;
    }

    /**
     * Rechercher un ingrédient par ID
     */
    @Override
    public Optional<Ingredient> findById(Long id) {
        String sql = "SELECT id, nom, description, type, quantite, unite, date_creation " +
                     "FROM ingredient WHERE id = ?";

        try {
            Ingredient ingredient = jdbcTemplate.queryForObject(sql, ingredientRowMapper, id);
            return Optional.of(ingredient);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    /**
     * Rechercher un ingrédient par nom exact
     */
    @Override
    public Optional<Ingredient> findByNom(String nom) {
        String sql = "SELECT id, nom, description, type, quantite, unite, date_creation " +
                     "FROM ingredient WHERE nom = ?";

        try {
            Ingredient ingredient = jdbcTemplate.queryForObject(sql, ingredientRowMapper, nom);
            return Optional.of(ingredient);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    /**
     * Rechercher tous les ingrédients
     */
    @Override
    public List<Ingredient> findAll() {
        String sql = "SELECT id, nom, description, type, quantite, unite, date_creation " +
                     "FROM ingredient ORDER BY nom";

        return jdbcTemplate.query(sql, ingredientRowMapper);
    }

    /**
     * Rechercher des ingrédients par nom (recherche partielle)
     */
    @Override
    public List<Ingredient> findByNomContaining(String nom) {
        String sql = "SELECT id, nom, description, type, quantite, unite, date_creation " +
                     "FROM ingredient WHERE UPPER(nom) LIKE UPPER(?) ORDER BY nom";

        String searchPattern = "%" + nom + "%";
        return jdbcTemplate.query(sql, ingredientRowMapper, searchPattern);
    }

    /**
     * Rechercher des ingrédients par type
     */
    @Override
    public List<Ingredient> findByType(TypeIngredient type) {
        String sql = "SELECT id, nom, description, type, quantite, unite, date_creation " +
                     "FROM ingredient WHERE type = ? ORDER BY nom";

        return jdbcTemplate.query(sql, ingredientRowMapper, type.name());
    }

    /**
     * Rechercher des ingrédients par unité
     */
    @Override
    public List<Ingredient> findByUnite(String unite) {
        String sql = "SELECT id, nom, description, type, quantite, unite, date_creation " +
                     "FROM ingredient WHERE unite = ? ORDER BY nom";

        return jdbcTemplate.query(sql, ingredientRowMapper, unite);
    }

    /**
     * Mettre à jour un ingrédient
     */
    @Override
    public Ingredient update(Ingredient ingredient) {
        String sql = "UPDATE ingredient SET nom = ?, description = ?, type = ?, quantite = ?, unite = ? " +
                     "WHERE id = ?";

        int rowsAffected = jdbcTemplate.update(sql,
            ingredient.getNom(),
            ingredient.getDescription(),
            ingredient.getType().name(),
            ingredient.getQuantite(),
            ingredient.getUnite(),
            ingredient.getId()
        );

        if (rowsAffected == 0) {
            throw new RuntimeException("Aucun ingrédient trouvé avec l'ID : " + ingredient.getId());
        }

        return ingredient;
    }

    /**
     * Supprimer un ingrédient par ID
     */
    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM ingredient WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        return rowsAffected > 0;
    }

    /**
     * Vérifier si un nom d'ingrédient existe
     */
    @Override
    public boolean existsByNom(String nom) {
        String sql = "SELECT COUNT(*) FROM ingredient WHERE nom = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, nom);
        return count != null && count > 0;
    }

    /**
     * Compter le nombre d'ingrédients
     */
    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM ingredient";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        return count != null ? count.longValue() : 0;
    }

    /**
     * Rechercher des ingrédients par tranche de quantité
     */
    @Override
    public List<Ingredient> findByQuantiteBetween(Double quantiteMin, Double quantiteMax) {
        String sql = "SELECT id, nom, description, type, quantite, unite, date_creation " +
                     "FROM ingredient WHERE quantite BETWEEN ? AND ? ORDER BY quantite";

        return jdbcTemplate.query(sql, ingredientRowMapper, quantiteMin, quantiteMax);
    }

    /**
     * Rechercher des ingrédients utilisés dans des aliments
     * Utilise une jointure avec la table food_ingredient
     */
    @Override
    public List<Ingredient> findIngredientsUsedInFoods() {
        String sql = "SELECT DISTINCT i.id, i.nom, i.description, i.type, i.quantite, i.unite, i.date_creation " +
                     "FROM ingredient i " +
                     "INNER JOIN food_ingredient fi ON i.id = fi.ingredient_id " +
                     "ORDER BY i.nom";

        return jdbcTemplate.query(sql, ingredientRowMapper);
    }
}