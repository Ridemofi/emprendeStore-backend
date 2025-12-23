package com.emprendeStore.servicetest;

import com.emprendeStore.application.mapper.FavoritosMapper;
import com.emprendeStore.application.mapper.ProductorMapper;
import com.emprendeStore.application.service.impl.FavoritoServiceImpl;
import com.emprendeStore.domain.model.Favoritos;
import com.emprendeStore.domain.model.Producto;
import com.emprendeStore.domain.model.Usuario;
import com.emprendeStore.domain.repository.FavoritosRepository;
import com.emprendeStore.domain.repository.ProductoRepository;
import com.emprendeStore.domain.repository.UsuarioRepository;
import com.emprendeStore.web.dto.request.FavoritoRequestDto;
import com.emprendeStore.web.dto.response.FavoritoResponseDto;
import com.emprendeStore.web.dto.response.ProductoResponseDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FavoritoServiceImplTest {

    @InjectMocks
    private FavoritoServiceImpl favoritoService;

    @Mock
    private FavoritosRepository favoritoRepo;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private FavoritosMapper favoritoMapper;

    @Mock
    private ProductorMapper productorMapper;

    @BeforeEach
    void setUp() {

    }
    @AfterEach
    void tearDown() {
    }

    @Test
    void savefav_deberiaRegistrarFavorito() {
        // Arrange
        FavoritoRequestDto request = FavoritoRequestDto.builder().idUsu(1L).idPro(10L).build();
        Usuario usuario = Usuario.builder().idUsu(1L).build();
        Producto producto = Producto.builder().idProducto(10L).nombreProd("Laptop").build();
        Favoritos entidad = Favoritos.builder().idFav(100L).usuario(usuario).producto(producto).build();
        FavoritoResponseDto responseEsperado = FavoritoResponseDto.builder().idFav(100L).build();

        // Mock repository lookups
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(productoRepository.findById(10L)).thenReturn(Optional.of(producto));

        // Mock: no existe previamente
        when(favoritoRepo.findByUsuarioIdUsuAndProductoIdProducto(1L, 10L)).thenReturn(Optional.empty());
        when(favoritoMapper.toEntity(request, usuario, producto)).thenReturn(entidad);
        when(favoritoRepo.save(entidad)).thenReturn(entidad);
        when(favoritoMapper.toDto(entidad)).thenReturn(responseEsperado);

        // Act
        FavoritoResponseDto resultado = favoritoService.savefav(request);

        // Assert
        assertNotNull(resultado);
        assertEquals(100L, resultado.getIdFav());
        verify(favoritoRepo, times(1)).save(entidad);
    }

    @Test
    void savefav_deberiaLanzarExcepcionSiYaExiste() {
        // Arrange
        FavoritoRequestDto request = FavoritoRequestDto.builder().idUsu(1L).idPro(10L).build();
        Usuario usuario = Usuario.builder().idUsu(1L).build();
        Producto producto = Producto.builder().idProducto(10L).build();
        Favoritos existente = Favoritos.builder().idFav(200L).build();

        // Mock repository lookups
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(productoRepository.findById(10L)).thenReturn(Optional.of(producto));

        // Mock: ya existe
        when(favoritoRepo.findByUsuarioIdUsuAndProductoIdProducto(1L, 10L)).thenReturn(Optional.of(existente));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> favoritoService.savefav(request));
        verify(favoritoRepo, never()).save(any(Favoritos.class));
    }

    @Test
    void listarFavoritosxUsu_deberiaRetornarListaDeDTOs() {
        // Arrange
        long idUsuario = 1L;
        Producto p1 = Producto.builder().idProducto(10L).nombreProd("Laptop").build();
        Producto p2 = Producto.builder().idProducto(20L).nombreProd("Celular").build();
        List<Producto> productos = List.of(p1, p2);

        when(favoritoRepo.findProductosFavoritosPorUsuario(idUsuario)).thenReturn(productos);
        when(productorMapper.toDto(p1)).thenReturn(ProductoResponseDTO.builder().id(10L).nombreProd("Laptop").build());
        when(productorMapper.toDto(p2)).thenReturn(ProductoResponseDTO.builder().id(20L).nombreProd("Celular").build());

        // Act
        List<ProductoResponseDTO> resultado = favoritoService.listarFavoritosxUsu(idUsuario);

        // Assert
        assertEquals(2, resultado.size());
        assertEquals("Laptop", resultado.get(0).getNombreProd());
        assertEquals("Celular", resultado.get(1).getNombreProd());
        verify(favoritoRepo, times(1)).findProductosFavoritosPorUsuario(idUsuario);
    }

    @Test
    void deletefav_deberiaEliminarFavorito() {
        // Arrange
        long idUsuario = 1L;
        long idProducto = 10L;
        doNothing().when(favoritoRepo).deleteByUsuarioIdUsuAndProductoIdProducto(idUsuario, idProducto);

        // Act
        favoritoService.deletefav(idUsuario, idProducto);

        // Assert
        verify(favoritoRepo, times(1)).deleteByUsuarioIdUsuAndProductoIdProducto(idUsuario, idProducto);
    }
}
