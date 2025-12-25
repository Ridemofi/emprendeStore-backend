package com.emprendeStore.application.service.impl;

import com.emprendeStore.application.mapper.EmprendedorMapper;
import com.emprendeStore.application.service.EmprendedorService;
import com.emprendeStore.domain.model.Emprendedor;
import com.emprendeStore.domain.repository.EmprendedorRepository;
import com.emprendeStore.web.dto.request.RegisterEmprendedorRequestDto;
import com.emprendeStore.web.dto.response.EmprendedorResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmprendedorServiceImpl implements EmprendedorService {
    private final EmprendedorRepository er;
    private final EmprendedorMapper em;
    private final PasswordEncoder pe;

    @Override
    @Transactional
    public EmprendedorResponseDto saveEmpre(RegisterEmprendedorRequestDto dto) {
        Emprendedor e=em.toEntity(dto);
        e.setPasswordempre(pe.encode(dto.getPassword()));
        er.save(e);
        return em.toDto(e);
    }

    @Transactional
    @Override
    public EmprendedorResponseDto deleteEmpre(Long id) {
        Emprendedor e= er.findById(id).orElseThrow(() -> new RuntimeException("El Emprendedor con el id: " + id + " no existe"));
        er.delete(e);
        return em.toDto(e);
    }

    @Override
    public List<EmprendedorResponseDto> listarEmprePopulares() {
        return er.findEmprePopulares()
                .stream()
                .map(em::toDto)
                .toList();
    }

    @Override
    public EmprendedorResponseDto obtenerEmpreXId(Long id) {
        return er.findById(id)
                .map(em::toDto)
                .orElseThrow(() -> new RuntimeException("Emprendedor no encontrado"));
    }

    @Override
    public List<EmprendedorResponseDto> listarAllEmpres() {
        return er.findAll()
                .stream()
                .map(em::toDto)
                .toList();
    }


}
