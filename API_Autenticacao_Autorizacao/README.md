 # 🔐 API de Autenticação e Autorização com JWT | Spring Boot

Este projeto é uma API REST desenvolvida com **Spring Boot 3**, implementando um sistema completo de **autenticação e autorização baseado em JWT (JSON Web Token)**. A aplicação inclui testes com JUnit e Mockito, persistência com Spring Data JPA, banco H2 em memória e documentação via Swagger/OpenAPI.

---

## 📌 Funcionalidades

- Login com autenticação JWT
- Validação de tokens JWT
- Controle de acesso por perfil (`USER`, `ADMIN`)
- Swagger UI para explorar os endpoints
- Testes unitários com JUnit 5 e Mockito
- Criação automática de usuários no banco ao iniciar

---

## ⚙️ Tecnologias Utilizadas

- Java 17
- Spring Boot 3.5.3
- Spring Web
- Spring Security
- Spring Data JPA
- H2 Database (memória)
- JUnit 5
- Mockito
- Swagger/OpenAPI (springdoc-openapi)

---

## 🏁 Como rodar o projeto

### 1. Clonar o repositório

```bash
git clone https://github.com/lucasrodriguesdias/API_Autenticacao_Autorizacao.git
cd API_Autenticacao_Autorizacao
```

### 2. Configurar o `application.properties`

A aplicação já está pronta para rodar com H2. Verifique se o arquivo contém:

```properties
jwt.secret=segredoJWT123456

spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.h2.console.enabled=true
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
```

> Você pode alterar para MariaDB se desejar (configuração comentada no projeto).

### 3. Executar a aplicação

Você pode executar com Maven:

```bash
./mvnw spring-boot:run
```

Ou direto pela sua IDE favorita.

---

## 📚 Documentação da API

Acesse o Swagger UI:

👉 **[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)**

---

## 👤 Usuários Criados Automaticamente

| Usuário         | Senha        | Perfil |
|----------------|--------------|--------|
| mestre          | topchave321  | ADMIN  |
| usuarioPadrao   | padrao321    | USER   |

Esses usuários são inseridos automaticamente no banco ao iniciar o projeto.

---

## 🧪 Executando os Testes

```bash
./mvnw test
```

Os testes utilizam **JUnit 5** e **Mockito**, cobrindo principalmente a camada de serviços (`UserService`) e autenticação.

---

## 📁 Estrutura de Pacotes

```
src
├── main
│   └── java
│       └── com.example.authserver
│           ├── controller
│           ├── config
│           ├── model
│           ├── repository
│           └── service
└── test
    └── com.example.authserver
        └── service
```

---

## ✅ Endpoints Principais

| Método | Endpoint            | Acesso         | Descrição                        |
|--------|---------------------|----------------|----------------------------------|
| POST   | /auth/login         | Público        | Autentica e gera o token JWT     |
| GET    | /auth/validate      | Público        | Valida um token JWT              |
| GET    | /api/admin/**       | ADMIN apenas   | Área protegida para admin        |

---

## 🧠 Observações

- O token JWT é necessário para acessar endpoints protegidos (exceto `/auth/**`).
- O console do H2 está disponível em `http://localhost:8080/h2-console`.
- O projeto segue a política **stateless**, sem sessões.

---

## 👨‍🎓 Projeto Acadêmico

Desenvolvido como parte da disciplina **Arquitetura de Aplicações Web** – Centro Universitário Newton Paiva.

---

## 📄 Licença

Este projeto é de uso educacional. Sinta-se livre para estudar e adaptar.

