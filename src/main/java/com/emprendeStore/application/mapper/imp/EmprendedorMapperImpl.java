package com.emprendeStore.application.mapper.imp;

import com.emprendeStore.application.mapper.EmprendedorMapper;
import com.emprendeStore.domain.model.Emprendedor;
import com.emprendeStore.web.dto.request.RegisterEmprendedorRequestDto;
import com.emprendeStore.web.dto.request.UpdateEmprendedorRequestDto;
import com.emprendeStore.web.dto.response.EmprendedorResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmprendedorMapperImpl implements EmprendedorMapper {

    @Override
    public Emprendedor toEntity(RegisterEmprendedorRequestDto dto) {
        return Emprendedor.builder()
                .imgempre(dto.getImgemp())
                .nombreemp(dto.getNombep())
                .correoemp(dto.getCorreo())
                .nrocellemp(dto.getNrocell())
                .passwordempre(dto.getPassword())
                .build();
    }

    @Override
    public EmprendedorResponseDto toDto(Emprendedor e) {
        return EmprendedorResponseDto.builder()
                .idemp(e.getIdempre())
                .imgemp(e.getImgempre())
                .nombep(e.getNombreemp())
                .correo(e.getCorreoemp())
                .nrocell(e.getNrocellemp())
                .fechaingreso(e.getFecharegistroemp())
                .build();
    }

    @Override
    public void UpdateEntity(UpdateEmprendedorRequestDto dto, Emprendedor e) {
        if (dto.getNombEmpre() != null) {
            e.setNombreemp(dto.getNombEmpre());
        }
        if (dto.getCorreo() != null) {
            e.setCorreoemp(dto.getCorreo());
        }
        if (dto.getTelefono() != null) {
            e.setNrocellemp(dto.getTelefono());
        }
    }

}
