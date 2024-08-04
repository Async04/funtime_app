package com.example.funtime_app.controller;

import com.example.funtime_app.entity.FormData;
import com.example.funtime_app.services.FormDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FormController {

    private static final String UPLOADED_FOLDER = "uploads/";

    private final FormDataService formDataService;

    @PostMapping("/submit-form")
    public ResponseEntity<?> handleFormSubmission(
            @RequestParam("subject") String subject,
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("explanation") String explanation,
            @RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }

        try {

            Path uploadPath = Paths.get(UPLOADED_FOLDER);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }


            byte[] bytes = file.getBytes();
            Path path = uploadPath.resolve(file.getOriginalFilename());
            Files.write(path, bytes);


            FormData formData = new FormData();
            formData.setSubject(subject);
            formData.setName(name);
            formData.setEmail(email);
            formData.setExplanation(explanation);
            formData.setFilePath(path.toString());

            FormData savedFormData = formDataService.saveFormData(formData);

            return ResponseEntity.ok(savedFormData);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error occurred while processing the file");
        }
    }
}
