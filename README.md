<h1 align="center">RESTFul API Orders com Spring Boot e Spring Security</h1>

## :memo: Resumo do projeto
Projeto de uma simples API de Orders para fins de prática aplicando boas práticas e autenticação/autorização via token. A base desse projeto foi baseado no curso Java do super mestre Nélio Alves, fiz diversas implementações para fins de prática e estudos.

## :classical_building: Diagrama de Classes

Obs: _Criei a representação parcial do diagrama para fins de estudos_

```mermaid
classDiagram
    class Category {
        - id: Long [0..1]
        - name: String [0..1]
        - products: Set<Product> [0..*]
        + addProduct(product: Product)
        + removeProduct(product: Product)
    }

    class Order {
        - client: User [0..1]
        - id: Long [0..1]
        - items: Set<OrderItem> [0..*]
        - moment: Instant [0..1]
        - orderStatus: Integer [0..1]
        - payment: Payment [0..1]
    }

    class OrderItem {
        - id: OrderItemPK [0..1]
        - price: Double [0..1]
        - quantity: Integer [0..1]
    }

    class OrderItemPK {
        - order: Order [0..1]
        - product: Product [0..1]
    }

    class Payment {
        - id: Long [0..1]
        - moment: Instant [0..1]
        - order: Order [0..1]
    }

    class Product {
        - categories: Set<Category> [0..*]
        - description: String [0..1]
        - id: Long [0..1]
        - imgUrl: String [0..1]
        - name: String [0..1]
        - price: Double [0..1]
        + addCategories(category: Category)
        + removeCategory(category: Category)
    }

    class User {
        - email: String [1]
        - id: Long [0..1]
        - login: String [1]
        - name: String [1]
        - orders: List<Order> [0..*]
        - password: String [1]
        - phone: String [0..1]
        - role: Integer [0..1]
        + addOrder(order: Order)
        + builder(): UserBuilder
        + removeOrder(order: Order)
    }

    Category --> Product: products [0..*]
    Order --> OrderItem: items [0..*]
    Order --> Payment: payment [0..1]
    Order --> User: client [0..1]
    OrderItem --> OrderItemPK: id [0..1]
    OrderItemPK --> Order: order [0..1]
    OrderItemPK --> Product: product [0..1]
    Payment --> Order: order [0..1]
    Product --> Category: categories [0..*]
    User --> Order: orders [0..*]
```
<br>
<details>
<summary>** Mais alguns digramas de classe: </summary>

```mermaid
classDiagram
    class AuthenticationService {
        +loadUserByUsername(username: String): UserDetails
    }

    class Configurations {
        -unSecuredPaths: String[]
        -exceptionResolver: HandlerExceptionResolver
        +filter(): FilterToken
        +mvc(introspector: HandlerMappingIntrospector): MvcRequestMatcher.Builder
        +getAntPathRequestMatchers(): AntPathRequestMatcher[]
        +filterChain(http: HttpSecurity, mvc: MvcRequestMatcher.Builder): SecurityFilterChain
        +authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager
        +passwordEncoder(): PasswordEncoder
    }

    class FilterToken {
        -exceptionResolver: HandlerExceptionResolver
        -tokenService: TokenService
        -repository: UserRepository
        +FilterToken(exceptionResolver: HandlerExceptionResolver)
        +doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain)
    }

    class TokenService {
        +generateToken(user: User): String
        +getSubject(token: String): String
    }

    AuthenticationService ..> UserRepository: <<Autowired>>
    Configurations ..> HandlerExceptionResolver: <<Autowired>>
    Configurations ..> FilterToken: <<Autowired>>
    Configurations ..> HandlerMappingIntrospector: <<Autowired>>
    Configurations ..> HttpSecurity
    Configurations ..> MvcRequestMatcher Builder
    Configurations ..> AuthenticationConfiguration: <<Autowired>>
    Configurations ..> PasswordEncoder: <<Lazy>>
    FilterToken ..> HandlerExceptionResolver: <<Autowired>>
    FilterToken ..> TokenService: <<Autowired>>
    FilterToken ..> UserRepository: <<Autowired>>
    TokenService ..> User
```

