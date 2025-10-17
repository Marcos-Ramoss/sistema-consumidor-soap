package com.consumidor.mapper;

import com.consumidor.dto.ContaDto;
import com.consumidor.generated.Conta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ContaMapperTest {

    private ContaMapper contaMapper;
    private Conta contaSoap;
    private ContaDto contaDto;

    @BeforeEach
    void configurar() {
        contaMapper = new ContaMapper();

        contaSoap = new Conta();
        contaSoap.setNumeroConta("12345-6");
        contaSoap.setTitular("João Silva");
        contaSoap.setSaldo(new BigDecimal("1500.00"));
        contaSoap.setTipoConta("CORRENTE");
        contaSoap.setAgencia("001");

        contaDto = ContaDto.builder()
                .numeroConta("12345-6")
                .titular("João Silva")
                .saldo(new BigDecimal("1500.00"))
                .tipoConta("CORRENTE")
                .agencia("001")
                .build();
    }

    @Test
    void deveConverterContaSoapParaDto() {
        // When
        ContaDto resultado = contaMapper.paraDto(contaSoap);

        // Then
        assertNotNull(resultado);
        assertEquals("12345-6", resultado.getNumeroConta());
        assertEquals("João Silva", resultado.getTitular());
        assertEquals(new BigDecimal("1500.00"), resultado.getSaldo());
        assertEquals("CORRENTE", resultado.getTipoConta());
        assertEquals("001", resultado.getAgencia());
    }

    @Test
    void deveConverterDtoParaContaSoap() {
        // When
        Conta resultado = contaMapper.paraEntidade(contaDto);

        // Then
        assertNotNull(resultado);
        assertEquals("12345-6", resultado.getNumeroConta());
        assertEquals("João Silva", resultado.getTitular());
        assertEquals(new BigDecimal("1500.00"), resultado.getSaldo());
        assertEquals("CORRENTE", resultado.getTipoConta());
        assertEquals("001", resultado.getAgencia());
    }

    @Test
    void deveRetornarNullQuandoContaSoapNula() {
        // When
        ContaDto resultado = contaMapper.paraDto(null);

        // Then
        assertNull(resultado);
    }

    @Test
    void deveRetornarNullQuandoDtoNulo() {
        // When
        Conta resultado = contaMapper.paraEntidade(null);

        // Then
        assertNull(resultado);
    }
}
