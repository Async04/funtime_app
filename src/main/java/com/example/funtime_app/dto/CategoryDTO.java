package com.example.funtime_app.dto;

import com.example.funtime_app.entity.Attachment;
import com.example.funtime_app.entity.CategoryTag;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * DTO for {@link com.example.funtime_app.entity.Category}
 */
public record CategoryDTO(MultipartFile photo, UUID categoryId, List<CategoryTag> tags) implements Serializable {
}
