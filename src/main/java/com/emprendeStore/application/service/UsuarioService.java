package com.emprendeStore.application.service;

import com.emprendeStore.web.dto.request.RegisterUsuarioRequestDto;
import com.emprendeStore.web.dto.request.UpdateUsuarioRequestDto;
import com.emprendeStore.web.dto.response.UsuarioResponseDto;

import java.util.List;

public interface UsuarioService {
    UsuarioResponseDto save(RegisterUsuarioRequestDto dto);
    UsuarioResponseDto delete(Long id);
    List<UsuarioResponseDto> listarUsuarios();
    UsuarioResponseDto update(UpdateUsuarioRequestDto dtoUpdate, Long id);
}
