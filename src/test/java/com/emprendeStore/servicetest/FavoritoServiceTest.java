package com.emprendeStore.servicetest;

import com.emprendeStore.application.exception.ErrorNegocio;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FavoritoServiceTest {

    @Mock
    private FavoritosMapper favoritosMapper;

    @Mock
    private FavoritosRepository favoritosRepository;

    @Mock
    private ProductorMapper productorMapper;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private FavoritoServiceImpl favoritoService;

    private Usuario usuario;
    private Producto producto;
    private Favoritos favorito;
    private FavoritoRequestDto favoritoRequestDto;
    private FavoritoResponseDto favoritoResponseDto;
    private ProductoResponseDTO productoResponseDTO;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setIdUsu(1L);

        producto = new Producto();
        producto.setIdProducto(1L);

        favoritoRequestDto = new FavoritoRequestDto(1L, 1L);

        favorito = new Favoritos();
        favorito.setIdFav(1L);
        favorito.setUsuario(usuario);
        favorito.setProducto(producto);

        favoritoResponseDto = new FavoritoResponseDto(1L, 1L, 1L, null);
        productoResponseDTO = new ProductoResponseDTO();
    }

    @Test
    void savefav_Success() {
        when(favoritosRepository.existsByUsuarioIdUsuAndProductoIdProducto(anyLong(), anyLong())).thenReturn(false);
        when(usuarioRepository.getReferenceById(anyLong())).thenReturn(usuario);
        when(productoRepository.getReferenceById(anyLong())).thenReturn(producto);
        when(favoritosMapper.toEntity(any(Usuario.class), any(Producto.class))).thenReturn(favorito);
        when(favoritosRepository.save(any(Favoritos.class))).thenReturn(favorito);
        when(favoritosMapper.toDto(any(Favoritos.class))).thenReturn(favoritoResponseDto);

        FavoritoResponseDto result = favoritoService.savefav(favoritoRequestDto);

        assertNotNull(result);
        assertEquals(favoritoResponseDto.getIdFav(), result.getIdFav());
        verify(favoritosRepository).save(any(Favoritos.class));
    }

    @Test
    void savefav_AlreadyExists_ThrowsErrorNegocio() {
        when(favoritosRepository.existsByUsuarioIdUsuAndProductoIdProducto(anyLong(), anyLong())).thenReturn(true);

        assertThrows(ErrorNegocio.class, () -> {
            favoritoService.savefav(favoritoRequestDto);
        });

        verify(favoritosRepository, never()).save(any(Favoritos.class));
    }

    @Test
    void listarFavoritosxUsu_Success() {
        when(favoritosRepository.findProductosFavoritosPorUsuario(anyLong())).thenReturn(Collections.singletonList(producto));
        when(productorMapper.toDto(any(Producto.class))).thenReturn(productoResponseDTO);

        List<ProductoResponseDTO> result = favoritoService.listarFavoritosxUsu(1L);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(favoritosRepository).findProductosFavoritosPorUsuario(1L);
    }
    
    @Test
    void listarFavoritosxUsu_Empty() {
        when(favoritosRepository.findProductosFavoritosPorUsuario(anyLong())).thenReturn(Collections.emptyList());

        List<ProductoResponseDTO> result = favoritoService.listarFavoritosxUsu(1L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(favoritosRepository).findProductosFavoritosPorUsuario(1L);
    }

    @Test
    void deletefav_Success() {
        doNothing().when(favoritosRepository).deleteFavoritoDirecto(anyLong(), anyLong());

        favoritoService.deletefav(1L, 1L);

        verify(favoritosRepository, times(1)).deleteFavoritoDirecto(1L, 1L);
    }
}
