package com.emprendeStore.servicetest;

import com.emprendeStore.application.exception.ErrorNegocio;
import com.emprendeStore.application.mapper.PedidoMapper;
import com.emprendeStore.application.mapper.VentaMapper;
import com.emprendeStore.application.service.CarritoService;
import com.emprendeStore.application.service.VentaService;
import com.emprendeStore.application.service.impl.PedidoServiceImpl;
import com.emprendeStore.domain.model.*;
import com.emprendeStore.domain.repository.*;
import com.emprendeStore.web.dto.request.PedidoRequestDto;
import com.emprendeStore.web.dto.response.PedidoResponseDto;
import com.emprendeStore.web.dto.response.VentaResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest {

    @InjectMocks
    private PedidoServiceImpl pedidoService;

    @Mock private DetalleCarritoRepository dcr;
    @Mock private UsuarioRepository ur;
    @Mock private DireccionRepository dr;
    @Mock private PedidoRepository peRepo;
    @Mock private CarritoService cs;
    @Mock private PedidoMapper pemp;
    @Mock private VentaService vs;
    @Mock private VentaMapper vm;

    @Test
    void crearPedido_Exitoso() {
        // Arrange
        // --- CORRECCIÓN AQUÍ: Usamos setters en lugar de constructor ---
        PedidoRequestDto request = new PedidoRequestDto();
        request.setIdUsu(1L);
        request.setIdDireccion(10L);

        Usuario usuario = new Usuario();
        usuario.setIdUsu(1L);

        Direccion direccion = new Direccion();
        direccion.setNombreContacto("Ronald Rios");

        Emprendedor emp1 = new Emprendedor();
        Producto p1 = new Producto(); p1.setEmprendedor(emp1);
        DetalleCarrito item1 = new DetalleCarrito(); item1.setProducto(p1);

        List<DetalleCarrito> detalles = List.of(item1);
        Pedido pedidoEntidad = new Pedido();
        Venta ventaEntidad = new Venta();
        ventaEntidad.setSubtotal(new BigDecimal("100.00"));

        when(ur.getReferenceById(1L)).thenReturn(usuario);
        when(dr.findByIdWithDetalles(10L)).thenReturn(Optional.of(direccion));
        when(dcr.findDetallesSeleccionadosPorUsuario(1L)).thenReturn(detalles);
        when(dcr.calcularCostoEnvioPorUsuario(1L)).thenReturn(new BigDecimal("10.00"));
        when(pemp.toEntity(any(), any(), any(), any(), any())).thenReturn(pedidoEntidad);
        when(peRepo.save(any(Pedido.class))).thenReturn(pedidoEntidad);
        when(vs.generarVenta(any(), any(), any())).thenReturn(ventaEntidad);
        when(pemp.toDto(any(), any())).thenReturn(generarPedidoResponseDto(1L));

        // Act
        PedidoResponseDto result = pedidoService.crearPedido(request);

        // Assert
        assertNotNull(result);
        verify(peRepo, times(2)).save(any(Pedido.class));
        verify(cs).limpiarCarrito(1L);
        verify(vs).generarVenta(any(), any(), any());
    }

    @Test
    void crearPedido_ErrorCarritoVacio() {

        PedidoRequestDto request = new PedidoRequestDto();
        request.setIdUsu(1L);
        request.setIdDireccion(10L);

        Usuario usuarioMock = new Usuario();
        usuarioMock.setIdUsu(1L);

        when(ur.getReferenceById(1L)).thenReturn(usuarioMock);
        when(dr.findByIdWithDetalles(10L)).thenReturn(Optional.of(new Direccion()));


        when(dcr.findDetallesSeleccionadosPorUsuario(1L)).thenReturn(List.of());


        ErrorNegocio exception = assertThrows(ErrorNegocio.class, () -> pedidoService.crearPedido(request));


        assertEquals("No hay productos seleccionados para comprar", exception.getMessage());
    }

    @Test
    void obtenerDetallePedido_Exitoso() {
        // Arrange
        Long idPedido = 1L;
        Pedido pedido = new Pedido();

        pedido.setVentas(java.util.Set.of(new Venta()));

        when(peRepo.findByIdWithVentasAndDetalles(idPedido)).thenReturn(Optional.of(pedido));
        when(vm.toDto(any())).thenReturn(new VentaResponseDto());
        when(pemp.toDto(any(), any())).thenReturn(generarPedidoResponseDto(idPedido));

        // Act
        PedidoResponseDto result = pedidoService.obtenerDetallePedido(idPedido);

        // Assert
        assertNotNull(result);
        assertEquals(idPedido, result.getIdPedido());
        verify(peRepo).findByIdWithVentasAndDetalles(idPedido);
    }

    @Test
    void listarPedidoXidUsuario() {
        // Arrange
        Long idUsu = 1L;
        when(peRepo.findAllByUsuario(idUsu)).thenReturn(List.of(new Pedido(), new Pedido()));
        when(pemp.toDtoResumen(any())).thenReturn(new PedidoResponseDto());

        // Act
        List<PedidoResponseDto> result = pedidoService.listarPedidoXidUsuario(idUsu);

        // Assert
        assertEquals(2, result.size());
        verify(peRepo).findAllByUsuario(idUsu);
    }

    private PedidoResponseDto generarPedidoResponseDto(Long id) {
        return PedidoResponseDto.builder()
                .idPedido(id)
                .idTransaccion("#ORD-ABC12")
                .totalPagado(new BigDecimal("110.00"))
                .ventas(List.of())
                .build();
    }
}