package com.lc.library.utils.mapper;

import com.lc.library.models.dto.ArgentinaDatosDolarQuoteDto;
import com.lc.library.models.entities.ArgentinaDatosDolarQuote;

public interface ArgentinaDatosDolarQuoteMapper {
    ArgentinaDatosDolarQuoteDto toDto(ArgentinaDatosDolarQuote entity);

    ArgentinaDatosDolarQuote toEntity(ArgentinaDatosDolarQuoteDto dto);
}
