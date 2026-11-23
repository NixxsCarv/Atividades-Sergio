package br.edu.unifio.studymatch.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;
import java.time.*;
@Entity @Table(name="estudantes", uniqueConstraints=@UniqueConstraint(columnNames="email"))
@Getter @Setter @NoArgsConstructor public class Estudante extends BaseEntity {
    @Column(nullable=false, length=120) private String nome;
    @Column(nullable=false, length=160) private String email;
    @Column(nullable=false, length=250) private String objetivo;
    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name="estudante_disciplinas", joinColumns=@JoinColumn(name="estudante_id"), inverseJoinColumns=@JoinColumn(name="disciplina_id"))
    private Set<Disciplina> disciplinas = new HashSet<>();
}
