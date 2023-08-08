package com.liveme.service;

import com.liveme.entity.Product;
import com.liveme.repository.ProductRepository;
import com.liveme.exception.BadRequestException;
import com.liveme.exception.SuccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(int id) throws BadRequestException {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            return productOptional.get();
        } else {
            throw new BadRequestException("Ошибка", "Продукт с указанным ID не найден", "id");
        }
    }

    public Product getProductWithGalleryById(int id) throws SuccessException, BadRequestException {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            return productOptional.get();
        } else {
            throw new SuccessException("Успешно", "Продукт не имеет галереи изображений");
        }
    }

    public Product createProduct(Product product) throws BadRequestException, SuccessException {
        if (product == null || product.getName() == null || product.getName().isEmpty()) {
            throw new BadRequestException("Ошибка", "Invalid product data", "product");
        }

        if (product.getPrice() < 0) {
            throw new BadRequestException("Ошибка", "Цена не может быть отрицательной", "price");
        }

        if (productRepository.existsByName(product.getName())) {
            throw new BadRequestException("Ошибка", "Product with this name already exists", "name");
        }

        Product createdProduct = productRepository.save(product);

        throw new SuccessException("Успешно", "Product created");
    }

    public Product updateProduct(int id, Product product) throws BadRequestException {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            product.setId(id);
            return productRepository.save(product);
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
