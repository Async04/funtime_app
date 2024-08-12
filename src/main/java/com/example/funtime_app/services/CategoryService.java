package com.example.funtime_app.services;

import com.example.funtime_app.dto.CategoryDTO;
import com.example.funtime_app.dto.CategoryTagDTO;
import com.example.funtime_app.dto.CreateCategoryDTO;
import com.example.funtime_app.entity.Attachment;
import com.example.funtime_app.entity.Category;
import com.example.funtime_app.entity.CategoryTag;
import com.example.funtime_app.interfaces.CategoryServiceInterface;
import com.example.funtime_app.mappers.CategoryMapper;
import com.example.funtime_app.repository.AttachmentRepository;
import com.example.funtime_app.repository.CategoryRepository;
import com.example.funtime_app.repository.CategoryTagRepository;
import com.github.fashionbrot.annotation.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Role;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService implements CategoryServiceInterface {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final AttachmentService attachmentService;
    private final CategoryTagRepository categoryTagRepository;
    private final AttachmentRepository attachmentRepository;


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


    @Override
    public ResponseEntity<?> addNewCategory(CreateCategoryDTO createCategoryDTO) {
        if (createCategoryDTO.name().isBlank()||createCategoryDTO.name().isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Category name not found");
        }
        List<CategoryTag> tags=new ArrayList<>();
        if (createCategoryDTO.tagIds()!=null) {
            for (UUID tagId : createCategoryDTO.tagIds()) {
                Optional<CategoryTag> byId = categoryTagRepository.findById(tagId);
                byId.ifPresent(tags::add);
            }
        }
        Attachment attachment = null;
        if (createCategoryDTO.attachmentId()!=null){
            Optional<Attachment> byId = attachmentRepository.findById(createCategoryDTO.attachmentId());
            if (byId.isPresent()){
                attachment=byId.get();
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Attachment not found");
            }
        }
        Category category=Category.builder()
                .attachment(attachment)
                .tags(tags)
                .name(createCategoryDTO.name())
                .build();
        Category save = categoryRepository.save(category);
        CategoryDTO categoryDTO = categoryMapper.toDto(save);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryDTO);

    }

    @Override
    public ResponseEntity<?> editCategory(@NotNull String id,@NotNull CreateCategoryDTO createCategoryDTO) {
        UUID uuid = UUID.fromString(id);
        Optional<Category> byId = categoryRepository.findById(uuid);
        if (byId.isPresent()){
            List<CategoryTag> tags=new ArrayList<>();
            if (createCategoryDTO.tagIds()!=null) {
                for (UUID tagId : createCategoryDTO.tagIds()) {
                    Optional<CategoryTag> categoryTag1 = categoryTagRepository.findById(tagId);
                    categoryTag1.ifPresent(tags::add);
                }
            }
            Attachment attachment = null;
            if (createCategoryDTO.attachmentId()!=null){
                Optional<Attachment> attachment1 = attachmentRepository.findById(createCategoryDTO.attachmentId());
                if (byId.isPresent()){
                    attachment=attachment1.get();
                }else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Attachment not found");
                }
            }
            Category category=byId.get();
            category.setTags(tags);
            category.setName(createCategoryDTO.name());
            category.setAttachment(attachment);
           return ResponseEntity.status(HttpStatus.OK).body(categoryMapper.toDto(category));
        }else {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found");
        }

    }

    @Override
    public ResponseEntity<?> deleteCategory(@NotNull String id) {
        UUID uuid = UUID.fromString(id);
        Optional<Category> byId = categoryRepository.findById(uuid);
        if (byId.isPresent()){
            Category category = byId.get();
            categoryRepository.delete(category);
            return ResponseEntity.status(HttpStatus.OK).body(category.getName()+" deleted!!!");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found!!!");
        }
    }

    @Override
    public ResponseEntity<?> addNewTag(CategoryTagDTO categoryTagDTO) {
        return null;
    }
}
