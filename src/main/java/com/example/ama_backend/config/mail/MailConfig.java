package com.example.ama_backend.config.mail;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com"); // 여러분의 SMTP 호스트를 설정하세요
        mailSender.setPort(587); // 여러분의 SMTP 포트를 설정하세요

        // 필요한 경우 추가적인 속성 설정 (인증, TLS 등)
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        mailSender.setJavaMailProperties(properties);

        // 인증이 필요한 경우 이메일 사용자 이름과 암호를 설정하세요
        mailSender.setUsername("dev.choiey@gmail.com");
        mailSender.setPassword("hlxxqflntgzjzxpw");

        return mailSender;
    }
}
