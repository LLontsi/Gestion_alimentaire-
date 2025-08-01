package com.univyaounde.foodmanagement.service;

import com.univyaounde.foodmanagement.dao.ImageDAO;
import com.univyaounde.foodmanagement.model.Image;
import com.univyaounde.foodmanagement.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ImageService {
    
    @Autowired
    private ImageRepository imageRepository;
    
    @Autowired
    private ImageDAO imageDAO;
    
    public Image createImage(Image image) {
        return imageDAO.save(image);
    }
    
    public Optional<Image> getImageById(Long id) {
        return imageDAO.findById(id);
    }
    
    public List<Image> getAllImages() {
        return imageDAO.findAll();
    }
    
    public Image updateImage(Long id, Image imageDetails) {
        Optional<Image> optionalImage = imageDAO.findById(id);
        if (optionalImage.isPresent()) {
            Image image = optionalImage.get();
            image.setNomFichier(imageDetails.getNomFichier());
            image.setChemin(imageDetails.getChemin());
            image.setTypeMime(imageDetails.getTypeMime());
            image.setTaille(imageDetails.getTaille());
            image.setDescription(imageDetails.getDescription());
            return imageDAO.save(image);
        }
        throw new RuntimeException("Image non trouvée avec l'id: " + id);
    }
    
    public void deleteImage(Long id) {
        if (imageDAO.findById(id).isPresent()) {
            imageDAO.deleteById(id);
        } else {
            throw new RuntimeException("Image non trouvée avec l'id: " + id);
        }
    }
    
    public List<Image> getImagesByFood(Long foodId) {
        return imageDAO.findImagesByFood(foodId);
    }
    
    public List<Image> getImagesByType(String typeMime) {
        return imageRepository.findByTypeMime(typeMime);
    }
    
    public List<Image> getLargeImages(Long minSize) {
        return imageRepository.findByTailleGreaterThan(minSize);
    }
    
    public Long getTotalImageSize() {
        return imageDAO.getTotalImageSize();
    }
    
    public Long getImageCountByFood(Long foodId) {
        return imageRepository.countByFoodId(foodId);
    }
}