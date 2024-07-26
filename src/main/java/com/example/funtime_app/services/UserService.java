package com.example.funtime_app.services;

import com.example.funtime_app.dto.UserDTO;
import com.example.funtime_app.entity.User;
import com.example.funtime_app.interfaces.UserServiceInterface;
import com.example.funtime_app.mappers.UserMapper;
import com.example.funtime_app.projection.UserProfileProjection;
import com.example.funtime_app.repository.UserRepository;
import com.github.fashionbrot.annotation.Valid;
import com.github.fashionbrot.annotation.Validated;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceInterface {


    private final UserMapper userMapper;
    private final UserRepository userRepository;




    @Override
    @Transactional
    public HttpEntity<?> saveUser(@Valid UserDTO userDTO) {
        try {
            User user = userMapper.toEntity(userDTO);
            User savedUser = userRepository.save(user);
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
}
