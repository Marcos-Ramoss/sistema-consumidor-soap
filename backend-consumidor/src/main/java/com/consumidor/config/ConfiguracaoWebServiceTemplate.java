package com.consumidor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;

/**
 * Configuração adicional para WebServiceTemplate
 */
@Configuration
public class ConfiguracaoWebServiceTemplate {

    @Bean
    public SaajSoapMessageFactory messageFactory() {
        SaajSoapMessageFactory messageFactory = new SaajSoapMessageFactory();
        messageFactory.setSoapVersion(org.springframework.ws.soap.SoapVersion.SOAP_11);
        return messageFactory;
    }
}
