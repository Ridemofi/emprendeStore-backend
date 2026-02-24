package com.emprendeStore.controller;

import com.emprendeStore.application.service.PedidoService;
import com.emprendeStore.domain.enums.EstadoPedido;
import com.emprendeStore.domain.enums.MetodoPago;
import com.emprendeStore.web.controller.PedidoController;
import com.emprendeStore.web.dto.request.PedidoRequestDto;
import com.emprendeStore.web.dto.response.PedidoResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PedidoControllerTest {

    @InjectMocks
    private PedidoController pedidoController;

    @Mock
    private PedidoService pedidoService;

    @Test
    void crearVenta() {
        // Arrange
        PedidoRequestDto requestDto = new PedidoRequestDto();
        PedidoResponseDto responseMock = generarPedidoResponseDto(1L, 10L);

        when(pedidoService.crearPedido(any(PedidoRequestDto.class))).thenReturn(responseMock);

        // Act
        ResponseEntity<PedidoResponseDto> response = pedidoController.crearVenta(requestDto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getIdPedido());
        assertEquals(BigDecimal.valueOf(150.00), response.getBody().getTotalPagado());
    }

    @Test
    void listarPedidosPorUsuario() {
        // Arrange
        Long idUsuario = 10L;
        List<PedidoResponseDto> listaMock = List.of(
                generarPedidoResponseDto(1L, idUsuario),
                generarPedidoResponseDto(2L, idUsuario)
        );

        when(pedidoService.listarPedidoXidUsuario(idUsuario)).thenReturn(listaMock);

        // Act
        ResponseEntity<List<PedidoResponseDto>> response = pedidoController.listarPedidosPorUsuario(idUsuario);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals(idUsuario, response.getBody().get(0).getIdUsuario());
    }

    @Test
    void obtenerDetallePedido() {
        // Arrange
        Long idPedido = 1L;
        PedidoResponseDto responseMock = generarPedidoResponseDto(idPedido, 10L);

        when(pedidoService.obtenerDetallePedido(idPedido)).thenReturn(responseMock);

        // Act
        ResponseEntity<PedidoResponseDto> response = pedidoController.obtenerDetallePedido(idPedido);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(idPedido, response.getBody().getIdPedido());
        assertEquals(EstadoPedido.PENDIENTE, response.getBody().getEstadoPedido());
    }

    // Método auxiliar actualizado con tus campos reales
    private PedidoResponseDto generarPedidoResponseDto(Long idPedido, Long idUsuario) {
        return PedidoResponseDto.builder()
                .idPedido(idPedido)
                .idUsuario(idUsuario)
                .idTransaccion("TX-12345")
                .subtotalGlobal(BigDecimal.valueOf(140.00))
                .costoEnvioTotal(BigDecimal.valueOf(10.00))
                .totalPagado(BigDecimal.valueOf(150.00))
                .metodoPago(MetodoPago.TARJETA)
                .estadoPedido(EstadoPedido.PENDIENTE)
                .fechaPedido(LocalDateTime.now())
                .direccionEnvio("Av. Siempreviva 123")
                .empresaEnvio("Olva Courier")
                .ventas(List.of())
                .build();
    }
}