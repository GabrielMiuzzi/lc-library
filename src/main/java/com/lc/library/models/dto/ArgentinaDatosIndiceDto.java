package com.lc.library.models.dto;

import java.math.BigDecimal;

public class ArgentinaDatosIndiceDto {
    private String fecha;
    private BigDecimal valor;

    public ArgentinaDatosIndiceDto() {
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
