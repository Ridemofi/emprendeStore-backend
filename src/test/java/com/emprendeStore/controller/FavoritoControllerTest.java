package com.emprendeStore.controller;

import com.emprendeStore.application.service.FavoritoService;
import com.emprendeStore.web.controller.FavoritoController;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FavoritoControllerTest {

    @InjectMocks
    private FavoritoController favoritoController;

    @Mock
    private FavoritoService favoritoService;
    @BeforeEach
    void setUp() {

    }
    @AfterEach
    void tearDown() {
    }
    @Test
    void registrarFav_devuelveFavoritoResponseDto() {
        FavoritoRequestDto requestDto = new FavoritoRequestDto();
        requestDto.setIdUsu(1L);
        requestDto.setIdPro(2L);
        FavoritoResponseDto favoritoResponse = new FavoritoResponseDto();
        favoritoResponse.setIdUsu(1L);
        favoritoResponse.setIdPro(2L);
        when(favoritoService.savefav(requestDto)).thenReturn(favoritoResponse);
        ResponseEntity<FavoritoResponseDto> responseEntity = favoritoController.registrarFav(requestDto);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1L, responseEntity.getBody().getIdUsu());
        assertEquals(2L, responseEntity.getBody().getIdPro());
        verify(favoritoService, times(1)).savefav(requestDto);
    }

    @Test
    void listarFavxUsu_devuelveListaDeProductos() {
        Long idUsu = 1L;
        List<ProductoResponseDTO> favoritos = generarListaProductosDto();
        when(favoritoService.listarFavoritosxUsu(idUsu)).thenReturn(favoritos);
        ResponseEntity<List<ProductoResponseDTO>> responseEntity = favoritoController.listarFavxUsu(idUsu);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(2, responseEntity.getBody().size());
        assertEquals(favoritos, responseEntity.getBody());
        verify(favoritoService, times(1)).listarFavoritosxUsu(idUsu);
    }

    @Test
    void deletefav_invocaServiceCorrectamente() {
        Long idUsu = 1L;
        Long idPro = 2L;
        doNothing().when(favoritoService).deletefav(idUsu, idPro);
        ResponseEntity<Void> responseEntity = favoritoController.deletefav(idUsu, idPro);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(favoritoService, times(1)).deletefav(idUsu, idPro);
    }

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
}
