package com.liveme.service;

import com.liveme.dto.ThumbnailDTO;
import com.liveme.entity.Gallery;
import com.liveme.entity.Thumbnail;
import com.liveme.exception.BadRequestException;
import com.liveme.exception.SuccessException;
import com.liveme.repository.ThumbnailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.util.StringUtils;
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

    public Thumbnail createThumbnail(MultipartFile imageFile, int position, int galleryId)
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

            Gallery gallery = new Gallery();
            gallery.setId(galleryId);
            thumbnail.setGallery(gallery);

            Thumbnail createdThumbnail = thumbnailRepository.save(thumbnail);

            return createdThumbnail;

        } catch (IOException e) {
            throw new BadRequestException("Ошибка", "Ошибка при обработке изображения", "image");
        }
    }

    @Transactional
    public ThumbnailDTO updateThumbnailPosition(int id, int newPosition) throws BadRequestException {
        Optional<Thumbnail> thumbnailOptional = thumbnailRepository.findById(id);

        if (thumbnailOptional.isPresent()) {
            Thumbnail existingThumbnail = thumbnailOptional.get();
            existingThumbnail.setPosition(newPosition);

            Thumbnail updatedThumbnail = thumbnailRepository.save(existingThumbnail);
            return new ThumbnailDTO(updatedThumbnail);
        } else {
            throw new BadRequestException("Ошибка", "Изображение с указанным ID не найдено", "id");
        }
    }

    public void deleteThumbnail(int id) throws BadRequestException {
        Thumbnail existingThumbnail = thumbnailRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Ошибка", "Изображение не найдено", "id"));

        thumbnailRepository.deleteById(id);
    }
}
