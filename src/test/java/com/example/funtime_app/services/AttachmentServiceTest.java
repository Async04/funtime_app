package com.example.funtime_app.services;

import com.example.funtime_app.entity.Attachment;
import com.example.funtime_app.mappers.UserMapper;
import com.example.funtime_app.repository.AttachmentRepository;
import com.example.funtime_app.repository.UserRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;

class AttachmentServiceTest {

    private AttachmentService attachmentService;
    private AttachmentRepository attachmentRepository;


    @BeforeEach
    void before(){

        this.attachmentRepository = Mockito.mock(AttachmentRepository.class);
        this.attachmentService=new AttachmentService(attachmentRepository);
    }

    @SneakyThrows
    @Test
    void saveAttachmentTest() {

        MultipartFile multipartFile = new MockMultipartFile("rasm", new byte[]{1,2,3});
        Attachment attachment = Attachment.builder()
                .content(multipartFile.getBytes())
                .build();

        Mockito.when(attachmentRepository.save(attachment)).thenReturn(
                Attachment.builder()
                        .content(multipartFile.getBytes())
                        .build()
        );
        Attachment saved = attachmentService.saveAttachment(multipartFile);

        Assertions.assertEquals(saved.getContent(), attachment.getContent());

    }

    @Test
    void saveAttachmentNullValue(){

        MultipartFile multipartFile = null;

        Assertions.assertThrows(RuntimeException.class,
                ()->{
            attachmentService.saveAttachment(multipartFile);
                }
                );

    }


}
