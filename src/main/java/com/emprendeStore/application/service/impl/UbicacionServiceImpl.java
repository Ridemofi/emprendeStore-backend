package com.emprendeStore.application.service.impl;

import com.emprendeStore.application.mapper.UbicacionMapper;
import com.emprendeStore.application.service.UbicacionService;
import com.emprendeStore.domain.repository.*;
import com.emprendeStore.web.dto.response.PaisResponseDto;
import com.emprendeStore.web.dto.response.UbicacionResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UbicacionServiceImpl implements UbicacionService {
    private final PaisRepository pr;
    private final UbicacionMapper um;
    private final UbicacionNivel1Repository un1r;
    private final UbicacionNivel2Repository un2r;
    private final UbicacionNivel3Repository un3r;


    @Override
    public List<PaisResponseDto> listarPaises() {
        return pr.findTodoConConfig()
                .stream()
                .map(pais -> {
                    return um.toPaisDto(pais, pais.getConfigPais());
                })
                .toList();
    }

    @Override
    public List<UbicacionResponseDto> listarUbisNivel1(Long idPais) {
        return un1r.findByPaisIdPais(idPais)
                .stream()
                .map(um::toDtoUn1)
                .toList();
    }

    @Override
    public List<UbicacionResponseDto> listarUbisNivel2(Long idNivel1) {
        return un2r.findByUbicacionNivel1IdN1(idNivel1)
                .stream()
                .map(um::toDtoUn2)
                .toList();
    }

    @Override
    public List<UbicacionResponseDto> listarUbisNivel3(Long idNivel2) {
        return un3r.findByUbicacionNivel2IdN2(idNivel2)
                .stream()
                .map(um::toDtoUn3)
                .toList();
    }
}
