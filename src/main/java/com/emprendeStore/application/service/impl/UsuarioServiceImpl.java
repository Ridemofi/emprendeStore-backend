package com.emprendeStore.application.service.impl;

import com.emprendeStore.application.exception.ErrorNegocio;
import com.emprendeStore.application.mapper.UsuarioMapper;
import com.emprendeStore.application.service.EmailService;
import com.emprendeStore.application.service.UsuarioService;
import com.emprendeStore.domain.model.Usuario;
import com.emprendeStore.domain.repository.UsuarioRepository;
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
    private final EmailService es;

    @Override
    @Transactional
    public UsuarioResponseDto saveUsu(RegisterUsuarioRequestDto dto) {
        Usuario u=um.toEntity(dto);
        u.setPassword(pe.encode(dto.getPassword()));
        ur.save(u);
        es.enviarCorreoBienvenida(u.getCorreo(), u.getNombReal());
        return um.toDto(u);
    }

    @Override
    @Transactional
    public UsuarioResponseDto deleteUsu(Long id) {
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

    //actualiza gracias a @Transactional
    @Override
    @Transactional
    public UsuarioResponseDto updateUsu(UpdateUsuarioRequestDto dtoUpdate, Long id) {
        Usuario u = ur.findById(id).orElseThrow(() -> new ErrorNegocio("El Usuario con el id: " + id + " no existe"));
        um.updateEntity(dtoUpdate, u);
        return um.toDto(u);
    }


}
