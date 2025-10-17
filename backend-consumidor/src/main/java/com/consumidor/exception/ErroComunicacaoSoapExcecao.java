package com.consumidor.exception;

/**
 * Exceção lançada quando há erro na comunicação SOAP
 */
public class ErroComunicacaoSoapExcecao extends RuntimeException {
    
    public ErroComunicacaoSoapExcecao(String mensagem) {
        super(mensagem);
    }
    
    public ErroComunicacaoSoapExcecao(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
