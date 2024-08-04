package com.example.funtime_app.services;

import com.example.funtime_app.entity.FormData;
import com.example.funtime_app.repository.FormDataRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FormDataService {


    private final FormDataRepository formDataRepository;

    public FormData saveFormData(FormData formData) {
        formData.setId(UUID.randomUUID());
        return formDataRepository.save(formData);
    }
}
