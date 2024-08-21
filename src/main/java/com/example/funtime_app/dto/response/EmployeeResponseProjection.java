package com.example.funtime_app.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


public interface EmployeeResponseProjection {

   String getFullName();
   String getJob();
   UUID getEmployeeAttachmentId();

}