```mermaid
classDiagram
    class UserResource {
        - limitPageSize: int [1]
        - service: UserService [1]
        + delete(id: Long): ResponseEntity<Void>
        + findAll(): ResponseEntity<List<UserDTO>>
        + findAllByOrder(pageable: Pageable): ResponseEntity<Page<UserDTO>>
        + findById(id: Long): ResponseEntity<UserDTO>
        + insert(obj: User): ResponseEntity<UserDTOSaved>
        + update(id: Long, obj: User): ResponseEntity<UserDTO>
    }

    class UserService {
        - passwordEncoder: PasswordEncoder [0..1]
        - repository: UserRepository [0..1]
        + delete(id: Long)
        + findAll(): List<UserDTO>
        + findAllByOrder(pageable: Pageable): Page<UserDTO>
        + findById(id: Long): User
        + insert(obj: User): User
        + update(id: Long, obj: User): User
        - updateData(entity: User, obj: User)
    }

    UserResource "1" --> "1" UserService : service
```

```mermaid
classDiagram
    class OpenAPIDefinition {
        servers
    }

    class Server {
        url
        description
    }

    class SpringBootApplication

    class Application {
        main(String[] args)
    }

    Application --> SpringBootApplication
    Application --> OpenAPIDefinition
    OpenAPIDefinition --> Server

    class UserRepository~User,UUID~ {
        <<interface>>
        +existsByAccountNumber(String accountNumber) boolean
    }

    class UserService {
        <<interface>>
        +findById(UUID id) Optional~User~
        +create(User userToCreate) Optional~User~
        +findAll() List<User>
    }

    class UserServiceImpl {
        <<Service>>
        - UserRepository repository
        +findById(UUID id) Optional~User~
        +create(User userToCreate) Optional~User~
        +findAll() List<User>
    }

    class UserController {
        <<RestController>>
        +findById(UUID id) Optional~User~
        +create(User userToCreate) Optional~User~
        +findAll() List<User>
    }

    class ResourceExceptionHandler {
        <<ControllerAdvice>>
        +handleException(Exception ex)
    }

    UserController ..> UserService: uses
    UserServiceImpl ..> UserRepository: uses
    UserServiceImpl --|> UserService: uses
```

```mermaid
classDiagram
  class OpenApi30Config {
    - moduleName: String
    - apiVersion: String
    - contactName: String
    - contactUrl: String
    - contactEmail: String
    - apiDescription: String
    --
    + customOpenAPI(): OpenAPI
  }
  class OpenAPI {
    --
    + addSecurityItem(SecurityRequirement): OpenAPI
    + components(Components): OpenAPI
    + info(Info): OpenAPI
  }
  class SecurityRequirement {
    + addList(String): SecurityRequirement
  }
  class Components {
    + addSecuritySchemes(String, SecurityScheme): Components
  }
  class SecurityScheme {
    - name: String
    - type: String
    - scheme: String
    - bearerFormat: String
  }
  class Info {
    - title: String
    - description: String
    - version: String
    - contact: Contact
  }
  class Contact {
    - name: String
    - url: String
    - email: String
  }
```
</details>

## :rocket: Funcionalidades que implementei como forma de estudo e prática de Spring Framework
- Usei Spring Data JPA para o relacionamento entre as entidades e CRUD
- Validação dos campos;
- Validação da complexidade da senha por meio de anotação personalizada;
- Endpoint para exibir todos os cadastros de usuários usando page/size/sort/limit retornando DTO
- JWT - Autenticação e Autorização
- HandlerExceptionResolver para capturar e personalizar as exceções do Spring Security
- Configurei o Springdoc/Swagger para suporte a autenticação via token

## :wrench: Tecnologias utilizadas

* Spring Boot 3
* Spring Data JPA
* SpringDoc OpenAPI WebMVC UI
* Lombok
* Maven
* Spring Boot Start Validation
* PostgreSQL
* Java 17
* Spring Security 6
* JWT auth0 4.4

