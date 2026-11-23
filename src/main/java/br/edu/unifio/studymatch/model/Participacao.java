package br.edu.unifio.studymatch.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;
import java.time.*;
@Entity @Table(name="participacoes", uniqueConstraints=@UniqueConstraint(columnNames={"grupo_id","estudante_id"}))
@Getter @Setter @NoArgsConstructor public class Participacao extends BaseEntity {
    @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="grupo_id",nullable=false) private GrupoEstudo grupo;
    @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="estudante_id",nullable=false) private Estudante estudante;
    @Column(nullable=false,updatable=false) private LocalDateTime entrouEm=LocalDateTime.now();
}
