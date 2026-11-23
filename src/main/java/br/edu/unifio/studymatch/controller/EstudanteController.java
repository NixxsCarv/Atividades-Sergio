package br.edu.unifio.studymatch.controller;

import br.edu.unifio.studymatch.model.*;
import br.edu.unifio.studymatch.dto.*;
import br.edu.unifio.studymatch.service.EstudanteService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import lombok.RequiredArgsConstructor;
import java.net.URI;
import java.util.*;
@RestController @RequestMapping("/api/estudantes") @RequiredArgsConstructor
public class EstudanteController {
  private final EstudanteService svc;
  @GetMapping public List<Map<String,Object>> listar(){return svc.listar().stream().map(ApiView::of).toList();}
  @GetMapping("/{id}") public Map<String,Object> obter(@PathVariable Long id){return ApiView.of(svc.obter(id));}
  @PostMapping public ResponseEntity<Map<String,Object>> criar(@Valid @RequestBody EstudanteForm form){
    Estudante criado=svc.criar(form);
    return ResponseEntity.created(URI.create("/api/estudantes/"+criado.getId())).body(ApiView.of(criado));
  }
  @PutMapping("/{id}") public Map<String,Object> atualizar(@PathVariable Long id,@Valid @RequestBody EstudanteForm form){return ApiView.of(svc.atualizar(id,form));}
  @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void excluir(@PathVariable Long id){svc.excluir(id);}

}
