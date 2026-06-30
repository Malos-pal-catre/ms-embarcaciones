package com.pesquera.embarcaciones.controller;

import com.pesquera.embarcaciones.dto.*;
import com.pesquera.embarcaciones.service.EmbarcacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/embarcaciones")
@RequiredArgsConstructor
@Tag(name = "Embarcaciones", description = "Gestión de embarcaciones pesqueras asociadas a cada pescador")
public class EmbarcacionController {

    private final EmbarcacionService embarcacionService;

    @Operation(summary = "Listar todas las embarcaciones")
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping
    public ResponseEntity<List<EmbarcacionResponseDTO>> listarTodas() {
        return ResponseEntity.ok(embarcacionService.listarTodas());
    }

    @Operation(summary = "Buscar embarcación por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Embarcación encontrada"),
        @ApiResponse(responseCode = "404", description = "No existe una embarcación con el ID indicado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<EmbarcacionResponseDTO> buscarPorId(
            @Parameter(description = "ID de la embarcación", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(embarcacionService.buscarPorId(id));
    }

    @Operation(summary = "Buscar embarcación por matrícula")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Embarcación encontrada"),
        @ApiResponse(responseCode = "404", description = "No existe una embarcación con esa matrícula", content = @Content)
    })
    @GetMapping("/matricula/{matricula}")
    public ResponseEntity<EmbarcacionResponseDTO> buscarPorMatricula(
            @Parameter(description = "Matrícula de la embarcación", example = "SI-1234") @PathVariable String matricula) {
        return ResponseEntity.ok(embarcacionService.buscarPorMatricula(matricula));
    }

    @Operation(summary = "Listar embarcaciones por pescador", description = "Retorna todas las embarcaciones registradas a nombre de un pescador específico.")
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping("/pescador/{pescadorId}")
    public ResponseEntity<List<EmbarcacionResponseDTO>> listarPorPescador(
            @Parameter(description = "ID del pescador", example = "3") @PathVariable Long pescadorId) {
        return ResponseEntity.ok(embarcacionService.listarPorPescador(pescadorId));
    }

    @Operation(summary = "Listar embarcaciones activas")
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping("/activas")
    public ResponseEntity<List<EmbarcacionResponseDTO>> listarActivas() {
        return ResponseEntity.ok(embarcacionService.listarActivas());
    }

    @Operation(summary = "Listar embarcaciones activas por zona")
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping("/zona")
    public ResponseEntity<List<EmbarcacionResponseDTO>> listarActivasPorZona(
            @Parameter(description = "Zona de operación", example = "Lo Abarca") @RequestParam String zona) {
        return ResponseEntity.ok(embarcacionService.listarActivasPorZona(zona));
    }

    @Operation(summary = "Registrar una nueva embarcación", description = "Crea una embarcación nueva asociada a un pescador.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        required = true,
        content = @Content(
            schema = @Schema(implementation = EmbarcacionRequestDTO.class),
            examples = @ExampleObject(
                name = "Embarcación de ejemplo",
                value = """
                {
                  "nombre": "Don Pancho II",
                  "matricula": "SI-1234",
                  "pescadorId": 3,
                  "zona": "Lo Abarca",
                  "activa": true
                }
                """
            )
        )
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Embarcación registrada correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o matrícula duplicada", content = @Content)
    })
    @PostMapping
    public ResponseEntity<EmbarcacionResponseDTO> registrar(@RequestBody @Valid EmbarcacionRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(embarcacionService.registrar(dto));
    }

    @Operation(summary = "Actualizar una embarcación existente")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        required = true,
        content = @Content(
            schema = @Schema(implementation = EmbarcacionRequestDTO.class),
            examples = @ExampleObject(
                name = "Actualización de ejemplo",
                value = """
                {
                  "nombre": "Don Pancho II",
                  "matricula": "SI-1234",
                  "pescadorId": 3,
                  "zona": "Lo Abarca",
                  "activa": false
                }
                """
            )
        )
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Embarcación actualizada correctamente"),
        @ApiResponse(responseCode = "404", description = "Embarcación no encontrada", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<EmbarcacionResponseDTO> actualizar(
            @Parameter(description = "ID de la embarcación", example = "1") @PathVariable Long id,
            @RequestBody @Valid EmbarcacionRequestDTO dto) {
        return ResponseEntity.ok(embarcacionService.actualizar(id, dto));
    }

    @Operation(summary = "Eliminar una embarcación")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Embarcación eliminada correctamente"),
        @ApiResponse(responseCode = "404", description = "Embarcación no encontrada", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@Parameter(description = "ID de la embarcación", example = "1") @PathVariable Long id) {
        embarcacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}