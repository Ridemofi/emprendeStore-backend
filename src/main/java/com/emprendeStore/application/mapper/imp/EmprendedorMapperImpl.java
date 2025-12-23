package com.emprendeStore.application.mapper.imp;

import com.emprendeStore.application.mapper.EmprendedorMapper;
import com.emprendeStore.domain.model.Emprendedor;
import com.emprendeStore.web.dto.request.RegisterEmprendedorRequestDto;
import com.emprendeStore.web.dto.response.EmprendedorResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmprendedorMapperImpl implements EmprendedorMapper {

    @Override
    public Emprendedor toEntity(RegisterEmprendedorRequestDto dto) {
        return Emprendedor.builder()
                .imgenemp(dto.getImgemp())
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
                .imgemp(e.getImgenemp())
                .nombep(e.getNombreemp())
                .correo(e.getCorreoemp())
                .nrocell(e.getNrocellemp())
                .fechaingreso(e.getFecharegistroemp())
                .build();
    }
}
