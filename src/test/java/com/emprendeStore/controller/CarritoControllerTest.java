package com.emprendeStore.controller;

import com.emprendeStore.application.service.CarritoService;
import com.emprendeStore.web.controller.CarritoController;
import com.emprendeStore.web.dto.response.CarritoResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CarritoControllerTest {

    @InjectMocks
    private CarritoController carritoController;

    @Mock
    private CarritoService carritoService;

    @Test
    void obtenerCarrito() {
        // Arrange
        CarritoResponseDto responseMock = generarCarritoResponseDto();
        when(carritoService.obtenerCarrito(1L)).thenReturn(responseMock);

        // Act
        ResponseEntity<CarritoResponseDto> response = carritoController.obtenerCarrito(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getIdCarrito());
        assertEquals(BigDecimal.valueOf(100), response.getBody().getTotal());
    }

    @Test
    void agregarProducto() {
        // Arrange
        CarritoResponseDto responseMock = generarCarritoResponseDto();
        when(carritoService.agregarProducto(1L, 10L)).thenReturn(responseMock);

        // Act
        ResponseEntity<CarritoResponseDto> response = carritoController.agregarProducto(1L, 10L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getIdCarrito());
        assertEquals(BigDecimal.valueOf(100), response.getBody().getTotal());
    }

    @Test
    void actualizarCantidadItem() {
        // Arrange
        CarritoResponseDto responseMock = generarCarritoResponseDto();
        when(carritoService.actualizarCantidadItem(1L, 5L, 3)).thenReturn(responseMock);

        // Act
        ResponseEntity<CarritoResponseDto> response = carritoController.actualizarCantidadItem(1L, 5L, 3);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getIdCarrito());
        assertEquals(BigDecimal.valueOf(100), response.getBody().getTotal());
    }

    @Test
    void removerItem() {
        // Arrange
        CarritoResponseDto responseMock = generarCarritoResponseDto();
        when(carritoService.removerItem(1L, 5L)).thenReturn(responseMock);

        // Act
        ResponseEntity<CarritoResponseDto> response = carritoController.removerItem(1L, 5L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getIdCarrito());
        assertEquals(BigDecimal.valueOf(100), response.getBody().getTotal());
    }

    private CarritoResponseDto generarCarritoResponseDto() {
        return CarritoResponseDto.builder()
                .idCarrito(1L)
                .total(BigDecimal.valueOf(100))
                .detalles(List.of())
                .build();
    }
}
