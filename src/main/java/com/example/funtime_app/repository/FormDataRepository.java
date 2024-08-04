package com.example.funtime_app.repository;

import com.example.funtime_app.entity.FormData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FormDataRepository extends JpaRepository<FormData, UUID> {
}