package com.example.ama_backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("http://13.124.15.169")
                .allowedOriginPatterns("http://13.124.15.169/")
                .allowedOriginPatterns("http://54.180.80.63:3000")
                .allowedOriginPatterns("http://localhost:3000")
                .allowedOriginPatterns("http://54.180.80.63")
                .allowedOriginPatterns("https://mumul.site/")
                .allowedOriginPatterns("http://mumul.site/")

//                .allowedOriginPatterns("*")   // .allowCredentails(true)랑 같이 못쓰는 와일드 카드임

                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                .allowedHeaders("*")
                .allowCredentials(true) // false -> true : 쿠키 인증 요청 허용
                .exposedHeaders("Authorization","RefreshToken")
                .exposedHeaders("Access-Control-Allow-Origin") // "Access-Control-Allow-Origin" 헤더를 노출합니다.
                .maxAge(3600); // 원하는 시간만큼 pre-flight 리퀘스트를 캐싱


    }

}
