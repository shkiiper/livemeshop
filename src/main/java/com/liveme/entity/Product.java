package com.liveme.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int price;
    private int sale_price;
    private String short_description;
    private String full_description;
    private boolean published;
    private double rating;

    @ManyToOne(fetch = FetchType.LAZY) // если нужно всегда загружать галерею
    @JoinColumn(name = "gallery_id")
    private Gallery gallery;
}