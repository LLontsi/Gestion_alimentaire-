package com.foodmanagement.util;

import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Utilitaire pour le traitement des images
 * Gère le redimensionnement, la compression et la validation des images
 */
@Component
public class ImageUtils {

    /**
     * Extensions d'images supportées
     */
    private static final List<String> EXTENSIONS_SUPPORTEES = 
        Arrays.asList("jpg", "jpeg", "png", "gif", "webp");

    /**
     * Types MIME d'images supportés
     */
    private static final List<String> TYPES_MIME_SUPPORTES = 
        Arrays.asList("image/jpeg", "image/png", "image/gif", "image/webp");

    /**
     * Taille maximale par défaut pour les miniatures
     */
    private static final int TAILLE_MINIATURE_DEFAUT = 150;

    /**
     * Taille maximale par défaut pour les images normales
     */
    private static final int TAILLE_NORMALE_DEFAUT = 800;

    /**
     * Qualité de compression JPEG par défaut
     */
    private static final float QUALITE_COMPRESSION_DEFAUT = 0.85f;

    /**
     * Valider une extension de fichier
     * 
     * @param nomFichier Nom du fichier à valider
     * @return true si l'extension est supportée
     */
    public boolean validerExtension(String nomFichier) {
        if (nomFichier == null || !nomFichier.contains(".")) {
            return false;
        }
        
        String extension = extraireExtension(nomFichier);
        return EXTENSIONS_SUPPORTEES.contains(extension.toLowerCase());
    }

    /**
     * Valider un type MIME
     * 
     * @param typeMime Type MIME à valider
     * @return true si le type est supporté
     */
    public boolean validerTypeMime(String typeMime) {
        if (typeMime == null) {
            return false;
        }
        
        return TYPES_MIME_SUPPORTES.contains(typeMime.toLowerCase());
    }

    /**
     * Extraire l'extension d'un nom de fichier
     * 
     * @param nomFichier Nom du fichier
     * @return Extension sans le point
     */
    public String extraireExtension(String nomFichier) {
        if (nomFichier == null || !nomFichier.contains(".")) {
            return "";
        }
        
        return nomFichier.substring(nomFichier.lastIndexOf(".") + 1).toLowerCase();
    }

    /**
     * Redimensionner une image
     * 
     * @param imageBytes Bytes de l'image originale
     * @param largeurMax Largeur maximale
     * @param hauteurMax Hauteur maximale
     * @param format Format de sortie (jpg, png, etc.)
     * @return Bytes de l'image redimensionnée
     * @throws IOException Si erreur de traitement
     */
    public byte[] redimensionnerImage(byte[] imageBytes, int largeurMax, int hauteurMax, String format) 
            throws IOException {
        
        // Lecture de l'image source
        BufferedImage imageOriginale = ImageIO.read(new ByteArrayInputStream(imageBytes));
        if (imageOriginale == null) {
            throw new IOException("Impossible de lire l'image");
        }

        // Calcul des nouvelles dimensions en conservant le ratio
        Dimension nouvellesTailles = calculerNouvellesTailles(
            imageOriginale.getWidth(), 
            imageOriginale.getHeight(), 
            largeurMax, 
            hauteurMax
        );

        // Redimensionnement
        BufferedImage imageRedimensionnee = new BufferedImage(
            nouvellesTailles.width, 
            nouvellesTailles.height, 
            BufferedImage.TYPE_INT_RGB
        );

        Graphics2D graphics = imageRedimensionnee.createGraphics();
        
        // Configuration pour une meilleure qualité
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        graphics.drawImage(imageOriginale, 0, 0, nouvellesTailles.width, nouvellesTailles.height, null);
        graphics.dispose();

        // Conversion en bytes
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(imageRedimensionnee, format, outputStream);
        
        return outputStream.toByteArray();
    }

    /**
     * Générer une miniature
     * 
     * @param imageBytes Bytes de l'image originale
     * @param format Format de sortie
     * @return Bytes de la miniature
     * @throws IOException Si erreur de traitement
     */
    public byte[] genererMiniature(byte[] imageBytes, String format) throws IOException {
        return redimensionnerImage(imageBytes, TAILLE_MINIATURE_DEFAUT, TAILLE_MINIATURE_DEFAUT, format);
    }

    /**
     * Optimiser une image pour le web
     * 
     * @param imageBytes Bytes de l'image originale
     * @param format Format de sortie
     * @return Bytes de l'image optimisée
     * @throws IOException Si erreur de traitement
     */
    public byte[] optimiserPourWeb(byte[] imageBytes, String format) throws IOException {
        return redimensionnerImage(imageBytes, TAILLE_NORMALE_DEFAUT, TAILLE_NORMALE_DEFAUT, format);
    }

