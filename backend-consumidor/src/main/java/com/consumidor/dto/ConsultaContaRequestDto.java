package com.consumidor.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * DTO para requisição de consulta de conta
 * Contém validações para garantir dados válidos
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados da requisição de consulta de conta")
public class ConsultaContaRequestDto {
    
    @NotBlank(message = "Número da conta é obrigatório")
    @Pattern(regexp = "\\d{5}-\\d", message = "Número da conta deve estar no formato 12345-6")
    @Schema(description = "Número da conta no formato 12345-6", example = "12345-6", required = true)
    private String numeroConta;
}
