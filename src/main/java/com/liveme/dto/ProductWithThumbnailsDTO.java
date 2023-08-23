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
    private int categoryId;
    private String categoryName;

    public ProductWithThumbnailsDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.sale_Price = product.getSale_price();
        this.short_Description = product.getShort_description();
        this.full_Description = product.getFull_description();
        this.published = product.getPublished();
        this.rating = product.getRating();
        this.categoryId = product.getCategory().getId();
        this.categoryName = product.getCategory().getName();

        List<ThumbnailInfoDTO> thumbnailInfoList = product.getGallery().getThumbnails().stream()
                .map(thumbnail -> new ThumbnailInfoDTO(thumbnail.getId(), thumbnail.getLink(), thumbnail.getPosition()))
                .collect(Collectors.toList());

        this.gallery = new GalleryDTO(product.getGallery().getId(), thumbnailInfoList);

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

    public Integer getSalePrice() {
        return sale_Price;
    }

    public void setSalePrice(Integer salePrice) {
        this.sale_Price = salePrice;
    }

    public String getShortDescription() {
        return short_Description;
    }

    public void setShortDescription(String shortDescription) {
        this.short_Description = shortDescription;
    }

    public String getFullDescription() {
        return full_Description;
    }

    public void setFullDescription(String fullDescription) {
        this.full_Description = fullDescription;
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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public GalleryDTO getGallery() {
        return gallery;
    }

    public void setGallery(GalleryDTO gallery) {
        this.gallery = gallery;
    }

}
