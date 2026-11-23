# API REST StudyMatch

Base: `http://localhost:8080/api` — todas as rotas exigem HTTP Basic.

## CRUD

| Recurso | GET lista | GET ID | POST | PUT ID | DELETE ID |
|---|---|---|---|---|---|
| Disciplinas | `/disciplinas` | `/disciplinas/{id}` | `/disciplinas` | `/disciplinas/{id}` | `/disciplinas/{id}` |
| Estudantes | `/estudantes` | `/estudantes/{id}` | `/estudantes` | `/estudantes/{id}` | `/estudantes/{id}` |
| Disponibilidades | `/disponibilidades` | `/disponibilidades/{id}` | `/disponibilidades` | `/disponibilidades/{id}` | `/disponibilidades/{id}` |
| Grupos | `/grupos` | `/grupos/{id}` | `/grupos` | `/grupos/{id}` | `/grupos/{id}` |
| Solicitações | `/solicitacoes` | `/solicitacoes/{id}` | `/solicitacoes` | `/solicitacoes/{id}` | `/solicitacoes/{id}` |
| Participações | `/participacoes` | `/participacoes/{id}` | `/participacoes` | `/participacoes/{id}` | `/participacoes/{id}` |
| Encontros | `/encontros` | `/encontros/{id}` | `/encontros` | `/encontros/{id}` | `/encontros/{id}` |

**Status REST:** POST 201 + Location; GET/PUT 200; DELETE 204. Bean Validation: 400. Registro não encontrado: 404. Conflito de negócio: 409. Sem Basic: 401. ALUNO tentando escrita administrativa: 403.

**Autorização:** todos os GETs requerem login. `POST /solicitacoes` pode ser realizado pelo ALUNO ou ADMIN. Demais POSTs, PUTs, DELETEs e ações de aprovação/rejeição requerem ADMIN.

## Consultas personalizadas
- `GET /grupos?disciplinaId=1&diaSemana=SEGUNDA` — consulta derivada Spring Data JPA (também aceita cada filtro individualmente).
- `GET /disponibilidades?estudanteId=2` — filtro por estudante.
- `GET /solicitacoes?status=PENDENTE` e `GET /solicitacoes?grupoId=1` — consultas específicas.
- `GET /encontros?grupoId=1` — agenda por grupo, ordenada por data.

## Funcionalidade específica
1. Admin cadastra estudante e suas disciplinas e disponibilidades.
2. Admin cria grupo com disciplina e horário (o administrador deve possuir matéria e disponibilidade compatíveis).
3. `GET /grupos/compativeis?estudanteId=2` mostra grupos com mesma matéria, horário e vagas.
4. ALUNO faz `POST /solicitacoes` com `{ "estudanteId":2, "grupoId":1 }`.
5. ADMIN faz `POST /solicitacoes/1/aprovar`; **a API cria uma Participacao automaticamente**, respeitando vagas e compatibilidade.
6. Admin pode agendar `POST /encontros` e todos podem consultar `GET /encontros?grupoId=1`.

**Regras:** não solicitar duas vezes; não aprovar duas vezes; não aceitar mais membros que vagas; não aceitar alunos sem matéria ou horário compatível; não permitir encontro fora do horário, dia ou sobreposto. Admin do grupo (propriedade de domínio) é um estudante; as credenciais de teste são usuários técnicos em memória com papéis ADMIN e ALUNO. A aprovação exige papel ADMIN e login HTTP Basic igual ao e-mail do estudante administrador do grupo. A solicitação exige login correspondente ao e-mail do estudante solicitante.

## Exemplos de JSON

### POST /disciplinas
```json
{"nome":"Matemática"}
```

### POST /estudantes
```json
{"nome":"Ana Silva","email":"admin@studymatch.test","objetivo":"Estudar para o vestibular","disciplinaIds":[1]}
```

### POST /disponibilidades
```json
{"estudanteId":1,"diaSemana":"SEGUNDA","horaInicio":"18:00","horaFim":"21:00"}
```

### POST /grupos
```json
{"nome":"Revisão de Matemática","descricao":"Grupo semanal de exercícios","disciplinaId":1,"administradorId":1,"diaSemana":"SEGUNDA","horaInicio":"19:00","horaFim":"20:00","capacidade":5}
```

### POST /solicitacoes
```json
{"estudanteId":2,"grupoId":1}
```

### POST /participacoes (apenas para solicitação já aprovada)
```json
{"estudanteId":2,"grupoId":1}
```

### POST /encontros (a data deve cair no dia semanal do grupo)
```json
{"grupoId":1,"inicio":"2026-11-30T19:00:00","fim":"2026-11-30T20:00:00","localDescricao":"Biblioteca central"}
```

**Nota:** `horaInicio` e `horaFim` usam HH:mm; `inicio` e `fim` usam ISO-8601 sem fuso. O backend não cria usuários reais por cadastro público, porque o enunciado permite credenciais HTTP Basic em memória.
