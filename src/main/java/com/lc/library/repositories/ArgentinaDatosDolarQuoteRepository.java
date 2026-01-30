package com.lc.library.repositories;

import com.lc.library.models.entities.ArgentinaDatosDolarQuote;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArgentinaDatosDolarQuoteRepository extends JpaRepository<ArgentinaDatosDolarQuote, Long> {
    Optional<ArgentinaDatosDolarQuote> findByFechaAndCasaAndMoneda(LocalDate fecha, String casa, String moneda);

    List<ArgentinaDatosDolarQuote> findAllByOrderByFechaAsc();
}
