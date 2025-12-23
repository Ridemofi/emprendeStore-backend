package com.emprendeStore.controller;

import com.emprendeStore.application.service.EmprendedorService;
import com.emprendeStore.web.controller.EmprendedorController;
import com.emprendeStore.web.dto.response.EmprendedorResponseDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmprendedorControllerTest {

    @InjectMocks
    private EmprendedorController emprendedorController;

    @Mock
    private EmprendedorService emprendedorService;
    @BeforeEach
    void setUp() {

    }
    @AfterEach
    void tearDown() {
    }
    @Test
    void testListarPopulares() {
        List<EmprendedorResponseDto> emprendedoresMock = generarListaEmprendedoresDto();
        when(emprendedorService.listarEmprePopulares()).thenReturn(emprendedoresMock);
        ResponseEntity<List<EmprendedorResponseDto>> response = emprendedorController.listarPopulares();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("Romario", response.getBody().get(0).getNombep());
        verify(emprendedorService, times(1)).listarEmprePopulares();
    }

    @Test
    void testObtenerEmpreXId() {
        Long id = 1L;
        EmprendedorResponseDto emprendedorMock = generarEmprendedorDto();
        when(emprendedorService.obtenerEmpreXId(id)).thenReturn(emprendedorMock);
        ResponseEntity<EmprendedorResponseDto> response = emprendedorController.obtenerEmpreXId(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getIdemp());
        assertEquals("Romario", response.getBody().getNombep());
        verify(emprendedorService, times(1)).obtenerEmpreXId(id);
    }

    private EmprendedorResponseDto generarEmprendedorDto() {
        return EmprendedorResponseDto.builder()
                .idemp(1L)
                .imgemp(null)
                .nombep("Romario")
                .correo("romario@gmail.com")
                .nrocell("938594859")
                .fechaingreso(LocalDate.now())
                .build();
    }

    private List<EmprendedorResponseDto> generarListaEmprendedoresDto() {
        EmprendedorResponseDto emprendedor2 = EmprendedorResponseDto.builder()
                .idemp(2L)
                .imgemp(null)
                .nombep("Domario")
                .correo("domario@gmail.com")
                .nrocell("938594859")
                .fechaingreso(LocalDate.now())
                .build();
        return List.of(generarEmprendedorDto(), emprendedor2);
    }
}
