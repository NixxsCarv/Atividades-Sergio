package br.edu.unifio.studymatch.repository;

import br.edu.unifio.studymatch.model.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.*;
import java.time.*;
public interface DisponibilidadeRepository extends JpaRepository<Disponibilidade,Long> {
List<Disponibilidade> findByEstudanteId(Long estudanteId);
boolean existsByEstudanteId(Long estudanteId);
}
