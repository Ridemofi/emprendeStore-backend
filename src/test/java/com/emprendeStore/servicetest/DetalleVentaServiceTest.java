package com.emprendeStore.servicetest;

import com.emprendeStore.application.exception.ErrorNegocio;
import com.emprendeStore.application.mapper.DetalleVentaMapper;
import com.emprendeStore.application.service.impl.DetalleVentaServiceImpl;
import com.emprendeStore.domain.model.DetalleCarrito;
import com.emprendeStore.domain.model.DetalleVenta;
import com.emprendeStore.domain.model.Producto;
import com.emprendeStore.domain.model.Venta;
import com.emprendeStore.domain.repository.ProductoRepository;
import com.emprendeStore.web.dto.request.DetalleVentaRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DetalleVentaServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private DetalleVentaMapper detalleVentaMapper;

    @InjectMocks
    private DetalleVentaServiceImpl detalleVentaService;

    private DetalleCarrito detalleCarrito;
    private Venta venta;
    private Producto producto;
    private DetalleVenta detalleVenta;

    @BeforeEach
    void setUp() {
        producto = new Producto();
        producto.setIdProducto(1L);
        producto.setNombreProd("Test Product");
        producto.setStock(10);
        producto.setPrecio(BigDecimal.TEN);

        detalleCarrito = new DetalleCarrito();
        detalleCarrito.setProducto(producto);
        detalleCarrito.setCantidad(2);
        detalleCarrito.setPrecioUnitario(BigDecimal.TEN);

        venta = new Venta();
        venta.setIdVenta(1L);

        detalleVenta = new DetalleVenta();
    }

    @Test
    void crearDetalleVenta_Success() {
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);
        when(detalleVentaMapper.toEntity(any(DetalleVentaRequestDto.class), any(Producto.class), any(Venta.class))).thenReturn(detalleVenta);

        DetalleVenta result = detalleVentaService.crearDetalleVenta(detalleCarrito, venta);

        assertNotNull(result);
        assertEquals(8, producto.getStock());
    }

    @Test
    void crearDetalleVenta_InsufficientStock_ThrowsErrorNegocio() {
        detalleCarrito.setCantidad(15);

        assertThrows(ErrorNegocio.class, () -> {
            detalleVentaService.crearDetalleVenta(detalleCarrito, venta);
        });
    }
}
