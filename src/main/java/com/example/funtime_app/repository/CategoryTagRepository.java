package com.example.funtime_app.repository;

import com.example.funtime_app.entity.CategoryTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryTagRepository extends JpaRepository<CategoryTag, UUID> {
}