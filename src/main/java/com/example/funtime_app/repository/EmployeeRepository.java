package com.example.funtime_app.repository;

import com.example.funtime_app.dto.response.EmployeeResponseProjection;
import com.example.funtime_app.entity.Employee;
import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    @Query(nativeQuery = true, value = """

            select
               e.first_name || ' ' || e.last_name as full_name,
               e.job as job,
               a.id as employee_attachment_id
            from employee e
               join attachment a on a.id = e.attachment_id
            """)
    List<EmployeeResponseProjection> getAllEmployees();


    @Query(nativeQuery = true, value = """

            select
               e.first_name || ' ' || e.last_name as full_name,
               e.job as job,
               a.id as employee_attachment_id
            from employee e
               join attachment a on a.id = e.attachment_id
               
               where e.id=:id
            """)
    EmployeeResponseProjection getOneEmployee(@Param("id") UUID id);
}