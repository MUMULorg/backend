package com.example.ama_backend.controller;

import com.google.api.client.util.Value;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/rds")
public class RDSController {
    @Value("${rds.hostname}")
    private String rdsHostname;

    @Value("${rds.port}")
    private String rdsPort;

    @PostConstruct
    public void postConstruct() {
//        System.out.println("rds.hostname: " + rdsHostname);
//        System.out.println("rds.port: " + rdsPort);
    }
}
