package com.consumidor.generated;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Classe gerada a partir do WSDL para requisição de consulta de conta
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConsultarContaRequest", propOrder = {
    "numeroConta"
})
@XmlRootElement(name = "ConsultarContaRequest", namespace = "http://consultarconta.com/soap")
public class ConsultarContaRequest {

    @XmlElement(required = true, name = "numeroConta")
    protected String numeroConta;

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String value) {
        this.numeroConta = value;
    }
}
