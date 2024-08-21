package com.example.funtime_app.dto.request;

import com.example.funtime_app.dto.CategoryDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class VideoSaveDTO {

    private String title;
    private String description;
    private UUID userId;
    private UUID attachmentId;
    private UUID categoryId;
    private List<CategoryRequestTagDTO> tags;

}
