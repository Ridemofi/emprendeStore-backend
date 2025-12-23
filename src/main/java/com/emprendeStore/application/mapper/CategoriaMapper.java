package com.emprendeStore.application.mapper;

import com.emprendeStore.domain.model.Categoria;
import com.emprendeStore.web.dto.request.CategoriaRequestDTO;
import com.emprendeStore.web.dto.response.CategoriaResponseDTO;

public interface CategoriaMapper {
    Categoria toEntity (CategoriaRequestDTO requestDTO);
    CategoriaResponseDTO toDto(Categoria categoria);
}
