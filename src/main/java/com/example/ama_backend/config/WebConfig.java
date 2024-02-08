package com.example.ama_backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("https://mumul.site", "https://api-mumul.site")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                .allowedHeaders("*")
                .allowCredentials(true) // 쿠키 인증 요청 허용
                .exposedHeaders("Authorization")
                .exposedHeaders("Access-Control-Allow-Origin")// "Access-Control-Allow-Origin" 헤더를 노출합니다.
                .maxAge(3000); // 원하는 시간만큼 pre-flight 리퀘스트를 캐싱


    }

}
