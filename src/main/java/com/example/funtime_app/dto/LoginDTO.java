package com.example.funtime_app.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.example.funtime_app.entity.User}
 */
@Value
public class LoginDTO implements Serializable {
    String username;
    String password;
}