package br.edu.unifio.studymatch.controller;

import br.edu.unifio.studymatch.model.*;
import br.edu.unifio.studymatch.dto.*;
import br.edu.unifio.studymatch.service.SolicitacaoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import lombok.RequiredArgsConstructor;
import java.net.URI;
import java.util.*;
@RestController @RequestMapping("/api/solicitacoes") @RequiredArgsConstructor
public class SolicitacaoController {
  private final SolicitacaoService svc;
  @GetMapping public List<Map<String,Object>> listar(@RequestParam(required=false) Long grupoId, @RequestParam(required=false) StatusSolicitacao status){return svc.listar(grupoId,status).stream().map(ApiView::of).toList();}
  @GetMapping("/{id}") public Map<String,Object> obter(@PathVariable Long id){return ApiView.of(svc.obter(id));}
  @PostMapping public ResponseEntity<Map<String,Object>> criar(@Valid @RequestBody SolicitacaoForm form){
    SolicitacaoEntrada criado=svc.criar(form);
    return ResponseEntity.created(URI.create("/api/solicitacoes/"+criado.getId())).body(ApiView.of(criado));
  }
  @PutMapping("/{id}") public Map<String,Object> atualizar(@PathVariable Long id,@Valid @RequestBody SolicitacaoForm form){return ApiView.of(svc.atualizar(id,form));}
  @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void excluir(@PathVariable Long id){svc.excluir(id);}
  @PostMapping("/{id}/aprovar") public Map<String,Object> aprovar(@PathVariable Long id){return ApiView.of(svc.aprovar(id));}
@PostMapping("/{id}/rejeitar") public Map<String,Object> rejeitar(@PathVariable Long id){return ApiView.of(svc.rejeitar(id));}
}
