package com.emprendeStore.controller;

import com.emprendeStore.application.service.DireccionService;
import com.emprendeStore.web.controller.DireccionController;
import com.emprendeStore.web.dto.request.DireccionRequestDto;
import com.emprendeStore.web.dto.response.DireccionResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DireccionControllerTest {

    @InjectMocks
    private DireccionController direccionController;

    @Mock
    private DireccionService direccionService;

    private DireccionRequestDto direccionRequestDto;
    private DireccionResponseDto direccionResponseDto;

    @BeforeEach
    void setUp() {
        direccionRequestDto = DireccionRequestDto.builder()
                .idUsu(1L)
                .nombreContacto("Test Contact")
                .telefono("123456789")
                .idPais(1L)
                .direccion1("Test Address")
                .build();

        direccionResponseDto = DireccionResponseDto.builder()
                .idDireccion(1L)
                .nombreContacto("Test Contact")
                .telefono("123456789")
                .direccion1("Test Address")
                .build();
    }

    @Test
    void crearDireccion_Success() {
        when(direccionService.saveDireccion(any(DireccionRequestDto.class))).thenReturn(direccionResponseDto);

        ResponseEntity<DireccionResponseDto> response = direccionController.crearDireccion(direccionRequestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(direccionResponseDto, response.getBody());
    }

    @Test
    void listarDirecciones_Success() {
        when(direccionService.listarDirecciones(anyLong())).thenReturn(Collections.singletonList(direccionResponseDto));

        ResponseEntity<List<DireccionResponseDto>> response = direccionController.listarDirecciones(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void obtenerDireccionPrincipal_Success() {
        when(direccionService.obtenerDireccionPrincipal(anyLong())).thenReturn(direccionResponseDto);

        ResponseEntity<DireccionResponseDto> response = direccionController.obtenerDireccionPrincipal(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(direccionResponseDto, response.getBody());
    }

    @Test
    void actualizarDireccion_Success() {
        when(direccionService.updateDireccion(anyLong(), any(DireccionRequestDto.class))).thenReturn(direccionResponseDto);

        ResponseEntity<DireccionResponseDto> response = direccionController.actualizarDireccion(1L, direccionRequestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(direccionResponseDto, response.getBody());
    }

    @Test
    void establecerPrincipal_Success() {
        doNothing().when(direccionService).establecerPrincipal(anyLong());

        ResponseEntity<Void> response = direccionController.establecerPrincipal(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(direccionService, times(1)).establecerPrincipal(1L);
    }

    @Test
    void eliminarDireccion_Success() {
        doNothing().when(direccionService).deleteDireccion(anyLong());

        ResponseEntity<Void> response = direccionController.eliminarDireccion(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(direccionService, times(1)).deleteDireccion(1L);
    }
}
