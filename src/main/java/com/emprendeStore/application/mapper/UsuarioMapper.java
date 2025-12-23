package com.emprendeStore.application.mapper;

import com.emprendeStore.domain.model.Usuario;
import com.emprendeStore.web.dto.request.RegisterUsuarioRequestDto;
import com.emprendeStore.web.dto.request.UpdateUsuarioRequestDto;
import com.emprendeStore.web.dto.response.UsuarioResponseDto;

public interface UsuarioMapper {
    Usuario toEntity(RegisterUsuarioRequestDto dto);
    void updateEntity(UpdateUsuarioRequestDto dtoUpdate, Usuario u);
    UsuarioResponseDto toDto(Usuario u);
}
