package com.emprendeStore.application.mapper;

import com.emprendeStore.domain.model.Favoritos;
import com.emprendeStore.domain.model.Producto;
import com.emprendeStore.domain.model.Usuario;
import com.emprendeStore.web.dto.request.FavoritoRequestDto;
import com.emprendeStore.web.dto.response.FavoritoResponseDto;

public interface FavoritosMapper {
    Favoritos toEntity(FavoritoRequestDto dto, Usuario u, Producto p);
    FavoritoResponseDto toDto(Favoritos f);
}
