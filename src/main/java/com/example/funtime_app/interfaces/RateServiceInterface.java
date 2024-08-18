package com.example.funtime_app.interfaces;

import com.example.funtime_app.dto.request.PostRateDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface RateServiceInterface {

    ResponseEntity<?> ratePost(PostRateDTO rateDTO);
}
