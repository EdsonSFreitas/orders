### API REST para pedidos de compras
1. Ambiente test usa banco H2 na memória, ambiente dev usa PostgreSQL local
2. Classe TestConfig popula banco para testes rápidos
3. Projeto usa tratamento de erro personalizado na resposta das requisições
4. Classe DTO usada para proteger atributos da classe User
5. Métodos relacionados aos conjuntos estão protegidos com tratamento para evitar null e getters retornam lista protegida contra modificações
6. TODO:
* Criar paginação/ordenação para método findAll() - OK
* Implementar OpenAPI/Feign SWAGGER
* Implementar JWT
* Implementar testes unitários
* Adicionar Orders
* Usar FlyWay para versionamento do banco


Respositório git da versão simplificada dessa API:
https://github.com/acenelio/workshop-springboot3-jpa/