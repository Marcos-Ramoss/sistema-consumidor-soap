package com.consumidor.service;

import com.consumidor.client.ConsultaContaClient;
import com.consumidor.dto.ConsultaContaRequestDto;
import com.consumidor.dto.ConsultaContaResponseDto;
import com.consumidor.dto.ContaDto;
import com.consumidor.exception.ContaNaoEncontradaExcecao;
import com.consumidor.generated.ConsultarContaResponse;
import com.consumidor.generated.Conta;
import com.consumidor.mapper.ContaMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultaContaServiceTest {

    @Mock
    private ConsultaContaClient consultaContaClient;

    @Mock
    private ContaMapper contaMapper;

    @InjectMocks
    private ConsultaContaService consultaContaService;

    private ConsultaContaRequestDto requestDto;
    private ConsultarContaResponse responseSoap;
    private Conta contaSoap;
    private ContaDto contaDto;

    @BeforeEach
    void configurar() {
        requestDto = ConsultaContaRequestDto.builder()
                .numeroConta("12345-6")
                .build();

        contaSoap = new Conta();
        contaSoap.setNumeroConta("12345-6");
        contaSoap.setTitular("João Silva");
        contaSoap.setSaldo(new BigDecimal("1500.00"));
        contaSoap.setTipoConta("CORRENTE");
        contaSoap.setAgencia("001");

        responseSoap = new ConsultarContaResponse();
        responseSoap.setConta(contaSoap);

        contaDto = ContaDto.builder()
                .numeroConta("12345-6")
                .titular("João Silva")
                .saldo(new BigDecimal("1500.00"))
                .tipoConta("CORRENTE")
                .agencia("001")
                .build();
    }

    @Test
    void deveConsultarContaComSucesso() {
        // Given
        when(consultaContaClient.consultarConta("12345-6")).thenReturn(responseSoap);
        when(contaMapper.paraDto(contaSoap)).thenReturn(contaDto);

        // When
        ConsultaContaResponseDto resultado = consultaContaService.consultarConta(requestDto);

        // Then
        assertNotNull(resultado);
        assertTrue(resultado.isSucesso());
        assertEquals("Conta consultada com sucesso", resultado.getMensagem());
        assertNotNull(resultado.getConta());
        assertEquals("12345-6", resultado.getConta().getNumeroConta());
        assertEquals("João Silva", resultado.getConta().getTitular());

        verify(consultaContaClient).consultarConta("12345-6");
        verify(contaMapper).paraDto(contaSoap);
    }

    @Test
    void deveLancarExcecaoQuandoContaNaoEncontrada() {
        // Given
        ConsultarContaResponse responseVazio = new ConsultarContaResponse();
        responseVazio.setConta(null);
        when(consultaContaClient.consultarConta("12345-6")).thenReturn(responseVazio);

        // When & Then
        ContaNaoEncontradaExcecao excecao = assertThrows(ContaNaoEncontradaExcecao.class, 
            () -> consultaContaService.consultarConta(requestDto));
        
        assertEquals("Conta não encontrada", excecao.getMessage());
        verify(consultaContaClient).consultarConta("12345-6");
        verify(contaMapper, never()).paraDto(any());
    }

    @Test
    void deveLancarExcecaoQuandoRespostaNula() {
        // Given
        when(consultaContaClient.consultarConta("12345-6")).thenReturn(null);

        // When & Then
        ContaNaoEncontradaExcecao excecao = assertThrows(ContaNaoEncontradaExcecao.class, 
            () -> consultaContaService.consultarConta(requestDto));
        
        assertEquals("Conta não encontrada", excecao.getMessage());
        verify(consultaContaClient).consultarConta("12345-6");
        verify(contaMapper, never()).paraDto(any());
    }
}
