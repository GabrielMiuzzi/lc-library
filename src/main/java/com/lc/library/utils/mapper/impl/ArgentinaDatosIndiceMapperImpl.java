package com.lc.library.utils.mapper.impl;

import com.lc.library.models.dto.ArgentinaDatosIndiceDto;
import com.lc.library.models.entities.ArgentinaDatosIndice;
import com.lc.library.utils.DateParsingUtils;
import com.lc.library.utils.mapper.ArgentinaDatosIndiceMapper;
import org.springframework.stereotype.Component;

@Component
public class ArgentinaDatosIndiceMapperImpl implements ArgentinaDatosIndiceMapper {

    @Override
    public ArgentinaDatosIndiceDto toDto(ArgentinaDatosIndice entity) {
        if (entity == null) {
            return null;
        }
        ArgentinaDatosIndiceDto dto = new ArgentinaDatosIndiceDto();
        dto.setFecha(DateParsingUtils.formatLocalDate(entity.getFecha()));
        dto.setValor(entity.getValor());
        return dto;
    }

    @Override
    public ArgentinaDatosIndice toEntity(ArgentinaDatosIndiceDto dto) {
        if (dto == null) {
            return null;
        }
        ArgentinaDatosIndice entity = new ArgentinaDatosIndice();
        entity.setFecha(DateParsingUtils.parseToLocalDate(dto.getFecha()));
        entity.setValor(dto.getValor());
        return entity;
    }
}
