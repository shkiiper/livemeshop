package com.liveme.service;

import com.liveme.entity.Thumbnail;
import com.liveme.exception.BadRequestException;
import com.liveme.exception.SuccessException;
import com.liveme.repository.ThumbnailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
public class ThumbnailService {
    @Value("${media.directory}")
    private String mediaDirectory;
    private final ThumbnailRepository thumbnailRepository;

    @Autowired
    public ThumbnailService(ThumbnailRepository thumbnailRepository) {
        this.thumbnailRepository = thumbnailRepository;
    }

    public List<Thumbnail> getAllThumbnails() {
        return thumbnailRepository.findAll();
    }

    public Thumbnail getThumbnailById(int id) {
        return thumbnailRepository.findById(id).orElse(null);
    }

    public Thumbnail createThumbnail(MultipartFile imageFile, int position)
            throws BadRequestException, SuccessException {
        try {
            byte[] imageBytes = imageFile.getBytes();

            String fileName = StringUtils.cleanPath(imageFile.getOriginalFilename());
            String thumbnailFilePath = mediaDirectory + fileName;

            Path targetLocation = Path.of(thumbnailFilePath);

            Files.copy(imageFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            Thumbnail thumbnail = new Thumbnail();
            thumbnail.setImage(imageBytes);
            thumbnail.setPosition(position);

            Thumbnail createdThumbnail = thumbnailRepository.save(thumbnail);

            return createdThumbnail;

        } catch (IOException e) {
            throw new BadRequestException("Ошибка", "Ошибка при обработке изображения", "image");
        }
    }

    public Thumbnail updateThumbnail(int id, Thumbnail updatedThumbnail) throws BadRequestException {
        Thumbnail existingThumbnail = thumbnailRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Ошибка", "Изображение не найдено", "id"));

        existingThumbnail.setImage(updatedThumbnail.getImage()); // Обновляем изображение (если необходимо)
        existingThumbnail.setPosition(updatedThumbnail.getPosition()); // Обновляем позицию (если необходимо)
        existingThumbnail.setLink(updatedThumbnail.getLink()); // Обновляем ссылку (если необходимо)

        return thumbnailRepository.save(existingThumbnail);
    }

    public void deleteThumbnail(int id) throws BadRequestException {
        Thumbnail existingThumbnail = thumbnailRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Ошибка", "Изображение не найдено", "id"));

        thumbnailRepository.deleteById(id);
    }
}
