package com.example.funtime_app.repository;


import com.example.funtime_app.entity.User;
import com.example.funtime_app.projection.UserEditProjection;
import com.example.funtime_app.projection.UserProfileProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByUsername(String username);
    List<User> findByEmail(String email);

    @Query(nativeQuery = true, value = """
            
            SELECT
                u.id AS userId,
                u.profile_photo_id AS profilePhotoId,
                b.id AS bannerId,
                CONCAT(u.first_name, ' ', u.last_name) AS fullName,
                ROUND(COALESCE(AVG(r.mark_value), 0), 1) AS rate,
                (SELECT COUNT(*) FROM follower f WHERE f.followed_by_id = u.id) AS followers,
                (SELECT COUNT(*) FROM posts p WHERE p.user_id = u.id) AS postCount
            FROM
                users u
                    LEFT JOIN
                posts p ON u.id = p.user_id
                    LEFT JOIN
                rate r ON p.id = r.post_id
                    LEFT JOIN
                banner b ON u.id = b.user_id
            WHERE
                    u.id = :userId
            GROUP BY
                u.id, u.profile_photo_id, b.id, u.first_name, u.last_name
            
            """)
    UserProfileProjection getUserProfile(@Param(value = "userId") UUID userId);


    @Query(nativeQuery = true, value = """
            select
                u.username,
                u.first_name,
                u.last_name,
                u.email
                from users u where u.id =:userId
            """)
    List<UserEditProjection> getUserEditData(@Param(value = "userId") UUID userId);
}