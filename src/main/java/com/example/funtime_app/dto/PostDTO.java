package com.example.funtime_app.dto;

import com.example.funtime_app.entity.CategoryTag;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public record PostDTO(String title, String description, UUID userId, UUID categoryId, List<CategoryTagDTO> tags, MultipartFile file) implements Serializable {
}
