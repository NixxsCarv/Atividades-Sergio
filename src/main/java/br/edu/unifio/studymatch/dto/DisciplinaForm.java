package br.edu.unifio.studymatch.dto;

import jakarta.validation.constraints.*;
import java.time.*;
import java.util.*;
import br.edu.unifio.studymatch.model.DiaSemana;
public record DisciplinaForm(@NotBlank @Size(max=100) String nome) {}
