# Consulta de CEP

Aplicação desenvolvida para o desafio técnico de Desenvolvedor Júnior.

## Objetivo

Receber um CEP, consultar os dados de endereço na API pública [ViaCEP](https://viacep.com.br/), devolver o resultado e manter um histórico das consultas realizadas em banco de dados.

## Estado atual

O projeto está sendo desenvolvido de forma incremental.

## Tecnologias

| Tecnologia | Versão | Uso |
|---|---|---|
| Java | 21 | Linguagem |
| Spring Boot | 4.1.1 | Framework da aplicação |
| Maven | 3.9 (via Maven Wrapper) | Build e gerenciamento de dependências |
| JUnit 5 | gerenciada pelo Spring Boot | Testes |
| PostgreSQL | a definir | Banco de dados (ainda não configurado) |

## Pré-requisitos

- JDK 21 instalado e disponível no `PATH` (ou `JAVA_HOME` configurado).

Não é necessário instalar o Maven: o projeto inclui o Maven Wrapper, que baixa a versão correta na primeira execução.

## Como executar

Na raiz do projeto:

```bash
# Linux / macOS / Git Bash
./mvnw spring-boot:run
```

```powershell
# Windows (PowerShell ou CMD)
.\mvnw.cmd spring-boot:run
```

A aplicação sobe em `http://localhost:8080`.

## Como rodar os testes

```bash
./mvnw test
```

```powershell
.\mvnw.cmd test
```

## Estrutura do projeto

```
.
├── .mvn/wrapper/          # configuração do Maven Wrapper
├── src
│   ├── main
│   │   ├── java/com/jadylima/consultacep/
│   │   │   └── ConsultaCepApplication.java   # classe principal
│   │   └── resources/
│   │       └── application.yml               # configuração da aplicação
│   └── test
│       └── java/com/jadylima/consultacep/
│           └── ConsultaCepApplicationTests.java   # teste de carga do contexto
├── mvnw / mvnw.cmd        # scripts do Maven Wrapper
└── pom.xml
```
