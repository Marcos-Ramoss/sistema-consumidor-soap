package com.consumidor.generated;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import java.math.BigDecimal;

/**
 * Classe gerada a partir do WSDL para representar uma conta bancária
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Conta", propOrder = {
    "numeroConta",
    "titular",
    "saldo",
    "tipoConta",
    "agencia"
})
@XmlRootElement(name = "Conta", namespace = "http://consultarconta.com/soap")
public class Conta {

    @XmlElement(required = true)
    protected String numeroConta;
    
    @XmlElement(required = true)
    protected String titular;
    
    @XmlElement(required = true)
    protected BigDecimal saldo;
    
    @XmlElement(required = true)
    protected String tipoConta;
    
    @XmlElement(required = true)
    protected String agencia;

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String value) {
        this.numeroConta = value;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String value) {
        this.titular = value;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal value) {
        this.saldo = value;
    }

    public String getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(String value) {
        this.tipoConta = value;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String value) {
        this.agencia = value;
    }
}
