package com.example.funtime_app.controller;

import com.example.funtime_app.dto.request.PostRateDTO;
import com.example.funtime_app.interfaces.RateServiceInterface;
import com.example.funtime_app.services.RateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rate")
public class RateController {

    private final RateServiceInterface rateServiceInterface;

    @PostMapping
    public ResponseEntity<?> ratePost(@RequestBody PostRateDTO rateDTO){
       return rateServiceInterface.ratePost(rateDTO);
    }

    @GetMapping("/post")
    public ResponseEntity<?> getRateValu(@RequestParam UUID postId, @RequestParam UUID userId){
        return rateServiceInterface.getOnePostRate(postId, userId);

    }
}
