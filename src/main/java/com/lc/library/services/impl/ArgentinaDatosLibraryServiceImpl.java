package com.lc.library.services.impl;

import com.lc.library.clients.ArgentinaDatosClient;
import com.lc.library.models.dto.ArgentinaDatosDolarQuoteDto;
import com.lc.library.models.dto.ArgentinaDatosIndiceDto;
import com.lc.library.models.entities.ArgentinaDatosDolarQuote;
import com.lc.library.models.entities.ArgentinaDatosIndice;
import com.lc.library.models.enums.IndiceTipo;
import com.lc.library.repositories.ArgentinaDatosDolarQuoteRepository;
import com.lc.library.repositories.ArgentinaDatosIndiceRepository;
import com.lc.library.services.ArgentinaDatosLibraryService;
import com.lc.library.utils.DateParsingUtils;
import com.lc.library.utils.mapper.ArgentinaDatosDolarQuoteMapper;
import com.lc.library.utils.mapper.ArgentinaDatosIndiceMapper;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArgentinaDatosLibraryServiceImpl implements ArgentinaDatosLibraryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ArgentinaDatosLibraryServiceImpl.class);

    private final ArgentinaDatosClient argentinaDatosClient;
    private final ArgentinaDatosDolarQuoteRepository dolarQuoteRepository;
    private final ArgentinaDatosIndiceRepository indiceRepository;
    private final ArgentinaDatosDolarQuoteMapper dolarQuoteMapper;
    private final ArgentinaDatosIndiceMapper indiceMapper;

    public ArgentinaDatosLibraryServiceImpl(ArgentinaDatosClient argentinaDatosClient,
                                            ArgentinaDatosDolarQuoteRepository dolarQuoteRepository,
                                            ArgentinaDatosIndiceRepository indiceRepository,
                                            ArgentinaDatosDolarQuoteMapper dolarQuoteMapper,
                                            ArgentinaDatosIndiceMapper indiceMapper) {
        this.argentinaDatosClient = argentinaDatosClient;
        this.dolarQuoteRepository = dolarQuoteRepository;
        this.indiceRepository = indiceRepository;
        this.dolarQuoteMapper = dolarQuoteMapper;
        this.indiceMapper = indiceMapper;
    }

    @Override
    public List<ArgentinaDatosDolarQuoteDto> getCotizacionesDolares() {
        return dolarQuoteRepository.findAllByOrderByFechaAsc()
                .stream()
                .map(dolarQuoteMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ArgentinaDatosIndiceDto> getIndicesInflacion() {
        return getIndicesByTipo(IndiceTipo.INFLACION);
    }

    @Override
    public ArgentinaDatosIndiceDto getIndiceInflacionUltimo() {
        return indiceRepository.findTopByTipoOrderByFechaDesc(IndiceTipo.INFLACION)
                .map(indiceMapper::toDto)
                .orElse(null);
    }

    @Override
    public List<ArgentinaDatosIndiceDto> getIndicesInflacionInteranual() {
        return getIndicesByTipo(IndiceTipo.INFLACION_INTERANUAL);
    }

    @Override
    public List<ArgentinaDatosIndiceDto> getIndicesRiesgoPais() {
        return getIndicesByTipo(IndiceTipo.RIESGO_PAIS);
    }

    @Override
    public ArgentinaDatosIndiceDto getIndiceRiesgoPaisUltimo() {
        return indiceRepository.findTopByTipoOrderByFechaDesc(IndiceTipo.RIESGO_PAIS)
                .map(indiceMapper::toDto)
                .orElse(null);
    }

    @Override
    @Transactional
    public void syncHistoricos() {
        syncDolarHistorico();
        syncIndiceHistorico(IndiceTipo.INFLACION, argentinaDatosClient.getIndicesInflacion());
        syncIndiceHistorico(IndiceTipo.INFLACION_INTERANUAL, argentinaDatosClient.getIndicesInflacionInteranual());
        syncIndiceHistorico(IndiceTipo.RIESGO_PAIS, argentinaDatosClient.getIndicesRiesgoPais());
    }

    private List<ArgentinaDatosIndiceDto> getIndicesByTipo(IndiceTipo tipo) {
        return indiceRepository.findAllByTipoOrderByFechaAsc(tipo)
                .stream()
                .map(indiceMapper::toDto)
                .collect(Collectors.toList());
    }

    private void syncDolarHistorico() {
        List<ArgentinaDatosDolarQuote> existentes = dolarQuoteRepository.findAll();
        Set<String> existentesKeys = existentes.stream()
                .map(this::buildDolarKey)
                .collect(Collectors.toSet());

        List<ArgentinaDatosDolarQuoteDto> historico = argentinaDatosClient.getCotizacionesDolares();
        int nuevos = 0;
        for (ArgentinaDatosDolarQuoteDto dto : historico) {
            LocalDate fecha = DateParsingUtils.parseToLocalDate(dto.getFecha());
            if (fecha == null) {
                LOGGER.warn("Fecha invalida en cotizacion dolar: {}", dto.getFecha());
                continue;
            }
            if (dto.getCasa() == null || dto.getCasa().isBlank()
                    || dto.getMoneda() == null || dto.getMoneda().isBlank()) {
                LOGGER.warn("Cotizacion dolar invalida (casa/moneda faltante). casa='{}' moneda='{}' fecha='{}'",
                        dto.getCasa(), dto.getMoneda(), dto.getFecha());
                continue;
            }
            String key = buildDolarKey(fecha, dto.getCasa(), dto.getMoneda());
            if (existentesKeys.contains(key)) {
                continue;
            }
            ArgentinaDatosDolarQuote entity = dolarQuoteMapper.toEntity(dto);
            if (entity.getFecha() == null) {
                entity.setFecha(fecha);
            }
            dolarQuoteRepository.save(entity);
            existentesKeys.add(key);
            nuevos++;
        }
        LOGGER.info("Sincronizacion dolar historico completa. Nuevos registros: {}", nuevos);
    }

    private void syncIndiceHistorico(IndiceTipo tipo, List<ArgentinaDatosIndiceDto> historico) {
        List<ArgentinaDatosIndice> existentes = indiceRepository.findAllByTipoOrderByFechaAsc(tipo);
        Set<LocalDate> fechasExistentes = new HashSet<>();
        for (ArgentinaDatosIndice indice : existentes) {
            fechasExistentes.add(indice.getFecha());
        }

        int nuevos = 0;
        for (ArgentinaDatosIndiceDto dto : historico) {
            LocalDate fecha = DateParsingUtils.parseToLocalDate(dto.getFecha());
            if (fecha == null) {
                LOGGER.warn("Fecha invalida en indice {}: {}", tipo, dto.getFecha());
                continue;
            }
            if (fechasExistentes.contains(fecha)) {
                continue;
            }
            ArgentinaDatosIndice entity = indiceMapper.toEntity(dto);
            if (entity.getFecha() == null) {
                entity.setFecha(fecha);
            }
            entity.setTipo(tipo);
            indiceRepository.save(entity);
            fechasExistentes.add(fecha);
            nuevos++;
        }
        LOGGER.info("Sincronizacion indice {} completa. Nuevos registros: {}", tipo, nuevos);
    }

    private String buildDolarKey(ArgentinaDatosDolarQuote entity) {
        return buildDolarKey(entity.getFecha(), entity.getCasa(), entity.getMoneda());
    }

    private String buildDolarKey(LocalDate fecha, String casa, String moneda) {
        return String.format("%s|%s|%s",
                fecha != null ? fecha : "",
                Optional.ofNullable(casa).orElse(""),
                Optional.ofNullable(moneda).orElse(""));
    }
}
