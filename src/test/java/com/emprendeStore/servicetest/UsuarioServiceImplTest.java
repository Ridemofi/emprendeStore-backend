package com.emprendeStore.servicetest;

import com.emprendeStore.application.mapper.UsuarioMapper;
import com.emprendeStore.application.service.EmailService;
import com.emprendeStore.domain.model.Usuario;
import com.emprendeStore.domain.repository.UsuarioRepository;
import com.emprendeStore.web.dto.request.RegisterUsuarioRequestDto;
import com.emprendeStore.web.dto.request.UpdateUsuarioRequestDto;
import com.emprendeStore.web.dto.response.UsuarioResponseDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UsuarioServiceImplTest {

    @InjectMocks
    com.emprendeStore.application.service.impl.UsuarioServiceImpl usuarioServiceImpl;

    @Mock
    UsuarioRepository usuarioRepo;

    @Mock
    UsuarioMapper usuarioMapper;

    @Mock
    EmailService emailService;

    @Mock
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        usuarioServiceImpl = new com.emprendeStore.application.service.impl.UsuarioServiceImpl(usuarioMapper, usuarioRepo, passwordEncoder, emailService);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void listarUsuarios_deberiaRetornarListaDeDTOs() {
        List<Usuario> usuarios = generarListaUsuarios();

        when(usuarioRepo.findAll()).thenReturn(usuarios);
        when(usuarioMapper.toDto(usuarios.get(0))).thenReturn(
                UsuarioResponseDto.builder().id(1L).correo("jesus@test.com").build()
        );
        when(usuarioMapper.toDto(usuarios.get(1))).thenReturn(
                UsuarioResponseDto.builder().id(2L).correo("maria@test.com").build()
        );

        List<UsuarioResponseDto> resultado = usuarioServiceImpl.listarUsuarios();

        assertEquals(2, resultado.size());
        assertEquals("jesus@test.com", resultado.get(0).getCorreo());
        assertEquals("maria@test.com", resultado.get(1).getCorreo());
    }

    @Test
    void save_deberiaRegistrarUsuario() {
        RegisterUsuarioRequestDto request = RegisterUsuarioRequestDto.builder()
                .correo("jesus@test.com")
                .password("1234")
                .build();

        Usuario entidad = generarUsuario(1L, "jesus@test.com", "encoded1234");

        UsuarioResponseDto responseEsperado = UsuarioResponseDto.builder()
                .id(1L)
                .correo("jesus@test.com")
                .build();

        when(usuarioMapper.toEntity(request)).thenReturn(entidad);
        when(passwordEncoder.encode("1234")).thenReturn("encoded1234");
        when(usuarioRepo.save(entidad)).thenReturn(entidad);
        when(usuarioMapper.toDto(entidad)).thenReturn(responseEsperado);

        UsuarioResponseDto resultado = usuarioServiceImpl.saveUsu(request);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("jesus@test.com", resultado.getCorreo());
        
        // Verificar que se envió el correo
        verify(emailService, times(1)).enviarCorreoBienvenida(any(), any());
    }

    @Test
    void delete_deberiaEliminarUsuario() {
        Usuario entidad = generarUsuario(1L, "jesus@test.com", "1234");

        when(usuarioRepo.findById(1L)).thenReturn(Optional.of(entidad));
        when(usuarioMapper.toDto(entidad)).thenReturn(
                UsuarioResponseDto.builder().id(1L).correo("jesus@test.com").build()
        );

        UsuarioResponseDto resultado = usuarioServiceImpl.deleteUsu(1L);

        verify(usuarioRepo, times(1)).delete(entidad);
        assertEquals("jesus@test.com", resultado.getCorreo());
    }

    @Test
    void update_deberiaActualizarUsuario() {
        Usuario entidad = generarUsuario(1L, "jesus@test.com", "1234");
        UpdateUsuarioRequestDto dtoUpdate = UpdateUsuarioRequestDto.builder()
                .correo("nuevo@test.com")
                .build();

        UsuarioResponseDto responseEsperado = UsuarioResponseDto.builder()
                .id(1L)
                .correo("nuevo@test.com")
                .build();

        when(usuarioRepo.findById(1L)).thenReturn(Optional.of(entidad));
        // Simulamos que el mapper actualiza la entidad
        doAnswer(inv -> {
            entidad.setCorreo("nuevo@test.com");
            return null;
        }).when(usuarioMapper).updateEntity(dtoUpdate, entidad);

        // En el servicio real no se llama a save explícitamente, pero el mock funciona igual
        // porque estamos probando la lógica del servicio, no la persistencia real.
        when(usuarioMapper.toDto(entidad)).thenReturn(responseEsperado);

        UsuarioResponseDto resultado = usuarioServiceImpl.updateUsu(dtoUpdate, 1L);

        assertEquals("nuevo@test.com", resultado.getCorreo());
    }


    private Usuario generarUsuario(Long id, String correo, String password) {
        return Usuario.builder()
                .idUsu(id)
                .correo(correo)
                .password(password)
                .build();
    }

    private List<Usuario> generarListaUsuarios() {
        Usuario u1 = generarUsuario(1L, "jesus@test.com", "1234");
        Usuario u2 = generarUsuario(2L, "maria@test.com", "5678");
        return List.of(u1, u2);
    }
}
