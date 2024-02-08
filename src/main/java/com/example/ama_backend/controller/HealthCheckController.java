package com.example.ama_backend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "https://mumul.site", maxAge=3600)
public class HealthCheckController {

    @GetMapping("/health")
    public String healthCheck() {
        return "The service is up and running...";
    }
}
