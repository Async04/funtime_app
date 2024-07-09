package com.example.funtime_app.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ViewRepository extends JpaRepository<View, UUID> {
}