package com.emprendeStore.web.controller;

import com.emprendeStore.application.mapper.AuthMapper;
import com.emprendeStore.domain.model.Emprendedor;
import com.emprendeStore.domain.model.Usuario;
import com.emprendeStore.domain.repository.EmprendedorRepository;
import com.emprendeStore.domain.repository.UsuarioRepository;
import com.emprendeStore.security.JwtTokenProvider;
import com.emprendeStore.web.dto.request.LoginEmprendedorRequestDto;
import com.emprendeStore.web.dto.request.LoginUsuarioRequestDto;
import com.emprendeStore.web.dto.response.AuthEmprendedorResponseDto;
import com.emprendeStore.web.dto.response.AuthUsuarioResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AutenticacionController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final EmprendedorRepository emprendedorRepository;
    private final UsuarioRepository usuarioRepository;
    private final AuthMapper am;

    @PostMapping("/loginempre")
    public ResponseEntity<AuthEmprendedorResponseDto> loginEmpre(@Valid @RequestBody LoginEmprendedorRequestDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getCorreo(), loginDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);
        Emprendedor empre = emprendedorRepository.findByCorreoemp(loginDto.getCorreo()).orElseThrow(() -> new RuntimeException("Emprendedor no encontrado"));
        return ResponseEntity.ok(am.toAuthEmprendedorDto(empre, token));
    }

    @PostMapping("/loginusu")
    public ResponseEntity<AuthUsuarioResponseDto> loginUsu(@Valid @RequestBody LoginUsuarioRequestDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getCorreo(), loginDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);
        Usuario usu = usuarioRepository.findByCorreo(loginDto.getCorreo()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return ResponseEntity.ok(am.toAuthUsuarioDto(usu, token));
    }
}
