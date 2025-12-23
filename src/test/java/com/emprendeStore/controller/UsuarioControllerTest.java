package com.emprendeStore.controller;

import com.emprendeStore.application.service.UsuarioService;
import com.emprendeStore.web.controller.UsuarioController;
import com.emprendeStore.web.dto.request.LoginUsuarioRequestDto;
import com.emprendeStore.web.dto.request.RegisterUsuarioRequestDto;
import com.emprendeStore.web.dto.response.UsuarioResponseDto;
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

// CORRECCIÓN: Usamos MockitoExtension para pruebas unitarias puras.
// Si quisieras usar el contexto de Spring, usarías @WebMvcTest(UsuarioController.class)
// y @Autowired MockMvc, pero para esto, Mockito puro es más simple y rápido.
@ExtendWith(MockitoExtension.class)
public class UsuarioControllerTest {

    @InjectMocks
    private UsuarioController usuarioController;

    @Mock
    private UsuarioService usuarioService;

    // CORRECCIÓN: El @BeforeEach y el mock de UsuarioRepository se eliminaron.

    @Test
    void listarUsuarios() {
        // Arrange (Preparar)
        List<UsuarioResponseDto> usuariosMock = generarListaUsuariosDto();
        when(usuarioService.listarUsuarios()).thenReturn(usuariosMock);

        // Act (Actuar)
        ResponseEntity<List<UsuarioResponseDto>> response = usuarioController.listar();

        // Assert (Verificar)
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("maria_dev", response.getBody().get(0).getNomUsu());
    }

    @Test
    void registrarUsuario() {
        // Arrange
        RegisterUsuarioRequestDto request = generarUsuarioRequestDto();
        UsuarioResponseDto responseMock = generarUsuarioResponseDto();
        when(usuarioService.save(any(RegisterUsuarioRequestDto.class))).thenReturn(responseMock);

        // Act
        ResponseEntity<UsuarioResponseDto> response = usuarioController.registrarusu(request);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(10L, response.getBody().getId());
        assertEquals("maria_dev", response.getBody().getNomUsu());
    }

    @Test
    void loginUsuario() {
        // Arrange
        LoginUsuarioRequestDto loginRequest = new LoginUsuarioRequestDto("maria@correo.com", "clave456");
        UsuarioResponseDto responseMock = generarUsuarioResponseDto();
        when(usuarioService.login(any(LoginUsuarioRequestDto.class))).thenReturn(responseMock);

        // Act
        ResponseEntity<UsuarioResponseDto> response = usuarioController.login(loginRequest);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(10L, response.getBody().getId());
        assertEquals("maria@correo.com", response.getBody().getCorreo());
    }

    @Test
    void eliminarUsuario() {
        Long id = 10L;
        // No necesitamos mockear 'delete' porque devuelve void, solo verificamos que se llame.
        doNothing().when(usuarioService).delete(id);
        ResponseEntity<String> response = usuarioController.delete(id);
        verify(usuarioService, times(1)).delete(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Usuario con ID " + id + " eliminado correctamente", response.getBody());
    }

    private UsuarioResponseDto generarUsuarioResponseDto() {
        return UsuarioResponseDto.builder()
                .id(10L)
                .nombreReal("María")
                .nomUsu("maria_dev")
                .correo("maria@correo.com")
                .nroCel("987654321")
                .fechaRegistro(LocalDate.now())
                .build();
    }

    private List<UsuarioResponseDto> generarListaUsuariosDto() {
        UsuarioResponseDto usuario2 = UsuarioResponseDto.builder()
                .id(20L).nombreReal("Carlos").nomUsu("carlos_web").correo("carlos@correo.com")
                .nroCel("912345678").fechaRegistro(LocalDate.now()).build();
        return List.of(generarUsuarioResponseDto(), usuario2);
    }

    private RegisterUsuarioRequestDto generarUsuarioRequestDto() {
        return RegisterUsuarioRequestDto.builder()
                .nombreReal("María").nomUsu("maria_dev").correo("maria@correo.com")
                .nroCel("987654321").password("clave456").img(null).build();
    }
}