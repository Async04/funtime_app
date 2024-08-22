package com.example.funtime_app.controller;

import com.example.funtime_app.entity.Attachment;
import com.example.funtime_app.entity.FormData;
import com.example.funtime_app.repository.AttachmentRepository;
import com.example.funtime_app.services.FormDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Form API", description = "API for handling form submissions including file attachments.")
public class FormController {

    private static final String UPLOADED_FOLDER = "uploads/";

    private final FormDataService formDataService;
    private final AttachmentRepository attachmentRepository;

    @Operation(
            summary = "Handle form submission",
            description = "Submit a form with subject, name, email, explanation, and an optional attachment.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Form successfully submitted"),
                    @ApiResponse(responseCode = "400", description = "Invalid form data or attachment ID")
            }
    )
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
            attachment = attachmentOptional.get();
        }

        FormData formData = FormData.builder()
                .explanation(explanation)
                .subject(subject)
                .email(email)
                .name(name)
                .attachment(attachment)
                .build();

        formDataService.saveFormData(formData);

        return ResponseEntity.ok("Thank you for contacting us!");
    }
}
