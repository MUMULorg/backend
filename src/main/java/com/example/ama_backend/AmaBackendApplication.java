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

@EnableAsync
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class AmaBackendApplication {
//	private static final Logger logger = LoggerFactory.getLogger(AmaBackendApplication.class);
//	@Value("${rds.hostname}")
//	private static String rdsHostname;
//
//	@Value("${rds.port}")
//	private static String rdsPort;
	public static void main(String[] args) {
//		logger.info("rds.hostname: " + rdsHostname);
//		logger.info("rds.port: " + rdsPort);
		SpringApplication.run(AmaBackendApplication.class, args);
	}

}
