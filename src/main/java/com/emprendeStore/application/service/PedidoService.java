package com.emprendeStore.application.service;

import com.emprendeStore.web.dto.request.PedidoRequestDto;
import com.emprendeStore.web.dto.response.PedidoResponseDto;

import java.util.List;

public interface PedidoService {
    PedidoResponseDto crearPedido(PedidoRequestDto request);
    List<PedidoResponseDto> listarPedidoXidUsuario(Long idUsuario);
}
