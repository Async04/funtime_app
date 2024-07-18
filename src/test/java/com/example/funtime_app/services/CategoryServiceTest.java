package com.example.funtime_app.services;

import com.example.funtime_app.entity.Category;
import com.example.funtime_app.mappers.CategoryMapper;
import com.example.funtime_app.repository.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CategoryServiceTest {

    private CategoryService categoryService;
    private CategoryRepository categoryRepository;
    private CategoryMapper categoryMapper;
    @BeforeEach
    void before(){

        this.categoryRepository = Mockito.mock(CategoryRepository.class);
        this.categoryMapper = Mockito.mock(CategoryMapper.class);
        this.categoryService = new CategoryService(categoryRepository, categoryMapper);
    }

    @Test
    void getAllCategories() {

        Mockito.when(categoryRepository.findAll()).thenReturn(new ArrayList<Category>());
        List<HttpEntity<?>> allCategories = categoryService.getAllCategories();
        Assertions.assertNotNull(allCategories);

    }

}