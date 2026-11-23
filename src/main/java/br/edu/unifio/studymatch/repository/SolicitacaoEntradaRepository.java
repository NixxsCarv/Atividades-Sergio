package br.edu.unifio.studymatch.repository;

import br.edu.unifio.studymatch.model.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.*;
import java.time.*;
public interface SolicitacaoEntradaRepository extends JpaRepository<SolicitacaoEntrada,Long> {
boolean existsByGrupoIdAndEstudanteId(Long grupoId,Long estudanteId);
boolean existsByGrupoId(Long grupoId);
boolean existsByEstudanteId(Long estudanteId);
boolean existsByGrupoIdAndEstudanteIdAndStatus(Long grupoId,Long estudanteId,StatusSolicitacao status);
List<SolicitacaoEntrada> findByGrupoId(Long grupoId);
List<SolicitacaoEntrada> findByStatus(StatusSolicitacao status);
}
