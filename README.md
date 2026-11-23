# StudyMatch — Backend REST (Projeto Integrador)

Backend acadêmico de uma plataforma para encontrar grupos de estudo compatíveis por disciplina e disponibilidade, enviar solicitações de entrada, administrar participações e agendar encontros.

> **Origem / adaptação:** projeto-base disponibilizado pelo professor: https://github.com/sergiodelfino/ecommerce-backend-4a/tree/main/ecommerce . Mantivemos o padrão de projeto Maven, Java 21, Spring Boot 4.1.1 e banco MySQL, substituindo o domínio de e-commerce por StudyMatch e acrescentando camadas, validações, regras e Basic Security. O projeto foi criado como **adaptação de estrutura e tecnologias**; não é um clone fiel do histórico Git do professor.

## Tecnologias

Java 21, Spring Boot 4.1.1, Spring Web MVC, Spring Data JPA, MySQL, Spring Security HTTP Basic, Hibernate Validator (Bean Validation), Lombok, Maven, Postman.

## Instalação no Windows

1. Instalar JDK 21, Maven e MySQL 8+ e verificar com `java -version` e `mvn -version`.
2. Criar o banco `studymatch` no MySQL ou usar o parâmetro `createDatabaseIfNotExist=true` (o usuário precisa de permissão).
3. Configurar credenciais do banco: no PowerShell, antes de iniciar:
   ```powershell
   $env:DB_USER="root"
   $env:DB_PASSWORD="SUA_SENHA_LOCAL"
   mvn spring-boot:run
   ```
   No CMD, use `set DB_USER=root`, `set DB_PASSWORD=SUA_SENHA_LOCAL` e `mvn spring-boot:run`.
4. A API inicia em `http://localhost:8080/api`.
5. Importar a coleção `postman/projeto-integrador.postman_collection.json` no Postman, conferir `baseUrl`, configurar a autenticação HTTP Basic para cada pasta/coleção ou usar as requisições de demonstração. **Rodar os testes na ordem indicada**.
6. Testar com:
   ```bash
   curl -u admin@studymatch.test:Admin@1234 http://localhost:8080/api/disciplinas
   ```

### Usuários de teste (somente para demonstração)
| Usuário | Senha | Perfil |
|---|---|---|
| admin@studymatch.test | Admin@1234 | ADMIN (todos os endpoints) |
| aluno@studymatch.test | Aluno@1234 | ALUNO (consultas e solicitação de entrada) |

São credenciais *fictícias* codificadas para o ambiente didático. Para produção, não usar Basic sem HTTPS e não manter credenciais fixas em código.

## Arquitetura

`Controller -> Service -> Repository -> MySQL`, com `model` (JPA), `dto` (requisições e visualização), `security` e `exception`.

## Entidades (7)

1. Estudante (N:M Disciplina via `estudante_disciplinas`).
2. Disciplina (Matemática, Física etc.).
3. Disponibilidade (estudante, dia, início e fim).
4. GrupoEstudo (disciplina, estudante administrador, horário e capacidade).
5. SolicitacaoEntrada (estado de aprovação).
6. Participacao (vínculo aprovado entre estudante e grupo).
7. Encontro (agenda vinculada ao grupo).

Diagrama: `docs/modelo-dados.png` e código Mermaid em `docs/modelo-dados.mmd`.

## Principais regras

- Não cadastrar e-mails/disciplinas duplicados; validar horários e capacidade.
- Grupo somente pode ser criado com administrador compatível com disciplina e horário.
- Solicitação exige compatibilidade de matéria e horário, não permite repetição nem aluno já participante.
- Aprovação cria participação automaticamente; bloqueia grupo cheio e aprovação repetida.
- Encontro deve coincidir com dia/horário do grupo e não sobrepor outro encontro.
- Não excluir registros que possuem vínculos impeditivos, gerando HTTP 409.

## Testes/evidências

Coleção Postman: `postman/projeto-integrador.postman_collection.json` (inclui variáveis de teste e requisições organizadas). **Os exemplos de resposta são previstos, não foram capturados de uma execução real**. Após executar com MySQL no computador do grupo, usar `Save Response > Save as example` nas respostas e exportar novamente a coleção com variáveis compartilhadas antes da entrega. Ver `docs/roteiro-testes.md`.

## Documentação de endpoints

`docs/documentacao-api.md` contém lista completa de CRUD, filtros, ações e JSONs de exemplo.

## Integrantes do grupo — preencher antes da entrega

| Nome completo | ID acadêmico |
|---|---|
| PREENCHER | PREENCHER |
| PREENCHER | PREENCHER |

## Entrega

Publicar o conteúdo desta pasta em um repositório GitHub público/acessível e entregar o URL no Ambiente Conhecer, atividade **Projeto Integrador**.

## Limitações conscientes

O fluxo de cadastro dos estudantes representa o **domínio da aplicação**, enquanto o login HTTP Basic permanece independente em memória (permitido pelo professor). As credenciais em memória usam o e-mail como username para vincular ações à identidade dos estudantes cadastrados; a criação de credenciais não é dinâmica. Aprovação exige perfil ADMIN e que o login HTTP Basic corresponda ao e-mail do estudante administrador do grupo. Solicitações exigem login igual ao e-mail do estudante solicitante. Os estudantes de teste devem ser cadastrados com os e-mails `admin@studymatch.test` (Ana) e `aluno@studymatch.test` (Bruno). Projeto acadêmico com banco MySQL local e migração `ddl-auto=update`; para produção usar Flyway, controle de acesso por identidade e HTTPS.

**Validação:** projeto gerado sem acesso local a Maven/MySQL; compilar, executar e capturar respostas reais no ambiente do grupo antes da entrega.
