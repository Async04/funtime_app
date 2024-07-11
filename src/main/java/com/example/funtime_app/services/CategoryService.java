package com.example.funtime_app.services;

import com.example.funtime_app.dto.CategoryDTO;
import com.example.funtime_app.entity.Category;
import com.example.funtime_app.interfaces.CategoryServiceInterface;
import com.example.funtime_app.mappers.CategoryMapper;
import com.example.funtime_app.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService implements CategoryServiceInterface {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;


    @Override
    public List<HttpEntity<?>> getAllCategories() {
        return null;
    }

    @Override
    public HttpEntity<?> getAllTagsByCategory(String id) {
        UUID categoryId = UUID.fromString(id);
        Optional<Category> categoryOpt = categoryRepository.findById(categoryId);
        if (categoryOpt.isPresent()){
            Category category = categoryOpt.get();
            CategoryDTO categoryDTO = categoryMapper.toDto(category);
            return ResponseEntity.status(HttpStatus.OK).body(categoryDTO);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category was not found");
    }
}