## :clipboard: Executando o projeto


- Para executar o projeto faça o clone do repositório, no diretório onde executar esse comando será criado uma pasta com o nome spring-vendas

  ```bash
  git clone https://github.com/EdsonSFreitas/orders.git
  ```

- Credenciais do banco: Se usar o profile test não precisar alterar nada por que já está configurado e usará o banco H2. 
- Se for usar ambiente DEV pode alterar diretamente o arquivo application-dev.propoerties e definir os dados de conexão
- Se for usar o ambiente de PROD, o projeto foi configurado para que o usuário e a senha de acesso ao banco estejam registradas apenas como variáveis de ambiente, portanto, crie as variáveis de ambiente de nome "DB_USER" e "DB_PASS" no Sistema Operacional ou direto na IDE de sua escolha. Por exemplo, usando Linux basta executar esses dois comandos no terminal informando o usuario e senha do seu banco relacional entre aspas duplas como no exemplo abaixo.

    ```bash
    # Substituia o conteudo entre < > pelos valores que pretende usar:
    export DATABASE_URL="jdbc:postgresql://localhost:5432/<nome do banco>"
    export PG_USERNAME="<digite aqui o usuario do seu banco>"
    export PG_PASSWORD="digite aqui a senha do seu banco"
    ```

- Precisamos também definir um hash base64 para a variável JWT_SECRET ou se não informar será usado o do projeto.
- Esse hash pode ser gerado em diversos sites ou direto no shell Linux. Use o comando echo com uma frase qualquer encadeado com o openssl para gerar o hash, repare que existe propositalmente um espaço em branco antes do comando echo, isso é uma boa prática para que o comando não fique gravado no history do shell.

    ```bash
     echo -n 'olá, sejam bem vindo ao meu projeto API Orders' | openssl base64
    ```

-  Se usou a frase acima o resultado será sempre esse hash:

    ```bash
    b2zDoSwgc2VqYW0gYmVtIHZpbmRvIGFvIG1ldSBwcm9qZXRvIEFQSSBPcmRlcnM=
    ```

- Defina o hash como valor para a variável que pretende usar, nesse exemplo usei a JWT_SECRET

    ```bash
    export JWT_SECRET="b2zDoSwgc2VqYW0gYmVtIHZpbmRvIGFvIG1ldSBwcm9qZXRvIEFQSSBPcmRlcnM="
    ```

- Compilação: Entre no diretório que contém o arquivo pom.xml e gere o artefato .jar com o comando mvn do maven:

    ```bash
    cd orders/orders
    mvn clean package -DskipTests
    ```

- Execução: Ao término do comando será gerado o arquivo vendas-0.9.8-SNAPSHOT.jar dentro da pasta target. Na mesma sessão do shell Linux que usou para definir as variáveis de ambiente execute o comando para entrar no diretório target e execute a aplicação com o comando:

    ```bash
    cd target/
    java -jar orders-0.9.8-SNAPSHOT.jar
    ```

  - Também é possível usar o comando:
    ```bash
    mvn spring-boot:run
    ```

- Com isso você terá acesso ao http://localhost:8080/swagger-ui/index.html para usar todos os endpoints disponíveis.
- No endpoint /login você pode usar o login **user** com senha **123@UmaSenha**
- Após o acesso será fornecido o token e usando-o poderá criar novos usuários com senha contendo caracteres minúsculo, maiúsculo, número e ao menos um caractere especial.
- Use token gerado na etapa anterior para autorizar o acesso via ícone de cadeado com nome “Authorize” disponível no canto direito superior para que o mesmo token seja usado em todos os endpoints.

## :soon: Implementação futura
- Revogar tokens
- Bloquear usuarios

## :dart: Status do projeto

* Em andamento

## :framed_picture: Screnshoot
![Screenshot SWAGGER](https://github.com/EdsonSFreitas/orders/blob/main/orders/src/docs/SpringDoc-2023-10-16%2011-52-19.png)
