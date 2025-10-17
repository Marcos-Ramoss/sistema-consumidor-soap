package com.consumidor.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Handler global para tratamento centralizado de exceções
 * Implementa o padrão de tratamento de erros padronizado
 */
@RestControllerAdvice
@Slf4j
public class HandlerGlobalExcecoes {

    /**
     * Trata exceções de validação de dados
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> tratarErroValidacao(MethodArgumentNotValidException ex) {
        Map<String, Object> resposta = new HashMap<>();
        Map<String, String> erros = new HashMap<>();
        
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String campo = ((FieldError) error).getField();
            String mensagem = error.getDefaultMessage();
            erros.put(campo, mensagem);
        });
        
        resposta.put("timestamp", LocalDateTime.now());
        resposta.put("status", HttpStatus.BAD_REQUEST.value());
        resposta.put("erro", "Dados inválidos");
        resposta.put("mensagem", "Erro de validação nos dados fornecidos");
        resposta.put("detalhes", erros);
        
        log.warn("Erro de validação: {}", erros);
        return ResponseEntity.badRequest().body(resposta);
    }

    /**
     * Trata exceções de conta não encontrada
     */
    @ExceptionHandler(ContaNaoEncontradaExcecao.class)
    public ResponseEntity<Map<String, Object>> tratarContaNaoEncontrada(ContaNaoEncontradaExcecao ex) {
        Map<String, Object> resposta = criarRespostaErro(HttpStatus.NOT_FOUND, "Conta não encontrada", ex.getMessage());
        log.warn("Conta não encontrada: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resposta);
    }

    /**
     * Trata exceções de comunicação SOAP
     */
    @ExceptionHandler(ErroComunicacaoSoapExcecao.class)
    public ResponseEntity<Map<String, Object>> tratarErroComunicacaoSoap(ErroComunicacaoSoapExcecao ex) {
        Map<String, Object> resposta = criarRespostaErro(HttpStatus.SERVICE_UNAVAILABLE, "Erro de comunicação", ex.getMessage());
        log.error("Erro de comunicação SOAP: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(resposta);
    }

    /**
     * Trata exceções genéricas
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> tratarErroGenerico(Exception ex) {
        Map<String, Object> resposta = criarRespostaErro(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno", "Ocorreu um erro interno no servidor");
        log.error("Erro interno: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resposta);
    }

    /**
     * Cria resposta padronizada de erro
     */
    private Map<String, Object> criarRespostaErro(HttpStatus status, String erro, String mensagem) {
        Map<String, Object> resposta = new HashMap<>();
        resposta.put("timestamp", LocalDateTime.now());
        resposta.put("status", status.value());
        resposta.put("erro", erro);
        resposta.put("mensagem", mensagem);
        return resposta;
    }
}
