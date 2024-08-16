package com.example.funtime_app.dto;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link com.example.funtime_app.entity.User}
 */
public record UserDTO(String firstName, String lastName, String username, String email, String password, MultipartFile profilePhoto) implements Serializable {
}