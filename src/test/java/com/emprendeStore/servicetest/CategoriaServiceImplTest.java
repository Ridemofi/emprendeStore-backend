package com.emprendeStore.servicetest;

import com.emprendeStore.application.mapper.CategoriaMapper;
import com.emprendeStore.application.service.impl.CategoriaServiceImpl;
import com.emprendeStore.domain.model.Categoria;
import com.emprendeStore.domain.repository.CategoriaRepository;
import com.emprendeStore.web.dto.request.CategoriaRequestDTO;
import com.emprendeStore.web.dto.response.CategoriaResponseDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoriaServiceImplTest {

    @InjectMocks
    CategoriaServiceImpl categoriaServiceImpl;

    @Mock
    CategoriaRepository categoriaRepo;

    @Mock
    CategoriaMapper categoriaMapper;


    @BeforeEach
    void setUp() {
        categoriaServiceImpl = new CategoriaServiceImpl(categoriaMapper, categoriaRepo);
    }

    @AfterEach
    void tearDown() {
    }


    @Test
    void listaCategorias() {
        List<Categoria> categorias = generarListaCategorias();

        when(categoriaRepo.findAll()).thenReturn(categorias);
        List<CategoriaResponseDTO> categoriaResponse = categoriaServiceImpl.listatodo();
        assertEquals(2, categoriaResponse.size());
    }




    @Test
    void categoriaspopular_deberiaRetornarListaDeDTOs() {
        // Arrange: entidades simuladas
        List<Categoria> entidades = generarListaCategorias();
        Categoria cat1 = entidades.get(0);
        Categoria cat2 = entidades.get(1);

        when(categoriaRepo.findCategoriasPopulares()).thenReturn(entidades);

        // Mock del mapper con argumentos correctos
        when(categoriaMapper.toDto(cat1)).thenReturn(
                CategoriaResponseDTO.builder().idCategoria(1L).nombreCat("Tecnologia").imgcat(null).build()
        );
        when(categoriaMapper.toDto(cat2)).thenReturn(
                CategoriaResponseDTO.builder().idCategoria(2L).nombreCat("Comida").imgcat(null).build()
        );

        // Act
        List<CategoriaResponseDTO> resultado = categoriaServiceImpl.categoriaspopular();

        // Assert
        assertEquals(2, resultado.size());
        assertEquals("Tecnologia", resultado.get(0).getNombreCat());
        assertEquals("Comida", resultado.get(1).getNombreCat());
    }

    @Test
        void registrarCategoria() {
            // DTO de entrada
            CategoriaRequestDTO request = CategoriaRequestDTO.builder()
                    .nombreCat("Tecnologia")
                    .imgcat(null)
                    .build();

            // Entidad simulada
            Categoria entidad = new Categoria();
                    entidad.setIdCategoria(1L);
                    entidad.setNombreCat("Tecnologia");
                    entidad.setImgCat(null);
            // DTO de salida esperado
            CategoriaResponseDTO responseEsperado = CategoriaResponseDTO.builder()
                    .idCategoria(1L)
                    .nombreCat("Tecnologia")
                    .imgcat(null)
                    .build();

            // Mock del mapper y repo
            when(categoriaMapper.toEntity(request)).thenReturn(entidad);
            when(categoriaRepo.save(entidad)).thenReturn(entidad);
            when(categoriaMapper.toDto(entidad)).thenReturn(responseEsperado);

            // Act: ejecutar el servicio
            CategoriaResponseDTO resultado = categoriaServiceImpl.create(request);

            // Assert: validar resultado
            assertNotNull(resultado);
            assertEquals(1L, resultado.getIdCategoria());
            assertEquals("Tecnologia", resultado.getNombreCat());
        }


    // Helpers para generar DTOs de prueba con otros nombres
    private List<Categoria> generarListaCategorias() {

        Categoria categoria1 = Categoria.builder().idCategoria(1L)
                .nombreCat("Tecnologia").imgCat(null).build();


        Categoria categoria2 = Categoria.builder()
                .idCategoria(2L).nombreCat("Comida").imgCat(null)
                .build();
        return List.of(categoria1, categoria2);
    }



}
