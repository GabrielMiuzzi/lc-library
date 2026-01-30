package com.lc.library.utils.mapper.impl;

import com.lc.library.models.dto.ArgentinaDatosDolarQuoteDto;
import com.lc.library.models.entities.ArgentinaDatosDolarQuote;
import com.lc.library.utils.DateParsingUtils;
import com.lc.library.utils.mapper.ArgentinaDatosDolarQuoteMapper;
import org.springframework.stereotype.Component;

@Component
public class ArgentinaDatosDolarQuoteMapperImpl implements ArgentinaDatosDolarQuoteMapper {

    @Override
    public ArgentinaDatosDolarQuoteDto toDto(ArgentinaDatosDolarQuote entity) {
        if (entity == null) {
            return null;
        }
        ArgentinaDatosDolarQuoteDto dto = new ArgentinaDatosDolarQuoteDto();
        dto.setMoneda(entity.getMoneda());
        dto.setCasa(entity.getCasa());
        dto.setFecha(DateParsingUtils.formatLocalDate(entity.getFecha()));
        dto.setCompra(entity.getCompra());
        dto.setVenta(entity.getVenta());
        return dto;
    }

    @Override
    public ArgentinaDatosDolarQuote toEntity(ArgentinaDatosDolarQuoteDto dto) {
        if (dto == null) {
            return null;
        }
        ArgentinaDatosDolarQuote entity = new ArgentinaDatosDolarQuote();
        entity.setMoneda(dto.getMoneda());
        entity.setCasa(dto.getCasa());
        entity.setFecha(DateParsingUtils.parseToLocalDate(dto.getFecha()));
        entity.setCompra(dto.getCompra());
        entity.setVenta(dto.getVenta());
        return entity;
    }
}
