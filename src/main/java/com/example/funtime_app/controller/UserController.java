package com.example.funtime_app.controller;

import com.example.funtime_app.dto.UserDTO;
import com.example.funtime_app.interfaces.UserServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceInterface userServiceInterface;
    @CrossOrigin
    @PostMapping("/authorize")
    public HttpEntity<?> login(@RequestBody UserDTO userDTO){
        return userServiceInterface.saveUser(userDTO);
    }



}
