package com.foodmanagement.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Configuration de la base de données
 * Gère les différentes sources de données selon l'environnement
 */
@Configuration
public class DatabaseConfig {

    @Value("${spring.datasource.url}")
    private String databaseUrl;

    @Value("${spring.datasource.username}")
    private String databaseUsername;

    @Value("${spring.datasource.password}")
    private String databasePassword;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    /**
     * Configuration de la source de données pour le développement
     * Utilise PostgreSQL local
     */
    @Bean
    @Profile("dev")
    public DataSource dataSourceDev() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(databaseUrl);
        dataSource.setUsername(databaseUsername);
        dataSource.setPassword(databasePassword);
        
        // Configuration supplémentaire pour PostgreSQL
        Properties connectionProperties = new Properties();
        connectionProperties.setProperty("ApplicationName", "FoodManagement-Dev");
        connectionProperties.setProperty("connectTimeout", "10");
        connectionProperties.setProperty("socketTimeout", "30");
        dataSource.setConnectionProperties(connectionProperties);
        
        return dataSource;
    }

    /**
     * Configuration de la source de données pour la production
     * Avec pool de connexions optimisé
     */
    @Bean
    @Profile("prod")
    public DataSource dataSourceProd() {
        // En production, on utiliserait HikariCP (configuré automatiquement par Spring Boot)
        // Cette méthode sert principalement pour des configurations spécifiques
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(databaseUrl);
        dataSource.setUsername(databaseUsername);
        dataSource.setPassword(databasePassword);
        
        // Configuration pour la production
        Properties connectionProperties = new Properties();
        connectionProperties.setProperty("ApplicationName", "FoodManagement-Prod");
        connectionProperties.setProperty("connectTimeout", "5");
        connectionProperties.setProperty("socketTimeout", "30");
        connectionProperties.setProperty("tcpKeepAlive", "true");
        dataSource.setConnectionProperties(connectionProperties);
        
        return dataSource;
    }

    /**
     * Configuration de la source de données pour les tests
     * Utilise H2 en mémoire
     */
    @Bean
    @Profile("test")
    public DataSource dataSourceTest() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        
        // Configuration pour H2
        Properties connectionProperties = new Properties();
        connectionProperties.setProperty("MODE", "PostgreSQL");
        connectionProperties.setProperty("DATABASE_TO_LOWER", "TRUE");
        dataSource.setConnectionProperties(connectionProperties);
        
        return dataSource;
    }

    /**
     * Configuration de la source de données pour Docker
     * Utilise les variables d'environnement du conteneur
     */
    @Bean
    @Profile("docker")
    public DataSource dataSourceDocker() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        
        // Utilisation des variables d'environnement Docker
        String dockerUrl = System.getenv("DATABASE_URL");
        String dockerUsername = System.getenv("DATABASE_USERNAME");
        String dockerPassword = System.getenv("DATABASE_PASSWORD");
        
        // Valeurs par défaut si les variables ne sont pas définies
        dataSource.setUrl(dockerUrl != null ? dockerUrl : "jdbc:postgresql://postgres:5432/food_management_db");
        dataSource.setUsername(dockerUsername != null ? dockerUsername : "food_user");
        dataSource.setPassword(dockerPassword != null ? dockerPassword : "food_password");
        
        // Configuration pour Docker/PostgreSQL
        Properties connectionProperties = new Properties();
        connectionProperties.setProperty("ApplicationName", "FoodManagement-Docker");
        connectionProperties.setProperty("connectTimeout", "15");
        connectionProperties.setProperty("socketTimeout", "60");
        connectionProperties.setProperty("tcpKeepAlive", "true");
        dataSource.setConnectionProperties(connectionProperties);
        
        return dataSource;
    }

    /**
     * Bean JdbcTemplate pour les DAO JDBC
     * Utilise la source de données configurée
     */
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        
        // Configuration du JdbcTemplate
        jdbcTemplate.setFetchSize(100); // Taille de fetch optimisée
        jdbcTemplate.setMaxRows(1000); // Limite de sécurité
        jdbcTemplate.setQueryTimeout(30); // Timeout de 30 secondes
        
        return jdbcTemplate;
    }

    /**
     * Configuration des propriétés de la base de données
     * Informations de connexion et optimisations
     */
    @Bean
    public DatabaseProperties databaseProperties() {
        DatabaseProperties properties = new DatabaseProperties();
        properties.setUrl(databaseUrl);
        properties.setUsername(databaseUsername);
        properties.setDriverClassName(driverClassName);
        
        return properties;
    }

    /**
     * Classe pour les propriétés de la base de données
     * Permet l'injection dans d'autres composants
     */
    public static class DatabaseProperties {
        private String url;
        private String username;
        private String driverClassName;

        // Getters et Setters
        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getDriverClassName() {
            return driverClassName;
        }

        public void setDriverClassName(String driverClassName) {
            this.driverClassName = driverClassName;
        }
    }
}