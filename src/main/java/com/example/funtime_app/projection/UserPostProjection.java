package com.example.funtime_app.projection;

import java.util.UUID;

public interface UserPostProjection {

  UUID getPostId();
  UUID getPostAttachmentId();
  UUID getUserId();
  UUID getProfilePhotoId();

  String getTitle();
  String getDescription();
  Integer getViews();
  Double getRateValue();



}
