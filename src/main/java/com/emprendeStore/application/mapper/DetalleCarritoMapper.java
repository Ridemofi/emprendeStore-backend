package com.emprendeStore.application.mapper;

import com.emprendeStore.domain.model.Carrito;
import com.emprendeStore.domain.model.DetalleCarrito;
import com.emprendeStore.domain.model.Producto;
import com.emprendeStore.web.dto.request.DetalleCarritoRequestDTO;
import com.emprendeStore.web.dto.response.DetalleCarritoResponseDto;

public interface DetalleCarritoMapper {
    DetalleCarrito toEntity(DetalleCarritoRequestDTO dto, Carrito c, Producto p);
    DetalleCarritoResponseDto toDto(DetalleCarrito dc);
}
