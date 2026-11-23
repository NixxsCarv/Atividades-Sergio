package br.edu.unifio.studymatch.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;
import java.time.*;
@Entity @Table(name="encontros")
@Getter @Setter @NoArgsConstructor public class Encontro extends BaseEntity {
    @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="grupo_id",nullable=false) private GrupoEstudo grupo;
    @Column(nullable=false) private LocalDateTime inicio;
    @Column(nullable=false) private LocalDateTime fim;
    @Column(nullable=false,length=160) private String localDescricao;
}
