package com.foodmanagement.dao;

import com.foodmanagement.entity.Image;
import com.foodmanagement.enums.TypeImage;
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
 * Implémentation JDBC de ImageDao
 */
@Repository
public class ImageDaoImpl implements ImageDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * RowMapper pour convertir les résultats SQL en objets Image
     */
    private final RowMapper<Image> imageRowMapper = (rs, rowNum) -> {
        Image image = new Image();
        image.setId(rs.getLong("id"));
        image.setNomFichier(rs.getString("nom_fichier"));
        image.setCheminFichier(rs.getString("chemin_fichier"));
        
        // Conversion String -> Enum pour le type d'image
        String typeStr = rs.getString("type_image");
        if (typeStr != null) {
            image.setTypeImage(TypeImage.valueOf(typeStr));
        }
        
        image.setTailleFichier(rs.getLong("taille_fichier"));
        
        // Conversion Timestamp -> LocalDateTime
        Timestamp timestamp = rs.getTimestamp("date_upload");
        if (timestamp != null) {
            image.setDateUpload(timestamp.toLocalDateTime());
        }
        
        // Gestion des clés étrangères optionnelles
        Long foodId = rs.getLong("food_id");
        if (!rs.wasNull()) {
            // Ici on pourrait créer un objet Food basique, mais pour simplifier
            // on ne stocke que l'ID dans un objet Food minimal
            // Dans un vrai projet, on ferait probablement une jointure
        }
        
        Long ingredientId = rs.getLong("ingredient_id");
        if (!rs.wasNull()) {
            // Même chose pour l'ingrédient
        }
        
        return image;
    };

    /**
     * Créer une nouvelle image
     */
    @Override
    public Image create(Image image) {
        String sql = "INSERT INTO image (nom_fichier, chemin_fichier, type_image, taille_fichier, " +
                     "food_id, ingredient_id, date_upload) VALUES (?, ?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, image.getNomFichier());
            ps.setString(2, image.getCheminFichier());
            ps.setString(3, image.getTypeImage().name());
            ps.setLong(4, image.getTailleFichier() != null ? image.getTailleFichier() : 0L);
            
            // Gestion des clés étrangères optionnelles
            if (image.getFood() != null) {
                ps.setLong(5, image.getFood().getId());
            } else {
                ps.setNull(5, java.sql.Types.BIGINT);
            }
            
            if (image.getIngredient() != null) {
                ps.setLong(6, image.getIngredient().getId());
            } else {
                ps.setNull(6, java.sql.Types.BIGINT);
            }
            
            ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
            return ps;
        }, keyHolder);

        Long generatedId = keyHolder.getKey().longValue();
        image.setId(generatedId);
        image.setDateUpload(LocalDateTime.now());

        return image;
    }

    /**
     * Rechercher une image par ID
     */
    @Override
    public Optional<Image> findById(Long id) {
        String sql = "SELECT id, nom_fichier, chemin_fichier, type_image, taille_fichier, " +
                     "food_id, ingredient_id, date_upload FROM image WHERE id = ?";

        try {
            Image image = jdbcTemplate.queryForObject(sql, imageRowMapper, id);
            return Optional.of(image);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    /**
     * Rechercher une image par nom de fichier
     */
    @Override
    public Optional<Image> findByNomFichier(String nomFichier) {
        String sql = "SELECT id, nom_fichier, chemin_fichier, type_image, taille_fichier, " +
                     "food_id, ingredient_id, date_upload FROM image WHERE nom_fichier = ?";

        try {
            Image image = jdbcTemplate.queryForObject(sql, imageRowMapper, nomFichier);
            return Optional.of(image);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    /**
     * Rechercher toutes les images
     */
    @Override
    public List<Image> findAll() {
        String sql = "SELECT id, nom_fichier, chemin_fichier, type_image, taille_fichier, " +
                     "food_id, ingredient_id, date_upload FROM image ORDER BY date_upload DESC";

        return jdbcTemplate.query(sql, imageRowMapper);
    }

    /**
     * Rechercher des images par type
     */
    @Override
    public List<Image> findByTypeImage(TypeImage typeImage) {
        String sql = "SELECT id, nom_fichier, chemin_fichier, type_image, taille_fichier, " +
                     "food_id, ingredient_id, date_upload FROM image WHERE type_image = ? " +
                     "ORDER BY date_upload DESC";

        return jdbcTemplate.query(sql, imageRowMapper, typeImage.name());
    }

    /**
     * Rechercher des images d'un aliment
     */
    @Override
    public List<Image> findByFoodId(Long foodId) {
        String sql = "SELECT id, nom_fichier, chemin_fichier, type_image, taille_fichier, " +
                     "food_id, ingredient_id, date_upload FROM image WHERE food_id = ? " +
                     "ORDER BY date_upload DESC";

        return jdbcTemplate.query(sql, imageRowMapper, foodId);
    }

    /**
     * Rechercher l'image principale d'un aliment
     */
    @Override
    public Optional<Image> findMainImageByFoodId(Long foodId) {
        String sql = "SELECT id, nom_fichier, chemin_fichier, type_image, taille_fichier, " +
                     "food_id, ingredient_id, date_upload FROM image " +
                     "WHERE food_id = ? AND type_image = 'PRINCIPALE'";

        try {
            Image image = jdbcTemplate.queryForObject(sql, imageRowMapper, foodId);
            return Optional.of(image);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    /**
     * Rechercher l'image d'un ingrédient
     */
    @Override
    public Optional<Image> findByIngredientId(Long ingredientId) {
        String sql = "SELECT id, nom_fichier, chemin_fichier, type_image, taille_fichier, " +
                     "food_id, ingredient_id, date_upload FROM image WHERE ingredient_id = ?";

        try {
            Image image = jdbcTemplate.queryForObject(sql, imageRowMapper, ingredientId);
            return Optional.of(image);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    /**
     * Mettre à jour une image
     */
    @Override
    public Image update(Image image) {
        String sql = "UPDATE image SET nom_fichier = ?, chemin_fichier = ?, type_image = ?, " +
                     "taille_fichier = ? WHERE id = ?";

        int rowsAffected = jdbcTemplate.update(sql,
            image.getNomFichier(),
            image.getCheminFichier(),
            image.getTypeImage().name(),
            image.getTailleFichier(),
            image.getId()
        );

        if (rowsAffected == 0) {
            throw new RuntimeException("Aucune image trouvée avec l'ID : " + image.getId());
        }

        return image;
    }

    /**
     * Supprimer une image par ID
     */
    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM image WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        return rowsAffected > 0;
    }

    /**
     * Supprimer toutes les images d'un aliment
     */
    @Override
    public int deleteByFoodId(Long foodId) {
        String sql = "DELETE FROM image WHERE food_id = ?";
        return jdbcTemplate.update(sql, foodId);
    }

    /**
     * Vérifier si un nom de fichier existe
     */
    @Override
    public boolean existsByNomFichier(String nomFichier) {
        String sql = "SELECT COUNT(*) FROM image WHERE nom_fichier = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, nomFichier);
        return count != null && count > 0;
    }

    /**
     * Compter le nombre d'images
     */
    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM image";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        return count != null ? count.longValue() : 0;
    }

    /**
     * Rechercher des images par tranche de taille
     */
    @Override
    public List<Image> findByTailleFichierBetween(Long tailleMin, Long tailleMax) {
        String sql = "SELECT id, nom_fichier, chemin_fichier, type_image, taille_fichier, " +
                     "food_id, ingredient_id, date_upload FROM image " +
                     "WHERE taille_fichier BETWEEN ? AND ? ORDER BY taille_fichier";

        return jdbcTemplate.query(sql, imageRowMapper, tailleMin, tailleMax);
    }

    /**
     * Rechercher des images uploadées entre deux dates
     */
    @Override
    public List<Image> findByDateUploadBetween(LocalDateTime dateDebut, LocalDateTime dateFin) {
        String sql = "SELECT id, nom_fichier, chemin_fichier, type_image, taille_fichier, " +
                     "food_id, ingredient_id, date_upload FROM image " +
                     "WHERE date_upload BETWEEN ? AND ? ORDER BY date_upload DESC";

        Timestamp timestampDebut = Timestamp.valueOf(dateDebut);
        Timestamp timestampFin = Timestamp.valueOf(dateFin);

        return jdbcTemplate.query(sql, imageRowMapper, timestampDebut, timestampFin);
    }
}