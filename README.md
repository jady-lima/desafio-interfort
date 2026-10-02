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
- JUnit 5, Mockito e H2 para testes
- Docker

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
├── Dockerfile
├── docker-compose.yml
├── pom.xml
├── .env.example
├── README.md
└── IA_USAGE.md
```

O `Controller` expõe os endpoints HTTP; o `Service` coordena a consulta e o armazenamento; o `Repository` acessa o banco; a `Entity` representa os dados persistidos; o `DTO` define os dados retornados pela API; e `integration/viacep` comunica com a ViaCEP.

## Execução com Docker

Esta é a forma recomendada para executar o projeto. Pré-requisitos:

- Docker Compose

O Compose usa valores padrão de desenvolvimento para que seja possível subir tudo com o único comando abaixo. Se quiser personalizar o banco, copie `.env.example` para `.env` e ajuste as variáveis; os valores de senha do exemplo são apenas para desenvolvimento local.

No Windows PowerShell:

```powershell
Copy-Item .env.example .env
```

No Linux, macOS ou Git Bash:

```bash
cp .env.example .env
```

Na raiz do projeto, suba a aplicação:

```bash
docker compose up --build
```

- Frontend: http://localhost:3000
- Backend: http://localhost:8080

As portas padrão são `3000` (frontend), `8080` (backend) e `5432` (PostgreSQL). Se alguma estiver ocupada, você pode definir `FRONTEND_HOST_PORT`, `BACKEND_HOST_PORT` ou `POSTGRES_HOST_PORT` no `.env`; essas variáveis mudam somente as portas publicadas na máquina, não a comunicação interna entre os containers.

Para encerrar os serviços:

```bash
docker compose down
```

Para encerrar e também remover os dados persistidos do PostgreSQL:

```bash
docker compose down -v
```

## Execução sem Docker

### Backend

Pré-requisitos: Java 21, PostgreSQL e o Maven Wrapper incluído no projeto.

Crie um banco PostgreSQL chamado `consulta_cep`, copie `.env.example` para `.env` e ajuste as variáveis de conexão com seus dados locais. Não versione credenciais.

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

O backend inicia em `http://localhost:8080`.

### Frontend

Com o backend em execução, abra outro terminal:

```bash
cd frontend
npm install
npm run dev
```

Abra o endereço local informado pelo Vite (por padrão, `http://localhost:5173`). Durante o desenvolvimento, o Vite encaminha as chamadas `/consultas` para o backend.

## Funcionalidades

- Consulta de endereço por CEP.
- Integração com a API ViaCEP.
- Persistência das consultas realizadas.
- Visualização do histórico de consultas.
- Tratamento de erros da API.
- Interface web em Vue.

## API

### `GET /consultas?cep={cep}`

Consulta o CEP informado e retorna `cep`, `logradouro`, `bairro`, `cidade` e `dataConsulta`.

Exemplo:

```text
GET http://localhost:8080/consultas?cep=01001000
```

### `GET /consultas`

Retorna o histórico das consultas realizadas ou uma lista vazia (`[]`) quando não há registros.

Exemplo:

```text
GET http://localhost:8080/consultas
```

Erros de CEP inválido, CEP não encontrado e indisponibilidade da ViaCEP retornam HTTP 400, 404 e 502, respectivamente.

## Testes

Execute os testes do backend na raiz do projeto:

```powershell
.\mvnw.cmd test
```

Os testes do banco utilizam H2. Para validar o build do frontend:

```bash
cd frontend
npm run build
```
