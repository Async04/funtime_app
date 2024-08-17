package com.example.funtime_app.dto;

import com.example.funtime_app.dto.request.CategoryRequestTagDTO;
import com.example.funtime_app.dto.response.CategoryResponseTagDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PostDTO implements Serializable {
    private String title;
    private String description;
    private UUID userId;
    private UUID categoryId;
    private List<CategoryRequestTagDTO> tags;
    private UUID attachmentId;
}
