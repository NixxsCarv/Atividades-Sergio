package br.edu.unifio.studymatch.service;

import br.edu.unifio.studymatch.model.Estudante;
import br.edu.unifio.studymatch.model.GrupoEstudo;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AutorizacaoService {
  public void somenteProprioEstudante(Estudante e) {
    String autenticado = SecurityContextHolder.getContext().getAuthentication().getName();
    if(!e.getEmail().equalsIgnoreCase(autenticado))
      throw new AccessDeniedException("A conta autenticada não corresponde ao estudante indicado");
  }
  public void somenteAdministradorDoGrupo(GrupoEstudo g) {
    String autenticado = SecurityContextHolder.getContext().getAuthentication().getName();
    if(!g.getAdministrador().getEmail().equalsIgnoreCase(autenticado))
      throw new AccessDeniedException("Apenas o administrador deste grupo pode executar a operação");
  }
}
