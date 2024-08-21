package com.example.funtime_app.repository;


import com.example.funtime_app.entity.Follower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface FollowerRepository extends JpaRepository<Follower, UUID> {
    @Query(value = "SELECT COUNT(*) FROM follower WHERE followed_by_id = :userId", nativeQuery = true)
    Integer followersCount(@Param("userId") UUID userId);

    void deleteByFollowerIdAndFollowedById(UUID followerId, UUID userId);
}