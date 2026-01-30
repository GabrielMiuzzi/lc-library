package com.lc.library.controllers;

import com.lc.library.models.dto.ArgentinaDatosDolarQuoteDto;
import com.lc.library.models.dto.ArgentinaDatosIndiceDto;
import com.lc.library.services.ArgentinaDatosLibraryService;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/argentinadatos")
public class ArgentinaDatosController {

    private final ArgentinaDatosLibraryService argentinaDatosLibraryService;

    public ArgentinaDatosController(ArgentinaDatosLibraryService argentinaDatosLibraryService) {
        this.argentinaDatosLibraryService = argentinaDatosLibraryService;
    }

    @GetMapping("/cotizaciones/dolares")
    public ResponseEntity<List<ArgentinaDatosDolarQuoteDto>> getCotizacionesDolares() {
        return ResponseEntity.ok(argentinaDatosLibraryService.getCotizacionesDolares());
    }

    @GetMapping("/finanzas/indices/inflacion")
    public ResponseEntity<List<ArgentinaDatosIndiceDto>> getIndicesInflacion() {
        return ResponseEntity.ok(argentinaDatosLibraryService.getIndicesInflacion());
    }

    @GetMapping("/finanzas/indices/inflacion/ultimo")
    public ResponseEntity<ArgentinaDatosIndiceDto> getIndiceInflacionUltimo() {
        return ResponseEntity.of(Optional.ofNullable(argentinaDatosLibraryService.getIndiceInflacionUltimo()));
    }

    @GetMapping("/finanzas/indices/inflacion-interanual")
    public ResponseEntity<List<ArgentinaDatosIndiceDto>> getIndicesInflacionInteranual() {
        return ResponseEntity.ok(argentinaDatosLibraryService.getIndicesInflacionInteranual());
    }

    @GetMapping("/finanzas/indices/riesgo-pais")
    public ResponseEntity<List<ArgentinaDatosIndiceDto>> getIndicesRiesgoPais() {
        return ResponseEntity.ok(argentinaDatosLibraryService.getIndicesRiesgoPais());
    }

    @GetMapping("/finanzas/indices/riesgo-pais/ultimo")
    public ResponseEntity<ArgentinaDatosIndiceDto> getIndiceRiesgoPaisUltimo() {
        return ResponseEntity.of(Optional.ofNullable(argentinaDatosLibraryService.getIndiceRiesgoPaisUltimo()));
    }
}
