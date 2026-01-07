package com.emprendeStore.application.mapper.imp;

import com.emprendeStore.application.mapper.DetalleVentaMapper;
import com.emprendeStore.domain.model.DetalleVenta;
import com.emprendeStore.domain.model.Producto;
import com.emprendeStore.domain.model.Venta;
import com.emprendeStore.web.dto.request.DetalleVentaRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DetalleVentaMapperImpl implements DetalleVentaMapper {
    @Override
    public DetalleVenta toEntity(DetalleVentaRequestDto dto, Producto p, Venta v) {
        return DetalleVenta.builder()
                .venta(v)
                .producto(p)
                .nombreProducto(dto.getNombreProducto())
                .cantidad(dto.getCantidad())
                .precioUnit(dto.getPrecioUnitario())
                .subtotal(dto.getSubtotal())
                .build();
    }
}
