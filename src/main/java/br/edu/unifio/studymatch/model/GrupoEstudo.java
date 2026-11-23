package br.edu.unifio.studymatch.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;
import java.time.*;
@Entity @Table(name="grupos_estudo")
@Getter @Setter @NoArgsConstructor public class GrupoEstudo extends BaseEntity {
    @Column(nullable=false, length=120) private String nome;
    @Column(nullable=false, length=500) private String descricao;
    @ManyToOne(fetch=FetchType.LAZY, optional=false) @JoinColumn(name="disciplina_id",nullable=false) private Disciplina disciplina;
    @ManyToOne(fetch=FetchType.LAZY, optional=false) @JoinColumn(name="administrador_id",nullable=false) private Estudante administrador;
    @Enumerated(EnumType.STRING) @Column(nullable=false) private DiaSemana diaSemana;
    @Column(nullable=false) private LocalTime horaInicio;
    @Column(nullable=false) private LocalTime horaFim;
    @Column(nullable=false) private Integer capacidade;
}
