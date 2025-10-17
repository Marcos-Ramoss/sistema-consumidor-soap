package com.consumidor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class ConsumidorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumidorApplication.class, args);
        log.info("http://localhost:8081/swagger-ui.html");
    }
    
}
