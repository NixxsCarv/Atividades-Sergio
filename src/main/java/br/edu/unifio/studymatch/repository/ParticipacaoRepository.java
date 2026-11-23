package br.edu.unifio.studymatch.repository;

import br.edu.unifio.studymatch.model.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.*;
import java.time.*;
public interface ParticipacaoRepository extends JpaRepository<Participacao,Long> {
boolean existsByGrupoIdAndEstudanteId(Long grupoId, Long estudanteId);
boolean existsByGrupoId(Long grupoId);
boolean existsByEstudanteId(Long estudanteId);
long countByGrupoId(Long grupoId);
List<Participacao> findByGrupoId(Long grupoId);
}
