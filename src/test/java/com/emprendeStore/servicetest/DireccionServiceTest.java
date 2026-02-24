package com.emprendeStore.servicetest;

import com.emprendeStore.application.exception.ErrorNegocio;
import com.emprendeStore.application.mapper.DireccionMapper;
import com.emprendeStore.application.service.impl.DireccionServiceImpl;
import com.emprendeStore.domain.model.*;
import com.emprendeStore.domain.repository.*;
import com.emprendeStore.web.dto.request.DireccionRequestDto;
import com.emprendeStore.web.dto.response.DireccionResponseDto;
import com.emprendeStore.web.dto.response.PaisResponseDto;
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
public class DireccionServiceTest {

    @InjectMocks
    private DireccionServiceImpl direccionService;

    @Mock private DireccionRepository dr;
    @Mock private DireccionMapper dm;
    @Mock private UsuarioRepository ur;
    @Mock private PaisRepository pr;
    @Mock private UbicacionNivel1Repository un1r;
    @Mock private UbicacionNivel2Repository un2r;
    @Mock private UbicacionNivel3Repository un3r;

    @Test
    void saveDireccion() {
        // Arrange
        DireccionRequestDto dto = new DireccionRequestDto();
        dto.setIdUsu(1L);
        dto.setIdPais(1L);

        Usuario usuarioMock = new Usuario();
        Pais paisMock = new Pais();
        Direccion direccionMock = new Direccion();
        DireccionResponseDto responseMock = generarDireccionResponseDto(500L);

        when(ur.getReferenceById(1L)).thenReturn(usuarioMock);
        when(pr.getReferenceById(1L)).thenReturn(paisMock);
        when(dr.countByUsuarioIdUsu(1L)).thenReturn(0L); // Primera dirección
        when(dm.toEntity(any(), any(), any(), any(), any(), any())).thenReturn(direccionMock);
        when(dr.save(any(Direccion.class))).thenReturn(direccionMock);
        when(dm.toDto(direccionMock)).thenReturn(responseMock);

        // Act
        DireccionResponseDto result = direccionService.saveDireccion(dto);

        // Assert
        assertNotNull(result);
        assertEquals(500L, result.getIdDireccion());
        assertEquals("987654321", result.getTelefono());
        assertEquals("Calle Principal 123", result.getDireccion1());
        assertTrue(dto.isEsPrincipal());
        verify(dr).save(any(Direccion.class));
    }

    @Test
    void listarDirecciones() {
        // Arrange
        Long idUsuario = 1L;
        List<Direccion> direcciones = List.of(new Direccion(), new Direccion());

        when(dr.findAllConDetallesPorUsuario(idUsuario)).thenReturn(direcciones);
        when(dm.toDto(any(Direccion.class))).thenReturn(new DireccionResponseDto());

        // Act
        List<DireccionResponseDto> result = direccionService.listarDirecciones(idUsuario);

        // Assert
        assertEquals(2, result.size());
        verify(dr).findAllConDetallesPorUsuario(idUsuario);
    }

    @Test
    void obtenerDireccionPrincipal() {
        // Arrange
        Long idUsuario = 1L;
        Direccion direccionMock = new Direccion();
        DireccionResponseDto responseMock = generarDireccionResponseDto(1L);

        when(dr.findDireccionPrincipalConDetalles(idUsuario)).thenReturn(Optional.of(direccionMock));
        when(dm.toDto(direccionMock)).thenReturn(responseMock);

        // Act
        DireccionResponseDto result = direccionService.obtenerDireccionPrincipal(idUsuario);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEsPrincipal());
        assertEquals("Juan Perez", result.getNombreContacto());
    }

    @Test
    void updateDireccion_Success() {
        // Arrange
        Long idDir = 1L;
        DireccionRequestDto dto = new DireccionRequestDto();
        dto.setIdPais(1L);

        Direccion direccionExistente = new Direccion();
        direccionExistente.setEsPrincipal(true);

        when(dr.findById(idDir)).thenReturn(Optional.of(direccionExistente));
        when(pr.getReferenceById(anyLong())).thenReturn(new Pais());
        when(dm.toDto(direccionExistente)).thenReturn(generarDireccionResponseDto(idDir));

        // Act
        DireccionResponseDto result = direccionService.updateDireccion(idDir, dto);

        // Assert
        verify(dr).save(direccionExistente);
        assertTrue(direccionExistente.getEsPrincipal());
        assertNotNull(result);
    }

    @Test
    void establecerPrincipal() {
        // Arrange
        Long idDir = 1L;
        Usuario usuario = new Usuario();
        usuario.setIdUsu(10L);

        Direccion d = new Direccion();
        d.setUsuario(usuario);
        d.setEsPrincipal(false);

        when(dr.findById(idDir)).thenReturn(Optional.of(d));

        // Act
        direccionService.establecerPrincipal(idDir);

        // Assert
        verify(dr).desmarcarTodasLasDelUsuario(10L);
        verify(dr).save(d);
        assertTrue(d.getEsPrincipal());
    }


    private DireccionResponseDto generarDireccionResponseDto(Long id) {
        return DireccionResponseDto.builder()
                .idDireccion(id)
                .nombreContacto("Juan Perez")
                .telefono("987654321")
                .documentoIdentidad("77889900")
                .pais(new PaisResponseDto()) // Asumiendo que existe el DTO
                .ubicacionNivel1("Departamento Test")
                .ubicacionNivel2("Provincia Test")
                .ubicacionNivel3("Distrito Test")
                .direccion1("Calle Principal 123")
                .direccion2("Apto 402")
                .codigoPostal("15001")
                .esPrincipal(true)
                .build();
    }
}