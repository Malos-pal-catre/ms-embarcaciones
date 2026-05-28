package com.pesquera.embarcaciones.dto;


import jakarta.validation.constraints.*;

public record EmbarcacionRequestDTO(

    @NotBlank(message = "La matrícula es obligatoria")
    String matricula,

    @NotBlank(message = "El nombre es obligatorio")
    String nombre,

    @NotNull(message = "La eslora es obligatoria")
    @Positive(message = "La eslora debe ser mayor a 0")
    @Max(value = 18, message = "La eslora no puede superar los 18 metros")
    Double eslora,

    @NotBlank(message = "La zona autorizada es obligatoria")
    String zonaAutorizada,

    @NotNull(message = "El ID del pescador es obligatorio")
    @Positive(message = "El ID del pescador debe ser mayor a 0")
    Long pescadorId,

    @NotNull(message = "El estado activa es obligatorio")
    Boolean activa
) {}