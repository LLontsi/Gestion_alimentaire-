package com.foodmanagement.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration pour ModelMapper
 * Permet la conversion automatique entre entités et DTOs
 */
@Configuration
public class ModelMapperConfig {

    /**
     * Bean ModelMapper pour la conversion automatique Entity <-> DTO
     * 
     * @return Instance configurée de ModelMapper
     */
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        
        // Configuration de la stratégie de mapping
        mapper.getConfiguration()
            .setMatchingStrategy(MatchingStrategies.STRICT) // Mapping strict
            .setFieldMatchingEnabled(true) // Mapping par nom de champ
            .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE); // Accès aux champs privés
        
        // Configuration personnalisée pour éviter le mapping du mot de passe
        mapper.typeMap(com.foodmanagement.entity.Personne.class, 
                      com.foodmanagement.dto.PersonneDto.class)
            .addMappings(mapping -> mapping.skip(com.foodmanagement.dto.PersonneDto::setNombreAlimentsCreés));
        
        return mapper;
    }
}