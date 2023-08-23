package com.liveme.service;

import com.liveme.dto.ThumbnailInfoDTO;
import com.liveme.entity.Gallery;
import com.liveme.entity.Thumbnail;
import com.liveme.repository.GalleryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class GalleryService {
    private final GalleryRepository galleryRepository;

    @Autowired
    public GalleryService(GalleryRepository galleryRepository) {
        this.galleryRepository = galleryRepository;
    }

    @Transactional
    public List<List<ThumbnailInfoDTO>> getAllGalleriesWithThumbnails() {
        List<List<ThumbnailInfoDTO>> galleriesWithThumbnails = new ArrayList<>();
        List<Gallery> galleries = galleryRepository.findAll();

        for (Gallery gallery : galleries) {
            List<ThumbnailInfoDTO> thumbnailInfoList = new ArrayList<>();
            for (Thumbnail thumbnail : gallery.getThumbnails()) {
                ThumbnailInfoDTO thumbnailInfo = new ThumbnailInfoDTO(thumbnail.getId(), thumbnail.getLink(),
                        thumbnail.getPosition());
                thumbnailInfoList.add(thumbnailInfo);
            }
            galleriesWithThumbnails.add(thumbnailInfoList);
        }

        return galleriesWithThumbnails;
    }

    @Transactional
    public List<ThumbnailInfoDTO> getThumbnailInfoForGallery(int galleryId) {
        Gallery gallery = galleryRepository.findById(galleryId).orElse(null);

        if (gallery == null) {
            return Collections.emptyList();
        }

        List<ThumbnailInfoDTO> thumbnailInfoList = new ArrayList<>();
        for (Thumbnail thumbnail : gallery.getThumbnails()) {
            ThumbnailInfoDTO thumbnailInfo = new ThumbnailInfoDTO(thumbnail.getId(), thumbnail.getLink(),
                    thumbnail.getPosition());
            thumbnailInfoList.add(thumbnailInfo);
        }

        return thumbnailInfoList;
    }

    @Transactional
    public Gallery createGallery(Gallery gallery) {
        return galleryRepository.save(gallery);
    }

    @Transactional
    public Gallery updateGallery(int id, Gallery gallery) {
        gallery.setId(id);
        return galleryRepository.save(gallery);
    }

    @Transactional
    public void deleteGallery(int id) {
        galleryRepository.deleteById(id);
    }
}
