package com.example.funtime_app.services;

import com.example.funtime_app.entity.Attachment;
import com.example.funtime_app.interfaces.FileServiceImpl;
import com.example.funtime_app.repository.AttachmentRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @Override
    public UUID savePhoto(MultipartFile file) {
        Attachment attachment = new Attachment();
        byte[] photo;
        try {
            photo = file.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        attachment.setContent(photo);
        attachment.setContentType(file.getContentType());
        Attachment save = attachmentRepository.save(attachment);
        return save.getId();
    }
}
