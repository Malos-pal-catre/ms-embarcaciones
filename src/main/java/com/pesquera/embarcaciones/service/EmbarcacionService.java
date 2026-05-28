package com.pesquera.embarcaciones.service;


import com.pesquera.embarcaciones.dto.*;
import com.pesquera.embarcaciones.exception.RecursoNoEncontradoException;
import com.pesquera.embarcaciones.model.Embarcacion;
import com.pesquera.embarcaciones.repository.EmbarcacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmbarcacionService {

    private final EmbarcacionRepository embarcacionRepository;

    public List<EmbarcacionResponseDTO> listarTodas() {
        return embarcacionRepository.findAll()
            .stream()
            .map(EmbarcacionMapper::toDTO)
            .toList();
    }

    public EmbarcacionResponseDTO buscarPorId(Long id) {
        Embarcacion e = embarcacionRepository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("Embarcación no encontrada con id: " + id));
        return EmbarcacionMapper.toDTO(e);
    }

    public EmbarcacionResponseDTO buscarPorMatricula(String matricula) {
        Embarcacion e = embarcacionRepository.findByMatricula(matricula)
            .orElseThrow(() -> new RecursoNoEncontradoException("Embarcación no encontrada con matrícula: " + matricula));
        return EmbarcacionMapper.toDTO(e);
    }

    public List<EmbarcacionResponseDTO> listarPorPescador(Long pescadorId) {
        return embarcacionRepository.findByPescadorId(pescadorId)
            .stream()
            .map(EmbarcacionMapper::toDTO)
            .toList();
    }

    public List<EmbarcacionResponseDTO> listarActivas() {
        return embarcacionRepository.findByActivaTrue()
            .stream()
            .map(EmbarcacionMapper::toDTO)
            .toList();
    }

    public List<EmbarcacionResponseDTO> listarActivasPorZona(String zona) {
        return embarcacionRepository.activasPorZona(zona)
            .stream()
            .map(EmbarcacionMapper::toDTO)
            .toList();
    }

    public EmbarcacionResponseDTO registrar(EmbarcacionRequestDTO dto) {
        if (embarcacionRepository.findByMatricula(dto.matricula()).isPresent()) {
            throw new IllegalArgumentException("Ya existe una embarcación con la matrícula: " + dto.matricula());
        }
        return EmbarcacionMapper.toDTO(embarcacionRepository.save(EmbarcacionMapper.toEntity(dto)));
    }

    public EmbarcacionResponseDTO actualizar(Long id, EmbarcacionRequestDTO dto) {
        Embarcacion existente = embarcacionRepository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("Embarcación no encontrada con id: " + id));
        existente.setMatricula(dto.matricula());
        existente.setNombre(dto.nombre());
        existente.setEslora(dto.eslora());
        existente.setZonaAutorizada(dto.zonaAutorizada());
        existente.setPescadorId(dto.pescadorId());
        existente.setActiva(dto.activa());
        return EmbarcacionMapper.toDTO(embarcacionRepository.save(existente));
    }

    public void eliminar(Long id) {
        if (!embarcacionRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Embarcación no encontrada con id: " + id);
        }
        embarcacionRepository.deleteById(id);
    }
}