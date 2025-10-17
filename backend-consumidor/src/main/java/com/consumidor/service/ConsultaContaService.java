package com.consumidor.service;

import com.consumidor.client.ConsultaContaClient;
import com.consumidor.dto.ConsultaContaRequestDto;
import com.consumidor.dto.ConsultaContaResponseDto;
import com.consumidor.dto.ContaDto;
import com.consumidor.exception.ContaNaoEncontradaExcecao;
import com.consumidor.exception.ErroComunicacaoSoapExcecao;
import com.consumidor.generated.ConsultarContaResponse;
import com.consumidor.mapper.ContaMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Serviço responsável pela lógica de negócio de consulta de conta
 * Orquestra a comunicação com o cliente SOAP e aplica regras de negócio
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ConsultaContaService {

    private final ConsultaContaClient consultaContaClient;
    private final ContaMapper contaMapper;

    /**
     * Consulta uma conta bancária
     * 
     * @param requestDto Dados da requisição de consulta
     * @return Dados da conta consultada
     */
    public ConsultaContaResponseDto consultarConta(ConsultaContaRequestDto requestDto) {
        log.info("Iniciando consulta de conta: {}", requestDto.getNumeroConta());
        
        try {
            ConsultarContaResponse response = consultaContaClient.consultarConta(requestDto.getNumeroConta());
            
            return processarResposta(response);
            
        } catch (ContaNaoEncontradaExcecao e) {
            log.warn("Conta não encontrada: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Erro ao consultar conta {}: {}", requestDto.getNumeroConta(), e.getMessage());
            throw new ErroComunicacaoSoapExcecao("Erro ao consultar conta: " + e.getMessage(), e);
        }
    }

    /**
     * Processa a resposta do serviço SOAP
     */
    private ConsultaContaResponseDto processarResposta(ConsultarContaResponse response) {
        if (response == null || response.getConta() == null) {
            throw new ContaNaoEncontradaExcecao("Conta não encontrada");
        }
        
        ContaDto contaDto = contaMapper.paraDto(response.getConta());
        
        return ConsultaContaResponseDto.builder()
                .conta(contaDto)
                .sucesso(true)
                .mensagem("Conta consultada com sucesso")
                .build();
    }
}
