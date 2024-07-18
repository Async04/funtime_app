package com.example.funtime_app.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.example.funtime_app.entity.User}
 */
@Value
public class LoginDto implements Serializable {
    String username;
    String password;
}