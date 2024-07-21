package com.example.funtime_app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LatestVideoDto {

    private UUID videoId;
    private UUID videoAttachmentId;

    private String title;
    private String description;

}

