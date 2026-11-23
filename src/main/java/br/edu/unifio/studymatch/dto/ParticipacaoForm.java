package br.edu.unifio.studymatch.dto;

import jakarta.validation.constraints.*;
import java.time.*;
import java.util.*;
import br.edu.unifio.studymatch.model.DiaSemana;
public record ParticipacaoForm(@NotNull Long estudanteId, @NotNull Long grupoId) {}
