package com.liveme.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Thumbnail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private byte[] image;
    private int position;

    // Связь с галереей - многие к одному
    @ManyToOne
    @JoinColumn(name = "gallery_id")
    private Gallery gallery;

}
