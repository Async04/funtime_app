package com.example.funtime_app.services;

import com.example.funtime_app.entity.Attachment;
import com.example.funtime_app.repository.AttachmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AttachmentService {
    private final AttachmentRepository attachmentRepository;
    @SneakyThrows
    public Attachment saveAttachment(MultipartFile file) {

        if (file==null){
            throw new RuntimeException("File can not be null");
        }

        Attachment attachment=Attachment.builder()
                .content(file.getBytes())
                .contentType(file.getContentType())
                .build();
        return attachmentRepository.save(attachment);
    }

}
