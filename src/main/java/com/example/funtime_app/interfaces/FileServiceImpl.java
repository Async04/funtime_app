package com.example.funtime_app.interfaces;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface FileServiceImpl {

    void showPhoto(HttpServletResponse response, UUID attachmentId);
    void showVideo(HttpServletResponse response, UUID attachmentId);

}
