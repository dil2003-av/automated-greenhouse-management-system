package com.assignment.automationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AutomationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutomationServiceApplication.class, args);
    }
}