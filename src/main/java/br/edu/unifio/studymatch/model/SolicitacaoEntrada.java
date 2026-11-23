package br.edu.unifio.studymatch.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;
import java.time.*;
@Entity @Table(name="solicitacoes_entrada", uniqueConstraints=@UniqueConstraint(columnNames={"grupo_id","estudante_id"}))
@Getter @Setter @NoArgsConstructor public class SolicitacaoEntrada extends BaseEntity {
    @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="grupo_id",nullable=false) private GrupoEstudo grupo;
    @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="estudante_id",nullable=false) private Estudante estudante;
    @Enumerated(EnumType.STRING) @Column(nullable=false) private StatusSolicitacao status=StatusSolicitacao.PENDENTE;
    @Column(nullable=false,updatable=false) private LocalDateTime criadaEm=LocalDateTime.now();
}
