package com.example.funtime_app.dto;

import java.io.Serializable;

/**
 * DTO for {@link com.example.funtime_app.entity.User}
 */
public record UserDTO(String firstName, String lastName, String username, String email) implements Serializable {
}