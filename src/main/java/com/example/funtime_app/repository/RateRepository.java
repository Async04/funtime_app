package com.example.funtime_app.repository;

import com.example.funtime_app.entity.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface RateRepository extends JpaRepository<Rate, UUID> {
     Optional<Rate> findByRatedByIdAndPostId(UUID rateUserId, UUID postId);
}