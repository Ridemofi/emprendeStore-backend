package com.emprendeStore.servicetest;

import com.emprendeStore.application.mapper.UbicacionMapper;
import com.emprendeStore.application.service.impl.UbicacionServiceImpl;
import com.emprendeStore.domain.model.*;
import com.emprendeStore.domain.repository.*;
import com.emprendeStore.web.dto.response.PaisResponseDto;
import com.emprendeStore.web.dto.response.UbicacionResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UbicacionServiceTest {

    @InjectMocks
    private UbicacionServiceImpl ubicacionService;

    @Mock private PaisRepository pr;
    @Mock private UbicacionMapper um;
    @Mock private UbicacionNivel1Repository un1r;
    @Mock private UbicacionNivel2Repository un2r;
    @Mock private UbicacionNivel3Repository un3r;

    @Test
    void listarPaises() {
        // Arrange
        Pais paisMock = new Pais();
        paisMock.setConfigPais(new ConfigPais());
        List<Pais> listaPaises = List.of(paisMock);

        when(pr.findTodoConConfig()).thenReturn(listaPaises);
        when(um.toPaisDto(any(), any())).thenReturn(new PaisResponseDto());

        // Act
        List<PaisResponseDto> result = ubicacionService.listarPaises();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(pr).findTodoConConfig();
    }

    @Test
    void listarUbisNivel1() {
        // Arrange
        Long idPais = 1L;
        List<UbicacionNivel1> nivel1List = List.of(new UbicacionNivel1(), new UbicacionNivel1());
        // Usamos campos reales: idUbi, nombreUbi, code, codigoPostal
        UbicacionResponseDto responseMock = generarUbicacionResponseDto(10L, "Lima", "LIM", "15001");

        when(un1r.findByPaisIdPais(idPais)).thenReturn(nivel1List);
        when(um.toDtoUn1(any())).thenReturn(responseMock);

        // Act
        List<UbicacionResponseDto> result = ubicacionService.listarUbisNivel1(idPais);

        // Assert
        assertEquals(2, result.size());
        assertEquals("Lima", result.get(0).getNombreUbi());
        assertEquals("LIM", result.get(0).getCode());
        verify(un1r).findByPaisIdPais(idPais);
    }

    @Test
    void listarUbisNivel2() {
        // Arrange
        Long idNivel1 = 5L;
        List<UbicacionNivel2> nivel2List = List.of(new UbicacionNivel2());
        UbicacionResponseDto responseMock = generarUbicacionResponseDto(50L, "Lima Metropolitana", null, null);

        when(un2r.findByUbicacionNivel1IdN1(idNivel1)).thenReturn(nivel2List);
        when(um.toDtoUn2(any())).thenReturn(responseMock);

        // Act
        List<UbicacionResponseDto> result = ubicacionService.listarUbisNivel2(idNivel1);

        // Assert
        assertFalse(result.isEmpty());
        assertEquals("Lima Metropolitana", result.get(0).getNombreUbi());
        verify(un2r).findByUbicacionNivel1IdN1(idNivel1);
    }

    @Test
    void listarUbisNivel3() {
        // Arrange
        Long idNivel2 = 50L;
        List<UbicacionNivel3> nivel3List = List.of(new UbicacionNivel3());
        UbicacionResponseDto responseMock = generarUbicacionResponseDto(500L, "San Juan de Lurigancho", null, "15442");

        when(un3r.findByUbicacionNivel2IdN2(idNivel2)).thenReturn(nivel3List);
        when(um.toDtoUn3(any())).thenReturn(responseMock);

        // Act
        List<UbicacionResponseDto> result = ubicacionService.listarUbisNivel3(idNivel2);

        // Assert
        assertNotNull(result);
        assertEquals("San Juan de Lurigancho", result.get(0).getNombreUbi());
        assertEquals("15442", result.get(0).getCodigoPostal());
        verify(un3r).findByUbicacionNivel2IdN2(idNivel2);
    }

    // Helper actualizado con tus nuevos campos
    private UbicacionResponseDto generarUbicacionResponseDto(Long id, String nombre, String code, String cp) {
        return UbicacionResponseDto.builder()
                .idUbi(id)
                .nombreUbi(nombre)
                .code(code)
                .codigoPostal(cp)
                .build();
    }
}