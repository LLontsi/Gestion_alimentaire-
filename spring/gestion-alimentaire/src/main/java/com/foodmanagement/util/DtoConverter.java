package com.foodmanagement.util;

import com.foodmanagement.dto.*;
import com.foodmanagement.entity.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Utilitaire pour la conversion entre entités et DTOs
 * Centralise toute la logique de conversion
 */
@Component
public class DtoConverter {

    @Autowired
    private ModelMapper modelMapper;

    // ============= CONVERSIONS PERSONNE =============

    /**
     * Convertir Personne vers PersonneDto
     */
    public PersonneDto toPersonneDto(Personne personne) {
        PersonneDto dto = modelMapper.map(personne, PersonneDto.class);
        
        // Ajout d'informations supplémentaires
        if (personne.getFoods() != null) {
            dto.setNombreAlimentsCreés(personne.getFoods().size());
        }
        
        return dto;
    }

    /**
     * Convertir PersonneCreateDto vers Personne
     */
    public Personne toPersonneEntity(PersonneCreateDto dto) {
        return modelMapper.map(dto, Personne.class);
    }

    /**
     * Convertir PersonneUpdateDto vers Personne
     */
    public Personne toPersonneEntity(PersonneUpdateDto dto) {
        return modelMapper.map(dto, Personne.class);
    }

    /**
     * Convertir une liste de Personne vers PersonneDto
     */
    public List<PersonneDto> toPersonneDtoList(List<Personne> personnes) {
        return personnes.stream()
            .map(this::toPersonneDto)
            .collect(Collectors.toList());
    }

    // ============= CONVERSIONS FOOD =============

    /**
     * Convertir Food vers FoodDto
     */
    public FoodDto toFoodDto(Food food) {
        FoodDto dto = modelMapper.map(food, FoodDto.class);
        
        // Ajout d'informations supplémentaires
        if (food.getPersonne() != null) {
            dto.setCreateurId(food.getPersonne().getId());
            dto.setCreateurNom(food.getPersonne().getNom());
        }
        
        if (food.getImages() != null) {
            dto.setNombreImages(food.getImages().size());
            // Recherche de l'image principale
            food.getImages().stream()
                .filter(img -> img.getTypeImage() == com.foodmanagement.enums.TypeImage.PRINCIPALE)
                .findFirst()
                .ifPresent(img -> dto.setImagePrincipaleUrl("/api/images/fichier/" + img.getNomFichier()));
        }
        
        if (food.getFoodIngredients() != null) {
            dto.setNombreIngredients(food.getFoodIngredients().size());
            dto.setIngredients(
                food.getFoodIngredients().stream()
                    .map(fi -> toIngredientSimpleDto(fi.getIngredient(), fi.getQuantiteUtilisee(), fi.getUnite()))
                    .collect(Collectors.toList())
            );
        }
        
        return dto;
    }

    /**
     * Convertir FoodCreateDto vers Food
     */
    public Food toFoodEntity(FoodCreateDto dto) {
        Food food = modelMapper.map(dto, Food.class);
        
        // Création de l'objet Personne pour le créateur
        Personne createur = new Personne();
        createur.setId(dto.getCreateurId());
        food.setPersonne(createur);
        
        return food;
    }

    /**
     * Convertir une liste de Food vers FoodDto
     */
    public List<FoodDto> toFoodDtoList(List<Food> foods) {
        return foods.stream()
            .map(this::toFoodDto)
            .collect(Collectors.toList());
    }

    // ============= CONVERSIONS INGREDIENT =============

    /**
     * Convertir Ingredient vers IngredientDto
     */
    public IngredientDto toIngredientDto(Ingredient ingredient) {
        IngredientDto dto = modelMapper.map(ingredient, IngredientDto.class);
        
        // Ajout d'informations supplémentaires
        if (ingredient.getFoodIngredients() != null) {
            dto.setNombreAlimentsUtilisants(ingredient.getFoodIngredients().size());
        }
        
        if (ingredient.getImage() != null) {
            dto.setImageUrl("/api/images/fichier/" + ingredient.getImage().getNomFichier());
        }
        
        return dto;
    }

    /**
     * Convertir Ingredient vers IngredientSimpleDto avec quantité
     */
    public IngredientSimpleDto toIngredientSimpleDto(Ingredient ingredient, Double quantite, String unite) {
        IngredientSimpleDto dto = modelMapper.map(ingredient, IngredientSimpleDto.class);
        dto.setQuantiteUtilisee(quantite);
        dto.setUnite(unite);
        return dto;
    }

    /**
     * Convertir une liste d'Ingredient vers IngredientDto
     */
    public List<IngredientDto> toIngredientDtoList(List<Ingredient> ingredients) {
        return ingredients.stream()
            .map(this::toIngredientDto)
            .collect(Collectors.toList());
    }

    // ============= CONVERSIONS IMAGE =============

    /**
     * Convertir Image vers ImageDto
     */
    public ImageDto toImageDto(Image image) {
        ImageDto dto = modelMapper.map(image, ImageDto.class);
        
        // Ajout d'informations sur l'association
        if (image.getFood() != null) {
            dto.setFoodId(image.getFood().getId());
            dto.setFoodNom(image.getFood().getNom());
        }
        
        if (image.getIngredient() != null) {
            dto.setIngredientId(image.getIngredient().getId());
            dto.setIngredientNom(image.getIngredient().getNom());
        }
        
        return dto;
    }

    /**
     * Convertir une liste d'Image vers ImageDto
     */
    public List<ImageDto> toImageDtoList(List<Image> images) {
        return images.stream()
            .map(this::toImageDto)
            .collect(Collectors.toList());
    }
}