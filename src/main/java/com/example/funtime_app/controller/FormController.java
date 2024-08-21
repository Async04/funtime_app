package com.example.funtime_app.controller;

import com.example.funtime_app.entity.Attachment;
import com.example.funtime_app.entity.FormData;
import com.example.funtime_app.repository.AttachmentRepository;
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
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FormController {

    private static final String UPLOADED_FOLDER = "uploads/";

    private final FormDataService formDataService;
    private final AttachmentRepository attachmentRepository;

    @PostMapping("/submit-form")
    public ResponseEntity<?> handleFormSubmission(
            @RequestParam("subject") String subject,
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("explanation") String explanation,
            @RequestParam("file") UUID attachmentId) {


        Optional<Attachment> attachmentOptional = attachmentRepository.findById(attachmentId);
        Attachment attachment = null;
        if (attachmentOptional.isPresent()){
            attachment=attachmentOptional.get();
        }

        FormData formData = FormData.builder()
                .explanation(explanation)
                .subject(subject)
                .email(email)
                .name(name)
                .attachment(attachment)
                .build();

        formDataService.saveFormData(formData);

        return ResponseEntity.ok("Thank you contact to us!!!");
    }
}
