package com.example.funtime_app.services;

import com.example.funtime_app.dto.request.EmployeeDTO;
import com.example.funtime_app.dto.response.EmployeeResponseProjection;
import com.example.funtime_app.entity.Attachment;
import com.example.funtime_app.entity.Employee;
import com.example.funtime_app.repository.AttachmentRepository;
import com.example.funtime_app.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final AttachmentRepository attachmentRepository;

    public ResponseEntity<?> getAllEmployees() {
        List<EmployeeResponseProjection> allEmployees = employeeRepository.getAllEmployees();
        return ResponseEntity.ok(allEmployees);
    }

    public ResponseEntity<?> getEmployeeById(UUID id) {
        EmployeeResponseProjection employee =
                employeeRepository.getOneEmployee(id);
        if (employee!=null){
            return ResponseEntity.ok(employee);
        }

        return ResponseEntity.badRequest().body("Employee not found");
    }

    @Transactional
    public ResponseEntity<?> saveEmployee(EmployeeDTO employeeDTO) {

        UUID attachmentId = employeeDTO.getAttachmentId();
        if (attachmentId ==null) {
            return ResponseEntity.badRequest().body("File not found!!!");
        }

        Optional<Attachment> attachmentOptional = attachmentRepository.findById(attachmentId);
        if (attachmentOptional.isEmpty()){
            return ResponseEntity.badRequest().body("File not found!!!");
        }

        Attachment attachment = attachmentOptional.get();
        Employee employee = Employee.builder()
                .job(employeeDTO.getJob())
                .firstName(employeeDTO.getFirstName())
                .lastName(employeeDTO.getLastName())
                .attachment(attachment)
                .build();

        Employee save = employeeRepository.save(employee);
        if (save!=null){
            return ResponseEntity.ok("Employee saved successfyully");
        }
        return ResponseEntity.badRequest().body("Not saved employee!!!");
    }

    @Transactional
    public void deleteEmployee(UUID id) {
        employeeRepository.deleteById(id);
    }
}
