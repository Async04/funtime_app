package com.example.funtime_app.services;

import com.example.funtime_app.dto.UserDTO;
import com.example.funtime_app.dto.UserEditDTO;
import com.example.funtime_app.entity.Attachment;
import com.example.funtime_app.entity.Banner;
import com.example.funtime_app.entity.User;
import com.example.funtime_app.interfaces.UserServiceInterface;
import com.example.funtime_app.mappers.UserMapper;
import com.example.funtime_app.projection.UserEditProjection;
import com.example.funtime_app.projection.UserProfileProjection;
import com.example.funtime_app.repository.AttachmentRepository;
import com.example.funtime_app.repository.BannerRepository;
import com.example.funtime_app.repository.UserRepository;
import com.github.fashionbrot.annotation.Valid;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceInterface {


    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final OTPService otpService;
    private final PasswordEncoder passwordEncoder;
    private final AttachmentRepository attachmentRepository;

    private final Map<String, String> otpStorage = new HashMap<>();
    private final BannerRepository bannerRepository;


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

    @Override
    public ResponseEntity<?> edit(UUID userId, UserEditDTO userEditDto) throws IOException {

        Optional<User> byId = userRepository.findById(userId);
        System.out.println(userEditDto);
        if (byId.isPresent()){
            User user = byId.get();
            if (passwordEncoder.matches(userEditDto.getOldPassword(), user.getPassword())){
                user.setEmail(userEditDto.getEmail());
                user.setUsername(userEditDto.getUsername());
                user.setPassword(passwordEncoder.encode(userEditDto.getNewPassword()));
                user.setLastName(userEditDto.getLastName());
                user.setFirstName(userEditDto.getFirstName());
                if (userEditDto.getProfileBanner()!=null){
                    bannerRepository.deleteByUserId(userId);
                    Attachment attachment = Attachment.builder()
                            .content(userEditDto.getProfileBanner().getBytes())
                            .contentType("png")
                            .build();
                    attachmentRepository.save(attachment);
                    Banner banner = Banner.builder()
                            .banner(attachment)
                            .user(user)
                            .build();
                    bannerRepository.save(banner);
                }

                if (userEditDto.getProfilePhoto()!=null){
                    Attachment attachment = Attachment.builder()
                            .contentType("png")
                            .content(userEditDto.getProfilePhoto().getBytes())
                            .build();

                    attachmentRepository.save(attachment);
                    user.setProfilePhoto(attachment);
                }
                userRepository.save(user);
                return ResponseEntity.status(202).body("User successfully edited !!!");

            }
            else {
                return ResponseEntity.status(201).body("Password does not match");
            }
        }
        return ResponseEntity.status(201).body("User not found!!!");
    }

    @Override
    public ResponseEntity<?> getEditData(UUID userId) {

        try {
            List<UserEditProjection> userEditData = userRepository.getUserEditData(userId);
            return ResponseEntity.ok(userEditData);
        }
        catch (Exception e){
            return ResponseEntity.status(401).body("Not found user!!!");
        }
    }
}
