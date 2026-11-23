package br.edu.unifio.studymatch.dto;

import jakarta.validation.constraints.*;
import java.time.*;
import java.util.*;
import br.edu.unifio.studymatch.model.DiaSemana;
public record EstudanteForm(@NotBlank @Size(max=120) String nome, @NotBlank @Email @Size(max=160) String email, @NotBlank @Size(max=250) String objetivo, @NotNull Set<@NotNull Long> disciplinaIds) {}
