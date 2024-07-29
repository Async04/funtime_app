package com.example.funtime_app.interfaces;

import com.example.funtime_app.dto.UserDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface UserServiceInterface {

    HttpEntity<?> saveUser(UserDTO userDTO);
    ResponseEntity<?> getUserProfile(UUID userId);

    HttpEntity<?> checkOtp(String otpNumber, String email);
}
