# Roteiro para testes manuais no Postman

Importe `postman/projeto-integrador.postman_collection.json`. O Postman deve ter `baseUrl=http://localhost:8080`.

## Cenário de ponta a ponta (na ordem)

1. POST disciplina Matemática usando ADMIN — **201**, guardar `id` (inicialmente 1 em base vazia).
2. POST estudante Ana (com disciplina 1) — **201**, guardar ID.
3. POST estudante Bruno (com disciplina 1) — **201**, guardar ID.
4. POST disponibilidade Ana, segunda 18h–21h — **201**.
5. POST disponibilidade Bruno, segunda 18h–21h — **201**.
6. POST grupo de matemática com Ana administradora, segunda 19h–20h e capacidade 2 — **201**.
7. GET /grupos?disciplinaId=1&diaSemana=SEGUNDA — **200** lista o grupo.
8. GET /grupos/compativeis?estudanteId=2 — **200** lista o grupo.
9. POST solicitação de Bruno com Basic ALUNO — **201** pendente.
10. POST /solicitacoes/1/aprovar com ADMIN — **200** e `status=APROVADA`; participação criada.
11. GET /participacoes?grupoId=1 — **200** contém Bruno.
12. POST encontro com ADMIN, dia 30/11/2026 segunda 19–20 — **201**.
13. GET /encontros?grupoId=1 — **200** mostra agenda.
14. POST mesma solicitação de Bruno — **409**.
15. POST disponibilidade inválida (início 21:00, fim 18:00) — **400**.
16. GET /disciplinas/999999 — **404**.
17. GET sem HTTP Basic — **401**.
18. POST disciplina usando ALUNO — **403**.
19. Testar PUT/DELETE de cada recurso (em registros de teste isolados, sem vínculos) e confirmar 200/204.
20. Testar criação de encontro sobreposto (enquanto ainda existe anterior) — **409**.

O Postman exportado **não contém evidência real nesta geração**. Para evidenciar: execute cada requisição, abra o menu `Save Response` e `Save as example`; inspecione as variáveis compartilhadas e exporte a coleção novamente. Grave capturas se o professor solicitar.

Se a base não estiver vazia, os IDs podem não ser 1 e 2: altere as variáveis da coleção e o corpo das requisições.
