package br.edu.unifio.studymatch.dto;

import br.edu.unifio.studymatch.model.*;
import java.util.*;
import java.util.stream.Collectors;
public final class ApiView {
  private ApiView() {}
  public static Map<String,Object> of(Disciplina x) { return Map.of("id",x.getId(),"nome",x.getNome()); }
  public static Map<String,Object> of(Estudante x) {
    Map<String,Object> m = new LinkedHashMap<>(); m.put("id",x.getId()); m.put("nome",x.getNome()); m.put("email",x.getEmail()); m.put("objetivo",x.getObjetivo());
    m.put("disciplinaIds",x.getDisciplinas().stream().map(Disciplina::getId).sorted().collect(Collectors.toList())); return m;
  }
  public static Map<String,Object> of(Disponibilidade x) { return Map.of("id",x.getId(),"estudanteId",x.getEstudante().getId(),"diaSemana",x.getDiaSemana(),"horaInicio",x.getHoraInicio(),"horaFim",x.getHoraFim()); }
  public static Map<String,Object> of(GrupoEstudo x) { return Map.of("id",x.getId(),"nome",x.getNome(),"descricao",x.getDescricao(),"disciplinaId",x.getDisciplina().getId(),"administradorId",x.getAdministrador().getId(),"diaSemana",x.getDiaSemana(),"horaInicio",x.getHoraInicio(),"horaFim",x.getHoraFim(),"capacidade",x.getCapacidade()); }
  public static Map<String,Object> of(SolicitacaoEntrada x) { return Map.of("id",x.getId(),"grupoId",x.getGrupo().getId(),"estudanteId",x.getEstudante().getId(),"status",x.getStatus(),"criadaEm",x.getCriadaEm()); }
  public static Map<String,Object> of(Participacao x) { return Map.of("id",x.getId(),"grupoId",x.getGrupo().getId(),"estudanteId",x.getEstudante().getId(),"entrouEm",x.getEntrouEm()); }
  public static Map<String,Object> of(Encontro x) { return Map.of("id",x.getId(),"grupoId",x.getGrupo().getId(),"inicio",x.getInicio(),"fim",x.getFim(),"localDescricao",x.getLocalDescricao()); }
}
