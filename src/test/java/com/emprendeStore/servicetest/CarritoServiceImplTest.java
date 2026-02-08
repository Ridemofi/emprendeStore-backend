package com.emprendeStore.servicetest;

import com.emprendeStore.application.mapper.CarritoMapper;
import com.emprendeStore.application.mapper.ProductorMapper;
import com.emprendeStore.application.service.impl.CarritoServiceImpl;
import com.emprendeStore.domain.model.Carrito;
import com.emprendeStore.domain.model.DetalleCarrito;
import com.emprendeStore.domain.model.Producto;
import com.emprendeStore.domain.model.Usuario;
import com.emprendeStore.domain.repository.CarritoRepository;
import com.emprendeStore.domain.repository.DetalleCarritoRepository;
import com.emprendeStore.domain.repository.ProductoRepository;
import com.emprendeStore.domain.repository.UsuarioRepository;
import com.emprendeStore.web.dto.response.CarritoResponseDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarritoServiceImplTest {

    @InjectMocks
    CarritoServiceImpl carritoServiceImpl;

    @Mock
    CarritoRepository carritoRepo;

    @Mock
    DetalleCarritoRepository detalleCarritoRepo;

    @Mock
    UsuarioRepository usuarioRepo;

    @Mock
    ProductoRepository productoRepo;

    @Mock
    CarritoMapper carritoMapper;

    @Mock
    ProductorMapper productoMapper;

    @BeforeEach
    void setUp() {
        carritoServiceImpl = new CarritoServiceImpl(detalleCarritoRepo, carritoRepo, carritoMapper, usuarioRepo, productoRepo);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getCarrito_deberiaRetornarDto() {
        // Corrección: Eliminado nomUsu
        Usuario usuario = Usuario.builder().idUsu(1L).correo("jesus@test.com").build();
        Carrito carrito = Carrito.builder().idCarrito(1L).usuario(usuario).detalles(new HashSet<>()).build();
        CarritoResponseDto responseEsperado = CarritoResponseDto.builder().idCarrito(1L).build();

        when(carritoRepo.findByUsuarioWithDetalles(1L)).thenReturn(Optional.of(carrito));
        when(carritoMapper.toDto(carrito)).thenReturn(responseEsperado);

        CarritoResponseDto resultado = carritoServiceImpl.getCarrito(1L);

        assertEquals(1L, resultado.getIdCarrito());
    }

    @Test
    void agregarProducto_deberiaAgregarNuevoDetalle() {
        Usuario usuario = Usuario.builder().idUsu(1L).build();
        Carrito carrito = Carrito.builder().idCarrito(1L).usuario(usuario).detalles(new HashSet<>()).build();
        Producto producto = Producto.builder().idProducto(10L).precio(BigDecimal.valueOf(100)).build();

        CarritoResponseDto responseEsperado = CarritoResponseDto.builder().idCarrito(1L).total(BigDecimal.valueOf(100)).build();

        when(carritoRepo.findByUsuarioWithDetalles(1L)).thenReturn(Optional.of(carrito));
        when(productoRepo.findById(10L)).thenReturn(Optional.of(producto));
        when(carritoRepo.save(carrito)).thenReturn(carrito);
        when(carritoMapper.toDto(carrito)).thenReturn(responseEsperado);

        CarritoResponseDto resultado = carritoServiceImpl.agregarProducto(1L, 10L);

        assertEquals(BigDecimal.valueOf(100), resultado.getTotal());
    }

    @Test
    void actualizarCantidadItem_deberiaUpdateCantidad() {
        Usuario usuario = Usuario.builder().idUsu(1L).build();
        Carrito carrito = Carrito.builder().idCarrito(1L).usuario(usuario).build();
        DetalleCarrito detalle = DetalleCarrito.builder()
                .idDetalleCarrito(5L)
                .carrito(carrito)
                .producto(Producto.builder().idProducto(10L).build())
                .cantidad(1)
                .precioUnitario(BigDecimal.valueOf(100))
                .build();

        // Simulamos que la query de actualización devuelve 1 fila afectada
        when(detalleCarritoRepo.actualizarCantidad(1L, 5L, 3)).thenReturn(1);
        when(carritoRepo.findByUsuarioWithDetalles(1L)).thenReturn(Optional.of(carrito));
        when(carritoMapper.toDto(carrito)).thenReturn(CarritoResponseDto.builder().idCarrito(1L).build());

        CarritoResponseDto resultado = carritoServiceImpl.updateCantidadItem(1L, 5L, 3);

        // Ya no verificamos el objeto detalle porque el mock del repo no lo modifica en memoria
        // pero verificamos que el flujo llegó al final sin errores
        verify(detalleCarritoRepo, times(1)).actualizarCantidad(1L, 5L, 3);
    }

    @Test
    void removerItem_deberiaEliminarDetalle() {
        Usuario usuario = Usuario.builder().idUsu(1L).build();
        Carrito carrito = Carrito.builder().idCarrito(1L).usuario(usuario).detalles(new HashSet<>()).build();
        DetalleCarrito detalle = DetalleCarrito.builder().idDetalleCarrito(5L).carrito(carrito).build();
        carrito.getDetalles().add(detalle);

        // Simulamos borrado exitoso
        when(detalleCarritoRepo.borrarItemDeUsuario(1L, 5L)).thenReturn(1);
        when(carritoRepo.findByUsuarioWithDetalles(1L)).thenReturn(Optional.of(carrito));
        when(carritoMapper.toDto(carrito)).thenReturn(CarritoResponseDto.builder().idCarrito(1L).build());

        CarritoResponseDto resultado = carritoServiceImpl.removerItem(1L, 5L);

        verify(detalleCarritoRepo, times(1)).borrarItemDeUsuario(1L, 5L);
    }
}
