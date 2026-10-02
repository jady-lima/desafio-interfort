# Uso de Inteligência Artificial

## Quais ferramentas de IA foram utilizadas?

Foram utilizadas as seguintes ferramentas como apoio ao desenvolvimento:

- **ChatGPT** — planejamento, discussão de arquitetura, análise de alternativas, revisão e documentação.
- **GitHub Copilot** — apoio à implementação das etapas finais do backend e da interface frontend.

As ferramentas foram utilizadas como apoio ao desenvolvimento, mantendo a revisão e as decisões finais sob minha responsabilidade.

## Em quais etapas foram utilizadas?

### Planejamento e definição da solução

O **ChatGPT** foi utilizado principalmente para discutir a estrutura da aplicação, responsabilidades das camadas, organização das etapas de desenvolvimento e alternativas para atender aos requisitos do teste.

Também foi utilizado para revisar decisões técnicas e identificar possíveis problemas antes da implementação.

### Implementação do backend

O **GitHub Copilot** foi utilizado no backend, principalmente para:

- implementação e ajustes dos testes;
- apoio na implementação das regras do Service.
- implementação do Controller;
- criação do DTO de resposta;
- tratamento global de exceções;
- testes do Controller;
- implementação da consulta do histórico;
- ajustes necessários para integrar as diferentes camadas.

### Implementação do frontend

O **GitHub Copilot** também foi utilizado como apoio na implementação da interface em Vue 3, principalmente para estruturar a página, realizar as chamadas para a API e exibir os resultados e o histórico.

A implementação foi mantida propositalmente simples para atender ao escopo do teste sem adicionar bibliotecas ou funcionalidades desnecessárias.

## Exemplos de prompts utilizados

### Planejamento do backend

> "Me ajude a definir uma estrutura incremental para uma API em Java + Spring Boot que consulte a ViaCEP e mantenha o histórico das consultas. Quero separar as responsabilidades entre Entity, Repository, Service, integração externa e Controller."

### Integração com a ViaCEP

> "Implemente a integração com a API ViaCEP considerando validação do CEP, tratamento de CEP inexistente e falhas de comunicação com a API externa. Mantenha a integração isolada em uma camada própria."

### Frontend

> "Crie uma interface Vue 3 simples para consumir a API existente, permitindo consultar um CEP, exibir o resultado e visualizar o histórico. Utilize apenas Vue, Vite, CSS e fetch, sem adicionar bibliotecas desnecessárias."

Os prompts foram utilizados de forma incremental, evitando solicitar a implementação completa do projeto de uma única vez.

## Como as respostas foram validadas?

As respostas geradas pelas ferramentas não foram incorporadas automaticamente.

Durante o desenvolvimento, revisei os arquivos criados ou alterados, verificando:

- se a implementação atendia aos requisitos do teste;
- se as responsabilidades estavam corretamente separadas;
- se as dependências adicionadas eram necessárias;
- se o código estava coerente com a estrutura existente;
- se os tratamentos de erro faziam sentido;
- se os contratos entre as camadas estavam corretos.

Além da revisão do código, as implementações foram validadas por meio de:

- execução dos testes automatizados;
- execução da aplicação;
- testes da integração real com a ViaCEP;
- validação da persistência no PostgreSQL;
- testes manuais dos endpoints;
- validação do fluxo completo pelo frontend.

As alterações foram revisadas antes de serem incorporadas ao projeto.

## Quais sugestões da IA foram aproveitadas?

Entre as sugestões aproveitadas durante o desenvolvimento estão:

- utilização de **DTO** para as respostas da API, evitando expor diretamente a Entity JPA;
- tratamento separado para CEP não encontrado e falhas de comunicação com o serviço externo;
- atualização do histórico a partir do endpoint da própria API, evitando duplicar a lógica de persistência no frontend;

As sugestões foram analisadas antes de serem adotadas e, quando necessário, ajustadas à estrutura e às decisões do projeto.

## Quais sugestões foram descartadas ou ajustadas?

Algumas sugestões e possibilidades foram avaliadas, mas não foram incorporadas quando não contribuíam diretamente para o objetivo do teste.

Entre elas:

- adicionar ferramentas de migração de banco como Flyway, optando por manter a configuração do Hibernate mais simples para o escopo da aplicação;
- adicionar Docker antes da conclusão da funcionalidade principal, priorizando primeiro a implementação e validação da aplicação;
- adicionar bibliotecas ou frameworks adicionais ao frontend, mantendo a interface em Vue 3 com uma implementação simples;
- manter soluções mais complexas quando uma implementação menor atendia ao requisito.

Também houve situações em que uma sugestão foi utilizada apenas como ponto de partida e posteriormente ajustada após a revisão manual.

## O que foi desenvolvido ou ajustado manualmente?

Além da revisão das sugestões das ferramentas, foram realizadas manualmente decisões, ajustes e validações ao longo do desenvolvimento.

Entre eles:

- definição e revisão da estrutura do projeto;
- definição das responsabilidades das camadas;
- escolha das tecnologias e do escopo da solução;
- revisão das entidades e relacionamentos utilizados;
- configuração do PostgreSQL e das variáveis de ambiente;
- validação da integração real com a ViaCEP;
- conferência das respostas para CEP válido e inexistente;
- revisão e execução dos testes;
- validação da persistência dos dados;
- revisão dos endpoints e respostas HTTP;
- ajustes na interface frontend;
- revisão das alterações antes dos commits;
- organização do histórico de desenvolvimento no Git.

## Considerações finais

A Inteligência Artificial foi utilizada como ferramenta de apoio para acelerar o desenvolvimento, explorar alternativas e auxiliar na implementação e revisão. As decisões finais sobre arquitetura, escopo, código e comportamento da aplicação foram tomadas após análise das sugestões e validação no próprio ambiente do projeto.