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
public class EncontroService {
  private final EncontroRepository repo; private final GrupoService grupos; private final AutorizacaoService autorizacao;
  @Transactional(readOnly=true) public List<Encontro> listar(Long grupoId){return grupoId==null?repo.findAll():repo.findByGrupoIdOrderByInicioAsc(grupoId);}
  @Transactional(readOnly=true) public Encontro obter(Long id){return repo.findById(id).orElseThrow(()->new NaoEncontradoException("Encontro",id));}
  private void aplicar(Encontro e,EncontroForm f,Long ignorarId){GrupoEstudo g=grupos.obter(f.grupoId());autorizacao.somenteAdministradorDoGrupo(g);
    if(!f.inicio().isBefore(f.fim())) throw new RequisicaoInvalidaException("Início do encontro deve ser anterior ao fim");
    if(!f.inicio().toLocalDate().equals(f.fim().toLocalDate())) throw new RequisicaoInvalidaException("O encontro deve iniciar e terminar no mesmo dia");
    if(Regras.dia(f.inicio())!=g.getDiaSemana()) throw new ConflitoNegocioException("Data do encontro não corresponde ao dia semanal do grupo");
    if(f.inicio().toLocalTime().isBefore(g.getHoraInicio())||f.fim().toLocalTime().isAfter(g.getHoraFim())) throw new ConflitoNegocioException("Encontro fora do horário do grupo");
    if(repo.contarConflitos(g.getId(),f.inicio(),f.fim(),ignorarId)>0) throw new ConflitoNegocioException("Já existe encontro neste horário");
    e.setGrupo(g);e.setInicio(f.inicio());e.setFim(f.fim());e.setLocalDescricao(f.localDescricao().trim());
  }
  public Encontro criar(EncontroForm f){Encontro e=new Encontro();aplicar(e,f,null);return repo.save(e);}
  public Encontro atualizar(Long id,EncontroForm f){Encontro e=obter(id);autorizacao.somenteAdministradorDoGrupo(e.getGrupo());aplicar(e,f,id);return repo.save(e);}
  public void excluir(Long id){Encontro e=obter(id);autorizacao.somenteAdministradorDoGrupo(e.getGrupo());repo.deleteById(id);}
}
