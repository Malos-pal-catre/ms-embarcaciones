package com.pesquera.embarcaciones.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "embarcaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Embarcacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String matricula;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private Double eslora; // largo en metros

    @Column(nullable = false)
    private String zonaAutorizada; // zona de pesca autorizada

    @Column(nullable = false)
    private Long pescadorId; // referencia a ms-pescadores

    @Column(nullable = false)
    private Boolean activa;
}