package com.emprendeStore.controller;

import com.emprendeStore.application.service.VentaService;
import com.emprendeStore.domain.enums.EstadoVenta;
import com.emprendeStore.web.controller.VentaController;
import com.emprendeStore.web.dto.response.VentaResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VentaControllerTest {

    @InjectMocks
    private VentaController ventaController;

    @Mock
    private VentaService ventaService;

    @Test
    void listarVentasxEmprendedor_Success() {
        VentaResponseDto ventaResponseDto = VentaResponseDto.builder()
                .idVenta(1L)
                .idPedido(1L)
                .idEmpre(1L)
                .nombreEmprendedor("Test Emprendedor")
                .subTotal(BigDecimal.TEN)
                .total(BigDecimal.TEN)
                .estadoVenta(EstadoVenta.PENDIENTE)
                .detalles(Collections.emptyList())
                .build();

        when(ventaService.listarVentasxIdEmprendedor(anyLong())).thenReturn(Collections.singletonList(ventaResponseDto));

        ResponseEntity<List<VentaResponseDto>> response = ventaController.listarVentasxEmprendedor(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(ventaResponseDto, response.getBody().get(0));
    }
}
