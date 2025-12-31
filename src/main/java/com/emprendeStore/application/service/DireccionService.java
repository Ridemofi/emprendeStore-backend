package com.emprendeStore.application.service;

import com.emprendeStore.web.dto.request.DireccionRequestDto;
import com.emprendeStore.web.dto.response.DireccionResponseDto;

import java.util.List;

public interface DireccionService {
    DireccionResponseDto saveDireccion(DireccionRequestDto dto);
    List<DireccionResponseDto> listarDirecciones(Long idUsuario);
    DireccionResponseDto obtenerDireccionPrincipal(Long idUsuario);
    DireccionResponseDto updateDireccion(Long idDireccion, DireccionRequestDto dto);
    void deleteDireccion(Long idDireccion);
}
