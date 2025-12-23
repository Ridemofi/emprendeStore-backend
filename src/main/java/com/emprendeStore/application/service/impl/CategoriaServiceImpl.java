package com.emprendeStore.application.service.impl;

import com.emprendeStore.application.mapper.CategoriaMapper;
import com.emprendeStore.application.service.CategoriaService;
import com.emprendeStore.domain.model.Categoria;
import com.emprendeStore.domain.repository.CategoriaRepository;
import com.emprendeStore.web.dto.request.CategoriaRequestDTO;
import com.emprendeStore.web.dto.response.CategoriaResponseDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoriaServiceImpl implements CategoriaService {
    private final CategoriaMapper cm;
    private final CategoriaRepository cr;

    @Override
    @Transactional
    public CategoriaResponseDTO create(CategoriaRequestDTO requestDTO) {
        Categoria c = cm.toEntity(requestDTO);
        cr.save(c);
        return cm.toDto(cr.save(c));
    }

    @Override
    public List<CategoriaResponseDTO> listatodo() {
        return cr.findAll()
                .stream()
                .map(cm::toDto)
                .toList();
    }

    @Override
    public List<CategoriaResponseDTO> categoriaspopular() {
        return cr.findCategoriasPopulares()
                .stream()
                .map(cm::toDto)
                .toList();
    }
}
