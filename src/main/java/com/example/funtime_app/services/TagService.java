package com.example.funtime_app.services;

import com.example.funtime_app.dto.request.CategoryRequestTagDTO;
import com.example.funtime_app.dto.request.TagSaveDTO;
import com.example.funtime_app.dto.response.CategoryResponseTagDTO;
import com.example.funtime_app.dto.PostDTO;
import com.example.funtime_app.entity.Category;
import com.example.funtime_app.entity.CategoryTag;
import com.example.funtime_app.repository.CategoryRepository;
import com.example.funtime_app.repository.CategoryTagRepository;
import com.example.funtime_app.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TagService {

    private final CategoryTagRepository categoryTagRepository;
    private final CategoryRepository categoryRepository;


    public List<CategoryTag> generateTags(PostDTO postDTO){

        List<CategoryRequestTagDTO> tags = postDTO.getTags();
        List<CategoryTag> tagList = new ArrayList<>();
        for (CategoryRequestTagDTO tag : tags) {
            CategoryTag categoryTag = categoryTagRepository.findById(tag.getTagId()).get();
            tagList.add(categoryTag);
        }
        return tagList;

    }

    public ResponseEntity<?> getTagsByCategoryId(UUID categoryId) {
        Optional<Category> byId = categoryRepository.findById(categoryId);
        if (byId.isPresent()){

            Category category = byId.get();
            List<CategoryTag> tags = category.getTags();
            List<CategoryResponseTagDTO> categoryResponseTagDTOS = new ArrayList<>();

            for (CategoryTag tag : tags) {
                CategoryResponseTagDTO categoryResponseTagDTO = CategoryResponseTagDTO.builder()
                        .tagId(tag.getId())
                        .tagName(tag.getTagName())
                        .build();

                categoryResponseTagDTOS.add(categoryResponseTagDTO);
            }

            return ResponseEntity.ok(categoryResponseTagDTOS);
        }
        else {
            return ResponseEntity.ok("Not found Category");
        }

    }

    @Transactional
    public ResponseEntity<?> addTag(TagSaveDTO tagSaveDTO) {
        Optional<Category> byId = categoryRepository.findById(tagSaveDTO.getCategoryId());
        if (byId.isPresent()){
            CategoryTag categoryTag = CategoryTag.builder()
                    .tagName(tagSaveDTO.getName())
                    .build();
            categoryTagRepository.save(categoryTag);
            Category category = byId.get();
            category.getTags().add(categoryTag);
            categoryRepository.save(category);
            return ResponseEntity.ok("Tag saved !!!");
        }

        return ResponseEntity.badRequest().body("Category not found!!!");
    }
}
