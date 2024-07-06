package com.example.funtime_app.repository;


import com.example.funtime_app.entity.View;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ViewRepository extends JpaRepository<View, UUID> {
}