package com.example.funtime_app.repository;

import com.example.funtime_app.entity.Code;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CodeRepository extends JpaRepository<Code, Integer> {
   Optional<Code> findByUserId(UUID id);
}