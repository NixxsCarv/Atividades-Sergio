package br.edu.unifio.studymatch.service;

import br.edu.unifio.studymatch.model.*;
import br.edu.unifio.studymatch.exception.*;
import br.edu.unifio.studymatch.repository.DisponibilidadeRepository;
import java.time.*;
public final class Regras {
  private Regras(){}
  public static void periodo(LocalTime inicio,LocalTime fim) {
    if(!inicio.isBefore(fim)) throw new RequisicaoInvalidaException("Hora inicial deve ser anterior à final");
  }
  public static void compatibilidade(Estudante aluno,GrupoEstudo grupo,DisponibilidadeRepository repo) {
    boolean materia=aluno.getDisciplinas().stream().anyMatch(d->d.getId().equals(grupo.getDisciplina().getId()));
    if(!materia) throw new ConflitoNegocioException("Estudante não estuda a disciplina do grupo");
    boolean horario=repo.findByEstudanteId(aluno.getId()).stream().anyMatch(d->
      d.getDiaSemana()==grupo.getDiaSemana() && !d.getHoraInicio().isAfter(grupo.getHoraInicio()) && !d.getHoraFim().isBefore(grupo.getHoraFim()));
    if(!horario) throw new ConflitoNegocioException("Estudante não possui disponibilidade compatível com o horário do grupo");
  }
  public static DiaSemana dia(LocalDateTime x) {return switch(x.getDayOfWeek()) {
    case MONDAY -> DiaSemana.SEGUNDA; case TUESDAY -> DiaSemana.TERCA; case WEDNESDAY -> DiaSemana.QUARTA;
    case THURSDAY -> DiaSemana.QUINTA; case FRIDAY -> DiaSemana.SEXTA; case SATURDAY -> DiaSemana.SABADO; case SUNDAY -> DiaSemana.DOMINGO;
  };}
}
