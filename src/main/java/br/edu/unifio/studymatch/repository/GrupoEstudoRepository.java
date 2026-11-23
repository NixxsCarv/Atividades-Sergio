package br.edu.unifio.studymatch.repository;

import br.edu.unifio.studymatch.model.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.*;
import java.time.*;
public interface GrupoEstudoRepository extends JpaRepository<GrupoEstudo,Long> {
List<GrupoEstudo> findByDisciplinaId(Long disciplinaId);
List<GrupoEstudo> findByDiaSemana(DiaSemana diaSemana);
List<GrupoEstudo> findByDisciplinaIdAndDiaSemana(Long disciplinaId,DiaSemana diaSemana);
boolean existsByDisciplinaId(Long disciplinaId);
boolean existsByAdministradorId(Long administradorId);
}
