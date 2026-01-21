package com.emprendeStore.application.mapper.imp;

import com.emprendeStore.application.mapper.VentaMapper;
import com.emprendeStore.domain.Estados.EstadoVenta;
import com.emprendeStore.domain.model.Emprendedor;
import com.emprendeStore.domain.model.Pedido;
import com.emprendeStore.domain.model.Venta;
import com.emprendeStore.web.dto.response.VentaResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VentaMapperImpl implements VentaMapper {

    @Override
    public Venta toEntity(Pedido p, Emprendedor e, EstadoVenta estadoVenta) {
        return Venta.builder()
                .pedido(p)
                .emprendedor(e)
                .estadoVenta(estadoVenta)
                .build();
    }

    @Override
    public VentaResponseDto toDto(Venta v) {
        if (v == null) {
            return null;
        }
        // obtenemos los datos globales desde el Pedido padre
        return VentaResponseDto.builder()
                .idVenta(v.getIdVenta())
                .total(v.getTotal())
                .metodoPago(v.getPedido().getMetodoPago())
                .estadoVenta(v.getEstadoVenta())
                .fechaVenta(v.getPedido().getFechaPedido())
                .idTransaccion(v.getPedido().getIdTransaccion())
                .nombreCliente(v.getPedido().getUsuario().getNombReal())
                .build();
    }
}
