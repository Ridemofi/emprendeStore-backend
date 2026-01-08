package com.emprendeStore.application.mapper.imp;

import com.emprendeStore.application.mapper.DireccionMapper;
import com.emprendeStore.application.mapper.UbicacionMapper;
import com.emprendeStore.domain.model.*;
import com.emprendeStore.domain.repository.ConfigPaisRepository;
import com.emprendeStore.web.dto.request.DireccionRequestDto;
import com.emprendeStore.web.dto.response.DireccionResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DireccionMapperImpl implements DireccionMapper {

    private final UbicacionMapper um;

    @Override
    public Direccion toEntity(DireccionRequestDto dto, Usuario u, Pais p, UbicacionNivel1 un1, UbicacionNivel2 un2, UbicacionNivel3 un3) {
        return Direccion.builder()
                .usuario(u)
                .nombreContacto(dto.getNombreContacto())
                .telefonoContacto(dto.getTelefono())
                .documentoIdentidad(dto.getDocumentoIdentidad())
                .pais(p)
                .ubicacionNivel1(un1)
                .ubicacionNivel2(un2)
                .ubicacionNivel3(un3)
                .direccion1(dto.getDireccion1())
                .direccion2(dto.getDireccion2())
                .codigoPostal(dto.getCodigoPostal())
                .esPrincipal(dto.isEsPrincipal())
                .build();
    }

    @Override
    public DireccionResponseDto toDto(Direccion d) {
        ConfigPais configPais = d.getPais().getConfigPais();
        return DireccionResponseDto.builder()
                .idDireccion(d.getIdDir())
                .nombreContacto(d.getNombreContacto())
                .telefono(d.getTelefonoContacto())
                .documentoIdentidad(d.getDocumentoIdentidad())
                // Usamos el método renombrado toPaisDto
                .pais(um.toPaisDto(d.getPais(), configPais))
                // Extraemos los nombres de las ubicaciones si existen (pueden ser null)
                .ubicacionNivel1(d.getUbicacionNivel1() != null ? d.getUbicacionNivel1().getNombre() : null)
                .ubicacionNivel2(d.getUbicacionNivel2() != null ? d.getUbicacionNivel2().getNombre() : null)
                .ubicacionNivel3(d.getUbicacionNivel3() != null ? d.getUbicacionNivel3().getNombre() : null)
                .direccion1(d.getDireccion1())
                .direccion2(d.getDireccion2())
                .codigoPostal(d.getCodigoPostal())
                .esPrincipal(d.getEsPrincipal())
                .build();
    }

    @Override
    public void updateEntity(Direccion d, DireccionRequestDto dto, Pais p, UbicacionNivel1 un1, UbicacionNivel2 un2, UbicacionNivel3 un3) {
        d.setNombreContacto(dto.getNombreContacto());
        d.setTelefonoContacto(dto.getTelefono());
        d.setDocumentoIdentidad(dto.getDocumentoIdentidad());
        d.setPais(p);
        d.setUbicacionNivel1(un1);
        d.setUbicacionNivel2(un2);
        d.setUbicacionNivel3(un3);
        d.setDireccion1(dto.getDireccion1());
        d.setDireccion2(dto.getDireccion2());
        d.setCodigoPostal(dto.getCodigoPostal());
        d.setEsPrincipal(dto.isEsPrincipal());
    }
}
