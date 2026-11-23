package br.edu.unifio.studymatch.service;

import br.edu.unifio.studymatch.exception.RequisicaoInvalidaException;
import br.edu.unifio.studymatch.model.DiaSemana;
import org.junit.jupiter.api.Test;
import java.time.LocalTime;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class RegrasTest {
  @Test void aceitaHorarioValido(){ assertDoesNotThrow(()->Regras.periodo(LocalTime.of(18,0),LocalTime.of(20,0))); }
  @Test void bloqueiaHorarioInvertido(){ assertThrows(RequisicaoInvalidaException.class,()->Regras.periodo(LocalTime.of(20,0),LocalTime.of(18,0))); }
  @Test void converteDiaDaSemana(){ assertEquals(DiaSemana.SEGUNDA,Regras.dia(LocalDateTime.of(2026,11,30,19,0))); }
}
