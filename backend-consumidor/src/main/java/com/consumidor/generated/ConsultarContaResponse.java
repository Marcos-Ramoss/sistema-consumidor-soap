package com.consumidor.generated;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Classe gerada a partir do WSDL para resposta de consulta de conta
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConsultarContaResponse", propOrder = {
    "conta"
})
@XmlRootElement(name = "ConsultarContaResponse", namespace = "http://consultarconta.com/soap")
public class ConsultarContaResponse {

    @XmlElement(required = true)
    protected Conta conta;

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta value) {
        this.conta = value;
    }
}
