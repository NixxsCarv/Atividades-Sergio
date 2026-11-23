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
public class SolicitacaoService {
  private final SolicitacaoEntradaRepository repo; private final GrupoService grupos; private final EstudanteService estudantes;
  private final DisponibilidadeRepository horarios; private final ParticipacaoRepository participacoes; private final ParticipacaoService participantes; private final AutorizacaoService autorizacao;
  @Transactional(readOnly=true) public List<SolicitacaoEntrada> listar(Long grupoId,StatusSolicitacao status){
    if(grupoId!=null) return repo.findByGrupoId(grupoId).stream().filter(s->status==null||s.getStatus()==status).toList();
    return status!=null?repo.findByStatus(status):repo.findAll();
  }
  @Transactional(readOnly=true) public SolicitacaoEntrada obter(Long id){return repo.findById(id).orElseThrow(()->new NaoEncontradoException("Solicitação",id));}
  private void preencher(SolicitacaoEntrada s,SolicitacaoForm f){Estudante e=estudantes.obter(f.estudanteId());GrupoEstudo g=grupos.obter(f.grupoId());
    if(repo.existsByGrupoIdAndEstudanteId(g.getId(),e.getId())) throw new ConflitoNegocioException("Solicitação duplicada: já existe pedido para esse grupo");
    if(participacoes.existsByGrupoIdAndEstudanteId(g.getId(),e.getId())) throw new ConflitoNegocioException("Estudante já participa do grupo");
    if(g.getAdministrador().getId().equals(e.getId())) throw new ConflitoNegocioException("Administrador já gerencia o próprio grupo");
    autorizacao.somenteProprioEstudante(e);Regras.compatibilidade(e,g,horarios);s.setEstudante(e);s.setGrupo(g);
  }
  public SolicitacaoEntrada criar(SolicitacaoForm f){SolicitacaoEntrada s=new SolicitacaoEntrada();preencher(s,f);return repo.save(s);}
  public SolicitacaoEntrada atualizar(Long id,SolicitacaoForm f){SolicitacaoEntrada s=obter(id);
    if(s.getStatus()!=StatusSolicitacao.PENDENTE) throw new ConflitoNegocioException("Somente solicitações pendentes podem ser alteradas");
    if(s.getEstudante().getId().equals(f.estudanteId())&&s.getGrupo().getId().equals(f.grupoId())) return s;
    preencher(s,f);return repo.save(s);
  }
  public SolicitacaoEntrada aprovar(Long id){SolicitacaoEntrada s=obter(id);autorizacao.somenteAdministradorDoGrupo(s.getGrupo());
    if(s.getStatus()!=StatusSolicitacao.PENDENTE) throw new ConflitoNegocioException("Solicitação já analisada");
    participantes.adicionarAprovado(s.getEstudante(),s.getGrupo());s.setStatus(StatusSolicitacao.APROVADA);return repo.save(s);
  }
  public SolicitacaoEntrada rejeitar(Long id){SolicitacaoEntrada s=obter(id);autorizacao.somenteAdministradorDoGrupo(s.getGrupo());
    if(s.getStatus()!=StatusSolicitacao.PENDENTE) throw new ConflitoNegocioException("Solicitação já analisada");s.setStatus(StatusSolicitacao.REJEITADA);return repo.save(s);
  }
  public void excluir(Long id){SolicitacaoEntrada s=obter(id);autorizacao.somenteAdministradorDoGrupo(s.getGrupo());
    if(s.getStatus()==StatusSolicitacao.APROVADA && participacoes.existsByGrupoIdAndEstudanteId(s.getGrupo().getId(),s.getEstudante().getId()))
      throw new ConflitoNegocioException("Exclua antes a participação vinculada à solicitação aprovada");
    repo.deleteById(id);
  }
}
