package com.liveme.service;

import com.liveme.entity.Gallery;
import com.liveme.repository.GalleryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GalleryService {
    private final GalleryRepository galleryRepository;

    @Autowired
    public GalleryService(GalleryRepository galleryRepository) {
        this.galleryRepository = galleryRepository;
    }

    public List<Gallery> getAllGalleries() {
        return galleryRepository.findAll();
    }

    public Gallery getGalleryById(int id) {
        return galleryRepository.findById(id).orElse(null);
    }

    public Gallery createGallery(Gallery gallery) {
        return galleryRepository.save(gallery);
    }

    public Gallery updateGallery(int id, Gallery gallery) {
        gallery.setId(id);
        return galleryRepository.save(gallery);
    }

    public void deleteGallery(int id) {
        galleryRepository.deleteById(id);
    }
}
