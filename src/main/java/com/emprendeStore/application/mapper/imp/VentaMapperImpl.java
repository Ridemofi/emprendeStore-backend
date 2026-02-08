package com.emprendeStore.application.mapper.imp;

import com.emprendeStore.application.mapper.DetalleVentaMapper;
import com.emprendeStore.application.mapper.VentaMapper;
import com.emprendeStore.domain.enums.EstadoVenta;
import com.emprendeStore.domain.model.Emprendedor;
import com.emprendeStore.domain.model.Pedido;
import com.emprendeStore.domain.model.Venta;
import com.emprendeStore.web.dto.response.DetalleVentaResponseDto;
import com.emprendeStore.web.dto.response.VentaResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class VentaMapperImpl implements VentaMapper {

    private final DetalleVentaMapper dvMapper;

    @Override
    public Venta toEntity(Pedido p, Emprendedor e, EstadoVenta estadoVenta) {
        return Venta.builder()
                .pedido(p)
                .emprendedor(e)
                .nombreEmprendedorSnapshot(e.getNombreemp()) // Guardamos el nombre actual del emprendedor
                .estadoVenta(estadoVenta)
                .build();
    }

    @Override
    public VentaResponseDto toDto(Venta v) {
        if (v == null) {
            return null;
        }
        List<DetalleVentaResponseDto> detallesDto = Collections.emptyList();
        if (v.getDetalles() != null) {
            detallesDto = v.getDetalles().stream()
                    .map(dvMapper::toDto)
                    .collect(Collectors.toList());
        }

        return VentaResponseDto.builder()
                .idVenta(v.getIdVenta())
                .idPedido(v.getPedido().getIdPedido())
                .idEmpre(v.getEmprendedor().getIdempre())
                .nombreEmprendedor(v.getNombreEmprendedorSnapshot())
                .subTotal(v.getSubtotal())
                .total(v.getTotal())
                .estadoVenta(v.getEstadoVenta())
                .detalles(detallesDto)
                .build();
    }
}
