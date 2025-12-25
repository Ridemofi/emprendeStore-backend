package com.emprendeStore.controller;

import com.emprendeStore.application.service.ProductoService;
import com.emprendeStore.web.controller.ProductoController;
import com.emprendeStore.web.dto.request.ProductoRequestDTO;
import com.emprendeStore.web.dto.response.ProductoResponseDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductoControllerTest {

    @InjectMocks
    private ProductoController productoController;

    @Mock
    private ProductoService productoService;
    @BeforeEach
    void setUp() {

    }
    @AfterEach
    void tearDown() {
    }

    @Test
    void listarRecientes() {
        // Arrange
        List<ProductoResponseDTO> recientesMock = generarListaProductosDto();
        when(productoService.listarnuevosproductos()).thenReturn(recientesMock);

        // Act
        ResponseEntity<List<ProductoResponseDTO>> response = productoController.listarRecientes();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(productoService, times(1)).listarnuevosproductos();
    }

    /*@Test
    void obtenerProducto() {
        ProductoResponseDTO producto = generarProductoDto();
        when(productoService.obtenerProducto(anyLong())).thenReturn(producto);

        ResponseEntity<?> responseEntity = productoController.obtenerProducto(1L);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        ProductoResponseDTO productoResponse = (ProductoResponseDTO) responseEntity.getBody();
        assertEquals(1L, productoResponse.getId());
    }
    */

    @Test
    void registrarProducto() {
        // Arrange
        ProductoRequestDTO requestDto = generarProductoRequestDto();
        ProductoResponseDTO responseMock = new ProductoResponseDTO();
        responseMock.setId(1L);
        responseMock.setNombreProd(requestDto.getNombreProd());
        when(productoService.saveProducto(any(ProductoRequestDTO.class))).thenReturn(responseMock);

        // Act
        ResponseEntity<ProductoResponseDTO> responseEntity = productoController.saveProducto(requestDto);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(responseMock.getId(), responseEntity.getBody().getId());
        assertEquals(responseMock.getNombreProd(), responseEntity.getBody().getNombreProd());
        verify(productoService, times(1)).saveProducto(requestDto);
    }

    /* @Test
    void modificarProducto() {
        ProductoResponseDTO producto = generarProductoDto();

        when(productoService.modificarProducto(any(Producto.class))).thenReturn(producto);

        ResponseEntity<?> responseEntity = productoController.modificarProducto(producto);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        Producto productoResponse = (Producto) responseEntity.getBody();
        assertEquals(1L, productoResponse.getIdProducto());
    }
    */

    /*
    @Test
    void eliminarEmpleado() {
        when(empleadoServiceImpl.eliminarEmpleado(anyInt())).thenReturn("Empleado eliminado");
        empleadoController.eliminarEmpleado(1);
        verify(empleadoServiceImpl,times(1)).eliminarEmpleado(anyInt());
    }
    */

    private List<ProductoResponseDTO> generarListaProductosDto() {
        ProductoResponseDTO producto1 = new ProductoResponseDTO();
        producto1.setId(1L);
        producto1.setNombreProd("Laptop Gamer");
        producto1.setDescrip("Laptop de alto rendimiento para juegos exigentes.");
        producto1.setPrecio(new BigDecimal("3500.00"));
        producto1.setStock(10);
        producto1.setIdCategoria(1L);
        producto1.setIdEmprendedor(1L);

        ProductoResponseDTO producto2 = new ProductoResponseDTO();
        producto2.setId(2L);
        producto2.setNombreProd("Smartphone 5G");
        producto2.setDescrip("Teléfono inteligente con conectividad 5G y cámara de 108MP.");
        producto2.setPrecio(new BigDecimal("2500.00"));
        producto2.setStock(20);
        producto2.setIdCategoria(1L);
        producto2.setIdEmprendedor(1L);

        List<ProductoResponseDTO> productos = new ArrayList<>();
        productos.add(producto1);
        productos.add(producto2);

        return productos;
    }

    private ProductoRequestDTO generarProductoRequestDto() {
        ProductoRequestDTO dto = new ProductoRequestDTO();
        dto.setNombreProd("Laptop Gamer");
        dto.setDescrip("Laptop de alto rendimiento");
        dto.setPrecio(new BigDecimal("3500.00"));
        dto.setStock(10);
        dto.setIdCategoria(1L);
        dto.setIdEmprendedor(1L);
        return dto;
    }
}
