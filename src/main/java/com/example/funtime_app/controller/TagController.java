package com.example.funtime_app.controller;

import com.example.funtime_app.repository.CategoryTagRepository;
import com.example.funtime_app.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/tag")
public class TagController {

    private final CategoryTagRepository categoryTagRepository;
    private final TagService tagService;

    @GetMapping("/{categoryId}")
    public ResponseEntity<?> getTagsByCategoryId(@PathVariable UUID categoryId){
        return tagService.getTagsByCategoryId(categoryId);
    }

}
