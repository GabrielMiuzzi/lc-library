package com.lc.library.utils.mapper;

import com.lc.library.models.dto.ArgentinaDatosIndiceDto;
import com.lc.library.models.entities.ArgentinaDatosIndice;

public interface ArgentinaDatosIndiceMapper {
    ArgentinaDatosIndiceDto toDto(ArgentinaDatosIndice entity);

    ArgentinaDatosIndice toEntity(ArgentinaDatosIndiceDto dto);
}
