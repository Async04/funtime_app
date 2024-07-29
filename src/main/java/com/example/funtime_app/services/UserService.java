package com.example.funtime_app.services;

import com.example.funtime_app.dto.UserDTO;
import com.example.funtime_app.entity.User;
import com.example.funtime_app.interfaces.UserServiceInterface;
import com.example.funtime_app.mappers.UserMapper;
import com.example.funtime_app.projection.UserProfileProjection;
import com.example.funtime_app.repository.UserRepository;
import com.github.fashionbrot.annotation.Valid;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceInterface {


    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final OTPService otpService;

    private final Map<String, String> otpStorage = new HashMap<>();


    @Override
    @Transactional
    public HttpEntity<?> saveUser(@Valid UserDTO userDTO) {
        try {
            User user = userMapper.toEntity(userDTO);
            User savedUser = userRepository.save(user);
            String otp = otpService.generateOTP();
            otpStorage.put(user.getEmail(), otp);
            emailService.sendEmail(user.getEmail(), "Your OTP Code", "Your OTP code is: " + otp);
            return ResponseEntity.ok(savedUser);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while saving the user.");
        }
    }

    @Override
    public ResponseEntity<?> getUserProfile(UUID userId) {
        UserProfileProjection userProfile = userRepository.getUserProfile(userId);
        return ResponseEntity.ok(userProfile);
    }

    @Transactional
    public HttpEntity<?> checkOtp(String otpNumber, String email) {
        String storedOtp = otpStorage.get(email);

        if (storedOtp == null) {
            return ResponseEntity.badRequest().body("No OTP found for this email");
        }

        if (!storedOtp.equals(otpNumber)) {
            return ResponseEntity.badRequest().body("Invalid OTP");
        }

        otpStorage.remove(email);
        return ResponseEntity.ok("OTP verified successfully");
    }
}
