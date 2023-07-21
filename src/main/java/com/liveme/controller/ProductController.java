package com.liveme.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.liveme.entity.Product;
import com.liveme.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/create")
    public String createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @GetMapping("/all")
    public List<Product> getAllProduct() {
        return productService.getAllProduct();
    }

}