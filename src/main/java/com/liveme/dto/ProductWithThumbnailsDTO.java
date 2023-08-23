package com.liveme.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.liveme.entity.Product;

public class ProductWithThumbnailsDTO {
    private int id;
    private String name;
    private Integer price;
    private Integer sale_Price;
    private String short_Description;
    private String full_Description;
    private Boolean published;
    private Double rating;
    private GalleryDTO gallery;
    private CategoryDTO category;

    public ProductWithThumbnailsDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.sale_Price = product.getSale_price();
        this.short_Description = product.getShort_description();
        this.full_Description = product.getFull_description();
        this.published = product.getPublished();
        this.rating = product.getRating();

        List<ThumbnailInfoDTO> thumbnailInfoList = product.getGallery().getThumbnails().stream()
                .map(thumbnail -> new ThumbnailInfoDTO(thumbnail.getId(), thumbnail.getLink(), thumbnail.getPosition()))
                .collect(Collectors.toList());

        this.gallery = new GalleryDTO(product.getGallery().getId(), thumbnailInfoList);
        this.category = new CategoryDTO(product.getCategory().getId(), product.getCategory().getName());

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getSale_price() {
        return sale_Price;
    }

    public void setSale_price(Integer salePrice) {
        this.sale_Price = salePrice;
    }

    public String getShort_description() {
        return short_Description;
    }

    public void setShort_description(String shortDescription) {
        this.short_Description = shortDescription;
    }

    public String getFull_description() {
        return full_Description;
    }

    public void setFull_description(String full_Description) {
        this.full_Description = full_Description;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public GalleryDTO getGallery() {
        return gallery;
    }

    public void setGallery(GalleryDTO gallery) {
        this.gallery = gallery;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

}
