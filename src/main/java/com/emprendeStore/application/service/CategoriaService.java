package com.emprendeStore.application.service;

import com.emprendeStore.web.dto.request.CategoriaRequestDTO;
import com.emprendeStore.web.dto.response.CategoriaResponseDTO;

import java.util.List;

public interface CategoriaService {
    CategoriaResponseDTO create(CategoriaRequestDTO requestDTO);

    List<CategoriaResponseDTO> listatodo();

    List<CategoriaResponseDTO> categoriaspopular();

}
