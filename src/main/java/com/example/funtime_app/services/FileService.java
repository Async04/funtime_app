package com.example.funtime_app.services;

import com.example.funtime_app.entity.Attachment;
import com.example.funtime_app.interfaces.FileServiceImpl;
import com.example.funtime_app.repository.AttachmentRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.beans.Transient;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService implements FileServiceImpl {

    private final AttachmentRepository attachmentRepository;

    @SneakyThrows
    @Override
    public void showPhoto(HttpServletResponse response, UUID attachmentId) {

        Attachment attachment = attachmentRepository.findById(attachmentId).orElseThrow(RuntimeException::new);
        byte[] content = attachment.getContent();
        response.setContentType("image/jpeg");
        response.getOutputStream().write(content);

    }

    @SneakyThrows
    @Override
    public void showVideo(HttpServletResponse response, UUID attachmentId) {

        Attachment attachment = attachmentRepository.findById(attachmentId).orElseThrow(RuntimeException::new);
        byte[] content = attachment.getContent();
        response.setContentType("video/mp4");
        response.getOutputStream().write(content);

    }

    @SneakyThrows
    @Transactional
    public ResponseEntity<?> saveFile(MultipartFile file) {
        if (file!=null){
            Attachment attachment = Attachment.builder()
                    .contentType(file.getContentType())
                    .content(file.getBytes())
                    .build();
            Attachment save = attachmentRepository.save(attachment);
            return ResponseEntity.ok(save.getId());
        }
        else {
            return ResponseEntity.badRequest().body("File cannot be null");
        }
    }
}
