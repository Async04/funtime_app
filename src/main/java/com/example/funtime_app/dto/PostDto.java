package com.example.funtime_app.dto;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.Serializable;
import java.util.UUID;

public record PostDto(String title, String description, UUID userId, UUID categoryId, MultipartFile file) implements Serializable {
}
