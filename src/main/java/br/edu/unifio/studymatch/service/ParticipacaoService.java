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
public class ParticipacaoService {
  private final ParticipacaoRepository repo; private final SolicitacaoEntradaRepository solicitacoes;
  private final GrupoService grupos; private final EstudanteService estudantes; private final DisponibilidadeRepository horarios; private final AutorizacaoService autorizacao;
  @Transactional(readOnly=true) public List<Participacao> listar(Long grupoId){return grupoId==null?repo.findAll():repo.findByGrupoId(grupoId);}
  @Transactional(readOnly=true) public Participacao obter(Long id){return repo.findById(id).orElseThrow(()->new NaoEncontradoException("Participação",id));}
  public Participacao adicionarAprovado(Estudante e,GrupoEstudo g){
    if(repo.existsByGrupoIdAndEstudanteId(g.getId(),e.getId())) throw new ConflitoNegocioException("Estudante já participa do grupo");
    if(repo.countByGrupoId(g.getId())>=g.getCapacidade()) throw new ConflitoNegocioException("Grupo sem vagas disponíveis");
    Regras.compatibilidade(e,g,horarios);
    Participacao p=new Participacao();p.setEstudante(e);p.setGrupo(g);return repo.save(p);
  }
  public Participacao criar(ParticipacaoForm f){Estudante e=estudantes.obter(f.estudanteId());GrupoEstudo g=grupos.obter(f.grupoId());autorizacao.somenteAdministradorDoGrupo(g);
    if(!solicitacoes.existsByGrupoIdAndEstudanteIdAndStatus(g.getId(),e.getId(),StatusSolicitacao.APROVADA)) throw new ConflitoNegocioException("É preciso ter solicitação aprovada");
    return adicionarAprovado(e,g);
  }
  public Participacao atualizar(Long id,ParticipacaoForm f){Participacao atual=obter(id);autorizacao.somenteAdministradorDoGrupo(atual.getGrupo());
    if(atual.getEstudante().getId().equals(f.estudanteId())&&atual.getGrupo().getId().equals(f.grupoId())) return atual;
    Estudante e=estudantes.obter(f.estudanteId());GrupoEstudo g=grupos.obter(f.grupoId());autorizacao.somenteAdministradorDoGrupo(g);
    if(!solicitacoes.existsByGrupoIdAndEstudanteIdAndStatus(g.getId(),e.getId(),StatusSolicitacao.APROVADA)) throw new ConflitoNegocioException("Nova participação exige solicitação aprovada");
    if(repo.existsByGrupoIdAndEstudanteId(g.getId(),e.getId())) throw new ConflitoNegocioException("Já existe participação");
    if(repo.countByGrupoId(g.getId())>=g.getCapacidade()) throw new ConflitoNegocioException("Grupo sem vagas disponíveis");
    Regras.compatibilidade(e,g,horarios);atual.setGrupo(g);atual.setEstudante(e);return repo.save(atual);
  }
  public void excluir(Long id){Participacao p=obter(id);autorizacao.somenteAdministradorDoGrupo(p.getGrupo());repo.deleteById(id);}
}
