package com.example.funtime_app.interfaces;

import com.example.funtime_app.dto.UserDTO;
import com.example.funtime_app.dto.UserEditDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.UUID;

public interface UserServiceInterface {

    HttpEntity<?> saveUser(UserDTO userDTO);
    ResponseEntity<?> getUserProfile(UUID userId);

    HttpEntity<?> checkOtp(String otpNumber, String email);

    ResponseEntity<?> edit(UUID userId, UserEditDTO userEditDto) throws IOException;

    ResponseEntity<?> getEditData(UUID userId);

}
