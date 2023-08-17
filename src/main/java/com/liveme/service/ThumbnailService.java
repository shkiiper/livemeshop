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

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ThumbnailService {
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
            if (imageFile.isEmpty()) {
                throw new BadRequestException("Ошибка", "Изображение не загружено", "image");
            }

            if (imageFile.getSize() > 10 * 1024 * 1024) {
                throw new BadRequestException("Ошибка", "Размер изображения превышает 10 МБ", "image");
            }

            byte[] imageBytes = imageFile.getBytes();

            Thumbnail thumbnail = new Thumbnail();
            thumbnail.setImage(imageBytes);
            thumbnail.setPosition(position);

            Thumbnail createdThumbnail = thumbnailRepository.save(thumbnail);

            throw new SuccessException("Успешно", "Thumbnail создан");
        } catch (IOException e) {
            throw new BadRequestException("Ошибка", "Ошибка при обработке изображения", "image");
        }
    }

    public Thumbnail updateThumbnail(int id, Thumbnail thumbnail) throws BadRequestException {
        Thumbnail existingThumbnail = thumbnailRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Ошибка", "Изображение не найдено", "id"));

        thumbnail.setId(id);
        return thumbnailRepository.save(thumbnail);
    }

    public void deleteThumbnail(int id) throws BadRequestException {
        Thumbnail existingThumbnail = thumbnailRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Ошибка", "Изображение не найдено", "id"));

        thumbnailRepository.deleteById(id);
    }
}
