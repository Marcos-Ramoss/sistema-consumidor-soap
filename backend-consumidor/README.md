# Sistema Consumidor SOAP

Sistema consumidor que se comunica com o sistema produtor de consulta de conta bancária via SOAP.

## Arquitetura

O sistema segue os princípios de Clean Architecture com separação clara de responsabilidades:

- **Controller**: Recebe requisições HTTP e delega para a camada de serviço
- **Service**: Contém a lógica de negócio e orquestra as operações
- **Client**: Responsável pela comunicação SOAP com o sistema produtor
- **Mapper**: Converte entre DTOs e classes geradas
- **DTOs**: Objetos de transferência de dados entre camadas
- **Exception Handler**: Tratamento centralizado de exceções

## Tecnologias

- Java 17
- Spring Boot 3.2.0
- Spring Web Services
- JAXB
- Lombok
- JUnit 5
- Mockito

## Configuração

### Pré-requisitos

- Java 17+
- Maven 3.6+
- Sistema produtor rodando na porta 8080

### Execução

1. **Gerar classes a partir do WSDL:**
```bash
mvn clean compile
```

2. **Executar a aplicação:**
```bash
mvn spring-boot:run
```

A aplicação estará disponível em `http://localhost:8081`

## 📚 Documentação e Testes

### Swagger UI
Acesse a documentação interativa em: **http://localhost:8081/swagger-ui.html**

- ✅ Interface interativa para testar endpoints
- ✅ Documentação automática da API
- ✅ Exemplos de request/response
- ✅ Validação de dados em tempo real

### Endpoints

#### GET /api/contas/{numeroConta}
Consulta uma conta pelo número.

**Exemplo:**
```bash
curl http://localhost:8081/api/contas/12345-6
```

#### POST /api/contas/consultar
Consulta uma conta via POST com validação.

**Exemplo:**
```bash
curl -X POST http://localhost:8081/api/contas/consultar \
  -H "Content-Type: application/json" \
  -d '{"numeroConta": "12345-6"}'
```

## Estrutura do Projeto

```
src/main/java/com/consumidor/
├── ConsumidorApplication.java          # Classe principal
├── config/
│   └── ConfiguracaoSoap.java          # Configuração SOAP
├── controller/
│   └── ConsultaContaController.java   # Controller REST
├── service/
│   └── ConsultaContaService.java      # Lógica de negócio
├── client/
│   └── ConsultaContaClient.java       # Cliente SOAP
├── dto/
│   ├── ContaDto.java                  # DTO da conta
│   ├── ConsultaContaRequestDto.java   # DTO de requisição
│   └── ConsultaContaResponseDto.java  # DTO de resposta
├── mapper/
│   └── ContaMapper.java               # Conversor DTO ↔ Entity
└── exception/
    ├── ContaNaoEncontradaExcecao.java # Exceção de conta não encontrada
    ├── ErroComunicacaoSoapExcecao.java # Exceção de comunicação SOAP
    └── HandlerGlobalExcecoes.java    # Handler global de exceções
```

## Testes

### Testes Unitários
Execute os testes com:
```bash
mvn test
```

### Testes via Swagger
1. Execute a aplicação: `mvn spring-boot:run`
2. Acesse: http://localhost:8081/swagger-ui.html
3. Teste os endpoints diretamente na interface
4. Veja exemplos de request/response
5. Valide dados em tempo real

## Logs

Os logs estão configurados para mostrar:
- DEBUG level para `com.consumidor`
- DEBUG level para `org.springframework.ws`
- Formato: `yyyy-MM-dd HH:mm:ss - mensagem`

## Troubleshooting

### Problemas Comuns

1. **Erro de Conexão**: Verifique se o sistema produtor está rodando na porta 8080
2. **Erro de Marshalling**: Execute `mvn clean compile` para gerar as classes
3. **Timeout**: Ajuste as configurações de timeout no `application.yml`
4. **Namespace Incorreto**: Verifique se o namespace está correto no WSDL

### Validação de Conectividade

```bash
# Teste se o WSDL está acessível
curl http://localhost:8080/ws/contas.wsdl

# Teste da aplicação
curl http://localhost:8081/api/contas/12345-6
```
