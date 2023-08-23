package com.liveme.service;

import com.liveme.dto.ProductWithThumbnailsDTO;
import com.liveme.entity.Gallery;
import com.liveme.entity.Product;
import com.liveme.repository.GalleryRepository;
import com.liveme.repository.ProductRepository;
import com.liveme.exception.BadRequestException;
import com.liveme.exception.SuccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final GalleryRepository galleryRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, GalleryRepository galleryRepository) {
        this.productRepository = productRepository;
        this.galleryRepository = galleryRepository;
    }

    @Transactional
    public List<ProductWithThumbnailsDTO> getAllProductsWithThumbnails() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(ProductWithThumbnailsDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductWithThumbnailsDTO getProductWithThumbnailInfo(int id) throws BadRequestException {
        Product product = getProductById(id);

        return new ProductWithThumbnailsDTO(product);
    }

    private Product getProductById(int id) throws BadRequestException {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            return productOptional.get();
        } else {
            throw new BadRequestException("Ошибка", "Продукт с указанным ID не найден", "id");
        }
    }

    public Product createProduct(Product product) throws BadRequestException, SuccessException {
        if (product == null || product.getName() == null || product.getName().isEmpty()) {
            throw new BadRequestException("Ошибка", "Invalid product data", "product");
        }

        if (product.getPrice() != null && product.getPrice() < 0) {
            throw new BadRequestException("Ошибка", "Цена не может быть отрицательной", "price");
        }

        if (productRepository.existsByName(product.getName())) {
            throw new BadRequestException("Ошибка", "Product with this name already exists", "name");
        }

        Gallery gallery = new Gallery();
        galleryRepository.save(gallery);

        product.setGallery(gallery);

        Product createdProduct = productRepository.save(product);

        return createdProduct;
    }

    public Product updateProduct(int id, Product product) throws BadRequestException {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product existingProduct = productOptional.get();

            if (product.getName() != null && !product.getName().isEmpty()) {
                existingProduct.setName(product.getName());
            }

            if (product.getPrice() != null) {
                existingProduct.setPrice(product.getPrice());
            }

            if (product.getSale_price() != null) {
                existingProduct.setSale_price(product.getSale_price());
            }

            if (product.getShort_description() != null) {
                existingProduct.setShort_description(product.getShort_description());
            }

            if (product.getFull_description() != null) {
                existingProduct.setFull_description(product.getFull_description());
            }

            if (product.getPublished() != null && !product.getPublished().equals(existingProduct.getPublished())) {
                existingProduct.setPublished(product.getPublished());
            }

            if (product.getRating() != null) {
                existingProduct.setRating(product.getRating());
            }

            if (product.getGallery() != null) {
                existingProduct.setGallery(product.getGallery());
            }

            if (product.getCategory() != null) {
                existingProduct.setCategory(product.getCategory());
            }

            return productRepository.save(existingProduct);
        } else {
            throw new BadRequestException("Ошибка", "Продукт с указанным ID не найден", "id");
        }
    }

    public void deleteProduct(int id) throws BadRequestException {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            productRepository.deleteById(id);
        } else {
            throw new BadRequestException("Ошибка", "Продукт с указанным ID не найден", "id");
        }
    }
}
