# Glaiss Core Modular 🚀

O **Glaiss Core** é um ecossistema de bibliotecas Java projetado para centralizar configurações, padrões de persistência, segurança e integrações de nuvem em projetos Spring Boot 3.

Anteriormente uma biblioteca única, o projeto foi refatorado para uma arquitetura **Multi-módulo Maven**, permitindo que cada microserviço utilize apenas o necessário, reduzindo o tamanho do artefato final e evitando dependências desnecessárias.

---

## 🏗️ Arquitetura dos Módulos

O projeto é composto por 5 módulos independentes e um Parent POM gerenciador:

### 1. `core-common` (O Alicerce)
*   **Responsabilidade**: Contém interfaces base, exceções globais, utilitários Jackson e anotações customizadas.
*   **Destaque**: Define a interface `CorsConfig`, permitindo que outros módulos configurem CORS sem depender do Spring Security.
*   **Dependências**: Apenas Spring Web e Validation.

### 2. `core-jpa` (Persistência)
*   **Responsabilidade**: Infraestrutura de banco de dados, auditoria automática (`CreatedBy`, `LastModifiedBy`) e abstrações de CRUD genérico (`BaseRepository`, `BaseService`).
*   **Auto-Configuração**: Ativa o JPA e Auditoria automaticamente. Possui um `DefaultAuditorAware` (fallback) que usa o nome da aplicação caso o módulo de segurança não esteja presente.

### 3. `core-cache` (Cache e Performance)
*   **Responsabilidade**: Configuração de `RedisTemplate` e `CacheManager`.
*   **Auto-Configuração**: Ativada por padrão ao adicionar a dependência.

### 4. `core-security` (Segurança e Integração)
*   **Responsabilidade**: Gestão de identidade via JWT (OAuth2), extração de contexto de usuário e **Integrações Modulares**.
*   **Papel Integrador**: Este módulo detecta a presença de outros módulos e adiciona comportamentos extras:
    *   **Com JPA**: Fornece o `AuditoriaAwareImpl` que extrai o usuário do JWT para o banco de dados.
    *   **Com Cloud**: Fornece o `FeignClientInterceptor` que propaga o token de autenticação em chamadas entre serviços.

### 5. `core-cloud` (Cloud e Service Discovery)
*   **Responsabilidade**: Integração com Eureka e OpenFeign.
*   **Destaque**: Fornece um `RestTemplate` com balanceamento de carga (`@LoadBalanced`) e configuração dinâmica de CORS baseada nas instâncias do Gateway registradas no Eureka.

---

## 🔌 Estratégia Plug-and-Play

Todas as bibliotecas utilizam o mecanismo de **Auto-configuration** do Spring Boot 3 (`META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports`). 

**O que isso significa?**
Você não precisa usar `@ComponentScan` ou `@Import` no seu projeto. Basta adicionar a dependência no `pom.xml`, e a funcionalidade estará **ativa por padrão**.

---

## 🛠️ Como Utilizar e Configurar

Para utilizar as bibliotecas, adicione o módulo desejado no seu `pom.xml`:

```xml
<dependency>
    <groupId>com.glaiss</groupId>
    <artifactId>core-{modulo}</artifactId>
    <version>1.1.0</version>
</dependency>
```

### 📦 core-common
**Funcionalidades:**
- **Exceções Globais**: Use `GlaissException` para retornos padronizados com `ProblemDetail`.
- **Contexto da Aplicação**: `ApplicationContextProvider.getBean(MyClass.class)` para acessar beans de forma estática.
- **Validação de BigDecimal**: Use `@ValorBigDecimal` para validar valores monetários.

**Configuração:**
Não requer propriedades extras. O `RestExceptionHandler` é ativado automaticamente.

---

### 🗄️ core-jpa
**Funcionalidades:**
- **Auditoria Automática**: Entidades que estendem `EntityAbstract` salvam automaticamente `created_date`, `created_by`, `modified_date` e `modified_by`.
- **Serviço Genérico**: Estenda `BaseServiceImpl` para ter um CRUD básico pronto.

**Configuração:**
Ativo por padrão. Use `spring.jpa.enabled=false` para desativar.
```properties
spring.application.name=meu-microservico # Usado no fallback da auditoria
```

---

### ⚡ core-cache (Redis)
**Funcionalidades:**
- **Serialização JSON**: Configura o Redis para usar Jackson, permitindo visualizar os dados no Redis Insight de forma legível.
- **Polimorfismo**: Suporte a tipos complexos com `@JsonTypeInfo`.

**Configuração:**
Ativo por padrão. Use `spring.redis.enabled=false` para desativar.
```properties
spring.data.redis.host=localhost
spring.data.redis.port=6379
```

---

### 🔐 core-security (JWT)
**Funcionalidades:**
- **Conversor de JWT**: Converte a claim `authorities` do token para a hierarquia do enum `Privilegio`.
- **Contexto de Segurança**: Use `SecurityContextUtils.getUsername()` ou `getId()` para obter dados do usuário logado.
- **Propagação de Token**: Se usar Feign, o token é enviado automaticamente para o próximo serviço.

**Configuração:**
Ativo por padrão. Use as propriedades abaixo para desativar:
```properties
spring.security.enabled=false
spring.security.cors.enabled=false
spring.feign.client.enabled=false
```

---

### ☁️ core-cloud (Eureka & OpenFeign)
**Funcionalidades:**
- **RestTemplate Balanceado**: Basta injetar um `RestTemplate` e usá-lo com o nome do serviço no Eureka.
- **CORS Dinâmico**: Permite requisições automáticas vindas do `GATEWAY` registrado no Eureka.

**Configuração:**
Ativo por padrão. Use `spring.eureka.enabled=false` para desativar.
```properties
eureka.client.serviceUrl.defaultZone=http://localhost:8761/eureka/
```

---

## ✅ Qualidade e Testes

O projeto conta com mais de **80% de cobertura de testes unitários**, seguindo o padrão **Given-When-Then** (Dado-Quando-Então).

---

## 🧩 Relações e Desacoplamento

Abaixo, o diagrama de dependências e integrações condicionais:

- **core-common**: Dependência base para todos.
- **core-jpa**, **core-cache**, **core-cloud**: Independentes entre si.
- **core-security**: Depende de `common`, mas integra-se opcionalmente com `jpa` e `cloud` via reflexão de classe (`@ConditionalOnClass`).
