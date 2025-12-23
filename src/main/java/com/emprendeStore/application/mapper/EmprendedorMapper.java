package com.emprendeStore.application.mapper;


import com.emprendeStore.domain.model.Emprendedor;
import com.emprendeStore.web.dto.request.RegisterEmprendedorRequestDto;
import com.emprendeStore.web.dto.response.EmprendedorResponseDto;

public interface EmprendedorMapper {
    Emprendedor toEntity(RegisterEmprendedorRequestDto dto);
    EmprendedorResponseDto toDto(Emprendedor e);
}
