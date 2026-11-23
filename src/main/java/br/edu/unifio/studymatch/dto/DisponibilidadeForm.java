package br.edu.unifio.studymatch.dto;

import jakarta.validation.constraints.*;
import java.time.*;
import java.util.*;
import br.edu.unifio.studymatch.model.DiaSemana;
public record DisponibilidadeForm(@NotNull Long estudanteId, @NotNull DiaSemana diaSemana, @NotNull LocalTime horaInicio, @NotNull LocalTime horaFim) {}
