package com.pesquera.embarcaciones.repository;


import com.pesquera.embarcaciones.model.Embarcacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmbarcacionRepository extends JpaRepository<Embarcacion, Long> {

    // Query Methods
    Optional<Embarcacion> findByMatricula(String matricula);
    List<Embarcacion> findByPescadorId(Long pescadorId);
    List<Embarcacion> findByZonaAutorizada(String zona);
    List<Embarcacion> findByActivaTrue();

    // Custom Queries
    @Query("SELECT e FROM Embarcacion e WHERE e.eslora <= :maxEslora AND e.activa = true")
    List<Embarcacion> embarcacionesActivasPorEslora(@Param("maxEslora") Double maxEslora);

    @Query(value = "SELECT * FROM embarcaciones WHERE zona_autorizada = :zona AND activa = true", nativeQuery = true)
    List<Embarcacion> activasPorZona(@Param("zona") String zona);
}
