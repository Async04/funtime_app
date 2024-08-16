package com.example.funtime_app.services;

import com.example.funtime_app.dto.CategoryTagDTO;
import com.example.funtime_app.dto.PostDTO;
import com.example.funtime_app.entity.Category;
import com.example.funtime_app.entity.CategoryTag;
import com.example.funtime_app.repository.CategoryRepository;
import com.example.funtime_app.repository.CategoryTagRepository;
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

        List<CategoryTagDTO> tags = postDTO.tags();
        List<CategoryTag> tagList = new ArrayList<>();
        for (CategoryTagDTO tag : tags) {
            CategoryTag categoryTag = CategoryTag.builder()
                    .tagName(tag.getTagName())
                    .id(tag.getTagId())
                    .build();
            tagList.add(categoryTag);
        }
        return tagList;

    }

    public ResponseEntity<?> getTagsByCategoryId(UUID categoryId) {
        Optional<Category> byId = categoryRepository.findById(categoryId);
        if (byId.isPresent()){

            Category category = byId.get();
            List<CategoryTag> tags = category.getTags();
            List<CategoryTagDTO> categoryTagDTOS = new ArrayList<>();

            for (CategoryTag tag : tags) {
                CategoryTagDTO categoryTagDTO = CategoryTagDTO.builder()
                        .tagId(tag.getId())
                        .tagName(tag.getTagName())
                        .build();

                categoryTagDTOS.add(categoryTagDTO);
            }

            return ResponseEntity.ok(categoryTagDTOS);
        }
        else {
            return ResponseEntity.ok("Not found Category");
        }

    }
}
