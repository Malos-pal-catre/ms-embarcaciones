package com.pesquera.embarcaciones.dto;

import com.pesquera.embarcaciones.model.Embarcacion;

public class EmbarcacionMapper {

    public static EmbarcacionResponseDTO toDTO(Embarcacion e) {
        return new EmbarcacionResponseDTO(
            e.getId(),
            e.getMatricula(),
            e.getNombre(),
            e.getEslora(),
            e.getZonaAutorizada(),
            e.getPescadorId(),
            e.getActiva()
        );
    }

    public static Embarcacion toEntity(EmbarcacionRequestDTO dto) {
        return Embarcacion.builder()
            .matricula(dto.matricula())
            .nombre(dto.nombre())
            .eslora(dto.eslora())
            .zonaAutorizada(dto.zonaAutorizada())
            .pescadorId(dto.pescadorId())
            .activa(dto.activa())
            .build();
    }
}
