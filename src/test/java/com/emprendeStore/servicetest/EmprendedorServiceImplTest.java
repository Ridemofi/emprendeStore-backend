package com.emprendeStore.servicetest;


import com.emprendeStore.application.mapper.EmprendedorMapper;
import com.emprendeStore.application.service.impl.CloudinaryServiceImpl;
import com.emprendeStore.application.service.impl.EmprendedorServiceImpl;
import com.emprendeStore.domain.model.Emprendedor;
import com.emprendeStore.domain.repository.EmprendedorRepository;
import com.emprendeStore.web.dto.request.RegisterEmprendedorRequestDto;
import com.emprendeStore.web.dto.response.EmprendedorResponseDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmprendedorServiceImplTest {

    @InjectMocks
    EmprendedorServiceImpl emprendedorServiceImpl;

    @Mock
    EmprendedorRepository emprendedorRepo;

    @Mock
    EmprendedorMapper emprendedorMapper;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    CloudinaryServiceImpl cs;


    @BeforeEach
    void setUp() {
        emprendedorServiceImpl = new EmprendedorServiceImpl(emprendedorRepo, emprendedorMapper, passwordEncoder, cs);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void listadoEmprendedores() {
        List<Emprendedor> emprendedores = generarListaEmprendedores();
        when(emprendedorRepo.findAll()).thenReturn(emprendedores);
        when(emprendedorMapper.toDto(emprendedores.get(0))).thenReturn(
                EmprendedorResponseDto.builder().idemp(1L).nombep("Juan").correo("juan@test.com").build()
        );
        when(emprendedorMapper.toDto(emprendedores.get(1))).thenReturn(
                EmprendedorResponseDto.builder().idemp(2L).nombep("Maria").correo("maria@test.com").build()
        );

        List<EmprendedorResponseDto> response = emprendedorServiceImpl.listarAllEmpres();
        assertEquals(2, response.size());
    }

    @Test
    void listarEmprePopulares_deberiaRetornarListaDeDTOs() {
        List<Emprendedor> entidades = generarListaEmprendedores();
        Emprendedor e1 = entidades.get(0);
        Emprendedor e2 = entidades.get(1);

        when(emprendedorRepo.findEmprePopulares()).thenReturn(entidades);

        when(emprendedorMapper.toDto(e1)).thenReturn(
                EmprendedorResponseDto.builder().idemp(1L).nombep("Juan").correo("juan@test.com").build()
        );
        when(emprendedorMapper.toDto(e2)).thenReturn(
                EmprendedorResponseDto.builder().idemp(2L).nombep("Maria").correo("maria@test.com").build()
        );

        List<EmprendedorResponseDto> resultado = emprendedorServiceImpl.listarEmprePopulares();

        assertEquals(2, resultado.size());
        assertEquals("Juan", resultado.get(0).getNombep());
        assertEquals("Maria", resultado.get(1).getNombep());
    }

    @Test
    void registrarEmprendedor() {
        // DTO de entrada
        RegisterEmprendedorRequestDto request = RegisterEmprendedorRequestDto.builder()
                .nombEmp("Juan")
                .correo("juan@test.com")
                .nrocell("999999999")
                .password("1234")
                .build();

        // Entidad simulada
        Emprendedor entidad = new Emprendedor();
        entidad.setIdempre(1L);
        entidad.setNombreemp("Juan");
        entidad.setCorreoemp("juan@test.com");
        entidad.setNrocellemp("999999999");
        entidad.setPasswordempre("encoded1234");

        // DTO de salida esperado
        EmprendedorResponseDto responseEsperado = EmprendedorResponseDto.builder()
                .idemp(1L)
                .nombep("Juan")
                .correo("juan@test.com")
                .build();

        // Mock del mapper, repo y encoder
        when(emprendedorMapper.toEntity(request)).thenReturn(entidad);
        when(passwordEncoder.encode("1234")).thenReturn("encoded1234");
        when(emprendedorRepo.save(entidad)).thenReturn(entidad);
        when(emprendedorMapper.toDto(entidad)).thenReturn(responseEsperado);

        // Act
        EmprendedorResponseDto resultado = emprendedorServiceImpl.saveEmpre(request);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdemp());
        assertEquals("Juan", resultado.getNombep());
    }

    // Helpers para generar entidades de prueba
    private List<Emprendedor> generarListaEmprendedores() {
        Emprendedor e1 = new Emprendedor();
        e1.setIdempre(1L);
        e1.setNombreemp("Juan");
        e1.setCorreoemp("juan@test.com");
        e1.setNrocellemp("999999999");
        e1.setPasswordempre("pass1");

        Emprendedor e2 = new Emprendedor();
        e2.setIdempre(2L);
        e2.setNombreemp("Maria");
        e2.setCorreoemp("maria@test.com");
        e2.setNrocellemp("888888888");
        e2.setPasswordempre("pass2");

        return List.of(e1, e2);
    }
}
