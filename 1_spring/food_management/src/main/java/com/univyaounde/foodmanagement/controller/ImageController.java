package com.univyaounde.foodmanagement.controller;

import com.univyaounde.foodmanagement.dto.ImageDTO;
import com.univyaounde.foodmanagement.model.Image;
import com.univyaounde.foodmanagement.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/images")
@CrossOrigin(origins = "*")
public class ImageController {
    
    @Autowired
    private ImageService imageService;
    
    @GetMapping
    public ResponseEntity<List<ImageDTO>> getAllImages() {
        try {
            List<Image> images = imageService.getAllImages();
            List<ImageDTO> imageDTOs = new ArrayList<>();
            
            for (Image image : images) {
                ImageDTO dto = new ImageDTO();
                dto.setId(image.getId());
                dto.setNomFichier(image.getNomFichier());
                dto.setChemin(image.getChemin());
                dto.setTypeMime(image.getTypeMime());
                dto.setTaille(image.getTaille());
                dto.setDescription(image.getDescription());
                dto.setDateUpload(image.getDateUpload());
                
                // Éviter lazy loading - valeurs par défaut
                dto.setFoodId(null);
                dto.setFoodNom(null);
                
                imageDTOs.add(dto);
            }
            
            return ResponseEntity.ok(imageDTOs);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new ArrayList<>());
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ImageDTO> getImageById(@PathVariable Long id) {
        try {
            Optional<Image> image = imageService.getImageById(id);
            if (image.isPresent()) {
                ImageDTO dto = convertToDTO(image.get());
                return ResponseEntity.ok(dto);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    private ImageDTO convertToDTO(Image image) {
        ImageDTO dto = new ImageDTO();
        dto.setId(image.getId());
        dto.setNomFichier(image.getNomFichier());
        dto.setChemin(image.getChemin());
        dto.setTypeMime(image.getTypeMime());
        dto.setTaille(image.getTaille());
        dto.setDescription(image.getDescription());
        dto.setDateUpload(image.getDateUpload());
        
        // Éviter lazy loading - valeurs par défaut
        dto.setFoodId(null);
        dto.setFoodNom(null);
        
        return dto;
    }
}