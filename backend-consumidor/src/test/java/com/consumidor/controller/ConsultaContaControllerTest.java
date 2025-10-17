package com.consumidor.controller;

import com.consumidor.dto.ConsultaContaRequestDto;
import com.consumidor.dto.ConsultaContaResponseDto;
import com.consumidor.dto.ContaDto;
import com.consumidor.service.ConsultaContaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ConsultaContaController.class)
class ConsultaContaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConsultaContaService consultaContaService;

    @Autowired
    private ObjectMapper objectMapper;

    private ConsultaContaResponseDto responseDto;
    private ConsultaContaRequestDto requestDto;

    @BeforeEach
    void configurar() {
        ContaDto contaDto = ContaDto.builder()
                .numeroConta("12345-6")
                .titular("João Silva")
                .saldo(new BigDecimal("1500.00"))
                .tipoConta("CORRENTE")
                .agencia("001")
                .build();

        responseDto = ConsultaContaResponseDto.builder()
                .conta(contaDto)
                .sucesso(true)
                .mensagem("Conta consultada com sucesso")
                .build();

        requestDto = ConsultaContaRequestDto.builder()
                .numeroConta("12345-6")
                .build();
    }

    @Test
    void deveConsultarContaViaGetComSucesso() throws Exception {
        // Given
        when(consultaContaService.consultarConta(any(ConsultaContaRequestDto.class))).thenReturn(responseDto);

        // When & Then
        mockMvc.perform(get("/api/contas/12345-6"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.sucesso").value(true))
                .andExpect(jsonPath("$.conta.numeroConta").value("12345-6"))
                .andExpect(jsonPath("$.conta.titular").value("João Silva"));
    }

    @Test
    void deveConsultarContaViaPostComSucesso() throws Exception {
        // Given
        when(consultaContaService.consultarConta(any(ConsultaContaRequestDto.class))).thenReturn(responseDto);

        // When & Then
        mockMvc.perform(post("/api/contas/consultar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.sucesso").value(true))
                .andExpect(jsonPath("$.conta.numeroConta").value("12345-6"));
    }

    @Test
    void deveRetornarErroQuandoDadosInvalidos() throws Exception {
        // Given
        ConsultaContaRequestDto requestInvalido = ConsultaContaRequestDto.builder()
                .numeroConta("") // Número vazio para gerar erro de validação
                .build();

        // When & Then
        mockMvc.perform(post("/api/contas/consultar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestInvalido)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.erro").value("Dados inválidos"));
    }
}
