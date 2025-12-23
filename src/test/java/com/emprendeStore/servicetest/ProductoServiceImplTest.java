package com.emprendeStore.servicetest;

import com.emprendeStore.application.mapper.ProductorMapper;
import com.emprendeStore.application.service.impl.ProductoServiceImpl;
import com.emprendeStore.domain.model.Categoria;
import com.emprendeStore.domain.model.Emprendedor;
import com.emprendeStore.domain.model.Producto;
import com.emprendeStore.domain.repository.CategoriaRepository;
import com.emprendeStore.domain.repository.EmprendedorRepository;
import com.emprendeStore.domain.repository.ProductoRepository;
import com.emprendeStore.web.dto.request.ProductoRequestDTO;
import com.emprendeStore.web.dto.response.ProductoResponseDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductoServiceImplTest {

    @InjectMocks
    private ProductoServiceImpl productoService;

    @Mock
    private ProductoRepository productoRepo;

    @Mock
    private ProductorMapper productoMapper;

    @Mock
    private CategoriaRepository categoriaRepo;

    @Mock
    private EmprendedorRepository emprendedorRepo;

    @BeforeEach
    void setUp() {

    }
    @AfterEach
    void tearDown() {
    }

    @Test
    void listarnuevosproductos_deberiaRetornarListaDeDTOs() {
        List<Producto> productosMock = generarListaProductos();
        when(productoRepo.findRecienLlegados()).thenReturn(productosMock);
        when(productoMapper.toDto(any(Producto.class))).thenAnswer(invocation -> {
            Producto p = invocation.getArgument(0);
            return ProductoResponseDTO.builder().id(p.getIdProducto()).nombreProd(p.getNombreProd()).build();
        });
        List<ProductoResponseDTO> resultado = productoService.listarnuevosproductos();
        assertEquals(2, resultado.size());
        verify(productoRepo, times(1)).findRecienLlegados();
        verify(productoMapper, times(2)).toDto(any(Producto.class));
    }

    @Test
    void save_deberiaRegistrarProductoCorrectamente() {
        ProductoRequestDTO requestDto = generarProductoRequestDto();
        Categoria categoriaMock = new Categoria();
        Emprendedor emprendedorMock = new Emprendedor();
        Producto productoParaGuardar = new Producto();
        Producto productoGuardado = new Producto();
        productoGuardado.setIdProducto(1L);
        ProductoResponseDTO responseDtoMock = new ProductoResponseDTO();
        responseDtoMock.setId(1L);
        when(categoriaRepo.findById(anyLong())).thenReturn(Optional.of(categoriaMock));
        when(emprendedorRepo.findById(anyLong())).thenReturn(Optional.of(emprendedorMock));
        when(productoMapper.toEntity(any(), any(), any())).thenReturn(productoParaGuardar);
        when(productoRepo.save(any(Producto.class))).thenReturn(productoGuardado);
        when(productoMapper.toDto(any(Producto.class))).thenReturn(responseDtoMock);
        ProductoResponseDTO resultado = productoService.save(requestDto);
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(categoriaRepo, times(1)).findById(requestDto.getIdCategoria());
        verify(emprendedorRepo, times(1)).findById(requestDto.getIdEmprendedor());
        verify(productoRepo, times(1)).save(any(Producto.class)); // Verificamos con any()
        verify(productoMapper, times(1)).toDto(any(Producto.class));   // Verificamos con any()
    }

    @Test
    void buscarProductosFiltrados() {
        // lista simulada de entidades
        List<Producto> productos = generarListaProductos();

        // Mock del repository con los filtros
        when(productoRepo.buscarProConFiltros("Lap", 1L, 2L)).thenReturn(productos);
        when(productoMapper.toDto(productos.get(0))).thenReturn(
                ProductoResponseDTO.builder().id(1L).nombreProd("Laptop").build()
        );
        when(productoMapper.toDto(productos.get(1))).thenReturn(
                ProductoResponseDTO.builder().id(2L).nombreProd("Celular").build()
        );
        List<ProductoResponseDTO> resultado = productoService.buscarProductos("Lap", 1L, 2L);
        assertEquals(2, resultado.size());
        assertEquals("Laptop", resultado.get(0).getNombreProd());
        assertEquals("Celular", resultado.get(1).getNombreProd());
    }

    @Test
    void delete_deberiaEliminarProducto() {
        Producto entidad = Producto.builder().idProducto(1L).nombreProd("Laptop").build();

        when(productoRepo.findById(1L)).thenReturn(Optional.of(entidad));
        when(productoMapper.toDto(entidad)).thenReturn(
                ProductoResponseDTO.builder().id(1L).nombreProd("Laptop").build()
        );

        ProductoResponseDTO resultado = productoService.delete(1L);

        verify(productoRepo, times(1)).delete(entidad);
        assertEquals("Laptop", resultado.getNombreProd());
    }

    private List<Producto> generarListaProductos() {
        return List.of(
                Producto.builder().idProducto(1L).nombreProd("Laptop").build(),
                Producto.builder().idProducto(2L).nombreProd("Celular").build()
        );
    }

    private ProductoRequestDTO generarProductoRequestDto() {
        return ProductoRequestDTO.builder()
                .nombreProd("Laptop Gamer")
                .idCategoria(1L)
                .idEmprendedor(1L)
                .precio(new BigDecimal("3500"))
                .stock(10)
                .build();
    }
}
