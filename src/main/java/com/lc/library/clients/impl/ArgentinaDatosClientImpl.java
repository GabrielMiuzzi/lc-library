package com.lc.library.clients.impl;

import com.lc.library.clients.ArgentinaDatosClient;
import com.lc.library.models.dto.ArgentinaDatosDolarQuoteDto;
import com.lc.library.models.dto.ArgentinaDatosIndiceDto;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class ArgentinaDatosClientImpl implements ArgentinaDatosClient {
    private final RestTemplate restTemplate;
    private final String middlewareBaseUrl;
    private final String cotizacionesDolaresPath;
    private final String indicesInflacionPath;
    private final String indicesInflacionInteranualPath;
    private final String indicesRiesgoPaisPath;

    @Autowired
    public ArgentinaDatosClientImpl(RestTemplate restTemplate,
                                    @Value("${lc.middleware.url}") String middlewareBaseUrl,
                                    @Value("${lc.middleware.argentinadatos.cotizaciones.dolares.path}") String cotizacionesDolaresPath,
                                    @Value("${lc.middleware.argentinadatos.finanzas.indices.inflacion.path}") String indicesInflacionPath,
                                    @Value("${lc.middleware.argentinadatos.finanzas.indices.inflacion.interanual.path}") String indicesInflacionInteranualPath,
                                    @Value("${lc.middleware.argentinadatos.finanzas.indices.riesgo.pais.path}") String indicesRiesgoPaisPath) {
        this.restTemplate = restTemplate;
        this.middlewareBaseUrl = middlewareBaseUrl;
        this.cotizacionesDolaresPath = cotizacionesDolaresPath;
        this.indicesInflacionPath = indicesInflacionPath;
        this.indicesInflacionInteranualPath = indicesInflacionInteranualPath;
        this.indicesRiesgoPaisPath = indicesRiesgoPaisPath;
    }

    @Override
    public List<ArgentinaDatosDolarQuoteDto> getCotizacionesDolares() {
        String url = buildUrl(cotizacionesDolaresPath);
        ArgentinaDatosDolarQuoteDto[] response = restTemplate.getForObject(url, ArgentinaDatosDolarQuoteDto[].class);
        return toList(response);
    }

    @Override
    public List<ArgentinaDatosIndiceDto> getIndicesInflacion() {
        String url = buildUrl(indicesInflacionPath);
        ArgentinaDatosIndiceDto[] response = restTemplate.getForObject(url, ArgentinaDatosIndiceDto[].class);
        return toList(response);
    }

    @Override
    public List<ArgentinaDatosIndiceDto> getIndicesInflacionInteranual() {
        String url = buildUrl(indicesInflacionInteranualPath);
        ArgentinaDatosIndiceDto[] response = restTemplate.getForObject(url, ArgentinaDatosIndiceDto[].class);
        return toList(response);
    }

    @Override
    public List<ArgentinaDatosIndiceDto> getIndicesRiesgoPais() {
        String url = buildUrl(indicesRiesgoPaisPath);
        ArgentinaDatosIndiceDto[] response = restTemplate.getForObject(url, ArgentinaDatosIndiceDto[].class);
        return toList(response);
    }

    private String buildUrl(String path) {
        return UriComponentsBuilder.fromHttpUrl(middlewareBaseUrl)
                .path(path)
                .toUriString();
    }

    private <T> List<T> toList(T[] response) {
        if (response == null) {
            return Collections.emptyList();
        }
        return Arrays.asList(response);
    }
}
