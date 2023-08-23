package com.liveme.dto;

import java.util.List;

public class GalleryDTO {
    private int id;
    private List<ThumbnailInfoDTO> thumbnails;

    public GalleryDTO(int id, List<ThumbnailInfoDTO> thumbnails) {
        this.id = id;
        this.thumbnails = thumbnails;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public GalleryDTO(List<ThumbnailInfoDTO> thumbnails) {
        this.thumbnails = thumbnails;
    }

    public List<ThumbnailInfoDTO> getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(List<ThumbnailInfoDTO> thumbnails) {
        this.thumbnails = thumbnails;
    }
}
