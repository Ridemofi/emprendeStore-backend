package com.emprendeStore.application.mapper;

import com.emprendeStore.domain.enums.EstadoVenta;
import com.emprendeStore.domain.model.Emprendedor;
import com.emprendeStore.domain.model.Pedido;
import com.emprendeStore.domain.model.Venta;
import com.emprendeStore.web.dto.response.VentaResponseDto;

public interface VentaMapper {
    Venta toEntity(Pedido p, Emprendedor e, EstadoVenta estadoVenta);
    VentaResponseDto toDto(Venta v);
}
