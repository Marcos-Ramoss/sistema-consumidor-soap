package com.consumidor.mapper;

import com.consumidor.dto.ContaDto;
import com.consumidor.generated.Conta;
import org.springframework.stereotype.Component;

/**
 * Mapper responsável pela conversão entre DTOs e classes geradas
 * Implementa o padrão Builder para conversões limpas
 */
@Component
public class ContaMapper {

    /**
     * Converte Conta (classe gerada) para ContaDto
     */
    public ContaDto paraDto(Conta conta) {
        if (conta == null) {
            return null;
        }
        
        return ContaDto.builder()
                .numeroConta(conta.getNumeroConta())
                .titular(conta.getTitular())
                .saldo(conta.getSaldo())
                .tipoConta(conta.getTipoConta())
                .agencia(conta.getAgencia())
                .build();
    }

    /**
     * Converte ContaDto para Conta (classe gerada)
     */
    public Conta paraEntidade(ContaDto contaDto) {
        if (contaDto == null) {
            return null;
        }
        
        Conta conta = new Conta();
        conta.setNumeroConta(contaDto.getNumeroConta());
        conta.setTitular(contaDto.getTitular());
        conta.setSaldo(contaDto.getSaldo());
        conta.setTipoConta(contaDto.getTipoConta());
        conta.setAgencia(contaDto.getAgencia());
        
        return conta;
    }
}
