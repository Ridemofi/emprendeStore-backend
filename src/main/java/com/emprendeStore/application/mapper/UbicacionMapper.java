package com.emprendeStore.application.mapper;

import com.emprendeStore.domain.model.*;
import com.emprendeStore.web.dto.response.PaisResponseDto;
import com.emprendeStore.web.dto.response.UbicacionResponseDto;

public interface UbicacionMapper {
    PaisResponseDto toPaisDto(Pais p, ConfigPais cp);
    UbicacionResponseDto toDtoUn1(UbicacionNivel1 un1);
    UbicacionResponseDto toDtoUn2(UbicacionNivel2 un2);
    UbicacionResponseDto toDtoUn3(UbicacionNivel3 un3);

}
