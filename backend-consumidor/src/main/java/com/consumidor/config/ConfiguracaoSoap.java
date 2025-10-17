package com.consumidor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

/**
 * Configuração para cliente SOAP
 * Responsável por configurar marshaller/unmarshaller JAXB
 * e template para comunicação SOAP
 */
@Configuration
public class ConfiguracaoSoap {

    /**
     * Configura o marshaller JAXB para serialização/deserialização
     * das classes geradas a partir do WSDL
     */
    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(
            com.consumidor.generated.ConsultarContaRequest.class,
            com.consumidor.generated.ConsultarContaResponse.class,
            com.consumidor.generated.Conta.class
        );
        marshaller.setCheckForXmlRootElement(false);
        return marshaller;
    }

    /**
     * Configura o template para comunicação SOAP
     * Utiliza o marshaller para conversão de objetos
     */
    @Bean
    public WebServiceTemplate webServiceTemplate(Jaxb2Marshaller marshaller, 
                                                 HttpComponentsMessageSender messageSender) {
        WebServiceTemplate template = new WebServiceTemplate();
        template.setMarshaller(marshaller);
        template.setUnmarshaller(marshaller);
        template.setDefaultUri("http://localhost:8080/ws");
        template.setMessageSender(messageSender);
        
        // Configuração básica sem interceptors por enquanto
        
        return template;
    }
}
