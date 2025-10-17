package com.consumidor.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO para representação de dados da conta bancária
 * Utilizado para comunicação entre camadas sem expor entidades de domínio
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados da conta bancária")
public class ContaDto {
    
    @Schema(description = "Número da conta no formato 12345-6", example = "12345-6")
    private String numeroConta;
    
    @Schema(description = "Nome do titular da conta", example = "João Silva")
    private String titular;
    
    @Schema(description = "Saldo atual da conta", example = "1500.00")
    private BigDecimal saldo;
    
    @Schema(description = "Tipo da conta", example = "CORRENTE", allowableValues = {"CORRENTE", "POUPANCA", "SALARIO"})
    private String tipoConta;
    
    @Schema(description = "Código da agência", example = "001")
    private String agencia;
}
