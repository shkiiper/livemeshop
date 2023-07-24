package com.liveme.controller;

import com.liveme.entity.Thumbnail;
import com.liveme.service.ThumbnailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/thumbnails")
public class ThumbnailController {
    private final ThumbnailService thumbnailService;

    @Autowired
    public ThumbnailController(ThumbnailService thumbnailService) {
        this.thumbnailService = thumbnailService;
    }

    @GetMapping
    public List<Thumbnail> getAllThumbnails() {
        return thumbnailService.getAllThumbnails();
    }

    @GetMapping("/{id}")
    public Thumbnail getThumbnailById(@PathVariable int id) {
        return thumbnailService.getThumbnailById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Thumbnail createThumbnail(@RequestBody Thumbnail thumbnail) {
        return thumbnailService.createThumbnail(thumbnail);
    }

    @PutMapping("/{id}")
    public Thumbnail updateThumbnail(@PathVariable int id, @RequestBody Thumbnail thumbnail) {
        return thumbnailService.updateThumbnail(id, thumbnail);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteThumbnail(@PathVariable int id) {
        thumbnailService.deleteThumbnail(id);
    }
}
