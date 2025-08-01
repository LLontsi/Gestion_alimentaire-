package com.foodmanagement.controller;

import com.foodmanagement.entity.Image;
import com.foodmanagement.enums.TypeImage;
import com.foodmanagement.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Contrôleur REST pour la gestion des images
 * Gère l'upload, la visualisation et la gestion des images
 */
@RestController
@RequestMapping("/images")
@CrossOrigin(origins = "*")
public class ImageController {

    @Autowired
    private ImageService imageService;

    /**
     * Uploader une image pour un aliment
     * POST /api/images/food/{foodId}
     * 
     * @param file Fichier image à uploader
     * @param foodId ID de l'aliment
     * @param typeImage Type d'image (PRINCIPALE, GALERIE, MINIATURE)
     * @return Image créée avec informations
     */
    @PostMapping(value = "/food/{foodId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploaderImageAliment(@RequestParam("file") MultipartFile file,
                                                 @PathVariable Long foodId,
                                                 @RequestParam(defaultValue = "GALERIE") TypeImage typeImage) {
        try {
            Image image = imageService.uploaderImageAliment(file, foodId, typeImage);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "statut", "IMAGE_UPLOADEE",
                "message", "Image uploadée avec succès",
                "image", image,
                "url_acces", "/api/images/" + image.getNomFichier()
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Fichier invalide",
                "message", e.getMessage()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Erreur d'upload",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Uploader une image pour un ingrédient
     * POST /api/images/ingredient/{ingredientId}
     */
    @PostMapping(value = "/ingredient/{ingredientId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploaderImageIngredient(@RequestParam("file") MultipartFile file,
                                                    @PathVariable Long ingredientId,
                                                    @RequestParam(defaultValue = "PRINCIPALE") TypeImage typeImage) {
        try {
            Image image = imageService.uploaderImageIngredient(file, ingredientId, typeImage);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "statut", "IMAGE_UPLOADEE",
                "message", "Image uploadée avec succès",
                "image", image,
                "url_acces", "/api/images/" + image.getNomFichier()
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Fichier invalide",
                "message", e.getMessage()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Erreur d'upload",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Obtenir une image par ID
     * GET /api/images/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenirImage(@PathVariable Long id) {
        try {
            Optional<Image> image = imageService.obtenirImageParId(id);
            
            if (image.isPresent()) {
                return ResponseEntity.ok(image.get());
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
     * Servir le fichier image
     * GET /api/images/fichier/{nomFichier}
     * 
     * @param nomFichier Nom du fichier image
     * @return Contenu binaire de l'image
     */
    @GetMapping("/fichier/{nomFichier}")
    public ResponseEntity<?> servirFichierImage(@PathVariable String nomFichier) {
        try {
            Optional<Image> imageOpt = imageService.obtenirImageParNomFichier(nomFichier);
            
            if (imageOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            
            Image image = imageOpt.get();
            Path cheminFichier = Paths.get(image.getCheminFichier());
            
            if (!Files.exists(cheminFichier)) {
                return ResponseEntity.notFound().build();
            }
            
            // Détermination du type MIME
            String typeMime = Files.probeContentType(cheminFichier);
            if (typeMime == null) {
                typeMime = "application/octet-stream";
            }
            
            // Lecture et retour du fichier
            byte[] contenuFichier = Files.readAllBytes(cheminFichier);
            
            return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(typeMime))
                .contentLength(contenuFichier.length)
                .body(contenuFichier);
                
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "erreur", "Erreur de lecture du fichier",
                "message", e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Nom de fichier invalide",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Lister toutes les images
     * GET /api/images
     */
    @GetMapping
    public ResponseEntity<List<Image>> listerImages() {
        List<Image> images = imageService.listerToutesLesImages();
        return ResponseEntity.ok(images);
    }

    /**
     * Rechercher des images par type
     * GET /api/images/type/{typeImage}
     */
    @GetMapping("/type/{typeImage}")
    public ResponseEntity<?> rechercherImagesParType(@PathVariable TypeImage typeImage) {
        try {
            List<Image> images = imageService.rechercherImagesParType(typeImage);
            return ResponseEntity.ok(images);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Type invalide",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Obtenir les images d'un aliment
     * GET /api/images/food/{foodId}
     */
    @GetMapping("/food/{foodId}")
    public ResponseEntity<?> obtenirImagesAliment(@PathVariable Long foodId) {
        try {
            List<Image> images = imageService.obtenirImagesAliment(foodId);
            return ResponseEntity.ok(images);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "ID aliment invalide",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Obtenir l'image principale d'un aliment
     * GET /api/images/food/{foodId}/principale
     */
    @GetMapping("/food/{foodId}/principale")
    public ResponseEntity<?> obtenirImagePrincipaleAliment(@PathVariable Long foodId) {
        try {
            Optional<Image> image = imageService.obtenirImagePrincipaleAliment(foodId);
            
            if (image.isPresent()) {
                return ResponseEntity.ok(image.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "ID aliment invalide",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Obtenir l'image d'un ingrédient
     * GET /api/images/ingredient/{ingredientId}
     */
    @GetMapping("/ingredient/{ingredientId}")
    public ResponseEntity<?> obtenirImageIngredient(@PathVariable Long ingredientId) {
        try {
            Optional<Image> image = imageService.obtenirImageIngredient(ingredientId);
            
            if (image.isPresent()) {
                return ResponseEntity.ok(image.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "ID ingrédient invalide",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Mettre à jour une image
     * PUT /api/images/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> modifierImage(@PathVariable Long id,
                                         @RequestBody Image image) {
        try {
            if (!id.equals(image.getId())) {
                image.setId(id);
            }
            
            Image imageModifiee = imageService.modifierImage(image);
            return ResponseEntity.ok(imageModifiee);
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
     * Supprimer une image
     * DELETE /api/images/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> supprimerImage(@PathVariable Long id) {
        try {
            boolean supprime = imageService.supprimerImage(id);
            
            if (supprime) {
                return ResponseEntity.ok(Map.of(
                    "statut", "IMAGE_SUPPRIMEE",
                    "message", "Image supprimée avec succès"
                ));
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
     * Supprimer toutes les images d'un aliment
     * DELETE /api/images/food/{foodId}
     */
    @DeleteMapping("/food/{foodId}")
    public ResponseEntity<?> supprimerToutesImagesAliment(@PathVariable Long foodId) {
        try {
            int nombreSupprimees = imageService.supprimerToutesImagesAliment(foodId);
            
            return ResponseEntity.ok(Map.of(
                "statut", "IMAGES_SUPPRIMEES",
                "message", "Images supprimées avec succès",
                "nombre_images_supprimees", nombreSupprimees
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "ID aliment invalide",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Changer le type d'une image
     * POST /api/images/{id}/type
     */
    @PostMapping("/{id}/type")
    public ResponseEntity<?> changerTypeImage(@PathVariable Long id,
                                            @RequestBody Map<String, TypeImage> data) {
        try {
            TypeImage nouveauType = data.get("nouveauType");
            Image image = imageService.changerTypeImage(id, nouveauType);
            
            return ResponseEntity.ok(Map.of(
                "statut", "TYPE_MODIFIE",
                "message", "Type d'image modifié avec succès",
                "image", image
            ));
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
     * Définir une image comme principale
     * POST /api/images/{id}/principale
     */
    @PostMapping("/{id}/principale")
    public ResponseEntity<?> definirCommePrincipale(@PathVariable Long id) {
        try {
            boolean definie = imageService.definirImageCommePrincipale(id);
            
            if (definie) {
                return ResponseEntity.ok(Map.of(
                    "statut", "IMAGE_PRINCIPALE_DEFINIE",
                    "message", "Image définie comme principale avec succès"
                ));
            } else {
                return ResponseEntity.badRequest().body(Map.of(
                    "erreur", "Échec de la définition",
                    "message", "Impossible de définir l'image comme principale"
                ));
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "ID invalide",
                "message", e.getMessage()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Erreur de définition",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Rechercher des images par tranche de taille
     * GET /api/images/taille?min={min}&max={max}
     */
    @GetMapping("/taille")
    public ResponseEntity<?> rechercherParTaille(@RequestParam Long min,
                                                @RequestParam Long max) {
        try {
            List<Image> images = imageService.rechercherImagesParTaille(min, max);
            return ResponseEntity.ok(images);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Paramètres invalides",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Rechercher des images par période
     * GET /api/images/periode?debut={debut}&fin={fin}
     */
    @GetMapping("/periode")
    public ResponseEntity<?> rechercherParPeriode(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime debut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        try {
            List<Image> images = imageService.rechercherImagesParPeriode(debut, fin);
            return ResponseEntity.ok(images);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Paramètres invalides",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Obtenir les statistiques des images
     * GET /api/images/statistiques
     */
    @GetMapping("/statistiques")
    public ResponseEntity<Map<String, Object>> obtenirStatistiques() {
        long nombreTotal = imageService.compterImages();
        Long tailleTotale = imageService.obtenirTailleTotaleImages();
        
        // Statistiques par type
        Map<String, Long> statistiquesParType = Map.of(
            "PRINCIPALE", imageService.compterImagesParType(TypeImage.PRINCIPALE),
            "GALERIE", imageService.compterImagesParType(TypeImage.GALERIE),
            "MINIATURE", imageService.compterImagesParType(TypeImage.MINIATURE)
        );
        
        Map<String, Object> statistiques = Map.of(
            "nombre_total_images", nombreTotal,
            "taille_totale_octets", tailleTotale,
            "taille_totale_mb", tailleTotale / (1024.0 * 1024.0),
            "repartition_par_type", statistiquesParType,
            "taille_moyenne_octets", nombreTotal > 0 ? tailleTotale / nombreTotal : 0,
            "date_generation", LocalDateTime.now()
        );
        
        return ResponseEntity.ok(statistiques);
    }

    /**
     * Valider un fichier image avant upload
     * POST /api/images/validation
     */
    @PostMapping(value = "/validation", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> validerFichierImage(@RequestParam("file") MultipartFile file) {
        try {
            imageService.validerFichierImage(file);
            
            return ResponseEntity.ok(Map.of(
                "statut", "VALIDE",
                "message", "Le fichier est valide",
                "nom_fichier", file.getOriginalFilename(),
                "taille_octets", file.getSize(),
                "type_mime", file.getContentType()
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "statut", "INVALIDE",
                "erreur", "Fichier invalide",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Nettoyer les fichiers orphelins
     * POST /api/images/nettoyage
     */
    @PostMapping("/nettoyage")
    public ResponseEntity<Map<String, Object>> nettoyerFichiersOrphelins() {
        int fichiersSupprimes = imageService.nettoyerFichiersOrphelins();
        
        return ResponseEntity.ok(Map.of(
            "statut", "NETTOYAGE_TERMINE",
            "message", "Nettoyage des fichiers orphelins terminé",
            "fichiers_supprimes", fichiersSupprimes
        ));
    }

    /**
     * Générer un nom de fichier unique
     * GET /api/images/nom-unique?original={original}
     */
    @GetMapping("/nom-unique")
    public ResponseEntity<?> genererNomUnique(@RequestParam String original) {
        try {
            String nomUnique = imageService.genererNomFichierUnique(original);
            
            return ResponseEntity.ok(Map.of(
                "nom_original", original,
                "nom_unique", nomUnique
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "erreur", "Nom original invalide",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Gestion globale des erreurs pour ce contrôleur
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