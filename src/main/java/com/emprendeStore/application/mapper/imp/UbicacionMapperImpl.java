package com.emprendeStore.application.mapper.imp;

import com.emprendeStore.application.mapper.UbicacionMapper;
import com.emprendeStore.domain.model.*;
import com.emprendeStore.web.dto.response.ConfigPaisResponseDto;
import com.emprendeStore.web.dto.response.PaisResponseDto;
import com.emprendeStore.web.dto.response.UbicacionResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UbicacionMapperImpl implements UbicacionMapper {

    private ConfigPaisResponseDto toCpDto(ConfigPais cp) {
        if (cp == null) return null;
        return ConfigPaisResponseDto.builder()
                .usaNivel1(cp.getUsaNivel1())
                .usaNivel2(cp.getUsaNivel2())
                .usaNivel3(cp.getUsaNivel3())
                .labelNivel1(cp.getLabelNivel1())
                .labelNivel2(cp.getLabelNivel2())
                .labelNivel3(cp.getLabelNivel3())
                .zipRequerido(cp.getZipRequerido())
                .formatoTelefono(cp.getFormatoTelefono())
                .build();
    }

    @Override
    public PaisResponseDto toPaisDto(Pais p, ConfigPais cp) {
        return PaisResponseDto.builder()
                .idPais(p.getIdPais())
                .isoCode(p.getIsoCode())
                .nombrePais(p.getNombrePais())
                .codigoTelefono(p.getCodigoTelefono())
                .configuracion(toCpDto(cp))
                .build();
    }

    @Override
    public UbicacionResponseDto toDtoUn1(UbicacionNivel1 un1) {
        return UbicacionResponseDto.builder()
                .idUbi(un1.getIdN1())
                .nombreUbi(un1.getNombre())
                .code(un1.getCode())
                .build();
    }

    @Override
    public UbicacionResponseDto toDtoUn2(UbicacionNivel2 un2) {
        return UbicacionResponseDto.builder()
                .idUbi(un2.getIdN2())
                .nombreUbi(un2.getNombre())
                .build();
    }

    @Override
    public UbicacionResponseDto toDtoUn3(UbicacionNivel3 un3) {
        return UbicacionResponseDto.builder()
                .idUbi(un3.getIdN3())
                .nombreUbi(un3.getNombre())
                .codigoPostal(un3.getCodigoPostal())
                .build();
    }
}
