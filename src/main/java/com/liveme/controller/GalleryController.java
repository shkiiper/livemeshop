package com.liveme.controller;

import com.liveme.dto.ThumbnailInfoDTO;
import com.liveme.entity.Gallery;
import com.liveme.service.GalleryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gallery")
public class GalleryController {
    private final GalleryService galleryService;

    @Autowired
    public GalleryController(GalleryService galleryService) {
        this.galleryService = galleryService;
    }

    @GetMapping
    public ResponseEntity<List<List<ThumbnailInfoDTO>>> getAllGalleriesWithThumbnails() {
        List<List<ThumbnailInfoDTO>> galleriesWithThumbnails = galleryService.getAllGalleriesWithThumbnails();

        if (galleriesWithThumbnails.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(galleriesWithThumbnails);
    }

    @GetMapping("/{galleryId}")
    public ResponseEntity<List<ThumbnailInfoDTO>> getThumbnailInfoForGallery(@PathVariable int galleryId) {
        List<ThumbnailInfoDTO> thumbnailInfoList = galleryService.getThumbnailInfoForGallery(galleryId);

        if (thumbnailInfoList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(thumbnailInfoList);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Gallery createGallery(@RequestBody Gallery gallery) {
        return galleryService.createGallery(gallery);
    }

    @PatchMapping("/{id}")
    public Gallery updateGallery(@PathVariable int id, @RequestBody Gallery gallery) {
        return galleryService.updateGallery(id, gallery);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGallery(@PathVariable int id) {
        galleryService.deleteGallery(id);
    }
}
