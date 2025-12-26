package com.emprendeStore.controller;

import com.emprendeStore.application.service.UsuarioService;
import com.emprendeStore.web.controller.UsuarioController;
import com.emprendeStore.web.dto.request.RegisterUsuarioRequestDto;
import com.emprendeStore.web.dto.request.UpdateUsuarioRequestDto;
import com.emprendeStore.web.dto.response.UsuarioResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    private UsuarioResponseDto usuarioResponseDto;

    @BeforeEach
    void setUp() {
        usuarioResponseDto = UsuarioResponseDto.builder()
                .id(1L)
                .correo("test@test.com")
                .nombreReal("Test User")
                .build();
    }

    @Test
    void listar_Usus_deberiaRetornarLista() {
        when(usuarioService.listarUsuarios()).thenReturn(List.of(usuarioResponseDto));

        ResponseEntity<List<UsuarioResponseDto>> response = usuarioController.listarUsus();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("test@test.com", response.getBody().get(0).getCorreo());
    }

    @Test
    void saveUsu_deberiaRetornarCreated() {
        RegisterUsuarioRequestDto request = RegisterUsuarioRequestDto.builder()
                .correo("test@test.com")
                .password("123456")
                .build();

        when(usuarioService.saveUsu(any(RegisterUsuarioRequestDto.class))).thenReturn(usuarioResponseDto);

        ResponseEntity<UsuarioResponseDto> response = usuarioController.saveUsu(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("test@test.com", response.getBody().getCorreo());
    }

    @Test
    void updateUsu_deberiaRetornarOk() {
        UpdateUsuarioRequestDto request = UpdateUsuarioRequestDto.builder()
                .correo("updated@test.com")
                .build();
        
        UsuarioResponseDto updatedResponse = UsuarioResponseDto.builder()
                .id(1L)
                .correo("updated@test.com")
                .build();

        when(usuarioService.updateUsu(any(UpdateUsuarioRequestDto.class), eq(1L))).thenReturn(updatedResponse);

        ResponseEntity<String> response = usuarioController.updateUsu(request, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Usuario: 1 actualizado correctamente", response.getBody());
    }

    @Test
    void delete_Usu_deberiaRetornarOk() {
        when(usuarioService.deleteUsu(1L)).thenReturn(usuarioResponseDto);

        ResponseEntity<String> response = usuarioController.deleteUsu(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Usuario con ID 1 eliminado correctamente", response.getBody());
    }
}
