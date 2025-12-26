package com.emprendeStore.application.mapper;

import com.emprendeStore.domain.model.Emprendedor;
import com.emprendeStore.domain.model.Usuario;
import com.emprendeStore.web.dto.response.AuthEmprendedorResponseDto;
import com.emprendeStore.web.dto.response.AuthUsuarioResponseDto;

public interface AuthMapper {
    AuthEmprendedorResponseDto toAuthEmprendedorDto(Emprendedor emprendedor, String token);
    AuthUsuarioResponseDto toAuthUsuarioDto(Usuario usuario, String token);
}
