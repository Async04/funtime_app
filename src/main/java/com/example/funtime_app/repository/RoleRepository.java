package com.example.funtime_app.repository;


import com.example.funtime_app.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query(value = """
                    select r.* from users_roles ur
                join roles r on ur.role_id=r.id where ur.user_id=:userId 
                    """, nativeQuery = true)
    List<Role> getRoles(UUID userId);
}