# Como o StudyMatch adapta o projeto de e-commerce do professor

**Repositório de referência:** https://github.com/sergiodelfino/ecommerce-backend-4a/tree/main/ecommerce

A estrutura Maven, pacote acadêmico `br.edu.unifio`, Spring Boot **4.1.1** e **Java 21** foram mantidos como referência. Em vez de copiar o domínio comercial, a aplicação passa a representar a realidade dos grupos de estudo.

| Conceito do exemplo de e-commerce | Adaptação para o PI StudyMatch |
|---|---|
| Entidade persistida com `@Entity` | Estudante, Disciplina, Disponibilidade, GrupoEstudo, SolicitacaoEntrada, Participacao, Encontro |
| Repository Spring Data JPA | Um Repository por entidade e consultas por matéria/dia, estudante e grupo |
| Acesso por endpoint REST | Controllers próprios com CRUD, filtros e ações de aprovação |
| Regras de operações comerciais | Compatibilidade de horário/matéria, limite de vagas, aprovação e encontros |
| MySQL local | Banco `studymatch` configurado em `application.properties` |

**Dependências adicionadas no `pom.xml`:** Spring Web MVC para API HTTP, Validation para `@Valid`/Bean Validation e Spring Security para HTTP Basic, além das dependências JPA, MySQL, Lombok e testes. A configuração foi escrita para **não copiar as credenciais** presentes no exemplo do professor: usa `DB_USER` e `DB_PASSWORD`.

**Atenção:** o ZIP fornecido é uma implementação acadêmica reconstruída e organizada com as mesmas tecnologias, não um fork Git da base original. Para documentar a origem em um GitHub com histórico, uma opção é criar um fork do repositório-base, aplicar os arquivos StudyMatch e registrar os commits de adaptação. Antes da avaliação, confira se a interpretação do professor exige isso, além de citar a origem no README.
