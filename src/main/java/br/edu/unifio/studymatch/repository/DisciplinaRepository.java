package br.edu.unifio.studymatch.repository;

import br.edu.unifio.studymatch.model.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.*;
import java.time.*;
public interface DisciplinaRepository extends JpaRepository<Disciplina,Long> {
boolean existsByNomeIgnoreCase(String nome);
}
