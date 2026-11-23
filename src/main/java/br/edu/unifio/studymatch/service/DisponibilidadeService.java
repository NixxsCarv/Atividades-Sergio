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
public class DisponibilidadeService {
  private final DisponibilidadeRepository repo; private final EstudanteService alunos;
  @Transactional(readOnly=true) public List<Disponibilidade> listar(Long estudanteId){return estudanteId==null?repo.findAll():repo.findByEstudanteId(estudanteId);}
  @Transactional(readOnly=true) public Disponibilidade obter(Long id){return repo.findById(id).orElseThrow(()->new NaoEncontradoException("Disponibilidade",id));}
  private void aplicar(Disponibilidade x,DisponibilidadeForm f){Regras.periodo(f.horaInicio(),f.horaFim()); x.setEstudante(alunos.obter(f.estudanteId()));x.setDiaSemana(f.diaSemana());x.setHoraInicio(f.horaInicio());x.setHoraFim(f.horaFim());}
  public Disponibilidade criar(DisponibilidadeForm f){Disponibilidade x=new Disponibilidade(); aplicar(x,f);return repo.save(x);}
  public Disponibilidade atualizar(Long id,DisponibilidadeForm f){Disponibilidade x=obter(id);aplicar(x,f);return repo.save(x);}
  public void excluir(Long id){obter(id);repo.deleteById(id);}
}
