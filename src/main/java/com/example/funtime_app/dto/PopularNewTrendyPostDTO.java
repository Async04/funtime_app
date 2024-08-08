package com.example.funtime_app.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PopularNewTrendyPostDTO {

    private UUID post_id;
    private UUID post_attachment_id;
    private UUID user_id;
    private UUID profile_photo_id;

    private String title;
    private String description;


}
