package com.consumidor.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para resposta de consulta de conta
 * Contém os dados da conta consultada
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Resposta da consulta de conta")
public class ConsultaContaResponseDto {
    
    @Schema(description = "Dados da conta consultada")
    private ContaDto conta;
    
    @Schema(description = "Indica se a consulta foi bem-sucedida", example = "true")
    private boolean sucesso;
    
    @Schema(description = "Mensagem de retorno", example = "Conta consultada com sucesso")
    private String mensagem;
}
