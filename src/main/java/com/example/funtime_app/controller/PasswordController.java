package com.example.funtime_app.controller;

import com.example.funtime_app.services.PasswordGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/password")
@RequiredArgsConstructor
public class PasswordController {

    private final PasswordGenerator passwordGenerator;

    @GetMapping("/random")
    public ResponseEntity<?> generatePassword(){
        String randomPassword = passwordGenerator.generateRandomPassword();
        return ResponseEntity.ok(randomPassword);
    }

}
