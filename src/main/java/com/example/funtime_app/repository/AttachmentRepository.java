package com.example.funtime_app.repository;


import com.example.funtime_app.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AttachmentRepository extends JpaRepository<Attachment, UUID> {
}