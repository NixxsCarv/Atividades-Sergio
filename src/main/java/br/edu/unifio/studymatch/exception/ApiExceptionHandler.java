package br.edu.unifio.studymatch.exception;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.dao.DataIntegrityViolationException;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;
@RestControllerAdvice
public class ApiExceptionHandler {
  private ResponseEntity<Map<String,Object>> erro(HttpStatus status,String mensagem,HttpServletRequest request) {
    Map<String,Object> body=new LinkedHashMap<>(); body.put("timestamp",LocalDateTime.now()); body.put("status",status.value());
    body.put("error",status.getReasonPhrase()); body.put("mensagem",mensagem); body.put("caminho",request.getRequestURI());
    return ResponseEntity.status(status).body(body);
  }
  @ExceptionHandler(NaoEncontradoException.class)
  public ResponseEntity<Map<String,Object>> naoEncontrado(NaoEncontradoException ex,HttpServletRequest req) { return erro(HttpStatus.NOT_FOUND,ex.getMessage(),req); }
  @ExceptionHandler(ConflitoNegocioException.class)
  public ResponseEntity<Map<String,Object>> conflito(ConflitoNegocioException ex,HttpServletRequest req) { return erro(HttpStatus.CONFLICT,ex.getMessage(),req); }
  @ExceptionHandler(RequisicaoInvalidaException.class)
  public ResponseEntity<Map<String,Object>> invalido(RequisicaoInvalidaException ex,HttpServletRequest req) { return erro(HttpStatus.BAD_REQUEST,ex.getMessage(),req); }
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String,Object>> bean(MethodArgumentNotValidException ex,HttpServletRequest req) {
    String mensagem=ex.getBindingResult().getFieldErrors().stream().map(e->e.getField()+": "+e.getDefaultMessage()).distinct().reduce((a,b)->a+"; "+b).orElse("Dados inválidos");
    return erro(HttpStatus.BAD_REQUEST,mensagem,req);
  }
  @ExceptionHandler({HttpMessageNotReadableException.class,org.springframework.web.method.annotation.MethodArgumentTypeMismatchException.class})
  public ResponseEntity<Map<String,Object>> json(Exception ex,HttpServletRequest req) {return erro(HttpStatus.BAD_REQUEST,"JSON, formato, parâmetro ou enum inválido",req);}
  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<Map<String,Object>> constraint(DataIntegrityViolationException ex,HttpServletRequest req) {return erro(HttpStatus.CONFLICT,"Operação viola uma restrição do banco de dados",req);}
}