    /**
     * Calculer les nouvelles dimensions en conservant le ratio
     * 
     * @param largeurOriginale Largeur originale
     * @param hauteurOriginale Hauteur originale
     * @param largeurMax Largeur maximale souhaitée
     * @param hauteurMax Hauteur maximale souhaitée
     * @return Nouvelles dimensions
     */
    private Dimension calculerNouvellesTailles(int largeurOriginale, int hauteurOriginale, 
                                             int largeurMax, int hauteurMax) {
        
        // Si l'image est déjà plus petite, on garde les dimensions originales
        if (largeurOriginale <= largeurMax && hauteurOriginale <= hauteurMax) {
            return new Dimension(largeurOriginale, hauteurOriginale);
        }

        // Calcul du ratio de redimensionnement
        double ratioLargeur = (double) largeurMax / largeurOriginale;
        double ratioHauteur = (double) hauteurMax / hauteurOriginale;
        double ratio = Math.min(ratioLargeur, ratioHauteur);

        int nouvelleLargeur = (int) (largeurOriginale * ratio);
        int nouvelleHauteur = (int) (hauteurOriginale * ratio);

        return new Dimension(nouvelleLargeur, nouvelleHauteur);
    }

    /**
     * Obtenir les informations d'une image
     * 
     * @param imageBytes Bytes de l'image
     * @return Informations sur l'image
     * @throws IOException Si erreur de lecture
     */
    public InfoImage obtenirInfoImage(byte[] imageBytes) throws IOException {
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
        if (image == null) {
            throw new IOException("Impossible de lire l'image");
        }

        InfoImage info = new InfoImage();
        info.setLargeur(image.getWidth());
        info.setHauteur(image.getHeight());
        info.setTaille(imageBytes.length);
        info.setFormat(determinerFormat(imageBytes));

        return info;
    }

    /**
     * Déterminer le format d'une image à partir de ses bytes
     * 
     * @param imageBytes Bytes de l'image
     * @return Format de l'image (jpg, png, etc.)
     */
    private String determinerFormat(byte[] imageBytes) {
        // Vérification des signatures de fichiers (magic numbers)
        if (imageBytes.length >= 3) {
            // JPEG
            if (imageBytes[0] == (byte) 0xFF && imageBytes[1] == (byte) 0xD8 && imageBytes[2] == (byte) 0xFF) {
                return "jpg";
            }
        }
        
        if (imageBytes.length >= 8) {
            // PNG
            if (imageBytes[0] == (byte) 0x89 && imageBytes[1] == 0x50 && imageBytes[2] == 0x4E && imageBytes[3] == 0x47) {
                return "png";
            }
        }
        
        if (imageBytes.length >= 6) {
            // GIF
            if (imageBytes[0] == 0x47 && imageBytes[1] == 0x49 && imageBytes[2] == 0x46) {
                return "gif";
            }
        }
        
        // Par défaut
        return "jpg";
    }

    /**
     * Convertir une taille en bytes vers une chaîne lisible
     * 
     * @param taille Taille en bytes
     * @return Chaîne formatée (ex: "1.5 MB")
     */
    public String formaterTaille(long taille) {
        if (taille < 1024) {
            return taille + " B";
        } else if (taille < 1024 * 1024) {
            return String.format("%.1f KB", taille / 1024.0);
        } else {
            return String.format("%.1f MB", taille / (1024.0 * 1024.0));
        }
    }

    /**
     * Classe pour les informations d'une image
     */
    public static class InfoImage {
        private int largeur;
        private int hauteur;
        private long taille;
        private String format;

        // Constructeurs
        public InfoImage() {}

        public InfoImage(int largeur, int hauteur, long taille, String format) {
            this.largeur = largeur;
            this.hauteur = hauteur;
            this.taille = taille;
            this.format = format;
        }

        // Getters et Setters
        public int getLargeur() {
            return largeur;
        }

        public void setLargeur(int largeur) {
            this.largeur = largeur;
        }

        public int getHauteur() {
            return hauteur;
        }

        public void setHauteur(int hauteur) {
            this.hauteur = hauteur;
        }

        public long getTaille() {
            return taille;
        }

        public void setTaille(long taille) {
            this.taille = taille;
        }

        public String getFormat() {
            return format;
        }

        public void setFormat(String format) {
            this.format = format;
        }

        @Override
        public String toString() {
            return String.format("Image: %dx%d, %s, %s", 
                               largeur, hauteur, format, formaterTaille(taille));
        }

        private String formaterTaille(long taille) {
            if (taille < 1024) {
                return taille + " B";
            } else if (taille < 1024 * 1024) {
                return String.format("%.1f KB", taille / 1024.0);
            } else {
                return String.format("%.1f MB", taille / (1024.0 * 1024.0));
            }
        }
    }
}