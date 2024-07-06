package com.example.funtime_app.repository;


import com.example.funtime_app.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}