package com.example.funtime_app.controller;

import com.example.funtime_app.dto.LoginDTO;
import com.example.funtime_app.dto.TokenResponse;
import com.example.funtime_app.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;


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
            return ResponseEntity.status(403).body("bad attampt");
        }

    }


}
