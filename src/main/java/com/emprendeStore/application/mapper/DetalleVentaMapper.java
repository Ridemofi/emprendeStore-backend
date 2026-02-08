package com.emprendeStore.application.mapper;

import com.emprendeStore.domain.model.DetalleVenta;
import com.emprendeStore.domain.model.Producto;
import com.emprendeStore.domain.model.Venta;
import com.emprendeStore.web.dto.request.DetalleVentaRequestDto;
import com.emprendeStore.web.dto.response.DetalleVentaResponseDto;

public interface DetalleVentaMapper {
    DetalleVenta toEntity(DetalleVentaRequestDto dto, Producto p, Venta v);
    DetalleVentaResponseDto toDto(DetalleVenta detalleVenta);
}
