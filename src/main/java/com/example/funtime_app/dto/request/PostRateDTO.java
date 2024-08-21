package com.example.funtime_app.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PostRateDTO {

    private UUID postId;
    private UUID rateUserId;
    private Integer value;

}
