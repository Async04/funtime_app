package com.example.funtime_app.controller;

import com.example.funtime_app.services.PasswordGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/password")
@RequiredArgsConstructor
@Tag(name = "Password API", description = "API for generating random passwords.")
public class PasswordController {

    private final PasswordGenerator passwordGenerator;

    @Operation(
            summary = "Generate a random password",
            description = "Retrieve a newly generated random password.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Password successfully generated"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping("/random")
    public ResponseEntity<?> generatePassword() {
        String randomPassword = passwordGenerator.generateRandomPassword();
        return ResponseEntity.ok(randomPassword);
    }
}
