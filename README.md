# Glaiss Core Modular 🚀

O **Glaiss Core** é um ecossistema de bibliotecas Java projetado para centralizar configurações, padrões de persistência, segurança e integrações de nuvem em projetos Spring Boot 3.

Anteriormente uma biblioteca única, o projeto foi refatorado para uma arquitetura **Multi-módulo Maven**, permitindo que cada microserviço utilize apenas o necessário, reduzindo o tamanho do artefato final e evitando dependências desnecessárias.

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
*   **Auto-Configuração**: Ativada condicionalmente pela propriedade `spring.redis.enabled=true`.

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
Você não precisa usar `@ComponentScan` ou `@Import` no seu projeto. Basta adicionar a dependência no `pom.xml`, e a funcionalidade estará pronta para uso.

---

## 🧩 Relações e Desacoplamento

Abaixo, o diagrama de dependências e integrações condicionais:

- **core-common**: Dependência base para todos.
- **core-jpa**, **core-cache**, **core-cloud**: Independentes entre si.
- **core-security**: Depende de `common`, mas integra-se opcionalmente com `jpa` e `cloud` via reflexão de classe (`@ConditionalOnClass`).

Dessa forma, o acoplamento cíclico foi eliminado. O módulo de nuvem (`core-cloud`) não sabe que existe segurança, mas o módulo de segurança sabe como adicionar um header ao Feign se o Feign estiver presente.

---

## ✅ Qualidade e Testes

O projeto conta com mais de **80% de cobertura de testes unitários**, seguindo o padrão **Given-When-Then** (Dado-Quando-Então).

*   **Testes de Configuração**: Utilizam `ApplicationContextRunner` para validar o carregamento condicional de Beans.
*   **Testes de Lógica**: Mockito e AssertJ para garantir o comportamento de validadores, conversores e handlers.

---

## 🛠️ Como Utilizar

Adicione o módulo desejado no seu `pom.xml`:

```xml
<dependency>
    <groupId>com.glaiss</groupId>
    <artifactId>core-jpa</artifactId>
    <version>1.1.0</version>
</dependency>
```

Para habilitar funcionalidades opcionais, utilize as propriedades:
*   `spring.redis.enabled=true`
*   `spring.jpa.enabled=true`
*   `spring.eureka.enabled=true`
