package br.edu.unifio.studymatch.service;

import br.edu.unifio.studymatch.repository.*;
import br.edu.unifio.studymatch.model.*;
import br.edu.unifio.studymatch.dto.*;
import br.edu.unifio.studymatch.exception.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import java.util.*;
@Service @RequiredArgsConstructor @Transactional
public class GrupoService {
  private final GrupoEstudoRepository repo; private final DisciplinaService disciplinas; private final EstudanteService estudantes;
  private final DisponibilidadeRepository horarios; private final ParticipacaoRepository participacoes;
  private final SolicitacaoEntradaRepository solicitacoes; private final EncontroRepository encontros; private final AutorizacaoService autorizacao;
  @Transactional(readOnly=true) public List<GrupoEstudo> listar(Long disciplinaId,DiaSemana diaSemana){
    if(disciplinaId!=null&&diaSemana!=null) return repo.findByDisciplinaIdAndDiaSemana(disciplinaId,diaSemana);
    if(disciplinaId!=null) return repo.findByDisciplinaId(disciplinaId);
    if(diaSemana!=null) return repo.findByDiaSemana(diaSemana); return repo.findAll();
  }
  @Transactional(readOnly=true) public GrupoEstudo obter(Long id){return repo.findById(id).orElseThrow(()->new NaoEncontradoException("Grupo",id));}
  private void aplicar(GrupoEstudo g,GrupoForm f){Regras.periodo(f.horaInicio(),f.horaFim());
    g.setNome(f.nome().trim());g.setDescricao(f.descricao().trim());g.setDisciplina(disciplinas.obter(f.disciplinaId()));
    g.setAdministrador(estudantes.obter(f.administradorId()));g.setDiaSemana(f.diaSemana());g.setHoraInicio(f.horaInicio());g.setHoraFim(f.horaFim());g.setCapacidade(f.capacidade());
    Regras.compatibilidade(g.getAdministrador(),g,horarios);
  }
  public GrupoEstudo criar(GrupoForm f){GrupoEstudo g=new GrupoEstudo();aplicar(g,f);autorizacao.somenteAdministradorDoGrupo(g);return repo.save(g);}
  public GrupoEstudo atualizar(Long id,GrupoForm f){GrupoEstudo g=obter(id);autorizacao.somenteAdministradorDoGrupo(g);
    if((participacoes.existsByGrupoId(id)||solicitacoes.existsByGrupoId(id)||encontros.existsByGrupoId(id)) &&
      (!g.getDisciplina().getId().equals(f.disciplinaId())||g.getDiaSemana()!=f.diaSemana()||!g.getHoraInicio().equals(f.horaInicio())||!g.getHoraFim().equals(f.horaFim())||!g.getAdministrador().getId().equals(f.administradorId())))
      throw new ConflitoNegocioException("Não altere matéria, horário ou administrador enquanto houver vínculos");
    if(participacoes.countByGrupoId(id)>f.capacidade()) throw new ConflitoNegocioException("Capacidade deve ser maior que o número atual de membros");
    aplicar(g,f);return repo.save(g);
  }
  public void excluir(Long id){GrupoEstudo g=obter(id);autorizacao.somenteAdministradorDoGrupo(g);if(participacoes.existsByGrupoId(id)||solicitacoes.existsByGrupoId(id)||encontros.existsByGrupoId(id)) throw new ConflitoNegocioException("Grupo possui solicitações, participantes ou encontros");repo.deleteById(id);}
  @Transactional(readOnly=true) public List<GrupoEstudo> compativeis(Long estudanteId){Estudante e=estudantes.obter(estudanteId);
    return repo.findAll().stream().filter(g -> participacoes.countByGrupoId(g.getId())<g.getCapacidade())
      .filter(g->!participacoes.existsByGrupoIdAndEstudanteId(g.getId(),estudanteId))
      .filter(g->{try{Regras.compatibilidade(e,g,horarios);return true;}catch(ConflitoNegocioException ex){return false;}}).toList();
  }
}
