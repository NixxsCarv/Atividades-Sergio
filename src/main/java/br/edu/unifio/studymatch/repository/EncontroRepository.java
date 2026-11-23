package br.edu.unifio.studymatch.repository;

import br.edu.unifio.studymatch.model.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.*;
import java.time.*;
public interface EncontroRepository extends JpaRepository<Encontro,Long> {
boolean existsByGrupoId(Long grupoId);
List<Encontro> findByGrupoIdOrderByInicioAsc(Long grupoId);
@Query("select count(e) from Encontro e where e.grupo.id = :grupoId and e.inicio < :fim and e.fim > :inicio and (:ignorarId is null or e.id <> :ignorarId)")
long contarConflitos(@Param("grupoId") Long grupoId, @Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim, @Param("ignorarId") Long ignorarId);
}
