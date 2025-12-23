package com.emprendeStore.application.mapper;

import com.emprendeStore.domain.model.Carrito;
import com.emprendeStore.domain.model.Usuario;
import com.emprendeStore.web.dto.request.CarritoRequestDTO;
import com.emprendeStore.web.dto.response.CarritoResponseDto;

public interface CarritoMapper {
    Carrito toEntity(CarritoRequestDTO dto, Usuario u);
    CarritoResponseDto toDto(Carrito c);
}
