package com.example.funtime_app.controller;

import com.example.funtime_app.dto.request.PostRateDTO;
import com.example.funtime_app.interfaces.RateServiceInterface;
import com.example.funtime_app.services.RateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rate")
public class RateController {

    private final RateServiceInterface rateServiceInterface;

    @PostMapping
    public ResponseEntity<?> ratePost(@RequestBody PostRateDTO rateDTO){
       return rateServiceInterface.ratePost(rateDTO);
    }

}
