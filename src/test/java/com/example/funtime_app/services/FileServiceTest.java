package com.example.funtime_app.services;

import com.example.funtime_app.entity.Attachment;
import com.example.funtime_app.repository.AttachmentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


class FileServiceTest {

    private AttachmentRepository attachmentRepository;

    @BeforeEach
    void before(){
        this.attachmentRepository= Mockito.mock(AttachmentRepository.class);
    }

    @Test
    void showPhoto() {
        UUID id = UUID.randomUUID();
        Mockito.when(attachmentRepository.findById(id)).thenReturn(
                Optional.of(
                Attachment.builder()
                        .id(id)
                        .build()
                )
        );
        Attachment attachment = Attachment.builder()
                .id(id)
                .build();
        Assertions.assertEquals(attachment.getId(), id);
    }

    @Test
    void showPhotoIdNull(){

        UUID id = UUID.randomUUID();
        Mockito.when(attachmentRepository.findById(id)).thenReturn(Optional.empty());
        Attachment attachment = null;

        Assertions.assertThrows(RuntimeException.class,

                ()->{
            attachment.getContent();
                }

                );
    }

    @Test
    void showVideo() {
        UUID id = UUID.randomUUID();
        Mockito.when(attachmentRepository.findById(id)).thenReturn(
                Optional.of(
                        Attachment.builder()
                                .id(id)
                                .build()
                )
        );
        Attachment attachment = Attachment.builder()
                .id(id)
                .build();
        Assertions.assertEquals(attachment.getId(), id);
    }

    @Test
    void showVideoIdNull(){

        UUID id = UUID.randomUUID();
        Mockito.when(attachmentRepository.findById(id)).thenReturn(Optional.empty());
        Attachment attachment = null;

        Assertions.assertThrows(RuntimeException.class,

                ()->{
                    attachment.getContent();
                }

        );
    }


}