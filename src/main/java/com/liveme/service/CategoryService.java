package com.liveme.service;

import com.liveme.entity.Category;
import com.liveme.exception.BadRequestException;
import com.liveme.exception.SuccessException;
import com.liveme.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() throws BadRequestException {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            throw new BadRequestException("Ошибка", "Нет доступных категорий", "categories");
        }
        return categories;
    }

    public Category getCategoryById(int id) throws BadRequestException {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isPresent()) {
            return categoryOptional.get();
        } else {
            throw new BadRequestException("Ошибка", "Категория с указанным ID не найдена", "id");
        }
    }

    public Category createCategory(Category category) throws BadRequestException, SuccessException {
        if (category == null || category.getName() == null || category.getName().isEmpty()) {
            throw new BadRequestException("Ошибка", "Некорректные данные категории", "category");
        }

        Category createdCategory = categoryRepository.save(category);

        throw new SuccessException("Успешно", "Категория создана");
    }

    // public Category updateCategory(int id, Category category) throws
    // BadRequestException {
    // Optional<Category> categoryOptional = categoryRepository.findById(id);
    // if (categoryOptional.isPresent()) {
    // category.setId(id);
    // return categoryRepository.save(category);
    // } else {
    // throw new BadRequestException("Ошибка", "Категория с указанным ID не
    // найдена", "id");
    // }
    // }
    public Category updateCategory(int id, Category updatedCategory) throws BadRequestException {
        Optional<Category> categoryOptional = categoryRepository.findById(id);

        if (categoryOptional.isPresent()) {
            Category existingCategory = categoryOptional.get();

            if (updatedCategory.getName() != null && !updatedCategory.getName().isEmpty()) {
                existingCategory.setName(updatedCategory.getName());
            }
            return categoryRepository.save(existingCategory);
        } else {
            throw new BadRequestException("Ошибка", "Категория с указанным ID не найдена", "id");
        }
    }

    public void deleteCategory(int id) throws BadRequestException {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isPresent()) {
            categoryRepository.deleteById(id);
        } else {
            throw new BadRequestException("Ошибка", "Категория с указанным ID не найдена", "id");
        }
    }
}
