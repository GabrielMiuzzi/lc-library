package com.lc.library.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        name = "argentina_datos_dolar_quotes",
        uniqueConstraints = @UniqueConstraint(columnNames = {"fecha", "casa", "moneda"})
)
public class ArgentinaDatosDolarQuote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String moneda;

    @Column(nullable = false)
    private String casa;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(precision = 19, scale = 4)
    private BigDecimal compra;

    @Column(precision = 19, scale = 4)
    private BigDecimal venta;
}
