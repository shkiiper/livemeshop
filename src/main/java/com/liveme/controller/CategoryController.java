package com.liveme.controller;

import com.liveme.entity.Category;
import com.liveme.exception.BadRequestException;
import com.liveme.exception.SuccessException;
import com.liveme.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        try {
            return ResponseEntity.ok(categoryService.getAllCategories());
        } catch (BadRequestException ex) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("status", ex.getStatus());
            errorResponse.put("errorMessage", ex.getErrorMessage());
            errorResponse.put("fieldName", ex.getFieldName());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable int id) {
        try {
            return ResponseEntity.ok(categoryService.getCategoryById(id));
        } catch (BadRequestException ex) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("status", ex.getStatus());
            errorResponse.put("errorMessage", ex.getErrorMessage());
            errorResponse.put("fieldName", ex.getFieldName());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody Category category) {
        try {
            categoryService.createCategory(category);
            Map<String, String> successResponse = new HashMap<>();
            successResponse.put("status", "Успешно");
            successResponse.put("message", "Категория создана успешно!");
            return ResponseEntity.ok(successResponse);
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
    public ResponseEntity<?> updateCategory(@PathVariable int id, @RequestBody Category category) {
        try {
            categoryService.updateCategory(id, category);
            Map<String, String> successResponse = new HashMap<>();
            successResponse.put("status", "Успешно");
            successResponse.put("message", "Категория обновлена успешно!");
            return ResponseEntity.ok(successResponse);
        } catch (BadRequestException ex) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("status", ex.getStatus());
            errorResponse.put("errorMessage", ex.getErrorMessage());
            errorResponse.put("fieldName", ex.getFieldName());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable int id) {
        try {
            categoryService.deleteCategory(id);
            Map<String, String> successResponse = new HashMap<>();
            successResponse.put("status", "Успешно");
            successResponse.put("message", "Категория удалена успешно!");
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
