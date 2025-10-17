package com.consumidor.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

/**
 * Configuração de timeout para WebServiceTemplate
 */
@Configuration
public class ConfiguracaoTimeout {

    @Value("${soap.client.timeout:60000}")
    private int timeout;

    @Value("${soap.client.connection-timeout:30000}")
    private int connectionTimeout;

    @Bean
    public HttpComponentsMessageSender messageSender() {
        HttpComponentsMessageSender messageSender = new HttpComponentsMessageSender();
        messageSender.setConnectionTimeout(connectionTimeout);
        messageSender.setReadTimeout(timeout);
        return messageSender;
    }
}
