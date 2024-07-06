package com.example.funtime_app.repository;


import com.example.funtime_app.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BannerRepository extends JpaRepository<Banner, UUID> {
}