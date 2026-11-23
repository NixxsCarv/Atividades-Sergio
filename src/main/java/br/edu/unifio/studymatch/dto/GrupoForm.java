package br.edu.unifio.studymatch.dto;

import jakarta.validation.constraints.*;
import java.time.*;
import java.util.*;
import br.edu.unifio.studymatch.model.DiaSemana;
public record GrupoForm(@NotBlank @Size(max=120) String nome, @NotBlank @Size(max=500) String descricao, @NotNull Long disciplinaId, @NotNull Long administradorId, @NotNull DiaSemana diaSemana, @NotNull LocalTime horaInicio, @NotNull LocalTime horaFim, @NotNull @Min(2) @Max(100) Integer capacidade) {}
