package com.example.funtime_app.controller;

import com.example.funtime_app.dto.UserDTO;
import com.example.funtime_app.dto.UserEditDTO;
import com.example.funtime_app.dto.request.ChangePhotoDTO;
import com.example.funtime_app.dto.request.ResendCodeDTO;
import com.example.funtime_app.entity.User;
import com.example.funtime_app.interfaces.UserServiceInterface;
import com.example.funtime_app.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@Tag(name = "User API", description = "API for managing user operations.")
public class UserController {

    private final UserServiceInterface userServiceInterface;
    private final UserRepository userRepository;

    @CrossOrigin(origins = "*")
    @Operation(
            summary = "Authorize user",
            description = "Authenticate and authorize a user.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User successfully authorized"),
                    @ApiResponse(responseCode = "400", description = "Invalid user data")
            }
    )
    @PostMapping("/authorize")
    public HttpEntity<?> login(@RequestBody UserDTO userDTO) {
        return userServiceInterface.saveUser(userDTO);
    }

    @Operation(
            summary = "Resend verification code",
            description = "Resend the verification code to the user.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Verification code resent successfully"),
                    @ApiResponse(responseCode = "400", description = "Failed to resend verification code")
            }
    )
    @PostMapping("/resend")
    public ResponseEntity<?> resendCode(@RequestBody ResendCodeDTO resendCodeDTO) {
        return userServiceInterface.resend(resendCodeDTO);
    }

    @Operation(
            summary = "Check OTP",
            description = "Verify the OTP number for a user.",
            parameters = {
                    @Parameter(name = "otpNumber", description = "One-time password for verification", required = true),
                    @Parameter(name = "email", description = "Email of the user", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "OTP verified successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid OTP or email")
            }
    )
    @PostMapping("/otp-check/{otpNumber}")
    public HttpEntity<?> checkOtp(@PathVariable String otpNumber, @RequestParam String email) {
        return userServiceInterface.checkOtp(otpNumber, email);
    }

    @Operation(
            summary = "Get current user profile",
            description = "Retrieve the profile information of the currently authenticated user.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User profile retrieved successfully"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized access")
            }
    )
    @GetMapping("/me")
    public ResponseEntity<?> getMe() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User byUsername = userRepository.findByUsername(username);
            return ResponseEntity.ok(byUsername);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("User not found");
        }
    }

    @Operation(
            summary = "Get user profile by ID",
            description = "Retrieve the profile information of a user by their ID.",
            parameters = {
                    @Parameter(name = "userId", description = "ID of the user", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "User profile retrieved successfully"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    @GetMapping("/profile/{userId}")
    public ResponseEntity<?> getUserProfile(@PathVariable UUID userId) {
        return userServiceInterface.getUserProfile(userId);
    }

    @Operation(
            summary = "Edit user profile",
            description = "Edit the profile information of a user.",
            parameters = {
                    @Parameter(name = "userId", description = "ID of the user to be edited", required = true),
                    @Parameter(name = "userEditDto", description = "User profile data to be updated", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "User profile updated successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid profile data"),
                    @ApiResponse(responseCode = "403", description = "Password does not match"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    @PostMapping("/edit/{userId}")
    public ResponseEntity<?> editProfile(@PathVariable UUID userId, @RequestBody @Valid UserEditDTO userEditDto) throws IOException {
        return userServiceInterface.edit(userId, userEditDto);
    }

    @Operation(
            summary = "Get edit data for user profile",
            description = "Retrieve the data needed to edit a user's profile.",
            parameters = {
                    @Parameter(name = "userId", description = "ID of the user", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Edit data retrieved successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid user ID format"),
                    @ApiResponse(responseCode = "404", description = "User not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping(value = "/edit/{userId}")
    public ResponseEntity<?> getEditData(@PathVariable UUID userId) {
        return userServiceInterface.getEditData(userId);
    }


    @Operation(
            summary = "Change user profile photo",
            description = "Update the profile photo of a user.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Profile photo updated successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid photo data or user/photo not found"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - User is not authenticated"),
                    @ApiResponse(responseCode = "404", description = "User or photo not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @PostMapping("/change/photo")
    public ResponseEntity<?> setProfilePhoto(@RequestBody ChangePhotoDTO photoDTO) {
        return userServiceInterface.changePhoto(photoDTO);
    }


    @Operation(
            summary = "Get user ID",
            description = "Retrieve the ID of the currently authenticated user.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User ID retrieved successfully"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - User is not authenticated"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping("/getId")
    public ResponseEntity<?> getUserId() {
        return userServiceInterface.getId();
    }

}
