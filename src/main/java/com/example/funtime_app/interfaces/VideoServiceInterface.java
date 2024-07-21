package com.example.funtime_app.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface VideoServiceInterface {

    ResponseEntity<?> getLatestVideo();

}
