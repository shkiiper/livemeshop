package com.liveme.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liveme.entity.Category;
import com.liveme.repository.CategoryRepository;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public String createCategory(Category category) {
        categoryRepository.save(category);
        return "Category created successfully!";
    }

    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

}
