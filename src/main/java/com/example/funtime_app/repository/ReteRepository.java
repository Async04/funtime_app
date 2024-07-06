package com.example.funtime_app.repository;


import com.example.funtime_app.entity.Rete;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReteRepository extends JpaRepository<Rete, UUID> {
}