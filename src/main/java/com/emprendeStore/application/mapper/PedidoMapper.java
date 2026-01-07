package com.emprendeStore.application.mapper;

import com.emprendeStore.domain.model.Pedido;
import com.emprendeStore.domain.model.Usuario;
import com.emprendeStore.web.dto.request.PedidoRequestDto;
import com.emprendeStore.web.dto.response.PedidoResponseDto;
import com.emprendeStore.web.dto.response.VentaResponseDto;

import java.math.BigDecimal;
import java.util.List;

public interface PedidoMapper {
    Pedido toEntity(PedidoRequestDto request, Usuario u, String idTransaccion, BigDecimal costoEnvio, String direccionSnapshot);
    PedidoResponseDto toDto(Pedido pedido, List<VentaResponseDto> ventas);
}
