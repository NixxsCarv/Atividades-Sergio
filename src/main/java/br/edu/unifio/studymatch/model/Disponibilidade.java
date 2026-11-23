package br.edu.unifio.studymatch.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;
import java.time.*;
@Entity @Table(name="disponibilidades")
@Getter @Setter @NoArgsConstructor public class Disponibilidade extends BaseEntity {
    @ManyToOne(fetch=FetchType.LAZY, optional=false) @JoinColumn(name="estudante_id",nullable=false) private Estudante estudante;
    @Enumerated(EnumType.STRING) @Column(nullable=false) private DiaSemana diaSemana;
    @Column(nullable=false) private LocalTime horaInicio;
    @Column(nullable=false) private LocalTime horaFim;
}
