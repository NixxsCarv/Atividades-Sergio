package br.edu.unifio.studymatch.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;
import java.time.*;
@Entity @Table(name="disciplinas", uniqueConstraints=@UniqueConstraint(columnNames="nome"))
@Getter @Setter @NoArgsConstructor public class Disciplina extends BaseEntity {
    @Column(nullable=false, length=100) private String nome;
}
