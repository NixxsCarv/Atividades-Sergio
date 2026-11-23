package br.edu.unifio.studymatch.service;

import br.edu.unifio.studymatch.repository.*;
import br.edu.unifio.studymatch.model.*;
import br.edu.unifio.studymatch.dto.*;
import br.edu.unifio.studymatch.exception.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import java.util.*;
import java.util.stream.Collectors;
@Service @RequiredArgsConstructor @Transactional
public class EstudanteService {
  private final EstudanteRepository repo; private final DisciplinaRepository disciplinas; private final GrupoEstudoRepository grupos;
  private final DisponibilidadeRepository horarios; private final ParticipacaoRepository membros; private final SolicitacaoEntradaRepository solicitacoes;
  @Transactional(readOnly=true) public List<Estudante> listar(){return repo.findAll();}
  @Transactional(readOnly=true) public Estudante obter(Long id){return repo.findById(id).orElseThrow(()->new NaoEncontradoException("Estudante",id));}
  private void aplicar(Estudante e,EstudanteForm f){e.setNome(f.nome().trim()); e.setEmail(f.email().trim().toLowerCase(Locale.ROOT)); e.setObjetivo(f.objetivo().trim());
    Set<Disciplina> ds=new HashSet<>(); for(Long id:f.disciplinaIds()) ds.add(disciplinas.findById(id).orElseThrow(()->new NaoEncontradoException("Disciplina",id))); e.setDisciplinas(ds);}
  public Estudante criar(EstudanteForm f){if(repo.existsByEmailIgnoreCase(f.email().trim())) throw new ConflitoNegocioException("E-mail já cadastrado"); Estudante e=new Estudante(); aplicar(e,f); return repo.save(e);}
  public Estudante atualizar(Long id,EstudanteForm f){Estudante e=obter(id); if(!e.getEmail().equalsIgnoreCase(f.email().trim())&&repo.existsByEmailIgnoreCase(f.email().trim())) throw new ConflitoNegocioException("E-mail já cadastrado"); aplicar(e,f); return repo.save(e);}
  public void excluir(Long id){obter(id); if(grupos.existsByAdministradorId(id)||horarios.existsByEstudanteId(id)||membros.existsByEstudanteId(id)||solicitacoes.existsByEstudanteId(id)) throw new ConflitoNegocioException("Exclua primeiro os vínculos do estudante"); repo.deleteById(id);}
}
