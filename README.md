# Consulta CEP

Aplicação para consultar endereços por CEP utilizando a API pública ViaCEP e armazenar o histórico das consultas realizadas.

## Tecnologias

- Java 21
- Spring Boot 4.1.1
- Spring Data JPA
- PostgreSQL
- Maven Wrapper
- Vue 3
- Vite
- HTML, CSS e JavaScript
- JUnit 5 e Mockito
- H2 para testes

## Estrutura do projeto

```text
consulta-cep/
├── src/
│   ├── main/
│   │   ├── java/com/jadylima/consultacep/
│   │   │   ├── controller/
│   │   │   ├── dto/
│   │   │   ├── entity/
│   │   │   ├── exception/
│   │   │   ├── integration/viacep/
│   │   │   ├── repository/
│   │   │   └── service/
│   │   └── resources/
│   └── test/
├── frontend/
├── pom.xml
├── .env.example
├── README.md
└── IA_USAGE.md
```

O `Controller` expõe os endpoints HTTP; o `Service` coordena a consulta e o armazenamento; o `Repository` realiza o acesso ao banco; a `Entity` representa os dados persistidos; o `DTO` define os dados retornados pela API; e `integration/viacep` é responsável pela comunicação com a ViaCEP.

## Como executar

### Backend

Pré-requisitos:

- Java 21
- PostgreSQL
- Maven Wrapper

Crie um banco PostgreSQL chamado `consulta_cep`.

Copie `.env.example` para `.env` na raiz do projeto e configure a conexão utilizando seus próprios dados locais. O arquivo `.env` não deve ser versionado.

No Windows PowerShell:

```powershell
Copy-Item .env.example .env
.\mvnw.cmd spring-boot:run
```

No Linux, macOS ou Git Bash:

```bash
cp .env.example .env
./mvnw spring-boot:run
```

O backend será iniciado em:

```text
http://localhost:8080
```

### Frontend

Com o backend em execução, abra outro terminal:

```bash
cd frontend
npm install
npm run dev
```

Abra o endereço local informado pelo Vite. Por padrão, a interface fica disponível em:

```text
http://localhost:5173
```

Durante o desenvolvimento, o Vite encaminha as chamadas para `/consultas` ao backend em `http://localhost:8080`.

## Funcionalidades

- Consulta de endereço por CEP.
- Integração com a API ViaCEP.
- Persistência das consultas realizadas.
- Visualização do histórico de consultas.
- Tratamento de erros da API.
- Interface web em Vue.

## Arquitetura

O fluxo principal da consulta é:

```text
Frontend Vue
     ↓
Controller
     ↓
Service
     ↓
ViaCepClient → ViaCEP
     ↓
Repository
     ↓
PostgreSQL
```

O `Service` coordena a consulta externa, transforma os dados recebidos e realiza a persistência do histórico.

## API

### `GET /consultas?cep={cep}`

Consulta o CEP informado e retorna os dados do endereço:

- `cep`
- `logradouro`
- `bairro`
- `cidade`
- `dataConsulta`

Exemplo:

```text
GET http://localhost:8080/consultas?cep=01001000
```

### `GET /consultas`

Retorna o histórico das consultas realizadas.

Quando não existem consultas, retorna uma lista vazia:

```json
[]
```

Exemplo:

```text
GET http://localhost:8080/consultas
```

### Tratamento de erros

- `400 Bad Request` — CEP inválido.
- `404 Not Found` — CEP não encontrado.
- `502 Bad Gateway` — indisponibilidade ou falha de comunicação com a ViaCEP.

## Testes

Para executar os testes do backend:

```powershell
.\mvnw.cmd test
```

Os testes cobrem as principais regras da aplicação, incluindo consulta, histórico, persistência, tratamento de erros e integração com a ViaCEP.

Os testes de persistência utilizam H2.

## Observações

O backend e o frontend são executados separadamente durante o desenvolvimento.

A estrutura do banco é criada ou atualizada automaticamente pelo Hibernate conforme a configuração da aplicação.