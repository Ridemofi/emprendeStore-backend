package com.emprendeStore.application.mapper.imp;

import com.emprendeStore.application.mapper.PedidoMapper;
import com.emprendeStore.domain.Estados.EstadoVenta;
import com.emprendeStore.domain.model.Pedido;
import com.emprendeStore.domain.model.Usuario;
import com.emprendeStore.web.dto.request.PedidoRequestDto;
import com.emprendeStore.web.dto.response.PedidoResponseDto;
import com.emprendeStore.web.dto.response.VentaResponseDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class PedidoMapperImpl implements PedidoMapper {

    @Override
    public Pedido toEntity(PedidoRequestDto request, Usuario u, String idTransaccion, BigDecimal costoEnvio, String direccionSnapshot) {
        return Pedido.builder()
                .usuario(u)
                .idTransaccion(idTransaccion)
                .metodoPago(request.getMetodoPago())
                .estadoPedido(EstadoVenta.PENDIENTE)
                .direccionSnapshot(direccionSnapshot)
                .costoEnvioTotal(costoEnvio)
                .subtotalGlobal(BigDecimal.ZERO)
                .totalPagado(BigDecimal.ZERO)
                .build();
    }

    @Override
    public PedidoResponseDto toDto(Pedido pedido, List<VentaResponseDto> ventas) {
        return PedidoResponseDto.builder()
                .idPedido(pedido.getIdPedido())
                .idUsuario(pedido.getUsuario().getIdUsu())
                .idTransaccion(pedido.getIdTransaccion())
                .subtotalGlobal(pedido.getSubtotalGlobal())
                .costoEnvioTotal(pedido.getCostoEnvioTotal())
                .totalPagado(pedido.getTotalPagado())
                .metodoPago(pedido.getMetodoPago())
                .estadoPedido(pedido.getEstadoPedido())
                .fechaPedido(pedido.getFechaPedido())
                .direccionEnvio(pedido.getDireccionSnapshot())
                .ventas(ventas)
                .build();
    }
}
