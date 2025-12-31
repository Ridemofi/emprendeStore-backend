package com.emprendeStore.application.service;

import com.emprendeStore.web.dto.response.PaisResponseDto;
import com.emprendeStore.web.dto.response.UbicacionResponseDto;

import java.util.List;

public interface UbicacionService {
    List<PaisResponseDto> listarPaises();
    List<UbicacionResponseDto> listarUbisNivel1(Long idPais);
    List<UbicacionResponseDto> listarUbisNivel2(Long idNivel1);
    List<UbicacionResponseDto> listarUbisNivel3(Long idNivel2);
}
