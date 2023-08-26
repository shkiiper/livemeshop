package com.liveme.controller;

import com.liveme.dto.ThumbnailDTO;
import com.liveme.entity.Thumbnail;
import com.liveme.exception.BadRequestException;
import com.liveme.exception.SuccessException;
import com.liveme.repository.ThumbnailRepository;
import com.liveme.service.ThumbnailService;

import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/thumbnail")
public class ThumbnailController {
    @Value("${media.directory}")
    private String mediaDirectory;
    private final ThumbnailService thumbnailService;
    private final ThumbnailRepository thumbnailRepository;

    @Autowired
    public ThumbnailController(ThumbnailService thumbnailService, ThumbnailRepository thumbnailRepository) {
        this.thumbnailService = thumbnailService;
        this.thumbnailRepository = thumbnailRepository;
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAllThumbnailsWithImageLinks() {
        List<Thumbnail> thumbnails = thumbnailService.getAllThumbnails();

        List<Map<String, Object>> thumbnailList = new ArrayList<>();
        for (Thumbnail thumbnail : thumbnails) {
            Map<String, Object> thumbnailInfo = new HashMap<>();
            thumbnailInfo.put("id", thumbnail.getId());
            thumbnailInfo.put("link", thumbnail.getLink());
            thumbnailInfo.put("position", thumbnail.getPosition());
            thumbnailInfo.put("galleryId", thumbnail.getGallery().getId());
            thumbnailList.add(thumbnailInfo);
        }

        return ResponseEntity.ok(thumbnailList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getThumbnailById(@PathVariable int id) {
        Thumbnail thumbnail = thumbnailService.getThumbnailById(id);
        if (thumbnail == null) {
            return ResponseEntity.notFound().build();
        }

        Map<String, Object> response = new HashMap<>();
        response.put("id", thumbnail.getId());
        response.put("position", thumbnail.getPosition());
        response.put("link", thumbnail.getLink());
        response.put("galleryId", thumbnail.getGallery().getId());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/media/{fileName:.+}")
    @PermitAll
    public ResponseEntity<byte[]> getImage(@PathVariable String fileName) {
        byte[] imageBytes;
        try {
            imageBytes = Files.readAllBytes(Paths.get(mediaDirectory + fileName));
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }

    @PostMapping
    @PermitAll
    public ResponseEntity<?> createThumbnail(@RequestParam("imageFile") MultipartFile imageFile,
            @RequestParam("position") int position,
            @RequestParam("galleryId") int galleryId) throws SuccessException {
        try {
            Thumbnail createdThumbnail = thumbnailService.createThumbnail(imageFile, position, galleryId);

            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                    .getRequest();
            String domain = request.getRequestURL().toString().replace(request.getRequestURI(), "");

            String fileName = StringUtils.cleanPath(imageFile.getOriginalFilename());
            String imageUrl = domain + "/thumbnails/media/" + fileName;

            createdThumbnail.setLink(imageUrl);
            thumbnailRepository.save(createdThumbnail);

            Map<String, Object> successResponse = new HashMap<>();
            successResponse.put("status", "Успешно");
            successResponse.put("message", "Thumbnail создан");
            successResponse.put("thumbnail", createdThumbnail);
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
    public ResponseEntity<?> updateThumbnailPosition(@PathVariable int id,
            @RequestBody Map<String, Integer> positionMap) {
        int newPosition = positionMap.get("position");

        try {
            ThumbnailDTO updatedThumbnail = thumbnailService.updateThumbnailPosition(id, newPosition);
            return new ResponseEntity<>(updatedThumbnail, HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getErrorBody(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteThumbnail(@PathVariable int id) throws BadRequestException {
        thumbnailService.deleteThumbnail(id);
    }
}
