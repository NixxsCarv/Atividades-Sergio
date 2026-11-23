package br.edu.unifio.studymatch.dto;

import jakarta.validation.constraints.*;
import java.time.*;
import java.util.*;
import br.edu.unifio.studymatch.model.DiaSemana;
public record EncontroForm(@NotNull Long grupoId, @NotNull LocalDateTime inicio, @NotNull LocalDateTime fim, @NotBlank @Size(max=160) String localDescricao) {}
