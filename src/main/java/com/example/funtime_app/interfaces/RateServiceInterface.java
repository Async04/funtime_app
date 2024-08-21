package com.example.funtime_app.interfaces;

import com.example.funtime_app.dto.request.PostRateDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface RateServiceInterface {

    ResponseEntity<?> ratePost(PostRateDTO rateDTO);

    ResponseEntity<?> getOnePostRate(UUID postId, UUID userId);
}
