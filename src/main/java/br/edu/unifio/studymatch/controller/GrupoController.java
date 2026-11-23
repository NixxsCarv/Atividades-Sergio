package br.edu.unifio.studymatch.controller;

import br.edu.unifio.studymatch.model.*;
import br.edu.unifio.studymatch.dto.*;
import br.edu.unifio.studymatch.service.GrupoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import lombok.RequiredArgsConstructor;
import java.net.URI;
import java.util.*;
@RestController @RequestMapping("/api/grupos") @RequiredArgsConstructor
public class GrupoController {
  private final GrupoService svc;
  @GetMapping public List<Map<String,Object>> listar(@RequestParam(required=false) Long disciplinaId, @RequestParam(required=false) DiaSemana diaSemana){return svc.listar(disciplinaId,diaSemana).stream().map(ApiView::of).toList();}
  @GetMapping("/{id}") public Map<String,Object> obter(@PathVariable Long id){return ApiView.of(svc.obter(id));}
  @PostMapping public ResponseEntity<Map<String,Object>> criar(@Valid @RequestBody GrupoForm form){
    GrupoEstudo criado=svc.criar(form);
    return ResponseEntity.created(URI.create("/api/grupos/"+criado.getId())).body(ApiView.of(criado));
  }
  @PutMapping("/{id}") public Map<String,Object> atualizar(@PathVariable Long id,@Valid @RequestBody GrupoForm form){return ApiView.of(svc.atualizar(id,form));}
  @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void excluir(@PathVariable Long id){svc.excluir(id);}
  @GetMapping("/compativeis") public List<Map<String,Object>> compativeis(@RequestParam Long estudanteId){return svc.compativeis(estudanteId).stream().map(ApiView::of).toList();}
}
