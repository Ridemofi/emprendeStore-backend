package com.emprendeStore.controller;

import com.emprendeStore.application.service.CategoriaService;
import com.emprendeStore.web.controller.CategoriaController;
import com.emprendeStore.web.dto.request.CategoriaRequestDTO;
import com.emprendeStore.web.dto.response.CategoriaResponseDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoriaControllerTest {

    @InjectMocks
    private CategoriaController categoriaController;

    @Mock
    private CategoriaService categoriaService;
    @BeforeEach
    void setUp() {

    }
    @AfterEach
    void tearDown() {
    }
    @Test
    void listarCategorias_deberiaRetornarListaYStatusOK() {
        List<CategoriaResponseDTO> categorias = generarListaCategoriasDto();
        when(categoriaService.listatodo()).thenReturn(categorias);
        ResponseEntity<List<CategoriaResponseDTO>> response = categoriaController.listarcategorias();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("Tecnologia", response.getBody().get(0).getNombreCat());
    }

    @Test
    void crearCategoria_deberiaRetornarCategoriaCreadaYStatusCreated() {
        CategoriaRequestDTO request = generarCategoriaRequestDto();
        CategoriaResponseDTO responseMock = CategoriaResponseDTO.builder().idCategoria(1L).nombreCat(request.getNombreCat()).build();
        when(categoriaService.create(request)).thenReturn(responseMock);
        ResponseEntity<CategoriaResponseDTO> response = categoriaController.crear(request);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Tecnologia", response.getBody().getNombreCat());
    }

    private List<CategoriaResponseDTO> generarListaCategoriasDto() {
        CategoriaResponseDTO categoria1 = CategoriaResponseDTO.builder().idCategoria(10L).nombreCat("Tecnologia").build();
        CategoriaResponseDTO categoria2 = CategoriaResponseDTO.builder().idCategoria(20L).nombreCat("Comida").build();
        return List.of(categoria1, categoria2);
    }

    private CategoriaRequestDTO generarCategoriaRequestDto() {
        return CategoriaRequestDTO.builder().nombreCat("Tecnologia").build();
    }
}
