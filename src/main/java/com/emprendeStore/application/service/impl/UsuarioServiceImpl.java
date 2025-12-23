package com.emprendeStore.application.service.impl;

import com.emprendeStore.application.exception.ErrorNegocio;
import com.emprendeStore.application.mapper.UsuarioMapper;
import com.emprendeStore.application.service.UsuarioService;
import com.emprendeStore.domain.model.Usuario;
import com.emprendeStore.domain.repository.UsuarioRepository;
import com.emprendeStore.web.dto.request.LoginUsuarioRequestDto;
import com.emprendeStore.web.dto.request.RegisterUsuarioRequestDto;
import com.emprendeStore.web.dto.request.UpdateUsuarioRequestDto;
import com.emprendeStore.web.dto.response.UsuarioResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioMapper um;
    private final UsuarioRepository ur;
    private final PasswordEncoder pe;
    @Override
    @Transactional
    public UsuarioResponseDto save(RegisterUsuarioRequestDto dto) {
        Usuario u=um.toEntity(dto);
        u.setPassword(pe.encode(dto.getPassword()));
        ur.save(u);
        return um.toDto(u);
    }

    @Override
    @Transactional
    public UsuarioResponseDto delete(Long id) {
        Usuario u = ur.findById(id).orElseThrow(() -> new ErrorNegocio("El Usuario con el id: " + id + " no existe"));
        ur.delete(u);
        return um.toDto(u);
    }

    @Override
    public List<UsuarioResponseDto> listarUsuarios() {
        return ur.findAll()
                .stream()
                .map(um::toDto)
                .toList();
    }

    @Override
    @Transactional
    public UsuarioResponseDto update(UpdateUsuarioRequestDto dtoUpdate, Long id) {
        Usuario u = ur.findById(id).orElseThrow(() -> new ErrorNegocio("El Usuario con el id: " + id + " no existe"));
        um.updateEntity(dtoUpdate, u);
        ur.save(u);
        return um.toDto(u);
    }

    @Override
    public UsuarioResponseDto login(LoginUsuarioRequestDto dto) {
        Usuario u = ur.findByCorreoOrNomUsu(dto.getIdentificador(), dto.getIdentificador())
                .orElseThrow(() -> new ErrorNegocio("El usuario o correo no existe"));
        if (!pe.matches(dto.getPassword(), u.getPassword())) {
            throw new ErrorNegocio("La contraseña es incorrecta");
        }
        return um.toDto(u);
    }
}
