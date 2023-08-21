package com.liveme.dto;

public class ThumbnailInfoDTO {
    private int id;
    private int position;
    private String link;
    private int galleryId; // Добавленное поле

    public ThumbnailInfoDTO(int id, String link, int position) {
        this.id = id;
        this.position = position;
        this.link = link;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getGalleryId() {
        return galleryId;
    }

    public void setGalleryId(int galleryId) {
        this.galleryId = galleryId;
    }
}
