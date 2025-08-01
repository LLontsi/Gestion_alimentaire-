package com.foodmanagement.dao;

import com.foodmanagement.entity.Personne;
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
 * Implémentation JDBC de PersonneDao
 * Utilise JdbcTemplate pour les opérations en base de données
 * 
 * @Repository : Indique que cette classe est un composant de persistance
 * Spring va automatiquement traduire les exceptions SQL en exceptions Spring
 */
@Repository
public class PersonneDaoImpl implements PersonneDao {

    /**
     * JdbcTemplate : Classe Spring qui simplifie l'utilisation de JDBC
     * - Gère automatiquement les connexions
     * - Gère les exceptions
     * - Simplifie l'exécution des requêtes
     * 
     * @Autowired : Injection de dépendance automatique
     */
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * RowMapper pour convertir les résultats SQL en objets Personne
     * Fonction lambda qui mappe chaque ligne de résultat vers un objet
     */
    private final RowMapper<Personne> personneRowMapper = (rs, rowNum) -> {
        Personne personne = new Personne();
        personne.setId(rs.getLong("id"));
        personne.setNom(rs.getString("nom"));
        personne.setEmail(rs.getString("email"));
        personne.setMotDePasse(rs.getString("mot_de_passe"));
        personne.setTelephone(rs.getString("telephone"));
        
        // Conversion Timestamp -> LocalDateTime
        Timestamp timestamp = rs.getTimestamp("date_creation");
        if (timestamp != null) {
            personne.setDateCreation(timestamp.toLocalDateTime());
        }
        
        return personne;
    };

    /**
     * Créer une nouvelle personne
     * Utilise GeneratedKeyHolder pour récupérer l'ID auto-généré
     */
    @Override
    public Personne create(Personne personne) {
        // Requête SQL d'insertion
        String sql = "INSERT INTO personne (nom, email, mot_de_passe, telephone, date_creation) " +
                     "VALUES (?, ?, ?, ?, ?)";

        // KeyHolder pour récupérer la clé générée
        KeyHolder keyHolder = new GeneratedKeyHolder();

        // Exécution de la requête avec récupération de la clé
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, personne.getNom());
            ps.setString(2, personne.getEmail());
            ps.setString(3, personne.getMotDePasse());
            ps.setString(4, personne.getTelephone());
            ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            return ps;
        }, keyHolder);

        // Récupération de l'ID généré et assignation
        Long generatedId = keyHolder.getKey().longValue();
        personne.setId(generatedId);
        personne.setDateCreation(LocalDateTime.now());

        return personne;
    }

    /**
     * Rechercher une personne par ID
     * Utilise try-catch pour gérer le cas où aucun résultat n'est trouvé
     */
    @Override
    public Optional<Personne> findById(Long id) {
        String sql = "SELECT id, nom, email, mot_de_passe, telephone, date_creation " +
                     "FROM personne WHERE id = ?";
        
        try {
            // queryForObject lance une exception si aucun résultat
            Personne personne = jdbcTemplate.queryForObject(sql, personneRowMapper, id);
            return Optional.of(personne);
        } catch (EmptyResultDataAccessException e) {
            // Aucun résultat trouvé
            return Optional.empty();
        }
    }

    /**
     * Rechercher une personne par email
     */
    @Override
    public Optional<Personne> findByEmail(String email) {
        String sql = "SELECT id, nom, email, mot_de_passe, telephone, date_creation " +
                     "FROM personne WHERE email = ?";
        
        try {
            Personne personne = jdbcTemplate.queryForObject(sql, personneRowMapper, email);
            return Optional.of(personne);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    /**
     * Rechercher toutes les personnes
     * Utilise query() qui retourne une liste (peut être vide)
     */
    @Override
    public List<Personne> findAll() {
        String sql = "SELECT id, nom, email, mot_de_passe, telephone, date_creation " +
                     "FROM personne ORDER BY date_creation DESC";
        
        return jdbcTemplate.query(sql, personneRowMapper);
    }

    /**
     * Rechercher des personnes par nom (recherche partielle, insensible à la casse)
     */
    @Override
    public List<Personne> findByNomContaining(String nom) {
        String sql = "SELECT id, nom, email, mot_de_passe, telephone, date_creation " +
                     "FROM personne WHERE UPPER(nom) LIKE UPPER(?) ORDER BY nom";
        
        // Ajout des % pour la recherche LIKE
        String searchPattern = "%" + nom + "%";
        
        return jdbcTemplate.query(sql, personneRowMapper, searchPattern);
    }

    /**
     * Mettre à jour une personne existante
     */
    @Override
    public Personne update(Personne personne) {
        String sql = "UPDATE personne SET nom = ?, email = ?, mot_de_passe = ?, telephone = ? " +
                     "WHERE id = ?";
        
        // Exécution de la mise à jour
        int rowsAffected = jdbcTemplate.update(sql, 
            personne.getNom(),
            personne.getEmail(),
            personne.getMotDePasse(),
            personne.getTelephone(),
            personne.getId()
        );

        // Vérification que la mise à jour a bien eu lieu
        if (rowsAffected == 0) {
            throw new RuntimeException("Aucune personne trouvée avec l'ID : " + personne.getId());
        }

        return personne;
    }

    /**
     * Supprimer une personne par ID
     */
    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM personne WHERE id = ?";
        
        int rowsAffected = jdbcTemplate.update(sql, id);
        
        // Retourne true si au moins une ligne a été supprimée
        return rowsAffected > 0;
    }

    /**
     * Supprimer une personne par email
     */
    @Override
    public boolean deleteByEmail(String email) {
        String sql = "DELETE FROM personne WHERE email = ?";
        
        int rowsAffected = jdbcTemplate.update(sql, email);
        
        return rowsAffected > 0;
    }

    /**
     * Vérifier si un email existe
     * Utilise queryForObject avec un COUNT pour optimiser
     */
    @Override
    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM personne WHERE email = ?";
        
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        
        return count != null && count > 0;
    }

    /**
     * Compter le nombre total de personnes
     */
    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM personne";
        
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        
        return count != null ? count.longValue() : 0;
    }

    /**
     * Rechercher des personnes créées entre deux dates
     */
    @Override
    public List<Personne> findByDateCreationBetween(LocalDateTime dateDebut, LocalDateTime dateFin) {
        String sql = "SELECT id, nom, email, mot_de_passe, telephone, date_creation " +
                     "FROM personne WHERE date_creation BETWEEN ? AND ? " +
                     "ORDER BY date_creation DESC";
        
        // Conversion LocalDateTime -> Timestamp pour JDBC
        Timestamp timestampDebut = Timestamp.valueOf(dateDebut);
        Timestamp timestampFin = Timestamp.valueOf(dateFin);
        
        return jdbcTemplate.query(sql, personneRowMapper, timestampDebut, timestampFin);
    }
}