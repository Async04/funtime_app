package com.example.funtime_app.interfaces;

import org.springframework.http.HttpEntity;

import java.util.List;

public interface CategoryServiceInterface {
    List<HttpEntity<?>> getAllCategories();


    HttpEntity<?> getAllTagsByCategory(String id);
}
