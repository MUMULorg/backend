package com.example.ama_backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // addCorsMappings : CORS 설정을 추가하는 메소드이다.
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedOrigins("https://54.180.80.63:3000/")
                .allowedOrigins("https://54.180.80.63")
                .allowedOrigins("http://54.180.80.63:3000/")
                .allowedOrigins("http://54.180.80.63")
                .allowedOrigins("http://13.124.15.169/")
                .allowedOrigins("https://mumul.site")
                .allowedOrigins("http://mumul.site")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(false)
                .exposedHeaders("Authorization")         // <----- 추가함 2023.06.06
                .exposedHeaders("Access-Control-Allow-Origin"); // "Access-Control-Allow-Origin" 헤더를 노출합니다.
    }

}
