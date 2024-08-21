package com.example.funtime_app.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;


public interface CommentResponseProjection {

     UUID getId();
     UUID getUserAttachmentId();
     String getFullName();
     LocalDateTime getCreatedAt();
     String getBody();

     Integer getReplyCount();




}
