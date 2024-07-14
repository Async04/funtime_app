package com.example.funtime_app.projection;

import java.util.UUID;

public interface PopularNewTrendyPostProjection {

  UUID getPostId();
  UUID getPostAttachmentId();
  UUID getUserId();
  UUID getProfilePhotoId();

  String getTitle();
  String getDescription();



}
