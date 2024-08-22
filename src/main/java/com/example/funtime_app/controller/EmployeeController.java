package com.example.funtime_app.controller;

import com.example.funtime_app.dto.request.EmployeeDTO;
import com.example.funtime_app.services.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/employees")
@Tag(name = "Employee API", description = "API for managing employee data, including retrieval, creation, and deletion.")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Operation(
            summary = "Get all employees",
            description = "Retrieve a list of all employees.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Employees successfully retrieved"),
                    @ApiResponse(responseCode = "500", description = "Internal server error"),
                    @ApiResponse(responseCode = "403", description = "Forbidden or bad attampt"),
                    @ApiResponse(responseCode = "404", description = "Not found!!!")
            }
    )
    @GetMapping
    public ResponseEntity<?> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @Operation(
            summary = "Get employee by ID",
            description = "Retrieve a specific employee by their ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Employee successfully retrieved"),
                    @ApiResponse(responseCode = "404", description = "Employee not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error"),
                    @ApiResponse(responseCode = "403", description = "Forbidden or bad attampt")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable UUID id) {
        return employeeService.getEmployeeById(id);
    }

    @Operation(
            summary = "Create a new employee",
            description = "Create a new employee with the provided details.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Employee successfully created"),
                    @ApiResponse(responseCode = "400", description = "Invalid employee data"),
                    @ApiResponse(responseCode = "500", description = "Internal server error"),
                    @ApiResponse(responseCode = "403", description = "Forbidden or bad attampt")
            }
    )

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return employeeService.saveEmployee(employeeDTO);
    }

    @Operation(
            summary = "Delete employee",
            description = "Delete an existing employee by their ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Employee successfully deleted"),
                    @ApiResponse(responseCode = "500", description = "Internal server error"),
                    @ApiResponse(responseCode = "403", description = "Forbidden or bad attampt")
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable UUID id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok().build();
    }
}
