package com.consumidor.controller;

import com.consumidor.dto.ConsultaContaRequestDto;
import com.consumidor.dto.ConsultaContaResponseDto;
import com.consumidor.service.ConsultaContaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST para consulta de contas bancárias
 * Responsável por receber requisições HTTP e delegar para a camada de serviço
 */
@RestController
@RequestMapping("/api/contas")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Consulta de Contas", description = "Endpoints para consulta de contas bancárias via SOAP")
public class ConsultaContaController {

    private final ConsultaContaService consultaContaService;

    /**
     * Consulta uma conta bancária pelo número
     * 
     * @param numeroConta Número da conta a ser consultada
     * @return Dados da conta consultada
     */
    @Operation(
        summary = "Consultar conta por número",
        description = "Consulta uma conta bancária pelo número da conta via GET"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Conta consultada com sucesso",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ConsultaContaResponseDto.class),
                examples = @ExampleObject(
                    name = "Sucesso",
                    value = """
                        {
                          "conta": {
                            "numeroConta": "12345-6",
                            "titular": "João Silva",
                            "saldo": 1500.00,
                            "tipoConta": "CORRENTE",
                            "agencia": "001"
                          },
                          "sucesso": true,
                          "mensagem": "Conta consultada com sucesso"
                        }
                        """
                )
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Conta não encontrada",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Conta não encontrada",
                    value = """
                        {
                          "timestamp": "2025-10-16T17:38:59",
                          "status": 404,
                          "erro": "Conta não encontrada",
                          "mensagem": "Conta não encontrada"
                        }
                        """
                )
            )
        ),
        @ApiResponse(
            responseCode = "503",
            description = "Erro de comunicação SOAP",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Erro de comunicação",
                    value = """
                        {
                          "timestamp": "2025-10-16T17:38:59",
                          "status": 503,
                          "erro": "Erro de comunicação",
                          "mensagem": "Erro ao consultar conta"
                        }
                        """
                )
            )
        )
    })
    @GetMapping("/{numeroConta}")
    public ResponseEntity<ConsultaContaResponseDto> consultarConta(
            @Parameter(description = "Número da conta no formato 12345-6", example = "12345-6")
            @PathVariable String numeroConta) {
        log.info("Recebida requisição REST para consulta da conta: {}", numeroConta);
        
        ConsultaContaRequestDto requestDto = ConsultaContaRequestDto.builder()
                .numeroConta(numeroConta)
                .build();
        
        ConsultaContaResponseDto response = consultaContaService.consultarConta(requestDto);
        
        return ResponseEntity.ok(response);
    }

    /**
     * Consulta uma conta bancária via POST com validação
     * 
     * @param requestDto Dados da requisição de consulta
     * @return Dados da conta consultada
     */
    @Operation(
        summary = "Consultar conta via POST",
        description = "Consulta uma conta bancária via POST com validação de dados"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Conta consultada com sucesso",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ConsultaContaResponseDto.class),
                examples = @ExampleObject(
                    name = "Sucesso",
                    value = """
                        {
                          "conta": {
                            "numeroConta": "12345-6",
                            "titular": "João Silva",
                            "saldo": 1500.00,
                            "tipoConta": "CORRENTE",
                            "agencia": "001"
                          },
                          "sucesso": true,
                          "mensagem": "Conta consultada com sucesso"
                        }
                        """
                )
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Erro de validação",
                    value = """
                        {
                          "timestamp": "2025-10-16T17:38:59",
                          "status": 400,
                          "erro": "Dados inválidos",
                          "mensagem": "Erro de validação nos dados fornecidos",
                          "detalhes": {
                            "numeroConta": "Número da conta é obrigatório"
                          }
                        }
                        """
                )
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Conta não encontrada",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Conta não encontrada",
                    value = """
                        {
                          "timestamp": "2025-10-16T17:38:59",
                          "status": 404,
                          "erro": "Conta não encontrada",
                          "mensagem": "Conta não encontrada"
                        }
                        """
                )
            )
        ),
        @ApiResponse(
            responseCode = "503",
            description = "Erro de comunicação SOAP",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Erro de comunicação",
                    value = """
                        {
                          "timestamp": "2025-10-16T17:38:59",
                          "status": 503,
                          "erro": "Erro de comunicação",
                          "mensagem": "Erro ao consultar conta"
                        }
                        """
                )
            )
        )
    })
    @PostMapping("/consultar")
    public ResponseEntity<ConsultaContaResponseDto> consultarContaPost(
            @Parameter(description = "Dados da requisição de consulta")
            @Valid @RequestBody ConsultaContaRequestDto requestDto) {
        log.info("Recebida requisição POST para consulta da conta: {}", requestDto.getNumeroConta());
        
        ConsultaContaResponseDto response = consultaContaService.consultarConta(requestDto);
        
        return ResponseEntity.ok(response);
    }
}
