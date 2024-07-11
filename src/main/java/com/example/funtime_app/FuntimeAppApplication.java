package com.example.funtime_app;

import jakarta.servlet.annotation.MultipartConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MultipartConfig
@SpringBootApplication
public class FuntimeAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(FuntimeAppApplication.class, args);
    }

}
