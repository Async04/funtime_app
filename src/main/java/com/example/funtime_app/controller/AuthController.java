package com.example.funtime_app.controller;

import com.example.funtime_app.dto.LoginDTO;
import com.example.funtime_app.dto.TokenResponse;
import com.example.funtime_app.security.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth API", description = "Authentication API for user login and token management.")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Operation(
            summary = "User Login",
            description = "Authenticate user with username and password and return access and refresh tokens.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful login with tokens returned"),
                    @ApiResponse(responseCode = "403", description = "Authentication failed due to invalid credentials")
            }
    )
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDto){

        try {
            var auth = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
            authenticationManager.authenticate(auth);
            String accessToken = "Bearer "+ jwtUtil.generateToken(loginDto.getUsername());
            String refreshToken = "Bearer "+ jwtUtil.generateRefreshToken(loginDto.getUsername());
            return ResponseEntity.ok(
                    new TokenResponse(accessToken, refreshToken)
            );
        }
        catch (Exception e){
            return ResponseEntity.status(403).body("bad attempt");
        }

    }
}
