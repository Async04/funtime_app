package com.example.funtime_app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OnePostDTO {

    private UUID id;
    private String title;
    private String description;
    private UUID attachmentId;
    private Integer rateValue;

}
