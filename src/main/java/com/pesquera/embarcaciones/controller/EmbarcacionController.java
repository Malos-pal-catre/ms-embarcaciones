package com.pesquera.embarcaciones.controller;

import com.pesquera.embarcaciones.dto.*;
import com.pesquera.embarcaciones.service.EmbarcacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/embarcaciones")
@RequiredArgsConstructor
public class EmbarcacionController {

    private final EmbarcacionService embarcacionService;

    @GetMapping
    public ResponseEntity<List<EmbarcacionResponseDTO>> listarTodas() {
        return ResponseEntity.ok(embarcacionService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmbarcacionResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(embarcacionService.buscarPorId(id));
    }

    @GetMapping("/matricula/{matricula}")
    public ResponseEntity<EmbarcacionResponseDTO> buscarPorMatricula(@PathVariable String matricula) {
        return ResponseEntity.ok(embarcacionService.buscarPorMatricula(matricula));
    }

    @GetMapping("/pescador/{pescadorId}")
    public ResponseEntity<List<EmbarcacionResponseDTO>> listarPorPescador(@PathVariable Long pescadorId) {
        return ResponseEntity.ok(embarcacionService.listarPorPescador(pescadorId));
    }

    @GetMapping("/activas")
    public ResponseEntity<List<EmbarcacionResponseDTO>> listarActivas() {
        return ResponseEntity.ok(embarcacionService.listarActivas());
    }

    @GetMapping("/zona")
    public ResponseEntity<List<EmbarcacionResponseDTO>> listarActivasPorZona(@RequestParam String zona) {
        return ResponseEntity.ok(embarcacionService.listarActivasPorZona(zona));
    }

    @PostMapping
    public ResponseEntity<EmbarcacionResponseDTO> registrar(@RequestBody @Valid EmbarcacionRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(embarcacionService.registrar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmbarcacionResponseDTO> actualizar(@PathVariable Long id,
                                                              @RequestBody @Valid EmbarcacionRequestDTO dto) {
        return ResponseEntity.ok(embarcacionService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        embarcacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}