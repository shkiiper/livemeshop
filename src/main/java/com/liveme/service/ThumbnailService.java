package com.liveme.service;

import com.liveme.entity.Thumbnail;
import com.liveme.repository.ThumbnailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Thumbnail createThumbnail(Thumbnail thumbnail) {
        return thumbnailRepository.save(thumbnail);
    }

    public Thumbnail updateThumbnail(int id, Thumbnail thumbnail) {
        thumbnail.setId(id);
        return thumbnailRepository.save(thumbnail);
    }

    public void deleteThumbnail(int id) {
        thumbnailRepository.deleteById(id);
    }
}
