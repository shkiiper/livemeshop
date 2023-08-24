package com.liveme.dto;

import com.liveme.entity.Thumbnail;

public class ThumbnailDTO {
    private int id;
    private int position;

    public ThumbnailDTO(Thumbnail thumbnail) {
        this.id = thumbnail.getId();
        this.position = thumbnail.getPosition();
    }

    public int getId() {
        return id;
    }

    public int getPosition() {
        return position;
    }
}
