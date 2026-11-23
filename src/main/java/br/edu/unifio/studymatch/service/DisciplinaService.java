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
public class DisciplinaService {
  private final DisciplinaRepository repo; private final EstudanteRepository estudantes; private final GrupoEstudoRepository grupos;
  @Transactional(readOnly=true) public List<Disciplina> listar(){return repo.findAll();}
  @Transactional(readOnly=true) public Disciplina obter(Long id){return repo.findById(id).orElseThrow(()->new NaoEncontradoException("Disciplina",id));}
  public Disciplina criar(DisciplinaForm f){ if(repo.existsByNomeIgnoreCase(f.nome())) throw new ConflitoNegocioException("Disciplina já cadastrada");
    Disciplina d=new Disciplina(); d.setNome(f.nome().trim()); return repo.save(d); }
  public Disciplina atualizar(Long id,DisciplinaForm f){ Disciplina d=obter(id); if(!d.getNome().equalsIgnoreCase(f.nome()) && repo.existsByNomeIgnoreCase(f.nome())) throw new ConflitoNegocioException("Disciplina já cadastrada"); d.setNome(f.nome().trim()); return repo.save(d); }
  public void excluir(Long id){obter(id); if(estudantes.existsByDisciplinas_Id(id)||grupos.existsByDisciplinaId(id)) throw new ConflitoNegocioException("Disciplina vinculada a estudantes ou grupos"); repo.deleteById(id);}
}
