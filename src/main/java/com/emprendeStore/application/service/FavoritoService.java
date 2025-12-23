package com.emprendeStore.application.service;

import com.emprendeStore.web.dto.request.FavoritoRequestDto;
import com.emprendeStore.web.dto.response.FavoritoResponseDto;
import com.emprendeStore.web.dto.response.ProductoResponseDTO;

import java.util.List;

public interface FavoritoService {
    FavoritoResponseDto savefav(FavoritoRequestDto dto);
    List<ProductoResponseDTO>listarFavoritosxUsu(Long id);
    void deletefav(Long idUsu, Long idPro);
}
