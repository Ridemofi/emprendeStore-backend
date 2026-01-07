package com.emprendeStore.application.service;

import com.emprendeStore.web.dto.request.PedidoRequestDto;
import com.emprendeStore.web.dto.response.PedidoResponseDto;

public interface PedidoService {
    PedidoResponseDto crearPedido(PedidoRequestDto request);
}
