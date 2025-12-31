package com.emprendeStore.application.mapper;

import com.emprendeStore.domain.model.*;
import com.emprendeStore.web.dto.request.DireccionRequestDto;
import com.emprendeStore.web.dto.response.DireccionResponseDto;

public interface DireccionMapper {
    Direccion toEntity(DireccionRequestDto dto, Usuario u, Pais p, UbicacionNivel1 un1, UbicacionNivel2 un2, UbicacionNivel3 un3);
    DireccionResponseDto toDto(Direccion d);
    void updateEntity(Direccion d, DireccionRequestDto dto, Pais p, UbicacionNivel1 un1, UbicacionNivel2 un2, UbicacionNivel3 un3);
}
