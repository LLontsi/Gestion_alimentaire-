package com.foodmanagement.controller;

import com.foodmanagement.entity.Personne;
import com.foodmanagement.service.PersonneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Contrôleur REST pour la gestion des personnes
 * Expose les API pour toutes les opérations CRUD et fonctionnalités métier
 * 
 * @RestController : Indique que cette classe est un contrôleur REST
 * @RequestMapping : Définit le chemin de base pour tous les endpoints
 * @CrossOrigin : Autorise les requêtes CORS pour les applications frontend
 */
@RestController
@RequestMapping("/personnes")
@CrossOrigin(origins = "*")
public class PersonneController {

    @Autowired
    private PersonneService personneService;

    /**
     * Créer une nouvelle personne
     * POST /api/personnes
     * 
     * @param personne Données de la personne à créer (dans le body JSON)
     * @return Personne créée avec code 201 (CREATED)
     */
    @PostMapping
    public ResponseEntity<?> creerPersonne(@Valid @RequestBody Personne personne) {
        try {
            Personne nouvellePersonne = personneService.creerPersonne(personne);
            return ResponseEntity.status(HttpStatus.CREATED).body(nouvellePersonne);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Données invalides",
                "message", e.getMessage()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Erreur de création",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Obtenir une personne par ID
     * GET /api/personnes/{id}
     * 
     * @param id Identifiant de la personne
     * @return Personne trouvée ou 404 si non trouvée
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenirPersonne(@PathVariable Long id) {
        try {
            Optional<Personne> personne = personneService.obtenirPersonneParId(id);
            
            if (personne.isPresent()) {
                return ResponseEntity.ok(personne.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "ID invalide",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Lister toutes les personnes
     * GET /api/personnes
     * 
     * @return Liste de toutes les personnes
     */
    @GetMapping
    public ResponseEntity<List<Personne>> listerPersonnes() {
        List<Personne> personnes = personneService.listerToutesLesPersonnes();
        return ResponseEntity.ok(personnes);
    }

    /**
     * Rechercher des personnes par nom
     * GET /api/personnes/recherche?nom={nom}
     * 
     * @param nom Nom ou partie du nom à rechercher
     * @return Liste des personnes trouvées
     */
    @GetMapping("/recherche")
    public ResponseEntity<?> rechercherPersonnes(@RequestParam String nom) {
        try {
            List<Personne> personnes = personneService.rechercherPersonnesParNom(nom);
            return ResponseEntity.ok(personnes);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Paramètre invalide",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Obtenir une personne par email
     * GET /api/personnes/email/{email}
     * 
     * @param email Email de la personne
     * @return Personne trouvée ou 404
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<?> obtenirPersonneParEmail(@PathVariable String email) {
        try {
            Optional<Personne> personne = personneService.obtenirPersonneParEmail(email);
            
            if (personne.isPresent()) {
                return ResponseEntity.ok(personne.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Email invalide",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Mettre à jour une personne
     * PUT /api/personnes/{id}
     * 
     * @param id ID de la personne à modifier
     * @param personne Nouvelles données de la personne
     * @return Personne mise à jour
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> modifierPersonne(@PathVariable Long id, 
                                            @Valid @RequestBody Personne personne) {
        try {
            // Vérification que l'ID correspond
            if (!id.equals(personne.getId())) {
                personne.setId(id);
            }
            
            Personne personneModifiee = personneService.modifierPersonne(personne);
            return ResponseEntity.ok(personneModifiee);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Données invalides",
                "message", e.getMessage()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Erreur de modification",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Supprimer une personne
     * DELETE /api/personnes/{id}
     * 
     * @param id ID de la personne à supprimer
     * @return 204 (NO_CONTENT) si suppression réussie
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> supprimerPersonne(@PathVariable Long id) {
        try {
            boolean supprime = personneService.supprimerPersonne(id);
            
            if (supprime) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "ID invalide",
                "message", e.getMessage()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Erreur de suppression",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Vérifier la disponibilité d'un email
     * GET /api/personnes/email/disponible?email={email}
     * 
     * @param email Email à vérifier
     * @return Statut de disponibilité
     */
    @GetMapping("/email/disponible")
    public ResponseEntity<Map<String, Object>> verifierEmailDisponible(@RequestParam String email) {
        boolean disponible = personneService.verifierEmailDisponible(email);
        
        return ResponseEntity.ok(Map.of(
            "email", email,
            "disponible", disponible
        ));
    }

    /**
     * Authentifier une personne
     * POST /api/personnes/authentification
     * 
     * @param credentials Informations de connexion (email + mot de passe)
     * @return Personne authentifiée ou 401 (UNAUTHORIZED)
     */
    @PostMapping("/authentification")
    public ResponseEntity<?> authentifierPersonne(@RequestBody Map<String, String> credentials) {
        try {
            String email = credentials.get("email");
            String motDePasse = credentials.get("motDePasse");
            
            Optional<Personne> personne = personneService.authentifierPersonne(email, motDePasse);
            
            if (personne.isPresent()) {
                return ResponseEntity.ok(Map.of(
                    "statut", "AUTHENTIFIE",
                    "personne", personne.get()
                ));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                    "erreur", "Authentification échouée",
                    "message", "Email ou mot de passe incorrect"
                ));
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Paramètres invalides",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Changer le mot de passe d'une personne
     * POST /api/personnes/{id}/mot-de-passe
     * 
     * @param id ID de la personne
     * @param motsDePasse Ancien et nouveau mot de passe
     * @return Statut du changement
     */
    @PostMapping("/{id}/mot-de-passe")
    public ResponseEntity<?> changerMotDePasse(@PathVariable Long id, 
                                             @RequestBody Map<String, String> motsDePasse) {
        try {
            String ancienMotDePasse = motsDePasse.get("ancienMotDePasse");
            String nouveauMotDePasse = motsDePasse.get("nouveauMotDePasse");
            
            boolean modifie = personneService.changerMotDePasse(id, ancienMotDePasse, nouveauMotDePasse);
            
            if (modifie) {
                return ResponseEntity.ok(Map.of(
                    "statut", "MOT_DE_PASSE_MODIFIE",
                    "message", "Mot de passe modifié avec succès"
                ));
            } else {
                return ResponseEntity.badRequest().body(Map.of(
                    "erreur", "Échec du changement",
                    "message", "Impossible de modifier le mot de passe"
                ));
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Paramètres invalides",
                "message", e.getMessage()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Erreur de modification",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Obtenir les statistiques générales des personnes
     * GET /api/personnes/statistiques
     * 
     * @return Statistiques (nombre total, inscriptions récentes, etc.)
     */
    @GetMapping("/statistiques")
    public ResponseEntity<Map<String, Object>> obtenirStatistiques() {
        long nombreTotal = personneService.compterPersonnes();
        
        // Statistiques des 30 derniers jours
        LocalDateTime il30Jours = LocalDateTime.now().minusDays(30);
        LocalDateTime maintenant = LocalDateTime.now();
        long inscriptions30Jours = personneService.obtenirStatistiquesInscription(il30Jours, maintenant);
        
        Map<String, Object> statistiques = Map.of(
            "nombre_total_personnes", nombreTotal,
            "inscriptions_30_derniers_jours", inscriptions30Jours,
            "date_generation", maintenant
        );
        
        return ResponseEntity.ok(statistiques);
    }

    /**
     * Obtenir les statistiques d'inscription pour une période
     * GET /api/personnes/statistiques/inscriptions?debut={debut}&fin={fin}
     * 
     * @param debut Date de début
     * @param fin Date de fin
     * @return Nombre d'inscriptions dans la période
     */
    @GetMapping("/statistiques/inscriptions")
    public ResponseEntity<?> obtenirStatistiquesInscription(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime debut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        try {
            long inscriptions = personneService.obtenirStatistiquesInscription(debut, fin);
            
            return ResponseEntity.ok(Map.of(
                "periode_debut", debut,
                "periode_fin", fin,
                "nombre_inscriptions", inscriptions
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Paramètres invalides",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Lister les personnes actives (ayant créé des aliments)
     * GET /api/personnes/actives
     * 
     * @return Liste des personnes ayant créé au moins un aliment
     */
    @GetMapping("/actives")
    public ResponseEntity<List<Personne>> listerPersonnesActives() {
        List<Personne> personnesActives = personneService.listerPersonnesActives();
        return ResponseEntity.ok(personnesActives);
    }

    /**
     * Valider les données d'une personne sans la créer
     * POST /api/personnes/validation
     * 
     * @param personne Données à valider
     * @return Résultat de la validation
     */
    @PostMapping("/validation")
    public ResponseEntity<?> validerDonneesPersonne(@RequestBody Personne personne) {
        try {
            personneService.validerDonneesPersonne(personne);
            
            return ResponseEntity.ok(Map.of(
                "statut", "VALIDE",
                "message", "Toutes les données sont valides"
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "statut", "INVALIDE",
                "erreur", "Données invalides",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Gestion globale des erreurs pour ce contrôleur
     * Capture les exceptions non traitées et retourne une réponse appropriée
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> gererErreurGenerique(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
            "erreur", "Erreur interne du serveur",
            "message", e.getMessage(),
            "timestamp", LocalDateTime.now()
        ));
    }
}