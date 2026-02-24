package com.emprendeStore.controller;

import com.emprendeStore.application.mapper.AuthMapper;
import com.emprendeStore.domain.model.Emprendedor;
import com.emprendeStore.domain.model.Usuario;
import com.emprendeStore.domain.repository.EmprendedorRepository;
import com.emprendeStore.domain.repository.UsuarioRepository;
import com.emprendeStore.security.JwtTokenProvider;
import com.emprendeStore.web.controller.AutenticacionController;
import com.emprendeStore.web.dto.request.LoginEmprendedorRequestDto;
import com.emprendeStore.web.dto.request.LoginUsuarioRequestDto;
import com.emprendeStore.web.dto.response.AuthEmprendedorResponseDto;
import com.emprendeStore.web.dto.response.AuthUsuarioResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AutenticacionControllerTest {

    @InjectMocks
    private AutenticacionController autenticacionController;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private EmprendedorRepository emprendedorRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private AuthMapper authMapper;

    @Test
    void loginEmpre() {
        // Arrange
        String correo = "test@emprende.com";
        String token = "jwt-token-test";
        LoginEmprendedorRequestDto requestDto = new LoginEmprendedorRequestDto(correo, "password");

        Authentication authentication = mock(Authentication.class);
        Emprendedor emprendedor = new Emprendedor();
        AuthEmprendedorResponseDto responseMock = generarAuthEmprendedorResponseDto(token);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(jwtTokenProvider.generateToken(authentication)).thenReturn(token);
        when(emprendedorRepository.findByCorreoemp(correo)).thenReturn(Optional.of(emprendedor));
        when(authMapper.toAuthEmprendedorDto(emprendedor, token)).thenReturn(responseMock);

        // Act
        ResponseEntity<AuthEmprendedorResponseDto> response = autenticacionController.loginEmpre(requestDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(token, response.getBody().getToken());
    }

    @Test
    void loginUsu() {
        // Arrange
        String correo = "usuario@test.com";
        String token = "jwt-token-usuario";
        LoginUsuarioRequestDto requestDto = new LoginUsuarioRequestDto(correo, "password");

        Authentication authentication = mock(Authentication.class);
        Usuario usuario = new Usuario();
        AuthUsuarioResponseDto responseMock = generarAuthUsuarioResponseDto(token);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(jwtTokenProvider.generateToken(authentication)).thenReturn(token);
        when(usuarioRepository.findByCorreo(correo)).thenReturn(Optional.of(usuario));
        when(authMapper.toAuthUsuarioDto(usuario, token)).thenReturn(responseMock);

        // Act
        ResponseEntity<AuthUsuarioResponseDto> response = autenticacionController.loginUsu(requestDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(token, response.getBody().getToken());
    }

    // Métodos auxiliares para generar los DTOs de respuesta (siguiendo tu estilo)

    private AuthEmprendedorResponseDto generarAuthEmprendedorResponseDto(String token) {
        // Asumiendo que usas @Builder o un constructor con parámetros
        return AuthEmprendedorResponseDto.builder()
                .token(token)
                .build();
    }

    private AuthUsuarioResponseDto generarAuthUsuarioResponseDto(String token) {
        return AuthUsuarioResponseDto.builder()
                .token(token)
                .build();
    }
}