# CRUD de Veículos — Spring Boot + JPA + MySQL + Thymeleaf

Atividade prática da UMC (Universidade de Mogi das Cruzes).
Tema: **Veículo** (modelo, placa, ano).

## Tecnologias
Java 17 · Spring Boot 3.4 · Spring MVC · Spring Data JPA · Thymeleaf · Bean Validation · MySQL (XAMPP)

## Arquitetura (5 camadas)
`Navegador → Controller → Service → Repository → MySQL` e retorno pela View (Thymeleaf).

```
src/main/java/com/umc/veiculos
├── controller   VeiculoController, HomeController
├── model        Veiculo (@Entity + validações)
├── repository   VeiculoRepository (JpaRepository + Query Method)
└── service      VeiculoService (regras de negócio)
src/main/resources
├── templates/veiculos   lista.html, form.html
├── static/css           estilo.css
└── application.properties
```

## Como executar

1. Abra o **XAMPP** e inicie **MySQL** e **Apache**.
2. No **phpMyAdmin** (http://localhost/phpmyadmin), crie o banco:
   ```sql
   CREATE DATABASE db_veiculos CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
   ```
3. (Opcional) Defina as variáveis de ambiente caso seu MySQL tenha senha:
   - Windows (PowerShell): `$env:DB_USER="root"; $env:DB_PASSWORD="sua_senha"`
   - Windows (permanente): `setx DB_PASSWORD "sua_senha"` (reabra o terminal/NetBeans)
   - Linux/macOS: `export DB_USER=root DB_PASSWORD=sua_senha`
   - NetBeans: Propriedades do projeto → Run → Environment
4. Execute: `mvn spring-boot:run` (ou rode `VeiculosApplication` pela IDE).
5. Acesse http://localhost:8080 — o Hibernate cria a tabela `veiculos` automaticamente.

## Rotas

| Método | Rota                    | Ação                          |
|--------|-------------------------|-------------------------------|
| GET    | `/veiculos`             | Lista (aceita `?busca=modelo`)|
| GET    | `/veiculos/novo`        | Formulário de cadastro        |
| POST   | `/veiculos/salvar`      | Salva (com validação) + redirect |
| GET    | `/veiculos/editar/{id}` | Formulário de edição          |
| POST   | `/veiculos/excluir/{id}`| Exclui + redirect             |

## Validações
- **Modelo:** obrigatório, de 2 a 60 caracteres
- **Placa:** obrigatória, formato `ABC-1234` ou `ABC1D23` (Mercosul), única
- **Ano:** obrigatório, entre 1900 e 2100

## Checkpoints
1. Banco, entidade e repository
2. Service e Controller
3. Views Thymeleaf, validação e CSS
4. GitHub, senha fora do código e conferência no phpMyAdmin
