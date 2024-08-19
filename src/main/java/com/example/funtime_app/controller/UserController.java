package com.example.funtime_app.controller;

import com.example.funtime_app.dto.UserDTO;
import com.example.funtime_app.dto.UserEditDTO;
import com.example.funtime_app.dto.request.ChangePhotoDTO;
import com.example.funtime_app.dto.request.ResendCodeDTO;
import com.example.funtime_app.entity.User;
import com.example.funtime_app.interfaces.UserServiceInterface;
import com.example.funtime_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceInterface userServiceInterface;
    private final UserRepository userRepository;

    @CrossOrigin(origins = "*")
    @PostMapping("/authorize")
    public HttpEntity<?> login(@RequestBody UserDTO userDTO) {
        return userServiceInterface.saveUser(userDTO);
    }

    @PostMapping("/resend")
    public ResponseEntity<?> resendCode(@RequestBody ResendCodeDTO resendCodeDTO){
        return userServiceInterface.resend(resendCodeDTO);
    }

    @PostMapping("/otp-check/{otpNumber}")
    public HttpEntity<?> checkOtp(@PathVariable String otpNumber, @RequestParam String email) {
        return userServiceInterface.checkOtp(otpNumber, email);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMe() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User byUsername = userRepository.findByUsername(username);
            System.out.println(byUsername);
            return ResponseEntity.ok(byUsername);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("User not found");
        }
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<?> getUserProfile(@PathVariable UUID userId) {
        return userServiceInterface.getUserProfile(userId);
    }

    @PostMapping("/edit/{userId}")
    public void editProfile(@PathVariable UUID userId, @RequestBody UserEditDTO userEditDto) throws IOException {
        System.out.println(userEditDto);
        userServiceInterface.edit(userId, userEditDto);
    }

    @GetMapping(value = "/edit/{userId}")
    public ResponseEntity<?> getEditData(@PathVariable UUID userId) {
        return userServiceInterface.getEditData(userId);
    }

    @PostMapping("/change/photo")
    public ResponseEntity<?> setProfilePhoto(@RequestBody ChangePhotoDTO photoDTO){
       return userServiceInterface.changePhoto(photoDTO);
    }

    @GetMapping("/getId")
    public ResponseEntity<?> getUserId(){
        System.out.println("HHHHHHHHHHHHHHHHHHHH");
        return userServiceInterface.getId();
    }

}
