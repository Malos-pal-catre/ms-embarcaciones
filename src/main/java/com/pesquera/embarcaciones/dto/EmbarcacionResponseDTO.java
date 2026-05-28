package com.pesquera.embarcaciones.dto;

public record EmbarcacionResponseDTO(
    Long id,
    String matricula,
    String nombre,
    Double eslora,
    String zonaAutorizada,
    Long pescadorId,
    Boolean activa
) {}