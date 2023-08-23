package com.liveme.controller;

import com.liveme.dto.ProductWithThumbnailsDTO;
import com.liveme.entity.Product;
import com.liveme.service.ProductService;
import com.liveme.exception.BadRequestException;
import com.liveme.exception.SuccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductWithThumbnailsDTO>> getAllProductsWithThumbnails() {
        List<ProductWithThumbnailsDTO> products = productService.getAllProductsWithThumbnails();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductWithThumbnailsDTO> getProductWithThumbnailInfo(@PathVariable int id)
            throws BadRequestException {
        ProductWithThumbnailsDTO product = productService.getProductWithThumbnailInfo(id);

        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody Product product) {
        try {
            ProductWithThumbnailsDTO createdProductDTO = productService.createProductAndReturnDTO(product);
            return ResponseEntity.ok(createdProductDTO);
        } catch (BadRequestException ex) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("status", ex.getStatus());
            errorResponse.put("errorMessage", ex.getErrorMessage());
            errorResponse.put("fieldName", ex.getFieldName());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (SuccessException ex) {
            Map<String, String> successResponse = new HashMap<>();
            successResponse.put("status", ex.getStatus());
            successResponse.put("message", ex.getMessage());
            return ResponseEntity.ok(successResponse);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable int id, @RequestBody Product product) {
        try {
            ProductWithThumbnailsDTO updatedProduct = productService.updateProduct(id, product);
            return ResponseEntity.ok(updatedProduct);
        } catch (BadRequestException ex) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("status", ex.getStatus());
            errorResponse.put("errorMessage", ex.getErrorMessage());
            errorResponse.put("fieldName", ex.getFieldName());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable int id) {
        try {
            productService.deleteProduct(id);
            Map<String, String> successResponse = new HashMap<>();
            successResponse.put("status", "Успешно");
            successResponse.put("message", "Product deleted");
            return ResponseEntity.ok(successResponse);
        } catch (BadRequestException ex) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("status", ex.getStatus());
            errorResponse.put("errorMessage", ex.getErrorMessage());
            errorResponse.put("fieldName", ex.getFieldName());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}