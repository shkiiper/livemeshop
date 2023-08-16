package com.liveme.controller;

import com.liveme.entity.Gallery;
import com.liveme.service.GalleryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public List<Gallery> getAllGalleries() {
        return galleryService.getAllGalleries();
    }

    @GetMapping("/{id}")
    public Gallery getGalleryById(@PathVariable int id) {
        return galleryService.getGalleryById(id);
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
