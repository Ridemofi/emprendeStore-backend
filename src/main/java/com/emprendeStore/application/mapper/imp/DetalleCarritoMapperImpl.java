package com.emprendeStore.application.mapper.imp;

import com.emprendeStore.application.mapper.DetalleCarritoMapper;
import com.emprendeStore.application.mapper.ProductorMapper;
import com.emprendeStore.domain.model.Carrito;
import com.emprendeStore.domain.model.DetalleCarrito;
import com.emprendeStore.domain.model.Producto;
import com.emprendeStore.web.dto.request.DetalleCarritoRequestDTO;
import com.emprendeStore.web.dto.response.DetalleCarritoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DetalleCarritoMapperImpl implements DetalleCarritoMapper {
    private final ProductorMapper pm;
    @Override
    public DetalleCarrito toEntity(DetalleCarritoRequestDTO dto, Carrito c, Producto p) {
        return DetalleCarrito.builder()
                .carrito(c)
                .producto(p)
                .cantidad(dto.getCantidad())
                .precioUnitario(p.getPrecio())
                .build();
    }

    @Override
    public DetalleCarritoResponseDto toDto(DetalleCarrito dc) {
        BigDecimal st = dc.getPrecioUnitario().multiply(new BigDecimal(dc.getCantidad()));
        return DetalleCarritoResponseDto.builder()
                .idDetalle(dc.getIdDetalleCarrito())
                .producto(pm.toDto(dc.getProducto()))
                .precioUnitario(dc.getPrecioUnitario())
                .cantidad(dc.getCantidad())
                .subtotal(st)
                .build();
    }
}
