package com.univyaounde.foodmanagement.controller;

import com.univyaounde.foodmanagement.dto.PersonneDTO;
import com.univyaounde.foodmanagement.model.Personne;
import com.univyaounde.foodmanagement.service.PersonneService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/personnes")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PersonneController {
    
    private static final Logger logger = LoggerFactory.getLogger(PersonneController.class);
    
    @Autowired
    private PersonneService personneService;
    
    // GET - Obtenir toutes les personnes
    @GetMapping
    public ResponseEntity<List<PersonneDTO>> getAllPersonnes() {
        logger.info("GET /api/personnes - Récupération de toutes les personnes");
        try {
            List<Personne> personnes = personneService.getAllPersonnes();
            List<PersonneDTO> personneDTOs = new ArrayList<>();
            
            for (Personne personne : personnes) {
                PersonneDTO dto = convertToDTO(personne);
                personneDTOs.add(dto);
            }
            
            logger.info("Nombre de personnes retournées: {}", personneDTOs.size());
            return ResponseEntity.ok(personneDTOs);
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des personnes", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
        }
    }
    
    // GET - Obtenir une personne par ID
    @GetMapping("/{id}")
    public ResponseEntity<PersonneDTO> getPersonneById(@PathVariable Long id) {
        logger.info("GET /api/personnes/{} - Récupération de la personne", id);
        try {
            Optional<Personne> personne = personneService.getPersonneById(id);
            if (personne.isPresent()) {
                PersonneDTO dto = convertToDTO(personne.get());
                return ResponseEntity.ok(dto);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération de la personne {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // POST - Créer une nouvelle personne
    @PostMapping
    public ResponseEntity<PersonneDTO> createPersonne(@Valid @RequestBody PersonneDTO personneDTO) {
        logger.info("POST /api/personnes - Création d'une nouvelle personne: {}", personneDTO.getEmail());
        try {
            // Convertir DTO vers entité
            Personne personne = new Personne();
            personne.setNom(personneDTO.getNom());
            personne.setPrenom(personneDTO.getPrenom());
            personne.setEmail(personneDTO.getEmail());
            personne.setDateNaissance(personneDTO.getDateNaissance());
            personne.setTelephone(personneDTO.getTelephone());
            
            // Sauvegarder
            Personne savedPersonne = personneService.savePersonne(personne);
            PersonneDTO responseDTO = convertToDTO(savedPersonne);
            
            logger.info("Personne créée avec succès - ID: {}", savedPersonne.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        } catch (Exception e) {
            logger.error("Erreur lors de la création de la personne", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    // PUT - Mettre à jour une personne
    @PutMapping("/{id}")
    public ResponseEntity<PersonneDTO> updatePersonne(@PathVariable Long id, @Valid @RequestBody PersonneDTO personneDTO) {
        logger.info("PUT /api/personnes/{} - Mise à jour de la personne", id);
        try {
            Optional<Personne> existingPersonne = personneService.getPersonneById(id);
            if (!existingPersonne.isPresent()) {
                return ResponseEntity.notFound().build();
            }
            
            // Mettre à jour l'entité existante
            Personne personne = existingPersonne.get();
            personne.setNom(personneDTO.getNom());
            personne.setPrenom(personneDTO.getPrenom());
            personne.setEmail(personneDTO.getEmail());
            personne.setDateNaissance(personneDTO.getDateNaissance());
            personne.setTelephone(personneDTO.getTelephone());
            
            // Sauvegarder
            Personne updatedPersonne = personneService.savePersonne(personne);
            PersonneDTO responseDTO = convertToDTO(updatedPersonne);
            
            logger.info("Personne mise à jour avec succès - ID: {}", id);
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            logger.error("Erreur lors de la mise à jour de la personne {}", id, e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    // DELETE - Supprimer une personne
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersonne(@PathVariable Long id) {
        logger.info("DELETE /api/personnes/{} - Suppression de la personne", id);
        try {
            Optional<Personne> existingPersonne = personneService.getPersonneById(id);
            if (!existingPersonne.isPresent()) {
                return ResponseEntity.notFound().build();
            }
            
            personneService.deletePersonne(id);
            logger.info("Personne supprimée avec succès - ID: {}", id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Erreur lors de la suppression de la personne {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // Méthode utilitaire pour convertir en DTO
    private PersonneDTO convertToDTO(Personne personne) {
        PersonneDTO dto = new PersonneDTO();
        dto.setId(personne.getId());
        dto.setNom(personne.getNom());
        dto.setPrenom(personne.getPrenom());
        dto.setEmail(personne.getEmail());
        dto.setDateNaissance(personne.getDateNaissance());
        dto.setTelephone(personne.getTelephone());
        dto.setNombreAliments(0); // À implémenter si nécessaire
        return dto;
    }
}