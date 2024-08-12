package com.example.funtime_app.dto;


import com.example.funtime_app.entity.Category;

import java.util.UUID;

public record CategoryTagDTO(UUID id, String tagName, UUID categoryId) {
}