package com.example.funtime_app.controller;

import com.example.funtime_app.services.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/file")
@Tag(name = "File API", description = "API for managing file uploads and retrievals.")
public class FileController {

    private final FileService fileService;

    @Operation(
            summary = "Get photo by  attachmentId",
            description = "Retrieve a photo by its ID and return it in the response.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Photo successfully retrieved"),
                    @ApiResponse(responseCode = "404", description = "Photo not found")
            }
    )
    @GetMapping("/photo/{id}")
    public void getPhoto(@PathVariable UUID id, HttpServletResponse response) {
        fileService.showPhoto(response, id);
    }

    @Operation(
            summary = "Get video by attachmentId",
            description = "Retrieve a video by its ID and return it in the response.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Video successfully retrieved"),
                    @ApiResponse(responseCode = "404", description = "Video not found")
            }
    )
    @GetMapping("/video/{id}")
    public void getVideo(@PathVariable UUID id, HttpServletResponse response) {
        fileService.showVideo(response, id);
    }

    @Operation(
            summary = "Upload a file",
            description = "Upload a file (photo or video) to the server.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "File successfully uploaded"),
                    @ApiResponse(responseCode = "400", description = "Invalid file or upload request")
            }
    )
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        return fileService.saveFile(file);
    }
}
