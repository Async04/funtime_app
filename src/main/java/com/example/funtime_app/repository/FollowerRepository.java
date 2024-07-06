package com.example.funtime_app.repository;


import com.example.funtime_app.entity.Follower;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FollowerRepository extends JpaRepository<Follower, UUID> {
}