package com.consumidor.exception;

/**
 * Exceção lançada quando uma conta não é encontrada
 */
public class ContaNaoEncontradaExcecao extends RuntimeException {
    
    public ContaNaoEncontradaExcecao(String mensagem) {
        super(mensagem);
    }
    
    public ContaNaoEncontradaExcecao(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
