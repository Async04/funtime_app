package com.example.funtime_app.controller;

import com.example.funtime_app.dto.UserDTO;
import com.example.funtime_app.entity.User;
import com.example.funtime_app.interfaces.UserServiceInterface;
import com.example.funtime_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceInterface userServiceInterface;
    private final UserRepository userRepository;
    @CrossOrigin
    @PostMapping("/authorize")
    public HttpEntity<?> login(@RequestBody UserDTO userDTO){
        return userServiceInterface.saveUser(userDTO);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMe(){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User byUsername = userRepository.findByUsername(username);
            return ResponseEntity.ok(byUsername);
        }

        catch (Exception e){
           return ResponseEntity.status(403).body("User not found");
        }
    }



}
