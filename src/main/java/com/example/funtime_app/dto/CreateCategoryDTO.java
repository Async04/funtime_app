package com.example.funtime_app.dto;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * DTO for {@link com.example.funtime_app.entity.Category}
 */
public record CreateCategoryDTO(UUID attachmentId, String name, List<UUID> tagIds) implements Serializable {

}

