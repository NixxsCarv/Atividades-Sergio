# StudyMatch — Backend REST (Projeto Integrador)

Backend acadêmico de uma plataforma para encontrar grupos de estudo compatíveis por disciplina e disponibilidade, enviar solicitações de entrada, administrar participações e agendar encontros.

## Identificação

| Campo | Informação |
| ----- | ---------- |
| **Aluno** | Nicolas Carvalho dos Reis |
| **Professor** | Sergio |
| **Curso** | Engenharia de Software |
| **Termo** | 4º Termo |
| **Atividade** | Trabalho Final, Segundo Bimestre (Projeto Integrador) |

## Sobre o projeto

O **StudyMatch** é uma API REST que resolve um problema comum entre estudantes: encontrar colegas para estudar juntos. A plataforma cruza as **disciplinas** e a **disponibilidade de horários** de cada estudante para sugerir grupos compatíveis, e permite todo o ciclo de vida de um grupo de estudo:

- Cadastro de estudantes, disciplinas e disponibilidades;
- Criação de grupos de estudo com horário e capacidade definidos;
- Envio de solicitações de entrada em grupos;
- Aprovação das solicitações, que gera a participação automaticamente;
- Agendamento de encontros vinculados ao grupo.

> **Origem / adaptação:** projeto-base disponibilizado pelo professor: [ecommerce-backend-4a](https://github.com/sergiodelfino/ecommerce-backend-4a/tree/main/ecommerce). Mantivemos o padrão de projeto Maven, Java 21, Spring Boot 4.1.1 e banco MySQL, substituindo o domínio de e-commerce por StudyMatch e acrescentando camadas, validações, regras de negócio e Basic Security. O projeto foi criado como **adaptação de estrutura e tecnologias**; não é um clone fiel do histórico Git do professor.

## Tecnologias

Java 21, Spring Boot 4.1.1, Spring Web MVC, Spring Data JPA, MySQL, Spring Security HTTP Basic, Hibernate Validator (Bean Validation), Lombok, Maven e Postman.

## Arquitetura

`Controller -> Service -> Repository -> MySQL`, com os pacotes `model` (entidades JPA), `dto` (requisições e visualização), `security` e `exception`.

## Entidades (7)

1. **Estudante** (N:M com Disciplina via `estudante_disciplinas`).
2. **Disciplina** (Matemática, Física etc.).
3. **Disponibilidade** (estudante, dia, início e fim).
4. **GrupoEstudo** (disciplina, estudante administrador, horário e capacidade).
5. **SolicitacaoEntrada** (estado de aprovação).
6. **Participacao** (vínculo aprovado entre estudante e grupo).
7. **Encontro** (agenda vinculada ao grupo).

Diagrama: `docs/modelo-dados.png` e código Mermaid em `docs/modelo-dados.mmd`.

## Principais regras de negócio

- Não é possível cadastrar e-mails ou disciplinas duplicados; horários e capacidade são validados.
- O grupo só pode ser criado com administrador compatível com a disciplina e o horário.
- A solicitação exige compatibilidade de matéria e horário, não permite repetição nem aluno que já participa do grupo.
- A aprovação cria a participação automaticamente; bloqueia grupo cheio e aprovação repetida.
- O encontro deve coincidir com o dia e horário do grupo e não pode sobrepor outro encontro.
- Registros com vínculos impeditivos não podem ser excluídos (retorna HTTP 409).

## Instalação no Windows

1. Instalar JDK 21, Maven e MySQL 8+ e verificar com `java -version` e `mvn -version`.
2. Criar o banco `studymatch` no MySQL ou usar o parâmetro `createDatabaseIfNotExist=true` (o usuário precisa de permissão).
3. Configurar as credenciais do banco e iniciar a aplicação.

   No PowerShell:

   ```powershell
   $env:DB_USER="root"
   $env:DB_PASSWORD="SUA_SENHA_LOCAL"
   mvn spring-boot:run
   ```

   No CMD:

   ```cmd
   set DB_USER=root
   set DB_PASSWORD=SUA_SENHA_LOCAL
   mvn spring-boot:run
   ```

4. A API inicia em `http://localhost:8080/api`.
5. Importar a coleção do Postman (`StudyMatch - PI - API REST.postman_collection.json`), conferir a variável `baseUrl` e configurar a autenticação HTTP Basic. **Executar os testes na ordem indicada.**
6. Testar com:

   ```bash
   curl -u admin@studymatch.test:Admin@1234 http://localhost:8080/api/disciplinas
   ```

### Usuários de teste (somente para demonstração)

| Usuário | Senha | Perfil |
| ------- | ----- | ------ |
| admin@studymatch.test | Admin@1234 | ADMIN (todos os endpoints) |
| aluno@studymatch.test | Aluno@1234 | ALUNO (consultas e solicitação de entrada) |

São credenciais *fictícias* codificadas para o ambiente didático. Em produção, não usar Basic sem HTTPS nem manter credenciais fixas em código.

## Testes e evidências

A coleção do Postman contém variáveis de teste e requisições organizadas por pasta (CRUD de cada entidade, fluxos de negócio e erros HTTP / regras de negócio). O roteiro completo está em `docs/roteiro-testes.md`.

## Documentação de endpoints

O arquivo `docs/documentacao-api.md` contém a lista completa de CRUD, filtros, ações e JSONs de exemplo.

## Limitações conscientes

O fluxo de cadastro dos estudantes representa o **domínio da aplicação**, enquanto o login HTTP Basic permanece independente, em memória (permitido pelo professor). As credenciais em memória usam o e-mail como username para vincular as ações à identidade dos estudantes cadastrados; a criação de credenciais não é dinâmica.

- A aprovação exige perfil ADMIN e que o login HTTP Basic corresponda ao e-mail do estudante administrador do grupo.
- As solicitações exigem login igual ao e-mail do estudante solicitante.
- Os estudantes de teste devem ser cadastrados com os e-mails `admin@studymatch.test` (Ana) e `aluno@studymatch.test` (Bruno).
- Projeto acadêmico com banco MySQL local e `ddl-auto=update`; para produção, usar Flyway, controle de acesso por identidade e HTTPS.

## Licença

Distribuído sob a licença GPL-3.0. Veja o arquivo [LICENSE](LICENSE).
