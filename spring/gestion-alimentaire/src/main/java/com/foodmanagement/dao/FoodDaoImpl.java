package com.foodmanagement.dao;

import com.foodmanagement.entity.Food;
import com.foodmanagement.entity.Personne;
import com.foodmanagement.enums.CategorieFood;
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
 * Implémentation JDBC de FoodDao
 */
@Repository
public class FoodDaoImpl implements FoodDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * RowMapper pour Food avec jointure sur Personne
     * Mappe les résultats de la requête vers un objet Food
     */
    private final RowMapper<Food> foodRowMapper = (rs, rowNum) -> {
        Food food = new Food();
        food.setId(rs.getLong("id"));
        food.setNom(rs.getString("nom"));
        food.setDescription(rs.getString("description"));
        
        // Conversion String -> Enum
        String categorieStr = rs.getString("categorie");
        if (categorieStr != null) {
            food.setCategorie(CategorieFood.valueOf(categorieStr));
        }
        
        food.setCalories(rs.getDouble("calories"));
        food.setPrix(rs.getDouble("prix"));
        food.setTempsPreparation(rs.getString("temps_preparation"));
        
        // Conversion Timestamp -> LocalDateTime
        Timestamp timestamp = rs.getTimestamp("date_creation");
        if (timestamp != null) {
            food.setDateCreation(timestamp.toLocalDateTime());
        }

        // Création de l'objet Personne associé
        Personne personne = new Personne();
        personne.setId(rs.getLong("personne_id"));
        personne.setNom(rs.getString("personne_nom"));
        personne.setEmail(rs.getString("personne_email"));
        food.setPersonne(personne);

        return food;
    };

    /**
     * Créer un nouvel aliment
     */
    @Override
    public Food create(Food food) {
        String sql = "INSERT INTO food (nom, description, categorie, calories, prix, temps_preparation, " +
                     "personne_id, date_creation) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, food.getNom());
            ps.setString(2, food.getDescription());
            ps.setString(3, food.getCategorie().name()); // Enum -> String
            ps.setDouble(4, food.getCalories() != null ? food.getCalories() : 0.0);
            ps.setDouble(5, food.getPrix() != null ? food.getPrix() : 0.0);
            ps.setString(6, food.getTempsPreparation());
            ps.setLong(7, food.getPersonne().getId());
            ps.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
            return ps;
        }, keyHolder);

        Long generatedId = keyHolder.getKey().longValue();
        food.setId(generatedId);
        food.setDateCreation(LocalDateTime.now());

        return food;
    }

    /**
     * Rechercher un aliment par ID avec jointure sur Personne
     */
    @Override
    public Optional<Food> findById(Long id) {
        String sql = "SELECT f.id, f.nom, f.description, f.categorie, f.calories, f.prix, " +
                     "f.temps_preparation, f.date_creation, f.personne_id, " +
                     "p.nom as personne_nom, p.email as personne_email " +
                     "FROM food f " +
                     "JOIN personne p ON f.personne_id = p.id " +
                     "WHERE f.id = ?";

        try {
            Food food = jdbcTemplate.queryForObject(sql, foodRowMapper, id);
            return Optional.of(food);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    /**
     * Rechercher tous les aliments
     */
    @Override
    public List<Food> findAll() {
        String sql = "SELECT f.id, f.nom, f.description, f.categorie, f.calories, f.prix, " +
                     "f.temps_preparation, f.date_creation, f.personne_id, " +
                     "p.nom as personne_nom, p.email as personne_email " +
                     "FROM food f " +
                     "JOIN personne p ON f.personne_id = p.id " +
                     "ORDER BY f.date_creation DESC";

        return jdbcTemplate.query(sql, foodRowMapper);
    }

    /**
     * Rechercher des aliments par nom
     */
    @Override
    public List<Food> findByNomContaining(String nom) {
        String sql = "SELECT f.id, f.nom, f.description, f.categorie, f.calories, f.prix, " +
                     "f.temps_preparation, f.date_creation, f.personne_id, " +
                     "p.nom as personne_nom, p.email as personne_email " +
                     "FROM food f " +
                     "JOIN personne p ON f.personne_id = p.id " +
                     "WHERE UPPER(f.nom) LIKE UPPER(?) " +
                     "ORDER BY f.nom";

        String searchPattern = "%" + nom + "%";
        return jdbcTemplate.query(sql, foodRowMapper, searchPattern);
    }

    /**
     * Rechercher des aliments par catégorie
     */
    @Override
    public List<Food> findByCategorie(CategorieFood categorie) {
        String sql = "SELECT f.id, f.nom, f.description, f.categorie, f.calories, f.prix, " +
                     "f.temps_preparation, f.date_creation, f.personne_id, " +
                     "p.nom as personne_nom, p.email as personne_email " +
                     "FROM food f " +
                     "JOIN personne p ON f.personne_id = p.id " +
                     "WHERE f.categorie = ? " +
                     "ORDER BY f.nom";

        return jdbcTemplate.query(sql, foodRowMapper, categorie.name());
    }

    /**
     * Rechercher des aliments par créateur
     */
    @Override
    public List<Food> findByPersonneId(Long personneId) {
        String sql = "SELECT f.id, f.nom, f.description, f.categorie, f.calories, f.prix, " +
                     "f.temps_preparation, f.date_creation, f.personne_id, " +
                     "p.nom as personne_nom, p.email as personne_email " +
                     "FROM food f " +
                     "JOIN personne p ON f.personne_id = p.id " +
                     "WHERE f.personne_id = ? " +
                     "ORDER BY f.date_creation DESC";

        return jdbcTemplate.query(sql, foodRowMapper, personneId);
    }

    /**
     * Mettre à jour un aliment
     */
    @Override
    public Food update(Food food) {
        String sql = "UPDATE food SET nom = ?, description = ?, categorie = ?, calories = ?, " +
                     "prix = ?, temps_preparation = ? WHERE id = ?";

        int rowsAffected = jdbcTemplate.update(sql,
            food.getNom(),
            food.getDescription(),
            food.getCategorie().name(),
            food.getCalories(),
            food.getPrix(),
            food.getTempsPreparation(),
            food.getId()
        );

        if (rowsAffected == 0) {
            throw new RuntimeException("Aucun aliment trouvé avec l'ID : " + food.getId());
        }

        return food;
    }

    /**
     * Supprimer un aliment par ID
     */
    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM food WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        return rowsAffected > 0;
    }

    /**
     * Vérifier si un nom d'aliment existe pour un créateur
     */
    @Override
    public boolean existsByNomAndPersonneId(String nom, Long personneId) {
        String sql = "SELECT COUNT(*) FROM food WHERE nom = ? AND personne_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, nom, personneId);
        return count != null && count > 0;
    }

    /**
     * Compter le nombre d'aliments
     */
    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM food";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        return count != null ? count.longValue() : 0;
    }

    /**
     * Rechercher des aliments par tranche de calories
     */
    @Override
    public List<Food> findByCaloriesBetween(Double caloriesMin, Double caloriesMax) {
        String sql = "SELECT f.id, f.nom, f.description, f.categorie, f.calories, f.prix, " +
                     "f.temps_preparation, f.date_creation, f.personne_id, " +
                     "p.nom as personne_nom, p.email as personne_email " +
                     "FROM food f " +
                     "JOIN personne p ON f.personne_id = p.id " +
                     "WHERE f.calories BETWEEN ? AND ? " +
                     "ORDER BY f.calories ASC";

        return jdbcTemplate.query(sql, foodRowMapper, caloriesMin, caloriesMax);
    }
}