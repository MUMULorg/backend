package com.example.ama_backend;

import com.google.api.client.util.Value;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.CrossOrigin;

@EnableAsync
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class AmaBackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(AmaBackendApplication.class, args);
	}

}
