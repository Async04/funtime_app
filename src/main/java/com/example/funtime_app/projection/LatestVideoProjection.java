package com.example.funtime_app.projection;

import java.util.UUID;

public interface LatestVideoProjection {

  UUID getVideoId();
  UUID getVideoAttachmentId();

  String getTitle();
  String getDescription();



}
