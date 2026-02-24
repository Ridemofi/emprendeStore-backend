package com.emprendeStore.controller;

import com.emprendeStore.application.service.UbicacionService;
import com.emprendeStore.web.controller.UbicacionController;
import com.emprendeStore.web.dto.response.PaisResponseDto;
import com.emprendeStore.web.dto.response.UbicacionResponseDto;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UbicacionControllerTest {

    @InjectMocks
    private UbicacionController ubicacionController;

    @Mock
    private UbicacionService ubicacionService;

    private PaisResponseDto paisResponseDto;
    private UbicacionResponseDto ubicacionResponseDto;

    @BeforeEach
    void setUp() {
        paisResponseDto = PaisResponseDto.builder()
                .idPais(1L)
                .nombrePais("Test Country")
                .build();

        ubicacionResponseDto = UbicacionResponseDto.builder()
                .idUbi(1L)
                .nombreUbi("Test Location")
                .build();
    }

    @Test
    void listarPaises_Success() {
        when(ubicacionService.listarPaises()).thenReturn(Collections.singletonList(paisResponseDto));

        ResponseEntity<List<PaisResponseDto>> response = ubicacionController.listarPaises();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void listarUbisNivel1_Success() {
        when(ubicacionService.listarUbisNivel1(anyLong())).thenReturn(Collections.singletonList(ubicacionResponseDto));

        ResponseEntity<List<UbicacionResponseDto>> response = ubicacionController.listarUbisNivel1(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void listarUbisNivel2_Success() {
        when(ubicacionService.listarUbisNivel2(anyLong())).thenReturn(Collections.singletonList(ubicacionResponseDto));

        ResponseEntity<List<UbicacionResponseDto>> response = ubicacionController.listarUbisNivel2(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void listarUbisNivel3_Success() {
        when(ubicacionService.listarUbisNivel3(anyLong())).thenReturn(Collections.singletonList(ubicacionResponseDto));

        ResponseEntity<List<UbicacionResponseDto>> response = ubicacionController.listarUbisNivel3(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }
}
