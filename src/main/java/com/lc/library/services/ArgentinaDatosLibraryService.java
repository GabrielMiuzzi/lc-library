package com.lc.library.services;

import com.lc.library.models.dto.ArgentinaDatosDolarQuoteDto;
import com.lc.library.models.dto.ArgentinaDatosIndiceDto;
import java.util.List;

public interface ArgentinaDatosLibraryService {
    List<ArgentinaDatosDolarQuoteDto> getCotizacionesDolares();

    List<ArgentinaDatosIndiceDto> getIndicesInflacion();

    ArgentinaDatosIndiceDto getIndiceInflacionUltimo();

    List<ArgentinaDatosIndiceDto> getIndicesInflacionInteranual();

    List<ArgentinaDatosIndiceDto> getIndicesRiesgoPais();

    ArgentinaDatosIndiceDto getIndiceRiesgoPaisUltimo();

    void syncHistoricos();
}
