package com.lc.library.models.dto;

import java.math.BigDecimal;

public class ArgentinaDatosDolarQuoteDto {
    private String moneda;
    private String casa;
    private String fecha;
    private BigDecimal compra;
    private BigDecimal venta;

    public ArgentinaDatosDolarQuoteDto() {
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getCasa() {
        return casa;
    }

    public void setCasa(String casa) {
        this.casa = casa;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getCompra() {
        return compra;
    }

    public void setCompra(BigDecimal compra) {
        this.compra = compra;
    }

    public BigDecimal getVenta() {
        return venta;
    }

    public void setVenta(BigDecimal venta) {
        this.venta = venta;
    }
}
