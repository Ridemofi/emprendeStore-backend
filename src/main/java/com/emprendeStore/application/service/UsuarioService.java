package com.emprendeStore.application.service;

import com.emprendeStore.web.dto.request.RegisterUsuarioRequestDto;
import com.emprendeStore.web.dto.request.UpdateUsuarioRequestDto;
import com.emprendeStore.web.dto.response.UsuarioResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UsuarioService {
    UsuarioResponseDto saveUsu(RegisterUsuarioRequestDto dto);
    UsuarioResponseDto updateUsu(UpdateUsuarioRequestDto dtoUpdate, Long id);
    UsuarioResponseDto saveAndupdateImagenUsuario(Long idUsuario, MultipartFile imagen);
    UsuarioResponseDto deleteUsu(Long id);
    List<UsuarioResponseDto> listarUsuarios();
}
