package com.example.funtime_app.services;

import com.example.funtime_app.dto.CategoryDTO;
import com.example.funtime_app.dto.request.CategorySaveDTO;
import com.example.funtime_app.entity.Attachment;
import com.example.funtime_app.entity.Category;
import com.example.funtime_app.interfaces.CategoryServiceInterface;
import com.example.funtime_app.mappers.CategoryMapper;
import com.example.funtime_app.repository.AttachmentRepository;
import com.example.funtime_app.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService implements CategoryServiceInterface {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final AttachmentRepository attachmentRepository;


    @Override
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        List<CategoryDTO> collect = categories.stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(collect);
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

    @Override
    public ResponseEntity<?> saveCategory(CategorySaveDTO categorySaveDTO) {

        Optional<Attachment> optionalAttachment
                = attachmentRepository.findById(categorySaveDTO.getAttachmentId());

        if (optionalAttachment.isEmpty()){
            return ResponseEntity.status(404).body("File not found!!!");
        }
        Attachment attachment = optionalAttachment.get();

        Category category = Category.builder()
                .attachment(attachment)
                .name(categorySaveDTO.getName())
                .build();

        categoryRepository.save(category);
        return ResponseEntity.ok("Category saved successfully!!!");
    }
}
