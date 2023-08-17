package com.liveme.controller;

import com.liveme.entity.Thumbnail;
import com.liveme.exception.BadRequestException;
import com.liveme.exception.SuccessException;
import com.liveme.service.ThumbnailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> createThumbnail(@RequestParam("imageFile") MultipartFile imageFile,
            @RequestParam("position") int position) throws SuccessException {
        try {
            Thumbnail createdThumbnail = thumbnailService.createThumbnail(imageFile, position);
            Map<String, String> successResponse = new HashMap<>();
            successResponse.put("status", "Успешно");
            successResponse.put("message", "Thumbnail created");
            return ResponseEntity.ok(successResponse);
        } catch (BadRequestException ex) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("status", ex.getStatus());
            errorResponse.put("errorMessage", ex.getErrorMessage());
            errorResponse.put("fieldName", ex.getFieldName());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @PatchMapping("/{id}")
    public Thumbnail updateThumbnail(@PathVariable int id, @RequestBody Thumbnail thumbnail)
            throws BadRequestException {
        return thumbnailService.updateThumbnail(id, thumbnail);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteThumbnail(@PathVariable int id) throws BadRequestException {
        thumbnailService.deleteThumbnail(id);
    }
}
