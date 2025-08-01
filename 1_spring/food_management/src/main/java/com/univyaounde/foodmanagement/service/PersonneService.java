package com.univyaounde.foodmanagement.service;

import com.univyaounde.foodmanagement.model.Personne;
import com.univyaounde.foodmanagement.repository.PersonneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PersonneService {
    
    @Autowired
    private PersonneRepository personneRepository;
    
    // Obtenir toutes les personnes
    public List<Personne> getAllPersonnes() {
        return personneRepository.findAll();
    }
    
    // Obtenir une personne par ID
    public Optional<Personne> getPersonneById(Long id) {
        return personneRepository.findById(id);
    }
    
    // Sauvegarder une personne (création ou mise à jour)
    public Personne savePersonne(Personne personne) {
        return personneRepository.save(personne);
    }
    
    // Supprimer une personne
    public void deletePersonne(Long id) {
        personneRepository.deleteById(id);
    }
    
    // Vérifier si une personne existe
    public boolean existsById(Long id) {
        return personneRepository.existsById(id);
    }
    
    // Obtenir une personne par email
    public Optional<Personne> getPersonneByEmail(String email) {
        return personneRepository.findByEmail(email);
    }
}