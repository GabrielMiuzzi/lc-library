package com.lc.library.repositories;

import com.lc.library.models.entities.ArgentinaDatosIndice;
import com.lc.library.models.enums.IndiceTipo;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArgentinaDatosIndiceRepository extends JpaRepository<ArgentinaDatosIndice, Long> {
    List<ArgentinaDatosIndice> findAllByTipoOrderByFechaAsc(IndiceTipo tipo);

    Optional<ArgentinaDatosIndice> findTopByTipoOrderByFechaDesc(IndiceTipo tipo);

    boolean existsByTipoAndFecha(IndiceTipo tipo, LocalDate fecha);
}
