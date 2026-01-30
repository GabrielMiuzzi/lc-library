package com.lc.library.clients;

import com.lc.library.models.dto.ArgentinaDatosDolarQuoteDto;
import com.lc.library.models.dto.ArgentinaDatosIndiceDto;
import java.util.List;

public interface ArgentinaDatosClient {
    List<ArgentinaDatosDolarQuoteDto> getCotizacionesDolares();

    List<ArgentinaDatosIndiceDto> getIndicesInflacion();

    List<ArgentinaDatosIndiceDto> getIndicesInflacionInteranual();

    List<ArgentinaDatosIndiceDto> getIndicesRiesgoPais();
}
