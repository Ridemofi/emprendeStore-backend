package com.emprendeStore.servicetest;

import com.emprendeStore.application.mapper.VentaMapper;
import com.emprendeStore.application.service.DetalleVentaService;
import com.emprendeStore.application.service.impl.VentaServiceImpl;
import com.emprendeStore.domain.enums.EstadoVenta;
import com.emprendeStore.domain.model.*;
import com.emprendeStore.domain.repository.EmprendedorRepository;
import com.emprendeStore.domain.repository.VentaRepository;
import com.emprendeStore.web.dto.response.VentaResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VentaServiceTest {

    @Mock
    private VentaRepository ventaRepository;

    @Mock
    private EmprendedorRepository emprendedorRepository;

    @Mock
    private VentaMapper ventaMapper;

    @Mock
    private DetalleVentaService detalleVentaService;

    @InjectMocks
    private VentaServiceImpl ventaService;

    private Pedido pedido;
    private Emprendedor emprendedor;
    private DetalleCarrito detalleCarrito;
    private Venta venta;
    private DetalleVenta detalleVenta;
    private VentaResponseDto ventaResponseDto;

    @BeforeEach
    void setUp() {
        pedido = new Pedido();
        pedido.setIdPedido(1L);

        emprendedor = new Emprendedor();
        emprendedor.setIdempre(1L);
        emprendedor.setSaldo(BigDecimal.ZERO);

        Producto producto = new Producto();
        producto.setIdProducto(1L);
        producto.setPrecio(BigDecimal.TEN);

        detalleCarrito = new DetalleCarrito();
        detalleCarrito.setProducto(producto);
        detalleCarrito.setCantidad(2);

        venta = new Venta();
        venta.setIdVenta(1L);
        venta.setEstadoVenta(EstadoVenta.PENDIENTE);

        detalleVenta = new DetalleVenta();
        detalleVenta.setSubtotal(BigDecimal.valueOf(20));

        ventaResponseDto = new VentaResponseDto();
    }

    @Test
    void generarVenta_Success() {
        when(ventaMapper.toEntity(any(Pedido.class), any(Emprendedor.class), any(EstadoVenta.class))).thenReturn(venta);
        when(detalleVentaService.crearDetalleVenta(any(DetalleCarrito.class), any(Venta.class))).thenReturn(detalleVenta);
        when(ventaRepository.save(any(Venta.class))).thenReturn(venta);
        when(emprendedorRepository.save(any(Emprendedor.class))).thenReturn(emprendedor);

        Venta result = ventaService.generarVenta(pedido, emprendedor, Collections.singletonList(detalleCarrito));

        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(20), result.getSubtotal());
        assertEquals(BigDecimal.valueOf(20), result.getTotal());
        assertEquals(BigDecimal.valueOf(20), emprendedor.getSaldo());
    }

    @Test
    void listarVentasxIdEmprendedor_Success() {
        when(ventaRepository.findAllByEmprendedor(anyLong())).thenReturn(Collections.singletonList(venta));
        when(ventaMapper.toDto(any(Venta.class))).thenReturn(ventaResponseDto);

        List<VentaResponseDto> result = ventaService.listarVentasxIdEmprendedor(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
    }
}
