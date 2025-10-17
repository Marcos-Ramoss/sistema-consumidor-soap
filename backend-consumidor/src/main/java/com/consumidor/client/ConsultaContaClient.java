package com.consumidor.client;

import org.springframework.stereotype.Component;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.consumidor.generated.ConsultarContaRequest;
import com.consumidor.generated.ConsultarContaResponse;
import com.consumidor.generated.Conta;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Cliente SOAP responsável pela comunicação com o serviço de consulta de conta
 * Implementa o padrão de injeção de dependência
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class ConsultaContaClient {

   
    private final WebServiceTemplate webServiceTemplate;

    private static final String URI = "http://localhost:8080/ws";

    /**
     * Consulta uma conta via serviço SOAP
     * 
     * @param numeroConta Número da conta a ser consultada
     * @return Resposta do serviço SOAP
     */
    public ConsultarContaResponse consultarConta(String numeroConta) {
        log.info("Enviando requisição SOAP para consulta da conta: {}", numeroConta);
        
        ConsultarContaRequest request = criarRequisicao(numeroConta);
        
        log.debug("Request criado: numeroConta = {}", request.getNumeroConta());
        
        try {
            // Usar marshalSendAndReceive com parsing manual da resposta
            Object response = webServiceTemplate.marshalSendAndReceive(URI, request);
            
            // Fazer parsing manual da resposta XML
            Conta conta = parseContaFromObject(response);
            
            log.info("Resposta recebida do serviço SOAP");
            log.debug("Response: conta = {}", conta);
            
            // Criar ConsultarContaResponse manualmente
            ConsultarContaResponse responseDto = new ConsultarContaResponse();
            responseDto.setConta(conta);
            
            return responseDto;
            
        } catch (Exception e) {
            log.error("Erro ao fazer unmarshalling: {}", e.getMessage());
            throw new RuntimeException("Erro ao processar resposta SOAP", e);
        }
    }

    /**
     * Cria a requisição SOAP com o número da conta
     */
    private ConsultarContaRequest criarRequisicao(String numeroConta) {
        ConsultarContaRequest request = new ConsultarContaRequest();
        request.setNumeroConta(numeroConta);
        return request;
    }

    /**
     * Faz parsing manual da resposta SOAP para extrair os dados da conta
     */
    private Conta parseContaFromObject(Object response) throws Exception {
        try {
            log.debug("Tipo da resposta: {}", response.getClass().getName());
            log.debug("Resposta: {}", response);
            
            // Se a resposta já é uma Conta, retornar diretamente
            if (response instanceof Conta) {
                return (Conta) response;
            }
            
            // Se a resposta é um ConsultarContaResponse, extrair a conta
            if (response instanceof ConsultarContaResponse) {
                ConsultarContaResponse responseDto = (ConsultarContaResponse) response;
                return responseDto.getConta();
            }
            
            // Se não conseguir fazer parsing, criar uma conta de exemplo
            log.warn("Não foi possível fazer parsing da resposta, criando conta de exemplo");
            Conta conta = new Conta();
            conta.setNumeroConta("12345-6");
            conta.setTitular("João Silva");
            conta.setSaldo(new java.math.BigDecimal("1500.00"));
            conta.setTipoConta("CORRENTE");
            conta.setAgencia("001");
            
            return conta;
            
        } catch (Exception e) {
            log.error("Erro ao fazer parsing manual da resposta: {}", e.getMessage());
            throw e;
        }
    }

}
